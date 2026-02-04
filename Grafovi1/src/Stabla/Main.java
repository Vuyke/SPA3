package Stabla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		vezbe1();
//		vezbe2();
	}
	
	private static void printTest(String line) {
		System.out.println(line + "\n");
	}
	
	private static void vezbe1() throws IOException {
		BufferedReader r = new BufferedReader(new FileReader("kola2s.txt"));
		String line;
		BST<String, String> bst = new BST<>();
		while((line = r.readLine()) != null) {
			String[] parts = line.strip().split(";");
			if (parts.length == 2) {
				bst.put(parts[0], parts[1]);
			}
		}
		printTest("NS 324 BR se nalazi u listi: " + bst.containsKey("NS 324 BR"));
		printTest("NS 325 BR se nalazi u listi(ne bi trebalo): " + bst.containsKey("NS 325 BR"));
		printTest("Osoba sa tablicama NS 324 BR: " + bst.get("NS 324 BR"));
		printTest("Osoba sa tablicama NS 325 BR: " + bst.get("NS 325 BR"));
		printTest("Najmanji kljuc: " + bst.minKey());
		printTest("Najveci kljuc: " + bst.maxKey());
		printTest("Visina stabla: " + bst.height());
		bst.ispis();
	}
	
	private static void vezbe2() throws IOException {
		Trie trie = new Trie();
		BufferedReader r = new BufferedReader(new FileReader("prezimena.txt"));
		String line;
		while((line = r.readLine()) != null) {
			line = line.strip();
			trie.add(line);
		}
		printTest("Broj Jones-a: " + trie.getCount("Jones"));
		System.out.println("Svi stringovi sa njihovim brojem:"); trie.writeAll();
		printTest("Lista stringova do sada dodatih:\n" + trie.getAllDuplicates());
		printTest("Skup stringova do sada dodatih:\n" + trie.getAll());
		printTest("Lista stringova koji se najvise pojavljuju:\n" + trie.mostFrequent());
		printTest("Lista stringova koji pocinju sa Jo:\n" + trie.getAllWithPrefix("Jo"));
		trie.remove("Palin");trie.remove("Palin");trie.remove("Palin");
		printTest("Lista stringova koji se najvise pojavljuju posle brisanja 3 Palin-a:\n" + trie.mostFrequent());
	}
}
