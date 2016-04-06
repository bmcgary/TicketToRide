package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import server.command.LogoutCommand;
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
        
        Command command = new LogoutCommand();
        command.preparedExecute(personal_id);
        //sendMessages(responseWrappers);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        Command command = new LogoutCommand();
        command.preparedExecute(personal_id);
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
            List<ResponseWrapper> responseWrappers = command.preparedExecute(personal_id);

	        if(command instanceof LoginCommand || command instanceof RegisterCommand)
	        {
	        	//make sure they successfully logged in/registered
	        	if(responseWrappers.get(0).getTargetIds()!= null)
	        	{
	        		//there should only be one id in the idlist
                    personal_id = responseWrappers.get(0).getTargetIds().get(0);
	        		sessions.put(personal_id, personal_session);
	     
	        	}
	        	else
	        	{
	        		sendInvalidMessage(responseWrappers.get(0).getResponse());
	        		return;
	        	}
	        }

	        //a message will be sent back regardless of whether request was successful or not
	        sendMessages(responseWrappers);
		}

		catch (CommandNotFoundException e1) {
			sendInvalidMessage(new ResponseWrapper(Response.newServerErrorResponse()).getResponse());
		}

    }

    public void sendPublicMessage(String message) {
    	System.out.println("Sending out this public message:");
    	System.out.println("\t" + message);
        sessions.forEach((id, session) -> {
            try {
                session.getRemote().sendString(message);
            } catch (IOException e) {
                System.err.println("Failed to send to user " + id);
            }
        });
    }

    public void sendMessage(Iterator<Integer> targetIds, String message) {
    	System.out.println("Sending out this message:");
    	System.out.println("\t" + message);
    	targetIds.forEachRemaining(targetId -> {
            try {
                sessions.get(targetId).getRemote().sendString(message);
            } catch (IOException e) {
                System.err.println("Failed to send to user " + id);
            }
        });
    }

    public void sendMessages(List<ResponseWrapper> wrappers)
    {
    	//go through each response wrapper
        wrappers.stream().forEach(wrapper -> {
            Iterator<Integer> targetIds = wrapper.getTargetIds().iterator();
            String message = wrapper.getResponse();

            System.out.println("Sending out this one message of many:");
            System.out.println("\t" + message);
            //send the message of this particular response wrapper to all of its targetIDs
            targetIds.forEachRemaining(targetId -> {
                try {

                    //-1 means to send to everyone, otherwise, send the message to the specified targetId.
                    if (targetId == -1)
                        sendPublicMessage(message);
                    else
                        sessions.get(targetId).getRemote().sendString(message);

                } catch (IOException e) {
                    System.err.println("Failed to send to user " + id);
                }

            });

        });
    }

    public void sendInvalidMessage(String message)
    {
    	System.out.println("Sending out this invalid message:");
    	System.out.println("\t" + message);
    	try {
			personal_session.getRemote().sendString(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}