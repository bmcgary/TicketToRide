package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import server.command.Command;
import server.exception.CommandNotFoundException;
import server.responses.Response;
import server.responses.ResponseWrapper;


@WebSocket
public class MyWebSocketHandler {

    static HashMap<Integer, Session> sessions = new HashMap<>();
    Integer personal_id = -1;
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
        // debugging
        System.out.println("Message: " + message);

        Command command;
		try {
            command = CommandFactory.makeCommand(message);
        } catch (CommandNotFoundException e) {
            sendPrivateMessage(new ResponseWrapper(Response.newServerErrorResponse()).getResponse());
            return;
        }

        List<ResponseWrapper> responses = command.execute(personal_id);

        responses.forEach(responseWrapper -> {
            // Check to see if this is a private response
            if (responseWrapper.isPrivate())
                sendPrivateMessage(responseWrapper.getResponse());
            // Check to see if this is a global message
            else if (responseWrapper.isPublic())
                sendPublicMessage(responseWrapper.getResponse());
            else {
                String commandName = responseWrapper.getCommandName();
                if (commandName.equalsIgnoreCase("login") || commandName.equalsIgnoreCase("register")) {
                    personal_id = responseWrapper.getTargetIds().get(0); //there should only be one id in the idlist
                    sessions.put(personal_id, personal_session);
                }
                sendMessage(responseWrapper);
            }
        });
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

    public void sendMessage(ResponseWrapper responseWrapper) {
        responseWrapper.getTargetIds().forEach(targetId -> {
            try {
                sessions.get(targetId).getRemote().sendString(responseWrapper.getResponse());
            } catch (IOException e) {
                System.err.println("Failed to send to user " + targetId);
            }
        });
    }
    
    public void sendPrivateMessage(String message)
    {
    	try {
			personal_session.getRemote().sendString(message);
		} catch (IOException e) {
            System.err.println("Failed to send to unnumbered user");
		}
    }
}