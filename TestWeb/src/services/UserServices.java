package services;



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
			return ErrorTools.serviceAccepted("key", key);
		}catch(Exception e) {
			print("erreur");
		}
		return null;

	}
//	public static JSONObject logout(String login){
//			String id=UserTools.userID(login);
//			//pour l'instant on n'a qu'une seule session ouverte a la fois, si cd n'est pas le cas passer key, puisque un id peut avoir plusieurs key
//			UserTools.deleteSession(id);
//	}

	public static void print(String msg) {
		System.out.println(msg);
	}

}
