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
    <title>Control Modules</title>
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
	
	<%@ include file="nav_bar.jsp"%> 

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
		
			///load control
			function getcontrol(type,name){
				$.ajax({
				  type: 'POST',
				  url: "/mod_controls.jsp",
				  data: { "moduleName": name, "moduleType": type },
				  success: function(resp){
					$("#control_panel").html(resp);
				}
				});
			}
			
	</script>
	
	<div class="container-fluid" style="padding-top: 60px;">
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
					String start_name = "";
					String start_type = "";
					int count = 0;
					for (Entity module : modules) {
						
						String cur_type = (String)module.getProperty("moduleType");	//current type in loop
						String cur_name = (String)module.getProperty("moduleName"); //current name in loop
						if(count < 1 ){
							start_type = cur_type;	//this is here to save the first module in the list
							start_name = cur_name;	//then load its controls
						}
						count = count + 1;
						
						if (cur_type.equals(the_type)){
							out.println("<li><a href=\"javascript:getcontrol(\'"+cur_type+"\',\'"+cur_name+"\')\">"+cur_name+"</a></li>");	//create list link for member of type
						}else{
							the_type = cur_type;
							out.println("<li class=\"nav-header\">"+cur_type+"</li>");	//create header if new type reached
							out.println("<li><a href=\"javascript:getcontrol(\'"+cur_type+"\',\'"+cur_name+"\')\">"+cur_name+"</a></li>");	//create list link for member of type
						}
					}
					
					//********************************************
					//********************************************
					%>
	              
	            </ul>
	          </div><!--/.well -->
	        </div><!--/span-->
			<script>
				<%out.println("getcontrol('"+start_type+"','"+start_name+"');");%>
			</script>
	        <div class="span9">
	          <div class="well" id="control_panel">
	            
				
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
