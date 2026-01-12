package Klase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class WeightedGraph extends GraphA {
	
	private List<Edge> listaGrana;

	public WeightedGraph(WeightedGraph g) {
		super(g);
	}

	public WeightedGraph(String filename) {
		super(filename);
	}

	public WeightedGraph(int cvorovi) {
		super(cvorovi);
	}

	@Override
	public void dodajGranu(String line) {
		String[] parts = line.split(" ");
		if (parts.length == 3) {
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			double z = Double.parseDouble(parts[2]);
			Set<Edge> trenSusedi = susedi.get(x);
			grane -= trenSusedi.size();
			Edge e = new Edge(x, y, z);
			trenSusedi.add(e);
			susedi.get(y).add(e.reversed());
			grane += trenSusedi.size();
			listaGrana.add(e);
		}
	}
	
	public void dodajGranu(Edge e) {
		dodajGranu(e.from + " " + e.to + " " + e.weight);
	}

	@Override
	protected void init() {
		super.init();
		listaGrana = new LinkedList<>();
	}

	@Override
	protected void iterForCycle() {
		// TODO Auto-generated method stub
	}

	public int suma(int node) {
		int sum = 0;
		for (Edge g : susedi.get(node)) {
			sum += g.weight;
		}
		return sum;
	}

	public int najtezeGrane() {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < cvorovi; i++) {
			max = Math.max(max, suma(i));
		}
		return max;
	}

	public int najlakseGrane() {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < cvorovi; i++) {
			min = Math.min(min, suma(i));
		}
		return min;
	}

	private List<Double> dikstra(DikstraEntry start, F<DikstraEntry, Boolean> check) {
		dfsReset();
		PriorityQueue<DikstraEntry> prio = new PriorityQueue<>(DikstraEntry::compare);
		List<Double> list = new ArrayList<>();
		for (int i = 0; i < cvorovi; i++) {
			list.add(Double.POSITIVE_INFINITY);
		}
		prio.add(start);
		while (prio.size() > 0) {
			DikstraEntry p = prio.poll();
			if (!visited[p.x]) {
				visited[p.x] = true;
				list.set(p.x, p.y);
				for (Edge g : susedi.get(p.x)) {
					int x = g.to;
					DikstraEntry d = new DikstraEntry(x, g.weight, p.z);
					if (!visited[x] && check.apply(d)) {
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
		for (int i = 0; i < cvorovi; i++)
			maxOxygen[i] = -1;
		PriorityQueue<DikstraEntry> prio = new PriorityQueue<>(DikstraEntry::compare);
		List<Double> list = new ArrayList<>();
		for (int i = 0; i < cvorovi; i++) {
			list.add(Double.POSITIVE_INFINITY);
		}
		prio.add(start);
		while (prio.size() > 0) {
			DikstraEntry p = prio.poll();
			if (maxOxygen[p.x] < p.z) {
				maxOxygen[p.x] = p.z;
				list.set(p.x, p.y);
				for (Edge g : susedi.get(p.x)) {
					int x = g.to;
					DikstraEntry d = new DikstraEntry(x, g.weight + p.y, p.z - g.weight);
					if (d.z > 0) {
						if (x % 2 == 0) {
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

	private WeightedGraph minimalSpanningTreeUtil(UnionFind union, List<Edge> list, WeightedGraph tree) {
		for (Edge e : list) {
			int x = e.from, y = e.to;
			if (union.par(x) != union.par(y)) {
				union.add(e);
				tree.dodajGranu(e);
			}
		}
		return tree;
	}
	
	public WeightedGraph minimalSpanningTree() {
		List<Edge> list = new LinkedList<>(listaGrana);
		list.sort((x, y) -> Util.compareDouble(x.weight, y.weight));
		UnionFind union = new UnionFind(cvorovi);
		WeightedGraph tree = new WeightedGraph(cvorovi);
		return minimalSpanningTreeUtil(union, list, tree);
	}
	
	public WeightedGraph minimalSpanningTreeFirstEdge() {
		List<Edge> list = new LinkedList<>(listaGrana);
		UnionFind union = new UnionFind(cvorovi);
		WeightedGraph tree = new WeightedGraph(cvorovi);
		union.add(list.getFirst());
		tree.dodajGranu(list.getFirst());
		list.removeFirst();
		list.sort((x, y) -> Util.compareDouble(x.weight, y.weight));
		return minimalSpanningTreeUtil(union, list, tree);
	}

}
