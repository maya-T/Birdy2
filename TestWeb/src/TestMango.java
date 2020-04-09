import java.util.Date;
import java.util.GregorianCalendar;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.client.*;
import bd.Database;
import tools.MessagesTools;
import tools.UserTools;

public class TestMango {
	
	public static void main(String[] args) {
//		MongoDatabase db = Database.getMongoDBConnection();
//		MongoCollection<Document> coll = db.getCollection("message");
//		
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		Date today = calendar.getTime();
//		calendar.add(calendar.DAY_OF_YEAR, -2);
//		Date yesterday= calendar.getTime();
		
//		Document doc1 = new Document();
//		doc1.append("id_author", 0);
//		doc1.append("name", "maya");
//		doc1.append("date", today );
//		coll.insertOne(doc1);
//		
//		Document doc2 = new Document();
//		doc2.append("id_author", 41);
//		doc2.append("name", "maya");
//		doc2.append("date", yesterday );
//		coll.insertOne(doc2);
		
		
//		JSONObject j=MessagesTools.getMessages(4);	
//		System.out.println(j);
//		JSONObject j2=MessagesTools.getLatestMessages(2);	
//		System.out.println(j2);
//		MessagesTools.insertMessage(200, "message");
//		MessagesTools.addComment(200 , 51, "hello");
//		MessagesTools.addComment(200, 52, "helloo");
		MessagesTools.deleteComment("5e8f6c8572ee0d35daf95cc8",1);	}

}
