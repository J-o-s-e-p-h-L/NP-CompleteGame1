package sol;
import java.util.TreeSet;


public class Taxman {
	
	// member attributes
	private int upperLimit;
	private TreeSet<Integer> numbers;
	private int playerScore = 0;
	private int taxmanScore = 0;
	
	
	
	
	// given N, update `upperLimit` and 
	// fill `numbers` with the necessary numbers.
	public Taxman(int n) {
		numbers = new TreeSet<Integer>();
		upperLimit = n;
		for (int i=1; i<=upperLimit; i++)
			numbers.add(i);
	}
	
	// upperLimit getter
	public int getUpperLimit() {
		return upperLimit;
	}
	
	// playerScore getter
	public int getPlayerScore() {
		return playerScore;
	}
	
	// taxmanScore getter
	public int getTaxmanScore() {
		return taxmanScore;
	}
	
	// return a deep copy of `numbers`
	public TreeSet<Integer> getAvailableNumbers() {
		TreeSet<Integer> clone = new TreeSet<Integer>();
		for (int n : numbers)
			clone.add(n);
		return clone;
	}
	
	// return true if there are divisors of `a` in `numbers` (not including `a`)
	// return false otherwise
	public boolean hasDivisorsOf(int a) {
		for (int n : numbers)
			if ((a%n==0) && a!=n)
				return true;
		return false;
	}
	
	// return true if there are no longer any legal choices for player to make
	public boolean gameOver() {
		for (int n : numbers)
			if (hasDivisorsOf(n))
				return false;
		return true;
	}
	
	// remove all the divisors of `a` in `numbers` (not including `a`)
	// return a TreeSet of all the numbers you removed
	private TreeSet<Integer> removeDivisorsOf(int a) {
		TreeSet<Integer> removed = new TreeSet<Integer>();
		for (int i=1; i<upperLimit; i++) {
			if (numbers.contains(i) && (a%i==0)) {
				numbers.remove(i);
				removed.add(i);
			}
		}
		return removed;
	}

	// the player chooses the number `a` from the set.
	// the taxman then takes some numbers, as per the rules of the game.
	// return `true` if `a` was a valid choice.
	// return `false` if `a` was an invalid choice.
	// this method should also update scores where necessary.
	// if the game is over, the taxman should take all remaining numbers.
	public boolean choose(int a) {
		if (!numbers.contains(a)) return false;
		if (!hasDivisorsOf(a)) return false;
		
		// add choice to player's score
		playerScore += a;
		numbers.remove(a);
		
		// remove divisors and add them to taxman's score
		for (int r : removeDivisorsOf(a)) {
			taxmanScore += r;
		}
		
		// check game over state
		if (gameOver())
			for (int i=1; i<=upperLimit; i++)
				if (numbers.contains(i)) {
					numbers.remove(i);
					taxmanScore += i;
				}
		
		return true;
	}
	
	// a helper function for displaying the current game state.
	public void displayGameState() {
		System.out.print("Remaining nums: ");
		for (int i=0; i<=upperLimit; i++)
			if (numbers.contains(i))
				System.out.print(i + " ");
		
		System.out.println("\nPlayer: " + playerScore + "\n" + "Taxman: " + taxmanScore);
		if (gameOver())
			System.out.println("Game Over!");
	}
}
