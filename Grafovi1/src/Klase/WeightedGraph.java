package Klase;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class WeightedGraph extends GraphA {

	public WeightedGraph(GraphA g) {
		super(g);
	}
	
	public WeightedGraph(String filename) {
		super(filename);
	}
	
	private double weight(GraphEntry g) {
		return ((PairEntry) g).weight;
	}
	
	@Override
	public void dodajGranu(String line) {
		String[] parts = line.split(" ");
		if (parts.length == 3) {
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			double z = Double.parseDouble(parts[2]);
			Set<GraphEntry> trenSusedi = susedi.get(x);
			grane -= trenSusedi.size();
			trenSusedi.add(new PairEntry(y, z));
			susedi.get(y).add(new PairEntry(x, z));
			grane += trenSusedi.size();
		}
	}

	@Override
	protected void iterForCycle() {
		// TODO Auto-generated method stub
	}
	
	public int suma(int node) {
		int sum = 0;
		for(GraphEntry g : susedi.get(node)) {
			sum += weight(g);
		}
		return sum;
	}
	
	public int najtezeGrane() {
		int max = Integer.MIN_VALUE;
		for(int i = 0; i < cvorovi; i++) {
			max = Math.max(max, suma(i));
		}
		return max;
	}
	
	public int najlakseGrane() {
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < cvorovi; i++) {
			min = Math.min(min, suma(i));
		}
		return min;
	}
	
	private List<Double> dikstra(DikstraEntry start, F<DikstraEntry, Boolean> check) {
		dfsReset();
		PriorityQueue<DikstraEntry> prio = new PriorityQueue<>(DikstraEntry::compare);
		List<Double> list = new ArrayList<>();
		for(int i = 0; i < cvorovi; i++) {
			list.add(Double.POSITIVE_INFINITY);
		}
		prio.add(start);
		while(prio.size() > 0) {
			DikstraEntry p = prio.poll();
			if (!visited[p.x]) {
				visited[p.x] = true;
				list.set(p.x, p.y);
				for(GraphEntry g : susedi.get(p.x)) {
					int x = g.node;
					DikstraEntry d = new DikstraEntry(x, weight(g), p.z);
					if(!visited[x] && check.apply(d)) {
						prio.add(d.add(new DikstraEntry(0, p.y, 0)));
					}
				}
			}
		}
		return list;
	}
	
	private List<Double> dikstra(DikstraEntry start) {
		return dikstra(start, (_) -> true);
	}
	
	private List<Double> dikstraRonilac3(DikstraEntry start, double n) {
		dfsReset();
		double[] maxOxygen = new double[cvorovi];
		for(int i = 0; i < cvorovi; i++) maxOxygen[i] = -1;
		PriorityQueue<DikstraEntry> prio = new PriorityQueue<>(DikstraEntry::compare);
		List<Double> list = new ArrayList<>();
		for(int i = 0; i < cvorovi; i++) {
			list.add(Double.POSITIVE_INFINITY);
		}
		prio.add(start);
		while(prio.size() > 0) {
			DikstraEntry p = prio.poll();
			if (maxOxygen[p.x] < p.z) {
				maxOxygen[p.x] = p.z;
				list.set(p.x, p.y);
				for(GraphEntry g : susedi.get(p.x)) {
					int x = g.node;
					DikstraEntry d = new DikstraEntry(x, weight(g) + p.y, p.z - weight(g));
					if(d.z > 0) {
						if(x % 2 == 0) {
							d.z = n;
						}
						if (maxOxygen[x] < d.z) {
							prio.add(d);
						}	
					}
				}
			}
		}
		return list;
	}
	
	private boolean ronilacCheck(DikstraEntry d) {
		return d.z > d.y;
	}
	
	public double najjeftinijiPut(int x, int y) {
		return dikstra(DikstraEntry.x(x)).get(y);
	}
	
	public List<Double> najjeftinijiPutevi(int x) {
		return dikstra(DikstraEntry.x(x));
	}
	
	public boolean ronilac1(int x, int y, double n) {
		return dikstra(DikstraEntry.x(x)).get(y) < n;
	}
	
	public boolean ronilac2(int x, int y, double n) {
		return !dikstra(DikstraEntry.xz(x, n), this::ronilacCheck).get(y).isInfinite();
	}
	
	public boolean ronilac3(int x, int y, double n) {
		return !dikstraRonilac3(DikstraEntry.xz(x, n), n).get(y).isInfinite();
	}

}
