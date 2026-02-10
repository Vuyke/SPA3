package Stabla;

import java.util.LinkedList;
import java.util.List;

public class Menu {
	private class Node {
		public String description, action, shortcut;
		public Node down, right;
		public List<String> downShortcuts;
		
		public Node(String description, String shortcut, String action) {
			this.description = description;
			this.shortcut = shortcut;
			this.action = action;
			this.down = null;
			this.right = null;
			this.downShortcuts = new LinkedList<>();
		}
		
		public Node(String description, String shortcut) {
			this(description, shortcut, null);
		}
		
		public Node() {
			this("", "");
		}
		
		public boolean contains(String shortcut) {
			return downShortcuts.contains(shortcut);
		}
		
		public String toString() {
			return description + (down == null ? (" - " + action) : "") + ": " + downShortcuts; 
		}
		
		public Node moveTo(String s, boolean p) {
			Node down = this.down;
			while(down != null) {
				if (p && down.shortcut.equals(s)) {
					return down;
				}
				else if (!p && down.description.equalsIgnoreCase(s)) {
					return down;
				}
				down = down.right;
			}
			return null;
		}
	}
	
	private Node root;
	private Node current;
	
	public Menu() {
		this.root = new Node();
	}
	
	public boolean put(String line) {
		String[] parts = line.strip().split(";");
		String[] path = parts[0].split(":");
		if (path[0].equals("")) {
			path = new String[0];
		}
		if (parts.length < 3 || parts.length > 4) {
			System.out.println("Wrong line format: " + line);
			return false;
		}
		Node newNode;
		if (parts.length == 4) {
			newNode = new Node(parts[1], parts[2], parts[3]);
		}
		else {
			newNode = new Node(parts[1], parts[2]);
		}
		return put(root, newNode, path, 0);
	}
	
	private boolean put(Node cur, Node newNode, String[] path, int pathIndex) {
		if (pathIndex == path.length) {
			if (cur.contains(newNode.shortcut)) {
				return false;
			}
			Node down = cur.down;
			cur.down = newNode;
			newNode.right = down;
			cur.downShortcuts.add(newNode.shortcut);
			return true;
		}
		Node next = cur.moveTo(path[pathIndex], false);
		if (next == null) return false;
		return put(next, newNode, path, pathIndex + 1);
	}
	
	public void startInteractive() {
		current = root;
	}
	
	public void printCurrent() {
		System.out.println(current);
	}
	
	public void changeCurrent(String shortcut) {
		Node move = current.moveTo(shortcut, true);
		if (move != null) current = move;
	}
}
