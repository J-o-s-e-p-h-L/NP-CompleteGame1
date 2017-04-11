package taxman;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * The Collection class is used to store a set of Coin instances to be used for
 * the game. The human and taxman both have collections as well as the table
 * with the coins still available.
 * 
 * @author Joseph Lewis
 *
 */
public class Collection {
	// Declare the variables to be used throughout the class'
	// LinkedHashSet is the primary data structure
	private LinkedHashSet<Coin> collection = new LinkedHashSet<Coin>();
	// An iterator is declared for moving through the LinkedHashSets
	private Iterator<Coin> it;

	/**
	 * The Collection constructor used to create an instance for the players.
	 */
	public Collection() {

	}

	/**
	 * The Collection constructor used for the table and fills a collection with
	 * all specified <code>Coin</code> instances for the specified upper limit.
	 * 
	 * @param lim
	 *            is the upper limit.
	 */
	public Collection(int lim) {
		Coin c;
		for (int i = 0; i < lim; i++) {
			c = new Coin(i + 1);
			collection.add(c);
		}
	}

	/**
	 * Used to add a coin to the collection.
	 * 
	 * @param c
	 *            is the specified coin
	 */
	public void add(Coin c) {
		collection.add(c);
	}

	/**
	 * Used to remove a given coin from the collection if it exists.
	 * 
	 * @param c
	 *            is the specified coin
	 */
	public void remove(Coin c) {
		// Iterator is used to scan through the set
		it = collection.iterator();
		while (it.hasNext()) {
			Coin tC = it.next();
			// If the coin is found, remove it
			if (tC.equals(c)) {
				it.remove();
			}
		}
	}

	/**
	 * A selected coin is moved from the current Collection instance to another
	 * specified Collection.
	 * 
	 * @param col
	 *            is the collection for the coin to be moved to
	 * @param c
	 *            the given coin
	 */
	public void move(Collection col, Coin c) {
		if (collection.contains(c)) {
			collection.remove(c);
			col.add(c);
		}
	}

	/**
	 * Returns the Coin with the value parsed through the method
	 * 
	 * @param val
	 *            is the value of the desired Coin
	 * @return the Coin
	 */
	public Coin getCoin(int val) {
		it = collection.iterator();
		while (it.hasNext()) {
			Coin c = it.next();
			// When the Coin is found, return it
			if (c.getValue() == val) {
				return c;
			}
		}
		// Return null if the Coin is not found
		return null;
	}

	/**
	 * A getter method that returns the collection when called.
	 * 
	 * @return
	 */
	public LinkedHashSet<Coin> getCollection() {
		return collection;
	}

	/**
	 * Returns the number of Coins in the collection
	 * 
	 * @return
	 */
	public int getNoCoins() {
		return collection.size();
	}

	/**
	 * Gets the combined value of all the coins in the collection.
	 * 
	 * @return val, the total value of the collection
	 */
	public int getValue() {
		int val = 0;
		it = collection.iterator();
		// While there are Coins in the collections, add the value to the value
		// variable
		while (it.hasNext()) {
			Coin tC = it.next();
			val += tC.getValue();
		}

		return val;
	}

	/**
	 * Gets the number of coins in the collection.
	 * 
	 * @return the number of coins
	 */
	public int getSize() {
		return collection.size();
	}

	/**
	 * Verifies that a Collection contains a given Coin.
	 * 
	 * @param c
	 *            the coin to be tested for
	 * @return the boolean answer to the coin being found
	 */
	public boolean contains(Coin c) {
		return collection.contains(c);
	}

}
