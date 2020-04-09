package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class AlterCommentsServlet {
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	
		
		String _id = req.getParameter("_id");

		JSONObject comments = services.MessageServices.listComments(_id);
		PrintWriter w = res.getWriter();
		w.print(comments.toString());

    }

}
