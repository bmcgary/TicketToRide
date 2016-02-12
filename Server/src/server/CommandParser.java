package server;
import com.google.gson.JsonObject;
import server.command.Command;

/**
 * Generates a Command object of the command type
 *
 * Created by rodriggl on 1/29/2016.
 */
public class CommandParser {
    String command;
    JsonObject parameters;

    public CommandParser(String command, JsonObject parameters) {
        this.command = command;
        this.parameters = parameters;
    }

    public Command parseCommand() {
        Command newCommand = null;
        try {
            newCommand = (Command) JsonTranslator.getGson().fromJson(parameters, Class.forName(command + "Command"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newCommand;
    }
}
