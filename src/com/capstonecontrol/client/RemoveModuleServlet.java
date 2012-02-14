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
public class RemoveModuleServlet extends HttpServlet{
	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(RemoveModuleServlet.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
				
		String thekey = req.getParameter("thekey");
	
		Key realkey = KeyFactory.stringToKey(thekey);
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		datastore.delete(realkey);

		
		//resp.sendRedirect("/addmodule.jsp?user=" + username);
	}
}
