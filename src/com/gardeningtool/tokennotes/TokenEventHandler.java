package com.gardeningtool.tokennotes;

import java.text.NumberFormat;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;

public class TokenEventHandler implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)  {
		Player p = e.getPlayer();
		if (p.getItemInHand().getItemMeta() == null) {
			return;
		}
		if (p.getItemInHand().getItemMeta().getDisplayName() == null) {
			return;
		}
		if (p.getItemInHand().getItemMeta().getLore() == null) {
			return;
		}
		if (!(p.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', ConfigValues.ITEM_NAME)))) {
			return;
		}
		if (p.getItemInHand().getType() != ConfigValues.ITEM_TYPE) {
			return;
		}
		List<String> lore = p.getItemInHand().getItemMeta().getLore();
		List<String> configlore = ConfigValues.ITEM_LORE;
		int loc = 0;
		for (int i = 0; i < configlore.size(); i++) {
			if (configlore.get(i).contains("[AMOUNT]") || configlore.get(i).contains("[AMOUNT_COMMAS]")) {
				loc = i;
				break;
			}
		}
		String s = ChatColor.stripColor(lore.get(loc));
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				builder.append(c);
			}
		}
		double amount = Double.parseDouble(builder.toString());
		EconUtils.addTokens(p, (double) amount);
		if (p.getItemInHand().getAmount() == 1) {
			p.setItemInHand(null);
		} else {
			p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
		}
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', formattedMessage(amount)));
		return;
	}

	private String formattedMessage(double amount) {
		String s = ConfigValues.ADDED_BALANCE_MSG;
		if (s.contains("[AMOUNT]")) {
			s = s.replace("[AMOUNT]", amount + "");
		}
		if (s.contains("[AMOUNT_COMMAS]")) {
			s = s.replace("[AMOUNT_COMMAS]", NumberFormat.getNumberInstance().format(amount).toString());
		}
		if (amount == 1) {
			s = s.replace("tokens", "token");
		}
		return s;
	}
}
