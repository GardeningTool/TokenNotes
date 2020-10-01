package dev.gardeningtool.tokennotes;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.gardeningtool.tokennotes.command.TokenNotesCommand;
import dev.gardeningtool.tokennotes.config.Config;
import dev.gardeningtool.tokennotes.listener.TokenEventHandler;
import dev.gardeningtool.tokennotes.listener.UpdateHandler;

public class TokenNotes extends JavaPlugin {

	public void onEnable() {
		saveDefaultConfig();
		Config config = new Config(this.getConfig());
		getCommand("tewithdraw").setExecutor(new TokenNotesCommand(config));
		Bukkit.getPluginManager().registerEvents(new TokenEventHandler(config), this);
		if (config.CHECK_FOR_UPDATES)
			Bukkit.getPluginManager().registerEvents(new UpdateHandler(config), this);
	}

}
