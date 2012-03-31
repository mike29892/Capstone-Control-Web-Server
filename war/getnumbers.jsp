<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%@ page import="com.google.appengine.api.datastore.PreparedQuery"%>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@ page import="com.google.appengine.api.datastore.Query.SortDirection"%>

<%
//get the posted data here
String get=(String)request.getParameter("get");

%>
<table class="table table-condensed">
    <thead>
        <tr>
            <th>Name</th>
            <th>Phone Number</th>
            <th>Remove</th>
        </tr>
    </thead>
    <tbody>
        <%

        //get the user
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        String username = user.getNickname();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // The Query interface assembles a query
        //filtered by user and moduleName
        Query q = new Query("PhoneNumbers");
        //filter for events on that day
        q.addFilter("user", FilterOperator.EQUAL, username);

        PreparedQuery pq = datastore.prepare(q);


        //loop through and output the proper lines
        for (Entity result : pq.asIterable()) {

        String name = (String) result.getProperty("name");
        String num = (String) result.getProperty("phoneNumber");

        //output the html
        out.println("<tr>");
        out.println("<td>"+name+"</td>");
        out.println("<td>"+num+"</td>");        
        out.println("<td><button type=\"button\" class=\"btn btn-danger\" onclick=\"deleteentry()\"><i class=\"icon-trash icon-white\"></i></button></td>");
        out.println("</tr>");
        
            
        }

                %>
    </tbody>
</table>