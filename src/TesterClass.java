import java.util.ArrayList;


public class TesterClass 
{
	public void test(GameManagement newGame)
	{	
		
		
		Player currentPlayer = new Player();
		currentPlayer.setName("Andrew");
		newGame.playerArray.add(currentPlayer);
		
		GameStatusAnalyzer gameStatus = new GameStatusAnalyzer();
		gameStatus.addToKnownCards(this.createSuggestion(), currentPlayer, newGame);
		gameStatus.removeFromPossibleCards(this.createNewSuggestion(), currentPlayer, newGame);
		gameStatus.playedOnSuggestion(this.createNewNewSuggestion(), currentPlayer, newGame);
		gameStatus.playedOnSuggestion(this.createNewNewNewSuggestion(), currentPlayer, newGame);
		this.printAllCards(currentPlayer);
		gameStatus.removeFromPossibleCards(this.createNewNewNewNewSuggestion(), currentPlayer, newGame);
		this.printAllCards(currentPlayer);
		
	}//test()
	
	
	void printAllCards(Player currentPlayer)
	{
		System.out.println("\n=======================\n" + currentPlayer.getName() + "'s Known Cards:");
		for(int i = 0; i < currentPlayer.knownCards.size(); i++)
		{
			System.out.println("\t" + currentPlayer.knownCards.get(i));
		}
		
		System.out.println("\n" + currentPlayer.getName() + "'s Possible Cards:");
		for(int i = 0; i < currentPlayer.possibleCards.size(); i++)
		{
			System.out.println("\t" + currentPlayer.possibleCards.get(i));
		}
		
		System.out.println("\n" + currentPlayer.getName() + "'s Played Cards:");
		for(int i = 0; i < currentPlayer.playedCards.size(); i++)
		{
			System.out.println("\t" + currentPlayer.playedCards.get(i));
		}
		
	}//printAllCards()
	
	
	ArrayList<String> createSuggestion()
	{
		ArrayList<String> suggestedCards = new ArrayList<String>();
		suggestedCards.add("Knife");
		suggestedCards.add("Mrs. Peacock");
		suggestedCards.add("Lounge");
		
		return suggestedCards;
		
	}//createSuggestion()
	
	
	ArrayList<String> createNewSuggestion()
	{
		ArrayList<String> suggestedCards = new ArrayList<String>();
		suggestedCards.add("Revolver");
		suggestedCards.add("Colonel Mustard");
		suggestedCards.add("Lounge");
		
		return suggestedCards;
		
	}//createNewSuggestion()
	
	
	ArrayList<String> createNewNewSuggestion()
	{
		ArrayList<String> suggestedCards = new ArrayList<String>();
		suggestedCards.add("Revoler");
		suggestedCards.add("Colonel Mustard");
		suggestedCards.add("Billiard Room");
		
		return suggestedCards;
		
	}//createNewNewSuggestion()
	
	
	ArrayList<String> createNewNewNewSuggestion()
	{
		ArrayList<String> suggestedCards = new ArrayList<String>();
		suggestedCards.add("Revolver");
		suggestedCards.add("Miss Scarlet");
		suggestedCards.add("Hall");
		
		return suggestedCards;
		
	}//createNewNewNewSuggestion()
	
	
	ArrayList<String> createNewNewNewNewSuggestion()
	{
		ArrayList<String> suggestedCards = new ArrayList<String>();
		suggestedCards.add("Revolver");
		suggestedCards.add("Miss Scarlet");
		suggestedCards.add("Lounge");
		
		return suggestedCards;
		
	}//createNewNewNewNewSuggestion()

	
}//TesterClass

