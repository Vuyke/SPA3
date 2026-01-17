package KolokvijumVezba;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

public class Main {
	public static void main(String[] args) {
//		vezbe1();
//		vezbe2();
		vezbe3();
		
	}	
	
	public static void vezbe1() {
		Graph g = new Graph(new In("tinyG.txt"));
		Vezbe1 v = new Vezbe1(g);
		System.out.println(g);

		System.out.println("Komponente: " + v.components());
		System.out.println("Komponenta cvora 0: " + v.component(0));
		
		System.out.println("Postojanje puta izmedju cvorova 0 i 6: " + v.pathExists(0, 6));
		System.out.println("Postojanje puta izmedju cvorova 0 i 7: " + v.pathExists(0, 7));
		System.out.println("Put izmedju cvorova 3 i 2: " + v.path(3, 2));
		System.out.println("Put izmedju cvorova 0 i 7: " + v.path(0, 7));
		
		System.out.println("Postojanje ciklusa u grafu: " + v.cycleExists());
		
		System.out.println("Distance izmedju cvora 0 i ostalih cvorova: " + v.distances(0));
		
		// Testiranje dodavanja grane
		System.out.println("Broj komponenti pre dodavanja grane 0-9: " + v.components().size());
		g.addEdge(0, 9);
		System.out.println("Broj komponenti posle dodavanja grane 0-9: " + v.components().size());
	}
	
	public static void vezbe2() {
		Digraph g = new Digraph(new In("tinyG.txt"));
		Graph gU = new Graph(new In("tinyG.txt")); 
		Vezbe2 v = new Vezbe2(g);
		System.out.println("Bipartitan graf: " + v.isBipartite(gU));
		System.out.println("Komponente: " + v.components());
		System.out.println("Broj komponenti: " + v.componentCount());
		System.out.println("Kontura postoji: " + v.cycleExists());
		System.out.println("Kontura: " + v.cycle());
		System.out.println("Topological: " + v.topologicalSort());
	}
	
	public static void vezbe3() {
		Digraph g = new Digraph(new In("tinyG.txt"));
		Vezbe3 v = new Vezbe3(g);
		System.out.println("Izvori: " + v.sources());
		System.out.println("Usca: " + v.mouth());
		System.out.println("Do svih cvorova iz bar 2: " + v.fromAtLeast2ToAll());
		System.out.println("Od cvora 0 do svih ostalih: " + v.connectedToAll(0));
		System.out.println("Do cvora 12 iz svih ostalih: " + v.allConnectedTo(12));
		System.out.println("Tranzitivno zatvorenje: " + v.transitiveClosure());
		System.out.println("Iz nekog do svih: " + v.connectedToAll());
		System.out.println("Iz svih do nekog: " + v.allConnectedTo());
		Graph g2 = new Graph(new In("tinyG.txt"));
		System.out.println("Pokrivajuce stablo graf: " + v.spanningTree(g2));
		System.out.println("Pokrivajuce stablo digraf: " + v.spanningTreeDigraph());
		
	}
}
