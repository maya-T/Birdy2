package tools;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import bd.Database;

public class UserTools {

	public static boolean isUser(String login) {
		
		try {
			Connection c = Database.getMySQLConnection();
			String query = "SELECT * FROM users where login='"+ login+"'" ;
			Statement stm = c.createStatement();
			ResultSet res = stm.executeQuery(query);
			return res.next();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
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
				
		} catch (SQLException e) {			
			e.printStackTrace();	
		}	
	}  
}
