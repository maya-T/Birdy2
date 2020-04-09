package services;



import org.json.JSONObject;

import tools.ErrorTools;
import tools.UserTools;
import tools.MessagesTools;

public class MessageServices {
	
	public static JSONObject listMessages() {
		
		return MessagesTools.getMessages();
		
	}
	
	public static JSONObject listMessages(String login) {
		if(login == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		return MessagesTools.getMessages(id);
		
	}
	
	public static JSONObject listMessages(String login,String filter) {
		if(login == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if( !UserTools.isUser(login) ) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		return MessagesTools.getMessages(id,filter);
		
	}
	
	public static JSONObject addMessage(String login,String message) {
		
		if(login == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		MessagesTools.insertMessage(id,message);
		return ErrorTools.serviceAccepted();
	
	}
	
	public static JSONObject deleteMessage(String login, String message) {
		
		if(login == null || message == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		
		int id = UserTools.userID(login);
		//MessagesTools.deleteMessage(id,message);
		return ErrorTools.serviceAccepted();
			  
	}
	
	public static JSONObject listComments(String _id) {
		if(_id == null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}
		if(! MessagesTools.messageExists(_id)) {
			return ErrorTools.serviceRefused("Message inexistant", 8);
		}
		return MessagesTools.getComments(_id);
		
	}
	

}
