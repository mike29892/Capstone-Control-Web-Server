package com.capstonecontrol.client;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ClearAlertsServelet extends HttpServlet {
    @SuppressWarnings("unused")
	private static final Logger log =
            Logger.getLogger(ClearAlertsServelet.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String username = user.getNickname();     

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        //QUERY FOR UN-ACKED ALERTS    
        Query q = new Query("Alerts");
        //filter for events on that day     
        q.addFilter("user", FilterOperator.EQUAL, username);
        q.addFilter("ACK", FilterOperator.EQUAL, false);  
        q.addSort("date", SortDirection.DESCENDING);   

        PreparedQuery pq = datastore.prepare(q);

        //loop through and output the proper lines
        for (Entity result : pq.asIterable()) {
        	 datastore.delete(result.getKey());        
        }       

     

      
    }
}