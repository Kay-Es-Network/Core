package it.kayes.core.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import it.kayes.core.functions.Messages;
import it.kayes.core.functions.MoneyFunctions;
import it.kayes.core.main.Main;
import it.kayes.core.main.utils;
import it.kayes.core.obj.Conto;
import it.kayes.core.obj.User;

public class MoneyCommands implements CommandExecutor, TabCompleter {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("money") || cmd.getName().equalsIgnoreCase("bal")) {
			if (args.length == 0) {
				if (!(sender instanceof Player))
					return utils.sendServerMsg(sender, "error.player-notconsole");
				
				String[] msg = Messages.getMessage("money.balance");
				for(String s : msg) {
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%MONEY%", Main.getUser(sender.getName()).getMoney()+""));
				}
				return true;
			} else {
				if (!sender.hasPermission("admin") && !sender.hasPermission("money")
						|| sender.hasPermission("money.limit") && !sender.hasPermission("*"))
					return utils.sendServerMsg(sender, "error.nopermission");
				
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);

				if (p==null)
					return utils.sendServerMsg(sender, "error.player-notexist");
				
				String[] msg = Messages.getMessage("money.balanceother");
				for(String s : msg) {
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%MONEY%", Main.getUser(p.getName()).getMoney()+"")
							.replaceAll("%VICTIM%", p.getName()));
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("eco")) {
			if (!sender.hasPermission("admin") && !sender.hasPermission("eco")
					|| sender.hasPermission("eco.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (args.length > 0 && args[0].equalsIgnoreCase("order")) {
				try {
					MoneyFunctions.reorder();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				String[] msg = Messages.getMessage("money.baltoporder");
				for(String s : msg) {
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()));
				}
				return true;
			}
			
			if (args.length < 2) 
				return utils.sendServerMsg(sender, "error.noargs");
			
			if (args[0].equalsIgnoreCase("give") || args[0].equalsIgnoreCase("add")) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				
				if (p==null) 
					return utils.sendServerMsg(sender, "error.player-notexist");
				
				if (args.length < 3) 
					return utils.sendServerMsg(sender, "error.noargs");
				
				double money = 0;
				try {
					money = Double.parseDouble(args[2]);
				} catch (NumberFormatException exc) {
					return utils.sendServerMsg(sender, "money.nonumber");
				}
				
				User u = Main.getUser(p.getName());
				u.setMoney(u.getMoney()+money);
				u.set();
				
				String[] msg = Messages.getMessage("money.give");
				for(String s : msg) {
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%MONEY%", money+""));
				}
				
				return true;
			} else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("take")) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				
				if (p==null) 
					return utils.sendServerMsg(sender, "error.player-notexist");
				
				if (args.length < 3) 
					return utils.sendServerMsg(sender, "error.noargs");
				
				double money = 0;
				try {
					money = Double.parseDouble(args[2]);
				} catch (NumberFormatException exc) {
					return utils.sendServerMsg(sender, "money.nonumber");
				}
				
				User u = Main.getUser(p.getName());
				u.setMoney(u.getMoney()-money);
				u.set();
				
				String[] msg = Messages.getMessage("money.remove");
				for(String s : msg) {
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%MONEY%", money+""));
				}
				
				return true;
			} else if (args[0].equalsIgnoreCase("set")) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				
				if (p==null) 
					return utils.sendServerMsg(sender, "error.player-notexist");
				
				if (args.length < 3) 
					return utils.sendServerMsg(sender, "error.noargs");
				
				double money = 0;
				try {
					money = Double.parseDouble(args[2]);
				} catch (NumberFormatException exc) {
					return utils.sendServerMsg(sender, "money.nonumber");
				}
				
				User u = Main.getUser(p.getName());
				u.setMoney(money);
				u.set();
				
				String[] msg = Messages.getMessage("money.set");
				for(String s : msg) {
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%MONEY%", money+""));
				}
				
				return true;
			} else if (args[0].equalsIgnoreCase("reset")) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				
				if (p==null) 
					return utils.sendServerMsg(sender, "error.player-notexist");
				
				User u = Main.getUser(p.getName());
				u.setMoney(0);
				u.set();
				
				String[] msg = Messages.getMessage("money.reset");
				for(String s : msg) {
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()));
				}
				
				return true;
			}
		} else if (cmd.getName().equalsIgnoreCase("pay")) {
			if (!sender.hasPermission("user") && !sender.hasPermission("pay")
					|| sender.hasPermission("pay.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");
			
			if (args.length<2)
				return utils.sendServerMsg(sender, "error.noargs");
			
			OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
			
			if (p==null) 
				return utils.sendServerMsg(sender, "error.player-notexist");
			
			double money = 0;
			try {
				money = Double.parseDouble(args[1]);
			} catch (NumberFormatException exc) {
				return utils.sendServerMsg(sender, "money.nonumber");
			}
			
			User s = Main.getUser(sender.getName()), v = Main.getUser(p.getName());
			if (money>s.getMoney())
				return utils.sendServerMsg(sender, "money.nomoney");
			
			s.setMoney(s.getMoney()-money);
			v.setMoney(v.getMoney()+money);
			s.set();v.set();
			
			String[] msg = Messages.getMessage("money.pay");
			for (String x : msg)
				utils.sendMsg(sender, x.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%VICTIM%", p.getName()).replaceAll("%MONEY%", money+""));
			
			Player vv = Bukkit.getPlayerExact(args[0]);
			if (vv!=null) {
				msg = Messages.getMessage("money.take");
				for (String x : msg)
					utils.sendMsg(vv, x.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%SENDER%", sender.getName()).replaceAll("%MONEY%", money+""));
			}
			
			return true;
			
		} else if (cmd.getName().equalsIgnoreCase("baltop")) {
			if (!sender.hasPermission("user") && !sender.hasPermission("baltop")
					|| sender.hasPermission("baltop.limit") && !sender.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");
			
			int page = 0;
			
			if (args.length>0) 
				try {
					page = Integer.parseInt(args[0]);
				} catch (NumberFormatException exc) {
					return utils.sendServerMsg(sender, "money.nonumber");
				}
			
			String[] msg = Messages.getMessage("money.baltop");
			String format = Messages.getMessage("money.baltopformat")[0];
			for (String s : msg) {
				if (s.contains("%BALTOP_VALUES%")) {
					Conto[] c = getBaltopPage(page);
					for (int i = 0; i<c.length; i++) {
						if (c[i]==null) continue;
						utils.sendMsg(sender, format.replaceAll("%POSITION%", page*c.length+i+"").replaceAll("%PLAYER%", c[i].getName())
								.replaceAll("%MONEY%", c[i].getMoney()+""));
					}
				}else{
					utils.sendMsg(sender, s.replaceAll("%PREFIX%", Messages.getPrefix()).replaceAll("%BALTOP_PAGE%", page+1+""));
				}
				
			}
			
			return true;
			
		}
		return true;
	}

	private static Conto[] getBaltopPage(int page) {
		Conto[] p = new Conto[10];
		
		for (int i = 0; i<p.length; i++)
			p[i] = MoneyFunctions.getBaltop(page*p.length+i);
		
		return p;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender,  Command cmd,  String label, String[] args) {
		ArrayList<String> res = new ArrayList<String>();
		if (cmd.getName().equalsIgnoreCase("money") || cmd.getName().equalsIgnoreCase("bal")) {
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
		}else if (cmd.getName().equalsIgnoreCase("eco")) {
			if (!sender.hasPermission("admin")) return null;
			if (args.length==1) {
				res.clear();
				res.add("give");
				res.add("take");
				res.add("set");
				res.add("reset");
			}else if (args.length == 2) {
				res.clear();
				if (args[1].length()>0) {
					for(Player p : Bukkit.getOnlinePlayers())
						if (p.getName().toUpperCase().startsWith(args[1].toUpperCase()))
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
