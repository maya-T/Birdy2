package mapReduce;

import java.util.*;


import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.MapReduceAction;

import bd.DBStatic;
import bd.Database;
import tools.UserTools;

public class MapReduce {
	
//	private static String mapDF = " function () { "      +
//			"var words = this.text.match(/\\w+/g);"  +
//	        "if (words === null) {"   +
//	               "return;"          +
//	        "}"+
//	        "for (var i = 0; i < words.length; i++) {"+
//	             "emit(words[i], { count : 1 });"     +
//            "}"+
//   "}"    ;
//	
//	private static String reduceDF = 	"function (key, values) {"   +
//            "var total = 0;"         +
//     "for (var i = 0; i < values.length; i++) {"+
//	   "   total += values[i].count;"             +
//     "}"                                        +
//     "return { count : total };"+
//    "}";
	
	private static String mapTF = " function () { "      +
			"var words = this.text.toLowerCase().match(/\\w+/g);"  +
			"var tf=[];"+
	        "if (words === null) {"   +
	               "return;"          +
	        "}"+ 
	         "for (i in words){"+
	           "if (tf[words[i]]==null) {tf[words[i]]=1;}"+
	           "else {tf[words[i]]++;}"+
	         "}"+
	        "for (w in tf) {"+
	             "emit(this._id, { word : w, count: tf[w]});"     +
            "}"+
   "}"    ;
	
	static String reduceTF="function (key, value){	" +
			  "return {tfs : value} ;" +
			"}";
	
	
	static String mapIDF = " function () { "      +
			"var words = this.text.toLowerCase().match(/\\w+/g);"  +
	        "if (words === null) {"   +
	               "return;"          +
	        "}"+
	        "for (var i = 0; i < words.length; i++) {"+
	             "emit(words[i],{document : this._id, position: i});"     +
            "}"+
   "}"    ;
			
//   static String reduceIDF = "function (key, values) {"   +
//        "var result = [];"         +
//		"for (var i = 0; i < values.length; i++) {"+
//		     "result =result.concat(values[i]);"             +
//		"}"                                        +
//        "return { at : result };"+
//   "}";
	
  static String reduceIDF = "function (key, values) {"   +
                                      
  "return { at : values };"+
"}";

	public static void indexTF() {
		
	   
	   MongoClient mongo = Database.getMongoClient();  
	   MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
	   
	   MongoCollection<Document> coll = db.getCollection("message");
	   
	   MapReduceIterable<Document> out = coll.mapReduce(mapTF, reduceTF);
	   out.collectionName("tfIndex");
	   out.action(MapReduceAction.REPLACE);
	   out.toCollection();
	   
	   if(mongo!= null)  mongo.close();
	   
//	   for ( Document  obj : out ) {
//		   System.out.println( obj );
//	   }  
	   
	}
	
//	public static void indexDF() {
//		
//		   
//		   MongoClient mongo = Database.getMongoClient();  
//		   MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
//		   
//		   MongoCollection<Document> coll = db.getCollection("message");
//		   
//		   MapReduceIterable<Document> out = coll.mapReduce(mapDF, reduceDF);
//		   
//		   out.collectionName("dfIndex");
//		   out.action(MapReduceAction.REPLACE);
//		   out.toCollection();
//		   
//		   if(mongo!= null)  mongo.close();
//		   
////		   for ( Document  obj : out ) {
////			   System.out.println( obj );
////		   }    
//		}
	
	public static void indexReverse() {
		
		
	   	   
	   MongoClient mongo = Database.getMongoClient();  
	   MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
	   
	   MongoCollection<Document> coll = db.getCollection("message");
	   
	   MapReduceIterable<Document> out = coll.mapReduce(mapIDF, reduceIDF);
	   
	   out.collectionName("reverseIndex");
	   out.action(MapReduceAction.REPLACE);
	   out.toCollection();
	   
	   if(mongo!= null)  mongo.close();
	   
//	   for ( Document  obj : out ) {
//		   System.out.println( obj );
//	   }      
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static ArrayList<ObjectId>  rsv(String query) {
		indexReverse();
		indexTF();
		
		MongoClient mongo = Database.getMongoClient();  
		MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd); 
		
		MongoCollection<Document> messages = db.getCollection("message");
		long numerateur = messages.countDocuments();
		
		
		StringTokenizer tokenizer = new StringTokenizer(query.toLowerCase()," ");
		
		ArrayList<String> str = new ArrayList<String>();
		
		
		while(tokenizer.hasMoreTokens()) {
			
			String word = tokenizer.nextToken();
			str.add(word);		
		}
		
		
		MongoCollection<Document> reverse_index = db.getCollection("reverseIndex");

		Document  condition = new Document();
		Document  in = new Document();
		in.append("$in", str);
		condition.append("_id",in);
		MongoCursor<Document> cursor = reverse_index.find(condition).iterator();
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		while(cursor.hasNext()) {
			Document next =  cursor.next();
			Object at =((Document)next.get("value")).get("at");
			if (at instanceof ArrayList<?>) sb2.append(((ArrayList<Document>) at).size()+" ");
			else sb2.append(1+" ");
			sb.append(next.getString("_id")+" ");				
		}		
		String denoms = sb2.toString();
		String query2 = sb.toString();
	    System.out.println(denoms+" "+query2);
		String map = " function () { "      +
		
		        "var text = \""+query2+"\";"+
				"var words = text.match(/\\w+/g);"  +
		        "var text2= \""+denoms+"\";"+
		        "var denoms = text2.match(/\\w+/g);"  +
		          
		        "if (words === null || denoms === null) {"   +
		               "return;"          +
		        "}"+
		        "var tfidf=[];"+
		        "var D = parseInt(\""+numerateur+"\", 10);"+
		        "for(i in words) {" + 
		          "tfidf[words[i]] = Math.log(D/parseInt(denoms[i])); "+
		        "}"+
		        "var tfs = this.value;"+
		        "var querytfs=[];"+
		        "var z= words.length;"+
		        "for (var i = 0; i < words.length; i++ ){"+
		             "if(tfs.tfs==null){"+
		                " var word = tfs.word;"+
			            " if (word===words[i]) {querytfs[word]=tfs.count;}"+
		                " else {querytfs[words[i]]=0;}"+
		              "}else{"+
				        "var tfsArray=tfs.tfs;"+
				        "for (j in tfsArray){" +
				          " var word = tfsArray[j].word;"+
				          " if (word===words[i]) {querytfs[word]=tfsArray[i].count;}"+
				        "}"+
				        "if(querytfs[words[i]]==null) {querytfs[words[i]] = 0;}"+
				     "}"+
		             "var res = querytfs[words[i]]*tfidf[words[i]];"+
		             "emit(this._id, {rsv: res});"+
	            "}"+
	    "}";
//		
		String reduce = 	"function (key, values) {"   +
	            "var total = 0;"         +
	            "for (var i = 0; i < values.length; i++) {"+
	       	   "   total += values[i].rsv;"             +
	            "}"                                        +
	            "return { rsv : total };"+
	           "}";
//		String reduce2 = "function (key, values) {"   +                                           
//	            "return { res : values };"+
//	           "}";
		
		 MongoCollection<Document> coll = db.getCollection("tfIndex");
		   
		 MapReduceIterable<Document> out = coll.mapReduce(map, reduce);
		 
		  for ( Document  obj : out ) {
			   System.out.println( obj );
		  }   
		   
		   out.collectionName("rsv");
		   out.action(MapReduceAction.REPLACE);
		   out.toCollection();
		   
		   MongoCollection<Document> rsv = db.getCollection("rsv");
		   
		   
		   Document  order = new Document();   
		   order.append("value.rsv", -1);
		   
		   Document  gt = new Document();   
		   gt.append("$gt", 0);
		   Document  find = new Document();   
		   find.append("value.rsv", gt);
		   
		   MongoCursor<Document> cursor2 = rsv.find(find).sort(order).iterator();
		   
		   ArrayList<ObjectId> ids = new ArrayList<ObjectId>();
		   while(cursor2.hasNext()) {
			   Document next = cursor2.next();
			   ids.add(next.getObjectId("_id"));
			   System.out.println(next.get("_id").toString());
		   }
		     
		   if(mongo!= null)  mongo.close();
		   return ids;
				    
	}
	public static JSONObject getMessages(String query) {
		ArrayList<JSONObject> result = new ArrayList<JSONObject>();
		MongoClient mongo = Database.getMongoClient();  
		MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd); 
		
		ArrayList<ObjectId> ids = rsv(query); 
		
		MongoCollection<Document> messages = db.getCollection("message");
		Document  condition = new Document();
		Document  in = new Document();
		in.append("$in", ids);
		condition.append("_id",in);
		MongoCursor<Document> cursor = messages.find(condition).iterator();
		
		Map<String, Document > msgs = new HashMap<>();
		ArrayList<Integer> users = new ArrayList<Integer>();
		while(cursor.hasNext()) {
			Document next = cursor.next();
			Object image = next.get("image");
			if(image instanceof org.bson.types.Binary) {
				next.remove("image");
				byte [] image64 = Base64.getEncoder().encode(((org.bson.types.Binary)image).getData());
				String imageString = org.apache.commons.codec.binary.StringUtils.newStringUtf8(image64);
				next.put("image", imageString);
			}
			msgs.put(next.getObjectId("_id").toString(), next);
			users.add(next.getInteger("author_id"));
			
		}
		for(ObjectId id: ids) {
				result.add(new JSONObject(msgs.get(id.toString())));
		}
		System.out.println(users);
		JSONObject info = UserTools.getUsersInfo(users);
		
		JSONObject  o = new JSONObject();
		try {
			o.put("messages", result);
			o.put("authors", info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return o;
		
	} 
	public static void rem(String coll) {
		MongoClient mongo = Database.getMongoClient();  
		MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd); 
		
		MongoCollection<Document> collection = db.getCollection(coll);
	    if (collection!=null) collection.drop();
	    if (mongo!=null)mongo.close();
	}
	
	

}
