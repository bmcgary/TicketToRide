package server.command;

import java.util.List;

import model.CityToCityRoute;
import model.Game;
import model.GameBoard;

import com.google.gson.annotations.SerializedName;

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
public class BuyRouteCommand extends Command {
	@SerializedName("gameId")
	private int gameID;
	@SerializedName("routeIndex")
	private int routeIndex;
	
    @Override
    public ResponseWrapper execute(int userID) {

    	CityToCityRoute route=getRoute();
    	try {
			serverFacade.buyRoute(userID, gameID, route);
		} catch (PreConditionException | InternalServerException
				| OutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return new ResponseWrapper(userID, Response.newServerErrorResponse(), commandName);
    }
    
    private CityToCityRoute getRoute()
    {
    	List<Game> games=serverFacade.getAllGames();
    	Game game=null;
    	
    	for(int i=0; i<games.size(); i++)
    	{
    		if(games.get(i).getGameID()==gameID)
    		{
    			game=games.get(i);
    			break;
    		}
    	}
    	
    	GameBoard board=game.getGameBoard();
    	List<CityToCityRoute> routes=board.getRoutes();
    	CityToCityRoute route=routes.get(routeIndex);
    	
    	return route;
    		
    }
}
