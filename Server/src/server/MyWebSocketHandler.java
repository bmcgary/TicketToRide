package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import com.google.gson.JsonObject;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

@WebSocket
public class MyWebSocketHandler {

    static HashMap<Integer, Session> sessions = new HashMap<>();
    static Integer id=0;
    Integer personal_id;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        sessions.put(id, session);
		personal_id=id;
		id++;
		//sendMessage("You are connected");
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("Message: " + message);
        
        //PUT JSON PARSING BELOW IF NEEDED
		/*	JSONObject test=new JSONObject(message);
			String name=test.getString("name");
			String password=test.getString("password");
			System.out.println(name + " " + password);*/
        
        //C=commandFactory(json)
        //json_response=c.execute(user_id)
        /*
         * Response object is an array of users ids and a string(message to send back)
         * 
         * for(all x in a)
         * x.send(response)
         */


        try {
            sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {

        JsonObject json = new JsonObject();
        json.addProperty("hello", message);
        for( Session s : sessions.values()) {
            s.getRemote().sendString(json.toString());

        }
        
        /*Set<Integer> ids=sessions.keySet();
        for(Integer i: ids)
        {
        	if(i==1)
        	{
        		Session s=sessions.get(i);
        		s.getRemote().sendString(json.toString());
        	}
        }*/
    }
}