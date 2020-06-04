package essais;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.*;
public class Configurator extends ServerEndpointConfig.Configurator {
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest req, HandshakeResponse res) {
		
		String usr = req.getParameterMap().get("username").get(0);	
		System.out.println(usr);
		sec.getUserProperties().put("username", usr);
	}

}
