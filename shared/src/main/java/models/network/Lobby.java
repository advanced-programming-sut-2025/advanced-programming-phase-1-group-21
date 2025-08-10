package models.network;

import models.game.Game;
import models.user.User;

import java.util.*;

public class Lobby {
	private String name;
	private String ID;
	private String password;
	private boolean isPrivate;
	private boolean isVisible;
	private ArrayList<LobbyUser> users = new ArrayList<>();
	private Game game = null;
	private boolean started = false;

	//private Lobby
	public Lobby(String name, String password, boolean isVisible, boolean isPrivate) {
		this.isPrivate = isPrivate;
		this.isVisible = isVisible;
		this.name = name;
		this.password = password;
		this.ID = Long.toString(Long.parseLong(UUID.randomUUID().toString().replace("-", "").substring(0, 8), 16), 36).toUpperCase();	}

	//Kryo-Net
	public Lobby() {

	}

	public LobbyUser getAdmin() {
		if (!users.isEmpty()) return users.get(0);
		return null;
	}

	public String getName() {
		return name;
	}

	public String getID() {
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

	public ArrayList<LobbyUser> getUsers() {
		return users;
	}

	public boolean checkName(String name) {
		return name.equals(this.name);
	}

	public boolean checkID(String ID) {
		return ID.equals(this.ID);
	}

	public void addUser(LobbyUser user) {
		users.add(user);
	}

	public void addUser(User user) {
		users.add(new LobbyUser(user));
	}

	public void removeUser(LobbyUser user) {
		users.remove(user);
	}

	public LobbyUser getUserByUsername(String username) {
		for (LobbyUser user : users) {
			if (user.user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public boolean isEmpty() {
		return users.isEmpty();
	}

	@Override
	public String toString() {
		return "{lobby=" + name + ", ID=" + ID + ", password=" + password + ", isPrivate=" + isPrivate + ", isVisible=" + isVisible + ", users=" + users + "}";
	}

	public void setMapID(String username, int mapID) {
		for (LobbyUser user : users) {
			if (user.user.getUsername().equals(username)) {
				user.mapID = mapID;
			}
		}
	}

	public void toggleReady(String username) {
		for (LobbyUser user : users) {
			if (user.user.getUsername().equals(username)) {
				user.isReady = !user.isReady;
			}
		}
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public void setStarted() {
		started = true;
	}

	public boolean isStarted() {
		return started;
	}
}
