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

<%
//get the posted data here
String mod_type=(String)request.getParameter("moduleType");
String mod_name=(String)request.getParameter("moduleName");
//mod_type = "Dimmer";
///////////////////////////////////////////////
/////Dimmer***********************************
///////////////////////////////////////////////
if(mod_type.equals("Dimmer")){
%>
<link rel="stylesheet" href="front-end/jquery-ui-1.8.17.custom/css/smoothness/jquery-ui-1.8.17.custom.css">
<script type="text/javascript" src="front-end/jquery-ui-1.8.17.custom/js/jquery-ui-1.8.17.custom.min.js"></script>
    <style>
	
	</style>
	<script>
		$(function() {
			$( "#Light_Dim" ).slider({
				range: "min",
				value: 50,
				min: 0,
				max: 100,
				slide: function( event, ui ) {
					$( "#amount" ).html(  ui.value );
					
				},
				change:  function( event, ui ) {
					var type = <%out.println("'"+mod_type+"'"+";"); %>
					var name = <% out.println("'"+mod_name+"'"+";"); %>
					var message = ui.value;
					$.ajax({
					  type: 'POST',
					  url: "/ControlModuleServlet",
					  data: { "moduleName": name, "moduleType": type, "message": message, "action": "Dim"},
					  success: function(resp){
						//$("#control_panel").html(resp);
					}
					});
				}
			});
			$( "#amount" ).html( $( "#Light_Dim" ).slider( "value" ) );
		});
		
		</script>
<div class="demo">

<h2><%out.println(mod_name); %></h2>
<p>Drag the slider to adjust brightness of the lights.</p>
	<p id="amount" style="border:0;width:50px;font-weight:bold;"></p>
	
<div id="Light_Dim"></div>
</div>
<script>

</script>
<%
///////////////////////////////////////////////
/////BUZZER***********************************
///////////////////////////////////////////////
}else if(mod_type.equals("Door Buzzer")){%>
	<link rel="stylesheet" href="front-end/jquery-ui-1.8.17.custom/css/smoothness/jquery-ui-1.8.17.custom.css">
	<script type="text/javascript" src="front-end/jquery-ui-1.8.17.custom/js/jquery-ui-1.8.17.custom.min.js"></script>
	<style>
	</style>
		<script>
		$(function() {
			$( "#Duration" ).slider({
				range: "min",
				value: 15,
				min: 1,
				max: 30,
				slide: function( event, ui ) {
					$( "#time" ).html( ui.value );
				}
			});
			$( "#time" ).html( $( "#Duration" ).slider( "value" ) );
			
			///send control
			function control(){
				var type = <%out.println("'"+mod_type+"'"+";"); %>
				var name = <% out.println("'"+mod_name+"'"+";"); %>
				var message = "OPEN";
				$.ajax({
				  type: 'POST',
				  url: "/ControlModuleServlet",
				  data: { "moduleName": name, "moduleType": type, "message": message },
				  success: function(resp){
					//$("#control_panel").html(resp);
				}
				});
			}
		});
		</script>
	<h2><%out.println(mod_name); %></h2>
    <p>Set the duration of time you want the door to open for and press the "Open" button.</p>
	<p id="time" style="border:0;width:50px;font-weight:bold;"></p>
	<div id="Duration"></div><br/>
	
	<a class="btn btn-primary btn-large btn-success">Open</a><br/>
    

	
<%}else{
	out.println("<h2>ERROR "+mod_type+" "+mod_name+"</h2>");
}%>