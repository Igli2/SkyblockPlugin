package skyblock.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class EnchantmentBase {
    public static final Material[] swords = new Material[]{Material.DIAMOND_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.NETHERITE_SWORD, Material.WOODEN_SWORD};

    public double getChance() {return 0.0;} // return chance that enchantment is applied when enchanting
    public String getName() {return "";} // return plain name without level
    public int getMaxLevel() {return 0;} // return max enchant level
    public void onAttack(EntityDamageByEntityEvent event, int level) {}

    public boolean appliesOn(ItemStack itemStack) {
        return itemStack.getType() == Material.BOOK;
    }

    public static String intToRomanLetters(int i) {
        switch (i) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            default:
                return "";
        }
    }

    public static int romanLettersToInt(String s) {
        switch (s) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            default:
                return 0;
        }
    }

    public static boolean isSword(ItemStack itemStack) {
        for (Material m : swords) {
            if (itemStack.getType() == m) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasEnchantment(ItemStack itemStack, EnchantmentBase enchantment) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && itemMeta.hasLore()) {
            List<String> lore = itemMeta.getLore();
            for (String s : lore) {
                s = ChatColor.stripColor(s);
                if (s.split(" ")[0].equals(enchantment.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getEnchantmentLevel(ItemStack itemStack, EnchantmentBase enchantment) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && itemMeta.hasLore()) {
            List<String> lore = itemMeta.getLore();
            for (String s : lore) {
                s = ChatColor.stripColor(s);
                String[] splitted = s.split(" ");
                if (splitted[0].equals(enchantment.getName())) {
                    return EnchantmentBase.romanLettersToInt(splitted[1]);
                }
            }
        }
        return 0;
    }
}
