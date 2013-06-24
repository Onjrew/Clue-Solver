import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GameStatusAnalyzer 
{
	
	public void playedOnSuggestion(ArrayList<String> suggestedCards, Player currentPlayer, GameManagement newGame, Solution solution)
	{
		ArrayList<String> possibleCardsFound = new ArrayList<String>();
		int cardsNotFound = 0;
		String cardToRemove = null;
		for(int i = 0; i < suggestedCards.size(); i++)
		{
			System.out.println("\n=== Checking " + suggestedCards.get(i) + " from the Suggested Cards in " + currentPlayer.getName() + "'s \"Known Cards\" and \"Possible Cards\"");
			if(currentPlayer.knownCards.contains(suggestedCards.get(i)))
			{
				System.out.println(suggestedCards.get(i) + " was found in " + currentPlayer.getName() + "'s \"Known Cards\", no information gained.\n");
				suggestedCards.set(i, "known");
				break;
			}
			else
			{
				System.out.println("\n" + suggestedCards.get(i) + " was not found in " + currentPlayer.getName() + "'s \"Known Cards\"");
				if(currentPlayer.possibleCards.contains(suggestedCards.get(i)))
				{
					System.out.println(suggestedCards.get(i) + " was found in " + currentPlayer.getName() + "'s \"Possible Cards\"");
					possibleCardsFound.add(suggestedCards.get(i));
					cardToRemove = suggestedCards.get(i);
				}
				else 
				{
					System.out.println(suggestedCards.get(i) + " was not found in " + currentPlayer.getName() + "'s \"Possible Cards\"");
					cardsNotFound++;
					suggestedCards.set(i, "notFound");
				}
				
			}
			
		}
		
		if(!suggestedCards.contains("known"))
		{
			if(cardsNotFound == 2)
			{
				this.removeTriplicateFromPlayedCards(cardToRemove, currentPlayer);
				this.addToKnownCards(possibleCardsFound, currentPlayer, newGame, solution);
			}
			else
			{
				currentPlayer.playedCards.addAll(suggestedCards);
				this.searchPlayedCards(currentPlayer, newGame, solution);
			}
		}
		
	}//playedOnSuggestion
	
	
	public void searchPlayedCards(Player currentPlayer, GameManagement newGame, Solution solution)
	{
		System.out.println("\n=== Searching " + currentPlayer.getName() + "'s \"Played Cards\" ===\n");
		ArrayList<String> currentCard = new ArrayList<String>();
		int cardsNotFound = 0;
		String cardToRemove = null;
		
		for(int i = 0; i < currentPlayer.playedCards.size(); i++)
		{
			System.out.println("Checking element " + i + " of " + (currentPlayer.playedCards.size() - 1) + ": " + currentPlayer.playedCards.get(i) + ":");
			if(!currentPlayer.possibleCards.contains(currentPlayer.playedCards.get(i)))
			{
				System.out.println("\t" + currentPlayer.playedCards.get(i) + " was not found in " + currentPlayer.getName() + "'s \"Possible Cards\"");
				System.out.print("\t" + currentPlayer.playedCards.get(i) + " changed to ");
				currentPlayer.playedCards.set(i, "notFound");
				System.out.println(currentPlayer.playedCards.get(i) + "\n");
			}
			else 
			{
				System.out.println("\t" + currentPlayer.playedCards.get(i) + " was found in " + currentPlayer.getName() + "'s \"Possible Cards\"");
			}
			if(currentPlayer.playedCards.get(i).equals("notFound"))
			{
				cardsNotFound++;
				System.out.println("\t\"notFound\" count increased.\n");
			}
			else 
			{
				if(currentCard.size() == 0)
				{
					currentCard.add(0, currentPlayer.playedCards.get(i));
				}
				else
				{	
					currentCard.set(0, currentPlayer.playedCards.get(i));
				}
				cardToRemove = currentCard.get(0);
				//System.out.println(currentPlayer.playedCards.get(i) + " added to \"currentCard\" array.");
			}
			
			System.out.println("\t\"cardsNotFound\" = " + cardsNotFound + "\n\tRemainder = " + (i % 3) + "\n");
			
			if((i % 3) == 2)
			{
				if(cardsNotFound == 2)
				{
					this.removeTriplicateFromPlayedCards(cardToRemove, currentPlayer);
					this.addToKnownCards(currentCard, currentPlayer, newGame, solution);
				}
				cardsNotFound = 0;
			}
		}
		
	}//searchPlayedCards
	
	
	public void removeFromPossibleCards(ArrayList<String> suggestedCards, Player currentPlayer, GameManagement newGame, Solution solution)
	{
		System.out.println("\n=== Removing Cards from " + currentPlayer.getName() + "'s \"Possible Cards\" ===\n");
		Iterator<String> itr = suggestedCards.iterator();
		String card;
		while(itr.hasNext())
		{
			card = itr.next();
			currentPlayer.possibleCards.remove(card);
			System.out.println("\t" + card + " removed from " + currentPlayer.getName() + "'s Possible Cards.");
		}
		
		this.searchPlayedCards(currentPlayer, newGame, solution);
		
		if( (currentPlayer.possibleCards.size() + currentPlayer.knownCards.size()) == currentPlayer.numOfHeldCards)
		{
			this.addToKnownCards(currentPlayer.possibleCards, currentPlayer, newGame, solution);
		}
		
	}//removeFromPossibleCards()
	
	
	public void addToKnownCards(ArrayList<String> cards, Player currentPlayer, GameManagement newGame, Solution solution)
	{	
		System.out.println("\n=== Adding cards to " + currentPlayer.getName() + "'s \"Known Cards\" ===\n");
		Iterator<String> itr = cards.iterator();
		String card;
		while(itr.hasNext())
		{
			card = itr.next();
			currentPlayer.knownCards.add(card);
			System.out.println("\t" + card + " added to " + currentPlayer.getName() + "'s Known Cards.");
		}
		
		System.out.println("\n");
		
		this.removeFromSolutionCards(cards, solution);
		
		if(currentPlayer.knownCards.size() == currentPlayer.numOfHeldCards)
		{
			currentPlayer.SOLVED = true;
			currentPlayer.playedCards.clear();
			currentPlayer.possibleCards.clear();
		}
		
		if(!currentPlayer.SOLVED)
		{
			for(int i = 0; i < cards.size(); i++)
			{
				this.removeTriplicateFromPlayedCards(cards.get(i), currentPlayer);
			}
		}
		
		for(int i = 0; i < newGame.playerArray.size(); i++)
		{
			if(!newGame.playerArray.get(i).SOLVED)
			{
				this.removeFromPossibleCards(cards, newGame.playerArray.get(i), newGame, solution);
			}
		}
	
	}//addToKnownCards()
	
	
	public void removeTriplicateFromPlayedCards(String cardToRemove, Player currentPlayer)
	{
		System.out.println("=== Deleting Triplicates that contain " + cardToRemove + " from " + currentPlayer.getName() + "'s \"Played Cards\"");
		int startOfTriplicate;
		while(currentPlayer.playedCards.contains(cardToRemove))
		{
			System.out.println("\t" + currentPlayer.getName() + "'s \"Played Cards\" still contains " + cardToRemove);
			startOfTriplicate = currentPlayer.playedCards.lastIndexOf(cardToRemove) - (currentPlayer.playedCards.lastIndexOf(cardToRemove) % 3);
			for(int i = 0; i < 3; i++)
			{
				System.out.println("\t\t" + currentPlayer.playedCards.get(startOfTriplicate) + " removed from " + currentPlayer.getName() + "'s Played Cards.");
				currentPlayer.playedCards.remove(startOfTriplicate);
			}
		}
		
	}//removeTriplicateFromPlayedCards()

	
	public void checkForSolution(GameManagement newGame, Solution solution)
	{
		
		
	}//checkForSolution
	
	
	public void removeFromSolutionCards(ArrayList<String> cards, Solution solution)
	{
		System.out.println("=== Removing New Known Card from Solution ===\n");
		
		//Remove the added card from the Solution's possible cards: Weapons, Characters, and Locations
		if(!solution.weaponFound)
		{
			for(int i = 0; i < cards.size(); i++)
			{
				if(solution.weaponCards.contains(cards.get(i)))
				{
					solution.weaponCards.remove(cards.get(i));
					System.out.println("\t" + cards.get(i) + " removed from Possible Weapons.\n");
				}
			}
			if(solution.weaponCards.size() == 1)
			{
				solution.knownCards.add(solution.weaponCards.get(0));
				solution.weaponFound = true;
				System.out.println("\n\tMURDER WEAPON FOUND\n\t\t" + solution.weaponCards.get(0) + "\n");
				solution.weaponCards.clear();
			}
		}
		if(!solution.characterFound)
		{
			for(int i = 0; i < cards.size(); i++)
			{
				if(solution.characterCards.contains(cards.get(i)))
				{
					solution.characterCards.remove(cards.get(i));
					System.out.println("\t" + cards.get(i) + " removed from Possible Characters.\n");
				}
			}
			if(solution.characterCards.size() == 1)
			{
				solution.knownCards.add(solution.characterCards.get(0));
				solution.characterFound = true;
				System.out.println("\n\tMURDERER FOUND\n\t\t" + solution.characterCards.get(0) + "\n");
				solution.characterCards.clear();
			}
		}
		if(!solution.locationFound)
		{
			for(int i = 0; i < cards.size(); i++)
			{
				if(solution.locationCards.contains(cards.get(i)))
				{
					solution.locationCards.remove(cards.get(i));
					System.out.println("\t" + cards.get(i) + " removed from Possible Locations.\n");
				}
			}
			if(solution.locationCards.size() == 1)
			{
				solution.knownCards.addAll(solution.locationCards);
				solution.locationFound = true;
				System.out.println("\n\tMURDER LOCATION FOUND\n\t\t" + solution.locationCards.get(0) + "\n");
				solution.locationCards.clear();
			}
		}
		
		
	}//removeFromSolutionCards()
	
}//GameStatusAnalyzer
