package Klase;

public class UnionFind {
	private int n;
	private int[] par;
	private int[] size;
	
	private void init() {
		par = new int[n];
		size = new int[n];
		for(int i = 0; i < n; i++) {
			par[i] = i;
			size[i] = 1;
		}
	}
	
	public UnionFind(int n) {
		this.n = n;
		init();
	}
	
	public int par(int x) {
		if (par[x] == x)
			return x;
		par[x] = par(par[x]);
		return par[x];
	}
	
	public void add(int x, int y) {
		x = par(x); y = par(y);
		if (size[x] < size[y]) {
			x ^= y; y ^= x; x ^= y; // swap 2 elements
		}
		size[x] += size[y];
		par[y] = x;
	}
	
	public boolean sameComponent(int a, int b) {
		return par(a) == par(b);
	}
}
