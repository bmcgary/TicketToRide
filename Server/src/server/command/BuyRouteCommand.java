package server.command;

import com.google.gson.annotations.SerializedName;

import server.responses.Response;
import server.responses.ResponseWrapper;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class BuyRouteCommand extends Command {
	@SerializedName("gameId")
	private int gameID;
	
    @Override
    public ResponseWrapper execute(int userID) {
    	
    	//serverFacade.buyRoute(userID, gameID, route);
    	
        return new ResponseWrapper(userID, Response.newServerErrorResponse(), commandName);
    }
}
