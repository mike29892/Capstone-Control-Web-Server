package com.capstonecontrol.server;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

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
    	
        
    }
}