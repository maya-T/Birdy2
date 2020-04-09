package tools;

import java.util.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.*;

import bd.Database;

public class MessagesTools {

	public static JSONObject getMessages(int id) {
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> messages = db.getCollection("message");
		Document query = new Document();
		query.append("author_id",id);
		MongoCursor<Document> cursor =  messages.find(query).iterator();
		List<JSONObject> msg = new ArrayList<JSONObject>(); 
		while(cursor.hasNext()) {
			msg.add(new JSONObject(cursor.next()));
		}
		
		JSONObject result=new JSONObject();
		try {
			result.append("messages", msg);
			
			return result;
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
		return result;
	}

	public static JSONObject getMessages(int id, String filter) {
		return null;
	}

	public static JSONObject getMessages() {
		
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> messages = db.getCollection("message");
		
		Document condition = new Document();
		
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday= calendar.getTime();
		condition.append("$gte", yesterday);
		
		Document query = new Document();
		query.append("date", condition );
		MongoCursor<Document> cursor =  messages.find(query).iterator();
		List<JSONObject> msg = new ArrayList<JSONObject>(); 
		while(cursor.hasNext()) {
			msg.add(new JSONObject(cursor.next()));
		}
		
		JSONObject result=new JSONObject();
		try {
			result.append("messages", msg);
			return result;
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
	public static JSONObject getLatestMessages(int limit) {
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> messages = db.getCollection("message");	
		Document query = new Document();
		query.append("date", -1);
		MongoCursor<Document> cursor =  messages.find().sort(query).limit(limit).iterator();
		List<JSONObject> msg = new ArrayList<JSONObject>(); 
		while(cursor.hasNext()) {
			msg.add(new JSONObject(cursor.next()));
		}
		
		JSONObject result=new JSONObject();
		try {
			result.append("messages", msg);
			return result;
			
		} catch (JSONException e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
	public static void insertMessage(int id, String message) {
		
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> coll = db.getCollection("message");
		Document doc = new Document();
		doc.append("author_id", id);
		doc.append("text", message);
		doc.append("count", 0);
		GregorianCalendar calendar= new java.util.GregorianCalendar();
		Date date=calendar.getTime();
		doc.append("date", date);
		ArrayList <JSONObject> listc=new ArrayList<JSONObject>();
		doc.append("comments",listc);
		ArrayList <JSONObject> listl=new ArrayList<JSONObject>();
		doc.append("likes",listl);
		coll.insertOne(doc);
		
		
	}
	
	public static void deleteMessage(String _id) {
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> coll = db.getCollection("message");
		coll.deleteOne(new Document("_id", new ObjectId(_id)));
		
	}
	
	public static boolean messageExists(String _id) {
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> messages = db.getCollection("message");
		
		Document query = new Document();
		query.append("_id", new ObjectId(_id));
		MongoCursor<Document> cursor =  messages.find(query).iterator();
		return cursor.hasNext();
	}
	
       public static void addComment (String _id, int idC, String comment) {
		
			MongoDatabase db = Database.getMongoDBConnection();
			MongoCollection<Document> coll = db.getCollection("message");
			
			Document find = new Document();
	        Document proj = new Document();
			
		    proj.append("count",1);
		    proj.append("_id", 0);
		    
			find.append("_id", new ObjectId(_id));
			
			MongoCursor<Document> cursor =  coll.find(find).projection(proj).iterator();
			int count = 0;
			if(cursor.hasNext()) {
				count = cursor.next().getInteger("count");
			}
			
			Document comnt= new Document();
	        comnt.append("_idC", count);
			comnt.append("comment_author_id", idC);
			comnt.append("content", comment );
			GregorianCalendar calendar= new java.util.GregorianCalendar();
			Date now=calendar.getTime();
			comnt.append("date", now);
			
			Document modif = new Document();
			modif.append("comments", comnt);
			
			Document inc = new Document();
			inc.append("count", 1);
			
			Document push = new Document();
			push.append("$push", modif);
			
			push.append("$inc", inc);
	
		    coll.updateOne(find, push);
			
     	
	}
	
	public static JSONObject getComments(String _id) {
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> messages = db.getCollection("message");
		
		Document query = new Document();
		Document proj = new Document();
		
		query.append("_id", new ObjectId(_id));
	    proj.append("comments",1);
	    proj.append("_id", 0);
		
		MongoCursor<Document> cursor =  messages.find(query).projection(proj).iterator();
		
		if(cursor.hasNext()) {
		 JSONObject result=new JSONObject(cursor.next());	
		 return result;
		}
		return new JSONObject();
	}
	
	public static void deleteComment(String _id, int _idC) {

		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> coll = db.getCollection("message");
		
		Document find = new Document();
		find.append("_id", new ObjectId(_id));
		
		Document comnt = new Document();
		comnt.append("_idC", _idC);
		

		Document modif = new Document();
		modif.append("comments", comnt);
		
		Document pull = new Document();
		pull.append("$pull", modif);
		
		coll.updateOne(find, pull);
				
	}
	
	public static boolean commentExists(String _id, int _idC) {
		
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> messages = db.getCollection("message");
		
		Document query = new Document();
		Document proj = new Document();
		
	    proj.append("comments",1);
	    proj.append("_id", 0); 
		query.append("_id", new ObjectId(_id));
		
		
		MongoCursor<Document> cursor =  messages.find(query).projection(proj).iterator();
	
		@SuppressWarnings("unchecked")
		List<Document> comments = (List<Document>) cursor.next().get("comments");
		for(Document cmnt: comments) {
			if(cmnt.getInteger("_idC") == _idC) {
				return true;
			}
		}
		
		return false;		
	}
	
	public static void addLike(String _id, int idL)
	{  
	   MongoDatabase db = Database.getMongoDBConnection();
	   MongoCollection<Document> coll = db.getCollection("message");
	   
       Document modif = new Document();
	   modif.append("likes", idL);
		
		
	   Document push = new Document();
	   push.append("$push", modif);
	   coll.updateOne(new Document("_id", new ObjectId(_id)), push);
		
	}
	
	public static void unLike(String _id, int idL)
	{
		 MongoDatabase db = Database.getMongoDBConnection();
		   MongoCollection<Document> coll = db.getCollection("message");
	       Document modif = new Document();
		   modif.append("likes", idL);
			
			
		    Document push = new Document();
			push.append("$pull", modif);
			
			coll.updateOne(new Document("_id", new ObjectId(_id)), push);
	}

	public static boolean likeExists(String _id, int _idL) {
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> messages = db.getCollection("message");
		
		Document query = new Document();
		Document proj = new Document();
		
	    proj.append("likes",1);
	    proj.append("_id", 0); 
		query.append("_id", new ObjectId(_id));
		
		
		MongoCursor<Document> cursor =  messages.find(query).projection(proj).iterator();
	
		@SuppressWarnings("unchecked")
		List<Integer> likes = (List<Integer>) cursor.next().get("likes");
		for(int like: likes) {
			if(like == _idL) {
				return true;
			}
		}
		
		return false;		
	}
	
	public static JSONObject getLikes(String _id) {
		MongoDatabase db = Database.getMongoDBConnection();
		MongoCollection<Document> messages = db.getCollection("message");
		
		Document query = new Document();
		Document proj = new Document();
		
	    proj.append("likes",1);
	    proj.append("_id", 0); 
		query.append("_id", new ObjectId(_id));
		
		
		MongoCursor<Document> cursor =  messages.find(query).projection(proj).iterator();
	
		return new JSONObject(cursor.next());
	}
	
}
