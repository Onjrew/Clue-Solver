import java.util.ArrayList;
import java.util.Iterator;

public class GameStatusAnalyzer 
{
	
	public void playedOnSuggestion(ArrayList<String> suggestedCards, Player currentPlayer, GameManagement newGame, Solution solution)
	{
		ArrayList<String> possibleCardsFound = new ArrayList<String>();
		int cardsNotFound = 0;
		String cardToRemove = null;
		for(String card : suggestedCards)
		{
			//Check the Player's known cards. If any of the suggested cards are already known, then no information can be gained.
			System.out.println("\n=== Checking " + card + " from the Suggested Cards in " + currentPlayer.getName() + "'s \"Known Cards\" and \"Possible Cards\"");
			if(currentPlayer.knownCards.contains(card))
			{
				System.out.println(card + " was found in " + currentPlayer.getName() + "'s \"Known Cards\", no information gained.\n");
				suggestedCards.set(suggestedCards.indexOf(card), "known");
				break;
			}
			//Check the Player's possible cards.
			else
			{
				System.out.println("\n" + card + " was not found in " + currentPlayer.getName() + "'s \"Known Cards\"");
				if(currentPlayer.possibleCards.contains(card))
				{
					System.out.println(card + " was found in " + currentPlayer.getName() + "'s \"Possible Cards\"");
					possibleCardsFound.add(card);
					cardToRemove = card;
				}
				else 
				{
					System.out.println(card + " was not found in " + currentPlayer.getName() + "'s \"Possible Cards\"");
					cardsNotFound++;
					suggestedCards.set(suggestedCards.indexOf(card), "notFound");
				}
				
			}
			
		}
		
		// If the suggestion contains only ONE found card, and it is NOT already KNOWN
		// then this card must be in the Player's hand.
		// Otherwise, simply add the Suggestion to the list of cards the user "played" on.
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
				this.searchPlayedCards(newGame, solution);
			}
		}
		
	}//playedOnSuggestion
	
	
	public void searchPlayedCards(GameManagement newGame, Solution solution)
	{
		ArrayList<String> currentCard = new ArrayList<String>();
		int cardsNotFound;
		String cardToRemove;
		
		for(Player currentPlayer : newGame.playerArray)
		{
			System.out.println("\n=== Searching " + currentPlayer.getName() + "'s \"Played Cards\" ===\n");
			cardsNotFound = 0;
			cardToRemove = null;
			
			for(int i = 0; i < currentPlayer.playedCards.size(); i++)
			{
				System.out.println("Checking element " + i + " of " + (currentPlayer.playedCards.size() - 1) + ": " + currentPlayer.playedCards.get(i) + ":");
				
				//Check to see if the card is no longer in the Player's Possible cards.
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
				
				// Keep track of how many cards are not no longer found in the
				// Player's possible cards.
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
				}
				
				System.out.println("\t\"cardsNotFound\" = " + cardsNotFound + "\n\tRemainder = " + (i % 3) + "\n");
				
				// If the end of the triplicate (which would have been a suggestion) is
				// reached, then check to see how many cards are not found in the Player's
				// possible cards. If there are any cards now "by themselves", then that must
				// have been the card the user "played" on some previous turn.
				// Add the card to the Player's known cards.
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
		}
		
	}//searchPlayedCards
	
	
	public void removeFromPossibleCards(ArrayList<String> suggestedCards, Player currentPlayer, GameManagement newGame, Solution solution, Boolean forAllPlayers)
	{	
		if(forAllPlayers)
		{
			for(Player selectedPlayer : newGame.playerArray)
			{
				if(!selectedPlayer.SOLVED)
				{
					System.out.println("\n=== Removing Cards from " + selectedPlayer.getName() + "'s \"Possible Cards\" ===\n");
					for(String card : suggestedCards)
					{
						selectedPlayer.possibleCards.remove(card);
						System.out.println("\t" + card + " removed from " + selectedPlayer.getName() + "'s Possible Cards.");
					}
					
					// If the number of possible cards left for a Player matches the number of "missing" Known Cards, 
					// then the remaining cards must be their known cards.
					if( (selectedPlayer.possibleCards.size() + selectedPlayer.knownCards.size()) == selectedPlayer.numOfHeldCards)
					{
						this.addToKnownCards(selectedPlayer.possibleCards, selectedPlayer, newGame, solution);
					}
				}
			}
			// Compare any changes in possible cards among players
			//this.comparePossibleCards(newGame, solution);
		}
		else
		{
			if(!currentPlayer.SOLVED)
			{
				System.out.println("\n=== Removing Cards from " + currentPlayer.getName() + "'s \"Possible Cards\" ===\n");
				for(String card : suggestedCards)
				{
					currentPlayer.possibleCards.remove(card);
					System.out.println("\t" + card + " removed from " + currentPlayer.getName() + "'s Possible Cards.");
				}
				
				// If the number of possible cards left for a Player matches the number of "missing" Known Cards, 
				// then the remaining cards must be their known cards.
				if( (currentPlayer.possibleCards.size() + currentPlayer.knownCards.size()) == currentPlayer.numOfHeldCards)
				{
					this.addToKnownCards(currentPlayer.possibleCards, currentPlayer, newGame, solution);
				}
				
				// Compare any changes in possible cards among players
				//this.comparePossibleCards(newGame, solution);
			}
		}
		// Check to see if removing these cards revealed any new cards from previous turns
		this.searchPlayedCards(newGame, solution);
		
	}//removeFromPossibleCards()
	
	
	public void addToKnownCards(ArrayList<String> cards, Player currentPlayer, GameManagement newGame, Solution solution)
	{	
		System.out.println("\n=== Adding cards to " + currentPlayer.getName() + "'s \"Known Cards\" ===\n");
		Iterator<String> itr = cards.iterator();
		ArrayList<String> cardsToRemove = new ArrayList<String>();
		String knownCard;
		while(itr.hasNext())
		{
			knownCard = itr.next();
			currentPlayer.knownCards.add(knownCard);
			cardsToRemove.add(knownCard);
			System.out.println("\t" + knownCard + " added to " + currentPlayer.getName() + "'s Known Cards.");
		}
		
		System.out.println("\n");
		
		// Remove new known card(s) from the Solution's possible cards
		// This call is up here in case the solution can be found without
		// needing to eliminate the cards from other players.
		this.removeFromSolutionCards(cards, currentPlayer, newGame, solution);
		
		// Eliminate any previous suggestions that contain the new known card, 
		// since they will no longer be able to find more cards.
		if(!currentPlayer.SOLVED)
		{
			for(String card : cards)
			{
				this.removeTriplicateFromPlayedCards(card, currentPlayer);
			}
		}
		
		// When the number of known cards and number of cards held by this player
		// are the same, all the Player's cards are now known, and there is no more
		// need to check any of their cards. 
		if(currentPlayer.knownCards.size() == currentPlayer.numOfHeldCards)
		{
			currentPlayer.SOLVED = true;
			currentPlayer.playedCards.clear();
			//currentPlayer.possibleCards.clear();
		}
		
		//Remove the new known card(s) from all other players' Possible Cards
		this.removeFromPossibleCards(cardsToRemove, currentPlayer, newGame, solution, true);
	
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

	
	public void comparePossibleCards(GameManagement newGame, Solution solution)
	{
		System.out.println("\n=== Comparing Possible Cards ===\n");
		ArrayList<String> comparisonArray = new ArrayList<String>();
		
		for(Player player : newGame.playerArray)
		{
			if(!player.SOLVED)
			{
				comparisonArray.clear();
				comparisonArray.addAll(player.possibleCards);
				
				// Remove any matching cards from other players' possible cards
				for(Player selectedPlayer : newGame.playerArray)
				{
					System.out.println("\n" + player.getName() + "'s Comparison Array:");
					for(String card : comparisonArray)
					{
						System.out.println(card);
					}
					System.out.println("\nComparing " + player.getName() + " to " + selectedPlayer.getName() + ":");
					if(!(player.getName().equals(selectedPlayer.getName())))
					{
						for(String card : selectedPlayer.possibleCards)
						{
							System.out.println("Checking " + card + "...");
							if(comparisonArray.contains(card));
							{
								comparisonArray.remove(card);
								System.out.println(card + " found and removed from Comparison Array.\n");
							}
						}
					}
				}
				
				
				// Remove any matches with the Solution's possible cards
				for(String card : solution.weaponCards)
				{
					System.out.println("Checking " + card + "...");
					if(comparisonArray.contains(card))
					{
						comparisonArray.remove(card);
						System.out.println(card + " found and removed from Comparison Array.\n");
					}	
				}
				for(String card : solution.characterCards)
				{
					System.out.println("Checking " + card + "...");
					if(comparisonArray.contains(card))
					{
						comparisonArray.remove(card);
						System.out.println(card + " found and removed from Comparison Array.\n");
					}
				}
				for(String card : solution.locationCards)
				{
					System.out.println("Checking " + card + "...");
					if(comparisonArray.contains(card))
					{
						comparisonArray.remove(card);
						System.out.println(card + " found and removed from Comparison Array.\n");
					}
				}
				
				// If any cards are left, then the current Player must hold those cards
				if(comparisonArray.size() > 0)
				{
					System.out.println("YYYYYYYYYYYYYY\tYYYYYYYYYYYYYY\tYYYYYYYYYYYYYY\t\n\nComparison Array has a size of: " + comparisonArray.size());
					System.out.println("It contains: ");
					for(String card : comparisonArray)
					{
						System.out.println(card);
					}
					System.out.print("\nYYYYYYYYYYYYYY\tYYYYYYYYYYYYYY\tYYYYYYYYYYYYYY");
					this.addToKnownCards(comparisonArray, player, newGame, solution);
					//newGame.comparisonCalled = true;
				}
			}
			System.out.println("The Comparison Array now contains:");
			for(String card : comparisonArray)
			{
				System.out.println(card);
			}
		}
		
	}//checkForSolution
	
	
	

	public void removeFromSolutionCards(ArrayList<String> cards, Player currentPlayer, GameManagement newGame, Solution solution)
	{
		System.out.println("=== Removing New Known Card from Solution ===\n");
		
		//Remove the added card from the Solution's possible cards: Weapons, Characters, and Locations
		if(!solution.weaponFound)
		{
			for(String card : cards)
			{
				if(solution.weaponCards.contains(card))
				{
					solution.weaponCards.remove(card);
					System.out.println("\t" + card + " removed from Possible Weapons.\n");
				}
			}
			if(solution.weaponCards.size() == 1)
			{
				solution.knownCards.add(solution.weaponCards.get(0));
				solution.weaponFound = true;
				System.out.println("\n\tMURDER WEAPON FOUND\n\t\t" + solution.weaponCards.get(0) + "\n");
				this.removeFromPossibleCards(solution.weaponCards, currentPlayer, newGame, solution, true);
				solution.weaponCards.clear();
			}
		}
		if(!solution.characterFound)
		{
			for(String card : cards)
			{
				if(solution.characterCards.contains(card))
				{
					solution.characterCards.remove(card);
					System.out.println("\t" + card + " removed from Possible Characters.\n");
				}
			}
			if(solution.characterCards.size() == 1)
			{
				solution.knownCards.add(solution.characterCards.get(0));
				solution.characterFound = true;
				System.out.println("\n\tMURDERER FOUND\n\t\t" + solution.characterCards.get(0) + "\n");
				this.removeFromPossibleCards(solution.characterCards, currentPlayer, newGame, solution, true);
				solution.characterCards.clear();
			}
		}
		if(!solution.locationFound)
		{
			for(String card : cards)
			{
				if(solution.locationCards.contains(card))
				{
					solution.locationCards.remove(card);
					System.out.println("\t" + card + " removed from Possible Locations.\n");
				}
			}
			if(solution.locationCards.size() == 1)
			{
				solution.knownCards.addAll(solution.locationCards);
				solution.locationFound = true;
				System.out.println("\n\tMURDER LOCATION FOUND\n\t\t" + solution.locationCards.get(0) + "\n");
				this.removeFromPossibleCards(solution.locationCards, currentPlayer, newGame, solution, true);
				solution.locationCards.clear();
			}
		}
		
		
	}//removeFromSolutionCards()
	
}//GameStatusAnalyzer
