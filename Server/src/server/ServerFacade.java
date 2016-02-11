package server;

import java.util.ArrayList;
import java.util.List;

import model.CityToCityRoute;
import model.Game;
import model.Player;
import model.PlayerColor;

public class ServerFacade {
	
	private static List<Game> games;
	private static List<User> users;
	private final int MAX_PLAYERS_PER_GAME = 5;
	
	public static synchronized void createGame(Game newGame)
	{
		//check to make sure game was instantiated properly
		if(newGame == null || newGame.getGameBoard() == null){
			assert(false);
			return;
		}
		else if(newGame.getPlayerManager() == null){
			assert(false);
			return;
		}
		else{
			assert(newGame.getPlayerManager().getNumPlayers() > 0);
		}
		
		//add game
		games.add(newGame);
		assert(games.contains(newGame));
	}
	
	public static synchronized void addNewUser(User newUser) throws AddUserException
	{
		//check to make sure user was added properly
		if(newUser == null || newUser.getPlayerID() <= 0 || newUser.getPlayerID() == Integer.MAX_VALUE){
			throw new AddUserException("User object wasn't instantiated properly");
		}
		
		//ensure no user exists with the given username
		for(User user : getUsers()){
			if(user.getUsername().equals(newUser.getUsername())) {
				throw new AddUserException("User with the same username already exists!");
			}
		}
		
		//add player
		getUsers().add(newUser);
		assert(users.contains(newUser));
	}
	
	/**
	 * 
	 * @param playerID
	 * @param gameID
	 * @return
	 */
	public boolean canAddPlayerToGame(int playerID, int gameID, PlayerColor playerColor)
	{
		Game game = null;
		User user = null;
		
		//game must exist
		for(Game g : getGames()){
			if(g.getGameID() == gameID){
				game = g;
				break;
			}
		}
		if(game == null){
			return false;
		}
		
		//user must exist
		for(User u : getUsers()){
			if(u.getPlayerID() == playerID){
				user = u;
				break;
			}
		}
		if(user == null){
			return false;
		}
		
		//color can't already be in game, user can't already be in game
		for(Player p : game.getPlayerManager().getPlayers()){
			if(p.getPlayerColor() == playerColor || p.getPlayerID() == playerID){
				return false;
			}
		}
		
		//game can't be full
		if(game.getPlayerManager().getNumPlayers() > this.MAX_PLAYERS_PER_GAME){
			return false;
		}
		
		return true;
	}
	
	public synchronized void addPlayerToGame(int playerID, int gameID, PlayerColor playerColor)
	{
		//helper check
		if(!this.canAddPlayerToGame(playerID, gameID, playerColor)){
			assert(false);
			return;
		}
		
		//update game's playerManager
		for(Game game : getGames()){
			if(game.getGameID() == gameID){
				game.getPlayerManager().addPlayer(playerID, playerColor);
			}
		}
		
		//update User's current games
		for(User user : getUsers()){
			if(user.getPlayerID() == playerID){
				user.joinGame(gameID);
			}
		}
	}
	
	public void canStartGame(int playerID, int gameID)
	{
		
	}
	
	public synchronized void startGame(int playerID, int gameID)
	{
		
	}
	
	public void canLeaveGame(int playerID, int gameID)
	{
		
	}
	
	public synchronized void leaveGame(int playerID, int gameID)
	{
		
	}
	
	/**
	 * Logs the user in
	 * @param username the user to be logged in
	 * @param password the user's password
	 * @throws BadCredentialsException thrown if the password is incorrect or if the user isn't registered
	 */
	public static synchronized void login(String username, String password) throws BadCredentialsException
	{
		//logs in the user if found
		for(User user : getUsers()){
			if(user.getUsername().equals(username)) {
				if(user.getPassword().equals(password)) {
					user.setLoggedIn(true);
					return;
				}
				else{
					throw new BadCredentialsException("Bad password");
				}
			}
		}
		
		//throws exception if user not found
		throw new BadCredentialsException("User not found");
	}
	
	/**
	 * Logs out the User with the given ID
	 * @param playerID the ID of the user to log out
	 * @throws BadCredentialsException thrown if the player isn't found
	 */
	public static void logout(int playerID) throws BadCredentialsException
	{
		for(User user : getUsers()){
			if(user.getPlayerID() == playerID){
				user.setLoggedIn(false);
				return;
			}
		}
		
		throw new BadCredentialsException("User not found");
	}
	
	public static synchronized void register(String username,String password) throws AddUserException, InternalServerException
	{
		User newUser = new User(username, password);
		addNewUser(newUser);
		try {
			login(username, password);
		} catch (BadCredentialsException e) {
			System.out.println("FATAL ERROR: User wasn't properly registered. See ServerFacade::register()");
			e.printStackTrace();
			throw new InternalServerException("Failed to properly register user");
		}
	}
	
	public void canBuyRoute(int playerID, int gameID, CityToCityRoute route)
	{
		
	}
	
	public synchronized void buyRoute(int playerID, int gameID, CityToCityRoute route)
	{
		
	}
	
	public void canDrawTrainCard(int playerID, int gameID, int cardLocation)
	{
		
	}
	
	public synchronized  void drawTrainCard(int playerID, int gameID, int cardLocation)
	{
		
	}
	
	public void canGetDestinations(int playerID, int gameID)
	{
		
	}
	
	public synchronized void getDestinations(int playerID, int gameID)
	{
		
	}
	
	public void canSelectDestinations(int playerID, int gameID, int[] destinationsSelected)
	{
		
	}
	
	public synchronized void selectDestinations(int playerID, int gameID, int[] destinationsSelected)
	{
		
	}
	
	public synchronized void loadGameState()
	{
		
	}
	
	public synchronized void saveGameState()
	{
		
	}
	
	public synchronized void sendClientModelInformation()
	{
		
	}
	
	public void getCityMapping()
	{
		
	}

	private static List<Game> getGames() {
		if (games == null) {
			games = new ArrayList<>();
		}

		return games;
	}

	private static List<User> getUsers() {
		if (users == null) {
			users = new ArrayList<>();
		}

		return users;
	}
}