package KolokvijumVezba;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

public class Vezbe2 {
	private Digraph g;
	private Digraph gReversed;
	
	public Vezbe2(Digraph g) {
		this.g = g;
		this.gReversed = g.reverse();
	}
	
	public boolean isBipartite(Graph g) {
		int[] part = Util.integerArray(g.V());
		for(int i = 0; i < g.V(); i++) {
			if(part[i] == -1) {
				if(!bfsBipartite(i, part)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean bfsBipartite(int start, int[] part) {
		Queue<Integer> q = new Queue<>();
		q.enqueue(start);
		part[start] = 0;
		while(!q.isEmpty()) {
			int cur = q.dequeue();
			for(int x : g.adj(cur)) {
				if(part[x] == -1) {
					part[x] = part[cur] ^ 1;
					q.enqueue(x);
				}
				else if (part[x] == part[cur]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public List<Set<Integer>> components() {
		boolean[] visited = Util.booleanArray(g.V());
		List<Set<Integer>> components = new LinkedList<>();
		for(int i = 0; i < g.V(); i++) {
			if(!visited[i]) {
				components.add(component(i, visited));
			}
		}
		return components;
	}
	
	public int componentCount() {
		return components().size();
	}
	
	private Set<Integer> component(int start, boolean[] visited) {
		Set<Integer> component = new HashSet<>();
		dfsComponent(start, visited, component);
		return component;
	}
	
	private void dfsComponent(int start, boolean[] visited, Set<Integer> component) {
		visited[start] = true;
		component.add(start);
		for(int x : g.adj(start)) {
			if(!visited[x]) {
				dfsComponent(x, visited, component);
			}
		}
		for(int x : gReversed.adj(start)) {
			if(!visited[x]) {
				dfsComponent(x, visited, component);
			}
		}
	}
	
	public List<Integer> cycle() {
		int[] par = Util.integerArray(g.V());
		for(int i = 0; i < g.V(); i++) {
			if(par[i] == -1) {
				List<Integer> list = dfsCycle(i, i, par, new HashSet<>());
				if(list != null) return list;
			}
		}
		return null;
	}
	
	public boolean cycleExists() {
		return cycle() != null;
	}
	
	public List<Integer> topologicalSort() {
		if (cycleExists()) return null;
		boolean[] visited = Util.booleanArray(g.V());
		List<Integer> order = new LinkedList<>();
		for(int i = 0; i < g.V(); i++) {
			if(!visited[i]) {
				dfsTopological(i, visited, order);
			}
		}
		return order;
	}
	
	private List<Integer> dfsCycle(int start, int prev, int[] par, Set<Integer> stack) {
		par[start] = prev;
		stack.add(start);
		for(int x : g.adj(start)) {
			if(par[x] == -1) {
				List<Integer> list = dfsCycle(x, start, par, stack);
				if(list != null) return list;
			}
			else if(stack.contains(x)) {
				List<Integer> list = new LinkedList<>();
				list.addFirst(x);
				int cur = start;
				while(cur != x) {
					list.addFirst(cur);
					cur = par[cur];
				}
				list.addFirst(x);
				return list;
			}
		}
		stack.remove(start);
		return null;
	}
	
	private void dfsTopological(int start, boolean[] visited, List<Integer> order) {
		visited[start] = true;
		for(int x : g.adj(start)) {
			if(!visited[x]) {
				dfsTopological(x, visited, order);
			}
		}
		order.addFirst(start);
	}
}
