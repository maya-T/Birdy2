package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
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
			
			JSONObject msg = FriendServices.listFollowersNfollowing(login);
			
		
			PrintWriter w = res.getWriter();
		    w.print(msg.toString());
		    
		}else {
			//erreur parametre manquant
		}
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
    IOException , ServletException{
		
        BufferedReader reader = req.getReader();
    	
		JSONObject params = Utils.getJSONObject(reader);
		
		try {
			String p1 = (String) params.get("follower");
			String p2 = (String) params.get("followed");
			JSONObject msg=FriendServices.addFriend(p1,p2);
			PrintWriter w = res.getWriter();
			w.print(msg.toString());
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public void doDelete(HttpServletRequest req,HttpServletResponse res)throws 
    IOException , ServletException{
		 
		String path = req.getPathInfo();
		StringTokenizer tokenizer = new StringTokenizer(path,"/");
		Map<String,String> params = new HashMap<String,String>();
		int nb = tokenizer.countTokens();
		if ( nb == 4 ) {
		  while(tokenizer.hasMoreTokens()) {
			
			params.put(tokenizer.nextToken(), tokenizer.nextToken());
			
		  }
		  
			String follower = params.get("follower");
			String followed = params.get("followed");
		
		JSONObject msg = FriendServices.deleteFriend(follower, followed);
		
		PrintWriter writer = res.getWriter();
		writer.println(msg.toString());
		
	}else {
		System.out.println("hello");
		//erreur parametre
	}
	}
}
