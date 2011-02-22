package com.bukkit.grrbrr.CraftIM;

import java.io.File;
import java.io.IOException;

import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
* Backbone Plugin for Android CraftIM
*
* @author Grrbrr404
*/
public class CraftIM extends JavaPlugin {

	private final CraftIMPlayerListener playerListener = new CraftIMPlayerListener(this);
	protected Database Db = null;
	
	public CraftIM(PluginLoader pluginLoader, Server instance,
			PluginDescriptionFile desc, File folder, File plugin,
			ClassLoader cLoader) {
		super(pluginLoader, instance, desc, folder, plugin, cLoader);
		
		try {
			Db = new Database(this);
			Db.executeUpdate("create table if not exists messages (message, author, timestamp);");
		} catch (ClassNotFoundException e) {
			Debug(e.toString());
		}
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		System.out.println("CraftIM disabled");
	}

	@Override
	public void onEnable() {
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Normal, this);
		
		setupPluginDirectoriesIfNotExist();
		
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println("Plugin enabled lol " + pdfFile.getName() + " V " + pdfFile.getVersion());
	}

	/**
	 * Checks if necessary file structure is already created
	 * If not, it will create it  
	 */
	private void setupPluginDirectoriesIfNotExist() {
		File pluginFileDir = getDataFolder();
		
		if (!pluginFileDir.isDirectory()) {
			if (pluginFileDir.mkdir()) {
				Debug("Plugin directory created");
			}
		}
		
		File config = new File(pluginFileDir, "config.yml");
		if (!config.exists()) {
			try {
				config.createNewFile();
				Debug("config.yml created inside Plugin directory");
			} catch (IOException e) {
				Debug(e.toString());
			}
		}
		
		File sqliteDb = new File(pluginFileDir, "sqlite.db");
		if (!sqliteDb.exists()) {
				Db.establishConnection();
				Debug("SQLite connection established");			
		}
	}
	
	private void Debug(String message) {
		System.out.println("CraftIM: " + message);
		
	}
}
