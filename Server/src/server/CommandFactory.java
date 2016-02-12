package server;

import server.command.Command;
import server.exception.CommandNotFoundException;

/**
 * Generate command
 *
 * Created by rodriggl on 2/4/2016.
 */
public class CommandFactory {
    public static Command makeCommand(String json) throws CommandNotFoundException {
        CommandParser commandParser = JsonTranslator.getGson().fromJson(json, CommandParser.class);
        return commandParser.parseCommand();
    }
}
