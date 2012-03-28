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
	        boolean recur = Boolean.parseBoolean(req.getParameter("recur"));
	        boolean Sun = Boolean.parseBoolean(req.getParameter("Sun"));
	        boolean Mon = Boolean.parseBoolean(req.getParameter("Mon"));
	        boolean Tue = Boolean.parseBoolean(req.getParameter("Tue"));
	        boolean Wed = Boolean.parseBoolean(req.getParameter("Wed"));
	        boolean Thu = Boolean.parseBoolean(req.getParameter("Thu"));
	        boolean Fri = Boolean.parseBoolean(req.getParameter("Fri"));
	        boolean Sat = Boolean.parseBoolean(req.getParameter("Sat"));
	        int theval = Integer.parseInt(req.getParameter("value"));
	        String theschedDate = req.getParameter("schedDate");
	        boolean isactive = Boolean.parseBoolean(req.getParameter("active"));
	        int offset = Integer.parseInt(req.getParameter("offset"));
	        //String eveType = req.getParameter("eventType");
	        
	        Date date = new Date();	        
	        Date finalschedDate = new Date(Long.parseLong(theschedDate));
	        	              
			///create seperate fields for the date
			//dow mon dd hh:mm:ss zzz yyyy
			
			int Year = finalschedDate.getYear()+1900;
			int Month = finalschedDate.getMonth()+1;
			int Day = finalschedDate.getDate();
			int Hours = finalschedDate.getHours();
			int Minutes = finalschedDate.getMinutes();
			
			boolean Sun2, Mon2, Tue2, Wed2, Thu2, Fri2, Sat2;
			//use time zone offset hours shift days of the week appropriately
			//use new booleans to preserve day of the week values while shifting
			if((Hours<=offset)){
				//fill temp boolean values
				Sun2 = Sat;
				Mon2 = Sun;
				Tue2 = Mon;
				Wed2 = Tue;
				Thu2 = Wed;
				Fri2 = Thu;
				Sat2 = Fri;
				//put temp values into original values
				Sun = Sun2;
				Mon = Mon2;
				Tue = Tue2;
				Wed = Wed2;
				Thu = Thu2;
				Fri = Fri2;
				Sat = Sat2;
			}else if((offset<0) && (Hours)>(24+offset) ){ //offset is negativie then shift opposite
				//fill temp boolean values
				Sun2 = Mon;
				Mon2 = Tue;
				Tue2 = Wed;
				Wed2 = Thu;
				Thu2 = Fri;
				Fri2 = Sat;
				Sat2 = Sun;
				//put temp values into original values
				Sun = Sun2;
				Mon = Mon2;
				Tue = Tue2;
				Wed = Wed2;
				Thu = Thu2;
				Fri = Fri2;
				Sat = Sat2;
			}
			
			
			
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
	        module.setProperty("hours", Hours);
	        module.setProperty("minutes", Minutes);
	        module.setProperty("active", isactive);
	        module.setProperty("TimeOffset", offset);
	        
	        

	        DatastoreService datastore =
	                DatastoreServiceFactory.getDatastoreService();
	        datastore.put(module);

	        //resp.sendRedirect("/control.jsp");
	    }
}
