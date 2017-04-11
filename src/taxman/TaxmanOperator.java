package taxman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * TaxmanOperator is used run the control of the game. A <code>Game</code>
 * instance is created after the initial setup has complete.
 * 
 * @author Joseph Lewis
 *
 */
public class TaxmanOperator {
	// Declare variables
	public static Game game;
	public static Human inputPlayer;
	private static int upperLimit;
	private static BufferedReader keyboard;

	/**
	 * The use of BufferedReader is a way to capture user input. This method is
	 * used to capture the next line of input a user enters on their keybpard.
	 * 
	 * @return the string of 3
	 * 7the entered information
	 */
	private static String getInput() {
		String in = "";
		try {
			in = keyboard.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return in;
	}

	/**
	 * This method is used to capture the users input to select both the game
	 * mode and upper limit for the round.
	 * 
	 * @throws TaxmanError
	 *             if input invalid to the choices made available is entered
	 */
	public static void setupGame() throws TaxmanError {
		System.out.println("------ Let's play! ------");
		int mode = 0;
		boolean t = true;
		while (t) {
			try {
				// User selects the game mode
				System.out.println("1 - Human       2 - Naive\n3 - Greedy      4 - Optimal");
				System.out.print("> To select a game mode, enter the number corresponding: ");
				mode = Integer.valueOf(getInput());
				// If the the input is out of range, throw a TaxmanError
				if (!(mode > 0) || !(mode < 5)) {
					TaxmanError te = new TaxmanError("Choose a mode from 1-4.");
					throw te;
				}
				// User chooses an upper limit
				System.out.print("> Choose the upper limit for the round: ");
				String inputNum = getInput();
				if (Integer.valueOf(inputNum) >= 2) {
					upperLimit = Integer.valueOf(inputNum);
					t = false;
					// Throw a TaxmanError if the limit is out of range
				} else {
					TaxmanError te = new TaxmanError("Select a legal upper limit >= 2.");
					throw te;
				}
				// Catch any errors and re-attempt to setup the game
			} catch (NumberFormatException e) {
				System.out.println("> Input needs to be a valid number. Let's try again.");
				setupGame();
			} catch (TaxmanError te) {
				System.out.println("> " + te + " Let's try again.");
				setupGame();
			}
		}
		startGame(mode);
	}

	/**
	 * Used to start the game by creating an instance of <code>Game</code> with
	 * the specified upper limit. While the game hasn't finished, moves will be
	 * made by the human or computer, depending on the game mode. When the game
	 * has ended the winner will be printed in the console.
	 * 
	 * @param mode
	 *            is the game mode (Stage) to play with
	 * @throws TaxmanError
	 *             if the move selected in invalid due to the rules of the game
	 */
	public static void startGame(int mode) throws TaxmanError {
		// Create a Game instance
		try {
			game = new Game(upperLimit);
		} catch (TaxmanError e) {
			System.out.println(e);
		}
		String stateOfPlay;
		// Loop until game has ended
		while (!game.gameOver()) {
			// Print the current coin collection of the table
			stateOfPlay = game.getTable().getTableState();
			stateOfPlay += "\nHuman has " + game.getHuman().getScore()[0] + " coins totaling "
					+ game.getHuman().getScore()[1] + ".";
			stateOfPlay += "\nTaxman has " + game.getTaxman().getScore()[0] + " coins totaling "
					+ game.getTaxman().getScore()[1] + ".";
			System.out.println(stateOfPlay);
			// Select a move determined by the game mode

			try {
				String moveInput = null;
				int moveInt = -1;
				// If mode '1', get user input
				if (mode == 1) {
					System.out.print("> Enter the number of the coin to take: ");
					moveInput = getInput();
					if (moveInput == null) {
						System.out.println("> Null input detected. Terminating program...");
						return;
					}
					moveInt = Integer.parseInt(moveInput);
				} else if (mode == 2) { // Modes 2, 3 and 4 used algorithms to
										// compute the move to choose
					moveInt = game.getHuman().naiveMove(game.getTable().getTaxableCoins());
					System.out.println("> The computer has chosen to take coin '" + moveInt + "'.");
				} else if (mode == 3) {
					moveInt = game.getHuman().greedyMove(game.getTable().getCollection());
					System.out.println("> The computer has chosen to take coin '" + moveInt + "'.");
				} else if (mode == 4) {
					// Prepare for the recursive back
					game.getHuman().preOptimal(game.getTable().getCollection().size());
					moveInt = game.getHuman().optimalMove(game.getTable().getCollection()).get(0);
					System.out.println("> The computer has chosen to take coin '" + moveInt + "'.");
				}
				// Play the selected move in the game
				game.getHuman().chooseCoin(game, moveInt);
				// Catch invalid input or errors to be handled
			} catch (NumberFormatException e) {
				System.out.println("> The choice must be a valid number displayed above.");
			} catch (TaxmanError te) {
				System.out.println("> " + te.getMessage());
			}
		}
		// Once the game has ended print the game state one last time
		stateOfPlay = "----- There are no more coins left -----";
		stateOfPlay += "\nHuman has " + game.getHuman().getScore()[0] + " coins totaling "
				+ game.getHuman().getScore()[1] + ".";
		stateOfPlay += "\nTaxman has " + game.getTaxman().getScore()[0] + " coins totaling "
				+ game.getTaxman().getScore()[1] + ".";
		System.out.println(stateOfPlay);
		// Determine the winner to be printed in the console
		String outcome;
		if (game.getTaxman().getCollection().getValue() > game.getHuman().getCollection().getValue()) {
			outcome = "Taxman as the winner";
		} else if (game.getTaxman().getCollection().getValue() < game.getHuman().getCollection().getValue()) {
			outcome = "Human as the winner";
		} else {
			outcome = "a draw";
		}
		// Prompt the user to play again if they enter 'y'
		System.out.print("----- The game has ended with " + outcome + ". -----\n> Enter 'y' to play again: ");
		if (getInput().equals("y")) {
			setupGame();
		} else {
			System.out.print("Thanks for playing!");
		}
	}

	/**
	 * A getter used to return the current game when called.
	 * 
	 * @return the game
	 */
	public static Game getGame() {
		return game;
	}

	/**
	 * Getter used to return the upper limit of the current game.
	 * 
	 * @return the upper limit
	 */
	public static int getUpperLimit() {
		return upperLimit;
	}

	/**
	 * The Main method of the program that starts the Main daemon. The
	 * BufferedReader is initialised and the @see {@link TaxmanOperator#setupGame()} method is called to
	 * start the game.
	 * 
	 * @param args
	 *            is input that can be entered from a command prompt / terminal
	 * @throws TaxmanError
	 *             if an Exception specific to the game arises
	 */
	public static void main(String[] args) {
		keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("----- The Taxman Cometh -----\nFollow the prompts to play the game.");
		try {
			setupGame();
		} catch (TaxmanError te) {
			System.out.println("> " + te);
		}
	}

}
