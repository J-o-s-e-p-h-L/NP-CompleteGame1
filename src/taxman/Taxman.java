package taxman;

import java.util.Iterator;

/**
 * The Taxman, a subclass of Player used in the game.
 * 
 * @author Joseh Lewis
 *
 */
public class Taxman extends Player {
	private Iterator<Coin> it;

	/**
	 * The empty Taxman constructor used to create <code>Taxman</code> instances
	 */
	public Taxman() {
	}

	/**
	 * Adds coins from the given table to the <code>Collection</code> of the
	 * taxman. This method is called when there are no 'taxable' coins left on
	 * the table.
	 * 
	 * @param table
	 *            is the specified table
	 */
	public void takeTable(Table table) {
		it = table.getCollection().iterator();
		// Iterates through the table's coin collection
		while (it.hasNext()) {
			Coin tC = it.next();
			// Add's a coin to the taxman's collection for each coin on the
			// table
			this.getCollection().add(table.getCoin(tC.getValue()));
		}
	}
}
