package it.kayes.core.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import it.kayes.core.main.Main;
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
		//Dati fisici come home e altro, prendi da file
		ResultSet rs = Main.getSQL().executeQuery("SELECT * FROM "+Main.usertable);
		
		while(rs.next()) {
			User u = new User();
			u.setUuid(rs.getString("UUID"));
			u.setName(rs.getString("NICKNAME"));
			
			addUser(u);
		}
	}
	
	public static void save() throws SQLException {
		Main.getSQL().executeUpdate("TRUNCATE "+Main.usertable);
		
		for (User u : getUsers().values())
			Main.getSQL().executeUpdate("INSERT INTO "+Main.usertable + " ('UUID','NICKNAME')"
					+ "VALUES ('"+u.getUuid()+"','"+u.getName()+"')");
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
