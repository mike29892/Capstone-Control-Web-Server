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
