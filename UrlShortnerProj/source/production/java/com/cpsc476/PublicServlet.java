package com.cpsc476;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cpsc476.Url;


public class PublicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//The URL's database:
    protected static Map<String, Object> database= new HashMap<>();   
    
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//if no user is loged in take the client to the guest page:
    	if (request.getSession().getAttribute("username")==null){
    	request.getRequestDispatcher("/WEB-INF/jsp/view/guest.jsp").forward(request,response);
    	}
	}
	
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		switch(action){
		case "guest":
		default:
			guest(request,response);
		}
    	
	}

    //The public user's post request method:
    private void guest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	//getting the short URL entered by the guest user:
    	String shortUrl= request.getParameter("shortUrl");
    	
    	//looking, in the URL DB, for the long version of the URL: 
    	Url urlObj = (Url) database.get(shortUrl);
       
    	//if the DB is empty, i.e no URL's are stored yet:
	    if (database.size()== 0){
	    	//setting the emptyDB flag to true before dispatching to the jsp page:
	    	request.setAttribute("emptyDB", true);
		    request.setAttribute("itemnotfound", false);
	    }
	    //if the requested URL is found in the DB:
	    else if (urlObj != null){
	    String originalUrl= urlObj.getUrl();	    
	    request.setAttribute("url", originalUrl);
	    request.setAttribute("emptyDB", false);
	    request.setAttribute("itemnotfound", false);
	    }
	    //finally, if the requested URL is not found in the DB:
	    else if(urlObj ==null){
	    	//setting not itemnotfound flag to true:
		    request.setAttribute("itemnotfound", true);
		    request.setAttribute("emptyDB", false);
	    }
	
	    request.getRequestDispatcher("WEB-INF/jsp/view/public.jsp").forward(request, response);
    }
    
	

}
