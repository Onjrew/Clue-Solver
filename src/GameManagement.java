import java.util.Scanner;
import java.util.ArrayList;

public class GameManagement 
{
	ArrayList<Player> playerArray = new ArrayList<Player>();
	Scanner scan = new Scanner(System.in);
	static Player currentPlayer;
	static Solution gameSolution = new Solution();
	static GameStatusAnalyzer gameStatus = new GameStatusAnalyzer();
	static TesterClass tester = new TesterClass();
	ArrayList<String> cardPool = new ArrayList<String>();
	int numberOfPlayers = 4, numberOfCards, numOfTurns = 0;
	String playerName;
	boolean AUTOPLAY = true, AUTODEAL = true;

	public void gameSetup()
	{	
		if(!AUTOPLAY)
		{
			System.out.println("How many players are there?");
			String enteredNumber;
			enteredNumber = scan.nextLine();
			numberOfPlayers = Integer.parseInt(enteredNumber);
			System.out.println("=== Finished setting the number of players. ===\n\n");
		}
		
		//Create all the players
		for(int i = 0; i < numberOfPlayers; i++)
		{
			if(!AUTOPLAY)
			{
				System.out.println("What is player " + (i+1) + "'s name?");
				playerName = scan.nextLine();
			}
			else 
			{
				playerName = "Player " + i;
				System.out.println(playerName + " created.");
			}
			Player newPlayer = new Player(cardPool);
			newPlayer.setName(playerName);
			if(!AUTOPLAY)
			{
				System.out.println("How many cards do they have?");
				newPlayer.numOfHeldCards = Integer.parseInt(scan.nextLine());
			}
			playerArray.add(newPlayer);	
		}
		
		System.out.println("\n\n=== Finished initializing players. ===\n\n");
		
		//Weapons
		this.cardPool.add("Knife");				//0
		this.cardPool.add("Candlestick");		//1
		this.cardPool.add("Lead Pipe");			//2
		this.cardPool.add("Revolver");			//3
		this.cardPool.add("Rope");				//4
		this.cardPool.add("Wrench");			//5
		
		//Characters
		this.cardPool.add("Miss Scarlet");		//6
		this.cardPool.add("Colonel Mustard");	//7
		this.cardPool.add("Mrs. White");		//8
		this.cardPool.add("Mr. Green");			//9
		this.cardPool.add("Mrs. Peacock");		//10
		this.cardPool.add("Professor Plum");	//11
		
		//Locations
		this.cardPool.add("Kitchen");			//12
		this.cardPool.add("Ballroom");			//13
		this.cardPool.add("Conservatory");		//14
		this.cardPool.add("Dining Room");		//15
		this.cardPool.add("Library");			//16
		this.cardPool.add("Billiard Room");		//17
		this.cardPool.add("Lounge");			//18
		this.cardPool.add("Hall");				//19
		this.cardPool.add("Study");				//20
		
		if(AUTOPLAY)
		{
			for(int i = 0; i < cardPool.size(); i++)
			{
				playerArray.get(i % playerArray.size()).numOfHeldCards++;
			}
			
			for(int i = 0; i < playerArray.size(); i++)
			{
				System.out.println(playerArray.get(i).getName() + " has " + playerArray.get(i).numOfHeldCards + " cards.");
			}
		}
		
		if(AUTODEAL)
		{
			tester.setHeldCards(cardPool, this);
			
			for(int i = 0; i < playerArray.size(); i++)
			{
				System.out.println("\n" + playerArray.get(i).getName() + " has:");
				for(int j = 0; j < playerArray.get(i).heldCards.size(); j++)
				{
					System.out.println(playerArray.get(i).heldCards.get(j));
				}
			}
		}
		
		currentPlayer = playerArray.get(0);
		
	}//gameSetup()
	
	
	public void nextPlayer(Player cP)
	{	
		System.out.print("\n=== Current player changed from " + currentPlayer.getName() + " to ");
		if(playerArray.indexOf(cP) == (playerArray.size() - 1))
		{
			currentPlayer = playerArray.get(0);
		}
		else
		{
			currentPlayer = playerArray.get(playerArray.indexOf(cP) + 1);
		}
		System.out.println(currentPlayer.getName() + " ===\n");
		
	}//nextPlayer()
	
	
	public void gameLoop()
	{	
		int indexOfCurrentPlayer;
		boolean running = true, inner;
		//Scanner scan = new Scanner(System.in);
		String response = "";
		String suggestingPlayerName = null;
		ArrayList<String> suggestedCards = new ArrayList<String>();
		
		while(running)
		{
			//[0]	Get new suggestion for solution, suggested by the Current Player
			suggestedCards = tester.createSuggestion();
			System.out.print("\nThe cards: ");
			for(int i = 0; i < suggestedCards.size(); i++)
			{
				System.out.print(suggestedCards.get(i) + ", ");
			}
			System.out.print("have been suggested by ");
			
			indexOfCurrentPlayer = playerArray.indexOf(currentPlayer);
			System.out.println(currentPlayer.getName() + ".");
			suggestingPlayerName = currentPlayer.getName();
			inner = true;
			while(inner)
			{
				//[1] Change the selected player and get response to suggestion
				this.nextPlayer(currentPlayer);
				//System.out.println("Selected player changed to " + currentPlayer.getName());
				System.out.print(suggestingPlayerName + " has suggested: ");
				for(int i = 0; i < suggestedCards.size(); i++)
				{
					System.out.print(suggestedCards.get(i));
					if(i == (suggestedCards.size() - 1))
					{
						System.out.print(".");
					}
					else 
					{
						System.out.print(", ");
					}
				}
				System.out.println("\nDid " + currentPlayer.getName() + " Pass or Play?");
				if(AUTOPLAY)
				{
					response = tester.playerResponse(suggestedCards, currentPlayer);
				}
				else 
				{
					response = scan.nextLine();
				}
				
				//[2] 	If the selected player Passes
				if(response.equals("Pass"))
				{
					System.out.println(currentPlayer.getName() + " passed.\n");
					
					//[2a] Call Relevant GameStatusAnalyzer methods
					if(!currentPlayer.SOLVED)
					{
						gameStatus.removeFromPossibleCards(suggestedCards, currentPlayer, this);
					}
				}
				//[3]	If the selected player Plays
				else if(response.equals("Play"))
				{
					System.out.println(currentPlayer.getName() + " played.\n");
					
					//[3a] Call relevant GameStatusAnalyzer methods
					if(!currentPlayer.SOLVED)
					{
						gameStatus.playedOnSuggestion(suggestedCards, currentPlayer, this);
					}
					
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
			
			numOfTurns++;
			
			tester.printAllCards(this, numOfTurns);
			
			int numSolved = 0;
			for(int i = 0; i < this.playerArray.size(); i++)
			{
				if(this.playerArray.get(i).SOLVED)
				{
					numSolved++;
				}
			}
			if(numSolved == this.playerArray.size())
			{
				System.out.println("\n\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nX\t   GAME SOLVED\t\tX\nX\t in " + numOfTurns + " suggestions\tX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				inner = false;
				running = false;
			}
			if(numOfTurns > 150)
			{
				inner = false;
				running = false;
			}
		}
		System.out.println("\n=== Game Loop Exited ===");
		tester.writeResultsToFile(numOfTurns, numberOfPlayers);
		
	}//gameLoop()
	
	
	public static void main(String args[]) 
	{	
		GameManagement newGame = new GameManagement();
		newGame.gameSetup();
		newGame.gameLoop();
		
		//TesterClass test = new TesterClass();
		//test.test(newGame);
		
	}//main()

}//GameManagement
