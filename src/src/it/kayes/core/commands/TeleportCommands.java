package it.kayes.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.kayes.core.functions.Animation;
import it.kayes.core.functions.Messages;
import it.kayes.core.functions.Teleport;
import it.kayes.core.main.utils;
import it.kayes.core.obj.TP;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class TeleportCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpa")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("user") || p.hasPermission("tpa.limit") && !p.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			if (args.length == 0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			String[] msg;
			
			if (p.getName().equalsIgnoreCase(args[0])) {
				msg = Messages.getMessage("teleport.request-self");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
				return true;
			}
			
			Player v = Bukkit.getPlayerExact(args[0]);
			
			if (v==null) {
				utils.sendServerMsg(sender, "error.player-notonline");
				return true;
			}
			
			if (Teleport.getTeleport(p.getName())!=null) {
				if (Teleport.getTeleport(p.getName()).getVictim().equalsIgnoreCase(v.getName())) {
					msg = Messages.getMessage("teleport.alredyrequest");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
					return true;
				}else {
					Player x = Bukkit.getPlayerExact(Teleport.getTeleport(p.getName()).getVictim());
					if (x!=null) {
						msg = Messages.getMessage("teleport.requestcancel-send");
						for (String s : msg)
							utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
						return true;
					}
					
					Teleport.getTeleports().remove(p.getName());
				}
			}
			
			Teleport.addRequest(p.getName(), v.getName(), false);
			
			msg = Messages.getMessage("teleport.request-send");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
			
			sendRequest(v, p, "teleport.request");
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("tpahere")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("user") || p.hasPermission("tpahere.limit") && !p.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			if (args.length == 0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}

			String[] msg;
			
			if (p.getName().equalsIgnoreCase(args[0])) {
				msg = Messages.getMessage("teleport.request-self");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
				return true;
			}
			
			Player v = Bukkit.getPlayerExact(args[0]);
			
			if (v==null) {
				utils.sendServerMsg(sender, "error.player-notonline");
				return true;
			}
			
			if (Teleport.getTeleport(p.getName())!=null) {
				if (Teleport.getTeleport(p.getName()).getVictim().equalsIgnoreCase(v.getName())) {
					msg = Messages.getMessage("teleport.alredyrequest");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
					return true;
				}else {
					Player x = Bukkit.getPlayerExact(Teleport.getTeleport(p.getName()).getVictim());
					if (x!=null) {
						msg = Messages.getMessage("teleport.requestcancel-send");
						for (String s : msg)
							utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
						return true;
					}
					
					Teleport.getTeleports().remove(p.getName());
				}
			}
			
			Teleport.addRequest(p.getName(), v.getName(), true);
			
			msg = Messages.getMessage("teleport.request-send");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
			
			sendRequest(v, p, "teleport.requesthere");
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("tpaccept") || cmd.getName().equalsIgnoreCase("tpyes")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("user") || p.hasPermission("tpaccept.limit") && !p.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			TP tp = Teleport.getPlayerTeleport(p.getName());
			
			String[] msg;
			
			if (tp==null) {
				msg = Messages.getMessage("teleport.notrequest");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}
			
			Player v = Bukkit.getPlayerExact(Teleport.getRequester(tp));
			
			if (v==null) {
				utils.sendServerMsg(sender, "error.player-notonline");
				return true;
			}
			
			if (utils.secureTp(v.getLocation())==-1) {
				msg = Messages.getMessage("teleport.notsecure");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}
			
			Teleport.cancelRequest(v.getName());
			
			if (tp.isTphere()) {
				v.teleport(p);
				Animation.createTeleportAnimation(v.getLocation());
			} else {
				p.teleport(v);
				Animation.createTeleportAnimation(p.getLocation());
			}
			
			msg = Messages.getMessage("teleport.requestaccept-send");
			for (String s : msg)
				utils.sendMsg(v, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%SENDER%", v.getName()));
			
			msg = Messages.getMessage("teleport.requestaccept-you");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%SENDER%", v.getName()));
		
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("tpdeny") || cmd.getName().equalsIgnoreCase("tpno")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("user") || p.hasPermission("tpdeny.limit") && !p.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			TP tp = Teleport.getPlayerTeleport(p.getName());
			
			String[] msg;
			
			if (tp==null) {
				msg = Messages.getMessage("teleport.notrequest");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}
			
			Player v = Bukkit.getPlayerExact(Teleport.getRequester(tp));
			
			Teleport.cancelRequest(v.getName());
			
			if (v!=null) {
				msg = Messages.getMessage("teleport.requestdeny-send");
				for (String s : msg)
					utils.sendMsg(v, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%SENDER%", v.getName()));
			}
			
			msg = Messages.getMessage("teleport.requestdeny-you");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%SENDER%", v.getName()));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("tpcancel")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("user") || p.hasPermission("tpcancel.limit") && !p.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			
			TP tp = Teleport.getTeleport(p.getName()); 
			
			if (tp == null) {
				msg = Messages.getMessage("teleport.notrequest");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}
			
			Player v = Bukkit.getPlayerExact(Teleport.getVictim(tp));
			
			Teleport.cancelRequest(p.getName());
			
			if (v!=null) {
				msg = Messages.getMessage("teleport.requestcancel-send");
				for (String s : msg)
					utils.sendMsg(v, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%SENDER%", v.getName()));
			}
			
			msg = Messages.getMessage("teleport.requestcancel-you");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%SENDER%", v.getName()));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("tp")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("mod") || p.hasPermission("tp.limit") && !p.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			
			if (args.length==0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			Player v = Bukkit.getPlayerExact(args[0]);
			
			if (v==null) {
				utils.sendServerMsg(sender, "error.player-notonline");
				return true;
			}
			
			p.teleport(v);
			
			msg = Messages.getMessage("teleport.tp");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()).replaceAll("%SENDER%", p.getName()));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("tphere")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("mod") || p.hasPermission("tphere.limit") && !p.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			
			if (args.length==0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			Player v = Bukkit.getPlayerExact(args[0]);
			
			if (v==null) {
				utils.sendServerMsg(sender, "error.player-notonline");
				return true;
			}
			
			v.teleport(p);
			
			msg = Messages.getMessage("teleport.tphere");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()).replaceAll("%SENDER%", p.getName()));
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("tpall")) {
			if (!(sender instanceof Player)) {
				utils.sendServerMsg(sender, "error.player-notconsole");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("mod") || p.hasPermission("tpall.limit") && !p.hasPermission("*")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			String[] msg;
			
			for (Player v : Bukkit.getOnlinePlayers())
				if (!v.getName().equalsIgnoreCase(p.getName()))
					v.teleport(p);
			
			msg = Messages.getMessage("teleport.tpall");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
			
			return true;
		}
		
		
		return true;
	}
	
	private static void sendRequest(Player p, Player v, String path) {
		String[] msg = Messages.getMessage(path);
		for (String s : msg)
			if (s.contains("%ACCEPT%")) {
				TextComponent accept = new TextComponent();
				accept.setText(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("general.accept")[0]));
				accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tpaccept"));
				
				TextComponent deny = new TextComponent();
				deny.setText(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("general.deny")[0]));
				deny.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/tpdeny"));
				
				s = s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", v.getName());
				String[] str = s.split("%ACCEPT%");
				TextComponent t = new TextComponent();
				t.setText(ChatColor.translateAlternateColorCodes('&', str[0]));
				t.addExtra(accept);
				str = str[1].split("%DENY%");
				if (str!=null)
					if (str.length>0) {
						t.addExtra(ChatColor.translateAlternateColorCodes('&', str[0]));
						t.addExtra(deny);
						t.addExtra(ChatColor.translateAlternateColorCodes('&', str[1]));
					}else {
						t.addExtra(deny);
					}
				
				p.spigot().sendMessage(t);
				
			} else utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
	}

	
	
}
