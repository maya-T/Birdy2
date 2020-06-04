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
		
		
//		
		
        JSONObject j=MessagesTools.getUserMessages(11);	
//		System.out.println(j);
//		JSONObject j2=MessagesTools.getLatestMessages(2);	
//		System.out.println(j2);
		//MessagesTools.insertMessage(400, "message");
//		MessagesTools.addLike( "5e8f8eeed821a8254bd7b275", 30);
//		MessagesTools.addLike("5e8f8eeed821a8254bd7b275", 52);
//		System.out.println(MessagesTools.likeExists("5e8f8eeed821a8254bd7b275",30));
//		MessagesTools.addComment("5e977c5acd4ef02dd12ee642", 4, "I adore you");
//		MessagesTools.addComment("5e977c5acd4ef02dd12ee642", 5, "I'm on the edge of glory");
//		JSONObject j=MessagesTools.getComments("5e977c5acd4ef02dd12ee642");
		System.out.println(j);
//		System.out.println(j.getClass());
		//MessagesTools.insertMessage(11, "hello");
		}

}
