package main;

import interfaces.List;

public class User {
	private int id;
	private String name; 
	private List<Integer> checkedOutList;
	public int getId() {
		return this.id;
	}


	public User(int id, String name, List<Integer> checkedOutList) {
		this.setId(id);
		this.setName(name);
		this.setCheckedOutList(checkedOutList);
	}
	public void setId(int id) {
		//check that id is a positive number
		if (id < 0) {
			throw new IllegalArgumentException("Id must be a positive number");
		}
		// TODO check that id is not already set
		if (id > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Id is too large for a 32-bit integer");
		}
		

		this.id = id;
		return;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name.trim(); // remove leading and trailing whitespace
	}

	public List<Integer> getCheckedOutList() {
		return this.checkedOutList;
	}

	public void setCheckedOutList(List<Integer> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
}
