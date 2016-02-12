package server.command;

import server.responses.ResponseWrapper;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class LoginCommand implements Command {
    private String username;
    private String password;

    @Override
    public ResponseWrapper execute(int userID) {
        return null;
    }
}
