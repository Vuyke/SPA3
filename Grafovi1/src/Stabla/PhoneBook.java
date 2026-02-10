package Stabla;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PhoneBook {
	private BST<String, String> names, phones;
	
	public PhoneBook() {
		this.names = new BST<>();
		this.phones = new BST<>();
	}
	
	public boolean put(String name, String phone) {
		if (names.containsKey(name) || phones.containsKey(phone)) {
			return false;
		}
		names.put(name, phone);
		phones.put(phone, name);
		return true;
	}
	
	public String getName(String phone) {
		return phones.get(phone);
	}
	
	public String getPhone(String name) {
		return names.get(name);
	}

	public Set<String> getPhones() {
		return new TreeSet<>(phones.getAll());
	}

	public List<String> getNames() {
		return names.getAll();
	}
}
