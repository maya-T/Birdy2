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
public class SearchServlet extends HttpServlet{
	  @Override
		public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
		IOException , ServletException{
	    	
	    	BufferedReader reader = req.getReader();
	    	
			JSONObject params = Utils.getJSONObject(reader);
			
			try {
				String filter = (String) params.get("filter");
//		        String filter = (String) req.getParameter("filter");
				System.out.println("filter"+filter);
				JSONObject msg = services.MessageServices.searchMessages(filter);
				PrintWriter w = res.getWriter();
				w.print(msg.toString());
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
}
