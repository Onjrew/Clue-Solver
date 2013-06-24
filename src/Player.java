import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Player 
{
	//=== Class Variables ===
	
	String name;
	int numOfHeldCards;
	boolean SOLVED;
	ArrayList<String> 	possibleCards 	= new ArrayList<String>(),
						playedCards 	= new ArrayList<String>(),
						heldCards		= new ArrayList<String>(),
						knownCards 		= new ArrayList<String>();
	
	
	//=== Class Constructor ===
	
	Player(ArrayList<String> cardPool)
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
		
		SOLVED = false;
	
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

	
}//Player class
