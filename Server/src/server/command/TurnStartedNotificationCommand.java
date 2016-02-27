package server.command;

import server.responses.Response;
import server.responses.ResponseWrapper;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class TurnStartedNotificationCommand extends Command {
    @Override
    public ResponseWrapper execute(int userID) {
        return new ResponseWrapper(userID, Response.newServerErrorResponse(), super.getCommandName());
    }
}
