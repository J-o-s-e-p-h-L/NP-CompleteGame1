package sol;

public class Main {
	public static void main(String[] args) {
		
		// Change the below line to use a NaivePlayer or a GreedyPlayer or an OptimalPlayer
		Player player = new GreedyPlayer();
		
		// This kicks off the game. You don't need to change this line.
		new TaxmanGameOperator(player);
	}
}
