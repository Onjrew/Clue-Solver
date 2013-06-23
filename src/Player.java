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
	
	void getPlayedCards()
	{	
		System.out.print(this.name + " has ");
		if(playedCards.isEmpty())
		{
			System.out.println("no played cards.\n");
		}
		else
		{
			System.out.print("played: ");
			Iterator<String> itr = playedCards.iterator();
			while(itr.hasNext())
			{
				String card = (String) itr.next();
				System.out.print(card);
				if(itr.hasNext())
				{
					System.out.print(", ");
				}
				else
				{
					System.out.println(".\n");
				}
			}
		}
	
	}//getPlayedCards()
	
	
	void getPossibleCards()
	{
		System.out.print(this.getName() + " has these possible cards: ");
		Iterator<String> itr = possibleCards.iterator();
		while(itr.hasNext())
		{
			System.out.print(itr.next());
			if (itr.hasNext()) 
			{
				System.out.print(", ");
			}
			else 
			{
				System.out.print(".\n");
			}
		}
		possibleCards.trimToSize();
		
	}//getPossibleCards()
	
	

}//Player class
