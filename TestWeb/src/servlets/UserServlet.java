package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

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
		
		
//		String path = req.getPathInfo();
//		StringTokenizer tokenizer = new StringTokenizer(path,"/");
//		Map<String,String> params = new HashMap<String,String>();
//		int nb = tokenizer.countTokens();
//		if ( nb == 10 ) {
//		  while(tokenizer.hasMoreTokens()) {
//			
//			params.put(tokenizer.nextToken(), tokenizer.nextToken());
//			
//		  }
//		  
//			String fname = params.get("fname");
//			String lname = params.get("lname");
//			String email = params.get("email");
//			String passwd = params.get("passwd");
//			String login = params.get("login");
//			JSONObject msg = services.UserServices. createUser(fname,lname,email,passwd,login);
//		  
//		  
//		  PrintWriter w = res.getWriter();
// 		  w.print(msg.toString());
//		}else {
//			//erreur parametre manquant
//		}
		
	}
}
