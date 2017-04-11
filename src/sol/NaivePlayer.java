package sol;
import java.util.TreeSet;


public class NaivePlayer implements Player {

	private boolean hasDivisors(int a, TreeSet<Integer> list) {
		for (int n : list)
			if (a%n==0 && a!=n)
				return true;
		return false;
	}
	
	@Override
	public int chooseNum(TreeSet<Integer> avail) {
		while (!avail.isEmpty()) {
			int biggest = avail.last();
			if (hasDivisors(biggest, avail))
				return biggest;
			avail.remove(biggest);
		}
		// no choices were legal
		return -1;
	}
}
