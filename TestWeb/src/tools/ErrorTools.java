package tools;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorTools {
  public static JSONObject serviceRefused(String message,int codeErreur) {
	  JSONObject o=new JSONObject();
	 
	try {
		  o.put("message",message);
		  o.put("code",codeErreur);
	} catch (JSONException e) {
		
		e.printStackTrace();
	}
	  return o;
  }
  public static JSONObject serviceAccepted() {
	  JSONObject o=new JSONObject();
	  return o;
  }
  
  public static JSONObject serviceAccepted(String message,Object objet) {
	  JSONObject o=new JSONObject();
	  try {
		o.put(message,objet);
	} catch (JSONException e) {
		
		e.printStackTrace();
	}  
	  return o;
  }
}
