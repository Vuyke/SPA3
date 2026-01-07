package Klase;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DigraphE extends Digraph {

	public DigraphE(String fileName) {
		super(fileName);
	}
	
	public List<Integer> izvori() {
		List<Integer> sources = new LinkedList<>();
		for(int i = 0; i < cvorovi; i++) {
			if (inDegree(i) == 0) {
				sources.add(i);
			}
		}
		return sources;
	}
	
	public List<Integer> usca() {
		List<Integer> outs = new LinkedList<>();
		for(int i = 0; i < cvorovi; i++) {
			if (outDegree(i) == 0) {
				outs.add(i);
			}
		}
		return outs;
	}
	
	public boolean jednaKomponentaOut(int x) {
		Set<Integer> set = new HashSet<>();
		dfsReset();
		dfs(x, set);
		return set.size() == cvorovi;
	}
	
	public boolean jednaKomponentaIn(int x) {
		List<Set<GraphEntry>> tmp = susedi;
		susedi = susediIn;
		boolean res = jednaKomponentaOut(x);
		susedi = tmp;
		return res;
	}
	
	public boolean doSvihOdBar2() {
		int cnt = 0;
		for(int i = 0; i < cvorovi; i++) {
			if (jednaKomponentaOut(i)) {
				cnt++;
			}
			if (cnt >= 2) {
				return true;
			}
		}
		return false;
	}
	
	public Digraph tranzitivnoZatvorenje() {
		Digraph g = new Digraph(this);
		for(int i = 0; i < cvorovi; i++) {
			for(int x : komponentaOut(i)) {
				if (i != x) g.dodajGranu(i + " " + x);
			}
		}
		return g;
	}
	
	public boolean cvorDoSvihDrugih() {
		for(int i = 0; i < cvorovi; i++) {
			if (komponentaOut(i).size() == cvorovi) {
				return true;
			}
		}
		return false;
	}
	
	public boolean cvorIzSvihDrugih() {
		List<Set<GraphEntry>> tmp = susedi;
		susedi = susediIn;
		boolean res = cvorDoSvihDrugih();
		susedi = tmp;
		return res;
	}

}
