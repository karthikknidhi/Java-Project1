package com.cpsc476;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
//import java.util.HashMap;
import java.util.Map;

public class PrivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   //String username= (String) request.getSession().getAttribute("username");
	  // response.getWriter().println("Welcome, "+username);
	   if(request.getSession().getAttribute("username") == null)
       {
           response.sendRedirect("login");
           return;
       }
	   
	   String action= (String) request.getParameter("action");
	   if (action.equals("redirect")){
	     String url=(String) request.getParameter("url");
	     String longUrl="";
	     
	     for(String item: PublicServlet.database.keySet()){
		    Url shortUrl = (Url) PublicServlet.database.get(item);
		    if (shortUrl.getUrl().equals(url)){  
			    longUrl=item;
			    response.sendRedirect("http://"+ longUrl);
		    }
	     }
	 
	   }
	   else{
	   String uname=(String)request.getSession().getAttribute("username");
	   request.setAttribute("username", uname);

	   request.getRequestDispatcher("/WEB-INF/jsp/view/private.jsp")
           .forward(request, response);
	   }
      
       


}


protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
   String uname=(String)request.getSession().getAttribute("username");
	if(uname == null)
  {
      response.sendRedirect("login");
      return;
  }
	String lurl=request.getParameter("longurl"); 
  String surl=this.createURL(request, response);
  String x[][]=this.showListURL(request, response);
   //System.out.println(x);
   request.setAttribute("url", x);
   request.setAttribute("shorturl", surl);
   request.setAttribute("longurl", lurl);
   request.getRequestDispatcher("/WEB-INF/jsp/view/private.jsp").forward(request, response);
}

private String createURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    String longurl1=request.getParameter("longurl");
    //System.out.println(longurl1);
    String shorturlstr = Base64.getUrlEncoder().encodeToString(longurl1.getBytes("utf-8")).substring(1,7);
    //System.out.println(shorturlstr);
    String shorturl="http://localhost:8080/short/"+shorturlstr;
    Url u=new Url(shorturl);
    PublicServlet.database.put(longurl1, u);
     //System.out.println(PublicServlet.database);
     
     return shorturl;

}

private String[][] showListURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	int n=PublicServlet.database.size();
	String[][] myStringArray = new String [100][100];
	Url values[]=new Url[n];
    int i=0;
    for (Map.Entry<String,Object> entry : PublicServlet.database.entrySet()) {
         String key = entry.getKey();
         values[i] = (Url)entry.getValue();
         myStringArray[i][0]=values[i].getUrl();
         myStringArray[i][1]=new Integer(values[i].getClicks()).toString();
         //System.out.println(myStringArray[i][0]+" "+myStringArray[i][1]);
         i++;
       }
    return myStringArray;
}
}