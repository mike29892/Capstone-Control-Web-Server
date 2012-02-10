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
public class AddModuleServlet extends HttpServlet {
    @SuppressWarnings("unused")
	private static final Logger log =
            Logger.getLogger(AddModuleServlet.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String username = user.getNickname();

        Key moduleKey = KeyFactory.createKey("user", username);
        String moduleName = req.getParameter("moduleName");
        String macAddr = req.getParameter("macAddr");
        String moduleType = req.getParameter("moduleType");
        Date date = new Date();
        Entity module = new Entity("Module", moduleKey);
        module.setProperty("user", username);
        module.setProperty("date", date);
        module.setProperty("moduleName", moduleName);
        module.setProperty("macAddr", macAddr);
        module.setProperty("moduleType", moduleType);

        DatastoreService datastore =
                DatastoreServiceFactory.getDatastoreService();
        datastore.put(module);

        resp.sendRedirect("/login.jsp?user="
                + username);
    }
}