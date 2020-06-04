import java.sql.*;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import bd.Database;
import services.FriendServices;
import tools.FriendTools;
import tools.UserTools;
import tools.MessagesTools;


public class TestBd {

	public static void main(String[] args) {
//		JSONObject o=MessagesTools.getUserNFriendsMessages(11);
//		
//	    try {
//	    	JSONArray a = (JSONArray) o.get("messages");
//			System.out.println(o);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    JSONObject o2=UserTools.getUserInfo(11);
//	    ArrayList<Integer> a= new ArrayList<>();
//	    a.add(11);
//	    a.add(12);
//	    a.add(5);
//	    System.out.println(o2);
//	    System.out.println(UserTools.getUsersInfo(a));  
		
		
		FriendTools.insertFriend(34,33 );
		 JSONObject o2=FriendServices.listNotifications("90");
		System.out.println(o2);
	}
    
}
