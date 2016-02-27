package server.responses;

import com.google.gson.annotations.SerializedName;
import server.JsonTranslator;
import server.command.Command;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper around the response class to hold the destination userids
 *
 * Created by rodriggl on 2/4/2016.
 */
public class ResponseWrapper {
    private transient List<Integer> targetIDs;
    @SerializedName("command")
    private String commandName;

    // This is an object so gson serializes fully
    // (see http://stackoverflow.com/questions/8153582/gson-doesnt-serialize-fields-defined-in-subclasses)
    @SerializedName("parameters")
    private Object response;

    public ResponseWrapper(Response response, String commandName) {
        this.commandName = commandName;
        this.response = response;
    }

	public ResponseWrapper(List<Integer> targetIDs, Response response, String commandName) {
        this(response, commandName);
        this.targetIDs = targetIDs;
    }

    public ResponseWrapper(int targetID, Response response, String commandName) {
        this(response, commandName);
        this.targetIDs = Collections.singletonList(targetID);
    }

    // getters & setters
    public List<Integer> getTargetIds() {
        return targetIDs;
    }

    public String getResponse() {
        return JsonTranslator.getGson().toJson(this);
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
