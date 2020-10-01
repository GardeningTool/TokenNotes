package dev.gardeningtool.tokennotes.listener;

import java.text.NumberFormat;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.gardeningtool.tokennotes.config.Config;
import dev.gardeningtool.tokennotes.util.TokenUtil;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public class TokenEventHandler implements Listener {
	
	private Config config;
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)  {
		Player player = event.getPlayer();
		ItemStack item = null;
		ItemMeta meta = null;
		try {
			item = player.getItemInHand();
			meta = item.getItemMeta();
		} catch (Exception exc) {
			return;
		}
		if (meta == null || !meta.hasLore() || !meta.hasDisplayName()) return;
		if (!meta.getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', config.ITEM_NAME))) return;
		if (item.getType() != config.ITEM_TYPE) return;
		List<String> lore = meta.getLore();
		List<String> configlore = config.ITEM_LORE;
		int line = 0;
		for (int i = 0; i < configlore.size(); i++) {
			if (configlore.get(i).contains("%amount%") || configlore.get(i).contains("%amount_formatted%")) {
				line = i;
				break;
			}
		}
		String lineContentRaw = ChatColor.stripColor(lore.get(line));
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < lineContentRaw.length(); i++){
			char c = lineContentRaw.charAt(i);
			if (Character.isDigit(c)) builder.append(c);
		}
		double amount = Double.parseDouble(builder.toString());
		TokenUtil.addTokens(player, amount);
		if (item.getAmount() == 1) {
			player.setItemInHand(null);
		} else {
			player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', formatMessage(amount)));
	}

	private String formatMessage(double amount) {
		String message = config.ADDED_BALANCE_MSG;
		if (message.contains("%amount%")) {
			message = message.replace("%amount%", amount + "");
		}
		if (message.contains("%amount_formatted%")) {
			message = message.replace("%amount_formatted%", NumberFormat.getNumberInstance().format(amount).toString());
		}
		if (amount == 1) {
			message = message.replace("tokens", "token");
		}
		return ChatColor.translateAlternateColorCodes('&', message.replace("%amount%", amount + "")
				.replace("%amount_formatted%", NumberFormat.getInstance().format(amount))
				.replace("tokens", amount == 1 ? "token" : "tokens"));
	}
}
