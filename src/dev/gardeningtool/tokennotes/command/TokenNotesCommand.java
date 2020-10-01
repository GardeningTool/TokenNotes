package dev.gardeningtool.tokennotes.command;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import dev.gardeningtool.tokennotes.config.Config;
import dev.gardeningtool.tokennotes.util.ItemFactory;
import dev.gardeningtool.tokennotes.util.TokenUtil;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public class TokenNotesCommand implements CommandExecutor {
	
	private Config config;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(translate(config.CONSOLE_MSG));
				return false;
			}
			Player player = (Player) sender;
			if (!player.hasPermission(config.PERMISSION_NODE)) {
				sender.sendMessage(translate(config.NO_PERMISSION_MESSAGE));
				return false;
			}
			if (args.length != 1 && args.length != 2) {
				sender.sendMessage(translate(config.WITHDRAW_SYNTAX));
				return false;
			}
			if (args.length == 1) {
				if (!isInteger(args[0])) {
					sender.sendMessage(translate(config.WITHDRAW_SYNTAX));
					return false;
				}
				final int amount = Integer.parseInt(args[0]);
				if (TokenUtil.getTokens(player) < amount) {
					sender.sendMessage(translate(config.NOT_ENOUGH_TOKENS));
					return false;
				}
				if (amount <= 0) {
					sender.sendMessage(translate(config.NEGATIVE_MSG));
					return false;
				}
				if (!hasAvailableSlot(player)) {
					sender.sendMessage(translate(config.NOT_ENOUGH_SPACE));
					return false;
				}
				ItemFactory item = new ItemFactory(config.ITEM_TYPE, 1);
				item.setName(formatMessage(player, (double) amount, config.ITEM_NAME));
				ArrayList<String> lore = new ArrayList<String>();
				config.ITEM_LORE.forEach(line -> {
					final String lineContentFormatted = formatMessage(player, (double) amount, line);
					System.out.println(lineContentFormatted);
					lore.add(ChatColor.translateAlternateColorCodes('&', lineContentFormatted));
				});
				item.setLore(lore);
				if (config.ENCHANTED) {
					item.addUnsafeEnchantment(Enchantment.LURE, 1);
					item.setFlags(ItemFlag.HIDE_ENCHANTS);
				}
				if (!config.STACKABLE) {
					item.setName(formatMessage(player, amount, config.ITEM_NAME) + generateRandom());
				}
				player.getInventory().addItem(item.build());
				TokenUtil.removeTokens(player, (double) amount);
				return true;
			}
			if (!config.WITHDRAW_QUANTITY_ENABLED) {
				sender.sendMessage(translate(config.WITHDRAW_SYNTAX));
				return false;
			}
			if (args.length == 2) {
				if (!isInteger(args[0]) || !isInteger(args[1])) {
					sender.sendMessage(translate(config.WITHDRAW_SYNTAX));
					return false;
				}
				int amount = Integer.parseInt(args[0]);
				int multi = Integer.parseInt(args[1]);
				if (TokenUtil.getTokens(player) < amount * multi) {
					sender.sendMessage(translate(config.NOT_ENOUGH_TOKENS));
					return false;
				}
				if (amount < 0 || multi < 0) {
					sender.sendMessage(translate(config.NEGATIVE_MSG));
					return false;
				}
				if (!hasAvailableSlot((Player) sender)) {
					sender.sendMessage(translate(config.NOT_ENOUGH_SPACE));
					return false;
				}
				ItemFactory item = new ItemFactory(config.ITEM_TYPE, 1);
				item.setName(formatMessage(player, amount, config.ITEM_NAME));
				ArrayList<String> lore = new ArrayList<String>();
				config.ITEM_LORE.forEach(line -> {
					String lineFormatted = formatMessage(player, amount, line);
					lore.add(ChatColor.translateAlternateColorCodes('&', lineFormatted));
				});
				item.setLore(lore);
				if (config.ENCHANTED) {
					item.addUnsafeEnchantment(Enchantment.LURE, 1);
					item.setFlags(ItemFlag.HIDE_ENCHANTS);
				}
				if (!config.STACKABLE) {
					for (int i = 0; i < multi; i++) {
						player.getInventory().addItem(item.setName(formatMessage(player, amount, config.ITEM_NAME) + generateRandom()).build());
					}
				} else {
					item.setName(formatMessage(player, amount, config.ITEM_NAME) + generateRandom());
					player.getInventory().addItem(item.build());
				}
				player.sendMessage(translate(formatMessage(player, amount * multi, config.COMPLETED_MSG)));
				TokenUtil.removeTokens(player, (double) amount * multi);
				return true;

		}
		return false;
	}
	
	private boolean hasAvailableSlot(Player player) {
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			if (player.getInventory().getItem(i) == null) {
				return true;
			}
		}
		return false;
	}
	
	private String formatMessage(Player withdrawer, double amount, String message) {
		return message.replace("%withdrawer%", withdrawer.getName()).replace("%amount%", amount + "")
				.replace("%amount_formatted%", NumberFormat.getInstance().format(amount)).replace("tokens", amount == 1 ? "token" : "tokens");
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
		StringBuilder builder = new StringBuilder("&7");
		for (int i = 0; i < 15; i++) {
			builder.append(ChatColor.translateAlternateColorCodes('&', "&" + ThreadLocalRandom.current().nextInt(0, 10)));
		}
		return builder.toString();
	}
	
	private String translate(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}
}
