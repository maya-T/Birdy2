package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class ShowMessagesServlet extends HttpServlet {


	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	

		BufferedReader reader = req.getReader();

		JSONObject params = Utils.getJSONObject(reader);

		try {
			//OutputStream os = res.getOutputStream();
			String login=(String) params.get("login");
			JSONObject obj = services.MessageServices.listUserNFriendsMessages(login);
			PrintWriter w = res.getWriter();	
			w.print(obj);
			System.out.println(obj.toString());
            

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
