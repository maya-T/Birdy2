package services;



import java.io.File;

import org.json.JSONObject;

import tools.ErrorTools;
import tools.SessionTools;
import tools.UserTools;
public class UserServices {

	public  static JSONObject createUser(String fname, String lname, String email, String passwd, String login) {

		if(fname==null || lname==null || email==null || passwd==null || login==null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}
		if(UserTools.isUser(login)) {
			return ErrorTools.serviceRefused("Login existant", 3);
		}

		UserTools.insertUser(fname,lname,email,passwd,login);
		return ErrorTools.serviceAccepted("message","utilisateur cree");

	}

	public static JSONObject login (String login,String passwd) {

		if(login==null || passwd==null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
		}
		try {

			if(!UserTools.isUser(login)) {
				return ErrorTools.serviceRefused("Login inexistant", 1);
			}
			if(!UserTools.checkPasswd(login,passwd)) {
				return ErrorTools.serviceRefused("Mot de passe incorrect", 2);
			}
			int id=UserTools.userID(login);
			String key=SessionTools.insertSession(id);
			JSONObject info = UserTools.getUserInfo(id);
			JSONObject result = new JSONObject ();
			result.put("key",key);
			result.put("info", info );
			return ErrorTools.serviceAccepted("loginInfo", result);
		}catch(Exception e) {
			print("erreur");
		}
		return null;

	}
	public static JSONObject logout(String login){
			if(login==null) {
				return ErrorTools.serviceRefused("Mauvais argument", 0);
			}
			if(!UserTools.isUser(login)) {
				return ErrorTools.serviceRefused("Login inexistant", 1);
			}
			int id=UserTools.userID(login);
		    SessionTools.deleteSession(id)   ;
		    return ErrorTools.serviceAccepted();
	}
	
	public static JSONObject updateBio(String login, String bio) {
		if( login==null || bio==null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
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
		UserTools.updateBio(id, bio);
		return ErrorTools.serviceAccepted("message","bio modifiee");
	}
	
	public static JSONObject updateAdress(String login, String adress) {
		if( login==null || adress==null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
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
		UserTools.updateAdress(id, adress);
		return ErrorTools.serviceAccepted("message","adresse modifiee");
	}
	
	public static JSONObject updateWebsite(String login, String website) {
		if( login==null || website==null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
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
		UserTools.updateWebsite(id, website);
		return ErrorTools.serviceAccepted("message","website modifie");
	}

	public static void print(String msg) {
		System.out.println(msg);
	}

	public static JSONObject updatePicture(String login, File file) {
		
		if( login==null) {
			return ErrorTools.serviceRefused("Mauvais argument", 0);
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
		UserTools.updatePicture(id, file);
		return ErrorTools.serviceAccepted("message","photo modifiee");
	}

}
