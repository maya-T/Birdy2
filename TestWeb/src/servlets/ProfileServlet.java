package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import services.UserServices;


@SuppressWarnings("serial")
public class ProfileServlet extends HttpServlet {
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 4000 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file = null;

	public void init( ){
		// Get the file location where it would be stored.
		filePath = " /Users/Maya/Documents/apache-tomcat-9.0.30/webapps/temp"; 
	}


	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	



		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(req);
		res.setContentType("text/html");

		if( !isMultipart ) {
			System.out.println("not multipart");
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();

		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(new File("/Users/Maya/Documents/apache-tomcat-9.0.30/webapps/temp"));


		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax( maxFileSize );

		try { 

			List<FileItem> fileItems = upload.parseRequest(req); 
			Iterator<FileItem> i = fileItems.iterator();
			String login ="";
			while ( i.hasNext () ) {
				FileItem fi = (FileItem)i.next();
				System.out.println(fi);
				if ( ! fi.isFormField () ) {
					String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();
					System.out.println(fieldName+" "+fileName+" "+contentType+" "+isInMemory+" "+sizeInBytes); 
					file = new File( filePath +"/"+ fileName ) ; 
					fi.write( file ) ;
					
				}else {
				
                      if(fi.getFieldName().equals("login")) {
                    	  login = fi.getString();
        
                      }
				}
			}	
            JSONObject obj = UserServices.updatePicture(login , file);		
			PrintWriter w = res.getWriter();
			w.print(obj.toString());
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println(ex);
		}

	}
	
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws 
	IOException , ServletException{	

		String path = req.getPathInfo();
		StringTokenizer tokenizer = new StringTokenizer(path,"/");
		Map<String,Object> params = new HashMap<String,Object>();
		int nb = tokenizer.countTokens();
		if ( nb == 6 ) {
			while(tokenizer.hasMoreTokens()) {

				params.put(tokenizer.nextToken(), tokenizer.nextToken());

			}
            System.out.println(params.toString());
			String field = (String) params.get("field");
			String login = (String) params.get("login");
			String content = (String) params.get("content");
			JSONObject msg = new JSONObject();
			if(field.equals("bio")) {
				msg = UserServices.updateBio(login, content);
			}else if(field.equals("adress")) {
				System.out.println("hello");
				msg = UserServices.updateAdress(login, content);
			}else if(field.equals("website")) {
				msg = UserServices.updateWebsite(login, content);
			}
			PrintWriter w = res.getWriter();
			w.print(msg.toString());

		}else {
			//erreur parametre manquant
		}

	}

}
