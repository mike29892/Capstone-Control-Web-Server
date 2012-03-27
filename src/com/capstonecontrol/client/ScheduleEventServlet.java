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
	        
	        String username = "";      
	        try {
				User user = userService.getCurrentUser();
				username = user.getNickname();
			} catch (Exception e) {
				username = req.getParameter("user");
			}
			if (username == null){
				username = req.getParameter("user"); 
			}
	        

	        Key moduleKey = KeyFactory.createKey("user", username);
	        String moduleName = req.getParameter("moduleName");	        
	        String moduleType = req.getParameter("moduleType");
	        String theaction = req.getParameter("action");
	        String recur = req.getParameter("recur");
	        String Sun = req.getParameter("Sun");
	        String Mon = req.getParameter("Mon");
	        String Tue = req.getParameter("Tue");
	        String Wed = req.getParameter("Wed");
	        String Thu = req.getParameter("Thu");
	        String Fri = req.getParameter("Fri");
	        String Sat = req.getParameter("Sat");
	        String theval = req.getParameter("value");
	        String theschedDate = req.getParameter("schedDate");
	        String isactive = req.getParameter("active");
	        //String offset = req.getParameter("offset");
	        //String eveType = req.getParameter("eventType");
	        
	        Date date = new Date();	        
	        Date finalschedDate = new Date(Long.parseLong(theschedDate));
	        
	        
	        /*
	        String Parsestring;	        
	        Parsestring = "MM/dd/yyyy kk:mm";
			try {
				finalschedDate = new SimpleDateFormat(Parsestring, Locale.ENGLISH).parse(theschedDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				finalschedDate.setTime(0);
			}*/
	        
	    
			
			///here we use the offset to calculate the UTC time of the 
			//scheduled event
			//int calcoff = finalschedDate.getHours() + (Integer.parseInt(offset)/60);
			//finalschedDate.setHours(calcoff);
			
			///create seperate fields for the date
			//dow mon dd hh:mm:ss zzz yyyy
			
			int Year = finalschedDate.getYear()+1900;
			int Month = finalschedDate.getMonth()+1;
			int Day = finalschedDate.getDate();
			
	        Entity module = new Entity("scheduleModuleEvent", moduleKey);
	        module.setProperty("user", username);
	        module.setProperty("date", date);
	        module.setProperty("moduleName", moduleName);
	        module.setProperty("moduleType", moduleType);
	        module.setProperty("action", theaction);
	        module.setProperty("value", theval);
	        module.setProperty("Sun", Sun);
	        module.setProperty("Mon", Mon);
	        module.setProperty("Tue", Tue);
	        module.setProperty("Wed", Wed);
	        module.setProperty("Thu", Thu);
	        module.setProperty("Fri", Fri);
	        module.setProperty("Sat", Sat);
	        module.setProperty("recur", recur);
	        module.setProperty("schedDate", finalschedDate);
	        module.setProperty("year", Year);
	        module.setProperty("month", Month);
	        module.setProperty("day", Day);
	        module.setProperty("hours", finalschedDate.getHours());
	        module.setProperty("minutes", finalschedDate.getMinutes());
	        module.setProperty("active", isactive);
	        
	        

	        DatastoreService datastore =
	                DatastoreServiceFactory.getDatastoreService();
	        datastore.put(module);

	        //resp.sendRedirect("/control.jsp");
	    }
}
