package Klase;

public class Main {
	public static void main(String args[]) {
		try {
			/// Vezbe1
//			Graph g = new Graph("tinyG.txt");
//			System.out.println(g);
//			
//			System.out.println("Broj cvorova: " + g.brojCvorova());
//			System.out.println("Broj grana: " + g.brojGrana());
//			System.out.println("Susedi cvora 0: " + g.susedi(0));
//			
//			System.out.println("Komponente: " + g.komponente());
//			System.out.println("Komponenta cvora 0: " + g.komponenta(0));
//			
//			System.out.println("Postojanje puta izmedju cvorova 0 i 6: " + g.postojanjePuta(0, 6));
//			System.out.println("Postojanje puta izmedju cvorova 0 i 7: " + g.postojanjePuta(0, 7));
//			System.out.println("Put izmedju cvorova 3 i 2: " + g.put(3, 2));
//			System.out.println("Put izmedju cvorova 0 i 7: " + g.put(0, 7));
//			
//			System.out.println("Postojanje ciklusa u grafu: " + g.konture());
//			
//			System.out.println("Iterativni dfs: ");
//			g.dfsIter();
//			
//			System.out.println("Distance izmedju cvora 0 i ostalih cvorova: " + g.distance(0));
//			
//			// Testiranje dodavanja grane
//			System.out.println("Broj komponenti pre dodavanja grane 0-9: " + g.komponente().size());
//			g.dodajGranu(0, 9);
//			System.out.println("Broj komponenti posle dodavanja grane 0-9: " + g.komponente().size());
			
			/// Vezbe2
//			Digraph g = new Digraph("tinyG.txt");
//			System.out.println("Broj cvorova: " + g.brojCvorova());
//			System.out.println("Broj grana: " + g.brojGrana());
//			System.out.println("Susedi cvora 3: " + g.susedi(3));
//			System.out.println(new BipartiteGraph(g).isBipartite());
//			System.out.println("Komponente: " + g.komponenteSlabe());
//			System.out.println("Broj komponenti: " + g.brojKomponentiSlabih());
//			System.out.println("Kontura postoji: " + g.postojanjeKonture());
//			System.out.println("Kontura: " + g.ciklus());
//			System.out.println("Topological: " + g.topologicalSort());
			
			/// Vezbe3
//			DigraphE g = new DigraphE("tinyG.txt");
//			System.out.println("Izvori: " + g.izvori());
//			System.out.println("Usca: " + g.usca());
//			System.out.println("Do svih cvorova iz bar 2: " + g.doSvihOdBar2());
//			System.out.println("Od cvora 0 do svih ostalih: " + g.jednaKomponentaOut(0));
//			System.out.println("Do cvora 12 iz svih ostalih: " + g.jednaKomponentaIn(12));
//			System.out.println("Tranzitivno zatvorenje: " + g.tranzitivnoZatvorenje());
//			System.out.println("Iz nekog do svih: " + g.cvorDoSvihDrugih());
//			System.out.println("Iz svih do nekog: " + g.cvorIzSvihDrugih());
//			Graph g2 = new Graph("tinyG.txt");
//			System.out.println("Pokrivajuce stablo: " + g2.pokrivajuceStablo());
//			
			/// Vezbe 4
//			WeightedGraph g = new WeightedGraph("wGraph.txt");
//			System.out.println("Najkrace od 5 do 4: " + g.najjeftinijiPut(5, 4));
//			System.out.println("Najkrace od 0: " + g.najjeftinijiPutevi(0));
//			System.out.println("Od 5 do 4 sa samo 5 kiseonika: " + g.ronilac1(5, 4, 5.1));
//			System.out.println("Od 0 do 6 sa izranjanjem i 3 kiseonika: " + g.ronilac2(0, 6, 3.1));
//			System.out.println("Od 7 do 6 sa izranjanjem na parnim i 3 kiseonika: " + g.ronilac3(7, 9, 5));
			
			/// Vezbe 5
			WeightedGraph g = new WeightedGraph("wGraph.txt");
			System.out.println("Minimalno stablo: " + g.minimalSpanningTree());
			System.out.println("Minimalno stablo sa sigurnom prvom granom iz fajla: " + g.minimalSpanningTreeFirstEdge());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
