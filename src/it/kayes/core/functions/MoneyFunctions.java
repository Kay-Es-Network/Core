package it.kayes.core.functions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;

import it.kayes.core.main.Main;
import it.kayes.core.obj.Conto;
import it.kayes.core.obj.User;

public class MoneyFunctions {

	private static final Statement sql = Main.getSQL();
	private static final HashMap<Integer, Conto> baltop = new HashMap<Integer, Conto>();
	private static final HashMap<String, Integer> baltopnames = new HashMap<String, Integer>();

	public static int getBaltopPosition(String name) {
		if (baltopnames.containsKey(name))
			return baltopnames.get(name);
		return -1;
	}

	public static Conto getBaltop(int pos) {
		if (baltop.size() < pos)
			return null;
		return baltop.get(pos);
	}

	public static void reorder() throws SQLException {
		sql.executeUpdate("TRUNCATE " + Main.usertable);
		for (User u : Main.getUsers().values())
			sql.executeUpdate("INSERT INTO " + Main.usertable + " (NAME,MONEY) VALUES ('" + u.getName() + "',"
					+ u.getMoney() + ")");

		baltop.clear();
		baltopnames.clear();

		ResultSet rs = sql.executeQuery("SELECT * FROM " + Main.usertable + " ORDER BY MONEY DESC");
		int pos = 1;
		while (rs.next()) {
			baltopnames.put(rs.getString("NAME").toUpperCase(), pos);
			baltop.put(pos, new Conto(rs.getDouble("MONEY"), rs.getString("NAME")));
			pos++;
		}
	}
	
	public static void autobaltop(final int s) {
		try {
			reorder();
		} catch (SQLException e1) {}
		new BukkitRunnable() {
			int sec = s;
			@Override
			public void run() {
				if (sec==0) {
					sec = s;
					try {
						reorder();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else
				sec--;
			}
			
		}.runTaskTimerAsynchronously(Main.getInstance(), 200L, 200L);
	}

}
