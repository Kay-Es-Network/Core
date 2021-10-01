package it.kayes.core.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import it.kayes.core.functions.Messages;
import it.kayes.core.main.Main;
import it.kayes.core.main.utils;
import it.kayes.core.obj.User;

public class GameCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gm") || cmd.getName().equalsIgnoreCase("gamemode")) {

			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");

			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode")
					|| sender.hasPermission("gamemode.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;
			GameMode mode;

			try {
				mode = GameMode.valueOf(args[0].toUpperCase());
			} catch (IllegalArgumentException exc) {
				if (args[0].equals("0")) {
					mode = GameMode.SURVIVAL;
				} else if (args[0].equals("1")) {
					mode = GameMode.CREATIVE;
				} else if (args[0].equals("2")) {
					mode = GameMode.ADVENTURE;
				} else if (args[0].equals("3")) {
					mode = GameMode.SPECTATOR;
				} else {
					msg = Messages.getMessage("gamemode.select-valid");
					for (String s : msg)
						utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}
			}

			String smode = "";

			switch (mode) {
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
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;

				p.setGameMode(mode);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", smode));
				return true;
			} else {
				Player p = Bukkit.getPlayerExact(args[1]);

				if (p == null)
					return utils.sendServerMsg(sender, "error.player-notonline");

				p.setGameMode(mode);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%", smode));
			}
			return true;

		} else if (cmd.getName().equalsIgnoreCase("gmc")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode")
					|| sender.hasPermission("gamemode.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;

			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;

				p.setGameMode(GameMode.CREATIVE);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%",
							Messages.getMessage("general.creative")[0]));
				return true;
			} else {
				Player p = Bukkit.getPlayerExact(args[0]);

				if (p == null)
					return utils.sendServerMsg(sender, "error.player-notonline");

				p.setGameMode(GameMode.CREATIVE);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%",
							Messages.getMessage("general.creative")[0]));
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("gms")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode")
					|| sender.hasPermission("gamemode.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;

			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;

				p.setGameMode(GameMode.SURVIVAL);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%",
							Messages.getMessage("general.survival")[0]));
				return true;
			} else {
				Player p = Bukkit.getPlayerExact(args[0]);

				if (p == null)
					return utils.sendServerMsg(sender, "error.player-notonline");

				p.setGameMode(GameMode.SURVIVAL);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%",
							Messages.getMessage("general.survival")[0]));
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("gma")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode")
					|| sender.hasPermission("gamemode.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;

			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;

				p.setGameMode(GameMode.ADVENTURE);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%",
							Messages.getMessage("general.adventure")[0]));
				return true;
			} else {
				Player p = Bukkit.getPlayerExact(args[0]);

				if (p == null)
					return utils.sendServerMsg(sender, "error.player-notonline");

				p.setGameMode(GameMode.ADVENTURE);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%",
							Messages.getMessage("general.adventure")[0]));
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("gmsp")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("gamemode")
					|| sender.hasPermission("gamemode.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;

			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;

				p.setGameMode(GameMode.SPECTATOR);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%",
							Messages.getMessage("general.spector")[0]));
				return true;
			} else {
				Player p = Bukkit.getPlayerExact(args[0]);

				if (p == null)
					return utils.sendServerMsg(sender, "error.player-notonline");

				p.setGameMode(GameMode.SPECTATOR);

				msg = Messages.getMessage("gamemode.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%GAMEMODE%",
							Messages.getMessage("general.spector")[0]));
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("speed")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("speed")
					|| sender.hasPermission("speed.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");

			String[] msg;

			if (args.length == 1) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;

				float speed;

				try {
					speed = Float.valueOf(args[0]);
					if (speed < 0)
						speed = 0;
					else if (speed > 10)
						speed = 10;
				} catch (IllegalArgumentException exc) {
					msg = Messages.getMessage("speed.invalid");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}

				msg = Messages.getMessage("speed.change");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SPEED%", speed + ""));

				p.setWalkSpeed((float) (Math.pow(speed, 2) * -0.01 + 0.2 * speed));
				p.setFlySpeed(speed / 10);

				User u = Main.getUser(p.getName());
				u.setSpeed(speed);
				u.set();
				return true;
			} else {
				User u = Main.getUser(Bukkit.getOfflinePlayer(args[0]).getName());

				if (u == null)
					return utils.sendServerMsg(sender, "error.player-notexist");

				float speed;

				try {
					speed = Float.valueOf(args[0]);
					if (speed < 0)
						speed = 0;
					else if (speed > 10)
						speed = 10;
				} catch (IllegalArgumentException exc) {
					msg = Messages.getMessage("speed.invalid");
					for (String s : msg)
						utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}

				msg = Messages.getMessage("speed.other");
				for (String s : msg)
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix())
							.replaceAll("%SPEED%", speed + "").replaceAll("%VICTIM%", u.getName()));

				Player p = Bukkit.getPlayerExact(u.getName());

				if (p != null) {
					p.setWalkSpeed((float) (Math.pow(speed, 2) * -0.01 + 0.2 * speed));
					p.setFlySpeed(speed / 10);
					msg = Messages.getMessage("speed.change");
					for (String s : msg)
						utils.sendMsg(Bukkit.getPlayer(u.getName()),
								s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SPEED%", speed + ""));
				}

				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("fly")) {
			if (!sender.hasPermission("vip") && !sender.hasPermission("fly")
					|| sender.hasPermission("fly.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;
				User u = Main.getUser(p.getName());
				
				u.setFly(!u.isFly());
				p.setAllowFlight(!p.getAllowFlight());
				
				if (u.isFly()) return utils.sendServerMsg(sender, "fly.enabled");
				else return utils.sendServerMsg(sender, "fly.disabled");
			} else {
				User u = Main.getUser(Bukkit.getOfflinePlayer(args[0]).getName());
				
				if (u==null) return utils.sendServerMsg(sender, "error.player-notexist");
				
				u.setFly(!u.isFly());
				
				if (u.isFly()) utils.sendServerMsg(sender, "fly.enabled");
				else utils.sendServerMsg(sender, "fly.disabled");
				
				Player p = Bukkit.getPlayer(args[0]);
				
				if (p!=null) {
					if (u.isFly()) utils.sendServerMsg(p, "fly.enabled");
					else utils.sendServerMsg(p, "fly.disabled");
					
					p.setAllowFlight(u.isFly());
				}
				
				return true;
			}
			
		} else if (cmd.getName().equalsIgnoreCase("god")) {
			if (!sender.hasPermission("vip") && !sender.hasPermission("god")
					|| sender.hasPermission("god.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;
				User u = Main.getUser(p.getName());
				
				u.setGod(!u.isGod());
				p.setInvulnerable(!p.isInvulnerable());
				
				if (u.isGod()) return utils.sendServerMsg(sender, "god.enabled");
				else return utils.sendServerMsg(sender, "god.disabled");
			} else {
				User u = Main.getUser(Bukkit.getOfflinePlayer(args[0]).getName());
				
				if (u==null) return utils.sendServerMsg(sender, "error.player-notexist");
				
				u.setFly(!u.isGod());
				
				if (u.isGod()) utils.sendServerMsg(sender, "god.enabled");
				else utils.sendServerMsg(sender, "god.disabled");
				
				Player p = Bukkit.getPlayer(args[0]);
				
				if (p!=null) {
					if (u.isGod()) utils.sendServerMsg(p, "god.enabled");
					else utils.sendServerMsg(p, "god.disabled");
					
					p.setInvulnerable(u.isGod());
				}
				
				return true;
			}
			
		} else if (cmd.getName().equalsIgnoreCase("heal")) {
			if (!sender.hasPermission("vip") && !sender.hasPermission("heal")
					|| sender.hasPermission("heal.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;
				
				p.setHealth(p.getMaxHealth());
			} else {
				Player p = Bukkit.getPlayerExact(args[0]);
				
				if (p==null) return utils.sendServerMsg(sender, "error.player-notonline");
				
				p.setHealth(p.getMaxHealth());
			}
			
			utils.sendServerMsg(sender, "heal.set");
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("feed")) {
			if (!sender.hasPermission("vip") && !sender.hasPermission("feed")
					|| sender.hasPermission("feed.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");

				Player p = (Player) sender;
				
				p.setFoodLevel(20);
			} else {
				Player p = Bukkit.getPlayerExact(args[0]);
				
				if (p==null) return utils.sendServerMsg(sender, "error.player-notonline");
				
				p.setFoodLevel(20);
			}
			
			utils.sendServerMsg(sender, "feed.set");
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("suicide")) {
			if (!sender.hasPermission("user") && !sender.hasPermission("suicide")
					|| sender.hasPermission("suicide.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;
			
			p.setHealth(0);

			utils.sendServerMsg(sender, "suicide.set");
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("kill")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("kill")
					|| sender.hasPermission("kill.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");
			
			Player p = Bukkit.getPlayer(args[0]);
				
			if (p==null) 
				return utils.sendServerMsg(sender, "error.player-notonline");
			
			p.setHealth(0);
			
			String[] msg = Messages.getMessage("kill.set");
			for (String s : msg)
				utils.sendMsg(sender, s.replaceAll("%VICTIM%", p.getName()));
			
			return utils.sendServerMsg(p, "kill.take");
		} else if (cmd.getName().equalsIgnoreCase("disposal")) {
			if (!sender.hasPermission("user") && !sender.hasPermission("disposal")
					|| sender.hasPermission("disposal.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;
			
			Inventory inv = Bukkit.createInventory(null, 27);
			
			p.openInventory(inv);

			return true;
		} else if (cmd.getName().equalsIgnoreCase("hat")) {
			if (!sender.hasPermission("user") && !sender.hasPermission("hat")
					|| sender.hasPermission("hat.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;
			
			ItemStack item = p.getInventory().getItemInHand();
			
			if (item==null)
				return utils.sendServerMsg(sender, "error.invalid-item");
			
			p.getInventory().setHelmet(item);
			
			p.getInventory().setItemInHand(null);

			return true;
		} else if (cmd.getName().equalsIgnoreCase("seen")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("seen")
					|| sender.hasPermission("seen.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
		
			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");
			
			if (Main.getUser(Bukkit.getOfflinePlayer(args[0]).getName())==null)
				return utils.sendServerMsg(sender, "error.player-notexist");
			
			Player p = Bukkit.getPlayerExact(args[0]);
			
			if (p!=null)
				return utils.sendServerMsg(sender, "seen.online");
			else {
				Date d = new Date();
				d.setTime(Bukkit.getOfflinePlayer(args[0]).getLastPlayed());
				
				String[] msg = Messages.getMessage("seen.offline");
				for (String s : msg)
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%DAY%", d.getDate()+"").replaceAll("%MONTH%", d.getMonth()+1+"").replaceAll("%YEAR%", d.getYear()+1900+"")
							.replaceAll("%HOUR%", d.getHours()<10 ? "0"+d.getHours() : d.getHours()+"")
							.replaceAll("%MINUTE%", d.getMinutes()<10 ? "0"+d.getMinutes() : d.getMinutes()+"")
							.replaceAll("%SECOND%", d.getSeconds()<10 ? "0"+d.getSeconds() : d.getSeconds()+""));
			}

			return true;
		} else if (cmd.getName().equalsIgnoreCase("skull")) {
			if (!sender.hasPermission("vip") && !sender.hasPermission("skull")
					|| sender.hasPermission("skull.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");
			
			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");

			/*Player p = (Player) sender;
			
			ItemStack item = utils.getPlayerhead(args[0]);
			
			if (args.length == 2) {
				int amount = 1;
				try {
					amount = Integer.parseInt(args[1]);
				} catch (NumberFormatException exc) {}
				if (amount > 64 ) amount = 64;
				else if (amount < 1) amount = 1;
				item.setAmount(amount);
			}
			
			p.getInventory().addItem(item);
			p.updateInventory();*/

			return true;
		} else if (cmd.getName().equalsIgnoreCase("near")) {
			if (!sender.hasPermission("vip") && !sender.hasPermission("near")
					|| sender.hasPermission("near.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;
			Location loc = p.getLocation();
			List<Entity> en = p.getNearbyEntities(25, 25, 25);
			
			ArrayList<String> players = new ArrayList<String>();
			
			String format = Messages.getMessage("near.format")[0];
			String separator = Messages.getMessage("near.separator")[0];
			
			System.out.println(en.size());
			
			for (Entity e : en)
				if (e instanceof Player) {
					int distance = (int) Math.sqrt(Math.pow((loc.getBlockX()-e.getLocation().getBlockX()), 2)+Math.pow((loc.getBlockZ()-e.getLocation().getBlockZ()), 2)) + Math.abs(loc.getBlockY()-e.getLocation().getBlockY());
					players.add(format.replaceAll("%VICTIM%", e.getName()).replaceAll("%DISTANCE%", distance+""));
				}
			
			if (players.size()==0) 
				return utils.sendServerMsg(sender, "near.none");
			
			String[] msg = Messages.getMessage("near.send");
			for (String s : msg) {
				if (s.contains("%PLAYERS%")) {
					String[] pp = players.toArray(new String[players.size()]);
					String x = "";
					for (int i = 0; i<pp.length; i++)
						if (i==pp.length-1)
							x += pp[i];
						else x += pp[i] + separator;
					s = s.replaceAll("%PLAYERS%", x);
				}
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
			}

			return true;
		} else if (cmd.getName().equalsIgnoreCase("broadcast")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("broadcast")
					|| sender.hasPermission("broadcast.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");
			
			String message = "";
			String[] f = Messages.getMessage("broadcast.format");
			
			for (String s : args) message += s + " ";
			
			for (Player p : Bukkit.getOnlinePlayers())
				for (String s : f)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%MESSAGE%", message));
			
			return true;
		}

		return true;
	} 

}
