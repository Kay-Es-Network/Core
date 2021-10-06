package it.kayes.core.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.BlockIterator;

import it.kayes.core.functions.Messages;

public class utils {

	public static void sendMsg(Player p, String s) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
	}
	
	public static void sendMsg(CommandSender p, String s) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
	}
	
	public static int secureTp(Location loc) {
		for (int i = loc.getBlockY(); i>0; i--) {
			Material lc = new Location(loc.getWorld(), loc.getBlockX(), i, loc.getBlockZ()).getBlock().getType();
			if (lc != Material.AIR && lc != Material.LAVA)
				return i;
		}
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
	
	public static final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }
	
	@SuppressWarnings("deprecation")
	public static ItemStack getPlayerhead(String p) {
		Material m = null;
		if (isMC113OrNewer())
			m = Material.valueOf("LEGACY_SKULL_ITEM");
		else m = Material.SKULL_ITEM;
		
		ItemStack item = new ItemStack(m, 1, (short) 3);
		SkullMeta skull = (SkullMeta) item.getItemMeta();
		skull.setOwner(p);
		item.setItemMeta(skull);
		
		return item;
	}
	
	public static boolean isMC113(){
	    return Bukkit.getBukkitVersion().contains("1.13");
	}
	
	public static boolean isMC114(){
	    return Bukkit.getBukkitVersion().contains("1.14");
	}
	
	public static boolean isMC115(){
	    return Bukkit.getBukkitVersion().contains("1.15");
	}
	
	public static boolean isMC116(){
	    return Bukkit.getBukkitVersion().contains("1.16");
	}
	
	public static boolean isMC117(){
	    return Bukkit.getBukkitVersion().contains("1.17");
	}
	
	public static boolean isMC118(){
	    return Bukkit.getBukkitVersion().contains("1.18");
	}
	
	public static boolean isMC119(){
	    return Bukkit.getBukkitVersion().contains("1.19");
	}
	
	public static boolean isMC120(){
	    return Bukkit.getBukkitVersion().contains("1.20");
	}

	public static boolean isMC113OrNewer(){
	    if (isMC113() || isMC114() || isMC115() || isMC116() || isMC117() || isMC118() || isMC119() || isMC120()) 
	    	return true;
	    return false;
	}
	
}
