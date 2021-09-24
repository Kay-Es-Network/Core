package it.kayes.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.kayes.core.functions.Animation;
import it.kayes.core.functions.Messages;
import it.kayes.core.listeners.WarpsLoader;
import it.kayes.core.main.utils;
import it.kayes.core.obj.Warp;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class WarpCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warps")) {
		
			if (!sender.hasPermission("user")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			if (sender instanceof Player)
				sendWarpsMsg(WarpsLoader.getWarps().values().toArray(new Warp[WarpsLoader.getWarps().size()]), (Player)sender);
			else
				sendWarpsMsg(WarpsLoader.getWarps().values().toArray(new Warp[WarpsLoader.getWarps().size()]), sender);
			
			return true;
		}else if (cmd.getName().equalsIgnoreCase("warp")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("user")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			if (args.length == 0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			String[] msg;
			
			if (args.length == 1) {
				
				String warp = WarpsLoader.exactNameWarp(args[0]);
				
				if (warp==null) {
					msg = Messages.getMessage("warps.nowarp");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}
				
				if (!p.hasPermission("warps.*") && (!p.hasPermission("warps."+args[0]) || !p.hasPermission("essentials.warps."+args[0]))) {
					utils.sendServerMsg(sender, "error.nopermission");
					return true;
				}
				
				msg = Messages.getMessage("warps.tp");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%WARP%", warp));
				
				p.teleport(WarpsLoader.getWarp(warp).getLoc());
				
				Animation.createTeleportAnimation(p.getLocation());
				
				return true;
			} else if (args.length > 1) {
				
				if (!p.hasPermission("admin")) {
					utils.sendServerMsg(sender, "error.nopermission");
					return true;
				}
				
				Player v = Bukkit.getPlayerExact(args[1]);
				if (v==null) {
					utils.sendServerMsg(sender, "error.player-notonline");
					return true;
				}
				
				String warp = WarpsLoader.exactNameWarp(args[0]);
				
				if (warp==null) {
					msg = Messages.getMessage("warps.nowarp");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}
				
				msg = Messages.getMessage("warps.tp");
				for (String s : msg)
					utils.sendMsg(v, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%WARP%", warp));
				
				v.teleport(WarpsLoader.getWarp(warp).getLoc());
				
				Animation.createTeleportAnimation(v.getLocation());
				
				return true;
			}
			
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
			
			String[] msg;
			
			msg = Messages.getMessage("warps.set");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
			
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
			
			String[] msg;
			
			if (warp==null) {
				msg = Messages.getMessage("warps.nowarp");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			} else
				WarpsLoader.getWarps().remove(warp);
			
			msg = Messages.getMessage("warps.remove");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
			
			return true;
		}
		
		
		return true;
	}
	
	private static void sendWarpsMsg(Warp[] warps, Player p) {
		TextComponent str = new TextComponent();
		String format = Messages.getMessage("warps.warpsformat")[0];
		TextComponent sep = new TextComponent();
		sep.setText(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("warps.warpsseparator")[0]));
		
		String[] msg = Messages.getMessage("warps.warps");
		for (String s : msg) {
			if (s.contains("%WARPS%")) {
				s = s.replaceAll("%PREFIX%", Messages.getPrefix());
				
				String[] sx = s.split("%WARPS%");
				
				str.setText(ChatColor.translateAlternateColorCodes('&', sx[0]));
				
				for (int i = 0; i<warps.length; i++) {
					Warp h = warps[i];
					TextComponent home = new TextComponent();
					home.setText(ChatColor.translateAlternateColorCodes('&', format.replaceAll("%WARP%", h.getName())));
					home.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/warp "+h.getName()));
					if (i!=warps.length-1) {
						str.addExtra(home);
						str.addExtra(sep);
					}else
						str.addExtra(home);
				}
				
				if (sx.length>1)
					str.addExtra(ChatColor.translateAlternateColorCodes('&', sx[1]));
				
				p.spigot().sendMessage(str);
				
			}else utils.sendMsg(p, s);
		}
		
	}
	
	private static void sendWarpsMsg(Warp[] warps, CommandSender p) {
		String format = Messages.getMessage("warps.warpsformat")[0];
		String sep = Messages.getMessage("warps.warpsseparator")[0];
		
		String[] msg = Messages.getMessage("warps.warps");
		for (String s : msg) {
			if (s.contains("%WARPS%")) {
				s = s.replaceAll("%PREFIX%", Messages.getPrefix());
				
				String[] sx = s.split("%WARPS%");
				
				for (int i = 0; i<warps.length; i++) {
					Warp h = warps[i];
					if (i!=warps.length-1)
						sx[0] = sx[0] + format.replaceAll("%WARP%", h.getName()) + sep;
					else sx[0] += format.replaceAll("%WARP%", h.getName());
				}
				
				if (sx.length>1)
					sx[0] += sx[1];
				
				utils.sendMsg(p, sx[0]);
				
			}else utils.sendMsg(p, s);
		}
		
	}

}
