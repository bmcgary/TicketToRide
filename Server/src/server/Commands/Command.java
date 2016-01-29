package server.commands;

/**
 * Abstract Command
 *
 * Created by rodriggl on 1/29/2016.
 */
public interface Command {
    /**
     * Executes this command
     * @return true if successful, false otherwise
     */
    boolean execute();
}
