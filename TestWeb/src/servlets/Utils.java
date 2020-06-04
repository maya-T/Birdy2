package servlets;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Utils {
	
	public static JSONObject getJSONObject(BufferedReader reader) {
		String result = "";
    	try {
            String line="";
            StringBuilder build = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                build.append(line);
            }
            result = build.toString();
        } catch (IOException e) { 
        	e.printStackTrace();
        }
    	try {
			JSONObject obj = new JSONObject(result);
			return obj;
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return null;
	}

}
