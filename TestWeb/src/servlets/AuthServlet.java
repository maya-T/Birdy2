package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class AuthServlet extends HttpServlet {

	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{
		
		String passwd = req.getParameter("passwd");
		String login = req.getParameter("login");
		JSONObject msg = services.UserServices.login(login,passwd);
		PrintWriter w = res.getWriter();
		w.print(msg.toString());

	}
}
