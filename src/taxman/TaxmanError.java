package taxman;

/**
 * TaxmanError is a custom Exception subclass, used to throw and catch
 * exceptions specific to the game.
 * 
 * @author Joseph Lewis
 *
 */
public class TaxmanError extends Exception {
	// Generated serialVersionUID
	private static final long serialVersionUID = -1001216979081256204L;

	/**
	 * TaxmanError constructor that takes an <code>TaxmanError</code> and calls
	 * the parents constructor to display the error.
	 * 
	 * @param te
	 *            is the description of the <code>TaxmanError</code>
	 */
	public TaxmanError(String te) {
		super(te);
	}
}
