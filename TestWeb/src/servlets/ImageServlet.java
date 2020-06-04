package servlets;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;

import bd.DBStatic;
import bd.Database;
import services.MessageServices;

import org.apache.commons.codec.binary.Base64InputStream;

@SuppressWarnings("serial")

/////// not used 
public class ImageServlet extends HttpServlet {
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 4000 * 1024;
   private int maxMemSize = 4 * 1024;
   private File file ;

   public void init( ){
      // Get the file location where it would be stored.
      filePath = getServletContext().getInitParameter("file-upload"); 
   }
   
   public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {
      System.out.println(filePath);
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
   
      if( !isMultipart ) {
         System.out.println("not multipart");
         return;
      }
  
      DiskFileItemFactory factory = new DiskFileItemFactory();
   
      factory.setSizeThreshold(maxMemSize);
      factory.setRepository(new File("/Users/Maya/Documents/apache-tomcat-9.0.30/webapps/images"));

      
      ServletFileUpload upload = new ServletFileUpload(factory);
      upload.setSizeMax( maxFileSize );

      try { 
         
         List<FileItem> fileItems = upload.parseRequest(request);
	
         
         Iterator<FileItem> i = fileItems.iterator();
   
         while ( i.hasNext () ) {
            FileItem fi = (FileItem)i.next();
            System.out.println(fi);
            if ( !fi.isFormField () ) {
              
               String fieldName = fi.getFieldName();
               String fileName = fi.getName();
               String contentType = fi.getContentType();
               boolean isInMemory = fi.isInMemory();
               long sizeInBytes = fi.getSize();
               System.out.println(fieldName+" "+fileName+" "+contentType+isInMemory+sizeInBytes);     
               file = new File( filePath +"/"+ fileName) ; 
               fi.write( file ) ;
               MessageServices.addMessage("maya@", "izzan",file);
               //file.delete();
            }
         }
         } catch(Exception ex) {
        	 ex.printStackTrace();
             System.out.println(ex);
         }
      }
      
   @SuppressWarnings("deprecation")
@Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws IOException {

	   
	   MongoClient mongo = Database.getMongoClient();
       MongoDatabase db = mongo.getDatabase(DBStatic.mongo_bd);
       MongoCollection<Document> coll = db.getCollection("message");
      
       
       OutputStream os = response.getOutputStream();
       Document query = new Document();
	   query.append("_id", new ObjectId("5e9afbc9315f9b354ebd06d0"));
	   
	   MongoCursor<Document> cursor = coll.find(query).iterator();
	   
	   org.bson.types.Binary result = (org.bson.types.Binary)cursor.next().get("image");
	   byte [] byte64 = Base64.getEncoder().encode(result.getData());
	   response.setContentType("image/png");
	   os.write(byte64);
	   mongo.close();
          
   }
     
   }
