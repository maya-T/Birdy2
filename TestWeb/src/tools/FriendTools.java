package tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import bd.Database;

public class FriendTools {

	public static boolean isFriend(String login1, String login2) {
		Connection c;
		try {
			c = Database.getMySQLConnection();
			String query="SELECT * FROM friends where (friend1='"+login1+"'and friend2='"+login2+"'";

			Statement stmnt = c.createStatement();
			ResultSet res = stmnt.executeQuery(query);
			return res.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static int insertFriend(String login1, String login2)
	{
		Connection c;
		try {
			c = Database.getMySQLConnection();
			String ajout="INSERT INTO friends (friend1,friend2) VALUES('"+login1+"','"+login2+"')";

			Statement stmnt = c.createStatement();
			int resultat =stmnt.executeUpdate(ajout);
			return resultat;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}
	public static int deleteFriend(String login1, String login2) //friend1 follow friend2 donc unfollow veut dire supprimer friend1,friend2 de la bd
	{
		Connection c;
		try {
			c = Database.getMySQLConnection();
			String ajout="DELETE FROM friends where (friend1='"+login1+"'and friend2='"+login2+"'";
			Statement stmnt = c.createStatement();
			int resultat =stmnt.executeUpdate(ajout);
			return resultat;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public static JSONObject listFriends(String login)
	{
		Connection c;
		try {   ArrayList<String> list=new ArrayList<String>();
		c = Database.getMySQLConnection();
		String query="SELECT * FROM friends where friend1='"+login+"'";

		Statement stmnt = c.createStatement();
		ResultSet res = stmnt.executeQuery(query);
		String amis="";
		while(res.next())
		{
			amis=res.getString("friend2");
			list.add(amis);

		}
		JSONObject o=new JSONObject();
		try {
			o.put(login, list);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return o;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}
