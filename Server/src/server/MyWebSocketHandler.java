package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonObject;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import server.command.Command;
import server.exception.CommandNotFoundException;
import server.responses.Response;
import server.command.LoginCommand;
import server.command.RegisterCommand;
import server.responses.ResponseWrapper;


@WebSocket
public class MyWebSocketHandler {

    static HashMap<Integer, Session> sessions = new HashMap<>();
    static Integer id=0;
    Integer personal_id;
    Session personal_session;

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
        personal_session=session;

    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("Message: " + message);

        Command c = null;
        ResponseWrapper responsewrapper = null;
		try {
			c = CommandFactory.makeCommand(message);
			 	
	        
	        List<Integer> idlist = null;
	        
	        if(c instanceof LoginCommand || c instanceof RegisterCommand)
	        {
	        	responsewrapper=c.execute(-1);	//pass in a -1 because user id is not used in login/register
	        	
	        	if(responsewrapper.getTargetIds()!=null)	//make sure they successfully logged in/registered
	        	{
	        		idlist=responsewrapper.getTargetIds();
	        		personal_id=idlist.get(0);	//there should only be one id in the idlist
	        		sessions.put(personal_id, personal_session);
	        	}
	        	else
	        	{
	        		sendInvalidMessage(responsewrapper.getResponse());
	        		return;
	        	}
	        }
	        else
	        {
	        	responsewrapper=c.execute(personal_id);
	        }
	        
			sendMessage(responsewrapper.getTargetIds().iterator(), responsewrapper.getResponse());		//send back to server
		}

		catch (CommandNotFoundException e1) {
			// TODO Auto-generated catch block
			
		}

    }

    public void sendPublicMessage(String message) {
        sessions.forEach((id, session) -> {
            try {
                session.getRemote().sendString(message);
            } catch (IOException e) {
                System.err.println("Failed to send to user " + id);
            }
        });
    }

    public void sendMessage(Iterator<Integer> targetIds, String message) {
        targetIds.forEachRemaining(targetId -> {
            try {
                sessions.get(targetId).getRemote().sendString(message);
            } catch (IOException e) {
                System.err.println("Failed to send to user " + id);
            }
        });
    }
    
    public void sendInvalidMessage(String message) 
    {
    	try {
			personal_session.getRemote().sendString(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}