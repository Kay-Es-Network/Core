package it.kayes.core.obj;

import org.bukkit.inventory.Inventory;

public class User {

	String name;
	String uuid;

	Home[] homes;
	
	Inventory inv;

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

}
