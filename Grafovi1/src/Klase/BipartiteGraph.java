package Klase;

public class BipartiteGraph {
	private GraphA graph;
	private int[] colors;
	
	public BipartiteGraph(GraphA graph)  {
		this.graph = graph;
		this.colors = new int[graph.brojCvorova()];
	}
	
	private void dfsReset() {
		for(int i = 0; i < colors.length; i++) {
			colors[i] = 0;
		}
	}
	
	public boolean isBipartite() {
		dfsReset();
		for(int i = 0; i < graph.brojCvorova(); i++) {
			if (colors[i] == 0) {
				colors[i] = 1;
				if (!isBipartiteDfs(i)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isBipartiteDfs(int cur) {
		for (GraphEntry g : graph.susedi(cur)) {
			int x = g.node;
			if (colors[x] != 0 && colors[x] == colors[cur]) {
				return false;
			}
			else if (colors[x] == 0) {
				colors[x] = colors[cur] * -1;
				if (!isBipartiteDfs(x)) return false;
			}
		}
		return true;
	}
}
