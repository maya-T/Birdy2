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

import tools.SessionTools;

@SuppressWarnings("serial")
public class AuthServlet extends HttpServlet {
    @Override
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{
    	
    	
    	BufferedReader reader = req.getReader();
    	
		JSONObject params = Utils.getJSONObject(reader);
		
		try {
			
			String passwd = (String) params.get("password");
			String login = (String) params.get("login");
//			String passwd = (String) req.getParameter("password");
//			String login = (String) req.getParameter("login");
			JSONObject msg = services.UserServices.login(login,passwd);
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
		Map<String,Object> params = new HashMap<String,Object>();
		int nb = tokenizer.countTokens();
		if ( nb == 2 ) {
			while(tokenizer.hasMoreTokens()) {

				params.put(tokenizer.nextToken(), tokenizer.nextToken());

			}

			String login = (String) params.get("login");
			JSONObject msg = services.UserServices.logout(login);
			PrintWriter w = res.getWriter();
			w.print(msg.toString());

		}else {
			//erreur parametre manquant
		}
	}
}
