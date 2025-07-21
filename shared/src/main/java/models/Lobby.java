package models;

import models.user.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Lobby {
	private String name;
	private int ID;
	private String password;
	private boolean isPrivate;
	private boolean isVisible;
	private ArrayList<User> users = new ArrayList<>();

	//private Lobby
	public Lobby(String name, String password, boolean isVisible, Random rand) {
		isPrivate = true;
		this.isVisible = isVisible;
		this.name = name;
		this.password = password;
		this.ID = createID(rand);
	}

	//public lobby
	public Lobby(String name, boolean isVisible, Random rand) {
		isPrivate = false;
		this.isVisible = isVisible;
		this.name = name;
		this.password = null;
		this.ID = createID(rand);
	}

	public Lobby(String name, String password, boolean isVisible) {}

	public static int createID(Random rand) {
		return rand.nextInt();
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
		return !isVisible;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public String getUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user.getUsername();
			}
		}
		return null;
	}

	public boolean isEmpty() {
		return users.isEmpty();
	}
}
