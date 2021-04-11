package skyblock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;

public class EnchantItemListener implements Listener {
    @EventHandler
    public void enchantItemEvent(EnchantItemEvent event) {
        for (EnchantmentBase enchantment : EnchantmentRegistry.enchantments) {
            if (enchantment.appliesOn(event.getItem()) && Math.random() < enchantment.getChance()) {
                int enchantmentLevel = (int) Math.round(event.getExpLevelCost() / (30.0 / enchantment.getMaxLevel()));
                insertEnchantmentLore(event.getItem(), enchantment, enchantmentLevel);
            }
        }
    }

    public static void insertEnchantmentLore(ItemStack itemStack, EnchantmentBase enchantment, int level) {
        ArrayList<String> lore = new ArrayList<>();
        if (itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null) {
            lore.addAll(itemStack.getItemMeta().getLore());
        }
        lore.add(0, ChatColor.GRAY + enchantment.getName() + " " + EnchantmentBase.intToRomanLetters(level));
        ItemRegistry.setLore(itemStack, lore);
    }
}
