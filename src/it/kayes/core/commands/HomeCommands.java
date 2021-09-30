package it.kayes.core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.kayes.core.functions.Animation;
import it.kayes.core.functions.HomeFunctions;
import it.kayes.core.functions.Messages;
import it.kayes.core.main.Main;
import it.kayes.core.main.utils;
import it.kayes.core.obj.Home;
import it.kayes.core.obj.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class HomeCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("homes")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("user") || p.hasPermission("homes.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			User u = Main.getUser(p.getName());
			String[] msg;
			Home[] homes = u.getHomes();

			if (homes.length == 0) {
				msg = Messages.getMessage("home.nohome");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}

			sendHomesMsg(homes, p);

			return true;
		} else if (cmd.getName().equalsIgnoreCase("home")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("user") || p.hasPermission("home.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			User u = Main.getUser(p.getName());
			String[] msg;
			Home[] homes = u.getHomes();

			if (homes.length == 0) {
				msg = Messages.getMessage("home.nohome");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}

			Home h;

			if (args.length == 0) {
				h = homes[0];
				p.teleport(h.getLoc());
			} else {
				h = HomeFunctions.getHomeByName(homes, args[0]);
				if (h == null) {
					msg = Messages.getMessage("home.notexisthome");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}
				p.teleport(h.getLoc());
			}

			Animation.createTeleportAnimation(h.getLoc());
			msg = Messages.getMessage("home.tp");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%HOME%", h.getName()));

			return true;
		} else if (cmd.getName().equalsIgnoreCase("delhome")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("user") || p.hasPermission("delhome.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			User u = Main.getUser(p.getName());
			String[] msg;
			Home[] homes = u.getHomes();

			if (homes.length == 0) {
				msg = Messages.getMessage("home.nohome");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				return true;
			}

			if (args.length == 0) {
				if (homes.length != 1) {
					msg = Messages.getMessage("home.noname");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}

				HomeFunctions.removeHome(u, (byte) 0);
			} else {
				byte id = HomeFunctions.getIdByName(homes, args[0]);

				if (id == -1) {
					msg = Messages.getMessage("home.notexisthome");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}

				HomeFunctions.removeHome(u, id);
			}

			msg = Messages.getMessage("home.remove");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));

			return true;
		} else if (cmd.getName().equalsIgnoreCase("sethome")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("user") || p.hasPermission("sethome.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			User u = Main.getUser(p.getName());
			String[] msg;
			Home[] homes = u.getHomes();

			Home h = null;

			if (args.length == 0) {
				if (homes.length > 1) {
					msg = Messages.getMessage("home.noname");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}

				if (homes.length == 1) {
					h = homes[0];
					h.setLoc(p.getLocation());
					homes[0] = h;
				} else if (h == null) {
					h = new Home("home", p.getLocation());
					homes = new Home[1];
					homes[0] = h;
				}

				u.setHomes(homes);
				u.set();
			} else {

				byte max = 1;

				for (byte i = 10; i > 0; i--)
					if (p.hasPermission("homes." + i)) {
						max = i;
						break;
					}

				if (homes.length == max && !p.hasPermission("homes.*")) {
					msg = Messages.getMessage("home.toohome");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));
					return true;
				}

				h = HomeFunctions.getHomeByName(homes, args[0]);

				if (h != null) {
					for (byte i = 0; i < homes.length; i++)
						if (homes[i].getName().equalsIgnoreCase(args[0]))
							homes[i] = h;

					u.setHomes(homes);
					u.set();
				} else
					HomeFunctions.addHome(u, new Home(args[0], p.getLocation()));
			}

			msg = Messages.getMessage("home.set");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));

			return true;
		}
		return true;
	}

	private static void sendHomesMsg(Home[] homes, Player p) {
		TextComponent str = new TextComponent();
		String format = Messages.getMessage("home.homesformat")[0];
		TextComponent sep = new TextComponent();
		sep.setText(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("home.homesseparator")[0]));

		String[] msg = Messages.getMessage("home.homes");
		for (String s : msg) {
			if (s.contains("%HOMES%")) {
				s = s.replaceAll("%PREFIX%", Messages.getPrefix());

				String[] sx = s.split("%HOMES%");

				str.setText(ChatColor.translateAlternateColorCodes('&', sx[0]));

				for (byte i = 0; i < homes.length; i++) {
					Home h = homes[i];
					TextComponent home = new TextComponent();
					home.setText(ChatColor.translateAlternateColorCodes('&', format.replaceAll("%HOME%", h.getName())));
					home.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/home " + h.getName()));
					if (i != homes.length - 1) {
						str.addExtra(home);
						str.addExtra(sep);
					} else
						str.addExtra(home);
				}

				if (sx.length > 1)
					str.addExtra(ChatColor.translateAlternateColorCodes('&', sx[1]));

				p.spigot().sendMessage(str);

			} else
				utils.sendMsg(p, s);
		}

	}

}
