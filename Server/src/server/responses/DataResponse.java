package server.responses;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by rodriggl on 3/17/2016.
 */
public class DataResponse extends Response {
    @SerializedName("data")
    private Object data;

    public DataResponse(Object data) {
        this("success", data);
    }

    public DataResponse(String message, Object data) {
        super(message);
        this.data = data;
    }
}
