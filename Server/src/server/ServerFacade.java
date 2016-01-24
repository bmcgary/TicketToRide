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
	
	public synchronized void canAddPlayerToGame(int playerID, int gameID)
	{
		
	}
	
	public synchronized void addPlayerToGame(int playerID, int gameID)
	{
		
	}
	
	public synchronized void canStartGame()
	{
		
	}
	
	public synchronized void startGame()
	{
		
	}
	
	public synchronized void canLeaveGame()
	{
		
	}
	
	public synchronized void leaveGame()
	{
		
	}
	
	public synchronized void login(String username, String password)
	{
		
	}
	
	public void logout()
	{
		
	}
	
	public synchronized void register(String username,String password,String email)
	{
		
	}
	
	public synchronized void canBuyRoute(int playerID, int gameID, CityToCityRoute route)
	{
		
	}
	
	public synchronized void buyRoute(int playerID, int gameID, CityToCityRoute route)
	{
		
	}
	
	public synchronized void canDrawTrainCard()
	{
		
	}
	
	public synchronized  void drawTrainCard()
	{
		
	}
	
	public synchronized void canGetDestinations()
	{
		
	}
	
	public synchronized void getDestinations()
	{
		
	}
	
	public synchronized void canSelectDestinations()
	{
		
	}
	
	public synchronized void selectDestinations()
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
	
	public synchronized void getCityMapping()
	{
		
	}
}