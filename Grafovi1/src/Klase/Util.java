package Klase;

public class Util {
	public static int compareDouble(double x, double y) {
		double val = x - y;
		return val > 0 ? 1 : (val < 0 ? -1 : 0);
	}
}
