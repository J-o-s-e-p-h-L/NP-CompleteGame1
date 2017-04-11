package sol;
import java.util.Scanner;
import java.util.TreeSet;


public class InputPlayer implements Player {

	@Override
	public int chooseNum(TreeSet<Integer> avail) {
		Scanner in = new Scanner(System.in);
		System.out.print("Which number do you choose? ");
		int choice = in.nextInt();
		return choice;
	}
}
