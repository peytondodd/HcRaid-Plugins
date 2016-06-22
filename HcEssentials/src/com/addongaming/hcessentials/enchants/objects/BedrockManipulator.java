package com.addongaming.hcessentials.enchants.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.addongaming.hcessentials.enchants.EnchantItem;
import com.addongaming.hcessentials.enchants.HcEnchantment;

public class BedrockManipulator implements HcEnchantment {
	private int randomNum;
	private int enchantLevel1;

	@Override
	public ItemStack addToItem(ItemStack toEnchant, int level)
			throws IllegalArgumentException {
		if (!EnchantItem.isValid(toEnchant, getHoldingType())) {
			throw new IllegalArgumentException("Expected: "
					+ getHoldingType().name() + " found "
					+ toEnchant.getType().name());
		}
		ItemMeta im = toEnchant.getItemMeta();
		List<String> lore;
		if (im.hasLore())
			lore = new ArrayList<String>(im.getLore());
		else
			lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + getName() + " "
				+ EnchantItem.numToNumeral(level));
		im.setLore(lore);
		toEnchant.setItemMeta(im);
		return toEnchant;
	}

	@Override
	public String getDescription() {
		return "This enchant will let you harvest bedrock!";
	}

	@Override
	public EnchantItem getHoldingType() {
		return EnchantItem.AXE;
	}

	@Override
	public int getLevelToEnchant(int level, ItemStack is) {
		if (!EnchantItem.isValid(is, getHoldingType()))
			return 0;
		int chance = new Random().nextInt(randomNum);
		if (chance == 1) {
			if (level > enchantLevel1)
				return 1;
		}
		return 0;
	}

	@Override
	public String getName() {
		return "Bedrock Manipulator";
	}

	@Override
	public boolean loadConfig(JavaPlugin jp) {
		jp.getConfig().addDefault("enchants.bedmanip.enabled", false);
		jp.getConfig().addDefault("enchants.bedmanip.enchantingRandom", 50);
		jp.getConfig().addDefault("enchants.bedmanip.enchantLevel1", 5);
		jp.getConfig().options().copyDefaults(true);
		jp.saveConfig();
		this.randomNum = jp.getConfig().getInt(
				"enchants.bedmanip.enchantingRandom");
		this.enchantLevel1 = jp.getConfig().getInt(
				"enchants.bedmanip.enchantLevel1");
		return jp.getConfig().getBoolean("enchants.bedmanip.enabled");
	}
}
