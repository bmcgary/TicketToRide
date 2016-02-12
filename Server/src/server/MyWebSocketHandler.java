package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonObject;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import server.command.Command;
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
        
        /*Command c=CommandFactory.makeCommand(message);	//create the command
        
        LoginCommand islogin=new LoginCommand(); //just to compare to the actual command
        RegisterCommand isregister=new RegisterCommand(); //just to compare to the actual command
        ResponseWrapper responsewrapper = null;
        List<Integer> idlist = null;
        
        if(c.getClass()== islogin.getClass() || c.getClass()==isregister.getClass())
        {
        	responsewrapper=c.execute(-1);	//pass in a -1 because user id is not used in login/register
        	if(responsewrapper.getResponse().equals("success"))	//make sure they successfully logged in/registered
        	{
        		idlist=responsewrapper.getTargetIDs();
        		personal_id=idlist.get(0);	//there should only be one id in the idlist
        		sessions.put(personal_id, personal_session);
        	}
        }
        else
        {
        	responsewrapper=c.execute(personal_id);
        }*/
        
		try {
			//sendMessage(responsewrapper.getResponse(), idlist);		//send back to server
			sendMessage(null, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    public void sendMessage(String message, List<Integer> ids) throws IOException {

        JsonObject json = new JsonObject();
        json.addProperty("hello", "what up");
       /* for( Integer id : ids) {
        	Session session=sessions.get(id);
            session.getRemote().sendString(json.toString());

        }*/
        personal_session.getRemote().sendString(json.toString());
    }
}