package taxman;

import java.util.Iterator;

/**
 * Player is the class used to define the behaviour to be inherrited by the
 * <code>Human</code> and <code>Taxman</code>.
 */
public class Player {
	// Declare and instantiate the Collection each player has
	private Collection collection = new Collection();
	private Iterator<Coin> it;

	/**
	 * The constructor for the player.
	 */
	public Player() {
	}

	/**
	 * Used to move a coin from the table to the player's collection.
	 * 
	 * @param table
	 *            containing the collection of coins in play
	 * @param coin
	 *            that will be taken
	 */
	public void take(Table table, Coin coin) {
		table.move(collection, coin);
	}

	/**
	 * Moves the coin from the table that has the value from the method
	 * parameter to the player's collection.
	 * 
	 * @param table
	 *            containing the collection of coins in play
	 * @param cVal
	 *            is the value of the coin
	 */
	public void take(Table table, int cVal) {
		// Iterate through the table of coins
		it = table.getCollection().iterator();
		while (it.hasNext()) {
			Coin tC = it.next();
			// If the coin is found, move it to the player's collection
			if (tC.getValue() == cVal) {
				table.move(collection, tC);
				it = table.getCollection().iterator();
			}
		}
	}

	/**
	 * Return the player's <code>Collection</code> of coins obtained during the
	 * game.
	 * 
	 * @return the collection of the player
	 */
	public Collection getCollection() {
		return collection;
	}

	/**
	 * Gets the number of coins the player has and the combined value of those
	 * coins.
	 * 
	 * @return the number of coins and thei total value in an int array
	 */
	public int[] getScore() {
		return new int[] { collection.getNoCoins(), collection.getValue() };
	}

}
