package server;

import java.util.ArrayList;
import java.util.List;

import server.exception.InvalidCredentialsException;

public class User {
	protected static int nextID = 1;
	protected int playerID;
	protected String username;
	protected String password;
	protected boolean loggedIn;
	

	public User(String username, String password) throws InvalidCredentialsException{
		if(username == null || password == null){
			throw new InvalidCredentialsException("Cannot register with no username/password");
		}
		else if(username.length() < 4 || password.length() < 4){
			throw new InvalidCredentialsException("Username/password must be 4+ characters long");
		}
		else{
			for(char c : username.toCharArray()){
				if(!Character.isLetterOrDigit(c)){
					throw new InvalidCredentialsException("Username contains invalid characters");
				}
			}
			for(char c : password.toCharArray()){
				if(!Character.isLetterOrDigit(c)){
					throw new InvalidCredentialsException("Username contains invalid characters");
				}
			}
		}
		playerID = nextID++;
		this.username = username;
		this.password = password;
		loggedIn = false;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public int getPlayerID() {
		return playerID;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
