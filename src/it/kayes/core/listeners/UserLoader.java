package it.kayes.core.listeners;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import it.kayes.core.main.Main;
import it.kayes.core.obj.Home;
import it.kayes.core.obj.User;

public class UserLoader implements Listener {

	private static HashMap<String, User> users = new HashMap<String, User>();
	
	public static HashMap<String, User> getUsers() {
		return users;
	}
	
	public static User getUser(String user) {
		return getUsers().get(user);
	}
	
	public static void setUser(User user) {
		if (getUsers().containsKey(user.getName()))
			getUsers().replace(user.getName(), user);
		else 
			getUsers().put(user.getName(), user);
	}
	
	public static void addUser(User user) {
		getUsers().put(user.getName(), user);
	}
	
	//DATABASE LOAD
	public static void load() throws SQLException {
		File f = new File("plugins/KayEs-Core" + File.separator + "users.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {}
		}
		
		ResultSet rs = Main.getSQL().executeQuery("SELECT * FROM "+Main.usertable);
		
		while(rs.next()) {
			User u = new User();
			u.setUuid(rs.getString("UUID"));
			u.setName(rs.getString("NICKNAME"));
			
			List<String> x = cfg.getStringList("users."+u.getName()+".homes"); //World,x,y,z,pitch,yaw,name
			Home[] homes = new Home[x.size()];
			
			for (byte i = 0; i<x.size(); i++) {
				String[] param = x.get(i).split(";");
				Location loc = new Location(Bukkit.getWorld(param[0]),Double.valueOf(param[1]),Double.valueOf(param[2]),Double.valueOf(param[3]));
				loc.setPitch(Float.valueOf(param[4]));
				loc.setYaw(Float.valueOf(param[5]));
				homes[i] = new Home(param[6],loc);
			}
			
			u.setHomes(homes);
			
			addUser(u);
		}
	}
	
	public static void save() throws SQLException {
		File f = new File("plugins/KayEs-Core" + File.separator + "users.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		ArrayList<String> homes = new ArrayList<String>();
		
		if (f.exists()) f.delete();
		
		Main.getSQL().executeUpdate("TRUNCATE "+Main.usertable);
		
		for (User u : getUsers().values()) {
			Main.getSQL().executeUpdate("INSERT INTO "+Main.usertable + " (UUID,NICKNAME)"
					+ "VALUES ('"+u.getUuid()+"','"+u.getName()+"')");
			
			homes.clear();
			
			for (Home h : u.getHomes())
				homes.add(h.getLoc().getWorld().getName()+";"+h.getLoc().getX()+";"+h.getLoc().getY()+";"+h.getLoc().getZ()
						+";"+h.getLoc().getPitch()+";"+h.getLoc().getYaw()+";"+h.getName());
			
			cfg.set("users."+u.getName()+".homes", homes);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (getUser(p.getName())==null) {
			User u = new User();
			u.setName(p.getName());
			u.setUuid(p.getUniqueId().toString());
			
			addUser(u);
		}
	}
	
}
