package it.kayes.core.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import it.kayes.core.commands.AdminHomeCommands;
import it.kayes.core.commands.HomeCommands;
import it.kayes.core.commands.InventoryCommands;
import it.kayes.core.commands.TeleportCommands;
import it.kayes.core.commands.WarpCommands;
import it.kayes.core.functions.InventoryModifyEvent;
import it.kayes.core.functions.Messages;
import it.kayes.core.listeners.UserLoader;
import it.kayes.core.listeners.WarpsLoader;

public class Main extends JavaPlugin {

	private static Main instance;
	public static String usertable;

	@Override
	public void onEnable() {
		instance = this;

		createDir();

		try {
			tryConnection();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

		// COMMANDS
		this.getCommand("tpa").setExecutor(new TeleportCommands());
		this.getCommand("tpahere").setExecutor(new TeleportCommands());
		this.getCommand("tpaccept").setExecutor(new TeleportCommands());
		this.getCommand("tpdeny").setExecutor(new TeleportCommands());
		this.getCommand("tpcancel").setExecutor(new TeleportCommands());
		this.getCommand("tp").setExecutor(new TeleportCommands());
		this.getCommand("tphere").setExecutor(new TeleportCommands());
		this.getCommand("tpall").setExecutor(new TeleportCommands());
		this.getCommand("home").setExecutor(new HomeCommands());
		this.getCommand("homes").setExecutor(new HomeCommands());
		this.getCommand("sethome").setExecutor(new HomeCommands());
		this.getCommand("delhome").setExecutor(new HomeCommands());
		this.getCommand("adminhome").setExecutor(new AdminHomeCommands());
		this.getCommand("adminhomes").setExecutor(new AdminHomeCommands());
		this.getCommand("adminsethome").setExecutor(new AdminHomeCommands());
		this.getCommand("admindelhome").setExecutor(new AdminHomeCommands());
		this.getCommand("warp").setExecutor(new WarpCommands());
		this.getCommand("warps").setExecutor(new WarpCommands());
		this.getCommand("setwarp").setExecutor(new WarpCommands());
		this.getCommand("delwarp").setExecutor(new WarpCommands());
		this.getCommand("repair").setExecutor(new InventoryCommands());
		this.getCommand("repairall").setExecutor(new InventoryCommands());
		this.getCommand("invsee").setExecutor(new InventoryCommands());

		// EVENTI
		getServer().getPluginManager().registerEvents((Listener)new UserLoader(),this);
		getServer().getPluginManager().registerEvents((Listener)new InventoryModifyEvent(),this);
		
		//Listeners
		try {
			UserLoader.load();
		} catch (SQLException e) {e.printStackTrace();}
		
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
	}

	public static void reloadConnection() throws SQLException, ClassNotFoundException, IOException {
		sql.close();
		connection.close();
		instance.tryConnection();
	}

}
