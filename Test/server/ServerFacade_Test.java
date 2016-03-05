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
import model.PlayerColor;
import model.TrackColor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.ServerFacade;
import server.exception.AddUserException;
import server.exception.InternalServerException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;

public class ServerFacade_Test {
	
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
	// current no way to create a gameboard...or do I miss something here?
	//May you show me a way to add a gameboard to a game?
	@Before
	public void buildGame_version1() throws Exception{
	game1 = new Game();
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
	
	}

	@Test
	public void testGetServerFacade() {
		assertNotEquals(serverFacade.getServerFacade(),null);
	}

	/*
	 * we could throw different exceptions if the game is null, or gameboard is null, or playermanager..
	 * I think the createGame function has logic problem.  Even if we have nothing inside the playermanager,
	 * it still passes junit tests
	 */
	@Test
	public void testCreateGame() {
		serverFacade.createGame(game1);
	}
	
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
	 * we need more getters, especially you define all variables as private. We can not even access them. For example, we may need a getUsers...
	 */
	@Test 
	public void testAddNewUserSuccessful() throws AddUserException {
		User user  = new User("user","password");
		serverFacade.addNewUser(user);	
		//assertEquals(serverFacade.g)  checking if an user is added successfully into a game
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
	 * I need a lot of functions to connect different objects together.
	 * For example, may you show me how to add a game to games? 
	 * or add players to a game?

	 */
	
	@Test(expected=PreConditionException.class)
	public void testStartGame() throws PreConditionException, InternalServerException {

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
			System.out.println("Something went wrong trying to register users");
			System.out.println("DON'T TRUST TEST RESULTS");
		}	
		catch(InternalServerException e)
		{
			System.out.println("Something went wrong trying to register users");
			System.out.println("DON'T TRUST TEST RESULTS");
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

	/**
	 * Player can select a route on the map to purchase



	 */
	@Test
	public void testCanBuyRoute() throws InternalServerException {
		City city1 = new City(new Point(2,2),"LA");
		City city2 = new City(new Point(3,3),"SA");
		CityToCityRoute ctoc1 = new CityToCityRoute(city1,city2,3,TrackColor.Orange);

		serverFacade.canBuyRoute(1,1,ctoc1);
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
	


	
	//I can not add a playmanager object to a game...!
	@Test (expected=OutOfBoundsException.class)
	public void testCanDrawTrainCardFailling() throws OutOfBoundsException, InternalServerException {
		serverFacade.canDrawTrainCard(1, 1, 10);

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
		//same problem, I can not manipulate playermanager to check if it indeed update cards.
		serverFacade.drawTrainCard(1,1,3);
	}

	//Player can scroll through list of destination tickets in hand
	//???junit test for this case??
	
	//When less than three destinations are left, card(s) do not appear on the modal
	//When destinations are exhausted, deck no longer appears
	//When destinations are completed upon drawing, status is automatically changed
	//When destinations are drawn they appear in the player's hand
	//are you handling those cases???
	///
	//Player cannot draw a train card after drawing destination
	//Player cannot build a route after drawing destination
	
	/**


	 */
	@Test
	public void testCanGetDestinations() {

		fail("Not yet implemented");
	}

	@Test
	public void testGetDestinations() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanSelectDestinations() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectDestinations() {
		fail("Not yet implemented");
	}



	@Test
	public void testSendClientModelInformation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCityMapping() {
		fail("Not yet implemented");
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
