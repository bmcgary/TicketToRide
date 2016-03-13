package test;

import org.junit.*;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.City;
import model.CityToCityRoute;
import model.DestinationRoute;
import model.Game;
import model.GameBoard;
import model.Player;
import model.PlayerColor;
import model.TestGame;
import model.TestGameBoard;
import model.TestPlayerManager;
import model.TrackColor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.ServerFacade;
import server.User;
import server.exception.AddUserException;
import server.exception.AlreadyLoggedInException;
import server.exception.BadCredentialsException;
import server.exception.InternalServerException;
import server.exception.InvalidCredentialsException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;

public class ServerFacade_Test {


	/*
	 * 	private List<City> cities;
	private List<CityToCityRoute> routes;
	private Map<Integer, List<CityToCityRoute>> currentRoutes;
	private List<DestinationRoute> destinationRoutes;
	private List<TrackColor> deckTrainCarCards;
	private TrackColor[] visibleTrainCarCards;
	private List<TrackColor> discardedTrainCarCards;

	public GameBoard(){
		cities = new ArrayList<City>();
		routes = new ArrayList<CityToCityRoute>();
		currentRoutes = new HashMap<Integer, List<CityToCityRoute>>();
		destinationRoutes = new ArrayList<DestinationRoute>();
		visibleTrainCarCards = new TrackColor[5];
		deckTrainCarCards = new ArrayList<TrackColor>();
		discardedTrainCarCards = new ArrayList<TrackColor>();
	}
	 */
	ServerFacade serverFacade;
	Game game1; //version 1
	GameBoard gameboard1;

	Game game2;//version2
	GameBoard gameboard2;

	Game game3;//version3
	GameBoard gameboard3;


	/**
	 * set up serverFacade
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		serverFacade = ServerFacade.getServerFacade();


	}
	/*
	 * does currentRoutes mean how many routes a player has? integer is his/her  player id?
	 */
	/**
	 * set up a game for testing
	 * @throws Exception
	 */
	@Before
	public void buildGame_version1() throws Exception{
	/*game1 = new Game();
	gameboard1 = new GameBoard();
	List<City> cities = new ArrayList<City>();
	List<CityToCityRoute> routes = new ArrayList<CityToCityRoute>();
	Map<Integer, List<CityToCityRoute>> currentRoutes = new HashMap<Integer,List<CityToCityRoute>>();
	List<DestinationRoute> destinationRoutes = new ArrayList<DestinationRoute>();
	List<TrackColor> deckTrainCarCards = new ArrayList<TrackColor>();
	TrackColor[] visibleTrainCarCards= new TrackColor[256];
	List<TrackColor> discardedTrainCarCards = new ArrayList<TrackColor>();
	City city1 = new City(new Point(3,4),"LA");
	City city2 = new City(new Point(4,5),"SA");
	City city3 = new City(new Point(5,5),"GA");


	cities.addAll(Arrays.asList(city1,city2,city3));
	CityToCityRoute ctoc1 = new CityToCityRoute(city1,city2,3,TrackColor.Orange);
	CityToCityRoute ctoc2 = new CityToCityRoute(city2,city3,3,TrackColor.White);
	currentRoutes.put(1, new ArrayList<CityToCityRoute>());

	routes.add(ctoc1);
	routes.add(ctoc2);
	DestinationRoute dr = new DestinationRoute(city1,city2,2);
	destinationRoutes.add(dr);
	deckTrainCarCards.add(TrackColor.Orange);
	visibleTrainCarCards[0] = TrackColor.Black;
	visibleTrainCarCards[1] = TrackColor.White;
	visibleTrainCarCards[2] = TrackColor.Orange;
	discardedTrainCarCards.add(TrackColor.Black);

	*/
	}

	@Before
	public void buildGame_version2() throws Exception{
	/*List<City> cities = new ArrayList<City>();
	List<CityToCityRoute> routes = new ArrayList<CityToCityRoute>();
	Map<Integer, List<CityToCityRoute>> currentRoutes = new HashMap<Integer,List<CityToCityRoute>>();
	List<DestinationRoute> destinationRoutes = new ArrayList<DestinationRoute>();
	List<TrackColor> deckTrainCarCards = new ArrayList<TrackColor>();
	TrackColor[] visibleTrainCarCards= new TrackColor[256];
	List<TrackColor> discardedTrainCarCards = new ArrayList<TrackColor>();
	City city1 = new City(new Point(0,1),"LA");
	City city2 = new City(new Point(1,0),"SA");
	City city3 = new City(new Point(1,0),"GA");

	cities.addAll(Arrays.asList(city1,city2,city3));
	CityToCityRoute ctoc1 = new CityToCityRoute(city1,city2,3,TrackColor.Orange);
	CityToCityRoute ctoc2 = new CityToCityRoute(city2,city3,3,TrackColor.White);
	currentRoutes.put(1, new ArrayList<CityToCityRoute>());

	routes.add(ctoc1);
	routes.add(ctoc2);
	DestinationRoute dr = new DestinationRoute(city1,city2,2);
	destinationRoutes.add(dr);
	deckTrainCarCards.add(TrackColor.Orange);
	visibleTrainCarCards[0] = TrackColor.Black;
	visibleTrainCarCards[1] = TrackColor.White;
	visibleTrainCarCards[2] = TrackColor.Orange;
	discardedTrainCarCards.add(TrackColor.Black);
	*/
	}

	@Before
	public void buildGame_version3() throws Exception{
	/*List<City> cities = new ArrayList<City>();
	List<CityToCityRoute> routes = new ArrayList<CityToCityRoute>();
	Map<Integer, List<CityToCityRoute>> currentRoutes = new HashMap<Integer,List<CityToCityRoute>>();
	List<DestinationRoute> destinationRoutes = new ArrayList<DestinationRoute>();
	List<TrackColor> deckTrainCarCards = new ArrayList<TrackColor>();
	TrackColor[] visibleTrainCarCards= new TrackColor[256];
	List<TrackColor> discardedTrainCarCards = new ArrayList<TrackColor>();
	City city1 = new City(new Point(2,2),"LA");
	City city2 = new City(new Point(3,3),"SA");
	City city3 = new City(new Point(1,2),"GA");

	cities.addAll(Arrays.asList(city1,city2,city3));
	CityToCityRoute ctoc1 = new CityToCityRoute(city1,city2,3,TrackColor.Orange);
	CityToCityRoute ctoc2 = new CityToCityRoute(city2,city3,3,TrackColor.White);
	currentRoutes.put(1, new ArrayList<CityToCityRoute>());

	routes.add(ctoc1);
	routes.add(ctoc2);
	DestinationRoute dr = new DestinationRoute(city1,city2,2);
	destinationRoutes.add(dr);
	deckTrainCarCards.add(TrackColor.Orange);
	visibleTrainCarCards[0] = TrackColor.Black;
	visibleTrainCarCards[1] = TrackColor.White;
	visibleTrainCarCards[2] = TrackColor.Orange;
	discardedTrainCarCards.add(TrackColor.Black);
	*/
	}

	@After
	public void firebombServerFacade()
	{
		//Call firebomb on ServerFacade after each test
		//ensure each test starts fresh
		ServerFacade.firebomb();
	}

	//VALID
	@Test
	public void testGetServerFacade() {
		assertNotEquals(ServerFacade.getServerFacade(),null);
	}
	
	//*********************************************************************************
	//REGISTER TESTS

	/* error:
	 * not handle if a user name is empty not handle is username is larger than 25 char.
	 * I can even enter a chinese character..this is a creepy test case.
	 */
	//VALID
	@Test
	public void testRegisterInvalidInputs() throws AddUserException, InternalServerException
	{
		//too short username
		try {
			serverFacade.register("", "password");
			fail("should have thrown exception blank username");
		} catch (InvalidCredentialsException e) {}
		
		//invalid characters
		try {
			serverFacade.register("test1", "passdasdsad啦啦啦啦啦ada啦dasdasdasdasdaasdasd");
			fail("should have thrown exception invalid characters");
		} catch (InvalidCredentialsException e) {}
		
		//too short password
		try {
			serverFacade.register("name", "abc");
			fail("should have thrown exception too small password");
		} catch (InvalidCredentialsException e) {}
		
		//null inputs
		try {
			serverFacade.register(null, null);
			fail("should have thrown exception null inputs");
		} catch (InvalidCredentialsException e) {}
		
		//this one should work
		try {
			serverFacade.register("name", "password");
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
			fail("threw exception on valid inputs");
		}
		
		//user already exists
		try {
			serverFacade.register("name", "password2");
			fail("should have thrown exception duplicate user");
		} catch (InvalidCredentialsException e) {}
	}

	//VALID
	@Test
	public void testRegisterSuccessCase() throws InvalidCredentialsException
	{
		ServerFacade sf = ServerFacade.getServerFacade();
		
		try {
			sf.register("name", "pass");
		} catch (InternalServerException e) {
			e.printStackTrace();
			fail("something went wrong registering valid user");
		} catch (AddUserException e) {
			e.printStackTrace();
			fail("Something went wrong registering valid user");
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
			fail("invalid credentials when registering");
		}
		
		List<User> users = sf.getAllUsers();
		User user = null;
		for (User u : users)
		{
			if (u.getUsername().equals("name") && u.getPassword().equals("pass"))
			{
				user = u;
			}
		}
		assertFalse(user == null);
		assertTrue(user.isLoggedIn());
	}
	
	//*********************************************************************************
	//LOGIN TESTS
	
	/*
	 * test login successful
	 */
	//VALID
	@Test
	public void testLoginSuccessful() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException, InternalServerException
	{
		int userID = serverFacade.register("myname11", "mypassword");
		serverFacade.logout(userID);
		int userIDTest = serverFacade.login("myname11", "mypassword");
		
		assertEquals(userID,userIDTest);

		List<User> users = serverFacade.getAllUsers();
		User user = null;
		for (User u : users)
		{
			if (u.getUsername().equals("myname11") && u.getPassword().equals("mypassword"))
			{
				user = u;
			}
		}
		assertFalse(user == null);
		assertTrue(user.isLoggedIn());
	}

	/*
	 * test login - wrong user name/password
	 */
	//VALID
	@Test
	public void testLoginWrongCredientials() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException
	{
		int id = 0;
		try {
			id = serverFacade.register("myname22","mypassword");
		} catch (InternalServerException e) {
			e.printStackTrace();
			fail("Unable to register");
		}
		serverFacade.logout(id);

		try
		{
			serverFacade.login("myname23", "mypassword");
			fail("should have thrown exception");
		}
		catch(BadCredentialsException e){}
		try
		{
			serverFacade.login("myname22", "mypassWord");
			fail("should have thrown exception");
		}
		catch(BadCredentialsException e){}
		try
		{
			serverFacade.login(null, null);
			fail("should have thrown exception");
		}
		catch(BadCredentialsException e){}
		
		List<User> users = serverFacade.getAllUsers();
		User user = null;
		for (User u : users)
		{
			if (u.getUsername().equals("myname22") && u.getPassword().equals("mypassword"))
			{
				user = u;
			}
		}
		assertFalse(user == null);
		assertFalse(user.isLoggedIn());
	}

	/*
	 * test login - already logged in
	 */
	//VALID
	@Test(expected=AlreadyLoggedInException.class)
	public void testLoginAlreadyLoggedIn() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException
	{
		//registering also logs in
		try {
			serverFacade.register("mynameL","mypassword");
		} catch (InternalServerException e) {
			e.printStackTrace();
			fail("Unable to register");
		}
		serverFacade.login("mynameL", "mypassword");
	}

	//*********************************************************************************
	//LOGOUT TESTS
	
	/*
	 * can logout
	 */
	//VALID
	@Test
	public void testLogout() throws BadCredentialsException, AddUserException, AlreadyLoggedInException, InvalidCredentialsException, InternalServerException
	{
		int userID = serverFacade.register("myname11", "mypassword");
		serverFacade.logout(userID);
		
		List<User> users = serverFacade.getAllUsers();
		User user = null;
		for (User u : users)
		{
			if (u.getUsername().equals("myname11") && u.getPassword().equals("mypassword"))
			{
				user = u;
			}
		}
		assertFalse(user == null);
		assertFalse(user.isLoggedIn());
	}

	//failed to log out
	//VALID
	@Test(expected=BadCredentialsException.class)
	public void testLogoutFailed() throws BadCredentialsException
	{
		serverFacade.logout(2);
		serverFacade.logout(19999);
	}
	
	//*********************************************************************************
	//CREATE GAME TESTS
	
	//VALID
	@Test
	public void testCreateGame() throws AddUserException, InternalServerException, InvalidCredentialsException, BadCredentialsException, AlreadyLoggedInException
	{
		int userID = serverFacade.register("test1", "test1");
		TestGame game = new TestGame();
		
		//null color
		try{
		serverFacade.createGame(game, userID, null);
		fail("should have thrown exception, null color");
		}
		catch (InternalServerException e){}
		
		//null game
		try{
		serverFacade.createGame(null, userID, PlayerColor.Black);
		fail("should have thrown exception, null game");
		} catch (InternalServerException e) {}
		
		//nonexistant player
		try{
		serverFacade.createGame(game, -1, PlayerColor.Blue);
		fail("should have thrown exception, nonexistant player");
		} catch (InternalServerException e) {}
		
		//logged out
		serverFacade.logout(userID);
		try{
		serverFacade.createGame(game, userID, PlayerColor.Blue);
		fail("should have thrown exception, not logged in");
		} catch (InternalServerException e) {}
		serverFacade.login("test1", "test1");
		
		//success case
		serverFacade.createGame(game, userID, PlayerColor.Blue);
		//verify created correctly and user is in first index
		
		TestGame test = null;
		List<Game> games = serverFacade.getAllGames();
		for (Game g : games)
		{
			if (g.getGameID() == game.getGameID())
			{
				test = (TestGame) g;
			}
		}
		assertFalse(test == null);
		assertTrue(test.containsPlayer(userID));
		assertFalse(test.isStarted());
		assertFalse(test.isGameOver());
	}

	//*********************************************************************************
	//ADD PLAYER TESTS

	/*
	 * INVALID
	 */
	@Test
	public void testAddPlayerSuccessCase() throws InvalidCredentialsException, AddUserException, InternalServerException
	{
		TestGame game = new TestGame();
		int id1 = serverFacade.register("test1", "test1");
		int id2 = serverFacade.register("test2", "test2");
		
		serverFacade.createGame(game, id1, PlayerColor.Red);
		try{
			serverFacade.addPlayerToGame(id2, game.getGameID(), PlayerColor.Yellow);
		}
		catch (PreConditionException e)
		{
			fail("adding player to game should have worked");
		}
		
		List<Game> games = serverFacade.getAllGames();
		TestGame test = null;
		for (Game g : games)
		{
			if (g.getGameID() == game.getGameID())
			{
				test = (TestGame) g;
				break;
			}
		}
		assertFalse(test == null);
		TestPlayerManager manager = (TestPlayerManager) test.getPlayerManager();
		assertTrue(manager.getNumPlayers() == 2);
		assertFalse(manager.getPlayerByID(id2) == null);
		Player player = manager.getPlayerByID(id2);
		assertTrue(player.getPlayerColor() == PlayerColor.Yellow);		
	}
	
	/*
	 * VALID
	 */
	@Test
	public void testAddPlayerFullGame() throws InvalidCredentialsException, AddUserException, InternalServerException, PreConditionException
	{
		TestGame game = new TestGame();
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++)
		{
			String userString = "test" + Integer.toString(i);
			ids.add(serverFacade.register(userString, userString));
		}
		
		serverFacade.createGame(game, ids.get(0), PlayerColor.Black);
		
		serverFacade.addPlayerToGame(ids.get(1), game.getGameID(), PlayerColor.Blue);
		serverFacade.addPlayerToGame(ids.get(2), game.getGameID(), PlayerColor.Green);
		serverFacade.addPlayerToGame(ids.get(3), game.getGameID(), PlayerColor.Red);
		serverFacade.addPlayerToGame(ids.get(4), game.getGameID(), PlayerColor.Yellow);
		
		assertTrue(game.getPlayerManager().getNumPlayers() == 5);

		try{
			serverFacade.addPlayerToGame(ids.get(5), game.getGameID(), PlayerColor.Yellow);
			fail("game should have been considered full already");
		}
		catch (PreConditionException e){}
	}

	//VALID
	@Test
	public void testAddPlayerGameAlreadyStarted() throws PreConditionException, InternalServerException, BadCredentialsException, AlreadyLoggedInException, AddUserException, InvalidCredentialsException
	{
		Game game = new Game();

		int user1 = serverFacade.register("user111","password");
		int user2 = serverFacade.register("user222","password");
		int user3 = serverFacade.register("user333","password");
		int user4 = serverFacade.register("user444","password");
		int user5 = serverFacade.register("user555","password");

		//serverFacade.createGame(game);
		serverFacade.createGame(game, user1, PlayerColor.Black);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Blue);
		serverFacade.addPlayerToGame(user3, game.getGameID(), PlayerColor.Green);
		serverFacade.addPlayerToGame(user4, game.getGameID(), PlayerColor.Red);

		serverFacade.startGame(user1, game.getGameID());
		assertTrue(game.isStarted());
		assertFalse(serverFacade.canAddPlayerToGame(user5, game.getGameID(), PlayerColor.Yellow));
	}
	
	/*
	 * VALID
	 */
	@Test
	public void testAddPlayerAlreadyJoinedGame() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException, InternalServerException
	{
		int userID = serverFacade.register("joinAlready", "joinedGame");
		Game newGame = new Game();
		serverFacade.createGame(newGame, userID, PlayerColor.Black);
		
		//Try as the same color and as a new color
		assertFalse(serverFacade.canAddPlayerToGame(userID, newGame.getGameID(), PlayerColor.Black));
		assertFalse(serverFacade.canAddPlayerToGame(userID, newGame.getGameID(), PlayerColor.Red));
	}
	
	/*
	 * VALID
	 */
	@Test(expected = PreConditionException.class)
	public void testAddPlayerDuplicateColor() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException, InternalServerException, PreConditionException
	{
		TestGame game = new TestGame();

		int id1 = serverFacade.register("duplicate1","password");
		int id2 = serverFacade.register("duplicate2","password");

		serverFacade.createGame(game, id1, PlayerColor.Black);
		serverFacade.addPlayerToGame(id2, game.getGameID(), PlayerColor.Black);
	}
	
	//VALID
	@Test
	public void testAddPlayerNotLoggedIn() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException, InternalServerException
	{
		int playerID = serverFacade.register("canJoin", "notLoggedIn");
		Game newGame = new Game();
		int gameCreatorID = serverFacade.register("game", "creator");
		serverFacade.createGame(newGame, gameCreatorID, PlayerColor.Blue);
		
		//log 1st player out, try to log in with a variety of tactics
		serverFacade.logout(playerID);
		assertFalse(serverFacade.canAddPlayerToGame(playerID, newGame.getGameID(), PlayerColor.Red));
		assertFalse(serverFacade.canAddPlayerToGame(playerID, newGame.getGameID(), PlayerColor.Blue));
		assertFalse(serverFacade.canAddPlayerToGame(playerID, newGame.getGameID()-1, PlayerColor.Red));
		assertFalse(serverFacade.canAddPlayerToGame(playerID, 0, PlayerColor.Yellow));
		
		//log out gameCreator, try similar things
		serverFacade.logout(gameCreatorID);
		assertFalse(serverFacade.canAddPlayerToGame(gameCreatorID, newGame.getGameID(), PlayerColor.Red));
		assertFalse(serverFacade.canAddPlayerToGame(gameCreatorID, newGame.getGameID(), PlayerColor.Blue));
		assertFalse(serverFacade.canAddPlayerToGame(gameCreatorID, 0, PlayerColor.Red));
		
		//log 1st player back in, ensure he can join now
		playerID = serverFacade.login("canJoin", "notLoggedIn");
		assertFalse(serverFacade.canAddPlayerToGame(playerID, newGame.getGameID(), PlayerColor.Blue));
		assertTrue(serverFacade.canAddPlayerToGame(playerID, newGame.getGameID(), PlayerColor.Red));
	}
	
	@Test
	public void testAddPlayerInvalidInputs() throws AddUserException, InternalServerException, InvalidCredentialsException
	{
		int id1 = serverFacade.register("test1", "test1");
		int id2 = serverFacade.register("test2", "test2");
		
		TestGame game = new TestGame();
		
		serverFacade.createGame(game, id1, PlayerColor.Black);
		
		assertFalse(serverFacade.canAddPlayerToGame(-1, game.getGameID(), PlayerColor.Blue));
		assertFalse(serverFacade.canAddPlayerToGame(id2, -1, PlayerColor.Blue));
		assertFalse(serverFacade.canAddPlayerToGame(id1, game.getGameID(), null));
		
		try{
			serverFacade.addPlayerToGame(id1, game.getGameID(), null);
			fail("should have thrown exception, null color");
		} catch (PreConditionException e){}
	}


	@Test
	public void testStartGame() {

		ServerFacade facade = ServerFacade.getServerFacade();
		int test1ID = 0;
		int test2ID = 0;
		int test3ID = 0;
		int test4ID = 0;

		//register multiple users
		try
		{
			test1ID = facade.register("test1", "test1");
			test2ID = facade.register("test2", "test2");
			test3ID = facade.register("test3", "test3");
			test4ID = facade.register("test4", "test4");


			//create two games
			Game game1 = new Game();
			Game game2 = new Game();
			int game1ID = game1.getGameID();
			int game2ID = game2.getGameID();
			facade.createGame(game1,test1ID,PlayerColor.Blue); //assume this game is started by test1 with Blue
			facade.createGame(game2,test3ID,PlayerColor.Blue); //assume this game is started by test3 with Blue

			//call canStartGame with only one user in game.
			assertFalse(facade.canStartGame(test1ID, game1ID));

			//call canStartGame with user who did not create it.
			facade.addPlayerToGame(test2ID, game1ID, PlayerColor.Red);
			assertFalse(facade.canStartGame(test2ID, game1ID));

			//logout with one user
			//call canStartGame with logged out user (should be user who created game)
			facade.addPlayerToGame(test4ID, game2ID, PlayerColor.Red);
			try {
				facade.logout(test3ID);
			} catch (BadCredentialsException e) {
				fail("Failed to logout correctly.");
			}
			assertFalse(facade.canStartGame(test3ID, game2ID));

			//call canStartGame with user that does not exist
			int nonExistantID = -1;
			assertFalse(facade.canStartGame(nonExistantID, game1ID));

			//call canStartGame with game that does not exist
			assertFalse(facade.canStartGame(test1ID, nonExistantID));

			//call canStartGame with valid credentials
			assertTrue(facade.canStartGame(test1ID, game1ID));

			//call startGame
			//verify game has been started and all other necessary states
			facade.startGame(test1ID, game1ID);
			assertTrue(game1.containsPlayer(test1ID));
			assertTrue(game1.containsPlayer(test2ID));
			assertTrue(game1.isStarted());
			assertFalse(game1.isGameOver());

			//Perhaps check some more things on the game??

			//call canStartGame on game that has already been started
			assertFalse(facade.canStartGame(test1ID, game1ID));
		}
		catch(AddUserException e)
		{
			fail("Something went wrong trying to register users");
		}
		catch(InternalServerException e)
		{
			fail("Something went wrong trying to register users");
		} catch (PreConditionException e1) {
			e1.printStackTrace();
			fail("Something went wrong");
		} catch (InvalidCredentialsException e1) {
			e1.printStackTrace();
			fail("Problems registering");
		}
	}

	/**
	 * Player can select a route on the map to purchase



	 */
	@Test
	public void testCanBuyRoute() throws InternalServerException {
		/*City city1 = new City(new Point(2,2),"LA");
		City city2 = new City(new Point(3,3),"SA");
		CityToCityRoute ctoc1 = new CityToCityRoute(city1,city2,3,TrackColor.Orange);

		serverFacade.canBuyRoute(1,1,ctoc1);*/
	}

	//Player can use wilds as part of a route purchase
	//are you handling this case?
	public void testCanBuyRouteWithWrongResources()
	{
		/*
		 * 		for(int i = 0; i < route.getNumTrains(); ++i){	//this allows us to check every combination of wild cards/route color
			Map<TrackColor, Integer> trainCards = new HashMap<TrackColor, Integer>();
			trainCards.put(route.getTrackColor(), route.getNumTrains()-i);
			trainCards.put(TrackColor.None, i);
			if(playerManager.canBuyTrackWithCards(playerID, route.getNumTrains(), route.getTrackColor(), trainCards)){
				return true;
			}
		}
		 */
		//I do not understand how you check if a player has required cards
		//Player cannot purchase a route with less than the required cards

		//Player cannot purchase a route with more than the required cards
		//Player cannot purchase a route with cards of the wrong color
		//Player cannot purchase a route they do not have enough trains for
	}

//	Player can cancel buying a route
	//not handle or it should not be a function on the server side?

//	Routes are populated with trains the color of the player who purchased them


	//Player cannot buy adjacent route if less than 4 players in the game
//	Player cannot draw a destination ticket after building
//	Player cannot draw a train card after building
//	Player's destination cards are updated when building the route completes the destination

	@Test
	public void testBuyRoute() {
		fail("Not yet implemented");
	}

	@Test (expected=OutOfBoundsException.class)
	public void testCanDrawTrainCardFailing() throws OutOfBoundsException, InternalServerException, AddUserException, InvalidCredentialsException {
		ServerFacade.firebomb();
		ServerFacade sf = ServerFacade.getServerFacade();
		int id1 = sf.register("test1", "test1");
		sf.createGame(new Game(), id1, PlayerColor.Black);
		sf.canDrawTrainCard(1, 1, 10);

	}

	@Test
	public void testCanDrawTrainCard() throws OutOfBoundsException, InternalServerException {


		//before checking if a player can draw train card I need to manipulate the playermanager class to meet the following contraint
		//playerManager.drewAlreadyCurrentTurn && gameBoard.getVisibleTrainCarCards()[cardLocation] == TrackColor.None
		//but currently, i can not manipulate the playermanager object using existing code



		//Player can draw from the deck
		serverFacade.canDrawTrainCard(1, 1, 1);

		//Player can draw one from the visible cards and one from the deck
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 5));
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 3));

		//Player can draw two from the visible cards
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 3));
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 2));

		//	//Player can only draw one wild from the visible cards
		//Visible cards are reset if there are more than two wilds

		//how do you define wildcards?
	}

	@Test
	public void testDrawTrainCardFailAfterVisible() throws OutOfBoundsException, InternalServerException
	{
		//Player cannot select wild card after drawing one from visible cards
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 3));
		//Player cannot draw destination ticket after initial train draw until next turn
		//Player cannot build route after intial train draw until next turn

		//how do you handle those two cases??
		// using playerManager.drewAlreadyCurrentTurn && gameBoard.getVisibleTrainCarCards()[cardLocation] == TrackColor.None????
	}


	@Test
	public void testDrawTrainCard() throws PreConditionException, OutOfBoundsException, InternalServerException {

		serverFacade.drawTrainCard(1,1,3);
	}



	@Test
	public void testGetDestinations() {

		ServerFacade facade = ServerFacade.getServerFacade();
		int test1ID = 0;
		int test2ID = 0;
		int test3ID = 0;
		int test4ID = 0;

		//register multiple users
		try
		{
			test1ID = facade.register("test1", "test1");
			test2ID = facade.register("test2", "test2");
			test3ID = facade.register("test3", "test3");
			test4ID = facade.register("test4", "test4");


			TestGame game1 = new TestGame();
			TestGame game2 = new TestGame();
			int game1ID = game1.getGameID();
			int game2ID = game2.getGameID();
			facade.createGame(game1,test1ID,PlayerColor.Blue); //assume this game is started by test1 with Blue
			facade.createGame(game2,test3ID,PlayerColor.Blue); //assume this game is started by test3 with Blue

			facade.addPlayerToGame(test2ID, game1ID, PlayerColor.Red);
			facade.addPlayerToGame(test4ID, game2ID, PlayerColor.Red);

			//call canGetDestinations on game that has not yet started
			assertFalse(facade.canGetDestinations(test1ID, game1ID));

			facade.startGame(test1ID, game1ID);
			facade.startGame(test3ID, game2ID);

			//call canGetDestinations on game that has already finished
			//figure out how to update game2 so it is considered over
			game2.setGameOver(true);
			assertFalse(facade.canGetDestinations(test3ID, game2ID));

			//call canGetDestinations on game that does not exist
			assertFalse(facade.canGetDestinations(test1ID, -1));
			//assertFalse(facade.canGetDestinations(test1ID, null));

			//call canGetDestination on game with invalid user
			assertFalse(facade.canGetDestinations(-1, game1ID));

			//call canGetDestinations on game when not user's turn
			assertFalse(facade.canGetDestinations(test2ID, game1ID));

			//call canGetDestinations on game when not logged in
			facade.logout(test1ID);
			assertFalse(facade.canGetDestinations(test1ID, game1ID));
			facade.login("test1", "test1"); //ensure that logging back in worked

			//working example
			assertTrue(facade.canGetDestinations(test1ID, game1ID));

			//call canGetDestinations on game when already called draw train car
			if(facade.canDrawTrainCard(test1ID, game1ID, 0)) //try to ensure this test will happen
			{
				facade.drawTrainCard(test1ID, game1ID, 5);
				assertFalse(facade.canGetDestinations(test1ID, game1ID));
				facade.drawTrainCard(test1ID, game1ID, 5);
			}
			else
			{
				fail("Was unable to draw train card");
			}

			//get game in position where getDestinations returns true
			if(facade.canGetDestinations(test2ID, game1ID))
			{
				facade.getDestinations(test2ID, game1ID);
				//verify destination routes were added for user to consider
				TestPlayerManager manager = (TestPlayerManager)game1.getPlayerManager();
				Player player1 = manager.getPlayerByID(test2ID);
				assertTrue(player1.getDestinationRoutesToConsider().length > 0);
			}
			else
			{
				fail("Something went wrong. Unable to run all tests");
			}
			/*
		//call canGetDestinations on game when no destinations remain
		//figure out how to clear the list of destinations
		TestGameBoard board = (TestGameBoard)game1.getGameBoard();
		board.setDestinationRoutes(new List<DestinationRoute>());
		assertFalse(facade.canGetDestinations(test1ID, game1ID));
			 */
		}
		catch(AddUserException e) {
			fail("Something went wrong trying to register users");
		}
		catch(InternalServerException e) {
			fail("Something went wrong trying to register users");
		}
		catch (PreConditionException e) {
			e.printStackTrace();
			fail("Something went wrong");
		}
		catch (BadCredentialsException e) {
			e.printStackTrace();
			fail("Something went wrong");
		}
		catch (AlreadyLoggedInException e) {
			e.printStackTrace();
			fail("Something went wrong");
		}
		catch (OutOfBoundsException e) {
			e.printStackTrace();
			fail("Something went wrong");
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
			fail("Problems registering");
		}
	}

	@Test
	public void testSelectDestinations() {

		try
		{
			ServerFacade.firebomb();
			ServerFacade facade = ServerFacade.getServerFacade();
			int user1 = facade.register("test1", "test1");
			int user2 = facade.register("test2", "test2");
			int[] destinations = new int[]{0,1,2};

			TestGame game1 = new TestGame();
			int game1ID = game1.getGameID();

			facade.createGame(game1, user1, PlayerColor.Blue);

			facade.addPlayerToGame(user2, game1ID, PlayerColor.Red);

			facade.startGame(user1, game1ID);
			facade.selectDestinations(user1, game1ID, new int[]{0,1,2});
			facade.selectDestinations(user2, game1ID, new int[]{0,1,2});

			if (facade.canGetDestinations(user1, game1ID)) {
				facade.getDestinations(user1, game1ID);
			}
			else
			{
				fail("Something went wrong with getDestinations");
			}

			//Not the correct turn
			assertFalse(facade.canSelectDestinations(user2, game1ID, destinations));

			//Not logged in
			facade.logout(user1);
			assertFalse(facade.canSelectDestinations(user1, game1ID, destinations));
			facade.login("test1", "test1");

			//invalid inputs
			assertFalse(facade.canSelectDestinations(-1, game1ID, destinations));
			assertFalse(facade.canSelectDestinations(user1, -1, destinations));
			assertFalse(facade.canSelectDestinations(user1, game1ID, null));

			//valid usage
			assertTrue(facade.canSelectDestinations(user1, game1ID, destinations));
			facade.selectDestinations(user1, game1ID, destinations);
			//test results

			//selecting destinations updates player's score
			//update a player with routes such that they have a destination
			//update destination deck such that that is the only destination available
			//update so it is that player's turn
			//getDestinations
			//selectDestination
			//verify score increased by desired amount

			//selecting destinations ends the game
			//update current turn and finalTurnIndex such that this is final turn
			//getDestinations
			//verify game ended

			//selecting destinations when already drawn train card
			//update current turn
			TestPlayerManager manager = (TestPlayerManager)game1.getPlayerManager();
			manager.setCurrentTurnIndex(1);
			manager.getPlayerByID(user2).setDestinationRoutesToConsider(new DestinationRoute[]{null, null, null});
			if (facade.canDrawTrainCard(user2, game1ID, 0))
			{
				facade.drawTrainCard(user2, game1ID, 0);
				assertFalse(facade.canSelectDestinations(user2, game1ID, destinations));
			}
			else
			{
				fail("Could not draw train card");
			}

			/*
			 * 1,5,7,8,9
			 */
		}
		catch(AddUserException e)
		{
			e.printStackTrace();
			fail("Something went wrong.");
		} catch (InternalServerException e) {
			e.printStackTrace();
			fail("Something went wrong");
		} catch (PreConditionException e) {
			e.printStackTrace();
			fail("Something went wrong.");
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
			fail("Something went wrong with destination indices");
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			fail("Something went wrong with logging out");
		} catch (AlreadyLoggedInException e) {
			e.printStackTrace();
			fail("Problem logging in");
		} catch (InvalidCredentialsException e) {
			e.printStackTrace();
			fail("Problems registering");
		}
	}



	@Test
	public void testSendClientModelInformation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCityMapping() {
		fail("Not yet implemented");
	}


	public static void main(String[] args)
	{
		String[] testClasses = new String[]{
				"ServerFacade_Test"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}


	//something not sure if we need to test
	/**
	 * 	@Test
	public void testCanLeaveGame() {
	fail("Not yet implemented");
	}

	 @Test
	 public void testLeaveGame() {
	 fail("Not yet implemented");
	 }

	 @Test
	 public void testLogin() {
	 fail("Not yet implemented");
	 }

	 @Test
	 public void testLogout() {
	 fail("Not yet implemented");
	 }
	 @Test
	 public void testLoadGameState() {
	 fail("Not yet implemented");
	 }

	 @Test
	 public void testSaveGameState() {
	 fail("Not yet implemented");
	 }

	 @Test
	 public void testGetUserGames() {
	 fail("Not yet implemented");
	 }

	 @Test
	 public void testGetJoinableGames() {
	 fail("Not yet implemented");
	 }

	 @Test
	 public void testRegister() {
	 fail("Not yet implemented");
	 }
	 */

}