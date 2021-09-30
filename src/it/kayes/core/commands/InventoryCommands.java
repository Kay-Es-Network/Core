package it.kayes.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import it.kayes.core.functions.InventoryModifyEvent;
import it.kayes.core.functions.Messages;
import it.kayes.core.main.Main;
import it.kayes.core.main.utils;
import it.kayes.core.obj.User;

public class InventoryCommands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("repair")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("vip") && !p.hasPermission("repair")
					|| p.hasPermission("repair.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;

			ItemStack item = p.getInventory().getItemInHand();

			if (item == null) {
				utils.sendServerMsg(sender, "error.invalid-item");
				return true;
			}

			item.setDurability(new ItemStack(item.getType()).getDurability());
			p.getInventory().setItemInHand(item);

			msg = Messages.getMessage("repair.do");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));

			return true;
		} else if (cmd.getName().equalsIgnoreCase("repairall")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("vip") && !p.hasPermission("repair")
					|| p.hasPermission("repairall.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			String[] msg;
			ItemStack item;

			for (byte i = 0; i < p.getInventory().getSize(); i++) {
				item = p.getInventory().getItem(i);
				item.setDurability(new ItemStack(item.getType()).getDurability());
				p.getInventory().setItem(i, item);
				;
			}

			msg = Messages.getMessage("repair.doall");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));

			return true;
		} else if (cmd.getName().equalsIgnoreCase("invsee")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("admin") && !p.hasPermission("invsee")
					|| p.hasPermission("invsee.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			if (args.length == 0)
				return utils.sendServerMsg(sender, "error.noargs");

			if (p.getName().equalsIgnoreCase(args[0])) {
				utils.sendServerMsg(sender, "error.player-notsender");
				return true;
			}

			User u = Main.getUser(Bukkit.getOfflinePlayer(args[0]).getName());

			if (u == null)
				return utils.sendServerMsg(sender, "error.player-notexist");

			if (Bukkit.getPlayerExact(u.getName()) == null)
				p.openInventory(u.getInv());
			else
				p.openInventory(Bukkit.getPlayer(u.getName()).getInventory());

			InventoryModifyEvent.getInvsee().put(p.getName(), u.getName());

			return true;
		} else if (cmd.getName().equalsIgnoreCase("ec") || cmd.getName().equalsIgnoreCase("enderchest")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (args.length == 0) {
				if (!p.hasPermission("vip") && !p.hasPermission("enderchest")
						|| p.hasPermission("enderchest.limit") && !p.hasPermission("*"))
					return utils.sendServerMsg(sender, "error.nopermission");

				p.openInventory(p.getEnderChest());
				return true;
			}

			if (p.getName().equalsIgnoreCase(args[0])) {
				utils.sendServerMsg(sender, "error.player-notsender");
				return true;
			}

			User u = Main.getUser(Bukkit.getOfflinePlayer(args[0]).getName());

			if (u == null)
				return utils.sendServerMsg(sender, "error.player-notexist");

			if (Bukkit.getPlayerExact(u.getName()) == null)
				p.openInventory(u.getEnderchest());
			else
				p.openInventory(Bukkit.getPlayer(u.getName()).getEnderChest());

			InventoryModifyEvent.getEnderchest().put(p.getName(), u.getName());

			return true;
		} else if (cmd.getName().equalsIgnoreCase("clear")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("admin") && !p.hasPermission("clear")
					|| p.hasPermission("clear.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			if (args.length == 0) {
				for (byte i = 0; i < p.getInventory().getSize(); i++)
					p.getInventory().setItem(i, null);

				return true;
			}

			if (p.getName().equalsIgnoreCase(args[0])) {
				utils.sendServerMsg(sender, "error.player-notsender");
				return true;
			}

			User u = Main.getUser(Bukkit.getOfflinePlayer(args[0]).getName());

			if (u == null)
				return utils.sendServerMsg(sender, "error.player-notexist");

			if (Bukkit.getPlayerExact(u.getName()) == null)
				u.setInv(Bukkit.createInventory(null, InventoryType.PLAYER));
			else {
				Player v = Bukkit.getPlayer(args[0]);

				for (byte i = 0; i < v.getInventory().getSize(); i++)
					v.getInventory().setItem(i, null);
			}

			return true;
		}

		return true;
	}

}
