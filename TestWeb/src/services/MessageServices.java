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
		if(login == null || filter == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if( !UserTools.isUser(login) ) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		return MessagesTools.getMessages(id,filter);
		
	}
	
	public static JSONObject listMessages(int limit) {
		
		return MessagesTools.getMessages(limit);
		
	}
	
	public static JSONObject addMessage(String login,String message) {
		
		if(login == null || message == null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int id = UserTools.userID(login);
		MessagesTools.insertMessage(id,message);
		return ErrorTools.serviceAccepted();
	
	}
	
	public static JSONObject deleteMessage(String _id) {
		
		if(_id == null ) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}

		if(!MessagesTools.messageExists(_id)) {
			return ErrorTools.serviceRefused("Message inexistant", 9);
		}
		
		MessagesTools.deleteMessage(_id);
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
	
	public static JSONObject addComment(String _id, String login, String comment) {
		
		if(login == null || _id == null || comment == null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}
		if(!MessagesTools.messageExists(_id)) {
			return ErrorTools.serviceRefused("Message inexistant", 9);
		}
		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		int idC = UserTools.userID(login);
		MessagesTools.addComment(_id, idC, comment);
		return ErrorTools.serviceAccepted();	
	}
	
	public static JSONObject deleteComment(String _id, int _idC) {
			
			if(_id == null) {
				return ErrorTools.serviceRefused("Mauvais argument", 0);
			}
			if(!MessagesTools.messageExists(_id)) {
				return ErrorTools.serviceRefused("Message inexistant", 9);
			}
			if(!MessagesTools.commentExists(_id, _idC)) {
				return ErrorTools.serviceRefused("Message inexistant", 9);
			}
			MessagesTools.deleteComment(_id, _idC);
			return ErrorTools.serviceAccepted();	
	}
	
	public static JSONObject like(String _id, String login) {
		if(_id == null || login == null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}
		if(!UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login inexistant", 1);
		}
		if(!MessagesTools.messageExists(_id)) {
			return ErrorTools.serviceRefused("Message inexistant", 9);
		}
		int idL = UserTools.userID(login);
		MessagesTools.addLike(_id, idL);
		return ErrorTools.serviceAccepted();	
	}
	
	public static JSONObject unlike(String _id, int idL) {
		if(_id == null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}
		if(!MessagesTools.messageExists(_id)) {
			return ErrorTools.serviceRefused("Message inexistant", 9);
		}
		if(!MessagesTools.likeExists(_id, idL)) {
			return ErrorTools.serviceRefused("Like inexistant", 10);
		}
		MessagesTools.unLike(_id, idL);
		return ErrorTools.serviceAccepted();	
	}
	
	public static JSONObject listLikes(String _id) {
		
		if(_id == null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}
		if(! MessagesTools.messageExists(_id)) {
			return ErrorTools.serviceRefused("Message inexistant", 8);
		}
		return MessagesTools.getLikes(_id);	
	}
}
