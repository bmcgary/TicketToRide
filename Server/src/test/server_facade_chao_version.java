package test;


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
import server.exception.AddUserException;
import server.exception.AlreadyLoggedInException;
import server.exception.BadCredentialsException;
import server.exception.GameOverException;
import server.exception.InternalServerException;
import server.exception.InvalidCredentialsException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;

public class server_facade_chao_version {
	
	/*
		serverFacade test Chao's version
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
	@Before
	public void buildGame_versionFailing() throws Exception{
	game1 = new TestGame();
	gameboard1 = new TestGameBoard();
	List<City> cities = new ArrayList<City>();
	List<CityToCityRoute> routes = new ArrayList<CityToCityRoute>();
	Map<Integer, List<CityToCityRoute>> currentRoutes = new HashMap<Integer,List<CityToCityRoute>>();
	List<DestinationRoute> destinationRoutes = new ArrayList<DestinationRoute>();
	List<TrackColor> deckTrainCarCards = new ArrayList<TrackColor>();
	TrackColor[] visibleTrainCarCards= new TrackColor[256];
	List<TrackColor> discardedTrainCarCards = new ArrayList<TrackColor>();
	City city1 = new City("city1");
	City city2 = new City("city2");
	City city3 = new City("city3");
	
	
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
	visibleTrainCarCards[3] = TrackColor.Green;
	discardedTrainCarCards.add(TrackColor.Black);
	discardedTrainCarCards.add(TrackColor.None);	
	
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
		try {
			serverFacade.register("","passdasdsad啦啦啦啦啦ada啦dasdasdasdasd");
		} catch (InternalServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * successful case
	 */
	@Test
	public void registeredSuccess() throws AddUserException
	{
		try {
			serverFacade.register("rightname","rightpass");
		} catch (InternalServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * existing object
	 */
	@Test//(expected = AddUserException.class)
	public void registeredUserNameexistobject() throws AddUserException
	{
		try {
			serverFacade.register("samename","samepass");
			serverFacade.register("samename","samepass");

		} catch (InternalServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// a test case says a user can switch to the login screen??  Is this handled by the server side..i thought
	//it should be a functionality in the augular.js???
	
	/*
	 * we add a new user to the game identified by his PASSWORD????? and username
	 * we are passing in the password as a parameter into the user object.. ?
	 * 
	 * 
	 */
	@Test// (expected=AddUserException.class)
	public void testAddNewUserWithExistingUserName() throws AddUserException, InternalServerException, InvalidCredentialsException {

		try {
			serverFacade.register("samename","samepass");
			serverFacade.register("samename","samepass");

		} catch (InternalServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*
	 * create game // how do we identify a game? by name, ID, or something else
	 * It seems we can create a game with empty input
	 * I personally think that currently the serverfacade is the one creating the game not the user.
	 * is that a problem??
	 */
	@Test (expected = PreConditionException.class)
	public void canStartGame() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException
	{
		int test1 = serverFacade.register("start1","test1");
		int test2 = serverFacade.register("start2","test2");
		int test3 = serverFacade.register("start3","test3");
		int test4 = serverFacade.register("start4","test4");
		int test5 = serverFacade.register("start5","test4");
		TestGame testGame1 = new TestGame();
		serverFacade.createGame(testGame1,test1,PlayerColor.Blue);
		//no enough player
		assertFalse(serverFacade.canStartGame(test1, testGame1.getGameID()));
		//add players test
		serverFacade.addPlayerToGame(test2, testGame1.getGameID(), PlayerColor.Red);
		serverFacade.addPlayerToGame(test3, testGame1.getGameID(), PlayerColor.Green);
		serverFacade.addPlayerToGame(test4, testGame1.getGameID(), PlayerColor.Yellow);
		
		serverFacade.startGame(test1, testGame1.getGameID());
		assertTrue(testGame1.containsPlayer(test1));
		assertTrue(testGame1.containsPlayer(test2));
		assertTrue(testGame1.containsPlayer(test3));
		assertTrue(testGame1.isStarted());
		serverFacade.addPlayerToGame(test5, testGame1.getGameID(), PlayerColor.Black);

	}
	/*
	 * failling to start a game
	 */
	@Test 
	public void canStartGameFaillingTests() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, BadCredentialsException
	{
		int test1 = serverFacade.register("start11","test1");
		int test2 = serverFacade.register("start22","test2");
		int test3 = serverFacade.register("start33","test3");
		int test4 = serverFacade.register("start44","test4");
		TestGame testGame1 = new TestGame();
		serverFacade.createGame(testGame1,test1,PlayerColor.Blue);
		//no enough player
		assertFalse(serverFacade.canStartGame(test1, testGame1.getGameID()));
		//add players test
		serverFacade.addPlayerToGame(test2, testGame1.getGameID(), PlayerColor.Red);
		serverFacade.addPlayerToGame(test3, testGame1.getGameID(), PlayerColor.Green);
		serverFacade.addPlayerToGame(test4, testGame1.getGameID(), PlayerColor.Yellow);
		assertFalse(serverFacade.canStartGame(test2, testGame1.getGameID()));
		serverFacade.leaveGame(test2, testGame1.getGameID());
		serverFacade.leaveGame(test2, testGame1.getGameID());


	}

	
	/*
	 * add player to the game
	 * A player can join a game without logging in..
	 */
	
	@Test
	public void addPlayer() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException
	{
		int test1 = serverFacade.register("start111","test1");
		int test2 = serverFacade.register("start222","test2");

		TestGame testGame1 = new TestGame();
		serverFacade.createGame(testGame1,test1,PlayerColor.Blue);
		assertFalse(serverFacade.canStartGame(test1, testGame1.getGameID()));
		serverFacade.addPlayerToGame(test2, testGame1.getGameID(), PlayerColor.Red);
		
	}
	/*
	 * player can join a game when there are 5 players already in the game
	 */
	@Test (expected=PreConditionException.class)
	public void addPlayerFullPlayers() throws PreConditionException, AddUserException, InternalServerException, InvalidCredentialsException
	{
		int test1 = serverFacade.register("full1","test1");
		int test2 = serverFacade.register("full12","test2");
		int test3 = serverFacade.register("full13","test3");
		int test4 = serverFacade.register("full14","test4");
		int test5 = serverFacade.register("full15","test4");

		TestGame testGame1 = new TestGame();
		serverFacade.createGame(testGame1,test1,PlayerColor.Blue);
		//no enough player
		assertFalse(serverFacade.canStartGame(test1, testGame1.getGameID()));
		//add players test
		serverFacade.addPlayerToGame(test2, testGame1.getGameID(), PlayerColor.Red);
		serverFacade.addPlayerToGame(test3, testGame1.getGameID(), PlayerColor.Green);
		serverFacade.addPlayerToGame(test4, testGame1.getGameID(), PlayerColor.Yellow);
		serverFacade.addPlayerToGame(test5, testGame1.getGameID(), PlayerColor.Blue);

		assertFalse(serverFacade.canStartGame(test2, testGame1.getGameID()));
		serverFacade.leaveGame(test2, testGame1.getGameID());
		serverFacade.leaveGame(test2, testGame1.getGameID());
	}
	/*
	 * player can join a game when it has started already
	 * It seems we can not start a game???
	 */
	@Test
	public void addPlayerGameAlreadyStarted() throws PreConditionException, InternalServerException, BadCredentialsException, AlreadyLoggedInException, AddUserException
	{
			//this test case was merged into canStartGame
	}
	
	/*
	 * play can not start a game when there is only a player
	 */
	
	@Test
	public void addPlayerOnlyOnePlayer() throws PreConditionException, InternalServerException
	{
			// this test case was merged into canStartGameFaillingTest
	}
	/*
	 * test login successful
	 */
	@Test 
	public void loginSuccessful() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InternalServerException, InvalidCredentialsException
	{
		serverFacade.register("loginSS", "password");
		//serverFacade.login("loginS", "password");
		
	}
	
	/*
	 * test login - wrong user name/password
	 */
	@Test(expected=BadCredentialsException.class)
	public void loginWrongCredientials() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
		serverFacade.login("nouschname", "mypassword");
			
	}
	
	/*
	 * test login - already logged in
	 */
	@Test(expected=AlreadyLoggedInException.class)
	public void loginAlreadyLoggedIn() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InternalServerException, InvalidCredentialsException
	{
		serverFacade.register("loginS", "password");
		serverFacade.login("loginS", "password");
			
	}
	
	/*
	 * can logout
	 */
	@Test
	public void logout() throws BadCredentialsException, AddUserException, AlreadyLoggedInException, InternalServerException, InvalidCredentialsException
	{
		int userID =serverFacade.register("logout", "test1");

		serverFacade.logout(userID);
	}
	//failed to log out
	@Test(expected=BadCredentialsException.class)
	public void logoutFailed() throws BadCredentialsException
	{
		serverFacade.logout(2);
		serverFacade.logout(19999);
	}

	
	
	/*
	 * test if a player can join the game if he has already joined
	 * the player can join a game that he has already joined
	 */
	@Test(expected=PreConditionException.class)
	public void joinAlreadyJoinedGame() throws AddUserException, BadCredentialsException, AlreadyLoggedInException, InternalServerException, PreConditionException, InvalidCredentialsException
	{
		int test1 = serverFacade.register("join1","test1");
		int test2 = serverFacade.register("join2","test2");
		int test3 = serverFacade.register("join3","test3");


		TestGame testGame1 = new TestGame();
		serverFacade.createGame(testGame1,test1,PlayerColor.Blue);
		//no enough player
		assertFalse(serverFacade.canStartGame(test1, testGame1.getGameID()));
		//add players test
		serverFacade.addPlayerToGame(test2, testGame1.getGameID(), PlayerColor.Red);
		serverFacade.addPlayerToGame(test3, testGame1.getGameID(), PlayerColor.Green);
		//serverFacade.addPlayerToGame(test3, testGame1.getGameID(), PlayerColor.Green);
		serverFacade.addPlayerToGame(100002, testGame1.getGameID(), PlayerColor.Black);

		assertFalse(serverFacade.canStartGame(test2, testGame1.getGameID()));
		serverFacade.leaveGame(test2, testGame1.getGameID());
		serverFacade.leaveGame(test2, testGame1.getGameID());

	}
	/*
	 * we can add players with duplicate color
	 */
	@Test
	public void duplicateColor() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
				//this test case was handled in the previous canStartGame();
	}
	
	/*
	 * user can join a game when he is not logged in
	 */
	@Test
	public void canJoinAgamewhenNotLogin() throws AddUserException, BadCredentialsException, AlreadyLoggedInException
	{
		//this test case was handled in the previous canStartGame();

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
	
	///start-> buy routes logic testing
	
	
	@Test 
	public void BuyRouteWrongInputsTest() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException
	{

		TestGame game = new TestGame();
		int user1 = serverFacade.register("buyRoute1", "test1");
		int user2 = serverFacade.register("buyRoute2", "test2");
		serverFacade.createGame(game, user1, PlayerColor.Green);
		
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Blue);
		serverFacade.startGame(user1, game.getGameID());
		
		//have to selectDestinations First before can buy routes!!!
		
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {1,0});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});
		
		Map<TrackColor,Integer> addCards = new HashMap<TrackColor,Integer>();
		for(int i =0; i < TrackColor.values().length;i++)
		{
			if(!addCards.containsKey(TrackColor.values()[i]))
			{
				addCards.put(TrackColor.values()[i],5);
			}
		}

		City start = new City("Seattle");
		City start2 = new City("Seattle_2");
		City start3 = new City("Seattle_3");
		game.getPlayerManager().addTrainCarCards(user1, addCards);
		game.getPlayerManager().addTrainCarCards(user2, addCards);
		City end = new City("Provo");
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Green);
		CityToCityRoute route2 = new CityToCityRoute(start2, end, 5, TrackColor.Blue);
		CityToCityRoute route3 = new CityToCityRoute(start3, end, 5, TrackColor.Orange);


		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Green, 5);
		
		assertFalse(serverFacade.canBuyRoute(0, game.getGameID(), route2, cards));
		assertFalse(serverFacade.canBuyRoute(0,0,null,null));

		assertFalse(serverFacade.canBuyRoute(user1, game.getGameID(), route3, cards));

		assertFalse(serverFacade.canBuyRoute(user1, game.getGameID(), route, null));
	}
	
	/*
	 * 
	 * buy route should success case
	 * test may have problems
	 */
	@Test
	public void BuyRouteSuccess() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException
	{
		int user1 = serverFacade.register("buyR11", "test1");
		int user2 = serverFacade.register("buyR22", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, user1, PlayerColor.Black);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Yellow);
		
		
		serverFacade.startGame(user1, game.getGameID());
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {1,0});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});
		
		
		Map<TrackColor,Integer> addCards = new HashMap<TrackColor,Integer>();
		//give 15 cards this time
		for(int i =0; i < TrackColor.values().length;i++)
		{
			if(!addCards.containsKey(TrackColor.values()[i]))
			{
				addCards.put(TrackColor.values()[i],10);
			}
		}
		game.getPlayerManager().addTrainCarCards(user1, addCards);
		game.getPlayerManager().addTrainCarCards(user2, addCards);
		
		City start = new City("Beijing");
		City end = new City("L A");
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Black);
		
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Black, 5);
		
		assertFalse(serverFacade.canBuyRoute(user1, game.getGameID(), route, cards));
		//serverFacade.buyRoute(user1, game.getGameID(), route, cards);

		
		
		TestPlayerManager CurrentP = (TestPlayerManager)game.getPlayerManager();
		
		TestPlayer player1 = (TestPlayer)CurrentP.getPlayerByID(user1);
		assertEquals(player1.getNumTrainsLeft(),45);
		

		List<TrackColor> discarded = game.getGameBoard().getDiscardedTrainCarCards();
		assertEquals(discarded.size(),0);

		
	
	}
	@Test(expected = PreConditionException.class)
	public void faillingToBuyRoutes() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException, BadCredentialsException, AlreadyLoggedInException, GameOverException
	{
		int user1 = serverFacade.register("buyRFaill1", "test1");
		int user2 = serverFacade.register("buyRFaill2", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, user1, PlayerColor.Black);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Yellow);
		
		
		serverFacade.startGame(user1, game.getGameID());
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {1,0});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});
		
		
		Map<TrackColor,Integer> addCards = new HashMap<TrackColor,Integer>();
		for(int i =0; i < TrackColor.values().length;i++)
		{
			if(!addCards.containsKey(TrackColor.values()[i]))
			{
				addCards.put(TrackColor.values()[i],5);
			}
		}
		game.getPlayerManager().addTrainCarCards(user1, addCards);
		game.getPlayerManager().addTrainCarCards(user2, addCards);
		
		City start = new City("Seattle33");
		City end = new City("Provo33");
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Green);
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Green, 5);
		
		//have problems
		//assertFalse(serverFacade.canBuyRoute(3333, game.getGameID(), route, cards));
		
		assertFalse(serverFacade.canBuyRoute(user2, game.getGameID(), route, cards));
		
		game.getPlayerManager().drewAlreadyCurrentTurn = true;
		assertFalse(serverFacade.canBuyRoute(user1, game.getGameID(), route, cards));
				
		game.getPlayerManager().advanceTurn();
		serverFacade.getDestinations(user1, game.getGameID());
		
		assertFalse(serverFacade.canBuyRoute(user1, game.getGameID(), route, cards));
		
		serverFacade.selectDestinations(user1, game.getGameID(), new int[]{0});
		
	}
	@Test
	public void BuyRouteClaimedRoutesAlready() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException, GameOverException
	{
		int user1 = serverFacade.register("buyRFail122", "test1");
		int user2 = serverFacade.register("buyRFail233", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, user1, PlayerColor.Black);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Yellow);
		
		
		serverFacade.startGame(user1, game.getGameID());
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {1,0});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});
		
		
		Map<TrackColor,Integer> addCards = new HashMap<TrackColor,Integer>();
		for(int i =0; i < TrackColor.values().length;i++)
		{
			if(!addCards.containsKey(TrackColor.values()[i]))
			{
				addCards.put(TrackColor.values()[i],5);
			}
		}
		game.getPlayerManager().addTrainCarCards(user1, addCards);
		game.getPlayerManager().addTrainCarCards(user2, addCards);
		
		City start = new City("Seattle333");
		City end = new City("Provo333");
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Green);
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();
		cards.put(TrackColor.Green, 5);
		game.getGameBoard().claimRoute(user1, route);
		assertFalse(serverFacade.canBuyRoute(user2, game.getGameID(), route, cards));
		
	//	assertFalse(serverFacade.canBuyRoute(user2, game.getGameID(), route, cards));
		
		
	//	game.getPlayerManager().drewAlreadyCurrentTurn = true;
	//	assertFalse(serverFacade.canBuyRoute(user1, game.getGameID(), route, cards));
				
	//	game.getPlayerManager().advanceTurn();
	//	serverFacade.getDestinations(user1, game.getGameID());
		
	//	assertFalse(serverFacade.canBuyRoute(user1, game.getGameID(), route, cards));
		
		

	}
	@Test
	public void BuyRouteWrongNoEnoughResources() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException, GameOverException
	{
		int user1 = serverFacade.register("buyRFail1", "test1");
		int user2 = serverFacade.register("buyRFail2", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, user1, PlayerColor.Black);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Yellow);
		
		
		serverFacade.startGame(user1, game.getGameID());
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {1,0});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});
		
		
		Map<TrackColor,Integer> addCards = new HashMap<TrackColor,Integer>();
		for(int i =0; i < TrackColor.values().length;i++)
		{
			if(!addCards.containsKey(TrackColor.values()[i]))
			{
				addCards.put(TrackColor.values()[i],5);
			}
		}
		game.getPlayerManager().addTrainCarCards(user1, addCards);
		game.getPlayerManager().addTrainCarCards(user2, addCards);
		
		City start = new City("Seattle3333");
		City end = new City("Provo3333");
		
		CityToCityRoute route1 = new CityToCityRoute(start, end, 3, TrackColor.Green);
		Map<TrackColor,Integer> cards = new HashMap<TrackColor,Integer>();

		assertFalse(serverFacade.canBuyRoute(user2, game.getGameID(), route1, cards));
		CityToCityRoute route = new CityToCityRoute(start, end, 5, TrackColor.Green);
		cards.put(TrackColor.Green, 5);
		
		game.getPlayerManager().advanceTurn();
		
		//insufficient train cards
		Map<TrackColor,Integer> tooFew = new HashMap<TrackColor,Integer>();
		tooFew.put(TrackColor.Green, 4);
		assertFalse(serverFacade.canBuyRoute(user2, game.getGameID(), route, tooFew));
		

		assertFalse(serverFacade.canBuyRoute(user2, game.getGameID(), route, cards));
		
		game.getPlayerManager().advanceTurn();
		
		///no enough cards
		game.getPlayerByIndex(0).useTrains(36);//should fail
		assertFalse(serverFacade.canBuyRoute(user1, game.getGameID(), route, cards));
		
		//change to second person's turn

		
	}
	/*
	 * 
	 * test draw card -> success case
	 * currently failed
	 */
	@Test
	public void DrawCardSuccess() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException
	{
		int user1 = serverFacade.register("drawCards1", "PASSWORD");
		int user2 = serverFacade.register("drawCards2", "PASSWORDD");
		int user3 = serverFacade.register("drawCards3", "PASSWORDD");
		int user4 = serverFacade.register("drawCards4", "PASSWORDD");


		
		TestGame game = new TestGame();
		serverFacade.createGame(game, user1, PlayerColor.Green);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Yellow);
		serverFacade.addPlayerToGame(user3, game.getGameID(), PlayerColor.Blue);
		serverFacade.addPlayerToGame(user4, game.getGameID(), PlayerColor.Red);

		
		serverFacade.startGame(user1, game.getGameID());
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(user3, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(user4, game.getGameID(), new int[] {1,0});
		List<TrackColor> allCards = game.getGameBoard().getDeckTrainCarCards();
		TrackColor[] visibleCards = game.getGameBoard().getVisibleTrainCarCards();

		//get track of players
		Player player1 = game.getPlayerByIndex(0);
		Player player2 = game.getPlayerByIndex(1);
		Player player3 = game.getPlayerByIndex(2);
		Player player4 = game.getPlayerByIndex(3);
		
		
		Map<TrackColor,Integer> Cards1 = player1.getTrainCarCards();
		Map<TrackColor,Integer> Cards2 = player2.getTrainCarCards();
		Map<TrackColor,Integer> Cards3 = player3.getTrainCarCards();
		Map<TrackColor,Integer> Cards4 = player4.getTrainCarCards();


		visibleCards[0] = TrackColor.Blue;
		visibleCards[1] = TrackColor.Orange;
		visibleCards[2] = TrackColor.Green;
		visibleCards[3] = TrackColor.Black;
		
		serverFacade.drawTrainCard(user1, game.getGameID(), 0);
		assertTrue(game.getPlayerManager().drewAlreadyCurrentTurn);
		//assertEqual(Cards3.get(TrackColor.Blue),2);
		//assertEqual(Cards1.get(TrackColor.Black),1);

		serverFacade.drawTrainCard(user1, game.getGameID(), 1);
		//assertNotEquals(Cards1.get(TrackColor.Blue),2);

		allCards.add(0, TrackColor.Orange);
		serverFacade.drawTrainCard(user2, game.getGameID(), 5);
		//not turn yet
		//serverFacade.drawTrainCard(user3, game.getGameID(), 5);


	}

	/*
	 * 
	 * wrong inputs
	 */
	@Test (expected=OutOfBoundsException.class)
	public void drawCardWrongInputsFaillingCases() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException
	{
		int user1 = serverFacade.register("wrongI1", "test1");
		int user2 = serverFacade.register("wronI2", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, user1, PlayerColor.Green);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Blue);
		serverFacade.startGame(user1, game.getGameID());
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {1,0});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});
		
		//no card initialized yet / wrong user id
		assertFalse(serverFacade.canDrawTrainCard(1111, game.getGameID(), 0));
		
		game.getGameBoard().getDeckTrainCarCards().clear();
		assertFalse(serverFacade.canDrawTrainCard(user1, game.getGameID(), 5));
		serverFacade.canDrawTrainCard(user1, game.getGameID(), -1);

		//serverFacade.canDrawTrainCard(user1, game.getGameID(), 6);

	}
	
	@Test (expected=OutOfBoundsException.class)
	public void drawCardNotiNITIALIZED() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException
	{
		int user1 = serverFacade.register("wrongI11", "test1");
		int user2 = serverFacade.register("wronI22", "test2");
		
		TestGame game = new TestGame();
		serverFacade.createGame(game, user1, PlayerColor.Green);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Blue);
		serverFacade.startGame(user1, game.getGameID());
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {1,0});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});		
		assertFalse(serverFacade.canDrawTrainCard(1111, game.getGameID(), 0));
		serverFacade.canDrawTrainCard(user1, game.getGameID(), 6);

	}
	/*
	 * 
	 * draw card failing cases
	 */
	@Test
	public void DrawCardMoreFailingCases() throws AddUserException, InternalServerException, InvalidCredentialsException, PreConditionException, OutOfBoundsException, BadCredentialsException, AlreadyLoggedInException
	{
		int user1 = serverFacade.register("dMore1", "test1");
		int user2 = serverFacade.register("dMore2", "test2");
		int user3 = serverFacade.register("dMore3", "test2");

		
		TestGame game = new TestGame();
		serverFacade.createGame(game, user1, PlayerColor.Green);
		serverFacade.addPlayerToGame(user2, game.getGameID(), PlayerColor.Blue);
		serverFacade.addPlayerToGame(user3, game.getGameID(), PlayerColor.Red);

		serverFacade.startGame(user1, game.getGameID());
		serverFacade.selectDestinations(user1, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(user2, game.getGameID(), new int[] {0,1});
		serverFacade.selectDestinations(user3, game.getGameID(), new int[] {0,1});
		
		assertFalse(serverFacade.canDrawTrainCard(user3, game.getGameID(), 0));
		//id is  a problem?
		assertFalse(serverFacade.canDrawTrainCard(9999, game.getGameID(), 0));

		//have destinations already
		serverFacade.getDestinations(user1, game.getGameID());
		assertFalse(serverFacade.canDrawTrainCard(user1, game.getGameID(), 0));
	}
	

	
	/*
	 * test tie score needs to be done  (end game logic)
	 * two people have longest routes. Both of them should receive 10 points
	 */
	@Test
	public void testTieScore()
	{
		//currently working on
	}



}

