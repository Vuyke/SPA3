package KolokvijumVezba;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

public class Vezbe1 {
	private Graph g;
	
	public Vezbe1(Graph g) {
		this.g = g;
	}
	
	private List<Integer> listFromArray(int[] a) {
		List<Integer> list = new LinkedList<>();
		for(int x : a) list.add(x);
		return list;
	}
	
	private Set<Integer> component(int start, boolean[] visited) {
		Set<Integer> component = new HashSet<>();
		dfsComponent(start, component, visited);
		return component;
	}
	
	public Set<Integer> component(int start) {
		return component(start, Util.booleanArray(g.V()));
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
	
	private void dfsComponent(int start, Set<Integer> component, boolean[] visited) {
		visited[start] = true;
		component.add(start);
		for(int x : g.adj(start)) {
			if (!visited[x]) {
				dfsComponent(x, component, visited);
			}
		}
	}
	
	private void dfsPaths(int start, int prev, int[] par) {
		par[start] = prev;
		for(int x : g.adj(start)) {
			if (par[x] == -1) {
				dfsPaths(x, start, par);
			}
		}
	}
	
	private boolean dfsPathsForCycle(int start, int prev, int[] par) {
		par[start] = prev;
		for(int x : g.adj(start)) {
			if (par[x] == -1) {
				if(dfsPathsForCycle(x, start, par)) {
					return true;
				}
			}
			else if (par[x] != prev) {
				return true;
			}
		}
		return false;
	}
	
	public boolean pathExists(int x, int y) {
		return component(x).contains(y);
	}
	
	public List<Integer> path(int x, int y) {
		int[] par = Util.integerArray(g.V());
		dfsPaths(x, x, par);
		if(par[y] == -1) {
			return null;
		}
		List<Integer> list = new LinkedList<>();
		int cur = y;
		while(cur != x) {
			list.addFirst(cur);
			cur = par[cur];
		}
		list.addFirst(x);
		return list;
	}
	
	private void bfsDist(int start, int[] dist) {
		Queue<Integer> q = new Queue<>();
		q.enqueue(start);
		dist[start] = 0;
		while(!q.isEmpty()) {
			int cur = q.dequeue();
			for(int x : g.adj(cur)) {
				if(dist[x] == -1) {
					dist[x] = dist[cur] + 1;
					q.enqueue(x);
				}
			}
		}
	}
	
	public List<Integer> distances(int start) {
		int[] dist = Util.integerArray(g.V());
		bfsDist(start, dist);
		return listFromArray(dist);
	}
	
	public boolean cycleExists() {
		int[] par = Util.integerArray(g.V());
		for(int i = 0; i < g.V(); i++) {
			if(par[i] == -1) {
				if(dfsPathsForCycle(i, i, par)) {
					return true;
				}
			}
		}
		return false;
	}
}
