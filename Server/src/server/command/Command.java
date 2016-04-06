package server.command;

import server.ServerFacade;
import server.responses.ResponseWrapper;

import java.util.List;

/**
 * Abstract Command
 *
 * Created by rodriggl on 1/29/2016.
 */
public abstract class Command {
    protected transient ServerFacade serverFacade;
    protected transient String commandName;

    /**
     * Execute this command
     *
     * @param userID    the userID of the user who invoked this command
     * @return  a list of userIDs to message and the response message
     */
    public abstract List<ResponseWrapper> execute(int userID);

    public List<ResponseWrapper> preparedExecute(int userID) {
        this.serverFacade = ServerFacade.getServerFacade();
        return execute(userID);
    }

    public Command setCommandName(String commandName) {
        this.commandName = commandName;
        return this;
    }
}
