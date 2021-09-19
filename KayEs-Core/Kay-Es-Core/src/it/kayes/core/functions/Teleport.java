package it.kayes.core.functions;

import java.util.HashMap;

import org.bukkit.Bukkit;

import it.kayes.core.main.utils;
import it.kayes.core.obj.TP;

public class Teleport {
	
	private static HashMap<String, TP> teleports = new HashMap<String, TP>(); 
	
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
	
	public static void denyRequest(String user) {
		teleports.remove(user);
	}
	
	public static String getRequester(TP tp) {
		TP[] t = getTeleports().values().toArray(new TP[getTeleports().values().size()]);
		for (int i = 0; i<t.length; i++)
			if (t[i].getVictim().equalsIgnoreCase(tp.getVictim())) return getTeleports().
	}
	
	public static void acceptRequest(String vic) {
		if (Bukkit.getPlayerExact(vic)==null) {
			
		}
		
		if (utils.secureTp(Bukkit.getPlayerExact(user).getLocation())!=-1) {
			//TP
		}
		
		teleports.remove(user);
	}

}
