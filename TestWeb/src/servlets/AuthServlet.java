package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

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
