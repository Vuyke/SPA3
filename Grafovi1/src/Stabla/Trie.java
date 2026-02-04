package Stabla;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Trie {
	private class Node {
		public int cnt;
		public Map<Character, Node> edges;
		
		public Node(int cnt) {
			this.cnt = cnt;
			this.edges = new HashMap<>();
		}
		
		public Node() {
			this(0);
		}
		
		public Node addChild(char c, boolean inc) {
			Node child = edges.get(c);
			if (child == null) {
				child = new Node(inc ? 1 : 0);
				edges.put(c, child);
			}
			else if (inc) {
				child.cnt++;
				edges.put(c, child);
			}
			return child;
		}
		
		public Node getChild(char c) {
			return edges.get(c);
		}
		
		public void removeChild(char c) {
			edges.remove(c);
		}
	}
	
	private Node root;
	private List<String> mostOftenList;
	private int mostOftenFreq;
	private boolean removeOccured;
	
	public Trie() {
		this.root = new Node();
		this.mostOftenList = new LinkedList<>();
		this.mostOftenFreq = 0;
		this.removeOccured = false;
	}
	
	public void add(String s) {
		Node cur = root;
		for(int i = 0; i < s.length(); i++) {
			boolean inc = i == s.length() - 1;
			cur = cur.addChild(s.charAt(i), inc);
		}
		if (cur.cnt > mostOftenFreq) {
			mostOftenList.clear();
			mostOftenFreq = cur.cnt;
		}
		if (cur.cnt == mostOftenFreq) {
			mostOftenList.add(s);
		}
	}
	
	public int getCount(String s) {
		Node cur = root;
		for(int i = 0; i < s.length(); i++) {
			cur = cur.getChild(s.charAt(i));
			if (cur == null) {
				return 0;
			}
		}
		return cur.cnt;
	}
	
	public void writeAll() {
		writeAll(root, new StringBuilder());
	}
	
	private void writeAll(Node cur, StringBuilder s) {
		if (cur.cnt > 0) {
			System.out.println(s + ": " + cur.cnt);
		}
		for (Map.Entry<Character, Node> entry : cur.edges.entrySet()) {
			s.append(entry.getKey());
			writeAll(entry.getValue(), s);
			s.deleteCharAt(s.length() - 1);
		}
	}
	
	public List<String> getAllDuplicates() {
		List<String> list = new LinkedList<>();
		getAllDuplicates(root, new StringBuilder(), list);
		return list;
	}
	
	private void getAllDuplicates(Node cur, StringBuilder s, List<String> list) {
		for(int i = 0; i < cur.cnt; i++) {
			list.add(s.toString());
		}
		for (Map.Entry<Character, Node> entry : cur.edges.entrySet()) {
			s.append(entry.getKey());
			getAllDuplicates(entry.getValue(), s, list);
			s.deleteCharAt(s.length() - 1);
		}
	}
	
	public List<String> getAllWithPrefix(String prefix) {
		Node cur = root;
		List<String> list = new LinkedList<>();
		for(int i = 0; i < prefix.length() && cur != null; i++) {
			cur = cur.getChild(prefix.charAt(i));
		}
		if (cur != null) {
			getAll(cur, new StringBuilder(prefix), list);
		}
		return list;
	}
	
	public List<String> getAll() {
		return getAllWithPrefix("");
	}
	
	private void getAll(Node cur, StringBuilder s, List<String> list) {
		if (cur.cnt > 0) {
			list.add(s.toString());
		}
		for (Map.Entry<Character, Node> entry : cur.edges.entrySet()) {
			s.append(entry.getKey());
			getAll(entry.getValue(), s, list);
			s.deleteCharAt(s.length() - 1);
		}
	}
	
	public List<String> mostFrequent() {
		if (removeOccured) {
			List<String> list = new LinkedList<>();
			mostFrequent(root, new StringBuilder(), list, getMaxCount(root));
			return list;
		}
		return mostOftenList;
	}
	
	private int getMaxCount(Node cur) {
		int max = cur.cnt;
		for (Node node : cur.edges.values()) {
			max = Math.max(getMaxCount(node), max);
		}
		return max;
	}
	
	private void mostFrequent(Node cur, StringBuilder s, List<String> list, int maxFreq) {
		if (cur.cnt == maxFreq) {
			list.add(s.toString());
		}
		for (Map.Entry<Character, Node> entry : cur.edges.entrySet()) {
			s.append(entry.getKey());
			mostFrequent(entry.getValue(), s, list, maxFreq);
			s.deleteCharAt(s.length() - 1);
		}
	}
	
	public void remove(String s) {
		if (remove(root, s, 0) >= 0) {
			removeOccured = true;
		}
	}
	
	private int remove(Node cur, String s, int ind) {
		if (cur == null) {
			return -1;
		}
		if (ind == s.length()) {
			if (cur.cnt == 0) {
				return -1;
			}
			cur.cnt--;
			return cur.cnt;
		}
		int removed = remove(cur.getChild(s.charAt(ind)), s, ind + 1);
		if (removed == -1) return -1;
		if (removed == 0) {
			cur.removeChild(s.charAt(ind));
		}
		return Math.max(removed, cur.cnt);
	}
}
