package server;

import java.util.ArrayList;
import java.util.List;

public class User {
	private static int nextID = 1;
	private int playerID;
	private String username;
	private String password;
	private List<Integer> joinedGames;
	private boolean loggedIn;
	

	public User(String username, String password){
		playerID = nextID++;
		this.username = username;
		this.password = password;
		joinedGames = new ArrayList<Integer>();
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

	public void joinGame(int gameID){
		joinedGames.add(gameID);
	}
	
	public void leaveGame(int gameID){
		if(joinedGames.contains(gameID)){
			joinedGames.remove(gameID);
		}
	}

}
