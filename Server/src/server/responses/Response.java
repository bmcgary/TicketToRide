package server.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Generic response class; DTO
 *
 * Created by rodriggl on 2/3/2016.
 */
public class Response {
    @SerializedName("description")
    private String description;

    public Response(String description) {
        this.description = description;
    }

    public static Response newSuccessResponse() {
        return new Response("success");
    }

    public static Response newInvalidInputResponse() {
        return new Response("invalid input");
    }

    public static Response newServerErrorResponse() {
        return new Response("server error");
    }
}
