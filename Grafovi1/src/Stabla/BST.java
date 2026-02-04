package Stabla;

import java.util.Comparator;

public class BST<K extends Comparable<K>, V> {
	private class Node {
		public K key;
		public V value;
		public Node left, right;
		public boolean red;
		
		public Node(K key, V value, Node left, Node right, boolean red) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
			this.red = red;
		}
		
		public Node(K key, V value, boolean red) {
			this(key, value, null, null, red);
		}
		
		public Node(K key, V value) {
			this(key, value, true);
		}
		
		public void invertRed() {
			red = !red;
			left.red = !left.red;
			right.red = !right.red;
		}
		
		public boolean rightRed() {
			return right != null && right.red;
		}
		
		public boolean leftRed() {
			return left != null && left.red;
		}
		
		private String leftKey() {
			return left == null ? "null" : left.key.toString();
		}
		
		private String rightKey() {
			return right == null ? "null" : right.key.toString();
		}
		
		public String toString() {
			return key + ": " + leftKey() + ", " + rightKey() + (red ? " red" : " black"); 
		}
	}
	
	private Node root;
	private Comparator<K> comparator;
	private K minKey, maxKey;
	
	public BST(Comparator<K> comparator) {
		this.comparator = comparator;
	}
	
	public BST() {
		this(Comparator.naturalOrder());
	}
	
	private int compare(K key1, K key2) {
		return comparator.compare(key1, key2);
	}
	
	public void put(K key, V value) {
		if (minKey == null || compare(minKey, key) > 0) minKey = key;
		if (maxKey == null || compare(maxKey, key) < 0) maxKey = key;
		root = put(root, key, value, 0);
	}
	
	private Node put(Node cur, K key, V value, int depth) {
		if (cur == null) return new Node(key, value);
		
		int comp = compare(key, cur.key);
		if (comp == 0) cur.value = value;
		else if (comp < 0) cur.left = put(cur.left, key, value, depth + 1);
		else cur.right = put(cur.right, key, value, depth + 1);
		
		if (cur.rightRed()) {
			if (cur.leftRed()) cur.invertRed();
			else cur = leftRotate(cur);
		}
		else if (cur.leftRed()) {
			Node left = cur.left;
			if (left.rightRed()) cur.left = leftRotate(left);
			if (left.leftRed()) {
				cur = rightRotate(cur);
				cur.invertRed();
			}
		}
		return cur;
	}
	
	public boolean containsKey(K key) {
		return containsKey(root, key);
	}
	
	private boolean containsKey(Node cur, K key) {
		if (cur == null) return false;
		
		int comp = compare(key, cur.key);
		if (comp == 0) return true;
		else if (comp < 0) return containsKey(cur.left, key);
		return containsKey(cur.right, key);
	}
	
	public V get(K key) {
		return get(root, key);
	}
	
	private V get(Node cur, K key) {
		if (cur == null) return null;
		
		int comp = compare(key, cur.key);
		if (comp == 0) return cur.value;
		else if (comp < 0) return get(cur.left, key);
		return get(cur.right, key);
	}
	
	public K minKey() {
		return minKey;
	}
	
	public K maxKey() {
		return maxKey;
	}
	
	public int height() {
		return height(root);
	}
	
	private int height(Node cur) {
		if (cur == null) return 0;
		return Math.max(height(cur.left), height(cur.right)) + 1;
	}
	
	public void balanceIfNeeded() {
		// We do nothing as this is RB implementation, so its always balanced
	}
	
	private void swapRed(Node x, Node y) {
		boolean red = x.red;
		x.red = y.red;
		y.red = red;
	}
	
	private Node leftRotate(Node cur) {
		Node right = cur.right;
		swapRed(cur, right);
		cur.right = right.left;
		right.left = cur;
		return right;
	}
	
	private Node rightRotate(Node cur) {
		Node left = cur.left;
		swapRed(cur, left);
		cur.left = left.right;
		left.right= cur;
		return left;
	}
	
	public void ispis() {
		ispis(root);
	}
	
	private void ispis(Node cur) {
		if (cur == null) return;
		System.out.println(cur);
		ispis(cur.left);
		ispis(cur.right);
	}
}
