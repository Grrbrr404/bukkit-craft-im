package com.bukkit.grrbrr.CraftIM;

import org.bukkit.event.block.BlockListener;
public class CraftIMBlockListener extends BlockListener {
	
	public static CraftIM plugin;
	
	public CraftIMBlockListener(CraftIM pluginInstance) {
		plugin = pluginInstance;
		
	}
}
