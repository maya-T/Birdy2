package tests;



import org.json.JSONObject;

import services.UserServices;

public class UserServicesTest {

	public static void main(String[] args) {
		String fname="nawel";
		String lname="astouati";
		String email= "nast@gmail.com";
		String passwd="*****";
		String login="nawel99";
		JSONObject o;
		o=UserServices.createUser(fname, lname, email, passwd, login);
		System.out.println(o);

	}

}
