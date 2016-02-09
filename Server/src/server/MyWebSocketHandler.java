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

import server.commands.Command;
import server.responses.ResponseWrapper;


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
        
        //C=commandFactory(json)
        //json_response=c.execute(user_id)
        /*
         * Response object is an array of users ids and a string(message to send back)
         * 
         * for(all x in a)
         * x.send(response)
         */
        CommandFactory commandfactory=new CommandFactory();
        Command c=commandfactory.makeCommand(message);
        
        ResponseWrapper responsewrapper=c.execute(personal_id);
        
        //String final_message=responsewrapper.getresponse();
        //List<Integer> ids=responsewrapper.gettargetIDS();
        
        /*
         * for(Integer id: ids)
         * {
         * 		Session session=sessions.get(id);
         * 		session.getRemote().sendString(final_message);
         * }
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