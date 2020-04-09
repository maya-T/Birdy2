package tools;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bd.Database;

public class SessionTools {

	public static String insertSession(int id) {

		try {
			String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i<32; i++) {
				int c = (int) (Math.random()*ALPHA_NUMERIC_STRING.length());
				builder.append(ALPHA_NUMERIC_STRING.charAt(c));
			}

			String key = builder.toString();

			Connection c = Database.getMySQLConnection();
			java.util.Date javaDate = new java.util.Date();
			long javaTime = javaDate.getTime();
			java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);
			String query = "INSERT INTO sessions values('"+ id +"','"+key+"','"+ sqlTimestamp +"')";
			Statement stm = c.createStatement();
			int res = stm.executeUpdate(query);

			return key;

		}catch(SQLException e ) {

			e.printStackTrace();
		}

		return "";

	}

	public static int deleteSession(int id) {
		Connection c;
		try {
			c = Database.getMySQLConnection();
			String ajout="DELETE FROM sessions where id='"+id+"'";
			Statement stmnt = c.createStatement();
			int resultat =stmnt.executeUpdate(ajout);
			return resultat;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public static boolean activeSession(int id)
	{   Connection c;
	try {
		c = Database.getMySQLConnection();
		String query = "select date from sessions where id='"+id+"'";
		Statement stm = c.createStatement();
		ResultSet res = stm.executeQuery(query);
		Date sqlTimestamp=null;
		if(res.next()) {
			sqlTimestamp = res.getDate("date");
		}
		System.out.println(sqlTimestamp);
		java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();

		int nbMinutes=30;
		long timeSession=sqlTimestamp.getTime();
		System.out.println(timeSession);
		System.out.println(javaTime);
		long somme=javaTime-nbMinutes*60*1000;
		System.out.println(somme);
		if(timeSession>=somme) return true;
		return false;

	}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
	}

}