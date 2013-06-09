import java.util.ArrayList;

public class Player 
{
	//=== Class Variables ===
	
	String name;
	ArrayList<String> 	possibleCards 	= new ArrayList<String>(), 
						playedCards 	= new ArrayList<String>(), 
						knownCards 		= new ArrayList<String>();
	
	
	//=== Class Constructor ===
	
	Player()
	{
		this.possibleCards.add("Knife");
		this.possibleCards.add("Candlestick");
		this.possibleCards.add("Lead Pipe");
		this.possibleCards.add("Revolver");
		this.possibleCards.add("Rope");
		this.possibleCards.add("Wrench");
		this.possibleCards.add("Miss Scarlet");
		this.possibleCards.add("Colonel Mustard");
		this.possibleCards.add("Mrs. White");
		this.possibleCards.add("Mr. Green");
		this.possibleCards.add("Mrs. Peacock");
		this.possibleCards.add("Professor Plum");
		this.possibleCards.add("Kitchen");
		this.possibleCards.add("Ballroom");
		this.possibleCards.add("Conservatory");
		this.possibleCards.add("Dining Room");
		this.possibleCards.add("Library");
		this.possibleCards.add("Billiard Room");
		this.possibleCards.add("Lounge");
		this.possibleCards.add("Hall");
		this.possibleCards.add("Study");
	
	}//Player()
	
	
	//=== Class Methods === 
	
	void setName(String enteredName)
	{
		this.name = enteredName;
	
	}//setName()
	
	
	String getName()
	{
		return this.name;
	
	}//getName()
	
	
	void addToPlayedCards(String cards)
	{
		this.playedCards.add(cards);
	
	}//addToPLayedCards()
	
	
	String getPlayedCards(int i)
	{
		return (String) this.playedCards.get(i);
	
	}//getPlayedCards()
	

}//Player class
