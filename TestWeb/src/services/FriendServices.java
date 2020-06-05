package services;



import org.json.JSONException;
import org.json.JSONObject;

import tools.ErrorTools;
import tools.UserTools;
import tools.FriendTools;
import tools.SessionTools;

public class FriendServices {
	 
	
	public static JSONObject listFollowing(String login) {
		if(login == null ) {
		
			return ErrorTools.serviceRefused("Mauvais argument", 0) ;
			
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		
		
		int id = UserTools.userID(login);
		
		if(!SessionTools.activeSession(id)) {
			UserServices.logout(login);
			SessionTools.deleteSession(id);
			return ErrorTools.serviceRefused("Session expirée", 5) ;
		}
		
		return FriendTools.listFollowing(id);
		
	}
	public static JSONObject listFollowers(String login) {
		if(login == null ) {
		
			return ErrorTools.serviceRefused("Mauvais argument", 0) ;
			
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		
		if(!SessionTools.activeSession(id)) {
			UserServices.logout(login);
			SessionTools.deleteSession(id);
			return ErrorTools.serviceRefused("Session expirée", 5) ;
		}
		return FriendTools.listFollowers(id);
		
	}
	
	public static JSONObject listNotifications(String login) {
		if(login == null ) {
		
			return ErrorTools.serviceRefused("Mauvais argument", 0) ;
			
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		
		if(!SessionTools.activeSession(id)) {
			UserServices.logout(login);
			SessionTools.deleteSession(id);
			return ErrorTools.serviceRefused("Session expirée", 5) ;
		}
		return FriendTools.listNotifications(id);
		
	}
	
public static JSONObject setNotificationsToSeen(String login) {
		
		if(login == null ) {
			
			return ErrorTools.serviceRefused("Mauvais argument", 0) ;
			
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		
		if(!SessionTools.activeSession(id)) {
			UserServices.logout(login);
			SessionTools.deleteSession(id);
			return ErrorTools.serviceRefused("Session expirée", 5) ;
		}
		FriendTools.setNotificationsToSeen(id);
		return ErrorTools.serviceAccepted();
	}
	
	public static JSONObject listFollowersNfollowing(String login) {
		if(login == null ) {
			
			return ErrorTools.serviceRefused("Mauvais argument", 0) ;
			
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		
		if(!SessionTools.activeSession(id)) {
			UserServices.logout(login);
			SessionTools.deleteSession(id);
			return ErrorTools.serviceRefused("Session expirée", 5) ;
		}
		JSONObject flwrs = FriendTools.listFollowers(id);
		JSONObject flwng = FriendTools.listFollowing(id);
		JSONObject res = new JSONObject();
		try {
			res.put("followers", flwrs.get("followers"));
			res.put("following", flwng.get("following"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public static JSONObject addFriend(String login,String loginFriend) {
		
		if(login == null || loginFriend == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!UserTools.isUser(login) || !UserTools.isUser(loginFriend)  ) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		
		if(!SessionTools.activeSession(id)) {
			UserServices.logout(login);
			SessionTools.deleteSession(id);
			return ErrorTools.serviceRefused("Session expirée", 5) ;
		}
		int id2 = UserTools.userID(loginFriend);
		if (FriendTools.isFriend(id,id2)) {
			return ErrorTools.serviceRefused("Deja amis", 4);
		}
		FriendTools.insertFriend(id,id2); 
		return ErrorTools.serviceAccepted();
	
	}
	
	public static JSONObject deleteFriend(String login, String loginFriend) {
		
		if(login == null || loginFriend == null ) {
			
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!UserTools.isUser(login) || ! UserTools.isUser(loginFriend)  ) {
			
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		
		if(!SessionTools.activeSession(id)) {
			UserServices.logout(login);
			SessionTools.deleteSession(id);
			return ErrorTools.serviceRefused("Session expirée", 5) ;
		}
		int id2 = UserTools.userID(loginFriend);
		if (!FriendTools.isFriend(id,id2)) {
			
			return ErrorTools.serviceRefused("Pas amis", 4);
		}
		FriendTools.deleteFriend(id,id2); 
		return ErrorTools.serviceAccepted();
			  
	}
	
	public static JSONObject getFriendInfo(String login, String otherLogin) {
		
		if(login==null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}
		try {

			if(!UserTools.isUser(login) || !UserTools.isUser(otherLogin)) {
				return ErrorTools.serviceRefused("Login inexistant", 1);
			}
			
			int id=UserTools.userID(login);	
			
			if(!SessionTools.activeSession(id)) {
				UserServices.logout(login);
				SessionTools.deleteSession(id);
				return ErrorTools.serviceRefused("Session expirée", 5) ;
			}
			int otherId=UserTools.userID(otherLogin);	
			JSONObject info = UserTools.getUserInfo(otherId);	
			boolean isFriend = FriendTools.isFriend(id, otherId);
			info.put("following", isFriend);
			return info;
		}catch(Exception e) {
			System.out.println("erreur");
		}
		return null;
		
	}
	
	
	
	
//	public static JSONObject getNumberFollowing(String login) {
//		if(login == null ) {
//			return ErrorTools.serviceRefused("Mauvais argument", 0);
//		}
//
//		if(!UserTools.isUser(login)) {
//			return ErrorTools.serviceRefused("Login inexistant", 1);
//		}
//		int id = UserTools.userID(login);
//		
//		return FriendTools.numberFollowing(id);
//		
//	}
//	public static JSONObject getNumberFollowers(String login) {
//		if(login == null ) {
//			return ErrorTools.serviceRefused("Mauvais argument", 0);
//		}
//
//		if(!UserTools.isUser(login)) {
//			return ErrorTools.serviceRefused("Login inexistant", 1);
//		}
//		int id = UserTools.userID(login);
//		
//		return FriendTools.numberFollowers(id);
//		
//	}
  
}
