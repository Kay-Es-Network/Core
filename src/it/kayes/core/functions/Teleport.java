package it.kayes.core.functions;

import java.util.HashMap;

import it.kayes.core.obj.TP;

public class Teleport {
	
	private static final HashMap<String, TP> teleports = new HashMap<String, TP>();
	
	public static HashMap<String, TP> getTeleports() {
		return teleports;
	}
	
	public static TP getTeleport(String executor) {
		return getTeleports().get(executor);
	}
	
	public static boolean isTphere(TP tp) {
		return tp.isTphere();
	}
	
	public static String getVictim(TP tp) {
		return tp.getVictim();
	}
	
	public static void cancelRequest(String user) {
		teleports.remove(user);
	}
	
	public static void addRequest(String user, String vic, boolean here) {
		if (getTeleport(user)!=null)
			cancelRequest(user);
		
		getTeleports().put(user, new TP(vic, here));
	}
	
	public static String getRequester(TP tp) {
		String[] t = getTeleports().keySet().toArray(new String[getTeleports().keySet().size()]);
		for (short i = 0; i<t.length; i++)
			if (getTeleports().get(t[i]).getVictim().equalsIgnoreCase(getVictim(tp))) return t[i];
		return null;
	}
	
	public static TP getPlayerTeleport(String user) {
		TP[] t = getTeleports().values().toArray(new TP[getTeleports().values().size()]);
		for (TP x : t) 
			if (x.getVictim().equalsIgnoreCase(user)) return x;
		return null;
	}

}
