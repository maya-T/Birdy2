package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;

import bd.Database;

public class UserTools {

	public static boolean isUser(String login) {
		Connection c = null ;
		try {
			c = Database.getMySQLConnection();
			String query = "SELECT * FROM users where login='"+ login+"'" ;
			Statement stm = c.createStatement();
			ResultSet res = stm.executeQuery(query);
			boolean result = res.next();

			return result;

		} catch (SQLException e) {

			e.printStackTrace();

		}finally {

			if(c!=null)
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}
		return false;	
	}

	public static boolean checkPasswd(String login, String passwd) {

		try {
			Connection c = Database.getMySQLConnection();
			String query = "SELECT password from users where login=" + "'"+login+"'";
			Statement stm = c.createStatement();
			ResultSet res = stm.executeQuery(query);
			String pwd = "";
			while(res.next()) {
				pwd=res.getString("password");
			}
			c.close();
			if(pwd.equals(passwd)) return true;
			return false;
		} catch (SQLException e) {

			e.printStackTrace();

		}
		return false;	
	}

	public static int userID(String login) {

		try {
			Connection c = Database.getMySQLConnection();
			String query = "SELECT id from users where login=" + "'"+login+"'";
			Statement stm = c.createStatement();
			ResultSet res = stm.executeQuery(query);
			int id=-1;
			while(res.next()) {
				id=res.getInt("id");
			}
			c.close();
			return id;

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return -1;
	}


	public static void insertUser(String fname, String lname, String email, String passwd, String login) {
		try {

			Connection c = Database.getMySQLConnection();
			String query = "INSERT INTO users ( login, fname,  lname,  email,  password )  values ('"+login+"','"+fname+"','"+lname +"','"+email+"','"+passwd+"')";
			Statement stm = c.createStatement();
			int res = stm.executeUpdate(query);
			c.close();
		} catch (SQLException e) {			
			e.printStackTrace();	
		}	
	}  
	public static JSONObject getUserInfo(int id) {
		JSONObject obj = new JSONObject();
		try {
			Connection c = Database.getMySQLConnection();
			String query = "SELECT   fname,lname, email, picture, adress,bio , website from users where id=" +id;
			Statement stm = c.createStatement();
			ResultSet res = stm.executeQuery(query);
			if(res.next()) {

				obj.put("firstName", res.getString("fname"));
				obj.put("lastName", res.getString("lname"));
				obj.put("email", res.getString("email"));
				obj.put("bio", res.getString("bio"));
				obj.put("adress", res.getString("adress"));
				obj.put("website", res.getString("website"));
				
				String img = res.getString("picture");
				if(!img.equals("none")) {
				
				File imgFile =  new File("/Users/Maya/Documents/apache-tomcat-9.0.30/webapps/profileImages/"+img);
				InputStream is = new FileInputStream(imgFile);
				int len = is.available();
				byte[] image = new byte[len];
				is.read(image, 0, len);
				byte [] image64 = Base64.getEncoder().encode(image);
				String imageString = org.apache.commons.codec.binary.StringUtils.newStringUtf8(image64);
				obj.put("picture", imageString);
				
				}else {
				 obj.put("picture", "none");
				}

			}
			c.close();
			return obj;

		} catch (SQLException | JSONException e) {		

			e.printStackTrace();	
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return obj;

	}
	public static JSONObject getUsersInfo(ArrayList<Integer> ids) {
		JSONObject objFinal = new JSONObject();
		if(ids!= null && ! ids.isEmpty()) {
			try {
				Connection c = Database.getMySQLConnection();
				StringBuilder q = new StringBuilder();
				q.append('(');
				for(Integer id: ids) {
					q.append(id+",");
				}
				q.deleteCharAt(q.lastIndexOf(","));
				q.append(")");

				String query = "SELECT id,login,fname,lname from users where id in" + q;
				Statement stm = c.createStatement();
				ResultSet res = stm.executeQuery(query);
				while(res.next()) {
					JSONObject obj = new JSONObject();
					obj.put("login", res.getString("login"));
					obj.put("firstName", res.getString("fname"));
					obj.put("lastName", res.getString("lname"));
					objFinal.put(res.getInt("id")+"", obj);

				}
				c.close();
				return objFinal;

			} catch (SQLException | JSONException e) {		

				e.printStackTrace();	
			}	
		}
		return objFinal;
	}

	public static int updateBio(int id, String bio) {
		Connection c;
		try {
			c = Database.getMySQLConnection();
			
			String query = "UPDATE users SET bio ='"+bio+"'"+"WHERE id='"+id+"'";
			Statement stm = c.createStatement();
			int res = stm.executeUpdate(query);
			c.close();
			return res;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
	
	public static int updateWebsite(int id, String website) {
		Connection c;
		try {
			c = Database.getMySQLConnection();
			
			String query = "UPDATE users SET website ='"+website+"'"+"WHERE id='"+id+"'";
			Statement stm = c.createStatement();
			int res = stm.executeUpdate(query);
			c.close();
			return res;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
	
	public static int updateAdress(int id, String adress) {
		Connection c;
		try {
			c = Database.getMySQLConnection();
			
			String query = "UPDATE users SET adress ='"+adress+"'"+"WHERE id='"+id+"'";
			Statement stm = c.createStatement();
			int res = stm.executeUpdate(query);
			c.close();
			return res;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}
	
	public static int updatePicture(int id, File picture) {
		Connection c;
		
		try {
			c = Database.getMySQLConnection();
			Statement stm = c.createStatement();
			
			String query1 = "SELECT picture FROM users WHERE id ='"+id+"'";
			ResultSet res1 = stm.executeQuery(query1);
			
			if(res1.next()) {
				String oldPicture = res1.getString("picture");
				if(oldPicture!=null) {
					File old = new File("/Users/Maya/Documents/apache-tomcat-9.0.30/webapps/temp/"+oldPicture);		
					if (old != null) old.delete();
				}
			}
			
			String ext = FilenameUtils.getExtension(picture.getName());
			String name = id+"."+ext; 			
			String query = "UPDATE users SET picture ='"+name+"'"+"WHERE id='"+id+"'";
			int res = stm.executeUpdate(query);
			
		    picture.renameTo(new File("/Users/Maya/Documents/apache-tomcat-9.0.30/webapps/profileImages/"+name)) ; 
			c.close();
			
			return res;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}
