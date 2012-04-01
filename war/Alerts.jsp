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
        <link rel="stylesheet" href="front-end/css/jquery-ui-1.8.18.custom.css">
        <script type="text/javascript" src="front-end/jquery-ui-1.8.17.custom/js/jquery-ui-1.8.17.custom.min.js"></script>
        <script src="front-end/js/jquery.ui.touch-punch.min.js"></script>
        <script src="front-end/js/bootstrap-tab.js"></script>
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
        <script>
            $(document).ready(function() {
                getNumbers();
                $("#signlinks").prepend("Logged in as ");
                    $("#account").append("<%= user.getNickname() %>");
                    $("#signwhat").append("Sign Out");
                    $("#tick").append(" | ");
                    $("#signwhat").attr('href', "<%= userService.createLogoutURL(request.getRequestURI()) %>");
            });

            function getNumbers() {
                $.ajax({
                type: 'POST',
                url: "/getnumbers.jsp",
                data: {"get" : "true"},
                success: function(resp) {
                    $("#numbers_in").html(resp);
                }
              });
            }

            function addNumbers() {
                var number = $("#phonenumber").val();
                var name = $("#name").val();
                $.ajax({
                type: 'POST',
                url: "/AddPhoneNumber",
                data: { "number" : number, "name" : name},
                success: function(resp) {
                    alert("Number has been added");
                    getNumbers();
                }
             });
            }

           
            
        </script>
        <ul id="tab" class="nav nav-tabs">
            <li class="active">
                <a href="#Alerts" data-toggle="tab">Alerts</a>
            </li>
            <li>
                <a href="#Settings" data-toggle="tab">Settings</a>
            </li>
        </ul>
        <div id="myTabContent" class="tab-content row-fluid">
            <div class="tab-pane fade in active" id="Alerts"></div>
            <div class="tab-pane fade in" id="Settings">
                <div class="well span4">
                    <fieldset>
                        <legend>
                            Add Phone Number
                        </legend>
                        <div class="control-group">
                            <label class="control-label" for="phonenumber">Phone Number</label>
                            <div class="controls">
                                <input type="text" class="span3" id="phonenumber">
                                <p class="help-block">
                                    Enter a mobile phone number to receive alerts
                                </p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="name">Name</label>
                            <div class="controls">
                                <input type="text" class="span3" id="name">
                                <p class="help-block">
                                    Enter a name to associate the number with
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <button id="addnum" onclick="addNumbers()" class="btn btn-info"><i class="icon-plus icon-white"></i>Add Phone Number</button>
                </div>
                <div class="span4" id="numbers_in"></div>
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
        <p>
            Hello! <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
            to add/remove modules to your account.
        </p>
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