
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import bd.Database;
import tools.MessagesTools;
import tools.UserTools;

public class TestServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		PrintWriter writer = res.getWriter();
		writer.println("Hello World 111333");
		JSONObject j = MessagesTools.getMessages(17);
		writer.print(j.toString());
	}

}
