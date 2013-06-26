import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;


public class TesterClass 
{	
	public void test(GameManagement newGame)
	{	
		for(int i = 0; i < newGame.playerArray.size() ; i++)
		{
			System.out.println("Player " + (i + 1) + " has " + newGame.playerArray.get(i).numOfHeldCards + " cards.");
		}
		
	}//test()
	
	
	void printAllCards(GameManagement newGame, int numOfTurns, Solution solution)
	{
		System.out.println("\n=======================\n    Turn " + numOfTurns +  " Summary\n=======================\n");
		
		//Print Player Cards
		float numOfCardsCorrect;
		for(int i = 0; i < newGame.playerArray.size(); i++)
		{
			numOfCardsCorrect = 0;
			if(newGame.playerArray.get(i).numOfHeldCards == newGame.playerArray.get(i).knownCards.size())
			{
				System.out.println("\n***********************\n*\tSOLVED\t\t*\n***********************");
			}
			System.out.print("\n" + newGame.playerArray.get(i).getName() + "'s Cards:");
			for(int j = 0; j < newGame.playerArray.get(i).heldCards.size(); j++)
			{
				if(newGame.playerArray.get(i).knownCards.contains(newGame.playerArray.get(i).heldCards.get(j)))
				{
					numOfCardsCorrect++;
				}
			}
			if(newGame.AUTOPLAY)
			{
				System.out.println("\t" + (100 * numOfCardsCorrect / newGame.playerArray.get(i).heldCards.size()) + " % Correct");
				System.out.println("\t= Held Cards =");
				for(int j = 0; j < newGame.playerArray.get(i).heldCards.size(); j++)
				{
					System.out.println("\t\t" + newGame.playerArray.get(i).heldCards.get(j));
				}
			}
			System.out.println("\t= Known Cards =");
			for(int j = 0; j < newGame.playerArray.get(i).knownCards.size(); j++)
			{
				System.out.println("\t\t" + newGame.playerArray.get(i).knownCards.get(j));
			}
			System.out.println("\t= Possible Cards =");
			for(int j = 0; j < newGame.playerArray.get(i).possibleCards.size(); j++)
			{
				System.out.println("\t\t" + newGame.playerArray.get(i).possibleCards.get(j));
			}
			System.out.println("\t= Played Cards =");
			for(int j = 0; j < newGame.playerArray.get(i).playedCards.size(); j++)
			{
				System.out.println("\t\t" + newGame.playerArray.get(i).playedCards.get(j));
			}
		}
		
		//Print Solution Cards
		numOfCardsCorrect = 0;
		if(solution.weaponFound & solution.characterFound & solution.locationFound)
		{
			System.out.println("\n***********************\n*\tSOLVED\t\t*\n***********************");
		}
		System.out.print("\nSolution's Cards: ");
		if(solution.weaponFound)
		{
			numOfCardsCorrect++;
		}
		if(solution.characterFound)
		{
			numOfCardsCorrect++;
		}
		if(solution.locationFound)
		{
			numOfCardsCorrect++;
		}
		if(newGame.AUTOPLAY)
		{
			System.out.println("\t" + (100 * numOfCardsCorrect / solution.heldCards.size()) + " % Correct");
			System.out.println("\t= Held Cards =");
			for(int j = 0; j < solution.heldCards.size(); j++)
			{
				System.out.println("\t\t" + solution.heldCards.get(j));
			}
		}
		System.out.println("\t= Known Cards =");
		for(int j = 0; j < solution.knownCards.size(); j++)
		{
			System.out.println("\t\t" + solution.knownCards.get(j));
		}
		System.out.println("\t= Possible Weapons =");
		for(int j = 0; j < solution.weaponCards.size(); j++)
		{
			System.out.println("\t\t" + solution.weaponCards.get(j));
		}
		System.out.println("\t= Possible Murderers =");
		for(int j = 0; j < solution.characterCards.size(); j++)
		{
			System.out.println("\t\t" + solution.characterCards.get(j));
		}
		System.out.println("\t= Possible Locations =");
		for(int j = 0; j < solution.locationCards.size(); j++)
		{
			System.out.println("\t\t" + solution.locationCards.get(j));
		}
		
	}//printAllCards()
	
	
	ArrayList<String> createSuggestion()
	{
		ArrayList<String> 		suggestedCards 		= new ArrayList<String>(),
								cardPool			= new ArrayList<String>();
		
		//Weapons
				cardPool.add("Knife");				//0
				cardPool.add("Candlestick");		//1
				cardPool.add("Lead Pipe");			//2
				cardPool.add("Revolver");			//3
				cardPool.add("Rope");				//4
				cardPool.add("Wrench");			//5
				
				//Characters
				cardPool.add("Miss Scarlet");		//6
				cardPool.add("Colonel Mustard");	//7
				cardPool.add("Mrs. White");		//8
				cardPool.add("Mr. Green");			//9
				cardPool.add("Mrs. Peacock");		//10
				cardPool.add("Professor Plum");	//11
				
				//Locations
				cardPool.add("Kitchen");			//12
				cardPool.add("Ballroom");			//13
				cardPool.add("Conservatory");		//14
				cardPool.add("Dining Room");		//15
				cardPool.add("Library");			//16
				cardPool.add("Billiard Room");		//17
				cardPool.add("Lounge");			//18
				cardPool.add("Hall");				//19
				cardPool.add("Study");				//20
		
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
			suggestedCards.add(cardPool.get(randNum));
		}
		
		return suggestedCards;
		
	}//createSuggestion()
	
	
	public String playerResponse(ArrayList<String> suggestedCards, Player currentPlayer)
	{
		String response = "Pass";
		for(int i = 0; i < suggestedCards.size(); i++)
		{
			if(currentPlayer.heldCards.contains(suggestedCards.get(i)))
			{
				response = "Play";
				break;
			}
		}
		
		return response;
		
	}

	
	public void setHeldCards(ArrayList<String> cardPool, GameManagement newGame)
	{
		Random rand = new Random();
		int index;
		String randomCard;
		
		for(int i = 0; i < newGame.playerArray.size(); i++)
		{
			for(int j = 0; j < newGame.playerArray.get(i).numOfHeldCards; j++)
			{
				index = rand.nextInt(cardPool.size());
				randomCard = cardPool.get(index);
				newGame.playerArray.get(i).heldCards.add(randomCard);
				cardPool.remove(randomCard);
			}
		}
		
	}
	
	
	public void writeResultsToFile(int numOfTurns, int numOfPlayers)
	{
		try 
		{
			String result = numOfTurns + "\n";
			
			File file  = new File("/Users/Andrew/Documents/workspace/ClueSolver/Suggestions_Until_Solution_for_" + numOfPlayers + "_Players.csv");
			
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result);
			bw.close();
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
}//TesterClass

