package server.command;

import server.dto.gameplay.GamePlayInfo;
import server.exception.GameNotFoundException;
import server.exception.InvalidCredentialsException;
import server.exception.PreConditionException;
import server.responses.GamePlayResponse;
import server.responses.GetDestinationsResponse;
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
public class GetDestinationsCommand extends Command {
	@SerializedName("gameId")
	private int gameId;
	
    @Override
    public List<ResponseWrapper> execute(int userID) {
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        responses.add(responseWrapper);
        try {
			serverFacade.getDestinations(userID, gameId);
			GamePlayInfo gamePlayInfo = new GamePlayInfo(serverFacade.getGame(gameId));
            responseWrapper.setResponse(new GetDestinationsResponse(gameId, gamePlayInfo.getPrivateInfo(userID).getPossibleDestinationCards()));
		} catch (PreConditionException e) {
            responseWrapper.setResponse(Response.newInvalidInputResponse().setMessage(e.getMessage()));
		} catch (InvalidCredentialsException | GameNotFoundException e) {
            responseWrapper.setResponse(Response.newServerErrorResponse().setMessage(e.getMessage()));
        }

        return responses;
        
    }
}
