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

public class OtherInfoServlet  extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
       
	

		String path = req.getPathInfo();
		StringTokenizer tokenizer = new StringTokenizer(path,"/");
		Map<String,Object> params = new HashMap<String,Object>();
		int nb = tokenizer.countTokens();
		if ( nb == 4 ) {

			while(tokenizer.hasMoreTokens()) {

				params.put(tokenizer.nextToken(), tokenizer.nextToken());

			}

			String otherLogin = (String) params.get("otherLogin");
			String myLogin = (String) params.get("myLogin");
			JSONObject obj = FriendServices.getFriendInfo(myLogin, otherLogin);
			
			
			PrintWriter w = res.getWriter();
			w.print(obj.toString());

		}else {
			//erreur parametre manquant
		}
	}
	

}
