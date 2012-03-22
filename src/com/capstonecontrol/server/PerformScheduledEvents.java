package com.capstonecontrol.server;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PerformScheduledEvents extends HttpServlet {
	List<Entity> scheduledModuleEvents;
	Date currentDate;

	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(PerformScheduledEvents.class.getName());

	/*
	 * this method will get called by the cron job
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// first start with getting the date so we can decide if anything needs
		// to be set off
		currentDate = new Date();
		// zero seconds
		currentDate.setSeconds(0);
		
		////here for outputting html to debug***********************
		resp.setContentType("text/html");
	    PrintWriter out = resp.getWriter();
	    out.println("<h1>results</h1><br/>");
	    
	    
		// now get the list of all scheduled events
		getScheduledEvents(out);
		// now check if any need to be ran
		//checkEventsToRun();
		
		
	}

	/*
	 * Checks the found schedules events to see if they should be ran
	 */
	private void checkEventsToRun() {
		int scheduledDays;
		Date scheduledDate;
		for (Entity scheduledModuleEvent : scheduledModuleEvents) {
			scheduledDays = (Integer) scheduledModuleEvent
					.getProperty("scheduledDays");
			scheduledDate = (Date) scheduledModuleEvent
					.getProperty("scheduledDate");
			if (scheduledDays == 0) {
				// then scheduled event is a one time event
				if (checkOneTimeEvent(scheduledDate))
					doEvent(scheduledModuleEvent);
				continue;
			} else {
				// the scheduled event is reoccuring based on the days of the
				// week so figure out which days based on the int
				if (checkWeekEvent(scheduledDays, scheduledDate))
					doEvent(scheduledModuleEvent);
			}

		}
	}

	/*
	 * runs the scheduled event
	 */
	private void doEvent(Entity scheduledModuleEvent) {
		// do post to MQTT based on the scheduled event
		String username = (String) scheduledModuleEvent.getProperty("username");
		String modulename = (String) scheduledModuleEvent
				.getProperty("moduleName");
		String message = (String) scheduledModuleEvent
				.getProperty("message");
		String moduletype = (String) scheduledModuleEvent
				.getProperty("moduleType");
		String action = (String) scheduledModuleEvent
				.getProperty("action");
		String mqttchan = "/" + username + "/" + modulename;
		try {
			// Construct data
			String data = URLEncoder.encode("where", "UTF-8") + "="
					+ URLEncoder.encode(mqttchan, "UTF-8");
			data += "&" + URLEncoder.encode("message", "UTF-8") + "="
					+ URLEncoder.encode(message, "UTF-8");

			// Send data
			URL url = new URL("http://23.21.229.136/message.php");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			wr.close();
			rd.close();

			// log the event in datastore

			Key moduleKey = KeyFactory.createKey("user", username);
			Date date = new Date();
			Entity actionmodule = new Entity("moduleEvent", moduleKey);
			actionmodule.setProperty("user", username);
			actionmodule.setProperty("date", date);
			actionmodule.setProperty("moduleName", modulename);
			actionmodule.setProperty("moduleType", moduletype);
			actionmodule.setProperty("action", action);
			actionmodule.setProperty("value", message);

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			datastore.put(actionmodule);

		} catch (Exception e) {
		}
	}

	/*
	 * Returns true if the event needs to be ran
	 */
	@SuppressWarnings("deprecation")
	private boolean checkWeekEvent(int scheduledDays, Date scheduledDate) {
		Boolean Monday = false, Tuesday = false, Wednesday = false, Thursday = false, Friday = false, Saturday = false, Sunday = false;
		if (scheduledDays > 63) {
			Monday = true;
			scheduledDays = scheduledDays - 64;
		}
		if (scheduledDays > 31) {
			Tuesday = true;
			scheduledDays = scheduledDays - 32;
		}
		if (scheduledDays > 15) {
			Wednesday = true;
			scheduledDays = scheduledDays - 16;
		}
		if (scheduledDays > 7) {
			Thursday = true;
			scheduledDays = scheduledDays - 8;
		}
		if (scheduledDays > 3) {
			Friday = true;
			scheduledDays = scheduledDays - 4;
		}
		if (scheduledDays > 1) {
			Saturday = true;
			scheduledDays = scheduledDays - 2;
		}
		if (scheduledDays == 1) {
			Sunday = true;
		}

		// 0 sunday, 1 monday , ...
		if (currentDate.getDay() == 0 && Sunday)
			return true;
		if (currentDate.getDay() == 1 && Monday)
			return true;
		if (currentDate.getDay() == 2 && Tuesday)
			return true;
		if (currentDate.getDay() == 3 && Wednesday)
			return true;
		if (currentDate.getDay() == 4 && Thursday)
			return true;
		if (currentDate.getDay() == 5 && Friday)
			return true;
		if (currentDate.getDay() == 6 && Saturday)
			return true;

		return false;
	}

	/*
	 * Returns true if the event needs to be ran
	 */
	private boolean checkOneTimeEvent(Date scheduledDate) {
		if (currentDate.getTime() == scheduledDate.getTime())
			return true;
		return false;
	}

	/*
	 * Pulls scheduled events from the data store
	 */
	private void getScheduledEvents(PrintWriter out) {
		// clear scheduledModuleEvents
		//scheduledModuleEvents.clear();
		// String username = user.getNickname();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		/////////////////////////////////////
		//get date and day of the week///////
		//than query for less than or equal//
		/////////////////////////////////////
		Date today = new Date();
		int theday = today.getDay();
		String bin = "";
		 switch (theday) {
		 case 0:  bin = "Sun";
         		  break;  
         case 1:  bin = "Mon";
                  break;
         case 2:  bin = "Tues";
                  break;
         case 3:  bin = "Wed";
                  break;
         case 4:  bin = "Thu";
                  break;
         case 5:  bin = "Fri";
                  break;
         case 6:  bin = "Sat";
                  break;                
        }
		
		// The Query interface assembles a query
		Query q = new Query("scheduleModuleEvent");
		//filter for events on that day		
		q.addFilter(bin, FilterOperator.EQUAL, "1");
		//q.addFilter("active", FilterOperator.EQUAL, 1);
		//q.addSort("schedDate", SortDirection.DESCENDING); 

		// PreparedQuery contains the methods for fetching query results
		// from the datastore
		PreparedQuery pq = datastore.prepare(q);
		

		for (Entity result : pq.asIterable()) {
		  String action = (String) result.getProperty("action");
		  String date = result.getProperty("date").toString();
		  //String days = (String) result.getProperty("days");
		  String modname = (String) result.getProperty("moduleName");
		  String modtype = (String) result.getProperty("moduleType");
		  String scheddate = result.getProperty("schedDate").toString();
		  String user = (String) result.getProperty("user");
		  String val = (String) result.getProperty("value");
		  
		  out.println(action + "---" + date + "---" + modname + "---" + modtype+ "---"  + scheddate + "---"  + user + "---" + val+"<br/>");
		}
		
		
		/*
		Key moduleKey = KeyFactory.createKey("user", null);
		// Run an ancestor query to ensure we see the most up-to-date
		Query query = new Query("schedModuleEvent", moduleKey).addSort(
				"date", Query.SortDirection.DESCENDING);
		// use max int for limit size
		scheduledModuleEvents = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(2147483647));
		*/
		
		

	}

	/*
	 * public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 * throws IOException { UserService userService =
	 * UserServiceFactory.getUserService(); User user =
	 * userService.getCurrentUser(); String username = user.getNickname();
	 * 
	 * Key moduleKey = KeyFactory.createKey("user", username); String moduleName
	 * = req.getParameter("moduleName"); String moduleMacAddr =
	 * req.getParameter("moduleMacAddr"); String moduleType =
	 * req.getParameter("moduleType"); Date date = new Date(); Entity module =
	 * new Entity("moduleInfo", moduleKey); module.setProperty("user",
	 * username); module.setProperty("date", date);
	 * module.setProperty("moduleName", moduleName);
	 * module.setProperty("moduleMacAddr", moduleMacAddr);
	 * module.setProperty("moduleType", moduleType);
	 * 
	 * DatastoreService datastore = DatastoreServiceFactory
	 * .getDatastoreService(); datastore.put(module);
	 * 
	 * resp.sendRedirect("/addmodule.jsp?user=" + username); }
	 */
}