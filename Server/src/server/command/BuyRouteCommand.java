package server.command;

import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class BuyRouteCommand extends Command {
    @Override
    public List<ResponseWrapper> execute(int userID) {
        return Collections.singletonList(new ResponseWrapper(userID, Response.newServerErrorResponse(), commandName));
    }
}
