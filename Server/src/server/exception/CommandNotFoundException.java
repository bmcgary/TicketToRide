package server.exception;

/**
 * Exception thrown by CommandParser
 *
 * Created by rodriggl on 2/12/2016.
 */
public class CommandNotFoundException extends Exception {
    public CommandNotFoundException(String message) {
        super(message);
    }
}
