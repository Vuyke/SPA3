package Klase;

public class DikstraEntry {
	public int x;
	public double y;
	public double z;
	
	public DikstraEntry(int x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public DikstraEntry(int x, double y) {
		this(x, y, Double.MAX_VALUE);
	}
	
	public static DikstraEntry xz(int x, double z) {
		return new DikstraEntry(x, 0, z);
	}
	
	public static DikstraEntry x(int x) {
		return new DikstraEntry(x, 0, 0);
	}
	
	public DikstraEntry updateZ(double z) {
		return new DikstraEntry(x, y, z);
	}
	
	public DikstraEntry add(DikstraEntry d) {
		return new DikstraEntry(d.x + x, d.y + y, d.z + z);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof DikstraEntry) {
			return ((DikstraEntry) o).x == x && ((DikstraEntry) o).y == y;
		}
		return false;
	}
	
	public static int compare(DikstraEntry a, DikstraEntry b) {
		double val = a.y - b.y;
		return val > 0 ? 1 : (val < 0 ? -1 : 0);
	}
}
