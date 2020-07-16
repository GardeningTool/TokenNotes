package com.gardeningtool.tokennotes;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TokenNotesCommand implements CommandExecutor {
		
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tewithdraw")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.CONSOLE_MSG));
				return false;
			}
			if (!sender.hasPermission(ConfigValues.PERMISSION_NODE)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.NO_PERMISSION_MESSAGE));
				return false;
			}
			if (args.length != 1 && args.length != 2) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.WITHDRAW_SYNTAX));
				return false;
			}
			if (args.length == 1) {
				if (!isInteger(args[0])) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.WITHDRAW_SYNTAX));
					return false;
				}
				if (EconUtils.getTokens(Bukkit.getPlayer(sender.getName())) < Integer.parseInt(args[0])) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.NOT_ENOUGH_TOKENS));
					return false;
				}
				if (Integer.parseInt(args[0]) < 0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.NEGATIVE_MSG));
					return false;
				}
				if (!hasAvailableSlot((Player) sender)) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.NOT_ENOUGH_SPACE));
					return false;
				}
				ItemFactory item = new ItemFactory(ConfigValues.ITEM_TYPE, 1);
				item.setName(getName(sender, args[0], ConfigValues.ITEM_NAME));
				ArrayList<String> lore = new ArrayList<String>();
				ConfigValues.ITEM_LORE.forEach(s -> {
					String l = getName(sender, args[0], s);
					lore.add(ChatColor.translateAlternateColorCodes('&', l));
				});
				item.setLore(lore);
				if (!(ConfigValues.ITEM_DATA != (byte) 0)) {
					item.setData(ConfigValues.ITEM_DATA);
				}
				if (ConfigValues.ENCHANTED) {
					item.setSecretEnchanted(true);
				}
				if (!ConfigValues.STACKABLE) {
					item.setName(getName(sender, args[0], ConfigValues.ITEM_NAME) + generateRandom());
				}
				Bukkit.getPlayer(sender.getName()).getInventory().addItem(item.build());
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getName(sender, args[0], ConfigValues.COMPLETED_MSG)));
				EconUtils.removeTokens(Bukkit.getPlayer(sender.getName()), (double) Integer.parseInt(args[0]));
				return true;
			}
			if (!ConfigValues.WITHDRAW_QUANTITY_ENABLED) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.WITHDRAW_SYNTAX));
				return false;
			}
			if (args.length == 2) {
				if (!isInteger(args[0])) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.WITHDRAW_SYNTAX));
					return false;
				}
				if (!isInteger(args[1])) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.WITHDRAW_SYNTAX));
					return false;
				}
				if (EconUtils.getTokens(Bukkit.getPlayer(sender.getName())) < (Integer.parseInt(args[0]) * Integer.parseInt(args[1]))) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.NOT_ENOUGH_TOKENS));
					return false;
				}
				if (Integer.parseInt(args[0]) < 0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.NEGATIVE_MSG));
					return false;
				}
				if (!hasAvailableSlot((Player) sender)) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigValues.NOT_ENOUGH_SPACE));
					return false;
				}
				ItemFactory item = new ItemFactory(ConfigValues.ITEM_TYPE, Integer.parseInt(args[1]));
				item.setName(getName(sender, args[0], ConfigValues.ITEM_NAME));
				ArrayList<String> lore = new ArrayList<String>();
				ConfigValues.ITEM_LORE.forEach(s -> {
					String l = getName(sender, args[0], s);
					lore.add(ChatColor.translateAlternateColorCodes('&', l));
				});
				item.setLore(lore);
				if (!(ConfigValues.ITEM_DATA != (byte) 0)) {
					item.setData(ConfigValues.ITEM_DATA);
				}
				if (ConfigValues.ENCHANTED) {
					item.setSecretEnchanted(true);
				}
				if (!ConfigValues.STACKABLE) {
					item.setName(getName(sender, args[0], ConfigValues.ITEM_NAME) + generateRandom());
				}
				Bukkit.getPlayer(sender.getName()).getInventory().addItem(item.build());
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getName(sender, args[0], ConfigValues.COMPLETED_MSG)));
				EconUtils.removeTokens(Bukkit.getPlayer(sender.getName()), ((double) Double.parseDouble(args[0]) * Integer.parseInt(args[1])));
				return true;
			}
		}
		return false;
	}
	
	private boolean hasAvailableSlot(Player p) {
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) == null) {
				return true;
			}
		}
		return false;
	}
	
	private String getName(CommandSender sender, String amount, String s) {
		String l = s;
		if (l.contains("[WITHDRAWER]")) {
			l = l.replace("[WITHDRAWER]", sender.getName());
		}
		if (l.contains("[AMOUNT]")) {
			l = l.replace("[AMOUNT]", amount);
		}
		if (l.contains("[AMOUNT_COMMAS]")) {
			l = l.replace("[AMOUNT_COMMAS]", NumberFormat.getNumberInstance().format(Integer.parseInt(amount)));
		}
		return (ChatColor.translateAlternateColorCodes('&', l));
	}
	
	private boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public String generateRandom() {
		StringBuilder builder = new StringBuilder();
		builder.append("");
		for(int i = 0; i < 15; i++) {
			builder.append(ChatColor.translateAlternateColorCodes('&', "&" + ThreadLocalRandom.current().nextInt(0, 10)));
		}
		return builder.toString();
	}
}
