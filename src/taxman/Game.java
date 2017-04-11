package taxman;

/**
 * Used to create an instance of 'The Taxman' game. Instances of the table and
 * players are created in this class.
 * 
 * @author Joseph Lewis
 *
 */
public class Game {
	// Declare variables
	private static Table table;
	private static Human human;
	private static Taxman taxman;
	private int coins;

	/**
	 * The constructor that creates an instance of the table, human and taxman.
	 * 
	 * @param uLim
	 *            the upper limit of the game
	 * @throws TaxmanError
	 *             if something does not go to plan
	 */
	public Game(int uLim) throws TaxmanError {
		table = new Table(uLim);
		human = new Human();
		taxman = new Taxman();
		coins = uLim;
	}

	/**
	 * A getter method for the game <code>Table</code>.
	 * 
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * A getter method for the game <code>Human</code>.
	 * 
	 * @return the human
	 */
	public Human getHuman() {
		return human;
	}

	/**
	 * A getter method for the game <code>Taxman</code>.
	 * 
	 * @return the taxman
	 */
	public Taxman getTaxman() {
		return taxman;
	}

	/**
	 * Gets the number of coins left in the game.
	 * 
	 * @return the number of coins
	 */
	public int getNoCoins() {
		return coins;
	}

	/**
	 * Determines if the game is over depending on the amount of taxable coins
	 * in play.
	 * 
	 * @return a boolean to describe if the game is over
	 */
	public boolean gameOver() {
		return table.getTaxableCoins().isEmpty();
	}
}
