import java.util.ArrayList;


public class Solution 
{
	String name;
	int numOfHeldCards;
	boolean weaponFound, characterFound, locationFound;
	ArrayList<String> 	weaponCards 	= new ArrayList<String>(),
						characterCards	= new ArrayList<String>(),
						locationCards	= new ArrayList<String>(),
						knownCards 		= new ArrayList<String>(),
						heldCards		= new ArrayList<String>();
	
	Solution()
	{	
		
		//Set name
		this.name = "Solution";
		
		//Set number of cards held
		this.numOfHeldCards = 3;
		
		//Set Weapon cards
		this.weaponCards.add("Knife");
		this.weaponCards.add("Candlestick");
		this.weaponCards.add("Lead Pipe");
		this.weaponCards.add("Revolver");
		this.weaponCards.add("Rope");
		this.weaponCards.add("Wrench");
		
		//Set Character cards
		this.characterCards.add("Miss Scarlet");
		this.characterCards.add("Colonel Mustard");
		this.characterCards.add("Mrs. White");
		this.characterCards.add("Mr. Green");
		this.characterCards.add("Mrs. Peacock");
		this.characterCards.add("Professor Plum");
		
		//Set Location cards
		this.locationCards.add("Kitchen");
		this.locationCards.add("Ballroom");
		this.locationCards.add("Conservatory");
		this.locationCards.add("Dining Room");
		this.locationCards.add("Library");
		this.locationCards.add("Billiard Room");
		this.locationCards.add("Lounge");
		this.locationCards.add("Hall");
		this.locationCards.add("Study");
		
		this.weaponFound 	= false;
		this.characterFound 	= false;
		this.locationFound 	= false;
		
	}//Solution()
	
}//Solution
