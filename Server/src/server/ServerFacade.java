package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.City;
import model.CityToCityRoute;
import model.Game;
import model.GameBoard;
import model.Player;
import model.PlayerColor;
import model.TrackColor;
import server.exception.AddUserException;
import server.exception.AlreadyLoggedInException;
import server.exception.BadCredentialsException;
import server.exception.GameNotFoundException;
import server.exception.InternalServerException;
import server.exception.InvalidCredentialsException;
import server.exception.OutOfBoundsException;
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

	public synchronized void createGame(Game newGame, int playerID, PlayerColor color) throws InternalServerException, PreConditionException
	{
		//check to make sure game was instantiated properly
		if(newGame == null || newGame.getGameBoard() == null){
			throw new PreConditionException("Game/GameBoard improperly instantiated");
		}
		else if(newGame.getPlayerManager() == null){
			throw new PreConditionException("PlayerManager was not properly instantiated");
		}
		else if(color == null){
			throw new PreConditionException("PlayerColor was null");
		}
		else{
			assert(newGame.getPlayerManager().getNumPlayers() == 0);
		}
		
		if(!this.isPlayerLoggedIn(playerID)){
			throw new PreConditionException("Creating player either doesn't exist or isn't logged in");
		}
		
		//add game
		games.add(newGame);
		assert(games.contains(newGame));
		
		//add creator to the game under the color that they choose
		try {
			this.addPlayerToGame(playerID, newGame.getGameID(), color);
		} catch (PreConditionException e) {
			// If this happens, Trent somehow messed up the createGame method
			e.printStackTrace();
			throw new InternalServerException("Trent messed up in ServerFacade::createGame()");
		}
	}
	
	/**
	 * Adds a new user to the list (part of registration, for example)
	 * @param newUser the user to be added
	 * @throws AddUserException thrown if the new user wasn't instantiated properly or a user with the same name already exists
	 */
	private synchronized void addNewUser(User newUser) throws AddUserException
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
	 * @throws PreConditionException 
	 */
	public synchronized void addPlayerToGame(int playerID, int gameID, PlayerColor playerColor) throws PreConditionException
	{
		//helper check
		if(!this.canAddPlayerToGame(playerID, gameID, playerColor)){
			throw new PreConditionException("Player can't be added to the game");
		}
		
		//update game's playerManager
		for(Game game : games){
			if(game.getGameID() == gameID){
				game.getPlayerManager().addPlayer(playerID, playerColor);
				game.addHistoryMessage("Player " + playerID + " added to game.");
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
	 * @throws AlreadyLoggedInException 
	 */
	public synchronized int login(String username, String password) throws BadCredentialsException, AlreadyLoggedInException
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
						throw new AlreadyLoggedInException("User already logged in!");
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
	 * @throws InvalidCredentialsException 
	 */
	public synchronized int register(String username,String password) throws AddUserException, InternalServerException, InvalidCredentialsException
	{
		User newUser = new User(username, password);
		for(User u : this.users){
			if(u.getUsername().equals(username)){
				throw new InvalidCredentialsException("That username already exists!");
			}
		}
		this.addNewUser(newUser);	//if this line throws an AddUserException for improper instantiation, there's a problem and I messed up. 
		try {
			return login(username, password);
		} catch (Exception e) {
			//This is very bad if it happens
			System.out.println("FATAL ERROR: User wasn't properly registered. See ServerFacade::register()");
			e.printStackTrace();
			throw new InternalServerException("Failed to properly register user");
		}
	}
	
	/**
	 * Reports whether a player in a given game may buy the desired route
	 * @param playerID The player wanting to buy the route
	 * @param gameID The game in which this takes place
	 * @param route the desired route
	 * @param cards the cards desired to use in order to purchase the route
	 * @return true if the route can be bought, false otherwise
	 * @throws InternalServerException 
	 */
	public boolean canBuyRoute(int playerID, int gameID, CityToCityRoute route, Map<TrackColor, Integer> cards) throws InternalServerException
	{
		//Helper methods
		if(!this.isPlayerLoggedIn(playerID) || !this.isPlayableGame(gameID)){
			return false;
		}
		else{
			for(Game g : games){
				if(g.getGameID() == gameID){
					return g.canPlayerBuyRoute(playerID, route, cards);
				}
			}
			throw new InternalServerException("See ServerFacade::canBuyRoute");
		}
	}
	
	/**
	 * Assumes the player will buy the route with the fewest wild cards possible used
	 * @param playerID the player buying the route
	 * @param gameID the game for the purchase
	 * @param route the route being bought
	 * @param cards TODO
	 * @throws PreConditionException thrown if the player can't buy the route
	 * @throws InternalServerException thrown if something horrible happens and Trent messed up
	 * @throws OutOfBoundsException 
	 */
	public synchronized void buyRoute(int playerID, int gameID, CityToCityRoute route, Map<TrackColor, Integer> cards) throws PreConditionException, InternalServerException, OutOfBoundsException
	{
		//helper method
		if(!this.canBuyRoute(playerID, gameID, route, cards)){
			throw new PreConditionException("Player: " + playerID + " cannot buy requested route in game " + gameID);
		}
		
		else{
			for(Game g : games){
				if(g.getGameID() == gameID){
					g.buyRoute(playerID, route, cards);
					return;
				}
			}
		}
	}
	
	/**
	 * Determines whether a player can draw a given train card
	 * @param playerID the player drawing the card
	 * @param gameID the game this is happening in
	 * @param cardLocation 0-4 refers to the visible cards, 5 refers to the top of the deck
	 * @return true if possible, false otherwise
	 * @throws OutOfBoundsException if the cardLocation is out of bounds
	 * @throws InternalServerException if Trent messed up
	 */
	public boolean canDrawTrainCard(int playerID, int gameID, int cardLocation) throws OutOfBoundsException, InternalServerException
	{
		for(Game g : games){
			if(g.getGameID() == gameID){
				return g.canPlayerDrawTrainCard(playerID, cardLocation);
			}
		}
		return false;
	}
	
	public synchronized  void drawTrainCard(int playerID, int gameID, int cardLocation) throws PreConditionException, OutOfBoundsException, InternalServerException
	{
		//helper method
		if(!this.canDrawTrainCard(playerID, gameID, cardLocation)){
			throw new PreConditionException("player " + playerID + " cannot draw the card");
		}
		
		for(Game g : games){
			if(g.getGameID() == gameID){
				g.drawTrainCard(playerID, cardLocation);
			}
		}
	}
	
	public boolean canGetDestinations(int playerID, int gameID)
	{
		if(!this.isPlayableGame(gameID) || !this.isPlayerLoggedIn(playerID)){
			return false;
		}
		for(Game g : games){
			if(g.getGameID() == gameID){
				return g.canPlayerGetDestinations(playerID);
			}
		}
		return false;
	}
	
	/**
	 * Draws 3 destination cards from the deck, adds them to the considered Destination cards of the player
	 * @param playerID The player doing the drawing of the destination cards
	 * @param gameID the game this is happening in
	 * @throws PreConditionException thrown if the player cannot draw cards now
	 */
	public synchronized void getDestinations(int playerID, int gameID) throws PreConditionException
	{
		//helper method
		if(!this.canGetDestinations(playerID, gameID)){
			throw new PreConditionException("Preconditions not met to get Destinations");
		}
		
		for(Game g : games){
			if(g.getGameID() == gameID){
				g.getDestinations(playerID);
				break;
			}
		}
	}
	
	public boolean canSelectDestinations(int playerID, int gameID, int[] destinationsSelected) throws OutOfBoundsException
	{
		//helper method
		if(!this.isPlayableGame(gameID) || !this.isPlayerLoggedIn(playerID)){
			return false;
		}
		
		//checks that destinationsSelected isn't null
		if(destinationsSelected == null){
			return false;
		}
		
		//checks boundaries for indices
		for(int i : destinationsSelected){
			if(i > 2 || i < 0){
				throw new OutOfBoundsException();
			}
		}
		
		//asks the appropriate game
		for(Game g : games){
			if(g.getGameID() == gameID){
				return g.canPlayerSelectDestinations(playerID, destinationsSelected);
			}
		}
		return false;
	}
	
	public synchronized void selectDestinations(int playerID, int gameID, int[] destinationsSelected) throws PreConditionException, OutOfBoundsException
	{
		//helper method
		if(!this.canSelectDestinations(playerID, gameID, destinationsSelected)){
			throw new PreConditionException("Preconditions not met for selectDestinations");
		}
		
		for(Game g : games){
			if(g.getGameID() == gameID){
				g.selectDestinations(playerID, destinationsSelected);
			}
		}
	}
	
	public synchronized void loadGameState()
	{
		Gson gson = new Gson();
		String relativePath = new File("").getAbsolutePath() + "/Server/src/saveFiles/";
		String file = relativePath + "saveUsers.json";
		
		//first load users
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			User[] usersArray = gson.fromJson(br, User[].class);
			users = Arrays.asList(usersArray);
		} catch (IOException e){
			e.printStackTrace();
		}
		
		//then load games
		file = relativePath + "saveGames.json";
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			Game[] gamesArray = gson.fromJson(br, Game[].class);
			games = Arrays.asList(gamesArray);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public synchronized void saveGameState()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String relativePath = new File("").getAbsolutePath() + "/Server/src/saveFiles/";	
		
		//first do users
		try {
			PrintWriter writer = new PrintWriter(relativePath + "saveUsers.json", "UTF-8");
			writer.println(gson.toJson(users));
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//then do games
		try {
			PrintWriter writer = new PrintWriter(relativePath + "saveGames.json", "UTF-8");
			writer.println(gson.toJson(games));
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void sendClientModelInformation()
	{
		//TODO: this
	}
	
	public Map<Integer, CityToCityRoute> getCityMapping()
	{
		return GameBoard.getRouteMapping();
	}
	
	/**
	 * Determines whether a player is currently logged in
	 * @param playerID the player to check
	 * @return false if the player is not logged in or doesn't exist, true otherwise
	 */
	protected boolean isPlayerLoggedIn(int playerID){
		//player must exist be logged in
		for(User u : users){
			if(u.getPlayerID() == playerID){
				if(u.isLoggedIn()){	//must be logged in
					return true;
				}
				break;
			}
		}
		return false;
	}
	
	/**
	 * Helper method that determines whether or not a game can have playing actions performed in it
	 * @param gameID the game to be checked
	 * @return false if the game doesn't exist or hasn't started yet, true otherwise
	 */
	protected boolean isPlayableGame(int gameID){
		for(Game g : games){
			if(g.getGameID()==gameID){
				return (g.isStarted() && !g.isGameOver());
			}
		}
		return false;
	}

	/**
	 * Queries for all joinable games. Joinable games are defined as games not containing the user that still have at
	 * least one spot open.
	 * @param userID the user requesting the joinable games
	 * @return a list of games joinable by the user
     */
	public List<Game> getJoinableGames(int userID) {
		List<Game> output = new ArrayList<Game>();
		for(Game g : games){
			if(isPlayableGame(g.getGameID()) && !g.containsPlayer(userID)){
				output.add(g);
			}
		}
		return Collections.unmodifiableList(output);
	}

	/**
	 * Queries for all games containing the user
	 * @param userID the user requesting the user's games
	 * @return a list of games which the user is in
     */
	public List<Game> getUserGames(int userID) {
		List<Game> output = new ArrayList<Game>();
		for(Game g : games){
			if(g.containsPlayer(userID)){
				output.add(g);
			}
		}
		return Collections.unmodifiableList(output);
	}
	
	/**
	 * Gets all games currently on the server facade
	 * @return an unmodifiable collection of games
	 */
	public List<Game> getAllGames(){
		return Collections.unmodifiableList(games);
	}
	
	/**
	 * Gets all users currently registered on the server facade
	 * @return an unmodifiable collection of users
	 */
	public List<User> getAllUsers(){
		return Collections.unmodifiableList(users);
	}

	/**
	 * Get a specified game
	 * @param gameID the id for the desired game
	 * @return game requested
     */
	public Game getGame(int gameID) throws GameNotFoundException {
		Optional<Game> possibleGame = games.parallelStream().filter(game -> game.getGameID() == gameID).findFirst();
		if (possibleGame.isPresent())
			return possibleGame.get();
		else
			throw new GameNotFoundException("could not find game " + gameID);
	}

	public static void firebomb()
	{
		serverFacade.games.clear();
		serverFacade.users.clear();
	}
	
	public static void main(String args[]) throws AddUserException, InternalServerException, PreConditionException, InvalidCredentialsException, OutOfBoundsException{
		ServerFacade sf = ServerFacade.getServerFacade();
		int pid = sf.register("Trent", "trent");
		int pid2 = sf.register("Jacob",  "jacob");
		Game g = new Game();
		Game g2 = new Game();
		sf.createGame(g, pid, PlayerColor.Black);
		sf.createGame(g2, pid, PlayerColor.Black);
		sf.addPlayerToGame(pid2, 1, PlayerColor.Green);
		sf.startGame(pid, 1);
		Map<TrackColor, Integer> m = new HashMap<TrackColor, Integer>();
		m.put(TrackColor.Red, 1);
		sf.saveGameState();
		sf.selectDestinations(pid, 1, new int[]{0,1});
		sf.selectDestinations(pid2, 1, new int[]{0,1});
		if(sf.canBuyRoute(pid, 1, new CityToCityRoute(new City("Seattle"), new City("Portland"), 1, TrackColor.None), m)){
			sf.buyRoute(pid, 1, new CityToCityRoute(new City("Seattle"), new City("Portland"), 1, TrackColor.None), m);
		}
		sf.loadGameState();
		sf.selectDestinations(pid, 1, new int[]{0,1});
		System.out.println("Success!");
	}
}