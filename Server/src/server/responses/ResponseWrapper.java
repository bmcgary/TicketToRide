package server.responses;

import server.JsonTranslator;

import java.util.Iterator;
import java.util.List;

/**
 * Wrapper around the response class to hold the destination userids
 *
 * Created by rodriggl on 2/4/2016.
 */
public class ResponseWrapper {
    private List<Integer> targetIDs;
    private String response;

    public ResponseWrapper(List<Integer> targetIDs, Response response) {
        this.targetIDs = targetIDs;
        this.response = JsonTranslator.getGson().toJson(response);
    }

    public ResponseWrapper(List<Integer> targetIDs, String response) {
        this.targetIDs = targetIDs;
        this.response = response;
    }

    public Iterator<Integer> getTargetIds() {
        return targetIDs.iterator();
    }

    public String getResponse() {
        return response;
    }
}
