package it.kayes.core.functions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import it.kayes.core.main.Main;
import it.kayes.core.obj.User;

public class DeathEvents implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		User u = Main.getUser(p.getName());
		if (u==null) return;
		u.setLastLocation(p.getLocation());
		u.setDeathLocation(p.getLocation());
		u.set();
	}

}
