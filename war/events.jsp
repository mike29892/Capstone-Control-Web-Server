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
<%@ page import="java.util.Date" %>


<%
//get the posted data here
String mod_type=(String)request.getParameter("moduleType");
String mod_name=(String)request.getParameter("moduleName");
int count = Integer.parseInt((String)request.getParameter("count"));
Long offset = Long.parseLong(request.getParameter("offset"));
%>


<table class="table table-condensed">
<thead>
    <tr>
      <th>Date</th>
      <!--<th>Action</th>-->
      <th>Module Name</th>
      <th>Value</th>
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
      Query q = new Query("moduleEvent");
      //filter for events on that day     
      q.addFilter("user", FilterOperator.EQUAL, username);
      q.addFilter("moduleName", FilterOperator.EQUAL, mod_name);  
      q.addSort("date", SortDirection.DESCENDING);   
      
      PreparedQuery pq = datastore.prepare(q);
    
      int counter = 0;
     
      //loop through and output the proper lines
      for (Entity result : pq.asIterable()) {
          
           Date thedate = (Date)result.getProperty("date");
           Long time = thedate.getTime();
           thedate.setTime(time-offset);
                            
           String date = thedate.toString();
           date = date.replace("UTC", "");
           String modname = (String) result.getProperty("moduleName");
           String action = (String) result.getProperty("action");
           String val = result.getProperty("value").toString();
           
           //output the html
           out.println("<tr>");        
           out.println("<td>"+date+"</td>");
           //out.println("<td>"+action+"</td>");    
           out.println("<td>"+modname+"</td>");  
           out.println("<td>"+val+"</td>");    
           out.println("</tr>");     
           counter ++;
           if(counter>count){break;}                 
       }
 
     %>

    
  </tbody>
</table>