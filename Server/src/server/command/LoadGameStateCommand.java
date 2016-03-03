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
public class LoadGameStateCommand extends Command {
    @SerializedName("path")
    private String path;

    @Override
    public List<ResponseWrapper> execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        // TODO change this once server facade is correct
        serverFacade.loadGameState();
        return Collections.singletonList(responseWrapper.setResponse(Response.newSuccessResponse()));
    }
}
