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
    
    -webkit-box-sizing: border-box; /* Safari/Chrome, other WebKit */ 
    -moz-box-sizing: border-box;    /* Firefox, other Gecko */ 
     box-sizing: border-box;         /* Opera/IE 8+ */ 

    #rectime #sintime #sinval {
        height:28px;
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
			$('#sinTime').datetimepicker({
                ampm: true
            });
            $('#recTime').timepicker({
               ampm: true
            });
		
		});
		
		 ///send control
        function schedule(single){
            var type = <%out.println("'"+mod_type+"'"+";"); %>
            var name = <% out.println("'"+mod_name+"'"+";"); %>
            var Sun = 0;
            var Mon = 0;
            var Tue = 0;
            var Wed = 0;
            var Thu = 0;
            var Fri = 0;
            var Sat = 0;
            var timeoff = new Date().getTimezoneOffset()/60;

            if(single){
                var value = $("#sinVal").val();
                var time = Date.parse($("#sinTime").val());
                var eventType = 'single';
                var dayw = getdayofweekBIN($("#sinTime").val());
                var recur = false;
                switch (dayw)
                {
                case 0:
                  Sun=1;              
                  break;
                case 1:
                  Mon=1;              
                  break;
                case 2:  
                  Tue=1;          
                  break;
                case 3: 
                  Wed=1;           
                  break;
                case 4:  
                  Thu=1;          
                  break;
                case 5:  
                  Fri=1;          
                  break;
                case 6: 
                  Sat=1;           
                  break;
               default:
               }
            }else{
                var value = $("#recVal").val();
                var time = Date.parse("01/01/1970 "+$("#recTime").val())-3600000;
                var eventType = 'recurr';
                var thedays = new Array();
                var recur = true;
                $('#Sun').is(':checked') ? Sun=true : Sun=false;
                $('#Mon').is(':checked') ? Mon=true : Mon=false;
                $('#Tues').is(':checked') ? Tue=true : Tue=false;
                $('#Wed').is(':checked') ? Wed=true : Wed=false;
                $('#Thurs').is(':checked') ? Thu=true : Thu=false;
                $('#Fri').is(':checked') ? Fri=true : Fri=false;
                $('#Sun').is(':checked') ? Sat=true : Sat=false;                   
            }
                        
            var action = "DIM";
            $.ajax({
              type: 'POST',
              url: "/ScheduleEvent",
              data: { "moduleName": name, "moduleType": type, "value": value,
              "action": action, "schedDate": time, "active": true, "eventType":eventType,
              "Sun": Sun, "Mon": Mon, "Tue":Tue, "Wed":Wed, "Thu":Thu, "Fri":Fri, "Sat":Sat,
               "offset": timeoff, "recur":recur  },
              success: function(resp){
                //$("#control_panel").html(resp);
                alert("Event Created");
            }
            });
        }
        
        //get the week day binary string
        function getdayofweekBIN(thedate){
            //03/01/2012 08:45
            datesp = thedate.split("/");
            year = datesp[2].split(" ");            
            newDate = new Date(year[0], datesp[0]-1, datesp[1]);
            var daystring = '0000000';
            var newDate2=newDate.getDay();            
            return newDate2;
        }
        
        function getEvents(){
            var count = $("#count").val();
            $.ajax({
                  type: 'POST',
                  url: "/events.jsp",
                  data: { "moduleName": <%out.println("'"+mod_name+"'"); %>,
                          "moduleType": <%out.println("'"+mod_type+"'"); %>,
                          "count": count },
                  success: function(resp){
                    $("#eventsin").html(resp);
                }
                });
         }
        
        
         $('#recurr').click(function(){ 
             schedule(0);
         });
         $('#single').click(function(){ 
             schedule(1);
         }); 
         $('#getEvents').click(function(){ 
             getEvents();
         }); 
                      
		
		</script>

<h2><%out.println(mod_name); %></h2>
<ul id="tab" class="nav nav-tabs">
    <li class="active"><a href="#Control" data-toggle="tab">Control</a></li>
    <li><a href="#Schedule" data-toggle="tab">Schedule</a></li>
    <li><a href="#Events" data-toggle="tab">Events</a></li>
</ul>
<div id="myTabContent" class="tab-content">
    
    <div class="tab-pane fade in active well" id="Control">
                
        <p>Drag the slider to adjust brightness of the lights.</p>
        <p id="amount" style="border:0;width:50px;font-weight:bold;"></p>
        <div id="Light_Dim"></div>  
            
    </div>
    
    <div class="tab-pane fade" id="Schedule">
        <div class="well span10" style="margin-left:0px;">
            <fieldset>
                <legend>Recurring Event</legend>
                <div class="control-group">
                    <label class="control-label" for="recTime">Time</label>
                    <div class="controls">
                        <input type="text" class="span5" id="recTime">
                        <p class="help-block">Click the above box to open time selection box</p>
                    </div>
                </div>
                <div class="control-group">                    
                    <label class="control-label" for="inlineCheckboxes">Days</label>                   
                            <label class="checkbox ">
                                <input type="checkbox" id="Sun" value="1">Sunday
                            </label>
                       
                            <label class="checkbox ">
                                 <input type="checkbox" id="Mon" value="1.">Monday
                            </label>
                                             
                            <label class="checkbox ">
                                <input type="checkbox" id="Tues" value="1">Tuesday
                            </label>
                       
                            <label class="checkbox ">
                                <input type="checkbox" id="Wed" value="1">Wednesday
                            </label>
                       
                            <label class="checkbox ">
                                <input type="checkbox" id="Thurs" value="1">Thursday
                            </label>
                    
                            <label class="checkbox ">
                                <input type="checkbox" id="Fri" value="1">Friday
                            </label>
                   
                            <label class="checkbox ">
                                <input type="checkbox" id="Sat" value="1">Saturday
                            </label>                                           
                    
                </div>
                <div class="control-group">
                    <label class="control-label" for="recVal">Value</label>
                    <div class="controls">
                        <input type="text" class="span5" id="recVal">
                        <p class="help-block">Enter A Brightness Value</p>
                    </div>
                </div>
                
            </fieldset>
            <button id="recurr" class="btn btn-primary">Create Recurring Event</button> 
       </div>
        
        
        <div class="well  span10" style="margin-left:0px;">
            <fieldset>
                <legend>One Time Event</legend>
                <div class="control-group">
                    <label class="control-label" for="sinTime">Time</label>
                    <div class="controls">
                        <input type="text" class="span5" id="sinTime">
                        <p class="help-block">Click the above box to open time selection box</p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="sinVal">Value</label>
                    <div class="controls">
                        <input type="text" class="span5" id="sinVal">
                        <p class="help-block">Enter A Brightness Value</p>
                    </div>
                </div>
                
            </fieldset>
            <button id="single" class="btn btn-info">Create Single Event</button>       
       </div>
        
                                 

    </div>
    
    <div class="tab-pane fade well" id="Events">
        
    <div class="form-inline span8" style="margin-left:0px;">
        <label class="control-label" for="count"># of Events</label>            
          <select id="count" class="span4">                
             <option value="10">10</option>
             <option value="50">50</option>
             <option value="100">100</option>
             <option value="1000000">All</option>
          </select>  
          <button id="getEvents" class="btn btn-warning"><i class="icon-search icon-white"></i></button>        
    </div>
    
            <div id="eventsin"></div>
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
    
    -webkit-box-sizing: border-box; /* Safari/Chrome, other WebKit */ 
    -moz-box-sizing: border-box;    /* Firefox, other Gecko */ 
     box-sizing: border-box;         /* Opera/IE 8+ */ 
     
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
			/*$( "#Duration" ).slider({
				range: "min",
				value: 15,
				min: 1,
				max: 30,
				slide: function( event, ui ) {
					$( "#time" ).html( ui.value );
				}
			});
			$( "#time" ).html( $( "#Duration" ).slider( "value" ) );
			*/
			
			//$('#Duration').draggable();
			$("#tabs").tab();
			
			$('#sinTime').datetimepicker({
			    ampm: true
            });
            $('#recTime').timepicker({
               ampm: true
            });
		
		});
		
		///send control
       function control(){
           var type = <%out.println("'"+mod_type+"'"+";"); %>
            var name = <% out.println("'"+mod_name+"'"+";"); %>
            var action = "OPEN";
            message=15;
            $.ajax({
              type: 'POST',
              url: "/ControlModuleServlet",
              data: { "moduleName": name, "moduleType": type, "message": message, "action": "Open"},
              success: function(resp){
                //$("#control_panel").html(resp);
            }
            });
        }
            
         ///send control
        function schedule(single){
            var type = <%out.println("'"+mod_type+"'"+";"); %>
            var name = <% out.println("'"+mod_name+"'"+";"); %>
            var Sun = 0;
            var Mon = 0;
            var Tue = 0;
            var Wed = 0;
            var Thu = 0;
            var Fri = 0;
            var Sat = 0;
            var timeoff = new Date().getTimezoneOffset()/60;

            if(single){
                var value = 10;
                var time = Date.parse($("#sinTime").val());
                var eventType = 'single';
                var dayw = getdayofweekBIN($("#sinTime").val());
                var recur = false;
                switch (dayw)
                {
                case 0:
                  Sun=1;              
                  break;
                case 1:
                  Mon=1;              
                  break;
                case 2:  
                  Tue=1;          
                  break;
                case 3: 
                  Wed=1;           
                  break;
                case 4:  
                  Thu=1;          
                  break;
                case 5:  
                  Fri=1;          
                  break;
                case 6: 
                  Sat=1;           
                  break;
               default:
               }
            }else{
                var value = 10;
                var time = Date.parse("01/01/1970 "+$("#recTime").val()-3600000);
                var eventType = 'recurr';
                var thedays = new Array();
                var recur = true;
                $('#Sun').is(':checked') ? Sun=true : Sun=false;
                $('#Mon').is(':checked') ? Mon=true : Mon=false;
                $('#Tues').is(':checked') ? Tue=true : Tue=false;
                $('#Wed').is(':checked') ? Wed=true : Wed=false;
                $('#Thurs').is(':checked') ? Thu=true : Thu=false;
                $('#Fri').is(':checked') ? Fri=true : Fri=false;
                $('#Sun').is(':checked') ? Sat=true : Sat=false;                
            }
                        
            var action = "OPEN";
            $.ajax({
              type: 'POST',
              url: "/ScheduleEvent",
              data: { "moduleName": name, "moduleType": type, "value": value,
              "action": action, "schedDate": time, "active": true, "eventType":eventType,
              "Sun": Sun, "Mon": Mon, "Tue":Tue, "Wed":Wed, "Thu":Thu, "Fri":Fri, "Sat":Sat, 
              "offset": timeoff, "recur":recur },
              success: function(resp){
                //$("#control_panel").html(resp);
                alert("Event Created");
            }
            });
        }
        
        //get the week day binary string
        function getdayofweekBIN(thedate){
            //03/01/2012 08:45
            datesp = thedate.split("/");
            year = datesp[2].split(" ");            
            newDate = new Date(year[0], datesp[0]-1, datesp[1]);
            var daystring = '0000000';
            var newDate2=newDate.getDay();            
            return newDate2;
        }
        
        $("button .door").click(function(){
            control();
        });
        
         $('#recurr').click(function(){ 
             schedule(0);
         });
         $('#single').click(function(){ 
             schedule(1);
         }); 
         
          function getEvents(){
            var count = $("#count").val();
            $.ajax({
                  type: 'POST',
                  url: "/events.jsp",
                  data: { "moduleName": <%out.println("'"+mod_name+"'"); %>,
                          "moduleType": <%out.println("'"+mod_type+"'"); %>,
                          "count": count },
                  success: function(resp){
                    $("#eventsin").html(resp);
                }
                });
         }
        
        
         $('#recurr').click(function(){ 
             schedule(0);
         });
         $('#single').click(function(){ 
             schedule(1);
         }); 
         $('#getEvents').click(function(){ 
             getEvents();
         });  
         
         ///open door function
         $('#opendoor').click(function(){ 
             control();
         });  
		</script>
	
	<h2><%out.println(mod_name); %></h2>	
<ul id="tab" class="nav nav-tabs" data-tabs="tabs">
    <li class="active"><a href="#Control" data-toggle="tab">Control</a></li>
    <li><a href="#Schedule" data-toggle="tab">Schedule</a></li>
    <li><a href="#Events" data-toggle="tab">Events</a></li>
</ul>
<div id="myTabContent" class="tab-content">
    
    
    <div class="tab-pane fade in active well" id="Control">
	   
        <p>Open the door for 10 seconds.</p>
	   <!--<p id="time" style="border:0;width:50px;font-weight:bold;"></p>
	   <div id="Duration"></div><br/>--></br/>
	   <a id="opendoor" class="door btn btn-primary btn-large btn-success">Open</a><br/>
    </div>
    
    
    <div class="tab-pane fade" id="Schedule">
        <div class="well span10" style="margin-left:0px;">
            <fieldset>
                <legend>Recurring Event</legend>
                <div class="control-group">
                    <label class="control-label" for="recTime">Time</label>
                    <div class="controls">
                        <input type="text" class="span5" id="recTime">
                        <p class="help-block">Click the above box to open time selection box</p>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inlineCheckboxes">Days</label>                   
                            <label class="checkbox ">
                                <input type="checkbox" id="Sun" value="1">Sunday
                            </label>
                       
                            <label class="checkbox ">
                                 <input type="checkbox" id="Mon" value="1.">Monday
                            </label>
                                             
                            <label class="checkbox ">
                                <input type="checkbox" id="Tues" value="1">Tuesday
                            </label>
                       
                            <label class="checkbox ">
                                <input type="checkbox" id="Wed" value="1">Wednesday
                            </label>
                       
                            <label class="checkbox ">
                                <input type="checkbox" id="Thurs" value="1">Thursday
                            </label>
                    
                            <label class="checkbox ">
                                <input type="checkbox" id="Fri" value="1">Friday
                            </label>
                   
                            <label class="checkbox ">
                                <input type="checkbox" id="Sat" value="1">Saturday
                            </label>                                           
                    
                </div>
                <div class="control-group">
                    <br/>
                    <label class="control-label" for="recVal">Value</label>
                    <div class="controls">
                        <input type="text" class="span5" id="recVal">
                        <p class="help-block">Enter A Brightness Value</p>
                    </div>
                </div>
                
            </fieldset>
            <button id="recurr" class="btn btn-primary">Create Recurring Event</button> 
       </div>
        
         <div class="well  span10" style="margin-left:0px;">
            <fieldset>
                <legend>One Time Event</legend>
                <div class="control-group">
                    <label class="control-label" for="sinTime">Time</label>
                    <div class="controls">
                        <input type="text" class="span5" id="sinTime">
                        <p class="help-block">Click the above box to open time selection box</p>
                    </div>
                </div>
                <!--<div class="control-group">
                    <label class="control-label" for="sinVal">Value</label>
                    <div class="controls">
                        <input type="text" class="span5" id="sinVal">
                        <p class="help-block">Enter A Brightness Value</p>
                    </div>
                </div>-->
                
            </fieldset>
            <button id="single" class="btn btn-info">Create Single Event</button>       
       </div>
        
    </div>
    
    <div class="tab-pane fade well" id="Events">
        
    <div class="form-inline span8" style="margin-left:0px;">
        <label class="control-label" for="count"># of Events</label>            
          <select id="count" class="span4">                
             <option value="10">10</option>
             <option value="50">50</option>
             <option value="100">100</option>
             <option value="1000000">All</option>
          </select>  
          <button id="getEvents" class="btn btn-warning"><i class="icon-search icon-white"></i></button>        
    </div>
    
            <div id="eventsin"></div>
    </div>
            
</div>   

	
<%}else{
	out.println("<h2>ERROR "+mod_type+" "+mod_name+"</h2>");
}%>