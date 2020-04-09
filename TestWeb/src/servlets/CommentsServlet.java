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
public class CommentsServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
		
		String _id = req.getParameter("_id");
		String login= req.getParameter("login");
		String comment = req.getParameter("comment");

		JSONObject obj = services.MessageServices.addComment(_id, login, comment);
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
			JSONObject comments = services.MessageServices.listComments(_id);
			PrintWriter w = res.getWriter();
			w.print(comments.toString());
		}else {
			//erreur parametre manquant
		}

    }
	
	public void doDelete(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
		
		String path = req.getPathInfo();
		StringTokenizer tokenizer = new StringTokenizer(path,"/");
		Map<String,Object> params = new HashMap<String,Object>();
		int nb = tokenizer.countTokens();
		if ( nb == 4 ) {
		    while(tokenizer.hasMoreTokens()) {
				  
				params.put(tokenizer.nextToken(), tokenizer.nextToken());
				
			}
		  
			String _id = (String) params.get("_id");
			int _idC = (int) params.get("_idC");
			JSONObject msg = services.MessageServices.deleteComment(_id, _idC);
			PrintWriter w = res.getWriter();
		    w.print(msg.toString());
		    
		}else {
			//erreur parametre manquant
		}
	}

}
