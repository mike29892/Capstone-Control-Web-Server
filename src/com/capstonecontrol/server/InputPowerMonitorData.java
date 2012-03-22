package com.capstonecontrol.server;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.capstonecontrol.client.AddModuleServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class InputPowerMonitorData extends HttpServlet {
    @SuppressWarnings("unused")
	private static final Logger log =
            Logger.getLogger(InputPowerMonitorData.class.getName());

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
 
    	String data = req.getParameter("data");
    	Date date = new Date();
        Entity module = new Entity("XBeeData");
        module.setProperty("incoming", data);
        module.setProperty("date", date);
            
        DatastoreService datastore =
               DatastoreServiceFactory.getDatastoreService();
        datastore.put(module);
    	

        
    }
}