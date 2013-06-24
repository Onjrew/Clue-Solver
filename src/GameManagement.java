import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class GameManagement 
{
	ArrayList<Player> playerArray = new ArrayList<Player>();
	Scanner scan = new Scanner(System.in);
	static Player currentPlayer;
	static Solution solution;
	static GameStatusAnalyzer gameStatus = new GameStatusAnalyzer();
	static TesterClass tester = new TesterClass();
	ArrayList<String> cardPool = new ArrayList<String>();
	int numberOfPlayers = 6, numberOfCards, numOfTurns = 0, numOfPasses = 0;
	boolean AUTOPLAY = true, AUTODEAL = true;

	public void gameSetup()
	{	
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
		
		if(!AUTOPLAY)
		{
			System.out.println("How many players are there?");
			numberOfPlayers = Integer.parseInt(scan.nextLine());
			System.out.println("=== Finished setting the number of players. ===\n\n");
		}
		
		//Create all the players
		for(int i = 0; i < numberOfPlayers; i++)
		{
			Player newPlayer = new Player(cardPool);
			if(!AUTOPLAY)
			{
				System.out.println("What is player " + (i+1) + "'s name?");
				newPlayer.setName(scan.nextLine());
			}
			else 
			{
				newPlayer.setName("Player " + i);
				System.out.println(newPlayer.getName() + " created.");
			}
			if(!AUTOPLAY)
			{
				System.out.println("How many cards do they have?");
				newPlayer.numOfHeldCards = Integer.parseInt(scan.nextLine());
			}
			playerArray.add(newPlayer);	
		}
		
		//Create a Solution
		solution = new Solution();
		solution.heldCards.addAll(this.setSolution(cardPool));
		
		System.out.println("\n\n=== Finished initializing players. ===\n\n");
		
		//Set the number of cards held by each player
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
			System.out.println(solution.name + " has " + solution.numOfHeldCards + " cards.");
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
			System.out.println("\n" + solution.name + " has:");
			for(int i = 0; i < solution.heldCards.size(); i++)
			{
				System.out.println(solution.heldCards.get(i));
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
			//Get new suggestion for solution, suggested by the Current Player
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
				this.nextPlayer(currentPlayer);
				System.out.print("Solution Cards: ");
				for(int i = 0; i < solution.heldCards.size(); i++)
				{
					System.out.print(solution.heldCards.get(i) + "\t");
				}
				System.out.print("\n" + suggestingPlayerName + " has suggested: ");
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
					numOfPasses++;
					if(numOfPasses == playerArray.size())
					{
						System.out.print("\n\tXXXXX Suggested Cards were the Solution XXXXX\n");
						inner = false;
						solution.knownCards.clear();
						solution.knownCards.addAll(suggestedCards);
						solution.weaponFound = true;
						solution.characterFound = true;
						solution.locationFound = true;
					}
					
					//[2a] Call Relevant GameStatusAnalyzer methods
					if(!(currentPlayer.SOLVED || (solution.weaponFound & solution.characterFound & solution.locationFound)))
					{
						gameStatus.removeFromPossibleCards(suggestedCards, currentPlayer, this, solution);
					}
					
				}
				//[3]	If the selected player Plays
				else if(response.equals("Play"))
				{
					System.out.println(currentPlayer.getName() + " played.\n");
					
					//[3a] Call relevant GameStatusAnalyzer methods
					if(!currentPlayer.SOLVED)
					{
						gameStatus.playedOnSuggestion(suggestedCards, currentPlayer, this, solution);
					}
					
					currentPlayer = playerArray.get(indexOfCurrentPlayer);
					this.nextPlayer(currentPlayer);
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
			numOfPasses = 0;
			
			tester.printAllCards(this, numOfTurns, solution);
			
			int numSolved = 0;
			for(int i = 0; i < this.playerArray.size(); i++)
			{
				if(this.playerArray.get(i).SOLVED)
				{
					numSolved++;
				}
			}
			if((numSolved == this.playerArray.size()) || (solution.weaponFound & solution.characterFound & solution.locationFound))
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
	
	
	public ArrayList<String> setSolution(ArrayList<String> cardPool)
	{
			ArrayList<String> 		solutionCards 		= new ArrayList<String>();		
			
			Random randNumGenerator = new Random();
			int randNum;
			
			for(int i = 0; i < 3; i++)
			{
				if(i == 0)
				{
					randNum = randNumGenerator.nextInt(6);
				}
				else if(i == 1)
				{
					randNum = 6 + randNumGenerator.nextInt(6);
				}
				else 
				{
					randNum = 12 + randNumGenerator.nextInt(9);
				}
				solutionCards.add(cardPool.get(randNum));
			}
			
			for(int i = 0; i < solutionCards.size(); i ++)
			{
				cardPool.remove(solutionCards.get(i));
			}
			
			return solutionCards;
		
	}//setSolution()
	
	
	public static void main(String args[]) 
	{	
		for(int i = 0; i < 100; i++)
		{
			GameManagement newGame = new GameManagement();
			newGame.gameSetup();
			newGame.gameLoop();
		}
		
		//TesterClass test = new TesterClass();
		//test.test(newGame);
		
	}//main()

}//GameManagement
