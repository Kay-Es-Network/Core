package it.kayes.core.obj;

public class User {

	String name;
	String uuid;

	Home[] homes;

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
