package server.command;

import model.TrackColor;
import server.exception.InternalServerException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;
import server.responses.AvailableTrainCardsNotificationResponse;
import server.responses.DrawTrainCardResponse;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class DrawTrainCardCommand extends TurnStartedNotificationCommand {
	@SerializedName("cardLocation")
	private int cardLocation;

	@Override
	public List<ResponseWrapper> turnExecute(int userId) {
		List<ResponseWrapper> responses = new ArrayList<>();
		ResponseWrapper responseWrapper = new ResponseWrapper(userId, commandName);
		responses.add(responseWrapper);

		try {
			TrackColor color = super.serverFacade.drawTrainCard(userId, super.gameId, cardLocation);
			responseWrapper.setResponse(new DrawTrainCardResponse(super.gameId, color, !super.game.getPlayerManager().drewAlreadyCurrentTurn));
			AvailableTrainCardsNotificationResponse response = new AvailableTrainCardsNotificationResponse(super.gameId, game.getGameBoard().getVisibleTrainCarCards());
			responses.add(new ResponseWrapper(super.playerIds, response, super.commandName));
		} catch (PreConditionException | OutOfBoundsException | InternalServerException e) {
			responseWrapper.setResponse(Response.newServerErrorResponse());
		}

		return responses;
	}
}
