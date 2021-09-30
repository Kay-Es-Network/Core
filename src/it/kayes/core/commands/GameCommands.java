package it.kayes.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			if (!sender.hasPermission("admin") && !sender.hasPermission("speed")
					|| sender.hasPermission("speed.limit") && !sender.hasPermission("*"))
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
			
		}

		return true;
	}

}
