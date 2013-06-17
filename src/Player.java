import java.util.ArrayList;
import java.util.Iterator;

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
	
	
	void addToPlayedCards(ArrayList<String> cards)
	{	
		Iterator<String> itr = cards.iterator();
		while(itr.hasNext())
		{
			playedCards.add(itr.next());
		}
	
	}//addToPLayedCards()
	
	
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
	

}//Player class
