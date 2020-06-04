package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.mongodb.client.*;


import bd.DBStatic;
import bd.Database;

public class MessagesTools {

	public static JSONObject getUserMessages(int id) {

		MongoClient mongo = Database.getMongoClient();
		try {

			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> messages = db.getCollection("message");
			Document query = new Document();
			query.append("author_id", id);
			Document order = new Document();
			order.append("date", -1);
			MongoCursor<Document> cursor = messages.find(query).sort(order).iterator();

			JSONObject result = new JSONObject();
			try {
				while (cursor.hasNext()) {
					Document msgDocument = cursor.next();
					JSONObject msg = new JSONObject(msgDocument);
					Object image = msgDocument.get("image");
					if(image instanceof org.bson.types.Binary) {
						msg.remove("image");
						byte [] image64 = Base64.getEncoder().encode(((org.bson.types.Binary)image).getData());
						String imageString = org.apache.commons.codec.binary.StringUtils.newStringUtf8(image64);
						msg.put("image", imageString);
					}
					
					ArrayList<Integer> likes =(ArrayList<Integer>) msg.get("likes");
					if(likes.contains(id)) msg.put("liked", 1) ;
					else msg.put("liked", 0) ;
					result.append("messages", msg);
				}
				
				ArrayList<Integer> friends = new ArrayList<Integer>();
				friends.add(id);JSONObject info = UserTools.getUsersInfo(friends);
				result.put("authors", info);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;

		} finally {
			if (mongo != null)
				mongo.close();
		}
	}

	public static JSONObject getUserNFriendsMessages(int id) {

		MongoClient mongo = Database.getMongoClient();
		JSONObject result = new JSONObject();
		try {
			JSONObject obj = FriendTools.listFollowingIds(id);
			System.out.println(obj);
			JSONArray array = (JSONArray) obj.getJSONArray("following");
			ArrayList<Integer> friends = new ArrayList<Integer>();
			int len = array.length();
			for (int i = 0; i < len; i++) {
				friends.add(Integer.valueOf(array.get(i).toString()));
			}
			friends.add(id);

			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);

			MongoCollection<Document> messages = db.getCollection("message");
			Document query = new Document();
			Document in = new Document();
			Document order = new Document();
			
			in.append("$in", friends);
			query.append("author_id", in);
			order.append("date", -1);
			

			MongoCursor<Document> cursor = messages.find(query).sort(order).iterator();

			if(!cursor.hasNext()) {

				result.put("messages", new JSONArray());

			}else {
				while (cursor.hasNext()) {
					
					Document msgDocument = cursor.next();
					JSONObject msg = new JSONObject(msgDocument);
					Object image = msgDocument.get("image");
					if(image instanceof org.bson.types.Binary) {
						msg.remove("image");
						byte [] image64 = Base64.getEncoder().encode(((org.bson.types.Binary)image).getData());
						String imageString = org.apache.commons.codec.binary.StringUtils.newStringUtf8(image64);
						msg.put("image", imageString);
					}
					ArrayList<Integer> likes =(ArrayList<Integer>) msg.get("likes");
					if(likes.contains(id)) msg.put("liked", 1) ;
					else msg.put("liked", 0) ;
					
					result.append("messages", msg);
				}
			}	
			JSONObject info = UserTools.getUsersInfo(friends);
			result.put("authors", info);
			return result;

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (mongo != null)
				mongo.close();
		}

		return result;
	}

	public static JSONObject getMessages(int id, String filter) {
		return null;
	}

	public static JSONObject getMessages() {

		MongoClient mongo = Database.getMongoClient();
		JSONObject result = new JSONObject();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> messages = db.getCollection("message");

			Document condition = new Document();

			GregorianCalendar calendar = new java.util.GregorianCalendar();
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date yesterday = calendar.getTime();
			condition.append("$gte", yesterday);
			Document order = new Document();
			order.append("date", -1);
			Document query = new Document();
			query.append("date", condition);
			MongoCursor<Document> cursor = messages.find(query).sort(order).iterator();
			List<JSONObject> msg = new ArrayList<JSONObject>();
			while (cursor.hasNext()) {
				msg.add(new JSONObject(cursor.next()));
			}

			try {
				result.append("messages", msg);
				return result;

			} catch (JSONException e) {

				e.printStackTrace();

			}
		} finally {
			if (mongo != null)
				mongo.close();
		}
		return result;
	}

	public static JSONObject getLatestMessages(int limit) {

		MongoClient mongo = Database.getMongoClient();
		JSONObject result = new JSONObject();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> messages = db.getCollection("message");
			Document query = new Document();
			query.append("date", -1);
			MongoCursor<Document> cursor = messages.find().sort(query).limit(limit).iterator();
			List<JSONObject> msg = new ArrayList<JSONObject>();
			while (cursor.hasNext()) {
				msg.add(new JSONObject(cursor.next()));
			}

			try {
				result.append("messages", msg);
				return result;

			} catch (JSONException e) {

				e.printStackTrace();

			}
		} finally {
			if (mongo != null)
				mongo.close();
		}
		return result;
	}

	public static void insertMessage(int id, String message, File image) {

		MongoClient mongo = Database.getMongoClient();
		try {
			
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> coll = db.getCollection("message");

			Document doc = new Document();
			doc.append("author_id", id);
			doc.append("text", message);
			doc.append("count", 0);
			GregorianCalendar calendar = new java.util.GregorianCalendar();
			Date date = calendar.getTime();
			doc.append("date", date);
			ArrayList<JSONObject> listc = new ArrayList<JSONObject>();
			doc.append("comments", listc);
			ArrayList<JSONObject> listl = new ArrayList<JSONObject>();
			doc.append("likes", listl);
			
			if(image!=null) {
				try {

				    byte[] data = Files.readAllBytes(image.toPath());
					//byte[] data64 = Base64.getEncoder().encode(data);	 
					//System.out.println(data64);
					doc.append("image",data);					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}else {
				doc.append("image","none");
			}
			
			coll.insertOne(doc);
			System.out.println(doc.get("_id").toString());

		} finally {
			if (mongo != null)
				mongo.close();
		}

	}

	public static void deleteMessage(String _id) {
		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> coll = db.getCollection("message");
			coll.deleteOne(new Document("_id", new ObjectId(_id)));
		} finally {
			if (mongo != null)
				mongo.close();
		}

	}

	public static boolean messageExists(String _id) {
		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> messages = db.getCollection("message");

			Document query = new Document();
			query.append("_id", new ObjectId(_id));
			MongoCursor<Document> cursor = messages.find(query).iterator();
			boolean result = cursor.hasNext();
			return result;
		} finally {
			if (mongo != null)
				mongo.close();
		}
	}

	public static void addComment(String _id, int idC, String comment) {

		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> coll = db.getCollection("message");

			Document find = new Document();
			Document proj = new Document();

			proj.append("count", 1);
			proj.append("_id", 0);

			find.append("_id", new ObjectId(_id));

			MongoCursor<Document> cursor = coll.find(find).projection(proj).iterator();
			int count = 0;
			if (cursor.hasNext()) {
				count = cursor.next().getInteger("count");
			}

			Document comnt = new Document();
			comnt.append("_idC", count);
			comnt.append("comment_author_id", idC);
			comnt.append("content", comment);
			GregorianCalendar calendar = new java.util.GregorianCalendar();
			Date now = calendar.getTime();
			comnt.append("date", now);

			Document modif = new Document();
			modif.append("comments", comnt);

			Document inc = new Document();
			inc.append("count", 1);

			Document push = new Document();
			push.append("$push", modif);

			push.append("$inc", inc);

			coll.updateOne(find, push);
		} finally {
			if (mongo != null)
				mongo.close();
	    }

	}

	public static JSONObject getComments(String _id) {
		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> messages = db.getCollection("message");

			Document query = new Document();
			Document proj = new Document();

			query.append("_id", new ObjectId(_id));
			proj.append("comments", 1);
			proj.append("_id", 0);

			MongoCursor<Document> cursor = messages.find(query).projection(proj).iterator();

			if (cursor.hasNext()) {
				JSONObject result = new JSONObject(cursor.next());

				ArrayList<Integer> ids = new ArrayList<Integer>();

				try {
					ArrayList<Document> comments = (ArrayList<Document>) result.get("comments");
					for (Document cmnt : comments) {
						ids.add(cmnt.getInteger("comment_author_id"));
					}
					System.out.println(ids);
					JSONObject authors = new JSONObject() ;
					if(ids.size()>0) authors = UserTools.getUsersInfo(ids);
					result.put("comment_authors", authors);
					return result;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return new JSONObject();
		} finally {
			if (mongo != null)
				mongo.close();
		}
	}

	public static void deleteComment(String _id, int _idC) {

		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
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
		} finally {
			if (mongo != null)
				mongo.close();
		}

	}

	public static boolean commentExists(String _id, int _idC) {

		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> messages = db.getCollection("message");

			Document query = new Document();
			Document proj = new Document();

			proj.append("comments", 1);
			proj.append("_id", 0);
			query.append("_id", new ObjectId(_id));

			MongoCursor<Document> cursor = messages.find(query).projection(proj).iterator();

			@SuppressWarnings("unchecked")
			List<Document> comments = (List<Document>) cursor.next().get("comments");
			for (Document cmnt : comments) {
				if (cmnt.getInteger("_idC") == _idC) {

					return true;
				}
			}
			return false;

		} finally {
			if (mongo != null)
				mongo.close();
		}

	}

	public static void addLike(String _id, int idL) {
		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> coll = db.getCollection("message");

			Document modif = new Document();
			modif.append("likes", idL);

			Document push = new Document();
			push.append("$push", modif);
			coll.updateOne(new Document("_id", new ObjectId(_id)), push);

		} finally {
			if (mongo != null)
				mongo.close();
		}
	}

	public static void unLike(String _id, int idL) {
		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> coll = db.getCollection("message");
			Document modif = new Document();
			modif.append("likes", idL);

			Document push = new Document();
			push.append("$pull", modif);

			coll.updateOne(new Document("_id", new ObjectId(_id)), push);
		} finally {
			if (mongo != null)
				mongo.close();
		}
	}

	public static boolean likeExists(String _id, int _idL) {
		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> messages = db.getCollection("message");

			Document query = new Document();
			Document proj = new Document();

			proj.append("likes", 1);
			proj.append("_id", 0);
			query.append("_id", new ObjectId(_id));

			MongoCursor<Document> cursor = messages.find(query).projection(proj).iterator();

			@SuppressWarnings("unchecked")
			List<Integer> likes = (List<Integer>) cursor.next().get("likes");
			for (int like : likes) {
				if (like == _idL) {
					mongo.close();

					return true;
				}
			}

			return false;
		} finally {
			if (mongo != null)
				mongo.close();
		}
	}

	public static JSONObject getLikes(String _id) {
		MongoClient mongo = Database.getMongoClient();
		try {
			MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
			MongoCollection<Document> messages = db.getCollection("message");

			Document query = new Document();
			Document proj = new Document();

			proj.append("likes", 1);
			proj.append("_id", 0);
			query.append("_id", new ObjectId(_id));

			MongoCursor<Document> cursor = messages.find(query).projection(proj).iterator();
			JSONObject result = new JSONObject(cursor.next());
			return result;
		} finally {
			if (mongo != null)
				mongo.close();
		}
	}

}
