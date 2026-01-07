package Klase;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Graph extends GraphA {
	
	public Graph(String fileName) {
		super(fileName);
	}
	
	public Graph(int cvorovi) {
		super(cvorovi);
	}

	@Override
	public void dodajGranu(String line) {
		String[] parts = line.split(" ");
		if (parts.length == 2) {
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			Set<GraphEntry> trenSusedi = susedi.get(x);
			grane -= trenSusedi.size();
			trenSusedi.add(new IntegerEntry(y));
			susedi.get(y).add(new IntegerEntry(x));
			grane += trenSusedi.size();
		}
	}

	@Override
	protected void iterForCycle() {
		cycle = false;
		bfsReset();
		for(int i = 0; i < cvorovi; i++) {
			if (dist[i] == -1) { // Ako distanca do cvora i nije azurirana (= -1), znaci da ga jos nismo obisli
				bfsForCycle(i);
			}
		}
	}
	
	private void bfsForCycle(int start) { // bfs obilazak od cvora start
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		dist[start] = 0;
		while(q.size() > 0) {
			int cur = q.poll();
			for (GraphEntry g : susedi.get(cur)) {
				int x = g.node;
				if (dist[x] == -1) {
					dist[x] = dist[cur] + 1;
					prev[x] = cur;
					q.add(x);
				}
				else if (x != prev[cur] && !cycle) { // Ako smo vec posetili cvor, a nije prethodni, morali smo zatvoriti ciklus
					cycle = true;
					cycleX = cur;
					cycleY = x;
				}
			}
		}
	}
	
	public Set<Integer> komponenta(int start) { // Vraca komponentu cvora start
		dfsReset();
		Set<Integer> s = new HashSet<>();
		dfs(start, s);
		return s;
	}
	
	public List<Set<Integer>> komponente() { // Vraca sve komponente u grafu
		dfs();
		return komponente;
	}

	public int brojKomponenti() {
		return komponente().size();
	}
	
	public Graph pokrivajuceStablo() {
		Graph g = new Graph(cvorovi);
		dfsReset();
		int brKomponenti = 0;
		for(int i = 0; i < cvorovi; i++) {
			if (!visited[i]) {
				if (brKomponenti >= 1) {
					System.out.println("Vise od jedne komponente, nema pokrivajuce stablo!");
					return null;
				}
				brKomponenti++;
				dfsPokrivajuceStablo(i, g);
			}
		}
		return g;
	}
	
	private void dfsPokrivajuceStablo(int start, Graph graph) {
		visited[start] = true;
		for(GraphEntry g : susedi.get(start)) {
			int x = g.node;
			if (!visited[x]) {
				graph.dodajGranu(start + " " + x);
				dfsPokrivajuceStablo(x, graph);
			}
		}
	}
}