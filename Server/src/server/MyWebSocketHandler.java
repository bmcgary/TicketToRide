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
    public void onMessage(String message) throws JSONException {
        System.out.println("Message: " + message);

        Command c = null;
		try {
			c = CommandFactory.makeCommand(message);
		} catch (CommandNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	//create the command
        
        LoginCommand islogin=new LoginCommand(); //just to compare to the actual command
        RegisterCommand isregister=new RegisterCommand(); //just to compare to the actual command
        ResponseWrapper responsewrapper = null;
        List<Integer> idlist = null;
        JSONObject json;
        
        if(c.getClass()== islogin.getClass() || c.getClass()==isregister.getClass())
        {
        	responsewrapper=c.execute(-1);	//pass in a -1 because user id is not used in login/register
        	json=new JSONObject(responsewrapper.getResponse());
        	
        	if(json.getString("description").equals("success"))	//make sure they successfully logged in/registered
        	{
        		idlist=responsewrapper.getTargetIDs();
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
        
		sendMessage(responsewrapper.getTargetIds(), responsewrapper.getResponse());		//send back to server

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