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

Long offset = Long.parseLong(request.getParameter("offset"));

%>
<br /><div class="span12 row-fluid">
<div class="span1"></div>
<div class="span8">
<%
                //get the user
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
                int count = 0;
                //loop through and output the proper lines
                for (Entity result : pq.asIterable()) {
                            
                     Date thedate = (Date)result.getProperty("date");
                     Long time = thedate.getTime();
                     thedate.setTime(time-offset);
                     
                     String dateString = thedate.toString();
                     String modname = (String) result.getProperty("moduleName");
                     String warn = (String) result.getProperty("Warning");
                       
                     //output the html
                     out.println("<div class=\"alert alert-block alert-error\">");        
                     out.println("<a class=\"close\" data-dismiss=\"alert\">×</a>");                   ;    
                     out.println("<h4 class=\"alert-heading\">Warning!</h4>");  
                     out.println("<br/>" + modname + ": " + warn + " time: " + dateString);    
                     out.println("</div>");                       
                     count++;  
                }
                
                if (count == 0){
                     out.println("<div class=\"alert alert-block alert-info\">");        
                     out.println("<a class=\"close\" data-dismiss=\"alert\">×</a>");                   ;    
                     out.println("<h4 class=\"alert-heading\">No Alerts</h4>");  
                     out.println("<br/> There are currently no active alerts.");    
                     out.println("</div>"); 
                }
                %>
                
    
 <!--               
<table class="table table-condensed">
<thead>
    <tr>
      <th>Date</th>
     
      <th>Module Name</th>
      <th>Alert</th>
    </tr>
  </thead>
  <tbody>
     -->
     <% 
     /*
                //QUERY FOR All Alerts --- last 50 
                q = new Query("Module Event");
                //filter for events on that day     
                q.addFilter("user", FilterOperator.EQUAL, username);
                q.addFilter("ACK", FilterOperator.EQUAL, true);  
                q.addSort("date", SortDirection.DESCENDING);   
      
                pq = datastore.prepare(q);
    
                int counter = 0;
     
                //loop through and output the proper lines
                for (Entity result : pq.asIterable()) {
                      
                     Date thedate = (Date)result.getProperty("date");
                     Long time = thedate.getTime();
                     thedate.setTime(time-offset);
                           
                     String dateString = thedate.toString();
                     String modname = (String) result.getProperty("moduleName");
                     String warn = (String) result.getProperty("warning");
           
                     //output the html
                     out.println("<tr>");        
                     out.println("<td>"+modname+"</td>");
                     //out.println("<td>"+warn+"</td>");    
                     out.println("<td>"+dateString+"</td>");                      
                     out.println("</tr>");     
                     counter ++;
                                    
                }
 
                */ 
                %>
    
 <!-- </tbody>
</table>
-->
</div>
<div class="span1"></div>
</div>

<script>
    
    
</script>

