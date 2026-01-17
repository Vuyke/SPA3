package KolokvijumVezba;

public class Util {
	public static boolean[] booleanArray(int n) {
		return new boolean[n];
	}
	
	public static int[] integerArray(int n) {
		int[] a = new int[n];
		for(int i = 0; i < n; i++) {
			a[i] = -1;
		}
		return a;
	}
}
