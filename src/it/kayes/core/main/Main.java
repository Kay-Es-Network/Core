package it.kayes.core.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import it.kayes.core.commands.AdminHomeCommands;
import it.kayes.core.commands.GameCommands;
import it.kayes.core.commands.HomeCommands;
import it.kayes.core.commands.InventoryCommands;
import it.kayes.core.commands.MoneyCommands;
import it.kayes.core.commands.TeleportCommands;
import it.kayes.core.commands.WarpCommands;
import it.kayes.core.functions.DeathEvents;
import it.kayes.core.functions.InventoryModifyEvent;
import it.kayes.core.functions.Messages;
import it.kayes.core.functions.MoneyFunctions;
import it.kayes.core.listeners.EconomyImplementer;
import it.kayes.core.listeners.UserLoader;
import it.kayes.core.listeners.WarpsLoader;
import it.kayes.core.obj.User;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private static Economy econ;
	private static Main instance;
	public static String usertable;
	private static HashMap<String, User> users = new HashMap<String, User>();

	@Override
	public void onEnable() {
		instance = this;

		createDir();
		
		setupEconomy();

		try {
			tryConnection();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

		// COMMANDS
		this.getCommand("tpa").setExecutor(new TeleportCommands());
		this.getCommand("tpa").setTabCompleter(new TeleportCommands());
		this.getCommand("tpahere").setExecutor(new TeleportCommands());
		this.getCommand("tpahere").setTabCompleter(new TeleportCommands());
		this.getCommand("tpaccept").setExecutor(new TeleportCommands());
		this.getCommand("tpdeny").setExecutor(new TeleportCommands());
		this.getCommand("tpcancel").setExecutor(new TeleportCommands());
		this.getCommand("tp").setExecutor(new TeleportCommands());
		this.getCommand("tp").setTabCompleter(new TeleportCommands());
		this.getCommand("tphere").setExecutor(new TeleportCommands());
		this.getCommand("tphere").setTabCompleter(new TeleportCommands());
		this.getCommand("tpall").setExecutor(new TeleportCommands());
		this.getCommand("home").setExecutor(new HomeCommands());
		this.getCommand("home").setTabCompleter(new HomeCommands());
		this.getCommand("homes").setExecutor(new HomeCommands());
		this.getCommand("sethome").setExecutor(new HomeCommands());
		this.getCommand("delhome").setExecutor(new HomeCommands());
		this.getCommand("delhome").setTabCompleter(new HomeCommands());
		this.getCommand("adminhome").setExecutor(new AdminHomeCommands());
		this.getCommand("adminhome").setTabCompleter(new AdminHomeCommands());
		this.getCommand("adminhomes").setExecutor(new AdminHomeCommands());
		this.getCommand("adminhomes").setTabCompleter(new AdminHomeCommands());
		this.getCommand("adminsethome").setExecutor(new AdminHomeCommands());
		this.getCommand("adminsethome").setTabCompleter(new AdminHomeCommands());
		this.getCommand("admindelhome").setExecutor(new AdminHomeCommands());
		this.getCommand("admindelhome").setTabCompleter(new AdminHomeCommands());
		this.getCommand("warp").setExecutor(new WarpCommands());
		this.getCommand("warp").setTabCompleter(new WarpCommands());
		this.getCommand("warps").setExecutor(new WarpCommands());
		this.getCommand("setwarp").setExecutor(new WarpCommands());
		this.getCommand("delwarp").setExecutor(new WarpCommands());
		this.getCommand("delwarp").setTabCompleter(new WarpCommands());
		this.getCommand("repair").setExecutor(new InventoryCommands());
		this.getCommand("repairall").setExecutor(new InventoryCommands());
		this.getCommand("invsee").setExecutor(new InventoryCommands());
		this.getCommand("invsee").setTabCompleter(new InventoryCommands());
		this.getCommand("enderchest").setExecutor(new InventoryCommands());
		this.getCommand("enderchest").setTabCompleter(new InventoryCommands());
		this.getCommand("clear").setExecutor(new InventoryCommands());
		this.getCommand("clear").setTabCompleter(new InventoryCommands());
		this.getCommand("gamemode").setExecutor(new GameCommands());
		this.getCommand("gamemode").setTabCompleter(new GameCommands());
		this.getCommand("gms").setExecutor(new GameCommands());
		this.getCommand("gms").setTabCompleter(new GameCommands());
		this.getCommand("gmc").setExecutor(new GameCommands());
		this.getCommand("gmc").setTabCompleter(new GameCommands());
		this.getCommand("gma").setExecutor(new GameCommands());
		this.getCommand("gma").setTabCompleter(new GameCommands());
		this.getCommand("gmsp").setExecutor(new GameCommands());
		this.getCommand("gmsp").setTabCompleter(new GameCommands());
		this.getCommand("speed").setExecutor(new GameCommands());
		this.getCommand("speed").setTabCompleter(new GameCommands());
		this.getCommand("fly").setExecutor(new GameCommands());
		this.getCommand("fly").setTabCompleter(new GameCommands());
		this.getCommand("god").setExecutor(new GameCommands());
		this.getCommand("god").setTabCompleter(new GameCommands());
		this.getCommand("heal").setExecutor(new GameCommands());
		this.getCommand("heal").setTabCompleter(new GameCommands());
		this.getCommand("feed").setExecutor(new GameCommands());
		this.getCommand("feed").setTabCompleter(new GameCommands());
		this.getCommand("jump").setExecutor(new TeleportCommands());
		this.getCommand("top").setExecutor(new TeleportCommands());
		this.getCommand("suicide").setExecutor(new GameCommands());
		this.getCommand("kill").setExecutor(new GameCommands());
		this.getCommand("kill").setTabCompleter(new GameCommands());
		this.getCommand("disposal").setExecutor(new GameCommands());
		this.getCommand("seen").setExecutor(new GameCommands());
		this.getCommand("hat").setExecutor(new GameCommands());
		this.getCommand("skull").setExecutor(new GameCommands());
		this.getCommand("near").setExecutor(new GameCommands());
		this.getCommand("broadcast").setExecutor(new GameCommands());
		this.getCommand("money").setExecutor(new MoneyCommands());
		this.getCommand("eco").setExecutor(new MoneyCommands());
		this.getCommand("eco").setTabCompleter(new MoneyCommands());
		this.getCommand("pay").setExecutor(new MoneyCommands());
		this.getCommand("pay").setExecutor(new MoneyCommands());
		this.getCommand("baltop").setExecutor(new MoneyCommands());
		this.getCommand("workbench").setExecutor(new GameCommands());
		this.getCommand("back").setExecutor(new GameCommands());
		this.getCommand("back").setTabCompleter(new GameCommands());

		// EVENTI
		getServer().getPluginManager().registerEvents((Listener)new UserLoader(),this);
		getServer().getPluginManager().registerEvents((Listener)new DeathEvents(),this);
		getServer().getPluginManager().registerEvents((Listener)new InventoryModifyEvent(),this);
		
		//Listeners
		try {
			UserLoader.load();
		} catch (SQLException e) {e.printStackTrace();}
		
		MoneyFunctions.autobaltop(60);
		
		Messages.load();
		WarpsLoader.load();
	}
	
	@Override
	public void onDisable() {
		//Listeners
		try {
			UserLoader.save();
			WarpsLoader.save();
		} catch (SQLException e) {e.printStackTrace();}
	}

	private static void createDir() {
		new File("plugins/KayEs-Core").mkdir();
	}

	public static Main getInstance() {
		return instance;
	}

	// DATABASE
	private static Connection connection;
	private static Statement sql;
	
	public static Statement getSQL() {
		return sql;
	}

	public void openConnection() throws SQLException, ClassNotFoundException {
		File f = new File("plugins/KayEs-Core" + File.separator + "mysql.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

		if (!f.exists()) {
			try {
				f.createNewFile();

				cfg.set("host", "localhost");
				cfg.set("port", "3306");
				cfg.set("username", "KayEsCore");
				cfg.set("database", "KayEsCore");
				cfg.set("password", "");
				cfg.set("usertable", "testdata");
				cfg.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (f.exists() && cfg.getString("host") != null) {
			usertable = cfg.getString("usertable");
			if (connection != null && !connection.isClosed())
				return;
			synchronized (this) {
				if (connection != null && !connection.isClosed()) {
					System.out.println("CONNESSIONE A MYSQL FALLITA");
					return;
				}
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager
						.getConnection(
								"jdbc:mysql://" + cfg.getString("host") + ":" + cfg.getString("port") + "/"
										+ cfg.getString("database"),
								cfg.getString("username"), cfg.getString("password"));
			}
		}
	}

	public void tryConnection() throws SQLException, ClassNotFoundException, IOException {
		openConnection();
		sql = connection.createStatement();
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					if (sql.isClosed()) {
						openConnection();
						sql = connection.createStatement();	
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}				
			}
		}.runTaskTimerAsynchronously(instance, 1200L, 1200L);
	}

	public void reloadConnection() throws SQLException, ClassNotFoundException, IOException {
		sql.close();
		connection.close();
		instance.tryConnection();
	}

	private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        getServer().getServicesManager().register(Economy.class, new EconomyImplementer(), this, ServicePriority.Normal);
        econ = (Economy)new EconomyImplementer();
        return (econ != null);
    }

    public Economy getEconomy() {
        return econ;
    }
	
	public static HashMap<String, User> getUsers() {
		return users;
	}
	
	public static User getUser(String user) {
		return getUsers().get(user);
	}
	

}
