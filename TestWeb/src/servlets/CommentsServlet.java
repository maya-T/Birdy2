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

@SuppressWarnings("serial")
public class CommentsServlet extends HttpServlet {

	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	

		BufferedReader reader = req.getReader();

		JSONObject params = Utils.getJSONObject(reader);

		try {
			String _id = (String) params.get("_id");
			String login=(String) params.get("login");
			String comment =(String) params.get("comment");
		
//			String _id = (String) req.getParameter("_id");
//			String login=(String) req.getParameter("login");
//			String comment =(String) req.getParameter("comment");
			System.out.println(login+" "+comment);

			JSONObject obj = services.MessageServices.addComment(_id, login, comment);
			PrintWriter w = res.getWriter();
			w.print(obj.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	public void doGet(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	

		String path = req.getPathInfo();
		System.out.println(path);
		StringTokenizer tokenizer = new StringTokenizer(path,"/");
		Map<String,Object> params = new HashMap<String,Object>();
		int nb = tokenizer.countTokens();
		if ( nb == 2 ) {
		    params.put(tokenizer.nextToken(), tokenizer.nextToken());
			String _id = (String) params.get("_id");
			JSONObject comments = services.MessageServices.listComments(_id);
			PrintWriter w = res.getWriter();
			w.print(comments.toString());
		}else {
			//erreur parametre manquant
		}

	}

}
