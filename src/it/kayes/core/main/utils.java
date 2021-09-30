package it.kayes.core.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.kayes.core.functions.Messages;

public class utils {

	public static void sendMsg(Player p, String s) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
	}
	
	public static void sendMsg(CommandSender p, String s) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
	}
	
	public static int secureTp(Location loc) {
		for (int i = loc.getBlockY(); i>0; i--)
			if (new Location(loc.getWorld(), loc.getBlockX(), i, loc.getBlockZ()).getBlock().getType() != Material.AIR)
				return i;
		return -1;
	}
	
	public static boolean sendServerMsg(Player p, String path) {
		String[] s = Messages.getMessage(path);
		for (String x : s)
			utils.sendMsg(p, x.replaceAll("%PREFIX%", Messages.getMessage("general.prefix")[0]));
		return true;
	}
	
	public static boolean sendServerMsg(CommandSender p, String path) {
		String[] s = Messages.getMessage(path);
		for (String x : s)
			utils.sendMsg(p, x.replaceAll("%PREFIX%", Messages.getPrefix()));
		return true;
	}
	
}
