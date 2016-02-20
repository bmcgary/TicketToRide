package server;

import java.util.ArrayList;
import java.util.List;

import model.CityToCityRoute;
import model.Game;
import model.Player;
import model.PlayerColor;
import server.exception.AddUserException;
import server.exception.BadCredentialsException;
import server.exception.InternalServerException;
import server.exception.PreConditionException;

public class ServerFacade {
	private static ServerFacade serverFacade;
	private List<Game> games;
	private List<User> users;
	private final int MAX_PLAYERS_PER_GAME = 5;

	public static ServerFacade getServerFacade() {
		if (serverFacade == null) {
			serverFacade = new ServerFacade();
		}
		return serverFacade;
	}

	private ServerFacade() {
		games = new ArrayList<>();
		users = new ArrayList<>();
	}

	public synchronized void createGame(Game newGame)
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
	
	/**
	 * Adds a new user to the list (part of registration, for example)
	 * @param newUser the user to be added
	 * @throws AddUserException thrown if the new user wasn't instantiated properly or a user with the same name already exists
	 */
	public synchronized void addNewUser(User newUser) throws AddUserException
	{
		//check to make sure user was added properly
		if(newUser == null || newUser.getPlayerID() <= 0 || newUser.getPlayerID() == Integer.MAX_VALUE){
			throw new AddUserException("User object wasn't instantiated properly");
		}
		
		//ensure no user exists with the given username
		for(User user : users){
			if(user.getUsername().equals(newUser.getUsername())) {
				throw new AddUserException("User with the same username already exists!");
			}
		}
		
		//add player
		users.add(newUser);
		assert(users.contains(newUser));
	}
	
	public boolean canAddPlayerToGame(int playerID, int gameID, PlayerColor playerColor)
	{
		Game game = null;
		User user = null;
		
		//game must exist
		for(Game g : games){
			if(g.getGameID() == gameID){
				game = g;
				break;
			}
		}
		if(game == null){
			return false;
		}
		
		//user must exist
		for(User u : users){
			if(u.getPlayerID() == playerID){
				user = u;
				break;
			}
		}
		if(user == null){
			return false;
		}
		
		//user must be logged in
		if(!user.isLoggedIn()){
			return false;
		}
		
		//color can't already be in game, user can't already be in game
		for(Player p : game.getPlayerManager().getPlayers()){
			if(p.getPlayerColor() == playerColor || p.getPlayerID() == playerID){
				return false;
			}
		}
		
		//game cannot already be in play
		if(game.isStarted()){
			return false;
		}
		
		//game can't be full
		return game.getPlayerManager().getNumPlayers() < this.MAX_PLAYERS_PER_GAME;

	}
	
	/**
	 * Adds a player with the given ID to the game with the given ID
	 * @param playerID the ID of the player
	 * @param gameID the ID of the game
	 * @param playerColor the color wished to join with
	 */
	public synchronized void addPlayerToGame(int playerID, int gameID, PlayerColor playerColor)
	{
		//helper check
		if(!this.canAddPlayerToGame(playerID, gameID, playerColor)){
			assert(false);
			return;
		}
		
		//update game's playerManager
		for(Game game : games){
			if(game.getGameID() == gameID){
				game.getPlayerManager().addPlayer(playerID, playerColor);
			}
		}
		
		//update User's current games
		for(User user : users){
			if(user.getPlayerID() == playerID){
				user.joinGame(gameID);
			}
		}
	}
	
	/**
	 * Determines whether or not the player can start the game
 	 * @param playerID The player attempting to start the game
 	 * @param gameID The game to be started
 	 * @return True if possible, false otherwise
	 */
	public boolean canStartGame(int playerID, int gameID)
	{
		User user = null;
		Game game = null;
		 //player must exist and be logged in
		for(User u : this.users){
			if(u.getPlayerID() == playerID){
			 	user = u;
			 	if(!user.isLoggedIn()){
			 		return false;
			 	}
			 	break;
			 }
		 }
		 if(user == null){	//guarantees user was found
		 	return false;
		 }
		 
		 //game must exist
		 for (Game g : this.games){
		 	if(g.getGameID() == gameID){
		 		game = g;
		 		break;
		 	}
		 }
		 if(game == null){	//guarantees game was found
		 	return false;
		 }
		 
		 //game can't already be started
		 if(game.isStarted()){
			 return false;
		 }
		 
		 //game must have at least 2 players
		 if(game.getPlayerManager().getNumPlayers() < 2){
			 return false;
		 }
		 
		 //player starting game must be 0th slot in game's list
		 if(game.getPlayerManager().getPlayers().get(0).getPlayerID() != playerID){	//can anyone say Demeter's law?
			 return false;
		 }
		 
		 return true;
	}
	
	/**
	 * Either called by the player who created the game, or when the game has 5 players. Initializes everything
	 * @param playerID the ID of the player attempting to start
	 * @param gameID the gameID desired to begin
	 * @throws PreConditionException Thrown if the player can't start the given game
	 * @throws InternalServerException thrown if somehow the canDo returns true, but we couldn't create the new game
	 */
	public synchronized void startGame(int playerID, int gameID) throws PreConditionException, InternalServerException
	{
		//helper check
		if(!this.canStartGame(playerID,  gameID)){
			throw new PreConditionException("Game " + gameID + " cannot be started by player " + playerID);
		}
		
		Game game = null;
		for(Game g : games){
			if(g.getGameID() == gameID){
				game = g;
				break;
			}
		}
		if(game == null){
			throw new InternalServerException("Something went dreadfully wrong in ServerFacade::startGame()");
		}
		else{
			game.startGame();
		}
		
		assert(game.getHistory().size() > 1);
	}
	
	public boolean canLeaveGame(int playerID, int gameID)
	{
		return false; //for future implementation
	}
	
	public synchronized void leaveGame(int playerID, int gameID)
	{
		return; 	//for future implementation
	}
	
	/**
	 * Logs the user in
	 * @param username the user to be logged in
	 * @param password the user's password
	 * @return the PlayerID of the logged in user
	 * @throws BadCredentialsException thrown if the password is incorrect, 
	 * the user is logged in already, or if the user isn't registered
	 */
	public synchronized int login(String username, String password) throws BadCredentialsException
	{
		//logs in the user if found
		for(User user : users){
			if(user.getUsername().equals(username)) {
				if(user.getPassword().equals(password)) {
					if(!user.isLoggedIn()){
						user.setLoggedIn(true);
						return user.getPlayerID();
					}
					else{
						throw new BadCredentialsException("User already logged in!");
					}
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
	public void logout(int playerID) throws BadCredentialsException
	{
		for(User user : users){
			if(user.getPlayerID() == playerID){
				user.setLoggedIn(false);
				return;
			}
		}
		
		throw new BadCredentialsException("User not found");
	}
	
	/**
	 * Registers the given user
	 * @param username the username to be added
	 * @param password the password to be added
	 * @return the playerID of the newly created user
	 * @throws AddUserException thrown if the user cannot be added, usually for a user name already taken
	 */
	public synchronized int register(String username,String password) throws AddUserException, InternalServerException
	{
		User newUser = new User(username, password);
		this.addNewUser(newUser);	//if this line throws an AddUserException for improper instantiation, there's a problem and I messed up. 
		try {
			return login(username, password);
		} catch (BadCredentialsException e) {
			//This is very bad if it happens
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
}