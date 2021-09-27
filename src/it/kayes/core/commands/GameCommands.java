package it.kayes.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.kayes.core.functions.Messages;
import it.kayes.core.main.utils;

public class GameCommands implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gm") || cmd.getName().equalsIgnoreCase("gamemode")) {
			
			if (args.length == 0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode") || sender.hasPermission("gamemode.limit") && !sender.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			GameMode mode;
			
			try {
				mode = GameMode.valueOf(args[0].toUpperCase());
			}catch(IllegalArgumentException exc) {
				if (args[0].equals("0")) {
					mode = GameMode.SURVIVAL;
				}else if (args[0].equals("1")) {
					mode = GameMode.CREATIVE;
				}else if (args[0].equals("2")) {
					mode = GameMode.ADVENTURE;
				}else if (args[0].equals("3")) {
					mode = GameMode.SPECTATOR;
				}else {
					msg = Messages.getMessage("gamemode.select-valid");
					for (String s : msg)
						utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}
			}
			
			String smode = "";
			
			switch(mode) {
				case ADVENTURE:
					smode = Messages.getMessage("general.adventure")[0];
					break;
				case CREATIVE:
					smode = Messages.getMessage("general.creative")[0];
					break;
				case SPECTATOR:
					smode = Messages.getMessage("general.spector")[0];
					break;
				case SURVIVAL:
					smode = Messages.getMessage("general.survival")[0];
					break;	
			}
			
			if (args.length == 1) {
				if (!(sender instanceof Player)) {
					utils.sendServerMsg(sender, "error.player-notconsole");
					return true;
				}
				
				Player p = (Player) sender;
				
				p.setGameMode(mode);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", smode));
				return true;
			}else {
				Player p = Bukkit.getPlayerExact(args[1]);
				
				if (p == null) {
					utils.sendServerMsg(sender, "error.player-notonline");
					return true;
				}
				
				p.setGameMode(mode);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", smode));
			}
			return true;
			
		} else if (cmd.getName().equalsIgnoreCase("gmc")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode") || sender.hasPermission("gamemode.limit") && !sender.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					utils.sendServerMsg(sender, "error.player-notconsole");
					return true;
				}
				
				Player p = (Player) sender;
				
				p.setGameMode(GameMode.CREATIVE);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", Messages.getMessage("general.creative")[0]));
				return true;
			}else {
				Player p = Bukkit.getPlayerExact(args[0]);
				
				if (p == null) {
					utils.sendServerMsg(sender, "error.player-notonline");
					return true;
				}
				
				p.setGameMode(GameMode.CREATIVE);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", Messages.getMessage("general.creative")[0]));
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("gms")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode") || sender.hasPermission("gamemode.limit") && !sender.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					utils.sendServerMsg(sender, "error.player-notconsole");
					return true;
				}
				
				Player p = (Player) sender;
				
				p.setGameMode(GameMode.SURVIVAL);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", Messages.getMessage("general.survival")[0]));
				return true;
			}else {
				Player p = Bukkit.getPlayerExact(args[0]);
				
				if (p == null) {
					utils.sendServerMsg(sender, "error.player-notonline");
					return true;
				}
				
				p.setGameMode(GameMode.SURVIVAL);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", Messages.getMessage("general.survival")[0]));
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("gma")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode") || sender.hasPermission("gamemode.limit") && !sender.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					utils.sendServerMsg(sender, "error.player-notconsole");
					return true;
				}
				
				Player p = (Player) sender;
				
				p.setGameMode(GameMode.ADVENTURE);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", Messages.getMessage("general.adventure")[0]));
				return true;
			}else {
				Player p = Bukkit.getPlayerExact(args[0]);
				
				if (p == null) {
					utils.sendServerMsg(sender, "error.player-notonline");
					return true;
				}
				
				p.setGameMode(GameMode.ADVENTURE);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", Messages.getMessage("general.adventure")[0]));
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("gmsp")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode") || sender.hasPermission("gamemode.limit") && !sender.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			
			if (args.length == 0) {
				if (!(sender instanceof Player)) {
					utils.sendServerMsg(sender, "error.player-notconsole");
					return true;
				}
				
				Player p = (Player) sender;
				
				p.setGameMode(GameMode.SPECTATOR);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", Messages.getMessage("general.spector")[0]));
				return true;
			}else {
				Player p = Bukkit.getPlayerExact(args[0]);
				
				if (p == null) {
					utils.sendServerMsg(sender, "error.player-notonline");
					return true;
				}
				
				p.setGameMode(GameMode.SPECTATOR);
				
				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", Messages.getMessage("general.spector")[0]));
				return true;
			}
		}
		
		
		return true;
	}

}
