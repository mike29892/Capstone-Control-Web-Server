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

<html lang="en">
<head>
    <title>Add Module</title>
	<link rel="stylesheet" href="front-end/css/bootstrap.min.css">
	
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }
    </style>
    <link rel="stylesheet" href="front-end/css/bootstrap-responsive.min.css">
	</head>
  <body>
	<%@ include file="nav_bar.jsp" %>

<%
	String moduleName = request.getParameter("moduleName");
    if (moduleName == null) {
        moduleName = "default";
    }
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<div class="container">
	<script>
		$(document).ready(function() {
			$("#signlinks").prepend("Logged in as ");
			$("#account").append("<%= user.getNickname() %>");
			$("#signwhat").append("Sign Out");
			$("#tick").append(" | ");
			$("#signwhat").attr('href',"<%= userService.createLogoutURL(request.getRequestURI()) %>");
		 });
		
		function deleteentry(key){
			$.ajax({
			  type: 'POST',
			  url: '/RemoveModule',
			  data: "thekey="+key,
			  success: function(){
				location.reload();
				},
			  dataType: "html"
			});
		}
		
	
	</script>


	<div class="row-fluid">
	    <div class="span6">
	    	<p>Hello, <%= user.getNickname() %>! You can add/remove modules to your account.</p>
			<FORM METHOD="POST" ACTION="/addModule" class="well">


				<div class="control-group">
					<label class="control-label" for="moduleName">Module Name</label>
					<div class="controls">
						<INPUT TYPE="TEXT" class="span6" NAME="moduleName" id="moduleName" SIZE=20>
					</div>	
				</div>

				<div class="control-group">
					<label class="control-label" for="moduleType">Module Type</label>	
					<div class="controls">
							<select id="moduleType" name="moduleType">  
			    				<option>Dimmer</option>
			    				<option>Door Buzzer</option> 
			    				<option>Power Monitor</option>
			    				<option>Water Alarm</option>
							</select> 
					</div>	
				</div>			

				<div class="control-group">
					<label class="control-label" for="moduleMacAddr">Module MAC Addr</label>
					<div class="controls">
						<INPUT TYPE="TEXT" class="span6" NAME="moduleMacAddr" SIZE=20>
					</div>	
				</div>		

				<div class="form-actions">		
					<input class="btn btn-primary" type="submit" value="Add Module" />
				</div>

			</FORM>
	
	
	    </div>
	    <div class="span6">
			<%
				String username = user.getNickname();
			    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			    Key moduleKey = KeyFactory.createKey("user", username);
			    // Run an ancestor query to ensure we see the most up-to-date
			    Query query = new Query("moduleInfo", moduleKey).addSort("date", Query.SortDirection.DESCENDING);
			    List<Entity> modules = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(15));

				if (modules.isEmpty()){
				%>
				<p>There are no modules added to your account.</p>
				<%
				} else {
				%>
				<p>There modules on your account are:</p>
				<table class="table table-striped table-bordered table-condensed">
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
					<td><%= module.getProperty("moduleMacAddr") %></td>
					<% 
						Key modkey = module.getKey();
						String strKey = KeyFactory.keyToString(modkey);
					%>
					<td><CENTER>
				<button type="button" class="btn btn-danger" onclick="deleteentry('<%=strKey%>')"><i class="icon-trash icon-white"></i></button>
				</CENTER></td>
				</tr>
				<%
			    }
				}
				%>
				</table>
				<%	
			    } else {
			%>
		</div>
	  </div>

	<script>
		$(document).ready(function() {
			//$("#account").append("");
			$("#signwhat").append("Sign In");
			$("#signwhat").attr('href',"<%= userService.createLoginURL(request.getRequestURI()) %>");
		 });


	</script>
<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to add/remove modules to your account.</p>
<%
    }
%>
</div>

<div class="row show-grid">
    <div class="span12"><hr></div>
 </div>
	
	<footer>
        <p>&copy; Mike & Mike Productions 2012</p>
     </footer>
  </body>
</html>
