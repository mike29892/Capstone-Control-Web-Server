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
            String wattsval ="";
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
            
           
            // q.setKeysOnly();
            //List<Entity> allKeys = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
            //int thecount =allKeys.size();
            int thecount =0;

            out.println("{\"cols\": [{\"label\": \"Time\", \"type\": \"string\"},");
            out.println("{\"label\": \"Watts\", \"type\": \"number\"}],");            
            out.println("\"rows\": [");
         
            //loop through and output the proper lines
            for (Entity result : pq) {  
                Date thedate = (Date)result.getProperty("date");
                thequery.setTime(thedate);
                wattsval = (String) result.getProperty("data");            
                ///Day
                if(time.equals("day")){                    
                    if( (thequery.compareTo(yesterday)) >= 0 ){
                        hour = thequery.get(Calendar.HOUR);
                        minute= thequery.get(Calendar.MINUTE);
                        finalList.add(0,result);
                        //if( (hour-4)<0){hour=24-(4-hour);}
                        //else{ hour = hour-4;}                                
                        //out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":"+wattsval+"} ]},"); 
                    }else{
                        //out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":0} ]}]}"); 
                        break;
                     } 
                     //thecount++;             
                 }else if(time.equals("week")){                    
                    if( (thequery.compareTo(weekago)) >= 0 ){
                        hour = thequery.get(Calendar.HOUR);
                        minute= thequery.get(Calendar.MINUTE);
                        finalList.add(0,result);
                        //if( (hour-4)<0){hour=24-(4-hour);}
                        //else{ hour = hour-4;}                                
                        //out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":"+wattsval+"} ]},"); 
                    }else{
                        //out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":0} ]}]}"); 
                        break;
                     } 
                     //thecount++;             
                 } 
                                             
          }
          
          ///output final list
         for (Entity result : finalList) {
             Date thedate = (Date)result.getProperty("date");
                thequery.setTime(thedate);
                wattsval = (String) result.getProperty("data");            
                ///Day
                if(time.equals("day")){                    
                    if( (thequery.compareTo(yesterday)) >= 0 ){
                        hour = thequery.get(Calendar.HOUR);
                        minute= thequery.get(Calendar.MINUTE);                        
                        if( (hour-4)<0){hour=24-(4-hour);}
                        else{ hour = hour-4;}                                
                        out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":"+wattsval+"} ]},"); 
                    }else{
                        //out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":0} ]}]}"); 
                        //break;
                     } 
                     thecount++;             
                 } else if(time.equals("week")){                    
                    if( (thequery.compareTo(weekago)) >= 0 ){
                        hour = thequery.get(Calendar.HOUR);
                        minute= thequery.get(Calendar.MINUTE);                        
                        if( (hour-4)<0){hour=24-(4-hour);}
                        else{ hour = hour-4;}                                
                        out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":"+wattsval+"} ]},"); 
                    }else{
                        //out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":0} ]}]}"); 
                        //break;
                     } 
                     thecount++;             
                 } 
        }
        out.println("{\"c\":[{\"v\": \""+thecount+"\", \"f\": \""+hour+":"+minute+"\"}, {\"v\":0} ]}]}"); 
   %>
    