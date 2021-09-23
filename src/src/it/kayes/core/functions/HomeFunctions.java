package it.kayes.core.functions;

import java.util.ArrayList;

import it.kayes.core.listeners.UserLoader;
import it.kayes.core.obj.Home;
import it.kayes.core.obj.User;

public class HomeFunctions {

	public static Home getHome(User u, byte id) {
		if (id>=u.getHomes().length) return null;
		return u.getHomes()[id];
	}
	
	public static void addHome(User u, Home h) {
		ArrayList<Home> homes = new ArrayList<Home>();
		Home[] alh = u.getHomes();
		for (byte i = 0; i<alh.length; i++)
			homes.add(alh[i]);
		
		homes.add(h);
		
		u.setHomes(homes.toArray(new Home[homes.size()]));
		UserLoader.setUser(u);
	}
	
	public static void removeHome(User u, byte id) {
		if (id<0) return;
		ArrayList<Home> homes = new ArrayList<Home>();
		Home[] alh = u.getHomes();
		for (byte i = 0; i<alh.length; i++)
			if (id!=i)
				homes.add(alh[i]);
		
		u.setHomes(homes.toArray(new Home[homes.size()]));
		UserLoader.setUser(u);
	}
	
	public static boolean existHome(Home[] homes, String name) {
		for (Home h : homes)
			if (h.getName().equalsIgnoreCase(name)) return true;
		return false;
	}
	
	public static Home getHomeByName(Home[] homes, String name) {
		for (Home h : homes)
			if (h.getName().equalsIgnoreCase(name)) return h;
		return null;
	}
	
	public static String getNameById(User u, byte id) {
		Home h = getHome(u,id);
		if (h!=null) return h.getName();
		return null;
	}
	
	public static byte getIdByName(Home[] h, String name) {
		for(byte i = 0; i<h.length; i++)
			if (h[i].getName().equalsIgnoreCase(name)) return i;
		return -1;
	}
	
}
