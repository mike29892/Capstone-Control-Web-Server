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
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class LogModuleEventServlet extends HttpServlet {

	String username;

	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(LogModuleEventServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// /get posted module type
		String moduletype = req.getParameter("moduleType");
		// /get posted module name
		String modulename = req.getParameter("moduleName");
		// get the message
		String message = req.getParameter("message");
		// get the action
		String action = req.getParameter("action");
		// /get the user name
		UserService userService = UserServiceFactory.getUserService();
		try {
			User user = userService.getCurrentUser();
			username = user.getNickname();
		} catch (Exception e) {
			username = req.getParameter("user");
		}
		if (username == null){
			username = req.getParameter("user");
		}

		try {
			// log the event in datastore
			Key moduleKey = KeyFactory.createKey("user", username);
			Date date = new Date();
			Entity actionmodule = new Entity("moduleEvent", moduleKey);
			actionmodule.setProperty("user", username);
			actionmodule.setProperty("date", date);
			actionmodule.setProperty("moduleName", modulename);
			actionmodule.setProperty("moduleType", moduletype);
			actionmodule.setProperty("action", action);
			actionmodule.setProperty("value", message);

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			datastore.put(actionmodule);

		} catch (Exception e) {
		}
	}
}