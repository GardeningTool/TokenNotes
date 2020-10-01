package dev.gardeningtool.tokennotes.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.gardeningtool.tokennotes.config.Config;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateHandler implements Listener {
	
	private Config config;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (!(event.getPlayer().isOp())) return;
		new Thread(() -> {
			try {
				URL url = new URL("https://gardeningtool.dev/plugins/version/TokenNotes.txt");
				URLConnection connection = url.openConnection();
			    connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
			    connection.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = "";
				while ((line = reader.readLine()) != null) {
					if (!line.equalsIgnoreCase(config.VERSION)) {
						event.getPlayer().sendMessage("§7[§cTokenNotes§7] A new version is available! Currently using §f" + config.VERSION + "§7!");
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
	}
}
