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
	        String offset = req.getParameter("offset");
	        //String eveType = req.getParameter("eventType");
	        
	        Date date = new Date();	        
	        Date finalschedDate = new Date();
	        
	        String Parsestring;
	        /*
	        if(eveType == "single"){
	        	Parsestring = "MM/dd/yyyy hh:mm";
	        }else{
	        	Parsestring = "hh:mm";
	        }*/
	        Parsestring = "MM/dd/yyyy kk:mm";
			try {
				finalschedDate = new SimpleDateFormat(Parsestring, Locale.ENGLISH).parse(theschedDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				finalschedDate.setTime(0);
			}
			
			///here we use the offset to calculate the UTC time of the 
			//scheduled event
			int calcoff = finalschedDate.getHours() + (Integer.parseInt(offset)/60);
			finalschedDate.setHours(calcoff);
	      
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
	        module.setProperty("schedDate", finalschedDate);
	        module.setProperty("hours", finalschedDate.getHours());
	        module.setProperty("minutes", finalschedDate.getMinutes());
	        module.setProperty("active", isactive);
	        
	        

	        DatastoreService datastore =
	                DatastoreServiceFactory.getDatastoreService();
	        datastore.put(module);

	        //resp.sendRedirect("/control.jsp");
	    }
}
