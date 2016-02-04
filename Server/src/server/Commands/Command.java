package server.commands;

import server.responses.ResponseWrapper;

/**
 * Abstract Command
 *
 * Created by rodriggl on 1/29/2016.
 */
public interface Command {
    /**
     * Execute this command
     *
     * @param userID    the userID of the user who invoked this command
     * @return  a list of userIDs to message and the response message
     */
    ResponseWrapper execute(int userID);
}
