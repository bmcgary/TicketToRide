package server.command;

import com.google.gson.annotations.SerializedName;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class StartGameCommand extends Command {
    @SerializedName("gameId")
    private int gameId;

    public StartGameCommand(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public List<ResponseWrapper> execute(int userID) {

        return Collections.singletonList(new ResponseWrapper(userID, Response.newServerErrorResponse(), commandName));
    }
}
