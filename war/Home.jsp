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
        <title>Home</title>
        <link rel="stylesheet" href="front-end/css/bootstrap.min.css">
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script type="text/javascript" src="http://www.google.com/jsapi"></script>
        <script type="text/javascript">
            google.load('visualization', '1', {packages: ['corechart']});
        </script>
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
        
        <link rel="stylesheet" href="front-end/css/jquery-ui-1.8.18.custom.css">
    <script type="text/javascript" src="front-end/jquery-ui-1.8.17.custom/js/jquery-ui-1.8.17.custom.min.js"></script>
    <script src="front-end/js/jquery.ui.touch-punch.min.js"></script>
    <script src="front-end/js/bootstrap-tab.js"></script>
    <script src="front-end/js/jquery-ui-timepicker-addon.js"></script>
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
        <div class="container row-fluid">
            <script>
                $(document).ready(function() {
                    $("#signlinks").prepend("Logged in as ");
                    $("#account").append("<%= user.getNickname() %>");
                    $("#signwhat").append("Sign Out");
                    $("#tick").append(" | ");
                    $("#signwhat").attr('href', "<%= userService.createLogoutURL(request.getRequestURI()) %>");
                });
                ///load control
                function getcontrol(type, name) {
                    $.ajax({
                        type : 'POST',
                        url : "/mod_controls.jsp",
                        data : {
                            "moduleName" : name,
                            "moduleType" : type
                        },
                        success : function(resp) {
                            $("#control_panel").html(resp);
                        }
                    });
                }
            </script>
<div class="container">
      <!-- Main hero unit for a primary marketing message or call to action -->
      

      <!-- Example row of columns -->
      <div class="row">
        <div class="span10">
          <h2>Cloud Driven Home Automation System</h2>
          <br/>
          <h3>Michael Bartucca, Michael Caulley, Zaihan Gui, Yuanyuan Tian</h3>
          <br/>
          <h3>Advisor: Charles DiMarzio</h3>
          <br/>
          <p>
The Cloud Driven Home Automation System is a complete platform built in the cloud, which interfaces with common household items. Various hardware modules can be installed in the home in order to control lights and to control doors, as well as monitor power usage and sense flooded basements. The system offers a web interface and an Android application that gives users full control over all of their modules, from anywhere in the world. The user can monitor, control, schedule and view module events remotely, from either a desktop or mobile phone, with any internet connection.
<br/><br/>
The Cloud Driven Home Automation System is built on Google's App Engine and Amazon's EC2 servers. It has ability to communicate directly with every module, eliminating any local server in the users' home. This modularity drives down the entry cost of the system while offering higher performance. All system modules are built on Arduino microcontroller hardware with the MQTT messaging protocol implemented for module to server communications. 
<br/><br/>
The design and implementation of the Cloud Driven Home Automation System creates a platform that allows for easy addition of newly designed modules. 
</p>
          
        </div>        
      </div>
  </div>
  
<%
            } else {
            %>
            <script>
                $(document).ready(function() {
                    //$("#account").append("");
                    $("#signwhat").append("Sign In");
                    $("#signwhat").attr('href', "<%= userService.createLoginURL(request.getRequestURI()) %>");
                });

            </script>
            <div class="hero-unit">
                    <h1>Welcome To SentientHome</h1>
                    <p>Please sign in to begin using our Cloud Driven Home AUtomation System</p>
                    <p><a class="btn btn-primary btn-large" href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
                    </p>
                </div>
            <%
            }
            %>
            <div class="row show-grid">
                <div class="span12">
                    <hr>
                </div>
            </div>
            <footer>
                <p>
                    &copy; Mike & Mike Productions 2012
                </p>
            </footer>
    </body>
</html>
