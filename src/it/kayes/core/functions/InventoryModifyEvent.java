package it.kayes.core.functions;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import it.kayes.core.main.Main;
import it.kayes.core.obj.User;

public class InventoryModifyEvent implements Listener {

	private static final HashMap<String, String> invsee = new HashMap<String, String>();
	private static final HashMap<String, String> enderchest = new HashMap<String, String>();
	
	public static HashMap<String, String> getInvsee() {
		return invsee;
	}
	
	public static HashMap<String, String> getEnderchest() {
		return enderchest;
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		
		if (getInvsee().containsKey(p.getName())) {
			User u = Main.getUser(getInvsee().get(p.getName()));
			
			Player v = Bukkit.getPlayerExact(u.getName());
			
			if (v==null)
				u.setInv(e.getInventory());
			else
				for (byte i = 0; i<v.getInventory().getSize(); i++)
					v.getInventory().setItem(i, e.getInventory().getItem(i));
			
			u.set();
			
			getInvsee().remove(p.getName());
			
			return;
		}
		
		if (getEnderchest().containsKey(p.getName())) {
			User u = Main.getUser(getEnderchest().get(p.getName()));
			
			Player v = Bukkit.getPlayerExact(u.getName());
			
			if (v==null)
				u.setEnderchest(e.getInventory());
			else
				for (byte i = 0; i<v.getEnderChest().getSize(); i++)
					v.getEnderChest().setItem(i, e.getInventory().getItem(i));
			
			u.set();
			
			getEnderchest().remove(p.getName());
			
			return;
		}
		
	}
	
}
