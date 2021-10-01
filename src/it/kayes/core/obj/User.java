package it.kayes.core.obj;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;

import it.kayes.core.main.Main;

public class User {

	private static HashMap<String, User> users = Main.getUsers();

	String name;
	String uuid;

	Home[] homes;

	Inventory inv;
	Inventory enderchest;

	float speed;

	boolean fly;
	boolean god;

	public boolean isGod() {
		return god;
	}

	public void setGod(boolean god) {
		this.god = god;
	}

	public static HashMap<String, User> getUsers() {
		return users;
	}

	public static void setUsers(HashMap<String, User> users) {
		User.users = users;
	}

	public boolean isFly() {
		return fly;
	}

	public void setFly(boolean fly) {
		this.fly = fly;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Inventory getEnderchest() {
		return enderchest;
	}

	public void setEnderchest(Inventory enderchest) {
		this.enderchest = enderchest;
	}

	public Inventory getInv() {
		return inv;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Home[] getHomes() {
		return homes;
	}

	public void setHomes(Home[] homes) {
		this.homes = homes;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid.replaceAll("-", "");
	}

	public void set() {
		users.put(name, this);
	}

}
