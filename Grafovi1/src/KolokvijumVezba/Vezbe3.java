package KolokvijumVezba;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;

public class Vezbe3 {
	private Digraph g;
	
	public Vezbe3(Digraph g) {
		this.g = g;
	}
	
	public List<Integer> sources() {
		List<Integer> list = new LinkedList<>();
		for(int v = 0; v < g.V(); v++) {
			if(g.indegree(v) == 0) {
				list.add(v);
			}
		}
		return list;
	}
	
	public List<Integer> mouth() {
		List<Integer> list = new LinkedList<>();
		for(int v = 0; v < g.V(); v++) {
			if(g.outdegree(v) == 0) {
				list.add(v);
			}
		}
		return list;
	}
	
	private boolean connectedToAll(int start, Digraph tree) {
		return componentSource(start, tree).size() == g.V();
	}
	
	public boolean connectedToAll(int start) {
		return connectedToAll(start, null);
	}
	
	public Set<Integer> componentSource(int start) {
		return componentSource(start, null);
	}
	
	private Set<Integer> componentSource(int start, Digraph tree) {
		boolean[] visited = Util.booleanArray(g.V());
		Set<Integer> component = new HashSet<>();
		dfsSource(start, visited, component, tree);
		return component;
	}
	
	public boolean fromAtLeast2ToAll() {
		int sum = 0;
		for(int i = 0; i < g.V(); i++) {
			if(connectedToAll(i))
				sum++;
			if (sum >= 2)
				return true;
		}
		return false;
	}
	
	private void dfsSource(int start, boolean[] visited, Set<Integer> component, Digraph tree) {
		visited[start] = true;
		component.add(start);
		for(int x : g.adj(start)) {
			if(!visited[x]) {
				if(tree != null) {
					tree.addEdge(start, x);
				}
				dfsSource(x, visited, component, tree);
			}
		}
	}
	
	public boolean allConnectedTo(int start) {
		g = g.reverse();
		boolean p = connectedToAll(start);
		g = g.reverse();
		return p;
	}
	
	public Digraph transitiveClosure() {
		Digraph d = new Digraph(g.V());
		for(int i = 0; i < g.V(); i++) {
			Set<Integer> component = componentSource(i);
			for(int x : component) {
				if (x != i) d.addEdge(i, x);
			}
		}
		return d;
	}
	
	public int connectedToAll() {
		for(int i = 0; i < g.V(); i++) {
			if(connectedToAll(i)) {
				return i;
			}
		}
		return -1;
	}
	
	public int allConnectedTo() {
		for(int i = 0; i < g.V(); i++) {
			if(allConnectedTo(i)) {
				return i;
			}
		}
		return -1;
	}
	
	public Graph spanningTree(Graph g) {
		Graph tree = new Graph(g.V());
		boolean[] visited = Util.booleanArray(g.V());
		for(int i = 0; i < g.V(); i++) {
			if(!visited[i]) {
				dfsTree(i, visited, tree, g);
			}
		}
		return tree;
	}
	
	private void dfsTree(int start, boolean[] visited, Graph tree, Graph g) {
		visited[start] = true;
		for(int x : g.adj(start)) {
			if(!visited[x]) {
				tree.addEdge(start, x);
				dfsTree(x, visited, tree, g);
			}
		}
	}
	
	public Digraph spanningTreeDigraph() {
		for(int i = 0; i < g.V(); i++) {
			Digraph tree = new Digraph(g.V());
			if(connectedToAll(i, tree)) {
				return tree;
			}
		}
		return null;
	}
}
