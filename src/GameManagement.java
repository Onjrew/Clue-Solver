import java.util.Scanner;
import java.util.ArrayList;

public class GameManagement 
{
	ArrayList<Player> playerArray = new ArrayList<Player>();
	static Player currentPlayer;
	static Solution gameSolution = new Solution();
	static GameStatusAnalyzer gameStatus = new GameStatusAnalyzer();

	public void gameSetup()
	{	
		System.out.println("How many players are there?");
		
		String enteredNumber;
		Scanner scan = new Scanner(System.in);
		enteredNumber = scan.nextLine();
		int numberOfPlayers = Integer.parseInt(enteredNumber);
		
		System.out.println("=== Finished setting the number of players. ===\n\n");
		
		for(int i = 0; i < numberOfPlayers; i++)
		{
			System.out.println("What is player " + (i+1) + "'s name?");
			String playerName = scan.nextLine();
			
			Player newPlayer = new Player();
			newPlayer.setName(playerName);
			
			playerArray.add(newPlayer);
			
		}
		
		currentPlayer = playerArray.get(0);
		
		System.out.println("\n\n=== Finished initializing players. ===\n\n");
		
	}//gameSetup()
	
	
	public void nextPlayer(Player cP)
	{	
		//System.out.print("Current player changed from " + currentPlayer.getName() + " to ");
		if(playerArray.indexOf(cP) == (playerArray.size() - 1))
		{
			currentPlayer = playerArray.get(0);
		}
		else
		{
			currentPlayer = playerArray.get(playerArray.indexOf(cP) + 1);
		}
		//System.out.println(currentPlayer.getName() + ".");
		
	}//nextPlayer()
	
	
	public void gameLoop()
	{	
		int indexOfCurrentPlayer;
		boolean running = true, inner;
		Scanner scan = new Scanner(System.in);
		String response = "";
		ArrayList<String> suggestedCards = new ArrayList<String>();
		
		while(running)
		{
			//[0]	Get new suggestion for solution, suggested by the Current Player
			suggestedCards.add("Knife");
			suggestedCards.add("Colonel Mustard");
			suggestedCards.add("Lounge");
			
			indexOfCurrentPlayer = playerArray.indexOf(currentPlayer);
			System.out.println(currentPlayer.getName() + " makes their suggestion.");
			inner = true;
			while(inner)
			{
				//[1] Change the selected player and get response to suggestion
				this.nextPlayer(currentPlayer);
				System.out.println("Selected player changed to " + currentPlayer.getName());
				System.out.println("Did " + currentPlayer.getName() + " Pass or Play?");
				response = scan.nextLine();
				
				//[2] 	If the selected player Passes
				if(response.equals("Pass"))
				{
					System.out.println(currentPlayer.getName() + " passed.");
					
					//[2a] Call Relevant GameStatusAnalyzer methods
					gameStatus.removeFromPossibleCards(suggestedCards, currentPlayer, this);
				}
				//[3]	If the selected player Plays
				else if(response.equals("Play"))
				{
					System.out.println(currentPlayer.getName() + " played.");
					
					//[3a] Call relevant GameStatusAnalyzer methods
					
					currentPlayer = playerArray.get(indexOfCurrentPlayer);
					this.nextPlayer(currentPlayer);
					//[3b]	Go back to [0]
					inner = false;
				}
				else if(response.equals("Quit"))
				{
					inner = false;
					running = false;
				}
				else 
				{
					System.out.println("\nUnrecognised command.\n");
					currentPlayer = playerArray.get(indexOfCurrentPlayer);
					inner = false;
				}
			}
		}
		System.out.println("\n=== Game Loop Exited ===");
		
	}//gameLoop()
	
	
	public static void main(String args[]) 
	{	
		GameManagement newGame = new GameManagement();
		//newGame.gameSetup();
		//newGame.gameLoop();
		
		TesterClass test = new TesterClass();
		test.test(newGame);
		
	}//main()

}//GameManagement
