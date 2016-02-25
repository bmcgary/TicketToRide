package server;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import server.command.Command;
import server.exception.CommandNotFoundException;

/**
 * Generates a Command object of the command type
 *
 * Created by rodriggl on 1/29/2016.
 */
public class CommandParser {
    @SerializedName("command")
    String command;
    @SerializedName("parameters")
    JsonObject parameters;

    public CommandParser(String command, JsonObject parameters) {
        this.command = command;
        this.parameters = parameters;
    }

    public Command parseCommand() throws CommandNotFoundException{
        try {
            return ((Command) JsonTranslator.getGson().fromJson(parameters, getCommandClass(command))).setCommandName(command);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CommandNotFoundException("Unable to find command " + command);
        }
    }

    private static Class getCommandClass(String commandName) throws ClassNotFoundException {
        StringBuilder stringBuilder = new StringBuilder(Command.class.getName());
        stringBuilder.insert(stringBuilder.lastIndexOf(".") + 1, toTitleCase(commandName));
        return Class.forName(stringBuilder.toString());
    }

    // from http://stackoverflow.com/questions/1086123/string-conversion-to-title-case
    private static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
