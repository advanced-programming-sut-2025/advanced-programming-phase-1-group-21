package models;

import models.user.User;

import java.util.ArrayList;

public class Lobby {
	private final String name;
	private final int ID;
	private final String password;
	private final boolean isPrivate;
	private final boolean isInvisible;
	private final ArrayList<User> users = new ArrayList<>();

	public Lobby(String name, int ID, String password, boolean isPrivate, boolean isInvisible) {
		this.name = name;
		this.ID = ID;
		this.password = password;
		this.isPrivate = isPrivate;
		this.isInvisible = isInvisible;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return ID;
	}

	public String getPassword() {
		return password;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public boolean isInvisible() {
		return isInvisible;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public boolean checkName(String name) {
		return name.equals(this.name);
	}

	public boolean checkID(int ID) {
		return ID == this.ID;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}
}
