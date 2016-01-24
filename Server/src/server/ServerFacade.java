package server;

import java.util.List;

import model.CityToCityRoute;

public class ServerFacade {
	
	private List<Game> games;
	private List<User> users;
	
	public synchronized void createGame(Game newGame)
	{
		
	}
	
	public synchronized void addNewUser(User newUser)
	{
		
	}
	
	public void canAddPlayerToGame(int playerID, int gameID)
	{
		
	}
	
	public synchronized void addPlayerToGame(int playerID, int gameID)
	{
		
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
	
	public synchronized void login(String username, String password)
	{
		
	}
	
	public void logout(int playerID)
	{
		
	}
	
	public synchronized void register(String username,String password,String email)
	{
		
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