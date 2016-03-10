package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

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
    Integer personal_id=-1;
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
		try {
            Command command = CommandFactory.makeCommand(message);
            // TODO: to stop errors
            ResponseWrapper responseWrapper = command.execute(personal_id).get(0);

	        if(command instanceof LoginCommand || command instanceof RegisterCommand)
	        {
	        	if(responseWrapper.getTargetIds()!= null)	//make sure they successfully logged in/registered
	        	{
                    personal_id = responseWrapper.getTargetIds().get(0); //there should only be one id in the idlist
	        		sessions.put(personal_id, personal_session);
	        	}
	        	else
	        	{
	        		sendInvalidMessage(responseWrapper.getResponse());
	        		return;
	        	}
	        }

			sendMessage(responseWrapper.getTargetIds().iterator(), responseWrapper.getResponse());		//send back to server
		}

		catch (CommandNotFoundException e1) {
			sendInvalidMessage(new ResponseWrapper(Response.newServerErrorResponse()).getResponse());
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

    public void sendMessages(ArrayList<ResponseWrapper> wrappers)
    {
    	//go through each response wrapper
    	for(int i=0; i<wrappers.size(); i++)
    	{

    		Iterator<Integer> targetIds=wrappers.get(i).getTargetIds().iterator();
    		String message=wrappers.get(i).getResponse();

    		//send the message of this particular response wrapper to all of its targetIDs
    		targetIds.forEachRemaining(targetId -> {
    			try{
    				sessions.get(targetId).getRemote().sendString(message);
    			} catch(IOException e) {
    				System.err.println("Failed to send to user " + id);
    			}

    		});

    	}
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