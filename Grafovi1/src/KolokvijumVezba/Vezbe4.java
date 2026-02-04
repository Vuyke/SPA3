package KolokvijumVezba;

import java.util.List;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class Vezbe4 {
	private EdgeWeightedGraph g;
	
	public Vezbe4(EdgeWeightedGraph g) {
		this.g = g;
	}
	
	public int minVertex() {
		int min = Integer.MAX_VALUE;
		int minVertex = -1;
		for(int i = 0; i < g.V(); i++) {
			int sum = 0;
			for(Edge e : g.adj(i)) {
				sum += e.weight();
			}
			if (sum < min) {
				min = sum;
				minVertex = i;
			}
		}
		return minVertex;
	}
	
	public int maxVertex() {
		int max = 0;
		int maxVertex = -1;
		for(int i = 0; i < g.V(); i++) {
			int sum = 0;
			for(Edge e : g.adj(i)) {
				sum += e.weight();
			}
			if (sum > max) {
				max = sum;
				maxVertex = i;
			}
		}
		return maxVertex;
	}
	
	private double[] dijsktra(int start) {
		IndexMinPQ<Double> queue = new IndexMinPQ<>(g.V());
		double[] dist = Util.doubleArray(g.V(), Double.MAX_VALUE);
		queue.insert(start, 0.0);
		dist[start] = 0;
		while(!queue.isEmpty()) {
			int x = queue.delMin();
			for(Edge e : g.adj(x)) {
				int y = e.other(x);
				double distTo = dist[x] + e.weight();
				if(dist[y] > distTo) {
					dist[y] = distTo;
					if (queue.contains(y)) queue.changeKey(y, distTo);
					else queue.insert(y, distTo);
				}
			}
		}
		return dist;
	}
	
	private double[] dijsktraCapacity(int start, double capacity) {
		IndexMinPQ<Double> queue = new IndexMinPQ<>(g.V());
		double[] dist = Util.doubleArray(g.V(), Double.MAX_VALUE);
		dist[start] = 0;
		queue.insert(start, 0.0);
		while(!queue.isEmpty()) {
			int x = queue.delMin();
			for(Edge e : g.adj(x)) {
				int y = e.other(x);
				double distTo = dist[x] + e.weight();
				if(dist[y] > distTo && e.weight() < capacity) {
					dist[y] = distTo;
					if (queue.contains(y)) queue.changeKey(y, distTo);
					else queue.insert(y, distTo);
				}
			}
		}
		return dist;
	}
	
	private double[] dijsktraCapacity2(int start, double capacity) {
		IndexMinPQ<Double> queue = new IndexMinPQ<>(g.V());
		double[] dist = Util.doubleArray(g.V(), Double.MAX_VALUE);
//		double[] maxCapacity = Util.doubleArray(g.V(), 0.0);
//		dist[start] = 0;
//		maxCapacity[start] = capacity;
//		queue.insert(start, 0.0);
//		while(!queue.isEmpty()) {
//			int x = queue.delMin();
//			for(Edge e : g.adj(x)) {
//				int y = e.other(x);
//				double distTo = dist[x] + e.weight();
//				if (maxCapacity[x] > e.weight()) {
//					double newCapacity = y % 2 == 0 ? capacity : maxCapacity[x] - e.weight();
//					if(dist[y] > distTo) {
//						dist[y] = distTo;
//						if (queue.contains(y)) queue.changeKey(y, distTo);
//						else queue.insert(y, distTo);
//					}
//				}
//			}
//		}
		return dist;
	}
	
	public double minPath(int x, int y) {
		return dijsktra(x)[y];
	}
	
	public List<Double> minPath(int x) {
		return Util.listFromArray(dijsktra(x));
	}
	
	public double ronilac1(int x, int y, double n) {
		double res = dijsktra(x)[y];
		return res < n ? dijsktra(x)[y] : Double.MAX_VALUE;
	}
	
	public double ronilac2(int x, int y, double n) {
		return dijsktraCapacity(x, n)[y];
	}
	
	public double ronilac3(int x, int y, double n) {
		return dijsktraCapacity2(x, n)[y];
	}
}
