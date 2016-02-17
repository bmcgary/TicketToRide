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
        //sessions.put(id, session);
		//personal_id=id;
		//id++;
		//sendMessage("You are connected");
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("Message: " + message);

        /*
         * ResponseWrapper is a list of users ids and a string (message to send back)
         * 
         * for(all x in a)
         * x.send(response)
         */
        ResponseWrapper responseWrapper;
        try {
            Command c = CommandFactory.makeCommand(message);
            responseWrapper = c.execute(personal_id);
        } catch (CommandNotFoundException e) {
            responseWrapper = new ResponseWrapper(personal_id, Response.newServerErrorResponse());
        }
        
        sendMessage(responseWrapper.getTargetIds(), responseWrapper.getResponse());
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
}