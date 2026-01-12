package Klase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public abstract class GraphA {
	protected List<Set<Edge>> susedi; // Lista susedstva
	protected int[] dist; // Lista distanci koja se popunjava prilikom bfs
	protected int[] prev; // Lista prethodnika koja se popunjava prilikom bfs
	protected List<Set<Integer>> komponente; // Povezane komponente grafa
	protected boolean[] visited; // Niz koji predstavlja koji cvorovi su poseceni u trenutnom prolazenju kroz graf
	protected int cvorovi; // Broj cvorova
	protected int grane; // Broj grana
	protected boolean cycle; // Da li postoji ciklus u grafu
	protected int cycleX, cycleY;
	
	protected void init() { // Inicijalizacija pomocnih promenljivih
		dist = new int[cvorovi];
		prev = new int[cvorovi];
		visited = new boolean[cvorovi];
		
		susedi = new ArrayList<>();
		for (int i = 0; i < cvorovi; i++) {
			susedi.add(new HashSet<>());
		}
	}
	
	public GraphA(String fileName) {
		try (BufferedReader r = new BufferedReader(new FileReader(fileName))) {
			cvorovi = Integer.parseInt(r.readLine());
			r.readLine();
			init();
			String line;
			while ((line = r.readLine()) != null) {
				dodajGranu(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GraphA(GraphA g) {
		this.cvorovi = g.cvorovi;
		init();
		for(int i = 0; i < cvorovi; i++) {
			for(Edge x : g.susedi.get(i)) {
				dodajGranu(i + " " + x);
			}
		}
	}
	
	public GraphA(int cvorovi) {
		this.cvorovi = cvorovi;
		init();
	}
	
	public int brojCvorova() {
		return cvorovi;
	}
	
	public int brojGrana() {
		return grane;
	}
	
	public Set<Edge> susedi(int x) { // Vraca suseda cvora x
		return susedi.get(x);
	}
	
	public abstract void dodajGranu(String line); // Drugacija implementacija za digraph i undirected graph
	protected abstract void iterForCycle();
	
	protected void bfsReset() { // Pomocna funkcija za resetovanje pomocnih promenljivih pre bfs obilaska grafa
		for(int i = 0; i < cvorovi; i++) {
			dist[i] = -1;
			prev[i] = -1;
		}
	}
	
	protected void dfsReset() { // Pomocna funkcija za resetovanje pomocnih promenljivih pre dfs obilaska grafa
		for(int i = 0; i < cvorovi; i++) {
			visited[i] = false;
		}
	}
	
	protected void bfs() { // bfs obilazak grafa, usput pamtimo i da li ima ciklus u grafu
		cycle = false;
		bfsReset();
		for(int i = 0; i < cvorovi; i++) {
			if (dist[i] == -1) { // Ako distanca do cvora i nije azurirana (= -1), znaci da ga jos nismo obisli
				bfs(i);
			}
		}
	}
	
	protected void bfs(int start) { // bfs obilazak od cvora start
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		dist[start] = 0;
		while(q.size() > 0) {
			int cur = q.poll();
			for (Edge g : susedi.get(cur)) {
				int x = g.to;
				if (dist[x] == -1) {
					dist[x] = dist[cur] + 1;
					prev[x] = cur;
					q.add(x);
				}
			}
		}
	}
	
	public void dfsIter() { // Iterativni dfs
		dfsReset();
		for(int i = 0; i < cvorovi; i++) {
			if (!visited[i]) { // Pustamo dfs iz cvora ako jos nije posecen
				dfsIter(i);
			}
		}
		System.out.println("Kraj iterativnog dfs");
	}
	
	private void dfsIter(int start) { // Iterativni dfs iz cvora start
		Stack<Integer> s = new Stack<>();
		s.add(start);
		while(s.size() > 0) {
			int cur = s.pop();
			if (!visited[cur]) {
				visited[cur] = true; // Za razliku od bfs, tek ovde mozemo postaviti visited na true
				System.out.print(cur + ", ");
				for (Edge g : susedi.get(cur)) {
					int x = g.to;
					if (!visited[x]) {
						s.add(x);
					}
				}
			}
		}
		System.out.println();
	}
	
	protected void dfs() { // dfs obilazak grafa, usput kupimo i sve povezane komponente grafa
		komponente = new LinkedList<>();
		dfsReset();
		for(int i = 0; i < cvorovi; i++) {
			if (!visited[i]) {
				Set<Integer> component = new HashSet<>();
				dfs(i, component);
				komponente.add(component);
			}
		}
	}
	
	protected void dfs(int start, Set<Integer> component) {
		visited[start] = true;
		component.add(start);
		for(Edge g : susedi.get(start)) {
			int x = g.to;
			if (!visited[x]) {
				dfs(x, component);
			}
		}
	}

	public List<Integer> put(int x, int y) { // Vraca put izmedju cvorova x i y ukoliko postoji
		bfsReset();
		bfs(x);
		if (dist[y] == -1) {
			System.out.println("Ne postoji put izmedju cvorova " + x + " i " + y);
			return null;
		}
		else {
			List<Integer> put = new LinkedList<>();
			int cur = y;
			put.add(cur);
			while (cur != x) {
				cur = prev[cur];
				put.add(cur);
			}
			return put.reversed();
		}
	}
	
	public List<Integer> distance(int start) { // Vraca listu distanci cvora start i svih ostalih cvorova u grafu
		bfsReset();
		bfs(start);
		List<Integer> distList = new LinkedList<>();
		for(int i = 0; i < cvorovi; i++) {
			distList.add(dist[i]);
		}
		return distList;
	}
	
	public boolean postojanjeKonture() { // Proverava da li postoje konture u grafu
		iterForCycle();
		return cycle;
	}
	
	public List<Integer> ciklus() { // Ispis ciklusa
		iterForCycle();
		if (!cycle) {
			System.out.println("Ne postoji ciklus u grafu!");
			return null;
		}
		System.out.println(cycleX + " " + cycleY);
		List<Integer> list = put(cycleX, cycleY); list.add(cycleX);
		return list;
	}
	
	public boolean postojanjePuta(int x, int y) { // Vraca da li postoji put izmedju cvorova x i y
		dfsReset();
		Set<Integer> s = new HashSet<>();
		dfs(x, s);
		return s.contains(y);
	}
	
	@Override
	public String toString() { // Ispis grafa preko liste susedstva
		return susedi.toString();
	}
}

