package server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class User {
	private static int nextID = 0;
	private int playerID;
	private String username;
	private String password;
	private List<Integer> joinedGames;
	private boolean loggedIn;
	
//---Ray added stuff below------------
//------------------------------------
	
	private PrintWriter printwriter;
	
	public PrintWriter getPrintwriter() {
		return printwriter;
	}

	public void setPrintwriter(PrintWriter printwriter) {
		this.printwriter = printwriter;
	}

//------------------------------------
//---end of what Ray added------------

	public User(String username, String password){
		playerID = nextID++;
		this.username = username;
		this.password = password;
		joinedGames = new ArrayList<Integer>();
		loggedIn = true;
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

	public boolean joinGame(int gameID){
		joinedGames.add(gameID);
		return true;
	}

}
