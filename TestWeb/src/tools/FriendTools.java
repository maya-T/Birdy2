package tools;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import bd.Database;

// friend

public class FriendTools {

	public static boolean isFriend(int id1, int id2) { // id1 follow id2
		Connection c;
		try {
			c = Database.getMySQLConnection();
			String query = "SELECT * FROM friends where friend1='" + id1 + "'and friend2='" + id2 + "'";

			Statement stmnt = c.createStatement();
			ResultSet res = stmnt.executeQuery(query);
			boolean result = res.next();
			c.close();
			return  result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static int insertFriend(int id1, int id2) {
		Connection c;
		try {
			System.out.println("hellooo");
			c = Database.getMySQLConnection();
			String ajout = "INSERT INTO friends (friend1,friend2) VALUES('" + id1 + "','" + id2 + "')";
			
			java.util.Date javaDate = new java.util.Date();
			long javaTime = javaDate.getTime();
			java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);
			
			String ajout2 = "INSERT INTO notifs (follower,following,date) VALUES( '" + id1 + "','" + id2 + "','"+sqlTimestamp+"')";
			
			Statement stmnt = c.createStatement();
			int resultat = stmnt.executeUpdate(ajout);
			int resultat2 = stmnt.executeUpdate(ajout2);
			System.out.println(resultat2);
			c.close();
			return resultat+resultat2;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public static int deleteFriend(int id1, int id2) // friend1 follow friend2 donc unfollow veut dire supprimer
														// friend1,friend2 de la bd
	{
		Connection c;
		try {
			c = Database.getMySQLConnection();
			String ajout = "DELETE FROM friends where friend1 =" + id1 + " and friend2 =" + id2;
			Statement stmnt = c.createStatement();
			int resultat = stmnt.executeUpdate(ajout);
			System.out.println(resultat);
			c.close();
			return resultat;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public static JSONObject listFollowing(int id) {
		Connection c;
		try {
			c = Database.getMySQLConnection();
			String query = "SELECT * FROM friends,users where id=friend2 and friend1='" + id + "'";

			Statement stmnt = c.createStatement();
			ResultSet res = stmnt.executeQuery(query);
			ArrayList<JSONObject> friends = new ArrayList<>();
			JSONObject o = new JSONObject();
			try {
			while (res.next()) {
				//id | login    | fname   | lname    | email
				JSONObject obj = new JSONObject();
				obj.put("id", res.getInt("id"));
				obj.put("login", res.getString("login"));
				obj.put("fname", res.getString("fname"));
				obj.put("lname", res.getString("lname"));
				obj.put("email", res.getString("email"));
				friends.add(obj);

			}
				o.put("following", friends);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			c.close();
			return o;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	public static JSONObject listFollowers(int id) {
		Connection c;
		try {
			c = Database.getMySQLConnection();
			String query = "SELECT * FROM friends,users where id=friend1 and friend2='" + id + "'";

			Statement stmnt = c.createStatement();
			ResultSet res = stmnt.executeQuery(query);
			ArrayList<JSONObject> friends = new ArrayList<>();
			JSONObject o = new JSONObject();
			try {
			while (res.next()) {
				//id | login    | fname   | lname    | email
				JSONObject obj = new JSONObject();
				obj.put("id", res.getInt("id"));
				obj.put("login", res.getString("login"));
				obj.put("fname", res.getString("fname"));
				obj.put("lname", res.getString("lname"));
				obj.put("email", res.getString("email"));
				friends.add(obj);

			}
				o.put("followers", friends);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			c.close();
			return o;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static JSONObject listNotifications(int id) {
		Connection c;
		JSONObject o = new JSONObject();
		try {
			c = Database.getMySQLConnection();
			String query = "SELECT login, seen FROM notifs,users where id=follower and following=" + id + " order by date desc" ;

			Statement stmnt = c.createStatement();
			ResultSet res = stmnt.executeQuery(query);
			ArrayList<String> seen = new ArrayList<>();
			ArrayList<String> unseen = new ArrayList<>();
			
			try {
				while (res.next()) {
					int isseen = res.getInt("seen");
					String friend = res.getString("login");
					if(isseen == 0 ) unseen.add(friend);
					else seen.add(friend);
					
					
					
	
				}
				o.put("seen", seen);
				o.put("unseen", unseen);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			c.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;

	}
	public static int setNotificationsToSeen(int id) {
		Connection c;
		int res=0;
		try {
			c = Database.getMySQLConnection();
			String query = "UPDATE notifs SET seen = 1 WHERE following='"+id+"'";
			Statement stm = c.createStatement();
			res = stm.executeUpdate(query);
			c.close();
			
		} catch (SQLException e) {
			e.printStackTrace();

	}
		return res;
	}
	public static JSONObject listFollowingIds(int id)
	{
		Connection c;
		try {   ArrayList<Integer> list=new ArrayList<Integer>();
		c = Database.getMySQLConnection();
		String query="SELECT * FROM friends where friend1='"+id+"'";

		Statement stmnt = c.createStatement();
		ResultSet res = stmnt.executeQuery(query);
		int following;
		while(res.next())
		{
			following=res.getInt("friend2");
			list.add(following);

		}
		JSONObject o=new JSONObject();
		try {
			o.put("following", list);
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
	
	


//	public static JSONObject numberFollowing(int id) {
//		Connection c;
//		try {
//			c = Database.getMySQLConnection();
//			String query = "SELECT count(*) as nb FROM friends where friend1 ='" + id + "'";
//
//			Statement stmnt = c.createStatement();
//			ResultSet res = stmnt.executeQuery(query);
//			int nb = 0;
//			if (res.next()) {
//				 nb = res.getInt("nb");
//
//			}
//			JSONObject o = new JSONObject();
//			try {
//				o.put("numberFollowing", nb);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			c.close();
//			return o;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static JSONObject numberFollowers(int id) {
//		Connection c;
//		try {
//			c = Database.getMySQLConnection();
//			String query = "SELECT count(*) as nb FROM friends where friend1 ='" + id + "'";
//
//			Statement stmnt = c.createStatement();
//			ResultSet res = stmnt.executeQuery(query);
//			int nb = 0;
//			if (res.next()) {
//				 nb = res.getInt("nb");
//
//			}
//			JSONObject o = new JSONObject();
//			try {
//				o.put("numberFollowers", nb);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			c.close();
//			return o;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;	}
	
}
