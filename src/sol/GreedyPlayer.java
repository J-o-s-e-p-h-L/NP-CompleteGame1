package sol;
import java.util.TreeSet;


public class GreedyPlayer implements Player {
	
	private boolean hasDivisors(int a, TreeSet<Integer> list) {
		for (int n : list)
			if (a%n==0 && a!=n)
				return true;
		return false;
	}
	
	private int sumDivisors(int a, TreeSet<Integer> list) {
		int sum = 0;
		for (int z : list) {
			if (a%z == 0 && z < a) {
				sum += z;
			}
		}
		return sum;
	}

	@Override
	public int chooseNum(TreeSet<Integer> avail) {
		// Implement a Greedy strategy for choosing which number the
		// player will take. return which number you choose for this turn.
		
		int besta   = -1;
		int maxdiff = -1;
		
		for (int a : avail) {
			int taxmangets = sumDivisors(a, avail);
			int diff = a - taxmangets;
					
			if (diff > maxdiff && taxmangets>0) {
				maxdiff = diff;
				besta = a;
			}
		}
		
		return besta;
	}
}
