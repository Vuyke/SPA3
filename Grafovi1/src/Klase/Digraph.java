package Klase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Digraph extends GraphA {
	protected List<Set<GraphEntry>> susediIn, susediOut; // Lista susedstva cvorova koji imaju granu ka datom cvoru
	private Set<Integer> stack; // Stack pomocni za proveru postojanja konture
	private List<Integer> topological; // Lista koja cuva topoloski sort
	private List<Set<Integer>> komponenteSlabe;
	
	public Digraph(String fileName) {
		super(fileName);
		susediOut = susedi;
	}
	
	public Digraph(Digraph g) {
		super(g);
		susediOut = susedi;
	}

	@Override
	public void dodajGranu(String line) { // Dodajemo granu u odvojene liste, jer radimo sa direkcionim grafom
		String[] parts = line.split(" ");
		if (parts.length == 2) {
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			Set<GraphEntry> trenSusedi = susedi.get(x);
			grane -= trenSusedi.size();
			trenSusedi.add(new IntegerEntry(y));
			susediIn.get(y).add(new IntegerEntry(x));
			grane += trenSusedi.size();
		}
	}
	
	private void dfsSlab() {
		komponenteSlabe = new LinkedList<>();
		dfsReset();
		for(int i = 0; i < cvorovi; i++) {
			if (!visited[i]) {
				Set<Integer> component = new HashSet<>();
				dfsSlab(i, component);
				komponenteSlabe.add(component);
			}
		}
	}
	
	protected void dfsSlab(int start, Set<Integer> component) {
		visited[start] = true;
		component.add(start);
		for(GraphEntry g : susediOut.get(start)) {
			int x = g.node;
			if (!visited[x]) {
				dfsSlab(x, component);
			}
		}
		for(GraphEntry g : susediIn.get(start)) {
			int x = g.node;
			if (!visited[x]) {
				dfsSlab(x, component);
			}
		}
	}
	
	@Override
	protected void iterForCycle() {
		topologicalSort();
	}
	
	@Override
	protected void init() {
		super.init();
		susediIn = new ArrayList<>();
		for (int i = 0; i < cvorovi; i++) {
			susediIn.add(new HashSet<>());
		}
		stack = new HashSet<>();
	}
	
	public List<Integer> topologicalSort() {
		dfsReset();
		topological = new LinkedList<>();
		for(int i = 0; i < cvorovi; i++) {
			if (!visited[i]) {
				topologicalSort(i);
				if (cycle) {
					System.out.println("Postoji kontura, topoloski sort je nemoguc!");
					return null;
				}
			}
		}
		return topological.reversed();
	}
	
	private void topologicalSort(int cur) {
		stack.add(cur);
		visited[cur] = true;
		for(GraphEntry g : susediOut.get(cur)) {
			int x = g.node;
			if (!visited[x]) {
				topologicalSort(x);
			}
			else if (stack.contains(x)) {
				cycle = true;
				cycleX = x;
				cycleY = cur;
			}
		}
		topological.add(cur);
		stack.remove(cur);
	}
	
	public int inDegree(int x) {
		return susediIn.get(x).size();
	}
	
	
	public int outDegree(int x) {
		return susediOut.get(x).size();
	}
	
	public List<Set<Integer>> komponenteSlabe() {
		dfsSlab();
		return komponenteSlabe;
	}
	
	public int brojKomponentiSlabih() {
		return komponenteSlabe().size();
	}
	
	public Set<Integer> komponentaOut(int start) { // Vraca komponentu cvora start
		dfsReset();
		Set<Integer> s = new HashSet<>();
		dfs(start, s);
		return s;
	}
}

