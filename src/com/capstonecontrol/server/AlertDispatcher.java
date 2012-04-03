package com.capstonecontrol.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class AlertDispatcher extends HttpServlet {
    @SuppressWarnings("unused")
	private static final Logger log =
            Logger.getLogger(AlertDispatcher.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
 
    	String warning = req.getParameter("warning");
    	String user = req.getParameter("user");
    	String modname = req.getParameter("moduleName");
    	Date date = new Date();
        Entity module = new Entity("Alerts");
        module.setProperty("Warning", warning);
        module.setProperty("user", user);
        module.setProperty("moduleName", modname);
        module.setProperty("date", date);
        module.setProperty("ACK", false);
            
        DatastoreService datastore =
               DatastoreServiceFactory.getDatastoreService();
        datastore.put(module);
    	
        
        ///query for all user phone numbers  
        
        // The Query interface assembles a query
        //filtered by user and moduleName      
        Query q = new Query("PhoneNumbers");
        //filter for events on that day     
        q.addFilter("user", FilterOperator.EQUAL, user);          
        q.addSort("date", SortDirection.DESCENDING);   
        
        PreparedQuery pq = datastore.prepare(q);
      
        //loop through and send out texts
        for (Entity result : pq.asIterable()) {                              
            String theNumber = (String)result.getProperty("phoneNumber");

            String data = URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(theNumber, "UTF-8");
         	data += "&" + URLEncoder.encode("moduleName", "UTF-8") + "=" + URLEncoder.encode(modname, "UTF-8");
         	data += "&" + URLEncoder.encode("alert", "UTF-8") + "=" + URLEncoder.encode(warning, "UTF-8");
         	// Send data
         	URL url = new URL("http://23.21.229.136/DispatchText.php");
         	URLConnection conn = url.openConnection();
         	conn.setDoOutput(true);
         	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
         	wr.write(data);
         	wr.flush();
         	// Get the response
         	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         	wr.close();
         	rd.close();	    
                         
         }      
        
        /*
        String useremail = user + "@gmail.com";
        
        /////Send out notifications to android users
        String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(useremail, "UTF-8");
     	data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(warning, "UTF-8");    
     	// Send data
     	URL url = new URL("http://capstonecontrol.appspot.com/AndroidPush.jsp");
     	URLConnection conn = url.openConnection();
     	conn.setDoOutput(true);
     	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
     	wr.write(data);
     	wr.flush();
     	// Get the response
     	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
     	wr.close();
     	rd.close();	 
      */
    }      
}
    
    
    