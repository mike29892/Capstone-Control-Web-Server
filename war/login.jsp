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

<html>
  <body>

<%
	String moduleName = request.getParameter("moduleName");
    if (moduleName == null) {
        moduleName = "default";
    }
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>Hello, <%= user.getNickname() %>! You can add/remove modules to your account. (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<FORM METHOD=POST ACTION="/addModule">
Module Name    <INPUT TYPE=TEXT NAME=moduleName SIZE=20><BR>
Module Type		<select id="moduleType" name="moduleType">  
    				<option>Dimmer</option>
    				<option>Door Buzzer</option> 
				</select> 
				<br /> 
Module MAC Addr<INPUT TYPE=TEXT NAME=macAddr SIZE=20><BR>
<div><input type="submit" value="Add Module" /></div>
</FORM>
<%
	String username = user.getNickname();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key moduleKey = KeyFactory.createKey("user", username);
    // Run an ancestor query to ensure we see the most up-to-date
    Query query = new Query("Module", moduleKey).addSort("date", Query.SortDirection.DESCENDING);
    List<Entity> modules = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(15));

	if (modules.isEmpty()){
	%>
	<p>There are no modules added to your account.</p>
	<%
	} else {
	%>
	<p>There modules on your account are:</p>
	<table border="1">
	<tr>
	<th>Module Name</th>
	<th>Module Type</th>
	<th>MAC Address</th>
	<th>Delete Module</th>
	</tr>
	<%
	for (Entity module : modules) {
	%>
	<tr>
	<td><%= module.getProperty("moduleName") %></td>
	<td><%= module.getProperty("moduleType") %></td>
	<td><%= module.getProperty("macAddr") %></td>
	<td><CENTER>
	<button type="button" 
	onclick="alert('Module removed!')">X
	</button></CENTER></td>
	</tr>
	<%
    }
	}
	%>
	</table>
	<%	
    } else {
%>
<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to add/remove modules to your account.</p>
<%
    }
%>
  </body>
</html>
  </body>
</html>