import java.util.Scanner;
import java.util.ArrayList;
import java.lang.reflect.Array;

public class GameManagement 
{
	
	public static void main(String args[]) 
	{
		
	ArrayList playerArray = new ArrayList();
	
	System.out.println("How many players are there?");
	
	String enteredNumber;
	Scanner scan = new Scanner(System.in);
	enteredNumber = scan.nextLine();
	int numberOfPlayers = Integer.parseInt(enteredNumber);
	
	System.out.println("=== Finished setting the number of players. ===\n\n");
	
	for(int i = 0; i < numberOfPlayers; i++)
	{
		System.out.println("What is player " + (i+1) + "'s name?");
		String playerName = scan.nextLine();
		
		Player newPlayer = new Player();
		newPlayer.setName(playerName);
		
		playerArray.add(newPlayer);
		
	}
	
	System.out.println("\n\n=== Finished initializing players. ===\n\n");
	
	String[] cardArray = {"Knife", "Mrs. Peacock", "Hall"};
	Player testPlayer = (Player) playerArray.get(0);
	for(int i = 0; i < cardArray.length; i++)
	{
		testPlayer.addToPlayedCards(cardArray[i]);
	}
	
	System.out.println("There are " + playerArray.size() + " players:\n");
	
	for(int i = 0; i < playerArray.size(); i++)
	{
		Player retreivedPlayer = (Player) playerArray.get(i);
		System.out.print(retreivedPlayer.getName() + " has played: ");
		for(int j = 0; j < retreivedPlayer.playedCards.size(); j++)
		{
			System.out.print(retreivedPlayer.getPlayedCards(j));
			if(j == (retreivedPlayer.playedCards.size() - 1))
			{
				System.out.print(".");
			}
			else if (j < retreivedPlayer.playedCards.size())
			{
				System.out.print(", ");
			}
		}
		System.out.println("");
	}
	
	
	}//main()

}//GameManagement
