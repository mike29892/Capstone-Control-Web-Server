package com.capstonecontrol.client;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ControlModuleServlet extends HttpServlet {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ControlModuleServlet.class
			.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		///get posted module type
		String moduletype = req.getParameter("moduleType");
		///get posted module name
		String modulename = req.getParameter("moduleName");
		//get the message
		String message = req.getParameter("message");
		//get the action
		String action = req.getParameter("action");
		///get the user name
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
		String username = user.getNickname();
		
		String mqttchan = "/"+username+"/"+modulename;

		try {
		    // Construct data
		    String data = URLEncoder.encode("where", "UTF-8") + "=" + URLEncoder.encode(mqttchan, "UTF-8");
		    data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");

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
		    
		    //log the event in datastore
		    
		    Key moduleKey = KeyFactory.createKey("user", username);	        
	        Date date = new Date();
	        Entity actionmodule = new Entity("moduleEvent", moduleKey);
	        actionmodule.setProperty("user", username);
	        actionmodule.setProperty("date", date);
	        actionmodule.setProperty("moduleName", modulename);
	        actionmodule.setProperty("moduleType", moduletype);
	        actionmodule.setProperty("action", action);
	        actionmodule.setProperty("value", message);

	        DatastoreService datastore =
	                DatastoreServiceFactory.getDatastoreService();
	        datastore.put(actionmodule);
       
	        
		} catch (Exception e) {
		}
	}
}