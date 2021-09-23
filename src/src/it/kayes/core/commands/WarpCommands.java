package it.kayes.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.kayes.core.listeners.WarpsLoader;
import it.kayes.core.main.utils;
import it.kayes.core.obj.Warp;

public class WarpCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warp")) {
			
			
		}else if (cmd.getName().equalsIgnoreCase("setwarp")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("admin")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			if (args.length == 0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			String warp = WarpsLoader.exactNameWarp(args[0]);
			
			if (warp==null)
				WarpsLoader.getWarps().put(args[0], new Warp(args[0],p.getLocation()));
			else
				WarpsLoader.getWarps().replace(warp, new Warp(warp,p.getLocation()));
			
			//MESSAGGIO
			
			return true;
		}else if (cmd.getName().equalsIgnoreCase("delwarp")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("admin")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			if (args.length == 0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			String warp = WarpsLoader.exactNameWarp(args[0]);
			
			if (warp==null) {
				//MESSAGGIO NON ESISTENTE WARP
				return true;
			} else
				WarpsLoader.getWarps().remove(warp);
			
			//MESSAGGIO
			
			return true;
		}
		
		
		return true;
	}

}
