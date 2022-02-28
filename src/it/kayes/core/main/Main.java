package it.kayes.core.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Objects;

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
	private static final HashMap<String, User> users = new HashMap<>();

	@Override
	public void onEnable() {
		instance = this;

		createDir();
		
		setupEconomy();

		// COMMANDS
		Objects.requireNonNull(this.getCommand("tpa")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tpa")).setTabCompleter(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tpahere")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tpahere")).setTabCompleter(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tpaccept")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tpdeny")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tpcancel")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tp")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tp")).setTabCompleter(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tphere")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tphere")).setTabCompleter(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("tpall")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("home")).setExecutor(new HomeCommands());
		Objects.requireNonNull(this.getCommand("home")).setTabCompleter(new HomeCommands());
		Objects.requireNonNull(this.getCommand("homes")).setExecutor(new HomeCommands());
		Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new HomeCommands());
		Objects.requireNonNull(this.getCommand("delhome")).setExecutor(new HomeCommands());
		Objects.requireNonNull(this.getCommand("delhome")).setTabCompleter(new HomeCommands());
		Objects.requireNonNull(this.getCommand("adminhome")).setExecutor(new AdminHomeCommands());
		Objects.requireNonNull(this.getCommand("adminhome")).setTabCompleter(new AdminHomeCommands());
		Objects.requireNonNull(this.getCommand("adminhomes")).setExecutor(new AdminHomeCommands());
		Objects.requireNonNull(this.getCommand("adminhomes")).setTabCompleter(new AdminHomeCommands());
		Objects.requireNonNull(this.getCommand("adminsethome")).setExecutor(new AdminHomeCommands());
		Objects.requireNonNull(this.getCommand("adminsethome")).setTabCompleter(new AdminHomeCommands());
		Objects.requireNonNull(this.getCommand("admindelhome")).setExecutor(new AdminHomeCommands());
		Objects.requireNonNull(this.getCommand("admindelhome")).setTabCompleter(new AdminHomeCommands());
		Objects.requireNonNull(this.getCommand("warp")).setExecutor(new WarpCommands());
		Objects.requireNonNull(this.getCommand("warp")).setTabCompleter(new WarpCommands());
		Objects.requireNonNull(this.getCommand("warps")).setExecutor(new WarpCommands());
		Objects.requireNonNull(this.getCommand("setwarp")).setExecutor(new WarpCommands());
		Objects.requireNonNull(this.getCommand("delwarp")).setExecutor(new WarpCommands());
		Objects.requireNonNull(this.getCommand("delwarp")).setTabCompleter(new WarpCommands());
		Objects.requireNonNull(this.getCommand("repair")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("repairall")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("invsee")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("invsee")).setTabCompleter(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("enderchest")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("enderchest")).setTabCompleter(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("clear")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("clear")).setTabCompleter(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("gamemode")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("gamemode")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("gms")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("gms")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("gmc")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("gmc")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("gma")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("gma")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("gmsp")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("gmsp")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("speed")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("speed")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("fly")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("fly")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("god")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("god")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("heal")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("heal")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("feed")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("feed")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("jump")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("top")).setExecutor(new TeleportCommands());
		Objects.requireNonNull(this.getCommand("suicide")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("kill")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("kill")).setTabCompleter(new GameCommands());
		Objects.requireNonNull(this.getCommand("disposal")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("anvil")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("smithingtable")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("grindstone")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("enchantingtable")).setExecutor(new InventoryCommands());
		Objects.requireNonNull(this.getCommand("seen")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("hat")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("skull")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("near")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("broadcast")).setExecutor(new GameCommands());
		/*this.getCommand("money").setExecutor(new MoneyCommands());
		this.getCommand("eco").setExecutor(new MoneyCommands());
		this.getCommand("eco").setTabCompleter(new MoneyCommands());
		this.getCommand("pay").setExecutor(new MoneyCommands());
		this.getCommand("pay").setExecutor(new MoneyCommands());
		this.getCommand("baltop").setExecutor(new MoneyCommands());*/
		Objects.requireNonNull(this.getCommand("workbench")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("back")).setExecutor(new GameCommands());
		Objects.requireNonNull(this.getCommand("back")).setTabCompleter(new GameCommands());

		// EVENTI
		getServer().getPluginManager().registerEvents(new UserLoader(),this);
		getServer().getPluginManager().registerEvents(new DeathEvents(),this);
		getServer().getPluginManager().registerEvents(new InventoryModifyEvent(),this);
		getServer().getPluginManager().registerEvents(new InventoryCommands(),this);
		
		//Listeners
		try {
			UserLoader.load();
		} catch (SQLException e) {e.printStackTrace();}
		
		//MoneyFunctions.autobaltop(60);
		
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
        econ = new EconomyImplementer();
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
