<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Url Shortner</title>
</head>

<%-- getting attributes from the servlet: --%>
<% 
   String url= (String)request.getAttribute("url"); 
   boolean emptyDB = (boolean) request.getAttribute("emptyDB");
   boolean notFound = (boolean) request.getAttribute("itemnotfound");
%>
<body>
   <%--if the URL's DB is not empty && URL is found in the DB: --%>
   <% if (emptyDB != true && notFound != true){ %>
   <h3>The original URL for the shortened one you entered is: </h3>
   <h3> <%=url%> </h3>
   <br/>
   <a href="public">Go Back</a>
   <%}%>
   
   <%-- if the URL is not found in the DB: --%>
   <% if (notFound == true){
	  out.println("Sorry, URL is not found. Try another one!");%>
	  <br/> 
	  <a href="public">Go Back</a>
   <%}%>
   
   <%-- if the URL DB is empty, i.e no URL's stored in DB yet: --%>
   <% if (emptyDB == true){
	  out.println("Sorry, There are no URL saved. Please login and add some URL's!");%>
	  <br/>
	  <a href="public">Go Back</a> 
   <%}%>
   
      
</body>
</html>