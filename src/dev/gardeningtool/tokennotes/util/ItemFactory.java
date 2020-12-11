package dev.gardeningtool.claimablerewards.util;

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

    private String name;
    private int amount;
    private Material material;
    private ItemFlag[] flags;
    private List<String> lore = new ArrayList<>();
    private ItemStack item;

    /**
     *
     * @param material the item type
     * @param amount the item amount
     * @description creates a new ItemStack object with the set parameters
     */
    public ItemFactory(Material material, int amount) {
        this(material, amount, 0);
    }

    /**
     *
     * @param material the item type
     * @param amount the item amount
     * @param data item data
     * @description creates a new ItemStack object with the set parameters
     */
    public ItemFactory(Material material, int amount, int data) {
        this(material, amount, (byte) data);
    }

    /**
     *
     * @param material the item type
     * @param amount the item amount
     * @param data item data
     * @description creates a new ItemStack object with the set parameters
     */
    public ItemFactory(Material material, int amount, byte data) {
        this.material = material;
        this.amount = amount;
        item = new ItemStack(this.material, this.amount, data);
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
        Arrays.asList(lore).forEach(line -> {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', line));
        });
        return this;
    }

    /**
     *
     * @param lore the lore to be set and ChatColor translated
     */

    public ItemFactory setLore(List<String> lore) {
        lore.forEach(line -> {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', line));
        });
        return this;
    }
    /***
     *
     * @description builds the ItemStack
     * @return returns the ItemStack
     */
    public ItemStack build() {
        if (item == null || item.getItemMeta() == null) {
            throw new IllegalStateException("Item or item meta cannot be null!");
        }
        ItemMeta meta = item.getItemMeta();
        if (lore != null)
            meta.setLore(lore);
        if (name != null) {
            meta.setDisplayName(name);
        }
        if (flags != null)
            Arrays.stream(flags).forEach(meta::addItemFlags);
        item.setItemMeta(meta);
        return item;
    }

}
