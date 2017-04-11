package taxman;

import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.junit.Test;

public class HumanTest {
	Human player = new Human();

	/**
	 * The first test for @see {@link Human#naiveMove(LinkedHashSet)} to assert
	 * that the largest valid choice is taken.
	 */
	@Test
	public void NaiveTest1() {
		Collection col = new Collection(6);
		LinkedHashSet<Coin> set = col.getCollection();

		try {
			// Assert largest valid choice is first chosen
			assertEquals(player.naiveMove(set), 6);

		} catch (TaxmanError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The second test for the @see {@link Human#naiveMove(LinkedHashSet)} method, asserting
	 * that the largest valid choice is taken.
	 */
	@Test
	public void NaiveTest2() {
		Collection col = new Collection(25);
		LinkedHashSet<Coin> set = col.getCollection();

		try {
			assertEquals(player.naiveMove(set), 25);
		} catch (TaxmanError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * The first test for @see {@link Human#naiveMove(LinkedHashSet)} to assert
	 * that a greedy choice is made, smarter than the naive move of '6'.
	 */
	@Test
	public void GreedyTest1() {
		Collection col = new Collection(6);
		LinkedHashSet<Coin> set = col.getCollection();

		try {
			// Assert a smarter 'greedy' choice is made
			assertEquals(player.greedyMove(set), 5);

		} catch (TaxmanError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * The first second for @see {@link Human#naiveMove(LinkedHashSet)} to assert
	 * that a greedy choice is made, smarter than the naive move of '25'.
	 */
	@Test
	public void GreedyTest2() {
		Collection col = new Collection(25);
		LinkedHashSet<Coin> set = col.getCollection();

		try {
			// Assert a smarter 'greedy' choice is made
			assertEquals(player.greedyMove(set), 23);

		} catch (TaxmanError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
