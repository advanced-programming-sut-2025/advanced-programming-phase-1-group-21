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
	private User admin;
	private Random rand;

	//private Lobby
	public Lobby(String name, String password, boolean isVisible, boolean isPrivate, Random rand) {
		this.isPrivate = isPrivate;
		this.isVisible = isVisible;
		this.name = name;
		this.password = password;
		this.ID = createID(rand);
		this.rand = rand;
	}

	//Kryo-Net
	public Lobby() {

	}

	public static int createID(Random rand) {
		return rand.nextInt();
	}

	public void setAdmin(User admin) {
		this.admin = admin;
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

	public boolean checkName(String name) {
		return name.equals(this.name);
	}

	public boolean checkID(int ID) {
		return ID == this.ID;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void setRandomAdmin() {
		if (users.isEmpty()) {
			return;
		}
		int idx = rand.nextInt(users.size());
		admin = users.get(idx);
	}

	public void removeUser(User user) {
		users.remove(user);
		if (user.equals(admin)) {
			setRandomAdmin();
		}
	}

	public User getUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public boolean isEmpty() {
		return users.isEmpty();
	}

	public User getAdmin() {
		return admin;
	}
}
