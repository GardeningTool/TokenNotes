package com.gardeningtool.tokennotes;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TokenNotes extends JavaPlugin {

	public void onEnable() {
		saveDefaultConfig();
		new ConfigManager(getConfig());
		getCommand("tewithdraw").setExecutor(new TokenNotesCommand());
		registerEvents(new TokenEventHandler());
	}
	
	public void registerEvents(Listener... listeners) {
		Arrays.asList(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
	}
}
