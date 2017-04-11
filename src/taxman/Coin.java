package taxman;

/**
 * A simple class that is used to create Coin objects.
 * 
 * @author Joseph Lewis
 *
 */
public class Coin {
	// Declare the value variable for a Coin instance
	private int value;

	/**
	 * The Coin constructor, used to create an instance and assign a value to a
	 * coin.
	 * 
	 * @param val
	 *            is the value the coin has
	 */
	public Coin(int val) {
		value = val;
	}

	/**
	 * A getter method for the value of the chosen coin
	 * 
	 * @return the value of the coin
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Used to compare the value of the current coin to a specified coin.
	 * 
	 * @param c
	 *            is the given coin to compare
	 * @return a boolean value to determine if the coin values are equal
	 */
	public boolean equals(Coin c) {
		return this.getValue() == c.getValue();
	}
}
