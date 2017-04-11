package sol;
import java.util.Scanner;


public class TaxmanGameOperator {
	// --- DO NOT MODIFY THIS FILE --- //
	
	/** TaxmanGameOperator receives a Player object, which will be used to
	    make the decisions in the game. It runs the whole game from start to end,
	    displaying progress as it goes.
	**/
	public TaxmanGameOperator(Player player) {
		
		// get upper limit from system input
		Scanner in = new Scanner(System.in);
		System.out.print("What is the upper limit? ");
		int upper = in.nextInt();
		
		// create the taxman object
		Taxman txm = new Taxman(upper);
		
		// keep getting and applying choices until the game is over
		while (!txm.gameOver()) {
			int chosen = player.chooseNum(txm.getAvailableNumbers());
			System.out.println("\nPlayer chose " + chosen);
			if (!txm.choose(chosen))
				System.out.println("Invalid choice!");
			txm.displayGameState();
		}
	}
}
