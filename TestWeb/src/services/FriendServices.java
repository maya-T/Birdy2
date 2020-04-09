package services;



import org.json.JSONObject;

import tools.ErrorTools;
import tools.UserTools;
import tools.FriendTools;

public class FriendServices {
	 
	
	public static JSONObject listFriends(String login) {
		if(login == null ) {
		
			return ErrorTools.serviceRefused("Mauvais argument", 0) ;
			
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		return FriendTools.listFriends(login);
		
	}
	
	public static JSONObject addFriend(String login,String loginFriend) {
		
		if(login == null || loginFriend == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!UserTools.isUser(login) || UserTools.isUser(loginFriend)  ) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		
		if (FriendTools.isFriend(login,loginFriend)) {
			return ErrorTools.serviceRefused("Deja amis", 4);
		}
		FriendTools.insertFriend(login,loginFriend); 
		return ErrorTools.serviceAccepted();
	
	}
	
	public static JSONObject deleteFriend(String login, String loginFriend) {
		
		if(login == null || loginFriend == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!UserTools.isUser(login) || UserTools.isUser(loginFriend)  ) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		
		if (!FriendTools.isFriend(login,loginFriend)) {
			return ErrorTools.serviceRefused("Pas amis", 4);
		}
		FriendTools.deleteFriend(login,loginFriend); 
		return ErrorTools.serviceAccepted();
			  
	}

}
