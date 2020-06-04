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
//// not used
@SuppressWarnings("serial")
public class UserMessagesServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
        
		BufferedReader reader = req.getReader();
    	
		JSONObject params = Utils.getJSONObject(reader);
		
		try {
			String login=(String) params.get("login");
			String message =(String) params.get("content");
			System.out.println(login+" "+message);

			JSONObject obj = services.MessageServices.addMessage(login, message,null);
			
			PrintWriter w = res.getWriter();
			w.print(obj.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		
		

    }
	
	public void doDelete(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
		
		System.out.println("called ");
		String path = req.getPathInfo();
		StringTokenizer tokenizer = new StringTokenizer(path,"/");
		Map<String,Object> params = new HashMap<String,Object>();
		int nb = tokenizer.countTokens();
		if ( nb == 2 ) {
			
		    while(tokenizer.hasMoreTokens()) {
				  
				params.put(tokenizer.nextToken(), tokenizer.nextToken());
				
			}
		  
			String _id = (String) params.get("_id");
			JSONObject msg = services.MessageServices.deleteMessage(_id);
			PrintWriter w = res.getWriter();
		    w.print(msg.toString());
		    
		}else {
			//erreur parametre manquant
		}

    }
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
			

		String path = req.getPathInfo();
		StringTokenizer tokenizer = new StringTokenizer(path,"/");
		Map<String,Object> params = new HashMap<String,Object>();
		int nb = tokenizer.countTokens();
		if ( nb == 2 ) {
			
		    while(tokenizer.hasMoreTokens()) {
				  
				params.put(tokenizer.nextToken(), tokenizer.nextToken());
				
			}
		  
			String login = (String) params.get("login");
			JSONObject obj = services.MessageServices.listUserMessages(login);
			PrintWriter w = res.getWriter();
			w.print(obj.toString());
		    
		}else {
			//erreur parametre manquant
		}		
    }
}
