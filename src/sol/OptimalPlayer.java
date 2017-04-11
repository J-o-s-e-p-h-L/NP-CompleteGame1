package sol;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class OptimalPlayer implements Player {

	private Map<Integer, Set<Integer>> divisors = new HashMap<Integer, Set<Integer>>();

	private class SumTreeSet {
		private Set<Integer> set = new HashSet<Integer>();
		private int sum = 0;
		private int max;

		public SumTreeSet() {
			this.set = new HashSet();
			max = -1;
		}

		public SumTreeSet(Set<Integer> pre) {
			this.set = new HashSet(pre);
			for (int i : set) {
				sum += i;
				if (i > max)
					max = i;
			}
		}

		public boolean isEmpty() {
			return set.isEmpty();
		}

		public boolean contains(int i) {
			if (set.contains(i))
				return true;

			if (i == max)
				max--;
			return false;
		}

		public int getSum() {
			return sum;
		}

		public int getMax() {
			return max;
		}

		public void add(int i) {
			if (set.add(i)) {
				sum += i;
				if (i > max)
					max = i;
			}
		}

		public void addAll(Set<Integer> toadd) {
			for (int i : toadd)
				add(i);
		}

		public void remove(int i) {
			if (set.remove(i)) {
				sum -= i;
				if (i == max)
					max--;
			}
		}

		public void removeAll(Set<Integer> rem) {
			for (int r : rem)
				remove(r);
		}

	}

	@Override
	public int chooseNum(TreeSet<Integer> avail) {

		// hard-code first choice
		if (avail.contains(1))
			return biggestPrime(avail);

		return bestLeadFrom(0, 0, new SumTreeSet(avail)).choice;
	}

	private boolean isPrime(int a) {
		return getDivisors(a).isEmpty();
	}

	private int biggestPrime(TreeSet<Integer> avail) {
		Iterator<Integer> it = avail.descendingIterator();

		while (it.hasNext()) {
			int i = it.next();
			if (isPrime(i))
				return i;
		}
		return avail.last();
	}

	private boolean hasDivOrMult(TreeSet<Integer> avail, int a) {
		Iterator<Integer> it = avail.iterator();
		while (it.hasNext()) {
			int i = it.next();
			if (i < a)
				if (a % i == 0)
					return true;
			if (i > a)
				if (i % a == 0)
					return true;
		}
		return false;
	}

	// returns the divisors of a. BUT ITS A REFERENCE so don't modify what you
	// get back!
	private Set<Integer> getDivisors(int a) {
		if (divisors.containsKey(a))
			return divisors.get(a);

		Set<Integer> divs = new HashSet<Integer>();
		int sqrt = (int) Math.sqrt(a) + 1;
		for (int i = 2; i <= sqrt; i++)
			if (a % i == 0)
				divs.add(i);

		divisors.put(a, divs);
		return divs;
	}

	private SumTreeSet getAvailDivisors(Set<Integer> avail, int a) {
		// return the intersection of the divisors of a and "avail"
		Set<Integer> divs = getDivisors(a);
		SumTreeSet availdivs = new SumTreeSet();
		for (int i : divs)
			if (avail.contains(i))
				availdivs.add(i);
		return availdivs;
	}
	
	private class Lead {
		private int choice;
		private int lead;
		public Lead(int ch, int ld) {
			this.choice = ch;
			this.lead = ld;
		}
	}

	private Lead bestLeadFrom(int taxman, int me, SumTreeSet avail) {
		
		int maxLead = -1000000;
		int maxLeadChoice = -1;
		
		for (int i=1; i<=avail.getMax(); i++) {
			// only iterate if present
			if (!avail.contains(i)) continue;
			
			// get the divisors
			SumTreeSet availdivs = getAvailDivisors(avail.set, i);
			
			// no available divisors. can't do nothing.
			if (availdivs.isEmpty()) continue;
			
			/* HERE WE CHOOSE I */
			{
				// remove used numbers
				avail.remove(i);
				avail.removeAll(availdivs.set);
				
				// CALL RECURSIVELY
				Lead thisLead = bestLeadFrom(taxman+availdivs.sum, me+i, avail);
				
				if (thisLead.lead > maxLead) {
					maxLead = thisLead.lead;
					maxLeadChoice = i;
				}
				
				// restore used numbers.
				avail.add(i);
				avail.addAll(availdivs.set);
			}
		}
		// at this point we know the best choice to make.
		return new Lead(maxLeadChoice, maxLead);
		
	}
}
