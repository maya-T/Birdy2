package essais;
import java.util.Collections;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.websocket.EndpointConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.*;

import org.json.JSONException;
import org.json.JSONObject;
@ServerEndpoint(value = "/EndPoint",configurator = Configurator.class)
 
public class EndPoint {
	
    static Set<Session> users =  Collections.synchronizedSet(new HashSet<Session>());
    @OnOpen
    public void handleOpen(EndpointConfig endpointConfig, Session session ) {
    	session.getUserProperties().put("username", endpointConfig.getUserProperties().get("username"));
    	this.users.add(session);
    }
    
    @OnClose
    public void handleClose(Session session ) {
    	this.users.remove(session);
    }
    @OnMessage
    public void handleMessage(String msg, Session session) {
    	System.out.println(msg);	
    	String username = (String) session.getUserProperties().get("username");
    	if(username != null) {
    		this.users.stream().forEach( x ->{
    			try{
    				x.getBasicRemote().sendText(this.build(username, msg).toString());
    			}catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    				
    				);
    	}
    	
              
    	
    }
    
    private JSONObject build(String username, String msg) {
    	JSONObject obj = new JSONObject();
    	try {
			obj.put("username", username );
			obj.put("followed", msg);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return obj;
    }
}
