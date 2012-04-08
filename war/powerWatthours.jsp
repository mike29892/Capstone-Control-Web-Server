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

<%@ page import="com.google.appengine.api.datastore.PreparedQuery"%>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@ page import="com.google.appengine.api.datastore.Query.SortDirection"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.ArrayList"%>



<%
String mod_type=(String)request.getParameter("moduleType");
String mod_name=(String)request.getParameter("moduleName");
String time = (String)request.getParameter("time");

%>

          
            <%
            
            
             ///query and output the chart for specified time
         
            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            String username = user.getNickname();     
            //get last dimmer type 
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
             //QUERY FOR UN-ACKED ALERTS    
            Query q = new Query("PowerMonitorData");
            //filter for events on that day     
            q.addFilter("user", FilterOperator.EQUAL, username);
            q.addFilter("moduleName", FilterOperator.EQUAL, mod_name);  
            q.addSort("date", SortDirection.DESCENDING);   
            List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
       
            List<Entity> finalList = new ArrayList<Entity>();
            int hour =0;
            int minute =0;		
            String day ="";
            String month ="";
            int year = 0;
            double wattsval = 0.0;
            int count = 0;
            Date now = new Date();
            Calendar today = Calendar.getInstance();
            Calendar yesterday = Calendar.getInstance();
            Calendar thequery = Calendar.getInstance();
            Calendar weekago = Calendar.getInstance();
            today.setTime(now);
            now.setTime( (now.getTime()-86400000) );
            yesterday.setTime(now);
            now.setTime( (now.getTime()-(86400000*6)) );
            weekago.setTime(now);
            
			//variables for keeping track of usage
			double totalKWatts = 0.0;
			double wattcount = 0.0;
			double KwattHour = 0.0;
			double totswatts = 0.0;
           
            // q.setKeysOnly();
            //List<Entity> allKeys = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
            //int thecount =allKeys.size();
            int thecount =0;

           
         
            //loop through and output the proper lines
            for (Entity result : pq) {  
	
                Date thedate = (Date)result.getProperty("date");
                thequery.setTime(thedate);
                wattsval = Double.parseDouble((String) result.getProperty("data"));   
         
                ///Day
                if(time.equals("day")){                    
                    if( (thequery.compareTo(yesterday)) >= 0 ){    
						totswatts = totswatts+ wattsval;                    
						KwattHour = (wattsval/1000)*0.034;
						totalKWatts= totalKWatts+KwattHour;
						wattcount++;
                    }else{
                       
                        break;
                     } 
                     //thecount++;             
                 }else if(time.equals("week")){                    
                    if( (thequery.compareTo(weekago)) >= 0 ){
						totswatts = totswatts+ wattsval;
                       	KwattHour = (wattsval/1000)*(0.034);
						totalKWatts= totalKWatts+KwattHour;
						wattcount++;
                    }else{
                       break;
                     } 
                     //thecount++;             
                 } 
                                             
          }
        

		///calculate the values
  		double avekWh = totswatts/wattcount;
		//avekWh = avekWh/(.034*wattcount);
      
        double temp = totalKWatts*1000;
        int tots = (int)temp;
        double temp2 = (double)tots;
        totalKWatts = temp2/1000;
        
        temp = avekWh*1000;
        tots = (int)temp;
        temp2 = (double)tots;
        avekWh = temp2/1000;
        
		out.println("<div class=\"alert \">");
		out.println("<p class=\"\">Total kWh observed: <b>"+ totalKWatts+"</b></p>");  
		out.println("<p class=\"\">Average Watts observed: <b>"+ avekWh+"</b></p>"); 
		out.println("</div>");
		
           
   %>
    