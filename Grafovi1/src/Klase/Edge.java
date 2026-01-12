package Klase;

public class Edge {
	public int from, to;
	public double weight;
	
	public Edge(int from, int to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public Edge(int x, int y) {
		this(x, y, 0);
	}
	
	@Override
	public int hashCode() {
		return from * 31 + to;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Edge) {
			Edge edge = (Edge) o;
			return edge.from == from && edge.to == to && edge.weight == weight;
		}
		return false;
	}
	
	public Edge reversed() {
		return new Edge(to, from, weight);
	}
	
	@Override
	public String toString() {
		return to + ": " + weight;
	}
}
