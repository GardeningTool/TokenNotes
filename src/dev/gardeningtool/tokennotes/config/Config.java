package dev.gardeningtool.tokennotes.config;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
	
	public final Material ITEM_TYPE;
	public final String ITEM_NAME;
	public final ArrayList<String> ITEM_LORE;
	public final String WITHDRAW_SYNTAX;
	public final String PERMISSION_NODE;
	public final String NO_PERMISSION_MESSAGE;
	public final String CONSOLE_MSG;
	public final boolean STACKABLE;
	public final boolean ENCHANTED;
	public final String NOT_ENOUGH_TOKENS;
	public final String COMPLETED_MSG;
	public final String ADDED_BALANCE_MSG;
	public final String NEGATIVE_MSG;
	public final String NOT_ENOUGH_SPACE;
	public final boolean WITHDRAW_QUANTITY_ENABLED;
	public final boolean CHECK_FOR_UPDATES;
	public final String VERSION;
	
	public Config(FileConfiguration config) {
		ITEM_TYPE = Material.valueOf(config.getString("Item.Type"));
		ITEM_NAME = config.getString("Item.Name");
		ITEM_LORE = (ArrayList<String>) config.getStringList("Item.Lore");
		NO_PERMISSION_MESSAGE = config.getString("General.WithdrawPermissionMessage");
		WITHDRAW_SYNTAX = config.getString("General.WithdrawSyntax");
		PERMISSION_NODE = config.getString("General.WithdrawPermission");
		CONSOLE_MSG = config.getString("General.ConsoleMessage");
		STACKABLE = config.getBoolean("General.Stackable");
		ENCHANTED = config.getBoolean("Item.Enchanted");
		NOT_ENOUGH_TOKENS = config.getString("General.NotEnoughTokensMessage");
		COMPLETED_MSG = config.getString("General.CompletedMsg");
		ADDED_BALANCE_MSG = config.getString("General.AddedBalanceMsg");
		NEGATIVE_MSG = config.getString("General.CannotBeNegativeMsg");
		NOT_ENOUGH_SPACE = config.getString("General.NotEnoughSpace");
		WITHDRAW_QUANTITY_ENABLED = config.getBoolean("General.WithdrawQuantityEnabled");
		CHECK_FOR_UPDATES = config.getBoolean("Updater.CheckForUpdates");
		VERSION = config.getString("Updater.Version");
	}
}
