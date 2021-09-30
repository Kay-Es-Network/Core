package it.kayes.core.listeners;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import it.kayes.core.main.Main;
import it.kayes.core.obj.Home;
import it.kayes.core.obj.User;

public class UserLoader implements Listener {
	
	//DATABASE LOAD
	@SuppressWarnings("deprecation")
	public static void load() throws SQLException {
		File f = new File("plugins/KayEs-Core" + File.separator + "users.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
		Inventory end = Bukkit.createInventory(null, InventoryType.ENDER_CHEST);
		
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {}
		}
		
		//ResultSet rs = Main.getSQL().executeQuery("SELECT * FROM "+Main.usertable);
		
		if (cfg.getString("users")!=null)
		for (String user : cfg.getConfigurationSection("users").getKeys(false)) {
			inv = Bukkit.createInventory(null, InventoryType.PLAYER);
			end = Bukkit.createInventory(null, InventoryType.ENDER_CHEST);
			
			User u = new User();
			u.setUuid(Bukkit.getOfflinePlayer(user).getUniqueId().toString());
			u.setName(user);
			
			List<String> x = cfg.getStringList("users."+user+".homes"); //World,x,y,z,pitch,yaw,name
			Home[] homes = new Home[x.size()];
			
			for (byte i = 0; i<x.size(); i++) {
				String[] param = x.get(i).split(";");
				Location loc = new Location(Bukkit.getWorld(param[0]),Double.valueOf(param[1]),Double.valueOf(param[2]),Double.valueOf(param[3]));
				loc.setPitch(Float.valueOf(param[4]));
				loc.setYaw(Float.valueOf(param[5]));
				homes[i] = new Home(param[6],loc);
			} 
			
			if (cfg.getString("users."+user+".inventory")!=null) 
				for (String key : cfg.getConfigurationSection("users."+user+".inventory").getKeys(false))
					inv.setItem(Integer.valueOf(key), cfg.getItemStack("users."+user+".inventory."+key));
			
			if (cfg.getString("users."+user+".enderchest")!=null) 
				for (String key : cfg.getConfigurationSection("users."+user+".enderchest").getKeys(false))
					end.setItem(Integer.valueOf(key), cfg.getItemStack("users."+user+".enderchest."+key));
			
			if (cfg.getString("users."+user+".speed")!=null)
				u.setSpeed((float)cfg.getDouble("users."+user+".speed"));
			else u.setSpeed(1);
			
			if (cfg.getString("users."+user+".fly")!=null)
				u.setFly(cfg.getBoolean("users."+user+".fly"));
			else u.setFly(false);;
			
			u.setHomes(homes);
			
			u.setInv(inv);
			u.setEnderchest(end);
			
			u.set();
		}
	}
	
	public static void save() throws SQLException {
		File f = new File("plugins/KayEs-Core" + File.separator + "users.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		ArrayList<String> homes = new ArrayList<String>();
		
		if (f.exists()) f.delete();
		
		//Main.getSQL().executeUpdate("TRUNCATE "+Main.usertable);
		
		for (User u : Main.getUsers().values()) {
			/*Main.getSQL().executeUpdate("INSERT INTO "+Main.usertable + " (UUID,NICKNAME)"
					+ "VALUES ('"+u.getUuid()+"','"+u.getName()+"')");*/
			
			homes.clear();
			if (u.getHomes()!=null)
				for (Home h : u.getHomes())
					homes.add(h.getLoc().getWorld().getName()+";"+h.getLoc().getX()+";"+h.getLoc().getY()+";"+h.getLoc().getZ()
							+";"+h.getLoc().getPitch()+";"+h.getLoc().getYaw()+";"+h.getName());
			
			cfg.set("users."+u.getName()+".homes", homes);
			
			Player p = Bukkit.getPlayerExact(u.getName());
			
			if (p==null) {
				for (byte i = 0; i<u.getInv().getSize(); i++)
					cfg.set("users."+u.getName()+".inventory."+i, u.getInv().getItem(i));
				
				for (byte i = 0; i<u.getInv().getSize(); i++)
					cfg.set("users."+u.getName()+".enderchest."+i, u.getEnderchest().getItem(i));
			} else {
				for (byte i = 0; i<p.getInventory().getSize(); i++)
					cfg.set("users."+u.getName()+".inventory."+i, p.getInventory().getItem(i));

				for (byte i = 0; i<u.getInv().getSize(); i++)
					cfg.set("users."+u.getName()+".enderchest."+i, p.getEnderChest().getItem(i));
			}
			
			cfg.set("users."+u.getName()+".speed", u.getSpeed());
			cfg.set("users."+u.getName()+".fly", u.isFly());
		}
		
		try {
			cfg.save(f);
		} catch (IOException e) {}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		User u = Main.getUser(p.getName());
		
		if (u==null) {
			u = new User();
			u.setName(p.getName());
			u.setUuid(p.getUniqueId().toString());
			u.setInv(p.getInventory());
			u.setEnderchest(p.getEnderChest());
			u.setHomes(new Home[0]);
			u.setSpeed(1);
			u.setFly(false);
			
			u.set();
		}
		
		for (byte i = 0; i<u.getInv().getSize(); i++)
			p.getInventory().setItem(i, u.getInv().getItem(i));
		
		p.setWalkSpeed((float)(Math.pow(u.getSpeed(), 2)*-0.01+0.2*u.getSpeed()));
		p.setFlySpeed(u.getSpeed()/10);
		
		p.setAllowFlight(u.isFly());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		User u = Main.getUser(p.getName());
		
		if (u==null) {
			u = new User();
			u.setName(p.getName());
			u.setUuid(p.getUniqueId().toString());
		}
		
		u.setSpeed(p.getWalkSpeed());
		u.setInv(p.getInventory());
		u.setEnderchest(p.getEnderChest());
		u.setFly(p.getAllowFlight());
		
		u.set();
	}
	
}
