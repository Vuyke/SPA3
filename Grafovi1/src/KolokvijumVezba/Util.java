package KolokvijumVezba;

import java.util.LinkedList;
import java.util.List;

public class Util {
	public static boolean[] booleanArray(int n) {
		return new boolean[n];
	}
	
	public static int[] integerArray(int n, int value) {
		int[] a = new int[n];
		for(int i = 0; i < n; i++) {
			a[i] = value;
		}
		return a;
	}
	
	public static int[] integerArray(int n) {
		return integerArray(n, -1);
	}
	
	public static double[] doubleArray(int n, double value) {
		double[] a = new double[n];
		for(int i = 0; i < n; i++) {
			a[i] = value;
		}
		return a;
	}
	
	public static List<Integer> listFromArray(int[] a) {
		List<Integer> list = new LinkedList<>();
		for(int x : a) list.add(x);
		return list;
	}
	
	public static List<Double> listFromArray(double[] a) {
		List<Double> list = new LinkedList<>();
		for(double x : a) list.add(x);
		return list;
	}
	
	public static int doubleCompare(double a, double b) {
		if (a == b) {
			return 0;
		}
		return a - b > 0 ? 1 : -1;
	}
}
