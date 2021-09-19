package it.kayes.core.main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		
	}
	
	public static Main getInstance() {
		return instance;
	}

}
