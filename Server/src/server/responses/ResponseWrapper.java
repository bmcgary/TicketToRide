package server.responses;

import com.google.gson.annotations.SerializedName;
import server.JsonTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper around the response class to hold the destination userids
 *
 * Created by rodriggl on 2/4/2016.
 */
public class ResponseWrapper {
    private transient List<Integer> targetIds;
    @SerializedName("command")
    private String commandName;

    // This is an object so gson serializes fully
    // (see http://stackoverflow.com/questions/8153582/gson-doesnt-serialize-fields-defined-in-subclasses)
    @SerializedName("parameters")
    private Object response;

    public ResponseWrapper(List<Integer> targetIDs, Response response, String commandName) {
        this.targetIds = targetIDs;
        this.response = response;
        this.commandName = commandName;
    }

    public ResponseWrapper(Response response) {
        this(null, response, null);
    }

    public ResponseWrapper(String commandName) {
        this(null, null, commandName);
    }

    public ResponseWrapper(int targetID, String commandName) {
        this(targetID, null, commandName);
    }

    public ResponseWrapper(int targetID, Response response, String commandName) {
        this(Collections.singletonList(targetID), response, commandName);
    }

    // getters & setters
    public List<Integer> getTargetIds() {
        return targetIds;
    }

    public ResponseWrapper setTargetIds(List<Integer> targetIds) {
        this.targetIds = targetIds;
        return this;
    }

    public ResponseWrapper addTargetId(int id) {
        if (targetIds == null) {
            targetIds = new ArrayList<>();
        }
        targetIds.add(id);
        return this;
    }

    public String getResponse() {
        return JsonTranslator.getGson().toJson(this);
    }

    public ResponseWrapper setResponse(Response response) {
        this.response = response;
        return this;
    }
}
