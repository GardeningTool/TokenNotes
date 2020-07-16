package com.gardeningtool.tokennotes;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

	public ConfigManager(FileConfiguration config) {
		ConfigValues.ITEM_TYPE = Material.valueOf(config.getString("Item.Type"));
		ConfigValues.ITEM_NAME = config.getString("Item.Name");
		ConfigValues.ITEM_LORE = (ArrayList<String>) config.getStringList("Item.Lore");
		ConfigValues.ITEM_DATA = (byte) config.getInt("Item.ItemData");
		ConfigValues.NO_PERMISSION_MESSAGE = config.getString("General.WithdrawPermissionMessage");
		ConfigValues.WITHDRAW_SYNTAX = config.getString("General.WithdrawSyntax");
		ConfigValues.PERMISSION_NODE = config.getString("General.WithdrawPermission");
		ConfigValues.CONSOLE_MSG = config.getString("General.ConsoleMessage");
		ConfigValues.STACKABLE = config.getBoolean("General.Stackable");
		ConfigValues.ENCHANTED = config.getBoolean("Item.Enchanted");
		ConfigValues.NOT_ENOUGH_TOKENS = config.getString("General.NotEnoughTokensMessage");
		ConfigValues.COMPLETED_MSG = config.getString("General.CompletedMsg");
		ConfigValues.ADDED_BALANCE_MSG = config.getString("General.AddedBalanceMsg");
		ConfigValues.NEGATIVE_MSG = config.getString("General.CannotBeNegativeMsg");
		ConfigValues.NOT_ENOUGH_SPACE = config.getString("General.NotEnoughSpace");
		ConfigValues.WITHDRAW_QUANTITY_ENABLED = config.getBoolean("General.WithdrawQuantityEnabled");
	}
	
}
