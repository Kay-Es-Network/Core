package it.kayes.core.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import it.kayes.core.functions.InventoryModifyEvent;
import it.kayes.core.functions.Messages;
import it.kayes.core.main.Main;
import it.kayes.core.main.utils;
import it.kayes.core.obj.User;

public class InventoryCommands implements CommandExecutor, TabCompleter, Listener {

	private static final HashMap<String, Inventory> saveInventory = new HashMap<>();
	private static final HashMap<String, ArrayList<ItemStack>> saveInventoryItems = new HashMap<>();

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("repair") || cmd.getName().equalsIgnoreCase("fix")) {
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
		}
		else if (cmd.getName().equalsIgnoreCase("repairall") || cmd.getName().equalsIgnoreCase("fixall")) {
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
			}

			msg = Messages.getMessage("repair.doall");
			for (String s : msg)
				utils.sendMsg(p, s.replaceAll("%PREFIX%", Messages.getPrefix()));

			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("invsee")) {
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
		}
		else if (cmd.getName().equalsIgnoreCase("ec") || cmd.getName().equalsIgnoreCase("enderchest")) {
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
		}
		else if (cmd.getName().equalsIgnoreCase("clear")) {
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
		else if (cmd.getName().equalsIgnoreCase("trash") || cmd.getName().equalsIgnoreCase("disposal") || cmd.getName().equalsIgnoreCase("cestino") || cmd.getName().equalsIgnoreCase("bin")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("vip") && !p.hasPermission("trash")
					|| p.hasPermission("trash.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			p.openInventory(Bukkit.createInventory(null, 27, ""));
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("anvil")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("vip") && !p.hasPermission("anvil")
					|| p.hasPermission("anvil.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			Inventory inv = Bukkit.createInventory(p, InventoryType.ANVIL);
			p.openInventory(inv);
			saveInventory.put(p.getName(), inv);
			saveInventoryItems.put(p.getName(), new ArrayList<>());
		}
		else if (cmd.getName().equalsIgnoreCase("smithingtable") || cmd.getName().equalsIgnoreCase("smtable")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("vip") && !p.hasPermission("smithingtable")
					|| p.hasPermission("smithingtable.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			Inventory inv = Bukkit.createInventory(p, InventoryType.SMITHING);
			p.openInventory(inv);
			saveInventory.put(p.getName(), inv);
			saveInventoryItems.put(p.getName(), new ArrayList<>());
		}
		else if (cmd.getName().equalsIgnoreCase("grindstone") || cmd.getName().equalsIgnoreCase("gstone")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("vip") && !p.hasPermission("gstone")
					|| p.hasPermission("gstone.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			Inventory inv = Bukkit.createInventory(p, InventoryType.GRINDSTONE);
			p.openInventory(inv);
			saveInventory.put(p.getName(), inv);
			saveInventoryItems.put(p.getName(), new ArrayList<>());
		}
		else if (cmd.getName().equalsIgnoreCase("enchanttable") || cmd.getName().equalsIgnoreCase("enchantingtable")) {
			if (!(sender instanceof Player))
				return utils.sendServerMsg(sender, "error.player-notconsole");

			Player p = (Player) sender;

			if (!p.hasPermission("vip") && !p.hasPermission("enchantingtable")
					|| p.hasPermission("enchantingtable.limit") && !p.hasPermission("*"))
				return utils.sendServerMsg(sender, "error.nopermission");

			Inventory inv = Bukkit.createInventory(p, InventoryType.ENCHANTING);
			p.openInventory(inv);
			saveInventory.put(p.getName(), inv);
			saveInventoryItems.put(p.getName(), new ArrayList<>());
		}

		return true;
	}

	/*@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory cInv = e.getClickedInventory();
		if (!saveInventory.containsKey(p.getName()) || (cInv.getType().equals(InventoryType.PLAYER))) {
			if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT))
				e.setCancelled(true);
			return;
		}

		ItemStack item = null, cursor = null;
		if (e.getCurrentItem()!=null)
			item = new ItemStack(e.getCurrentItem());
		if (e.getCursor()!=null)
			cursor = new ItemStack(e.getCursor());

		int slot = e.getSlot();

		if (cInv.getItem(slot)!=null) {
			saveInventoryItems.get(p.getName()).remove(item);
		} else {
			saveInventoryItems.get(p.getName()).add(cursor);
		}

	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (!saveInventory.containsKey(p.getName()))
			return;

		for (ItemStack it : saveInventoryItems.get(p.getName()))
			if (it != null && !it.getType().equals(Material.AIR))
				p.getLocation().getWorld().dropItem(p.getLocation(), it);

		saveInventoryItems.remove(p.getName());
		saveInventory.remove(p.getName());
	}*/

	@Override
	public List<String> onTabComplete(CommandSender sender,  Command cmd,  String label, String[] args) {
		ArrayList<String> res = new ArrayList<String>();
		if (cmd.getName().equalsIgnoreCase("invsee") || cmd.getName().equalsIgnoreCase("clear")
				|| cmd.getName().equalsIgnoreCase("enderchest") || cmd.getName().equalsIgnoreCase("ec")) {
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
