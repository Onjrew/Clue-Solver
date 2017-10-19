import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Player 
{
	//=== Class Variables ===
	
	String name;
	int numOfHeldCards;
	boolean isYou, SOLVED;
	ArrayList<String> 	possibleCards 	= new ArrayList<String>(),
						playedCards 	= new ArrayList<String>(),
						heldCards		= new ArrayList<String>(),
						knownCards 		= new ArrayList<String>();
	
	
	//=== Class Constructor ===
	
	Player(ArrayList<String> cardPool)
	{	
		this.possibleCards.addAll(cardPool);
		
		SOLVED = false;
		isYou = false;
	
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
