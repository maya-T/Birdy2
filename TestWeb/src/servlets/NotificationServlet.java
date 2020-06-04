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

public class NotificationServlet extends HttpServlet {
	
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
				
				JSONObject msg = FriendServices.listNotifications(login);
				
			
				PrintWriter w = res.getWriter();
			    w.print(msg.toString());
			    
			}else {
				//erreur parametre manquant
			}
			
		}
		
		public void doDelete(HttpServletRequest req,HttpServletResponse res)throws 
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
				
			
			JSONObject msg = FriendServices.setNotificationsToSeen(login);
			
			PrintWriter writer = res.getWriter();
			writer.println(msg.toString());
			
		}else {
			System.out.println("hello");
			//erreur parametre
		}
		}
	}

