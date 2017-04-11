package taxman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * The human player in The Taxman game. Human extends the player class and
 * instances inherit their own behaviour. The class includes algorithms to
 * automate naive, greedy and optimal moves for the game when opted.
 * 
 * @author Joseph Lewis
 *
 */
public class Human extends Player {
	// Declare the iterator
	private Iterator<Coin> it;

	private int bestTotal;
	ArrayList<Integer> bestMoves = new ArrayList<Integer>();
	ArrayList<Integer> moves = new ArrayList<Integer>();
	int collectionSize = 0; // Used for original recursion
	int[] path; // OR USE LINKED LIST
	int currentPathLevel = 0;
	int prevScore = 0;

	/**
	 * The constructor for the Human class
	 */
	public Human() {
	}

	/**
	 * The human player used this method to choose a coin to take from the table
	 * during the instance of the game.
	 * 
	 * @param game
	 *            is the game being played
	 * @param val
	 *            is the value of the coin to be taken
	 * @throws TaxmanError
	 *             if the coin chosen does not exist
	 */
	public void chooseCoin(Game game, int val) throws TaxmanError {
		boolean coinFound = false;
		it = game.getTable().getTaxableCoins().iterator();
		Coin currentCoin;
		// Iterate through the table's valid coins until the value of the given
		// coin is found
		while (it.hasNext()) {
			currentCoin = it.next();
			if (currentCoin.getValue() == val) {
				// If the coin is found, take it from the game table
				game.getHuman().take(game.getTable(), game.getTable().getCoin(val), game.getTaxman());
				coinFound = true;
			}
		}
		// If the coin is not present, throw a custom TaxmanError
		if (!coinFound) {
			TaxmanError te = new TaxmanError("The coin to be selected was not found.");
			throw te;
		}
	}

	/**
	 * If the chosen coin is part of the taxable Collection, take it from the
	 * table. The Taxman then gets the rest of the divisors for that coins
	 * value. If there are no other taxable coins in the game, the Taxman gets
	 * the rest of the table.
	 * 
	 * @param table
	 *            contains the collection of coins in play
	 * @param coin
	 *            is the chosen coin by the <code>Human</code>
	 * @param taxman
	 *            is the <code>Taxman</code> in the game
	 * @throws TaxmanError
	 *             if there are no divisors for the specified coin
	 */
	public void take(Table table, Coin coin, Taxman taxman) throws TaxmanError {
		if (table.getTaxableCoins().contains(coin)) {
			// The Human takes the coin from the table
			super.take(table, coin);
			for (int i = 1; i < coin.getValue(); i++) {
				// The Taxman takes all divisors for the chosen coin
				if (coin.getValue() % i == 0) {
					taxman.take(table, i);
				}
			}
			table.calTaxable();
			if (table.getTaxableCoins().isEmpty()) {
				// The taxman takes the rest if there are no more choices
				taxman.takeTable(table);
				// Clear the table - not necessary as the game will end
				// table.getCollection().clear();
			}
			// Throw a TaxmanError if the move is invalid due to there being no
			// divisors for the chosen number
		} else {
			TaxmanError te = new TaxmanError("The Taxman must always get something.");
			throw te;
		}
	}

	/**
	 * Returns the largest taxable coin.
	 * 
	 * @param inPlay
	 *            are the coins on the table
	 * @return the last element of the parsed Set
	 * @throws TaxmanError
	 *             if a game logic error occurs
	 */
	public int naiveMove(LinkedHashSet<Coin> inPlay) throws TaxmanError {
		LinkedHashSet<Coin> taxableCoins = Table.getTaxable(inPlay);
		it = taxableCoins.iterator();
		Coin tC = new Coin(0);
		while (it.hasNext()) {
			// This works as the LinkedHashSet is created in order
			tC = it.next();
		}
		return tC.getValue();
	}

	/**
	 * For loops are used to go through each choice of coin to decide what move
	 * leads to the Taxman gaining the most value gap in that turn and returns
	 * the value of that coin.
	 * 
	 * @param inPlay
	 *            are the coins on the table
	 * @param taxableCoins
	 * @return the greedy move
	 * @throws TaxmanError
	 */
	public int greedyMove(LinkedHashSet<Coin> inPlay) throws TaxmanError {
		Coin greedyChoice = null;
		Integer bestMoveSoFar = null;
		LinkedHashSet<Coin> taxableCoins = Table.getTaxable(inPlay);
		it = taxableCoins.iterator();
		// Iterates through each of the 'taxable' coins
		while (it.hasNext()) {
			Coin tC = it.next();
			int taxmanTotal = 0;
			Iterator<Coin> it2 = inPlay.iterator();
			// Iterates through the coins still in play
			while (it2.hasNext()) {
				Coin tC2 = it2.next();
				if (tC.getValue() > tC2.getValue() && tC.getValue() % tC2.getValue() == 0) {
					taxmanTotal += tC2.getValue();
				}
			}
			// If this move results in the largest gap in points between the
			// plays, set that as the best move
			if (bestMoveSoFar == null || tC.getValue() - taxmanTotal > bestMoveSoFar) {
				greedyChoice = tC;
				bestMoveSoFar = tC.getValue() - taxmanTotal;
			}
		}
		return greedyChoice.getValue();
	}

	/**
	 * Used to return a <code>Coin</coin> LinkedHashSet created from an upper
	 * limit as a parameter for the method.
	 * 
	 * @param lim
	 *            is the upper limit provided
	 * @return the created collection of coins
	 */
	public LinkedHashSet<Coin> createCollection(int size) {
		LinkedHashSet<Coin> set = new LinkedHashSet<Coin>();
		for (int i = 1; i <= size; i++) {
			set.add(new Coin(i));
		}
		return set;
	}

	/**
	 * Called before using @see {@link Human#optimalMove(LinkedHashSet)} to
	 * calculate the optimal moves for the game using recursive backtracking.
	 * Used to set the class variables to their correct starting values.
	 * 
	 * @param setSize
	 *            to set the size of the path array; used to keep track of the
	 *            position for backtracking
	 */
	public void preOptimal(int setSize) {
		// Create the integer array for keeping track of the recursion
		path = new int[setSize];
		for (int i = 0; i < path.length; i++) {
			path[i] = 1;
		}
		prevScore = 0;
		moves.clear();
		bestTotal = 0;
		collectionSize = setSize;
	}

	/**
	 * Helper method for backtracking in @see
	 * {@link Human#optimalMove(LinkedHashSet)}. This method returns a
	 * <code>LinkedHashSet</code> containing the set of coins available for the
	 * next path of calculations on the game tree. The current score obtained
	 * and the current level of recursion is kept track of here.
	 * 
	 * @return the next collection to complete the recursion on
	 */
	public LinkedHashSet<Coin> nextPath() {
		LinkedHashSet<Coin> full = createCollection(collectionSize);
		LinkedHashSet<Coin> tmp = Table.getTaxable(full);
		boolean somethingLeft = true;
		boolean found = false;
		int sum = 0;
		for (int i = 0; i < path.length; i++) {
			it = tmp.iterator();
			if (path[i] != 1) {
				int tC = 0;
				if (it.hasNext()) {
					while (somethingLeft && tC < path[i]) {
						tC = it.next().getValue();
						// If at the end and no success
						if (!it.hasNext() && tC < path[i]) {
							somethingLeft = false;
							found = false;
						} else {
							found = true;
						}
					}
				} else {
					somethingLeft = false;
				}
				// If no more choices in higher level, increment lower
				if (!found) {
					if (i != 0) {
						path[i - 1]++;
						somethingLeft = true;
						// i -= 2;
						// Return to the start of the loop to rebuild collection
						tmp = Table.getTaxable(full);
						i = -1;
					} else {
						return null;
					}
				} else { // Otherwise remove found coin for next level
					// path[i] = tC;
					if (path[i + 1] != 1) {
						sum += tC;
						full.remove(tC);
						// -- Remove coins from table after choice
						Iterator<Coin> it2 = full.iterator();
						while (it2.hasNext()) {
							Coin c2 = it2.next();
							if (tC % c2.getValue() == 0) {
								full.remove(c2);
								it2 = full.iterator();
							}
						} // -- End removal
						tmp = Table.getTaxable(full);
						found = false;
					} else {
						currentPathLevel = i;
						prevScore = sum;
						return tmp;
					}
				}
			}
		}
		prevScore = sum;
		return tmp;
	}

	/**
	 * Recursive backtracking is used along with some helper methods to
	 * calculate all possible combinations of the game tree generated from the
	 * given collection. The move set leading to the highest total is kept track
	 * of and returned at the end of the recursion.
	 * 
	 * @param inPlay
	 *            the collection of coins to find the best moves for
	 * @return the best move set
	 * @throws TaxmanError
	 *             if a game logic related error occurs
	 */
	public ArrayList<Integer> optimalMove(LinkedHashSet<Coin> inPlay) throws TaxmanError {
		// Get the valid moves of the given coins
		LinkedHashSet<Coin> choices = nextPath();
		int score = 0;
		// Test end condition
		if (choices == null) {
			return bestMoves;
		}
		// Take the next coin
		it = choices.iterator();
		int curr = it.next().getValue();
		moves.add(curr);
		score += curr;
		inPlay.remove(curr);
		path[currentPathLevel] = curr;
		// Update best moves if the conditions are met
		if (score > bestTotal) {
			bestMoves.clear();
			bestTotal = score;
			for (int i = 0; i < moves.size(); i++) {
				bestMoves.add(moves.get(i));
			}
		}
		// If there are still nodes to be follows, continue recursion
		choices = nextPath();
		if (choices != null) {
			currentPathLevel++;
			optimalMove(choices);
		}
		return bestMoves;
	}

}
