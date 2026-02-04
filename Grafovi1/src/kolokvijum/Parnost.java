package kolokvijum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;

public class Parnost {
	private static void putevi(int start, EdgeWeightedDigraph g) {
		List<List<DirectedEdge>> trips = new ArrayList<>();
		double[] dist = new double[g.V()];
		DirectedEdge[] par = new DirectedEdge[g.V()];
		for(int i = 0; i < g.V(); i++) {
			trips.add(new LinkedList<>());
			dist[i] = Double.MAX_VALUE;
		}
		IndexMinPQ<Double> prio = new IndexMinPQ<>(g.V());
		prio.insert(start, 0.0);
		par[start] = new DirectedEdge(start, start, 0);
		while(!prio.isEmpty()) {
			double w = prio.minKey();
			int x = prio.delMin();
			dist[x] = w;
			if (x != start) {
				List<DirectedEdge> list = new LinkedList<>(trips.get(par[x].from()));
				list.addLast(par[x]);
				trips.set(x, list);
			}
			for(DirectedEdge e : g.adj(x)) {
				int y = e.to();
				double path = e.weight() + w;
				if ((x + y) % 2 == 1 && dist[y] > path) {
					if(!prio.contains(y)) {
						prio.insert(y, path);
					}
					else {
						prio.changeKey(y, path);
					}
					par[y] = e;
				}
			}
		}
		
		for(int i = 0; i < g.V(); i++) {
			double d = dist[i] == Double.MAX_VALUE ? -1 : dist[i];
			System.out.print(i + ": " + d);
			if (d != -1) {
				System.out.print(" --");
				for(DirectedEdge e : trips.get(i)) {
					System.out.print(" " + e.to() + " (" + e.weight() + ")");
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		System.out.print("Unesite ime fajla: ");
		try(BufferedReader b = new BufferedReader(new InputStreamReader(System.in))) {
			String fileName = b.readLine().trim();
			EdgeWeightedDigraph g = new EdgeWeightedDigraph(new In(fileName));
			putevi(0, g);
		}
		catch (Exception e) {
			System.out.println("Greska prilikom ucitavanja fajla.");
		}
	}
}
