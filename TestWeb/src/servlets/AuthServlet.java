package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
			SessionTools.deleteSession(11);
			JSONObject msg = services.UserServices.login(login,passwd);
			PrintWriter w = res.getWriter();
			w.print(msg.toString());
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
