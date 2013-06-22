import java.util.ArrayList;
import java.util.Iterator;

public class GameStatusAnalyzer 
{
	public void playedOnSuggestion(ArrayList<String> suggestedCards, Player currentPlayer, GameManagement newGame)
	{
		ArrayList<String> possibleCardsFound = new ArrayList<String>();
		int cardsNotFound = 0;
		for(int i = 0; i < suggestedCards.size(); i++)
		{
			if(currentPlayer.knownCards.contains(suggestedCards.get(i)))
			{
				System.out.println(suggestedCards.get(i) + " was found in " + currentPlayer.getName() + "'s \"Known Cards\", no information gained.");
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
				this.addToKnownCards(possibleCardsFound, currentPlayer, newGame);
			}
			else
			{
				currentPlayer.playedCards.addAll(suggestedCards);
				this.searchPlayedCards(currentPlayer, newGame);
			}
		}
		
	}//playedOnSuggestion
	
	
	public void searchPlayedCards(Player currentPlayer, GameManagement newGame)
	{
		System.out.println("\n=== Searching " + currentPlayer.getName() + "'s \"Played Cards\"===\n");
		ArrayList<String> currentCard = new ArrayList<String>();
		int cardsNotFound = 0, indexToRemove = 0;
		
		for(int i = 0; i < currentPlayer.playedCards.size(); i++)
		{
			System.out.println("Checking element " + i + ": " + currentPlayer.playedCards.get(i) + ":");
			if(!currentPlayer.possibleCards.contains(currentPlayer.playedCards.get(i)))
			{
				System.out.print(currentPlayer.playedCards.get(i) + " changed to ");
				currentPlayer.playedCards.set(i, "notFound");
				System.out.println(currentPlayer.playedCards.get(i));
			}
			if(currentPlayer.playedCards.get(i).equals("notFound"))
			{
				cardsNotFound++;
				System.out.println("\"notFound\" count increased.");
			}
			else 
			{
				currentCard.add(0,currentPlayer.playedCards.get(i));
				indexToRemove = i;
				System.out.println(currentPlayer.playedCards.get(i) + " added to \"currentCard\" array.");
			}
			
			System.out.println("\"cardsNotFound\" = " + cardsNotFound + "\nRemainder = " + (i % 3) + "\n");
			
			if((i % 3) == 2)
			{
				if(cardsNotFound == 2)
				{
					this.addToKnownCards(currentCard, currentPlayer, newGame);
					this.removeTriplicateFromPlayedCards(indexToRemove, currentPlayer);
				}
				cardsNotFound = 0;
			}
		}
		
	}//searchPlayedCards
	
	
	public void removeFromPossibleCards(ArrayList<String> suggestedCards, Player currentPlayer, GameManagement newGame)
	{
		Iterator<String> itr = suggestedCards.iterator();
		String card;
		while(itr.hasNext())
		{
			card = itr.next();
			currentPlayer.possibleCards.remove(card);
			System.out.println(card + " removed from " + currentPlayer.getName() + "'s Possible Cards.");
		}
		this.searchPlayedCards(currentPlayer, newGame);
		
	}//removeFromPossibleCards()
	
	
	public void addToKnownCards(ArrayList<String> cards, Player currentPlayer, GameManagement newGame)
	{	
		Iterator<String> itr = cards.iterator();
		String card;
		while(itr.hasNext())
		{
			card = itr.next();
			currentPlayer.knownCards.add(card);
			System.out.println(card + " added to " + currentPlayer.getName() + "'s Known Cards.");
		}
		
		System.out.println("\n");
		
		for(int i = 0; i < newGame.playerArray.size(); i++)
		{
			this.removeFromPossibleCards(cards, currentPlayer, newGame);
			newGame.nextPlayer(currentPlayer);
		}
	
	}//addToKnownCards()
	
	
	public void removeTriplicateFromPlayedCards(int index, Player currentPlayer)
	{
		int indexToRemove = index - (index % 3);
		for(int j = 0; j < 3; j++)
		{	
			System.out.println("\t" + currentPlayer.playedCards.get(indexToRemove) + " removed from " + currentPlayer.getName() + "'s Played Cards.");
			currentPlayer.playedCards.remove(indexToRemove);
		}
		
	}//removeTriplicateFromPlayedCards()
	
	
}//GameStatusAnalyzer
