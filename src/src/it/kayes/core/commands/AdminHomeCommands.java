package it.kayes.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.kayes.core.functions.HomeFunctions;
import it.kayes.core.functions.Messages;
import it.kayes.core.listeners.UserLoader;
import it.kayes.core.main.utils;
import it.kayes.core.obj.Home;
import it.kayes.core.obj.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class AdminHomeCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("adminhomes") || cmd.getName().equalsIgnoreCase("ahomes")) {
			
			if (!sender.hasPermission("admin")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			if (args.length == 0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			OfflinePlayer v = Bukkit.getOfflinePlayer(args[0]);
			User u = UserLoader.getUser(v.getName());

			if (u==null) {
				utils.sendServerMsg(sender, "error.player-notexist");
				return true;
			}
			
			String[] msg;
			Home[] homes = u.getHomes();
			
			if (homes.length==0) {
				msg = Messages.getMessage("adminhome.nohome");
				for (String s : msg)
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
				return true;
			}
			
			sendHomesMsg(homes, sender, v);
			
			return true;
		} else if (cmd.getName().equalsIgnoreCase("adminhome") || cmd.getName().equalsIgnoreCase("ahome")) {
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
			
			OfflinePlayer v = Bukkit.getOfflinePlayer(args[0]);
			User u = UserLoader.getUser(v.getName());

			if (u==null) {
				utils.sendServerMsg(sender, "error.player-notexist");
				return true;
			}
			
			String[] msg;
			Home[] homes = u.getHomes();
			
			if (homes.length==0) {
				msg = Messages.getMessage("adminhome.nohome");
				for (String s : msg)
					utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
				return true;
			}
			
			Home h;
			
			if (args.length==1) {
				h = homes[0];
				p.teleport(h.getLoc());
			} else {
				h = HomeFunctions.getHomeByName(homes, args[1]);
				if (h==null) {
					msg = Messages.getMessage("adminhome.notexisthome");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
					return true;
				}
				p.teleport(h.getLoc());
			}
			
			msg = Messages.getMessage("adminhome.tp");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%HOME%", h.getName()).replaceAll("%VICTIM%", v.getName()));
			
			return true;
		}else if (cmd.getName().equalsIgnoreCase("admindelhome") || cmd.getName().equalsIgnoreCase("adelhome")) {
			
			if (!sender.hasPermission("admin")) {
				utils.sendServerMsg(sender, "error.nopermission");
				return true;
			}
			
			if (args.length == 0) {
				utils.sendServerMsg(sender, "error.noargs");
				return true;
			}
			
			OfflinePlayer v = Bukkit.getOfflinePlayer(args[0]);
			User u = UserLoader.getUser(v.getName());

			if (u==null) {
				utils.sendServerMsg(sender, "error.player-notexist");
				return true;
			}
			
			String[] msg;
			Home[] homes = u.getHomes();
			
			if (homes.length==0) {
				msg = Messages.getMessage("adminhome.nohome");
				for (String s : msg)
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
				return true;
			}
			
			if (args.length==1) {
				if (homes.length!=1) {
					msg = Messages.getMessage("adminhome.noname");
					for (String s : msg)
						utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
					return true;
				}
				
				HomeFunctions.removeHome(u, (byte)0);
			} else {
				byte id = HomeFunctions.getIdByName(homes, args[1]);
				
				if (id==-1) {
					msg = Messages.getMessage("adminhome.notexisthome");
					for (String s : msg)
						utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
					return true;
				}
	
				HomeFunctions.removeHome(u, id);
			}
			
			msg = Messages.getMessage("adminhome.remove");
			for (String s : msg)
				utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
			
			return true;
		}else if (cmd.getName().equalsIgnoreCase("adminsethome") || cmd.getName().equalsIgnoreCase("asethome")) {
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
			
			OfflinePlayer v = Bukkit.getOfflinePlayer(args[0]);
			User u = UserLoader.getUser(v.getName());
			
			if (u==null) {
				utils.sendServerMsg(sender, "error.player-notexist");
				return true;
			}
			
			String[] msg;
			Home[] homes = u.getHomes();
			
			Home h = null;
			
			if (args.length==1) {
				if (homes.length>1) {
					msg = Messages.getMessage("adminhome.noname");
					for (String s : msg)
						utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
					return true;
				}
				
				if (homes.length==1) {
					h = homes[0];
					h.setLoc(p.getLocation());
					homes[0] = h;
				}else if (h==null) {
					h = new Home("home",p.getLocation());
					homes = new Home[1];
					homes[0] = h;
				}
				
				u.setHomes(homes);
				UserLoader.setUser(u);
			} else {
				
				h = HomeFunctions.getHomeByName(homes, args[1]);
				
				if (h!=null) {
					for (byte i = 0; i<homes.length; i++) 
						if (homes[i].getName().equalsIgnoreCase(args[1]))
							homes[i] = h;
					
					u.setHomes(homes);
					UserLoader.setUser(u);
				} else
					HomeFunctions.addHome(u, new Home(args[1], p.getLocation()));
			}
			
			msg = Messages.getMessage("adminhome.set");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName()));
			
			return true;
		}
		
		return true;
	}
	
	private static void sendHomesMsg(Home[] homes, CommandSender p, OfflinePlayer v) {
		TextComponent str = new TextComponent();
		String format = Messages.getMessage("adminhome.homesformat")[0];
		String spe = Messages.getMessage("adminhome.homesseparator")[0];
		TextComponent sep = new TextComponent();
		sep.setText(ChatColor.translateAlternateColorCodes('&', spe));
		
		String[] msg = Messages.getMessage("adminhome.homes");
		for (String s : msg) {
			if (s.contains("%HOMES%")) {
				s = s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", v.getName());
				String[] sx = s.split("%HOMES%");
				
				if (p instanceof Player) {
					str.setText(ChatColor.translateAlternateColorCodes('&', sx[0]));
					
					for (byte i = 0; i<homes.length; i++) {
						Home h = homes[i];
						TextComponent home = new TextComponent();
						home.setText(ChatColor.translateAlternateColorCodes('&', format.replaceAll("%HOME%", h.getName())));
						home.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/adminhome "+v.getName()+" "+h.getName()));
						if (i!=homes.length-1) {
							str.addExtra(home);
							str.addExtra(sep);
						}else
							str.addExtra(home);
					}
					
					if (sx.length>1)
						str.addExtra(ChatColor.translateAlternateColorCodes('&', sx[1]));
					
					p.spigot().sendMessage(str);
				} else {
					for (byte i = 0; i<homes.length; i++) {
						Home h = homes[i];
						if (i!=homes.length-1)
							sx[0] = sx[0] + format.replaceAll("%HOME%", h.getName()) + spe;
						else sx[0] += format.replaceAll("%HOME%", h.getName());
					}
					
					if (sx.length>1)
						sx[0] += sx[1];
					
					utils.sendMsg(p, sx[0]);
				}
			}else utils.sendMsg(p, s);
		}
		
	}

}
