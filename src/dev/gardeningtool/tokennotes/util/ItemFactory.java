package dev.gardeningtool.tokennotes.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFactory {
	
	private boolean hiddenEnch;
	private String name;
	private int amount;
	private Material material;
	private ItemFlag[] flags;
	private List<String> lore = new ArrayList<String>();
	private ItemStack item = null;
	
	/**
	 * 
	 * @param material the item type
	 * @param amount the item amount
	 * @description creates a new ItemStack object with the set parameters
	 */
	public ItemFactory(Material material, int amount) {
		this.material = material;
		this.amount = amount;
		item = new ItemStack(this.material, this.amount);
	}
	
	/**
	 * 
	 * @param material the item type
	 * @param amount the item amount
	 * @param the item data
	 * @description creates a new ItemStack object with the set parameters
	 */
	public ItemFactory(Material material, int amount, int data) {
		this.material = material;
		this.amount = amount;
		item = new ItemStack(this.material, this.amount, Byte.valueOf(String.valueOf(data)));
	}

	/**
	 * 
	 * @param material the item type
	 * @param amount the item amount
	 * @param the item data
	 * @description creates a new ItemStack object with the set parameters
	 */
	public ItemFactory(Material material, int amount, byte data) {
		this.material = material;
		this.amount = amount;
		item = new ItemStack(this.material, this.amount, data);
	}
	/**

	 /**
	 *
	 * @param enchanted whether the item generated should be secretly enchanted
	 */
	public ItemFactory setHiddenEnch(boolean enchanted) {
		this.hiddenEnch = enchanted;
		return this;
	}

	/**
	 *
	 * @param name the name of the generated ItemStack
	 */
	public ItemFactory setName(String name) {
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		return this;
	}

	/**
	 * 
	 * @param ench the enchantment to be added
	 * @param amount the level of the enchant
	 */
	public ItemFactory addEnchantment(Enchantment ench, int amount) {
		this.item.addEnchantment(ench, amount);
		return this;
	}
	
	public ItemFactory addUnsafeEnchantment(Enchantment ench, int amount) {
		this.item.addUnsafeEnchantment(ench, amount);
		return this;
	}
	
	/**
	 * 
	 * @param flags the ItemFlags to be added
	 */
	public ItemFactory setFlags(ItemFlag...flags) {
		this.flags = flags;
		return this;
	}
	
	/***
	 * 
	 * @param lore the lore of the generated Item
	 */
	public ItemFactory setLore(String...lore) {
		Arrays.asList(lore).forEach(l -> {
			this.lore.add(ChatColor.translateAlternateColorCodes('&', l));
		});
		return this;
	}
	
	/**
	 * 
	 * @param lore the lore to be set and ChatColor translated
	 */
	
	public ItemFactory setLore(List<String> lore) {
		lore.forEach(l -> {
			this.lore.add(ChatColor.translateAlternateColorCodes('&', l));
		});
		return this;
	}
	/***
	 * 
	 * @description builds the ItemStack
	 * @return returns the ItemStack
	 */
	public ItemStack build() {
		if (item == null) 
			return null;
		ItemMeta meta = item.getItemMeta();
		if (meta == null)
			System.err.println("Null meta");
		if (this.lore != null) 
			meta.setLore(this.lore);
		meta.setDisplayName(this.name);
		if (flags != null)
		Arrays.asList(this.flags).forEach(f -> {
			meta.addItemFlags(f);
		});
		if (hiddenEnch) {
			item.addUnsafeEnchantment(Enchantment.LURE, 1);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(meta);
		return item;
	}

}
