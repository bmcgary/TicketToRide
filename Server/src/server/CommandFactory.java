package server;

import server.commands.Command;

/**
 * Generate command
 *
 * Created by rodriggl on 2/4/2016.
 */
public class CommandFactory {
    public static Command makeCommand(String json) {
        CommandParser commandParser = JsonTranslator.getGson().fromJson(json, CommandParser.class);
        return commandParser.parseCommand();
    }
}
