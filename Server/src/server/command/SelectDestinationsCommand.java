package server.command;

import com.google.gson.annotations.SerializedName;
import model.Game;
import server.dto.gameplay.GamePlayInfo;
import server.exception.GameNotFoundException;
import server.exception.InvalidCredentialsException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;
import server.responses.Response;
import server.responses.ResponseWrapper;
import server.responses.SelectDestinationsResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class SelectDestinationsCommand extends TurnStartedNotificationCommand {
    @SerializedName("destinationsSelected")
    private int[] selectedDestinations;

    @Override
    public List<ResponseWrapper> turnExecute(int userID) {
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        responses.add(responseWrapper);
        try {
            super.serverFacade.selectDestinations(userID, gameId, selectedDestinations);
            GamePlayInfo gamePlayInfo = new GamePlayInfo(super.game);
            responseWrapper.setResponse(new SelectDestinationsResponse(gameId, gamePlayInfo.getPrivateInfo(userID)));
        } catch (PreConditionException | OutOfBoundsException e) {
            responseWrapper.setResponse(Response.newInvalidInputResponse());
        } catch (InvalidCredentialsException e) {
            responseWrapper.setResponse(Response.newServerErrorResponse());
        }
        return responses;
    }
}
