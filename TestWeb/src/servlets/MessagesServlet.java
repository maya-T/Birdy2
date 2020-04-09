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

@SuppressWarnings("serial")
public class MessagesServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
		
		String login= req.getParameter("login");
		String message = req.getParameter("message");

		JSONObject obj = services.MessageServices.addMessage(login, message);
		PrintWriter w = res.getWriter();
		w.print(obj.toString());

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
		  
			String _id = (String) params.get("_id");
			JSONObject msg = services.MessageServices.deleteMessage(_id);
			PrintWriter w = res.getWriter();
		    w.print(msg.toString());
		    
		}else {
			//erreur parametre manquant
		}
		

    }
	
}
