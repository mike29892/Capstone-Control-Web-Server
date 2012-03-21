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

<link rel="stylesheet" href="front-end/css/jquery-ui-1.8.18.custom.css">
<script type="text/javascript" src="front-end/jquery-ui-1.8.17.custom/js/jquery-ui-1.8.17.custom.min.js"></script>
<script src="front-end/js/jquery.ui.touch-punch.min.js"></script>
<script src="front-end/js/bootstrap-tab.js"></script>
<script src="front-end/js/jquery-ui-timepicker-addon.js"></script>
    <style>
	.ui-slider .ui-slider-handle {
        position: absolute;
        z-index: 2;
        width: 1.8em;
        height: 1.8em;
        cursor: default;
    }
    .ui-slider-horizontal .ui-slider-handle {
        top: -.6em;
        margin-left: -.6em;
    }
	</style>
	<script>
		$(document).ready(function() {
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
			$('#Light_Dim').draggable();
		
		});
		
		
		</script>


<ul id="tab" class="nav nav-tabs">
    <li class="active"><a href="#Control" data-toggle="tab">Control</a></li>
    <li><a href="#Schedule" data-toggle="tab">Schedule</a></li>
</ul>
<div id="myTabContent" class="tab-content">
    
    <div class="tab-pane fade in active" id="Control">
        <h2><%out.println(mod_name); %></h2>
        <p>Drag the slider to adjust brightness of the lights.</p>
        <p id="amount" style="border:0;width:50px;font-weight:bold;"></p>
        <div id="Light_Dim"></div>       
    </div>
    
    <div class="tab-pane fade" id="Schedule">
             TEST    
    </div>
            
</div>


<script>

</script>
<%
///////////////////////////////////////////////
/////BUZZER***********************************
///////////////////////////////////////////////
}else if(mod_type.equals("Door Buzzer")){%>
	
	<link rel="stylesheet" href="front-end/css/jquery-ui-1.8.18.custom.css">
	<script type="text/javascript" src="front-end/jquery-ui-1.8.17.custom/js/jquery-ui-1.8.17.custom.min.js"></script>
	<script src="front-end/js/jquery.ui.touch-punch.min.js"></script>
	<script src="front-end/js/bootstrap-tab.js"></script>
	<script src="front-end/js/jquery-ui-timepicker-addon.js"></script>
	<style>
	.ui-slider .ui-slider-handle {
        position: absolute;
        z-index: 2;
        width: 1.8em;
        height: 1.8em;
        cursor: default;
    }
    .ui-slider-horizontal .ui-slider-handle {
        top: -.6em;
        margin-left: -.6em;
    }
    
    /* css for timepicker */
    .ui-timepicker-div .ui-widget-header { margin-bottom: 8px; top: 60px; }
    .ui-timepicker-div dl { text-align: left; }
    .ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
    .ui-timepicker-div dl dd { margin: 0 10px 20px 65px; }
    .ui-timepicker-div td { font-size: 90%; }
    .ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
    
	</style>
		<script>
		$(document).ready(function() {
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
			
			
			$('#Duration').draggable();
			$("#tabs").tab();
			
			$('#sinTime').datetimepicker({
			    
            });
            $('#recTime').timepicker({
               
            });
		
		});
		
		///send control
       function control(){
           var type = <%out.println("'"+mod_type+"'"+";"); %>
            var name = <% out.println("'"+mod_name+"'"+";"); %>
            var action = "OPEN";
            $.ajax({
              type: 'POST',
              url: "/ControlModuleServlet",
              data: { "moduleName": name, "moduleType": type, "message": message },
              success: function(resp){
                //$("#control_panel").html(resp);
            }
            });
        }
            
        ///send control
        function schedule(single){
            var type = <%out.println("'"+mod_type+"'"+";"); %>
            var name = <% out.println("'"+mod_name+"'"+";"); %>
            if(single){
                var value = $("#sinVal").val();
                var time = $("#sinTime").val();
                var eventType = 'single';
            }else{
                var value = $("#recVal").val();
                var time = "01/01/1970 "+$("#recTime").val();
                var eventType = 'recurr';
            }
            var thedays = new Array();
            $('#Sun').is(':checked') ? thedays[0]=1 : thedays[0]=0;
            $('#Mon').is(':checked') ? thedays[1]=1 : thedays[1]=0;
            $('#Tues').is(':checked') ? thedays[2]=1 : thedays[2]=0;
            $('#Wed').is(':checked') ? thedays[3]=1 : thedays[3]=0;
            $('#Thurs').is(':checked') ? thedays[4]=1 : thedays[4]=0;
            $('#Fri').is(':checked') ? thedays[5]=1 : thedays[5]=0;
            $('#Sun').is(':checked') ? thedays[6]=1 : thedays[6]=0;
            var thedaysfin = thedays.join('');
            
            var action = "OPEN";
            $.ajax({
              type: 'POST',
              url: "/ScheduleEvent",
              data: { "moduleName": name, "moduleType": type, "value": value,
              "action": action, "schedDate": time, "active": 1, "days": thedaysfin, "eventType":eventType },
              success: function(resp){
                //$("#control_panel").html(resp);
                alert("Event Created");
            }
            });
        }
        
         $('#recurr').click(function(){ 
             schedule(0);
         });
         $('#single').click(function(){ 
             schedule(1);
         });  
		</script>
		
<ul id="tab" class="nav nav-tabs" data-tabs="tabs">
    <li class="active"><a href="#Control" data-toggle="tab">Control</a></li>
    <li><a href="#Schedule" data-toggle="tab">Schedule</a></li>
</ul>
<div id="myTabContent" class="tab-content">
    
    
    <div class="tab-pane fade in active" id="Control">
	   <h2><%out.println(mod_name); %></h2>
        <p>Set the duration of time you want the door to open for and press the "Open" button.</p>
	   <p id="time" style="border:0;width:50px;font-weight:bold;"></p>
	   <div id="Duration"></div><br/>
	   <a class="btn btn-primary btn-large btn-success">Open</a><br/>
    </div>
    
    
    <div class="tab-pane fade" id="Schedule">
        
        <form class="form-horizontal">
            <fieldset>
                <legend>Recurring Event</legend>
                <div class="control-group">
                    <label class="control-label" for="recTime">Time</label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="recTime">
                        <p class="help-block">Click the above box to open time selection box</p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inlineCheckboxes">Days</label>
                    <div class="controls">
                        <label class="checkbox inline">
                            <input type="checkbox" id="Sun" value="1">Su.
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="Mon" value="1.">Mo.
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="Tues" value="1">Tu.
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="Wed" value="1">We.
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="Thurs" value="1">Th.
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="Fri" value="1">Fr.
                        </label>
                        <label class="checkbox inline">
                            <input type="checkbox" id="Sat" value="1">Sa.
                        </label>                        
                       </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="recVal">Value</label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="recVal">
                        <p class="help-block">Enter an appropriate value for module</p>
                    </div>
                </div>
                
                               
                
            </fieldset>
       </form>
        <div class="form-actions">
                    <button id="recurr" class="btn btn-primary">Create Event</button>                    
                </div>
        
        <form class="form-horizontal">
            <fieldset>
                <legend>One Time Event</legend>
                <div class="control-group">
                    <label class="control-label" for="sinTime">Time</label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="sinTime">
                        <p class="help-block">Click the above box to open time selection box</p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="sinVal">Value</label>
                    <div class="controls">
                        <input type="text" class="input-xlarge" id="sinVal">
                        <p class="help-block">Enter an appropriate value for module</p>
                    </div>
                </div>
                
            </fieldset>
       </form>
        <div class="form-actions">
                    <button id="single" class="btn btn-primary">Create Event</button>                    
                </div>
    </div>
            
</div>   

	
<%}else{
	out.println("<h2>ERROR "+mod_type+" "+mod_name+"</h2>");
}%>