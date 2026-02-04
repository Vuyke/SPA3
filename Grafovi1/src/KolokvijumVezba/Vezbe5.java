package KolokvijumVezba;

import java.util.ArrayList;
import java.util.List;

import Klase.UnionFind;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class Vezbe5 {
	private EdgeWeightedGraph g;
	
	public Vezbe5(EdgeWeightedGraph g) {
		this.g = g;
	}
	
	private EdgeWeightedGraph minimalSpanningTreeUtil(List<Edge> edges, EdgeWeightedGraph tree, UnionFind union) {	
		for(Edge e : edges) {
			int x = e.either();
			int y = e.other(x);
			if (!union.sameComponent(x, y)) {
				union.add(x, y);
				tree.addEdge(e);
			}
		}
		return tree;
	}
	
	public EdgeWeightedGraph minimalSpanningTree() {
		EdgeWeightedGraph tree = new EdgeWeightedGraph(g.V());
		UnionFind union = new UnionFind(g.V());
		List<Edge> edges = new ArrayList<>();
		for(Edge e : g.edges()) {
			edges.add(e);
		}
		edges.sort((a, b) -> Util.doubleCompare(a.weight(), b.weight()));
		return minimalSpanningTreeUtil(edges, tree, union);
	}
	public EdgeWeightedGraph minimalSpanningTreePredefined() {
		EdgeWeightedGraph tree = new EdgeWeightedGraph(g.V());
		List<Edge> edges = new ArrayList<>();
		UnionFind union = new UnionFind(g.V());
		for(Edge e : g.edges()) {
			edges.add(e);
		}
		Edge first = edges.removeFirst();
		tree.addEdge(first);
		int temp = first.either();
		union.add(temp, first.other(temp));
		edges.sort((a, b) -> Util.doubleCompare(a.weight(), b.weight()));
		return minimalSpanningTreeUtil(edges, tree, union);
	}
}
