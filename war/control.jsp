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
<head>
	<link rel="stylesheet" href="front-end/css/bootstrap.min.css">
	<link rel="stylesheet" href="front-end/css/bootstrap-responsive.min.css">
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
	<style>
	      body {
	        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
	      }
	</style>
	</head>
  <body>
	<div class="navbar navbar-fixed-top">
	      <div class="navbar-inner">
	        <div class="container-fluid">
	          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </a>
	          <a class="brand" href="#">SentientHome</a>
	          <div class="nav-collapse">
	            <ul class="nav">
	              <li class="active"><a href="#">Home</a></li>
	              <li><a href="/control.jsp">Control</a></li>
	              <li><a href="#contact">Contact</a></li>
	            </ul>
	            <p class="navbar-text pull-right" id="signlinks" >
					<a href="#">
						<span id="account"></span>
					</a>
					 	<span id="tick"></span>
					<a id="signwhat" href=""></a>
				</p>
	          </div><!--/.nav-collapse -->
	        </div>
	      </div>
	    </div>

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
		
	
	</script>
	
	<div class="container-fluid">
	  	<div class="row-fluid">
	 		<div class="span3">
	          <div class="well sidebar-nav">
	            <ul class="nav nav-list">
					<%
						String username = user.getNickname();
					    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
					    Key moduleKey = KeyFactory.createKey("user", username);
					    //Run an ancestor query to ensure we see the most up-to-date
					    Query query = new Query("moduleInfo", moduleKey).addSort("moduleType", Query.SortDirection.DESCENDING);
					    List<Entity> modules = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(15));
					
					
					//*********Populate list in column***********
					//********  *with current devices************
					//starting type	
					String the_type = "";
					for (Entity module : modules) {
						String cur_type = (String)module.getProperty("moduleType");	//current type in loop
						String cur_name = (String)module.getProperty("moduleName"); //current name in loop
						if (cur_type==the_type){
							out.println("<li><a href=\"#\">"+cur_name+"</a></li>");	//create list link for member of type
						}else{
							the_type = cur_type;
							out.println("<li class=\"nav-header\">"+cur_type+"</li>");	//create header if new type reached
						}
					}
					//********************************************
					//********************************************
					%>
	              
	            </ul>
	          </div><!--/.well -->
	        </div><!--/span-->
	        <div class="span9">
	          <div class="hero-unit">
	            <h2>Door Control</h2>
	            <p>Set the duration of time you want the door to open for and press the "Open" button.</p>
	            <p><a class="btn btn-primary btn-large btn-success">Open</a></p>
				
	          </div>
	          
	        </div><!--/span-->
	      </div><!--/row-->
	
	</div><!--/.fluid-container-->
	
	<%	
    } else {
%>
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
	<div class="row show-grid">
	    <div class="span12"><hr></div>
	 </div>

		<footer>
	        <p>&copy; Mike & Mike Productions 2012</p>
	     </footer>
	  </body>
	</html>
