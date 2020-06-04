package essais;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@SuppressWarnings("serial")
public class WebSocketServlet  extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
       String usr = req.getParameter("username");
       PrintWriter w = res.getWriter();
       
       if(usr != null) {
    	   
       }
	}
}

