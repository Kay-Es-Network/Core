package it.kayes.core.commands;

import it.kayes.core.functions.Animation;
import it.kayes.core.functions.Messages;
import it.kayes.core.functions.Teleport;
import it.kayes.core.main.Main;
import it.kayes.core.main.utils;
import it.kayes.core.obj.TP;
import it.kayes.core.obj.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportCommands implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpa")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("user") || p.hasPermission("tpa.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");

			String[] msg;

			if (p.getName().equalsIgnoreCase(args[0])) {
				msg = Messages.getMessage("teleport.request-self");
				for (String s : msg)
					utils.sendMsg(p,
							s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
				return true;
			}

			Player v = Bukkit.getPlayerExact(args[0]);

			if (v == null)
				return utils.sendServerMsg(sender, "error.player-notonline");

			if (Teleport.getTeleport(p.getName()) != null) {
				if (Teleport.getTeleport(p.getName()).getVictim().equalsIgnoreCase(v.getName())) {
					msg = Messages.getMessage("teleport.alredyrequest");
					for (String s : msg)
						utils.sendMsg(p,
								s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
					return true;
				} else {
					Player x = Bukkit.getPlayerExact(Teleport.getTeleport(p.getName()).getVictim());
					if (x != null) {
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
		}
		else if (cmd.getName().equalsIgnoreCase("tpahere")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("user") || p.hasPermission("tpahere.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");

			String[] msg;

			if (p.getName().equalsIgnoreCase(args[0])) {
				msg = Messages.getMessage("teleport.request-self");
				for (String s : msg)
					utils.sendMsg(p,
							s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
				return true;
			}

			Player v = Bukkit.getPlayerExact(args[0]);

			if (v == null)
				return utils.sendServerMsg(sender, "error.player-notonline");

			if (Teleport.getTeleport(p.getName()) != null) {
				if (Teleport.getTeleport(p.getName()).getVictim().equalsIgnoreCase(v.getName())) {
					msg = Messages.getMessage("teleport.alredyrequest");
					for (String s : msg)
						utils.sendMsg(p,
								s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));
					return true;
				} else {
					Player x = Bukkit.getPlayerExact(Teleport.getTeleport(p.getName()).getVictim());
					if (x != null) {
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
		}
		else if (cmd.getName().equalsIgnoreCase("tpaccept") || cmd.getName().equalsIgnoreCase("tpyes")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender; //Vittima

			if (!p.hasPermission("user") || p.hasPermission("tpaccept.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			TP tp = Teleport.getPlayerTeleport(p.getName());

			String[] msg;

			if (tp == null) {
				msg = Messages.getMessage("teleport.notrequest");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}

			Player v = Bukkit.getPlayerExact(Teleport.getRequester(tp)); //Richiede

			if (v == null)
				return utils.sendServerMsg(sender, "error.player-notonline");

			if (utils.secureTp(v.getLocation()) == -1) {
				msg = Messages.getMessage("teleport.notsecure");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}

			if (tp.isTphere()) {
				User u = Main.getUser(p.getName());
				u.setLastLocation(p.getLocation());
				u.set();
				p.teleport(v);
				Animation.createTeleportAnimation(p.getLocation());
			} else {
				User u = Main.getUser(v.getName());
				u.setLastLocation(v.getLocation());
				u.set();
				v.teleport(p);
				Animation.createTeleportAnimation(p.getLocation());
			}

			Teleport.cancelRequest(v.getName());

			msg = Messages.getMessage("teleport.requestaccept-send");
			for (String s : msg)
				utils.sendMsg(v, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName())
						.replaceAll("%SENDER%", v.getName()));

			msg = Messages.getMessage("teleport.requestaccept-you");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName())
						.replaceAll("%SENDER%", v.getName()));

			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("tpdeny") || cmd.getName().equalsIgnoreCase("tpno")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("user") || p.hasPermission("tpdeny.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			TP tp = Teleport.getPlayerTeleport(p.getName());

			String[] msg;

			if (tp == null) {
				msg = Messages.getMessage("teleport.notrequest");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}

			Player v = Bukkit.getPlayerExact(Teleport.getRequester(tp));

			Teleport.cancelRequest(v.getName());

			if (v != null) {
				msg = Messages.getMessage("teleport.requestdeny-send");
				for (String s : msg)
					utils.sendMsg(v, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName())
							.replaceAll("%SENDER%", v.getName()));
			}

			msg = Messages.getMessage("teleport.requestdeny-you");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName())
						.replaceAll("%SENDER%", v.getName()));

			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("tpcancel") || cmd.getName().equalsIgnoreCase("tpacancel")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("user") || p.hasPermission("tpcancel.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

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

			if (v != null) {
				msg = Messages.getMessage("teleport.requestcancel-send");
				for (String s : msg)
					utils.sendMsg(v, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName())
							.replaceAll("%SENDER%", v.getName()));
			}

			msg = Messages.getMessage("teleport.requestcancel-you");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName())
						.replaceAll("%SENDER%", v.getName()));

			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("tp")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("mod") || p.hasPermission("tp.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;

			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");

			Player v = Bukkit.getPlayerExact(args[0]);

			if (v == null)
				return utils.sendServerMsg(sender, "error.player-notonline");
			
			User u = Main.getUser(p.getName());
			u.setLastLocation(p.getLocation());
			u.set();

			p.teleport(v);

			msg = Messages.getMessage("teleport.tp");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName())
						.replaceAll("%SENDER%", p.getName()));

			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("tphere")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("mod") || p.hasPermission("tphere.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;

			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");

			Player v = Bukkit.getPlayerExact(args[0]);

			if (v == null)
				return utils.sendServerMsg(sender, "error.player-notonline");

			User u = Main.getUser(v.getName());
			u.setLastLocation(v.getLocation());
			u.set();
			
			v.teleport(p);

			msg = Messages.getMessage("teleport.tphere");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName())
						.replaceAll("%SENDER%", p.getName()));

			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("tpall")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("mod") || p.hasPermission("tpall.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;

			for (Player v : Bukkit.getOnlinePlayers())
				if (!v.getName().equalsIgnoreCase(p.getName())) {
					User u = Main.getUser(v.getName());
					u.setLastLocation(v.getLocation());
					u.set();
					v.teleport(p);
				}

			msg = Messages.getMessage("teleport.tpall");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", p.getName()));

			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("jump")) {
			if (!sender.hasPermission("vip") && !sender.hasPermission("jump")
					|| sender.hasPermission("jump.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");
	
			Player p = (Player) sender;

			
			if (utils.getTargetBlock(p, 30) != null) {
				Location loc = utils.getTargetBlock(p, 30).getLocation();
				final World world = loc.getWorld();
			    final double x = loc.getX();
				final double y = loc.getBlockY();
				final double z = loc.getZ();
				
				for (int i = (int) y; i < 255 ;i++)		
					if ((new Location(world,x,i,z)).getBlock().getType().equals(Material.AIR) && (new Location(world,x,i,z)).getBlock().getType().equals(Material.AIR)) {
						p.teleport(new Location(world,x,i,z));
						Animation.createTeleportAnimation(new Location(world,x,i,z));
						break;
					}
				
				return utils.sendServerMsg(sender, "jump.set");
			}
			
			return utils.sendServerMsg(sender, "jump.null");
		}
		else if (cmd.getName().equalsIgnoreCase("top")) {
			if (!sender.hasPermission("vip") && !sender.hasPermission("top")
					|| sender.hasPermission("top.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");
	
			Player p = (Player) sender;
			
			Location loc = p.getLocation();
			final World world = loc.getWorld();
			final double x = loc.getBlockX();
			final double z = loc.getBlockZ();
			
			for (int i = 255; i > 0 ; i--)		
				if (!(new Location(world,x,i,z)).getBlock().getType().equals(Material.AIR)) {
					p.teleport(new Location(world,x,i+1,z));
					Animation.createTeleportAnimation(new Location(world,x,i+1,z));
					break;
				}
			
			return utils.sendServerMsg(sender, "top.set");
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
				if (str != null)
					if (str.length > 0) {
						t.addExtra(ChatColor.translateAlternateColorCodes('&', str[0]));
						t.addExtra(deny);
						t.addExtra(ChatColor.translateAlternateColorCodes('&', str[1]));
					} else {
						t.addExtra(deny);
					}

				p.spigot().sendMessage(t);

			} else
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
	}
	

	@Override
	public List<String> onTabComplete(CommandSender sender,  Command cmd,  String label, String[] args) {
		ArrayList<String> res = new ArrayList<String>();
		if (cmd.getName().equalsIgnoreCase("tpa") || cmd.getName().equalsIgnoreCase("tpahere")) {
			if (args.length == 1) {
				res.clear();
				if (args[0].length()>0) {
					for(Player p : Bukkit.getOnlinePlayers())
						if (p.getName().toUpperCase().startsWith(args[0].toUpperCase()))
							res.add(p.getName());
				}else {
					for(Player p : Bukkit.getOnlinePlayers())
						res.add(p.getName());
				}
				return res;
			}
		} else if (cmd.getName().equalsIgnoreCase("tp") || cmd.getName().equalsIgnoreCase("tphere")) {
			if (!sender.hasPermission("admin")) return null;
			if (args.length == 1) {
				res.clear();
				if (args[0].length()>0) {
					for(Player p : Bukkit.getOnlinePlayers())
						if (p.getName().toUpperCase().startsWith(args[0].toUpperCase()))
							res.add(p.getName());
				}else {
					for(Player p : Bukkit.getOnlinePlayers())
						res.add(p.getName());
				}
				return res;
			}
		}
		return null;
	}

}
