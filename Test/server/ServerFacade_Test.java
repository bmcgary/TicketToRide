package server;

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
import server.exception.AddUserException;
import server.exception.AlreadyLoggedInException;
import server.exception.BadCredentialsException;
import server.exception.InternalServerException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;

public class ServerFacade_Test {
	


	//buy routes, draw train card
	
	/*
	 * do you think we need a card class?
	 */
	
	/*
	 * all class variables (such as cities, routes) in the gameboard class are private
	 * then how could we create a gameboard object? see the code below. same problem for the game class
	 * don't we need some setters? For example, in the game class, we should have a setter called setGameboard
	 * so we can create a gameboard and add it to the game.
	 */
	
	/*

	 */
	ServerFacade serverFacade;
	TestGame game1; //version 1
	TestGameBoard gameboard1;
	
	TestGame game2;//version2
	TestGameBoard gameboard2;
	
	TestGame game3;//version3
	TestGameBoard gameboard3;
	

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
	// current no way to create a gameboard...or do I miss something here?
	//May you show me a way to add a gameboard to a game?
	@Before
	public void buildGame_version1() throws Exception{
	game1 = new TestGame();
	gameboard1 = new TestGameBoard();
	List<City> cities = new ArrayList<City>();
	List<CityToCityRoute> routes = new ArrayList<CityToCityRoute>();
	Map<Integer, List<CityToCityRoute>> currentRoutes = new HashMap<Integer,List<CityToCityRoute>>();
	List<DestinationRoute> destinationRoutes = new ArrayList<DestinationRoute>();
	List<TrackColor> deckTrainCarCards = new ArrayList<TrackColor>();
	TrackColor[] visibleTrainCarCards= new TrackColor[256];
	List<TrackColor> discardedTrainCarCards = new ArrayList<TrackColor>();
	City city1 = new City("LA");
	City city2 = new City("SA");
	City city3 = new City("GA");
	
	
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
	
	
	}

	@Before
	public void buildGame_version2() throws Exception{
	List<City> cities = new ArrayList<City>();
	List<CityToCityRoute> routes = new ArrayList<CityToCityRoute>();
	Map<Integer, List<CityToCityRoute>> currentRoutes = new HashMap<Integer,List<CityToCityRoute>>();
	List<DestinationRoute> destinationRoutes = new ArrayList<DestinationRoute>();
	List<TrackColor> deckTrainCarCards = new ArrayList<TrackColor>();
	TrackColor[] visibleTrainCarCards= new TrackColor[256];
	List<TrackColor> discardedTrainCarCards = new ArrayList<TrackColor>();
	City city1 = new City("LA");
	City city2 = new City("SA");
	City city3 = new City("GA");
	
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
	
	}
	
	@Before
	public void buildGame_version3() throws Exception{
	List<City> cities = new ArrayList<City>();
	List<CityToCityRoute> routes = new ArrayList<CityToCityRoute>();
	Map<Integer, List<CityToCityRoute>> currentRoutes = new HashMap<Integer,List<CityToCityRoute>>();
	List<DestinationRoute> destinationRoutes = new ArrayList<DestinationRoute>();
	List<TrackColor> deckTrainCarCards = new ArrayList<TrackColor>();
	TrackColor[] visibleTrainCarCards= new TrackColor[256];
	List<TrackColor> discardedTrainCarCards = new ArrayList<TrackColor>();
	City city1 = new City("LA");
	City city2 = new City("SA");
	City city3 = new City("GA");
	
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
	
	}
	
	@Test
	public void testGetServerFacade() {
		assertNotEquals(serverFacade.getServerFacade(),null);
	}
	/* error:
	 * not handle if a user name is empty not handle is username is larger than 25 char.
	 * not checking if a user enters his/her email...we require a user to enter his/her email??
	 * I can even enter a chinese character..this is a creepy test case. 
	 */
	@Test
	public void registered() throws AddUserException
	{
		User user = new User("","passdasdsad啦啦啦啦啦ada啦dasdasdasdasdaasdasd");
		serverFacade.addNewUser(user);
		
	}
	/*
	 * successful case
	 */
	@Test
	public void registeredSuccess() throws AddUserException
	{
		User user = new User("name","pass");
		serverFacade.addNewUser(user);	
	}
	/*
	 * existing object
	 */
	@Test(expected = AddUserException.class)
	public void registeredUserNameexistobject() throws AddUserException
	{
		User user = new User("name","pass");
		serverFacade.addNewUser(user);	
		serverFacade.addNewUser(user);	

	}
	// a test case says a user can switch to the login screen??  Is this handled by the server side..i thought
	//it should be a functionality in the augular.js???
	
	/*
	 * we add a new user to the game identified by his PASSWORD????? and username
	 * we are passing in the password as a parameter into the user object.. ?
	 * 
	 * 
	 */
	@Test (expected=AddUserException.class)
	public void testAddNewUserWithExistingUserName() throws AddUserException {
		User user  = new User("user","password");
		User user1  = new User("user","password");
		serverFacade.addNewUser(user);
		serverFacade.addNewUser(user1);	
	}
	/*
	 * create game // how do we identify a game? by name, ID, or something else
	 * It seems we can create a game with empty input
	 * I personally think that currently the serverfacade is the one creating the game not the user.
	 * is that a problem??
	 */
	@Test
	public void createGame()
	{
		TestGame game = new TestGame();
		//User user = new User("testUser","password");
		//user.
	//	serverFacade.createGame(game, 0, null);
	}
	
	
	/*
	 * add player to the game
	 * A player can join a game without logging in..
	 */
	
	@Test
	public void addPlayer()
	{
		TestGame game = new TestGame();
		User user = new User("testUser","password");
		System.out.println(user.loggedIn);
		user.joinGame(game.getGameID());
		
	}
	/*
	 * player can join a game when there are 5 players already in the game
	 */
	@Test
	public void addPlayerFullPlayers()
	{
		TestGame game = new TestGame();
		User user1 = new User("user1","password");
		User user2 = new User("user2","password");
		User user3 = new User("user3","password");
		User user4 = new User("user4","password");
		User user5 = new User("user5","password");
		User user6 = new User("user6","password");
		user1.joinGame(game.getGameID());
		user2.joinGame(game.getGameID());
		user3.joinGame(game.getGameID());
		user4.joinGame(game.getGameID());
		user5.joinGame(game.getGameID());
		user6.joinGame(game.getGameID());
	}
	/*
	 * player can join a game when it has started already
	 * It seems we can not start a game???
	 */
	@Test
	public void addPlayerGameAlreadyStarted() throws PreConditionException, InternalServerException, BadCredentialsException, AlreadyLoggedInException, AddUserException
	{
		TestGame game = new TestGame();
		User user1 = new User("user111","password");
		User user2 = new User("user222","password");
		User user3 = new User("user333","password");
		User user4 = new User("user444","password");
		User user5 = new User("user555","password");
		serverFacade.addNewUser(user1);
		serverFacade.addNewUser(user2);
		serverFacade.addNewUser(user3);
		serverFacade.addNewUser(user4);
		serverFacade.addNewUser(user5);

		serverFacade.login("user111","password");
		serverFacade.login("user222","password");
		serverFacade.login("user333","password");
		serverFacade.login("user444","password");
		serverFacade.login("user555","password");

		serverFacade.createGame(game, user1.playerID, PlayerColor.Green);
	//	serverFacade.addPlayerToGame(user1.getPlayerID(), game.getGameID(), PlayerColor.Black);
	//	serverFacade.addPlayerToGame(user2.getPlayerID(), game.getGameID(), PlayerColor.Blue);
	//	serverFacade.addPlayerToGame(user3.getPlayerID(), game.getGameID(), PlayerColor.Green);
	//	serverFacade.addPlayerToGame(user4.getPlayerID(), game.getGameID(), PlayerColor.Red);


		user1.joinGame(game.getGameID());
		user2.joinGame(game.getGameID());
		user3.joinGame(game.getGameID());
		user4.joinGame(game.getGameID());
		//user5.joinGame(game.getGameID());
		serverFacade.startGame(user1.playerID, game.getGameID());
	}
	/*
	 * play can not start a game when there is only a player
	 */
	
	@Test(expected=PreConditionException.class)
	public void addPlayerOnlyOnePlayer() throws PreConditionException, InternalServerException
	{
		TestGame game = new TestGame();
		User user1 = new User("user111","password");
		serverFacade.startGame(user1.playerID, game.getGameID());
	}
	/*
	 * test login successful
	 */
	@Test 
	public void loginSuccessful() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
		User user = new User("myname11","mypassword");
		serverFacade.addNewUser(user);
		serverFacade.login("myname11", "mypassword");
		
		
	}
	
	/*
	 * test login - wrong user name/password
	 */
	@Test(expected=BadCredentialsException.class)
	public void loginWrongCredientials() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
		User user = new User("myname22","mypassword");
		serverFacade.addNewUser(user);
		serverFacade.login("myname23", "mypassword");
			
	}
	
	/*
	 * test login - already logged in
	 */
	@Test(expected=AlreadyLoggedInException.class)
	public void loginAlreadyLoggedIn() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
		User user = new User("mynameL","mypassword");
		serverFacade.addNewUser(user);
		serverFacade.login("mynameL", "mypassword");
		serverFacade.login("mynameL", "mypassword");

			
	}
	
	/*
	 * can logout
	 */
	@Test
	public void logout() throws BadCredentialsException, AddUserException, AlreadyLoggedInException
	{
		User user = new User("mynamelogout","mypassword");
		serverFacade.addNewUser(user);
		serverFacade.login("mynamelogout", "mypassword");
		serverFacade.logout(user.playerID);
	}
	//failed to log out
	@Test(expected=BadCredentialsException.class)
	public void logoutFailed() throws BadCredentialsException
	{
		
		serverFacade.logout(2);
		serverFacade.logout(19999);

	}
	
	/*
	 * user can not logout another user
	 * this test case doese not make sense in the server side
	 * we are testing from the serverFacade perspective and we actually can logout another user
	 * I am not sure if I understand this test case correctly
	 */
	
	
	
	
	
	/*
	 * test if a player can join the game if he has already joined
	 * the player can join a game that he has already joined
	 */
	@Test
	public void joinAlreadyJoinedGame() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
		TestGame game = new TestGame();
		User user1 = new User("already","password");

		serverFacade.addNewUser(user1);
		serverFacade.login("already","password");
		user1.joinGame(game.getGameID());
		user1.joinGame(game.getGameID());

	}
	/*
	 * we can add players with duplicate color
	 */
	@Test
	public void duplicateColor() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
		TestGame game = new TestGame();
		User user1 = new User("duplicate1","password");
		User user2 = new User("duplicate2","password");

		serverFacade.addNewUser(user1);
		serverFacade.addNewUser(user2);

		serverFacade.login("duplicate1","password");
		serverFacade.login("duplicate2","password");


		serverFacade.createGame(game);
		serverFacade.addPlayerToGame(user1.getPlayerID(), game.getGameID(), PlayerColor.Black);
		serverFacade.addPlayerToGame(user2.getPlayerID(), game.getGameID(), PlayerColor.Black);
	}
	
	/*
	 * user can join a game when he is not logged in
	 */
	@Test
	public void canJoinAgamewhenNotLogin() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
		TestGame game = new TestGame();
		User user1 = new User("notLogIn","password");
		serverFacade.addNewUser(user1);
		serverFacade.createGame(game);
		user1.joinGame(game.getGameID());
	}
	
	/**
	 * things to need to review to tomorrow again 
	 * buy route
	 * draw train card
	 * @throws AddUserException 
	 * @throws AlreadyLoggedInException 
	 * @throws BadCredentialsException 
	 * @throws OutOfBoundsException 
	 * @throws InternalServerException 
	 * @throws PreConditionException 
	 */
	
	/*
	 * can buy a route
	 * it seems we can not buy a route. I am missing something here.
	 * 
	 */
	@Test
	public void buyRoute() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, PreConditionException, InternalServerException, OutOfBoundsException
	{
		User user = new User("canBuy","password");
		User user2 = new User("canBuy2","password");
		User user3 = new User("canBuy3","password");
		User user4 = new User("canBuy4","password");

		serverFacade.addNewUser(user);
		serverFacade.addNewUser(user2);
		serverFacade.addNewUser(user3);
		serverFacade.addNewUser(user4);
		serverFacade.login("canBuy","password");
		serverFacade.login("canBuy2","password");
		serverFacade.login("canBuy3","password");
		serverFacade.login("canBuy4","password");


		City city1 = new City("LA");
		City city2 = new City("SA");
		CityToCityRoute ctoc1 = new CityToCityRoute(city1,city2,3,TrackColor.Orange);
		//ctoc1.
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Orange, 8);
		TestGameBoard testGB = new TestGameBoard();
		testGB.getRoutes().add(ctoc1);
		TestGame game = new TestGame();
		game.setGameBoard(testGB);
		serverFacade.createGame(game, user.playerID, PlayerColor.Black);
		serverFacade.addPlayerToGame(user2.playerID, game.getGameID(),PlayerColor.Blue);
		serverFacade.addPlayerToGame(user3.playerID, game.getGameID(),PlayerColor.Green);
		serverFacade.addPlayerToGame(user4.playerID, game.getGameID(),PlayerColor.Yellow);
		serverFacade.startGame(user.playerID, game.getGameID());
	//	serverFacade.
		serverFacade.buyRoute(user.playerID, game.getGameID(), ctoc1, cards);
		//no enough resources
		
		cards.put(TrackColor.Orange, 0);

		
		assertFalse(serverFacade.getAllGames().get(game.getGameID()).getPlayerManager().canBuyTrack(user.playerID, 2));
		assertFalse(serverFacade.getAllGames().get(game.getGameID()).getPlayerManager().canBuyTrackWithCards(user.playerID, 2, TrackColor.Black, cards));
	
	}
	
	/*
	 * can not buy a route when not log in
	 */
	@Test
	public void buyRouteNotLogin() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InternalServerException
	{
		User user = new User("buyrouteNotLogin","password");
		serverFacade.addNewUser(user);
		//serverFacade.login("buyrouteNotLogin","password");
		City city1 = new City("LA");
		City city2 = new City("SA");	
		CityToCityRoute ctoc1 = new CityToCityRoute(city1,city2,3,TrackColor.Orange);
		assertFalse(serverFacade.isPlayerLoggedIn(user.playerID));
		assertFalse(serverFacade.canBuyRoute(user.playerID, 1, ctoc1, new HashMap<TrackColor, Integer>()));
	}

	/*
	 * we need getters to get games info in the facade
	 */

	@Test
	public void testAddPlayerToGame() {
		assertFalse(serverFacade.canAddPlayerToGame(1,2,PlayerColor.Blue));//no game id
		
		assertFalse(serverFacade.canAddPlayerToGame(1,1,PlayerColor.Red));//wrong color
		
		assertFalse(serverFacade.canAddPlayerToGame(1,10,PlayerColor.Red));//no game
	}
	/*
	 * 	// no way to create a game currently


	 */
	/*
	 * can draw train card
	 */
	@Test
	public void canDraw() throws AddUserException, InternalServerException, PreConditionException, OutOfBoundsException, BadCredentialsException, AlreadyLoggedInException
	{
		TestGame testGame = new TestGame();
		TestGameBoard testBoard = new TestGameBoard();
		TrackColor[] t = new TrackColor[10];
		t[0] = TrackColor.Black;
		t[1] = TrackColor.Green;
		testBoard.setVisibleTrainCarCards(t);
		User user = new User("canDraw","password");
		serverFacade.register("canDraw", "password");
		//serverFacade.login("canDraw", "password");
		serverFacade.createGame(testGame, user.playerID, PlayerColor.Red);
		System.out.println(serverFacade.getAllGames().get(testGame.getGameID()).getPlayerManager().isPlayersTurn(user.playerID));

		//serverFacade.getAllGames().get(testGame.getGameID()).getPlayerManager().isPlayersTurn(user.playerID);
		serverFacade.drawTrainCard(user.playerID, testGame.getGameID(), 1);

	}
	
	 
	/*
	 * can not draw train card - when not log in- not current turn- invalide input - no train cards exist
	 */
	@Test
	public void canNotDraw()
	{
		//you are not checking if the current game started
	}

	/*
	 * draw card can trigger the end of the game
	 */
	@Test
	public void drawTriggerEnd()
	{
		
	}
	
	@Test(expected=PreConditionException.class)
	public void testStartGame() throws PreConditionException, InternalServerException {
		//Note: this test will not work properly until Issue #13 is resolved
		fail("Note: this test will not work properly until Issue #13 is resolved");
		ServerFacade facade = ServerFacade.getServerFacade();
		int test1ID;
		int test2ID;
		int test3ID;
		int test4ID;
		
		//register multiple users
		try
		{
			test1ID = facade.register("test1", "test1");
			test2ID = facade.register("test2", "test2");
			test3ID = facade.register("test3", "test3");
			test4ID = facade.register("test4", "test4");
		}
		catch(AddUserException e)
		{
			fail("Something went wrong trying to register users");
			//System.out.println("Something went wrong trying to register users");
			//System.out.println("DON'T TRUST TEST RESULTS");
		}	
		catch(InternalServerException e)
		{
			fail("Something went wrong trying to register users");
			//System.out.println("Something went wrong trying to register users");
			//System.out.println("DON'T TRUST TEST RESULTS");
		}
		
		//create two games
		Game game1 = new Game();
		Game game2 = new Game();
		int game1ID = game1.getGameID();
		int game2ID = game2.getGameID();
		facade.createGame(game1); //assume this game is started by test1 with Blue
		facade.createGame(game2); //assume this game is started by test3 with Blue
		
		//call canStartGame with only one user in game.
		assertFalse(facade.canStartGame(test1ID, game1ID));
		
		//call canStartGame with user who did not create it.
		facade.addPlayerToGame(test2ID, game1ID, PlayerColor.Red);
		assertFalse(facade.canStartGame(test2ID, game1ID))
		
		//logout with one user
		//call canStartGame with logged out user (should be user who created game)
		facade.addPlayerToGame(test4ID, game2ID, PlayerColor.Red);
		facade.logout(test3ID);
		assertFalse(facade.canStartGame(test3ID, game2ID));
		
		//call canStartGame with user that does not exist
		int nonExistantID = -1;
		assertFalse(facade.canStartGame(nonExistantID, game1ID))
		
		//call canStartGame with game that does not exist
		assertFalse(facade.canStartGame(test1ID, nonExistantID));
		assertFalse(facade.canStartGame(test1ID, null));
		
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


	
//	Player can cancel buying a route 
	//not handle or it should not be a function on the server side?
	
//	Routes are populated with trains the color of the player who purchased them

	

	


	
	//I can not add a playmanager object to a game...!
	@Test (expected=OutOfBoundsException.class)
	public void testCanDrawTrainCardFailling() throws OutOfBoundsException, InternalServerException {
		serverFacade.canDrawTrainCard(1, 1, 10);

	}
	
	@Test 
	public void testCanDrawTrainCard() throws OutOfBoundsException, InternalServerException {
		

		
		
		
		serverFacade.canDrawTrainCard(1, 1, 1);
		
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 5));
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 3));
		
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 3));
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 2));
		

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
		//same problem, I can not manipulate playermanager to check if it indeed update cards.
		serverFacade.drawTrainCard(1,1,3);
	}

	

	@Test
	public void testGetDestinations() { //Note: this test will not work properly until Issue #13 is resolved
		
		fail("Note: this test will not work properly until Issue #13 is resolved");
		
		ServerFacade facade = ServerFacade.getServerFacade();
		int test1ID;
		int test2ID;
		int test3ID;
		int test4ID;
		
		//register multiple users
		try
		{
			test1ID = facade.register("test1", "test1");
			test2ID = facade.register("test2", "test2");
			test3ID = facade.register("test3", "test3");
			test4ID = facade.register("test4", "test4");
		}
		catch(AddUserException e)
		{
			fail("Something went wrong trying to register users");
			//System.out.println("Something went wrong trying to register users");
			//System.out.println("DON'T TRUST TEST RESULTS");
		}	
		catch(InternalServerException e)
		{
			fail("Something went wrong trying to register users");
			//System.out.println("Something went wrong trying to register users");
			//System.out.println("DON'T TRUST TEST RESULTS");
		}
		
		TestGame game1 = new TestGame();
		TestGame game2 = new TestGame();
		int game1ID = game1.getGameID();
		int game2ID = game2.getGameID();
		facade.createGame(game1); //assume this game is started by test1 with Blue
		facade.createGame(game2); //assume this game is started by test3 with Blue
		
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
		assertFalse(facade.canGetDestinations(test1ID, null));
		
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
			facade.drawTrainCard(test1ID, game1ID, 1);
			assertFalse(facade.canGetDestinations(test1ID, game1ID));
		}
		else
		{
			fail("Was unable to draw train card");
		}
		
		//get game in position where getDestinations returns true
		if(facade.canGetDestinations(test1ID, game1ID))
		{
			facade.getDestinations(test1ID, game1ID);
			//verify destination routes were added for user to consider
			TestPlayerManager manager = (TestPlayerManager)game1.getPlayerManager();
			Player player1 = manager.getPlayerByID(test1ID);
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








}
