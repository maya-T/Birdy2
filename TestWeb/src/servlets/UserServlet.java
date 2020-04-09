package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet  {
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
    IOException , ServletException{
		
		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String email = req.getParameter("email");
		String passwd = req.getParameter("passwd");
		String login = req.getParameter("login");
		JSONObject msg = services.UserServices. createUser(fname,lname,email,passwd,login);
		PrintWriter w = res.getWriter();
		w.print(msg.toString());
		
	}
}
