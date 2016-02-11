package server.commands;

import com.google.gson.annotations.SerializedName;
import server.AddUserException;
import server.InternalServerException;
import server.ServerFacade;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class RegisterCommand implements Command {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("email")
    String email;

    @Override
    public ResponseWrapper execute(int userID) {
        try {
            ServerFacade.register(username, password);
        } catch (AddUserException e) {
            return new ResponseWrapper(Collections.singletonList(userID), Response.newInvalidInputResponse());
        } catch (InternalServerException e) {
            return new ResponseWrapper(Collections.singletonList(userID), Response.newServerErrorResponse());
        }
        return null;
    }
}
