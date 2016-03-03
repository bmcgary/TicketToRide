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
        return new Response(getSuccessString());
    }

    public static Response newInvalidInputResponse() {
        return new Response(getInvalidInputString());
    }

    public static Response newServerErrorResponse() {
        return new Response(getServerErrorResponse());
    }

    public static String getSuccessString() { return "success"; }

    public static String getInvalidInputString() { return "invalid input"; }

    public static String getServerErrorResponse() { return "server error"; }
}
