package server;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.ServerFacade;

public class ServerFacade_test {
	ServerFacade serverFacade;
	/**
	 * //are you using the singleton design pattern to create the serverFacade object??? It seems you made the constructor to be private.
	 //I can not create one here. So I temporarily changed the visibility of the serverfacade constructor to the default public...
	 */
	
	@Before
	public void setUp()
	{
		serverFacade = new ServerFacade();
		serverFacade.getServerFacade();
	}
	/**
	 * why getserverfacade is static???
	 */
	@Test
	public void testGetServerFacade() {
	
		assertNotEquals(serverFacade.getServerFacade(),null);
	}

	@Test
	public void testCreateGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddNewUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanAddPlayerToGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddPlayerToGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanStartGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartGame() {
		fail("Not yet implemented");
	}

	@Test
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
	public void testRegister() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanBuyRoute() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuyRoute() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanDrawTrainCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testDrawTrainCard() {
		fail("Not yet implemented");
	}

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
	public void testLoadGameState() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveGameState() {
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

	@Test
	public void testGetJoinableGames() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserGames() {
		fail("Not yet implemented");
	}

}
