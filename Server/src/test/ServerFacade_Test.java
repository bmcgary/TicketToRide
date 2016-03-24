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
import model.TestPlayer;
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
import server.exception.GameOverException;
import server.exception.InternalServerException;
import server.exception.InvalidCredentialsException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;

public class ServerFacade_Test {

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

	@After
	public void firebombServerFacade()
	{
		//TODO should this actually be @Before?
		//Call firebomb on ServerFacade after each test
		//ensure each test starts fresh
		ServerFacade.firebomb();
	}

	@Test
	public void testGetServerFacade() {
		assertNotEquals(ServerFacade.getServerFacade(),null);
	}
	
	//*********************************************************************************
	//REGISTER TESTS
	
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

	@Test(expected=BadCredentialsException.class)
	public void testLogoutFailed() throws BadCredentialsException
	{
		serverFacade.logout(2);
		serverFacade.logout(19999);
	}
	
	//*********************************************************************************
	//CREATE GAME TESTS
	
	@Test
	public void testCreateGame() throws AddUserException, InternalServerException, InvalidCredentialsException, BadCredentialsException, AlreadyLoggedInException, PreConditionException
	{
		int userID = serverFacade.register("test1", "test1");
		TestGame game = new TestGame();
		
		//null color
		try{
		serverFacade.createGame(game, userID, null);
		fail("should have thrown exception, null color");
		}
		catch (PreConditionException e) {}
		
		//null game
		try{
		serverFacade.createGame(null, userID, PlayerColor.Black);
		fail("should have thrown exception, null game");
		}
		catch (PreConditionException e) {}
		
		//nonexistant player
		try{
		serverFacade.createGame(game, -1, PlayerColor.Blue);
		fail("should have thrown exception, nonexistant player");
		}
		catch (PreConditionException e) {}
		
		//logged out
		serverFacade.logout(userID);
		try{
		serverFacade.createGame(game, userID, PlayerColor.Blue);
		fail("should have thrown exception, not logged in");
		} 
		catch (PreConditionException e) {}
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

	@Test
	public void testAddPlayerSuccessCase() throws InvalidCredentialsException, AddUserException, InternalServerException, PreConditionException
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
	
	@Test
	public void testAddPlayerAlreadyJoinedGame() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException, InternalServerException, PreConditionException
	{
		int userID = serverFacade.register("joinAlready", "joinedGame");
		Game newGame = new Game();
		serverFacade.createGame(newGame, userID, PlayerColor.Black);
		
		//Try as the same color and as a new color
		assertFalse(serverFacade.canAddPlayerToGame(userID, newGame.getGameID(), PlayerColor.Black));
		assertFalse(serverFacade.canAddPlayerToGame(userID, newGame.getGameID(), PlayerColor.Red));
	}
	
	@Test(expected = PreConditionException.class)
	public void testAddPlayerDuplicateColor() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException, InternalServerException, PreConditionException
	{
		TestGame game = new TestGame();

		int id1 = serverFacade.register("duplicate1","password");
		int id2 = serverFacade.register("duplicate2","password");

		serverFacade.createGame(game, id1, PlayerColor.Black);
		serverFacade.addPlayerToGame(id2, game.getGameID(), PlayerColor.Black);
	}
	
	@Test
	public void testAddPlayerNotLoggedIn() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InvalidCredentialsException, InternalServerException, PreConditionException
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
	public void testAddPlayerInvalidInputs() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException
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

	//*********************************************************************************
	//START GAME TESTS
	
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
			TestGame game1 = new TestGame();
			TestGame game2 = new TestGame();
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

			//more in-depth test
			game1.getHistory();
			TestGameBoard board = (TestGameBoard)game1.getGameBoard();
			TestPlayerManager manager = (TestPlayerManager)game1.getPlayerManager();
			
			//12 * 8 = 96 regular train cards; + 14 wilds; -5 for visible cards -(4*2) for opening hands
			assertTrue(board.getDeckTrainCarCards().size() == 97);
			//30 destination routes; -(3*2) for player's hands
			assertTrue(board.getDestinationRoutes().size() == 24);
			assertTrue(board.getDiscardedTrainCarCards().size() == 0);
			board.getCities();
			board.getRoutes();
			for (TrackColor c : board.getVisibleTrainCarCards())
			{
				assertTrue(c != null);
			}
			
			assertFalse(manager.drewAlreadyCurrentTurn);
			assertTrue(manager.getCurrentTurnIndex() == 0);
			assertTrue(manager.getNumPlayers() == 2);
			assertTrue(manager.getRound()==0);
			Player player1 = manager.getPlayerByID(test1ID);
			assertTrue(player1.getDestinationRoutesToConsider().size() == 3);
			assertTrue(player1.getPlayerColor() == PlayerColor.Blue);
			assertTrue(player1.getTrainsLeft() == 45);
			assertFalse(player1.hasLongestRoute());
			int totalTrainCards = 0;
			for (Integer i : player1.getTrainCarCards().values())
			{
				totalTrainCards += i;
			}
			assertTrue(totalTrainCards == 4);
			assertTrue(player1.getDestinationRoute().size() == 0);

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

	//*********************************************************************************
	//LEAVE GAME TESTS
	
	@Test
	public void testLeaveGame()
	{
		fail("Not yet implemented in ServerFacade");
	}
	
	//*********************************************************************************
	//BUY ROUTE TESTS
	//TODO verify/rewrite all these tests
	
	@Test
	public void testBuyRouteInvalidInputs() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException
	{
		int id1 = serverFacade.register("test1", "test1");
		int id2 = serverFacade.register("test2", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, id1, PlayerColor.Black);
		serverFacade.addPlayerToGame(id2, game.getGameID(), PlayerColor.Blue);
		serverFacade.startGame(id1, game.getGameID());
		serverFacade.selectDestinations(id1, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(id2, game.getGameID(), new int[] {0,1});
		
		//give everyone all da train cards
		Map<TrackColor,Integer> tenCardsEach = new HashMap<TrackColor,Integer>();
		for (TrackColor tc : TrackColor.values())
		{
			tenCardsEach.put(tc, 10);
		}
		game.getPlayerManager().addTrainCarCards(id1, tenCardsEach);
		game.getPlayerManager().addTrainCarCards(id2, tenCardsEach);
		
		City start = new City("Portland");
		City end = new City("San Francisco");
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Green);
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Green, 5);
		
		//invalid or null inputs
		assertFalse(serverFacade.canBuyRoute(-1, game.getGameID(), route, cards));
		assertFalse(serverFacade.canBuyRoute(id1, -1, route, cards));
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), null, cards));
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), route, null));
		
		//nonexistant citytocityroute
		City badStart = new City("Narnia");
		City badEnd = new City("Hogwarts");
		CityToCityRoute badRoute = new CityToCityRoute(badStart, badEnd, 5, TrackColor.Green);
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), badRoute, cards));
		
		//wrong color
		Map<TrackColor,Integer> reds = new HashMap<TrackColor,Integer>();
		reds.put(TrackColor.Red, 5);
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), route, reds));
	}
	
	@Test
	public void testBuyRouteInvalidPreconditions() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException, BadCredentialsException, AlreadyLoggedInException
	{
		int id1 = serverFacade.register("test1", "test1");
		int id2 = serverFacade.register("test2", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, id1, PlayerColor.Black);
		serverFacade.addPlayerToGame(id2, game.getGameID(), PlayerColor.Blue);
		
		//game not started
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), 
				new CityToCityRoute(null, null, id2, null), 
				new HashMap<TrackColor,Integer>()));
		
		serverFacade.startGame(id1, game.getGameID());
		serverFacade.selectDestinations(id1, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(id2, game.getGameID(), new int[] {0,1});
		
		//give everyone all da train cards
		Map<TrackColor,Integer> tenCardsEach = new HashMap<TrackColor,Integer>();
		for (TrackColor tc : TrackColor.values())
		{
			tenCardsEach.put(tc, 10);
		}
		game.getPlayerManager().addTrainCarCards(id1, tenCardsEach);
		game.getPlayerManager().addTrainCarCards(id2, tenCardsEach);
		
		City start = new City("Portland");
		City end = new City("San Francisco");
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Green);
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Green, 5);
		
		//not logged in
		serverFacade.logout(id1);
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), route, cards));
		
		//not correct turn
		assertFalse(serverFacade.canBuyRoute(id2, game.getGameID(), route, cards));
		
		serverFacade.login("test1", "test1");
		
		//already drew train cards
		game.getPlayerManager().drewAlreadyCurrentTurn = true;
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), route, cards));
		
		game.getPlayerManager().drewAlreadyCurrentTurn = false;
		
		//game over
		game.setGameOver(true);
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), route, cards));
		game.setGameOver(false);
		
		//already got destinations
		serverFacade.getDestinations(id1, game.getGameID());
		
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), route, cards));
		
		serverFacade.selectDestinations(id1, game.getGameID(), new int[]{0});
		
		//route already claimed
		game.getGameBoard().claimRoute(id1, route);
		assertFalse(serverFacade.canBuyRoute(id2, game.getGameID(), route, cards));

	}
	
	@Test
	public void testBuyRouteInsufficientResources() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException, GameOverException
	{
		int id1 = serverFacade.register("test1", "test1");
		int id2 = serverFacade.register("test2", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, id1, PlayerColor.Black);
		serverFacade.addPlayerToGame(id2, game.getGameID(), PlayerColor.Blue);
		serverFacade.startGame(id1, game.getGameID());
		serverFacade.selectDestinations(id1, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(id2, game.getGameID(), new int[] {0,1});
		
		//give everyone all da train cards
		Map<TrackColor,Integer> tenCardsEach = new HashMap<TrackColor,Integer>();
		for (TrackColor tc : TrackColor.values())
		{
			tenCardsEach.put(tc, 10);
		}
		game.getPlayerManager().addTrainCarCards(id1, tenCardsEach);
		game.getPlayerManager().addTrainCarCards(id2, tenCardsEach);
		
		City start = new City("Portland");
		City end = new City("San Francisco");
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Green);
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Green, 5);
		
		//insufficient train cars
		Player player1 = game.getPlayerByIndex(0);
		player1.useTrains(41);
		assertFalse(serverFacade.canBuyRoute(id1, game.getGameID(), route, cards));
		game.getPlayerManager().advanceTurn();
		
		//insufficient train cards
		Map<TrackColor,Integer> tooFew = new HashMap<TrackColor,Integer>();
		tooFew.put(TrackColor.Green, 4);
		assertFalse(serverFacade.canBuyRoute(id2, game.getGameID(), route, tooFew));
		
		TestPlayerManager manager = (TestPlayerManager)game.getPlayerManager();
		Player player2 = manager.getPlayerByID(id2);
		Map<TrackColor, Integer> playerCards = player2.getTrainCarCards();
		playerCards.put(TrackColor.Green, 4);
		playerCards.put(TrackColor.None, 0);
		assertFalse(serverFacade.canBuyRoute(id2, game.getGameID(), route, cards));

		
		//what happens if they give more than enough cards?
	}
	
	@Test
	public void testBuyRouteSuccessCases() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException
	{
		int id1 = serverFacade.register("test1", "test1");
		int id2 = serverFacade.register("test2", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, id1, PlayerColor.Black);
		serverFacade.addPlayerToGame(id2, game.getGameID(), PlayerColor.Blue);
		serverFacade.startGame(id1, game.getGameID());
		serverFacade.selectDestinations(id1, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(id2, game.getGameID(), new int[] {0,1});
		
		//give everyone all da train cards
		Map<TrackColor,Integer> tenCardsEach = new HashMap<TrackColor,Integer>();
		for (TrackColor tc : TrackColor.values())
		{
			tenCardsEach.put(tc, 10);
		}
		game.getPlayerManager().addTrainCarCards(id1, tenCardsEach);
		game.getPlayerManager().addTrainCarCards(id2, tenCardsEach);
		
		City start = new City("Portland");
		City end = new City("San Francisco");
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Green);
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Green, 5);
		
		assertTrue(serverFacade.canBuyRoute(id1, game.getGameID(), route, cards));
		serverFacade.buyRoute(id1, game.getGameID(), route, cards);
		
		//player gets route
		Map<Integer,List<CityToCityRoute>> currentRoutes = game.getGameBoard().getCurrentRoutes();
		assertTrue(currentRoutes.containsKey(id1));
		assertTrue(currentRoutes.get(id1).size() == 1);
		assertTrue(currentRoutes.get(id1).get(0).equals(route));
		
		//turn advances
		TestPlayerManager manager = (TestPlayerManager)game.getPlayerManager();
		assertTrue(manager.getCurrentTurnIndex() == 1);
		
		//trains go down
		TestPlayer player1 = (TestPlayer)manager.getPlayerByID(id1);
		assertTrue(player1.getNumTrainsLeft() == 40);
		
		//cards are discarded
		//points update correctly
		//can trigger final round
		//can trigger game end
		//can complete destination(s)
		//can update longest road when appropriate
		//can with combo of color and wilds
	}
	
	//*********************************************************************************
	//DRAW TRAIN CARD TESTS
	//TODO verify/rewrite all these tests

	@Test
	public void testDrawTrainCardInvalidInputs()
	{
		//invalid or null inputs
		//index out of range for visible
	}
	
	@Test
	public void testDrawTrainCardInvalidPreconditions()
	{
		//not logged in
		//not correct turn
		//no cards remaining
	}
	
	@Test
	public void testDrawTrainCardSuccessCases()
	{
		//the following cases should all advance the turn, add cards to player, etc...
			//drawing 2 cards from the deck works
			//drawing a single wild from visible works
			//drawing 2 non-wilds from visible works
			//drawing 1-and-1 works when visible is not wild
		//can trigger end game
		
		
	}

	//*********************************************************************************
	//GET DESTINATIONS TESTS

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
			facade.selectDestinations(test1ID, game1ID, new int[]{0,1,2});
			facade.selectDestinations(test2ID, game1ID, new int[]{0,1,2});
			assertTrue(facade.canGetDestinations(test1ID, game1ID));

			//call canGetDestinations on game when already called draw train car
			if(facade.canDrawTrainCard(test1ID, game1ID, 0)) //try to ensure this test will happen
			{
				facade.drawTrainCard(test1ID, game1ID, 5);
				assertFalse(facade.canGetDestinations(test1ID, game1ID));
				
				//set turn index to next player
				TestPlayerManager manager = (TestPlayerManager)game1.getPlayerManager();
				manager.drewAlreadyCurrentTurn = false;
				manager.setCurrentTurnIndex(1);
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
				assertTrue(player1.getDestinationRoutesToConsider().size() > 0);
				assertFalse(facade.canGetDestinations(test2ID, game1ID));
				facade.selectDestinations(test2ID, game1ID, new int[]{0,1,2});
			}
			else
			{
				fail("Something went wrong. Unable to run all tests");
			}
			
			//cannot get destinations when none remain
			assertTrue(facade.canGetDestinations(test1ID, game1ID));
			TestGameBoard board = (TestGameBoard)game1.getGameBoard();
			board.setDestinationRoutes(new ArrayList<DestinationRoute>());
			assertFalse(facade.canGetDestinations(test1ID, game1ID));
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
	
	//*********************************************************************************
	//SELECT DESTINATIONS TESTS

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
			
			//test selecting only one destination on first round
			try
			{
				facade.selectDestinations(user1, game1ID, new int[]{0});
				fail("incorrectly allowed player to select 1 dest in first round");
			}
			catch (PreConditionException e){}
			
			
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

			TestPlayerManager manager = (TestPlayerManager)game1.getPlayerManager();

			//valid usage
			assertTrue(facade.canSelectDestinations(user1, game1ID, destinations));
			int dests = manager.getPlayerByID(user1).getDestinationRoute().size();
			facade.selectDestinations(user1, game1ID, destinations);
			//test results
			assertTrue(manager.getPlayerByID(user1).getDestinationRoute().size() == dests+3);
			assertTrue(manager.getCurrentTurnIndex() == 1);
			assertTrue(manager.getPlayerByID(user1).getDestinationRoutesToConsider() == null);

			//TODO verify a selected destination will correctly be marked as completed when necessary
			
			manager.setCurrentTurnIndex(1);
			manager.getPlayerByID(user2).setDestinationRoutesToConsider(null);
			
			//selecting when haven't previously called getDestinations
			assertFalse(facade.canSelectDestinations(user2, game1ID, new int[]{1}));
			
			//selecting destinations when already drawn train card
			if (facade.canDrawTrainCard(user2, game1ID, 0))
			{
				facade.drawTrainCard(user2, game1ID, 0);
				assertFalse(facade.canSelectDestinations(user2, game1ID, destinations));
				manager.setDrewAlreadyCurrentTurn(false);
				manager.setCurrentTurnIndex(0);
			}
			else
			{
				fail("Could not draw train card");
			}
			
			//selecting destinations ends the game
			//update current turn and finalTurnIndex such that this is final turn
			manager.setFinalTurnIndex(1);
			facade.getDestinations(user1, game1ID);
			facade.selectDestinations(user1,game1ID, new int[]{0});
			//verify game ended
			assertTrue(game1.isGameOver());
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
	public void testLoadGameState() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveGameState() {
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

	/**
	 * 	
	 @Test
	 public void testGetUserGames() {
	 fail("Not yet implemented");
	 }

	 @Test
	 public void testGetJoinableGames() {
	 fail("Not yet implemented");
	 }

	 */

}