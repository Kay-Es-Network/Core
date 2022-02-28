package it.kayes.core.listeners;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import it.kayes.core.obj.Warp;

public class WarpsLoader {

	private static final HashMap<String, Warp> warps = new HashMap<String, Warp>();
	
	public static HashMap<String, Warp> getWarps() {
		return warps;
	}
	
	public static Warp getWarp(String name) {
		return warps.get(name);
	}

	public static String exactNameWarp(String name) {
		for (String key : warps.keySet())
			if (key.equalsIgnoreCase(name)) return key;
		return null;
	}
	
	public static void load() {
		File f = new File("plugins/KayEs-Core" + File.separator + "warps.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		if (!f.exists() || cfg.getString("warps")==null) return;
		
		for (String key : cfg.getConfigurationSection("warps").getKeys(false)) {
			Location loc = new Location(Bukkit.getWorld(cfg.getString("warps."+key+".world")), cfg.getDouble("warps."+key+".x"),
					cfg.getDouble("warps."+key+".y"), cfg.getDouble("warps."+key+".z"));
			loc.setYaw((float)cfg.getDouble("warps."+key+".yaw"));
			loc.setPitch((float)cfg.getDouble("warps."+key+".pitch"));
			warps.put(key, new Warp(key,loc));
		}
	}
	
	public static void save() {
		File f = new File("plugins/KayEs-Core" + File.separator + "warps.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		if (f.exists()) f.delete();
		
		try {
			f.createNewFile();
		} catch (IOException e) {}
		
		for (Warp w : warps.values()) {
			cfg.set("warps."+w.getName()+".world", w.getLoc().getWorld().getName());
			cfg.set("warps."+w.getName()+".x", w.getLoc().getX());
			cfg.set("warps."+w.getName()+".y", w.getLoc().getY());
			cfg.set("warps."+w.getName()+".z", w.getLoc().getZ());
			cfg.set("warps."+w.getName()+".pitch", w.getLoc().getPitch());
			cfg.set("warps."+w.getName()+".yaw", w.getLoc().getYaw());
		}
		
		try {
			cfg.save(f);
		} catch (IOException e) {}
	}
	
}
