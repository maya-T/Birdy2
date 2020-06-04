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

@SuppressWarnings("serial")
public class UserServlet extends HttpServlet  {
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
    IOException , ServletException{
		
        BufferedReader reader = req.getReader();
    	JSONObject params = Utils.getJSONObject(reader);
		
		try {
			String fname = (String) params.get("firstname");
			String lname = (String) params.get("lastname");
			String email = (String) params.get("email");
			String passwd = (String) params.get("password");
			String login = (String) params.get("login");
			JSONObject msg = services.UserServices. createUser(fname,lname,email,passwd,login);
			PrintWriter w = res.getWriter();
			w.print(msg.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}	
	}
}
