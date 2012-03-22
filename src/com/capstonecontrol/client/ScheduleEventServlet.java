package com.capstonecontrol.client;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ScheduleEventServlet extends HttpServlet {

	 @SuppressWarnings("unused")
		private static final Logger log =
	            Logger.getLogger(ScheduleEventServlet.class.getName());

	    public void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws IOException {
	    	UserService userService = UserServiceFactory.getUserService();
	        User user = userService.getCurrentUser();
	        String username = user.getNickname();

	        Key moduleKey = KeyFactory.createKey("user", username);
	        String moduleName = req.getParameter("moduleName");	        
	        String moduleType = req.getParameter("moduleType");
	        String theaction = req.getParameter("action");
	        String thedays = req.getParameter("days");
	        String theval = req.getParameter("value");
	        String theschedDate = req.getParameter("schedDate");
	        String isactive = req.getParameter("active");
	        String eveType = req.getParameter("eventType");
	        
	        Date date = new Date();
	        
	        /*
	        //parse date string
	        String[] datesplit = theschedDate.split("/");
	        //04/19/2012 13:21
	        
	        int month = Integer.parseInt( datesplit[0] );
	        int day = Integer.parseInt( datesplit[1] );
	        String[] datesplit2 = datesplit[2].split(" ");	        
	        int year = Integer.parseInt( datesplit2[0] );
	        String[] datesplit3 = datesplit2[1].split(":");	 
	        int hours = Integer.parseInt( datesplit3[0] );
	        int minutes = Integer.parseInt( datesplit3[1] );
	        int seconds = 0;
	        */
	        
	        Date finalschedDate = new Date();
	        
	        String Parsestring;
	        /*
	        if(eveType == "single"){
	        	Parsestring = "MM/dd/yyyy hh:mm";
	        }else{
	        	Parsestring = "hh:mm";
	        }*/
	        Parsestring = "MM/dd/yyyy hh:mm";
			try {
				finalschedDate = new SimpleDateFormat(Parsestring, Locale.ENGLISH).parse(theschedDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				finalschedDate.setTime(0);
			}
	      
	        Entity module = new Entity("schedModuleEvent", moduleKey);
	        module.setProperty("user", username);
	        module.setProperty("date", date);
	        module.setProperty("moduleName", moduleName);
	        module.setProperty("moduleType", moduleType);
	        module.setProperty("action", theaction);
	        module.setProperty("value", theval);
	        module.setProperty("days", thedays);
	        module.setProperty("schedDate", finalschedDate);
	        module.setProperty("active", isactive);
	        
	        

	        DatastoreService datastore =
	                DatastoreServiceFactory.getDatastoreService();
	        datastore.put(module);

	        //resp.sendRedirect("/control.jsp");
	    }
}
