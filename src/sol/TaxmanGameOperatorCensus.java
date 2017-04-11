package sol;
import java.util.Scanner;


public class TaxmanGameOperatorCensus {
	
	/** TaxmanGameOperator receives a Player object, which will be used to
	    make the decisions in the game. It runs the whole game from start to end,
	    displaying progress as it goes.
	**/
	public TaxmanGameOperatorCensus(Player player) {
		
		int n = 100;
		
		System.out.println("Checking 2 to " + n);
		
		int losscount = 0;
		int tiecount  = 0;
		int wincount  = 0;
		
		for (int i=2; i<=n; i++) {
			
			int maxsum = 0;
			for (int p=1; p<=i; p++) {
				maxsum+=p;
			}
			
			// create the taxman object
			Taxman txm = new Taxman(i);
			
			// keep getting and applying choices until the game is over
			while (!txm.gameOver()) {
				int chosen = player.chooseNum(txm.getAvailableNumbers());
				if (!txm.choose(chosen))
					System.out.println("Invalid choice!");
//				txm.displayGameState();
			}
			if (!txm.getAvailableNumbers().isEmpty())
				System.err.println("On " + i + ", you haven't cleared out all the numbers at the end!");
			if (txm.getPlayerScore()+txm.getTaxmanScore() != maxsum) {
				System.err.println("On " + i + ", the two scores don't add up to the right amount!");
				System.out.println("player " + txm.getPlayerScore());
				System.out.println("taxman " + txm.getTaxmanScore());
				System.out.println("total: " + maxsum);
			}
			if (txm.getPlayerScore() > txm.getTaxmanScore())
				wincount++;
			else if (txm.getPlayerScore() < txm.getTaxmanScore())
				losscount++;
			else
				tiecount++;
		}
		
		System.out.println("Wins:   " + wincount);
		System.out.println("Ties:   " + tiecount);
		System.out.println("Losses: " + losscount);
	}
}
