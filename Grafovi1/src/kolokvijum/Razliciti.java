package kolokvijum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

public class Razliciti {
	private static boolean cvorSusediNeparni(int start, Graph g) {
		for(int x : g.adj(start)) {
			if(x % 2 == 0)
				return false;
		}
		return true;
	}
	
	private static Set<Integer> vratiCvorove(Graph g) {
		Set<Integer> razliciti = new HashSet<>();
		for(int i = 0; i < g.V(); i += 2) {
			if(cvorSusediNeparni(i, g)) {
				razliciti.add(i);
			}
		}
		return razliciti;
	}
	
	public static void main(String[] args) {
		System.out.print("Unesite ime fajla: ");
		try(BufferedReader b = new BufferedReader(new InputStreamReader(System.in))) {
			String fileName = b.readLine().trim();
			Graph g = new Graph(new In(fileName));
			System.out.println(vratiCvorove(g));
		}
		catch (Exception e) {
			System.out.println("Greska prilikom ucitavanja fajla.");
		}
	}
	
}
