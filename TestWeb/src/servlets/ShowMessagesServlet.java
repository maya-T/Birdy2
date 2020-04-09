package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class ShowMessagesServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
			
		String login= req.getParameter("login");

		JSONObject obj = services.MessageServices.listMessages(login);
		PrintWriter w = res.getWriter();
		w.print(obj.toString());

    }
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
		
		int limit = 0;
		JSONObject obj = services.MessageServices.listMessages(limit);
		PrintWriter w = res.getWriter();
		w.print(obj.toString());

    }
	
}
