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

import services.FriendServices;


@SuppressWarnings("serial")
public class FriendsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws 
    IOException , ServletException{
		String path = req.getPathInfo();
		StringTokenizer tokenizer = new StringTokenizer(path,"/");
		Map<String,String> params = new HashMap<String,String>();
		int nb = tokenizer.countTokens();
		if ( nb == 2 ) {
		  while(tokenizer.hasMoreTokens()) {
			
			params.put(tokenizer.nextToken(), tokenizer.nextToken());
			
		  }
		  
			String login = params.get("login");
			JSONObject msg=FriendServices.listFriends(login);
		    PrintWriter w = res.getWriter();
			    w.print(msg.toString());
		}else {
			//erreur parametre manquant
		}
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
    IOException , ServletException{
		String paramLogin1 = req.getParameter("loginA");
		String paramLogin2 = req.getParameter("loginF");
		
		JSONObject j=FriendServices.addFriend(paramLogin1, paramLogin2);
		PrintWriter writer = res.getWriter();
		writer.println(j.toString());
		
	}
	
	public void doDelete(HttpServletRequest req,HttpServletResponse res)throws 
    IOException , ServletException{
		String paramLogin1 = req.getParameter("loginA");
		String paramLogin2 = req.getParameter("loginF");
		
		JSONObject j=FriendServices.deleteFriend(paramLogin1, paramLogin2);
		PrintWriter writer = res.getWriter();
		writer.println(j.toString());
		
	}
}
