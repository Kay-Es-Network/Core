package it.kayes.core.obj;

public class TP {

	String victim;
	boolean tphere;

	public String getVictim() {
		return victim;
	}

	public void setVictim(String victim) {
		this.victim = victim;
	}

	public boolean isTphere() {
		return tphere;
	}

	public void setTphere(boolean tphere) {
		this.tphere = tphere;
	}
	
	public TP(String victim, boolean tphere) {
		this.victim = victim;
		this.tphere = tphere;
	}

}
