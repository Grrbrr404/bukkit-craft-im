package com.bukkit.grrbrr.CraftIM;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class CraftIMPlayerListener extends PlayerListener {
	
	public static CraftIM plugin;
	
	public CraftIMPlayerListener(CraftIM pluginInstance) {
		plugin = pluginInstance;
	}
	
	@Override
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		String msg = event.getMessage();
		java.util.Date today = new java.util.Date();
		
		msg = msg.replace("'", "''");
		
		plugin.Db.executeUpdate("" +
				"insert into messages (message, author, timestamp) values ("
				+ "'" + player.getName() + "', "
				+ "'" + msg + "', "
				+ "'" + new java.sql.Timestamp(today.getTime()) + "');");
		
		
		onNewAndroidMessage(player.getName(), msg);
		
		
		super.onPlayerChat(event);
		
	}
	
	public void onNewAndroidMessage(String senderNickname, String message) {
		plugin.getServer().broadcastMessage(
				ChatColor.GREEN 
				+ "<" + senderNickname + ">"
				+ ChatColor.WHITE 
				+ message);
	}
	
}
