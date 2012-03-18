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
public class LogModuleEventTestServlet extends HttpServlet {

	String username;

	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(LogModuleEventTestServlet.class.getName());

	@SuppressWarnings("deprecation")
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
		// get the user name
		UserService userService = UserServiceFactory.getUserService();
		// get the entered date
		String hour = req.getParameter("hour");
		String minute = req.getParameter("minute");
		String day = req.getParameter("day");
		String month = req.getParameter("month");
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
			//For the test case the date will be entered
			Date date = new Date();
			date.setMonth(Integer.parseInt(month)-1);
			date.setDate(Integer.parseInt(day));
			date.setHours(Integer.parseInt(hour));
			date.setMinutes(Integer.parseInt(minute));
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
			
			resp.sendRedirect("/logModuleEventTestAdd.html");

		} catch (Exception e) {
		}
	}
}