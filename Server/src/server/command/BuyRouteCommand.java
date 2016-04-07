package server.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CityToCityRoute;
import model.Game;
import model.GameBoard;
import model.TrackColor;

import com.google.gson.annotations.SerializedName;

import server.ServerFacade;
import server.exception.InternalServerException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;
import server.responses.Response;
import server.responses.ResponseWrapper;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class BuyRouteCommand extends TurnStartedNotificationCommand {
	@SerializedName("routeIndex")
	private int routeIndex;
	@SerializedName("trainColor")
	private String trainColor;
	@SerializedName("numberOfWilds")
	private int numberOfWilds;

	@Override
	public List<ResponseWrapper> turnExecute(int userId) {
		List<ResponseWrapper> responses = new ArrayList<>();
		ResponseWrapper responseWrapper = new ResponseWrapper(userId, commandName);
		responses.add(responseWrapper);

		try {
			CityToCityRoute route = ServerFacade.getCityMapping().get(routeIndex);
			Map<TrackColor, Integer> cards = new HashMap<>();
			cards.put(TrackColor.getColor(trainColor), route.getNumTrains() - numberOfWilds);
			if (numberOfWilds > 0)
				cards.put(TrackColor.None, numberOfWilds);
			serverFacade.buyRoute(userId, super.gameId, route, cards);
			responses.addAll(new SendClientModelInformationCommand(super.gameId).setSendPublic(true).preparedExecute(userId));
		} catch (PreConditionException e) {
			responses.clear();
			responses.add(responseWrapper);
			responseWrapper.setResponse(Response.newServerErrorResponse().setMessage("Precondition error: " + e.getMessage()));
		} catch (InternalServerException e) {
			responses.clear();
			responses.add(responseWrapper);
			responseWrapper.setResponse(Response.newServerErrorResponse().setMessage("Internal error: " + e.getMessage()));
		} catch (OutOfBoundsException e) {
			responses.clear();
			responses.add(responseWrapper);
			responseWrapper.setResponse(Response.newServerErrorResponse().setMessage("Out of Bounds error: " + e.getMessage()));
		}

		return responses;
	}
}
