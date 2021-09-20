package it.kayes.core.obj;

public class User {

	String name;
	String uuid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid.replaceAll("-", "");
	}

}
