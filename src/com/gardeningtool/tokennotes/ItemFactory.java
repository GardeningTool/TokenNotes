package com.gardeningtool.tokennotes;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFactory {
	
	private ItemStack item;
	private ArrayList<String> lore = new ArrayList<String>();
	private ArrayList<ItemFlag> flags = new ArrayList<ItemFlag>();
	private boolean enchanted;
	private boolean hideEnchants;
	private String name;
	
	public ItemFactory(Material type, Integer amount) {
		this.item = new ItemStack(type, amount);
	}
		
	@SuppressWarnings("deprecation")
	public void setData(int data) {
		this.item.getData().setData((byte) data);
	}
	
	public void setLore(String... lore) {
		Arrays.asList(lore).forEach(l -> this.lore.add(ChatColor.translateAlternateColorCodes('&', l)));
	}
	
	public void setLore(ArrayList<String> lore) {
		lore.forEach(l -> this.lore.add(ChatColor.translateAlternateColorCodes('&', l)));
	}
	
	public void setName(String name) {
		this.name = ChatColor.translateAlternateColorCodes('&', name);
	}
	
	public void hideEnchants(boolean hide) {
		this.hideEnchants = hide;
	}
	
	public void setSecretEnchanted(boolean enchanted) {
		this.enchanted = enchanted;
	}
	
	public void addItemFlags(ItemFlag... flags) {
		for (ItemFlag flag : flags) {
			this.flags.add(flag);
		}
	}
	
	public void setDurability(Short s) {
		this.item.setDurability(s);
	}
	
	public void addEnchantment(Enchantment ench, Integer amount) {
		item.addEnchantment(ench, amount);
	}

	public void addUnsafeEnchantment(Enchantment ench, Integer amount) {
		item.addUnsafeEnchantment(ench, amount);
	}
	
	public ItemStack build() {
		ItemMeta meta = this.item.getItemMeta();
		if (this.enchanted) {
			meta.addEnchant(Enchantment.LURE, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		if (this.hideEnchants) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		}
		for (ItemFlag flag : this.flags) {
			meta.addItemFlags(flag);
		}
		meta.setLore(this.lore);
		meta.setDisplayName(this.name);
		this.item.setItemMeta(meta);
		return item;
	}
}
