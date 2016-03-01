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
	public void testCanStartGame() throws PreConditionException, InternalServerException {
		User user1 = new User("user1","password1");
		User user2 = new User("user2","password2");
		
		serverFacade.startGame(1, 1);
	}
	
	//currently need add some functionalities to allow me to create a game
	@Test
	public void testStartGame() {
		User user1 = new User("user1","password1");
		User user2 = new User("user2","password2");
		
	}




	@Test
	public void testCanBuyRoute() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuyRoute() {
		fail("Not yet implemented");
	}
	
	//Player can draw two from the visible cards
	//Player can only draw one wild from the visible cards
	//Visible cards are reset if there are more than two wilds
	//Player cannot select wild card after drawing one from visible cards
	//Player cannot draw destination ticket after initial train draw until next turn
	//Player cannot build route after intial train draw until next turn
	
	//I can not add a playmanager object to a game...!
	@Test (expected=OutOfBoundsException.class)
	public void testCanDrawTrainCardFailling() throws OutOfBoundsException, InternalServerException {
		serverFacade.canDrawTrainCard(1, 1, 10);

	}
	
	@Test 
	public void testCanDrawTrainCard() throws OutOfBoundsException, InternalServerException {
		//Player can draw from the deck
		serverFacade.canDrawTrainCard(1, 1, 1);
		
		//Player can draw one from the visible cards and one from the deck
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 5));
		assertTrue(serverFacade.canDrawTrainCard(1, 1, 3));




	}

	@Test
	public void testDrawTrainCard() {

		fail("Not yet implemented");
	}

	/**
	 * Player can scroll through list of destination tickets in hand
When destinations are exhausted, deck no longer appears
When less than three destinations are left, card(s) do not appear on the modal
Player cannot draw a train card after drawing destination
Player cannot build a route after drawing destination
When destinations are completed upon drawing, status is automatically changed
When destinations are drawn they appear in the player's hand
	 */
	@Test
	public void testCanGetDestinations() {

		fail("Not yet implemented");
	}
	/**
	 * Player can select a route on the map to purchase
Player can use wilds as part of a route purchase
Player cannot purchase a route with less than the required cards
Player cannot purchase a route with more than the required cards
Player cannot purchase a route with cards of the wrong color
Player cannot purchase a route they do not have enough trains for
Player can cancel buying a route
Player cannot buy adjacent route if less than 4 players in the game
Routes are populated with trains the color of the player who purchased them
Player cannot draw a destination ticket after building
Player cannot draw a train card after building
Player's destination cards are updated when building the route completes the destination
	 */
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
