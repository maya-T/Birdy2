
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/Test2")

public class TestServlet2 extends HttpServlet {

	 public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	    IOException , ServletException
	    {
	        PrintWriter pr=res.getWriter();// line 10
	        res.setContentType("text/html");
	        try
	        {
	           int x=Integer.parseInt(req.getParameter("t1"));
	           int y=Integer.parseInt(req.getParameter("t2"));
	           int z=x+y;
	           pr.println("<HTML>");
	           pr.println("<HEAD><TITLE>Hello</TITLE></HEAD>");
	           pr.println("<BODY>");
	           pr.println("First No.<input type=text value=" +x +"><br><br>");
	           pr.println("Second No.<input type=text value=" +y +"><br><br>");
	           pr.println("Output No:<input type=text value=" +z +"><br><br>");
	           pr.println("<input type=submit value=submit>");
	           pr.println("</BODY></HTML>");
	        }
	        catch(Exception e)
	        {
	        e.printStackTrace();
	        pr.println("Invalid Input");
	        }
	    } 
	    
		
	}







