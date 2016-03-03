package server.command;
import com.google.gson.annotations.SerializedName;
import model.PlayerColor;
import server.responses.Response;
import server.responses.ResponseWrapper;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class JoinGameCommand extends Command {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("color")
    private String colorName;

    @Override
    public ResponseWrapper execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        PlayerColor color = PlayerColor.getColor(colorName);

        if (colorName == null) {
            return responseWrapper.setResponse(Response.newInvalidInputResponse());
        } else if (!serverFacade.canAddPlayerToGame(userID, gameId, color)) {

        }
        return new ResponseWrapper(userID, Response.newServerErrorResponse(), commandName);
    }
}
