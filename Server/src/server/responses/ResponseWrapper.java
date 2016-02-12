package server.responses;

import server.JsonTranslator;

import java.util.List;

/**
 * Wrapper around the response class to hold the destination userids
 *
 * Created by rodriggl on 2/4/2016.
 */
public class ResponseWrapper {
    List<Integer> targetIDs;
    String response;
    
    
//added by Ray with permission from Levi---------
    
    public List<Integer> getTargetIDs() {
		return targetIDs;
	}

	public void setTargetIDs(List<Integer> targetIDs) {
		this.targetIDs = targetIDs;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
//end of what Ray added--------------------------

	public ResponseWrapper(List<Integer> targetIDs, Response response) {
        this.targetIDs = targetIDs;
        this.response = JsonTranslator.getGson().toJson(response);
    }

    public ResponseWrapper(List<Integer> targetIDs, String response) {
        this.targetIDs = targetIDs;
        this.response = response;
    }
}
