package taxman;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Table is a subclass to the Collection class and is used to manage the
 * <code>Collection</code> of coins in play.
 * 
 * @author Joseph Lewis
 *
 */
public class Table extends Collection {
	// Declare variables for the table
	private LinkedHashSet<Coin> taxable = new LinkedHashSet<Coin>();
	private static Iterator<Coin> it;

	/**
	 * Constructor for Table that fill the table with a collection of coins to
	 * the upper limit given by the human when an instance is created.
	 * 
	 * @param uLim
	 * @throws TaxmanError
	 */
	public Table(int uLim) throws TaxmanError {
		// Sends uLim back to the superclass
		super(uLim);
		// Adds coins to the taxable Collection
		// Note that 1 can never be chosen as it has no divisors
		for (int i = 1; i < this.getSize(); i++) {
			taxable.add(this.getCoin(i + 1));
		}
	}

	/**
	 * Gets the <code>Collection</code> of 'taxable' coins on the table.
	 * 
	 * @return the collection of taxable coins
	 */
	public LinkedHashSet<Coin> getTaxableCoins() {
		return taxable;
	}

	/**
	 * Used to return a string representation of the coins left in the game.
	 * Coins that are valid for taking by the player are surrounded with pipes.
	 * 
	 * @return the game state representation for a command line UI
	 */
	public String getTableState() {
		String tableStr = "";
		it = this.getCollection().iterator();
		Coin currentCoin;
		// Iterate through the collection on the table
		while (it.hasNext()) {
			currentCoin = it.next();
			// If the coin is taxable, represent it with pipes surrounding it
			if (taxable.contains(currentCoin)) {
				tableStr += "|" + String.valueOf(currentCoin.getValue()) + "|, ";
			} else {
				if (currentCoin != null) {
					tableStr += String.valueOf(currentCoin.getValue()) + ", ";
				}
			}

		}
		// Return the table coin values as a string for visualisation
		return tableStr.substring(0, tableStr.length() - 2);
	}

	/**
	 * Used to calculate the coins on the table that are taxable, storing them
	 * in a private <code>LinkedHashSet</code> for the <code>Table</code> class.
	 */
	public void calTaxable() {
		taxable = new LinkedHashSet<Coin>();
		it = this.getCollection().iterator();
		// Iterates through each coin on the table
		while (it.hasNext()) {
			Coin c1 = it.next();
			Iterator<Coin> it2 = this.getCollection().iterator();
			while (it2.hasNext()) {
				Coin c2 = it2.next();
				// Calculates if a coin has a divisor
				if (c1.getValue() > c2.getValue() && c1.getValue() % c2.getValue() == 0) {
					taxable.add(c1);
				}
			}
		}
	}

	/**
	 * This method is used to calculate which coins are valid for taking or
	 * 'taxable' from a set given as a parameter. The valid coins are returned
	 * in the same format.
	 * 
	 * @param coinsInPlay
	 *            is the given set of coins
	 * @return the valid coins to be chosen in that set
	 */
	public static LinkedHashSet<Coin> getTaxable(LinkedHashSet<Coin> coinsInPlay) {
		LinkedHashSet<Coin> taxableInPlay = new LinkedHashSet<Coin>();
		it = coinsInPlay.iterator();
		// Iterates through each coin on the table
		while (it.hasNext()) {
			Coin c1 = it.next();
			Iterator<Coin> it2 = coinsInPlay.iterator();
			while (it2.hasNext()) {
				Coin c2 = it2.next();
				// Calculates if a coin has a divisor
				if ((c1.getValue() > c2.getValue() && c1.getValue() % c2.getValue() == 0)) {
					taxableInPlay.add(c1);
				}
			}
		}
		return taxableInPlay;
	}
}
