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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class PerformScheduledEvents extends HttpServlet {
	//List<Entity> scheduledModuleEvents;
	ArrayList<Entity> EVENTS = new ArrayList<Entity>(); 
	ArrayList<String> EVENTSsched = new ArrayList<String>();
	Date currentDate = new Date();

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
	    
	    
		// now get the arraylist of all scheduled events
		getScheduledEvents(out);
		// now remove the ones that shouldn't run
		//checkEventsToRun(out);
		//finally go through array and execute them
		
		
	}

	/*Execute all events in the list */
	private void RunEvents(PrintWriter out) {
		out.println(EVENTS.size() +"is the size");
		for(int i=0; i<EVENTS.size(); i++){			
			doEvent(EVENTS.get(i));
			
		}
		
	}


	/*Check all arraylist entities and 
	 * remove ones that shouldn't run*/
	private void checkEventsToRun(PrintWriter out) {
		out.println("the current time is " + currentDate.toString() + "<br/>");
		for(int i=0; i<EVENTS.size(); i++){
			
			String sched = new String(EVENTSsched.get(i));
			//Sat Mar 24 21:15:00 UTC 2012
			String[] sp1 = new String[0];
			String[] time = new String[0];
			sp1 = sched.split(" ");
			time = sp1[3].split(":");
			int hours = new Integer(Integer.parseInt(time[0]));
			int minutes = new Integer(Integer.parseInt(time[1]));
			
			String active = (String) EVENTS.get(i).getProperty("active");
			String value = (String) EVENTS.get(i).getProperty("value");
			//check it versus curent time
			 out.println("<br/>"+value + "    " +currentDate.getHours() + " == "+ hours +" and "+ currentDate.getMinutes()+ " == " +minutes + " active is " + active);
			
			 
			 
			 if( (currentDate.getHours() == hours ) && (currentDate.getMinutes() == minutes) && (Integer.parseInt(active)==1)){
				//left in arraylist	
				doEvent(EVENTS.get(i));
				out.println(i + " checked<br/>");
			}else{
				out.println(i + " BAD<br/>");
			}
		}
		
	}



	/*
	 * runs the scheduled event
	 */
	private void doEvent(Entity scheduledModuleEvent) {
		
		
		// do post to MQTT based on the scheduled event
		String username = (String) scheduledModuleEvent.getProperty("user");
		String modulename = (String) scheduledModuleEvent.getProperty("moduleName");
		String value = scheduledModuleEvent.getProperty("value").toString();
		String moduletype = (String) scheduledModuleEvent.getProperty("moduleType");
		String action = (String) scheduledModuleEvent.getProperty("action");
		///for debugging
		String time = scheduledModuleEvent.getProperty("schedDate").toString();
		String mqttchan = "/" + username + "/" + modulename;
		try {
			// Construct data
			String data = URLEncoder.encode("where", "UTF-8") + "="+ URLEncoder.encode(mqttchan, "UTF-8");
			data += "&" + URLEncoder.encode("message", "UTF-8") + "="+ URLEncoder.encode(value+time, "UTF-8");

			// Send data
			URL url = new URL("http://23.21.229.136/message.php");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
			actionmodule.setProperty("value", value);

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			datastore.put(actionmodule);

		} catch (Exception e) {
		}
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
		EVENTS.clear();
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
         case 2:  bin = "Tue";
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
		
		 //out.println(currentDate.getHours() + " and " +currentDate.getMinutes() + "<br/>");
		 
		 
		/////////////////////////////////////////////////////////
		////////////////RECURRING EVENTS QUERY///////////////////
		/////////////////////////////////////////////////////////
		// The Query interface assembles a query
		Query q = new Query("scheduleModuleEvent");
		//filter for events on that day		
		q.addFilter(bin, FilterOperator.EQUAL, true);
		q.addFilter("hours", FilterOperator.EQUAL, currentDate.getHours());
		q.addFilter("minutes", FilterOperator.EQUAL, currentDate.getMinutes());
		q.addFilter("recur", FilterOperator.EQUAL, true);
		
	
		// PreparedQuery contains the methods for fetching query results
		// from the datastore
		PreparedQuery pq = datastore.prepare(q);
		

		for (Entity result : pq.asIterable()) {
		  doEvent(result);
		  /*
		    String action = (String) result.getProperty("action");
			String date = result.getProperty("date").toString();
			//String days = (String) result.getProperty("days");
			String modname = (String) result.getProperty("moduleName");
			String modtype = (String) result.getProperty("moduleType");
			String scheddate = result.getProperty("schedDate").toString();
			String user = (String) result.getProperty("user");
			String val = result.getProperty("value").toString();
		  
		  out.println(action + "---" + date + "---" + modname + "---" + modtype+ "---"  + scheddate + "---"  + user + "---" + val+"<br/>");
		  */
		}
		
		
		//get month in string	
		
		int Year = currentDate.getYear()+1900;
		int Month = currentDate.getMonth()+1;
		int Day = currentDate.getDate();

		/////////////////////////////////////////////////////////
		////////////////One Time EVENTS QUERY ///////////////////
		/////////////////////////////////////////////////////////
		// The Query interface assembles a query
		Query qo = new Query("scheduleModuleEvent");
		//filter for events on that day		
		//qo.addFilter(bin, FilterOperator.EQUAL, "1");
		qo.addFilter("hours", FilterOperator.EQUAL, currentDate.getHours());
		qo.addFilter("minutes", FilterOperator.EQUAL, currentDate.getMinutes());
		qo.addFilter("recur", FilterOperator.EQUAL, false);
		qo.addFilter("active", FilterOperator.EQUAL, true);
		qo.addFilter("year", FilterOperator.EQUAL, Year);
		qo.addFilter("month", FilterOperator.EQUAL, Month);
		qo.addFilter("day", FilterOperator.EQUAL, Day);


		// PreparedQuery contains the methods for fetching query results
		// from the datastore
		PreparedQuery pqo = datastore.prepare(qo);
				
		for (Entity result : pqo.asIterable()) {							
			doEvent(result);
			///set active to 0
			result.setProperty("active", false);
			datastore.put(result);
		/*
			String act = result.getProperty("active").toString();
			String scheddate = result.getProperty("schedDate").toString();			
			String action = (String) result.getProperty("action");
			String date = result.getProperty("date").toString();
			//String days = (String) result.getProperty("days");
			String modname = (String) result.getProperty("moduleName");
			String modtype = (String) result.getProperty("moduleType");			
			String user = (String) result.getProperty("user");
			String val = result.getProperty("value").toString();
			out.println(action + "---" + date + "---" + modname + "---" + modtype+ "---"  + scheddate + "---"  + user + "---" + val+ "active: "+ act +" <br/>");
			*/
		}
		
		
		

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