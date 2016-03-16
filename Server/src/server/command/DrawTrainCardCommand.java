package server.command;

import server.exception.InternalServerException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class DrawTrainCardCommand extends Command {
	@SerializedName("gameId")
	private int gameId;
	@SerializedName("cardLocation")
	private int cardLocation;
	
    @Override
    public List<ResponseWrapper> execute(int userID) {
    	
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
    	
    	try {
    		
			serverFacade.drawTrainCard(userID, gameId, cardLocation);
			
		} catch (PreConditionException | OutOfBoundsException | InternalServerException e) {
			
			e.printStackTrace();
			
			responseWrapper.setResponse(Response.newServerErrorResponse());
			responses.add(responseWrapper);
			return responses;
			
		}
    	
        responseWrapper.setResponse(Response.newSuccessResponse());
        responses.add(responseWrapper);
        
        return responses;

    }
}
