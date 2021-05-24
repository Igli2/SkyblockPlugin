package skyblock.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class EnchantmentBase {
    public static final Material[] swords = new Material[]{Material.DIAMOND_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.NETHERITE_SWORD, Material.WOODEN_SWORD};
    public static ArrayList<ItemStack> customSwords = new ArrayList<>();
    public static final Material[] pickaxes = new Material[]{Material.DIAMOND_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.NETHERITE_PICKAXE, Material.WOODEN_PICKAXE};
    public static ArrayList<ItemStack> customPickaxes = new ArrayList<>();
    public static final Material[] axes = new Material[]{Material.DIAMOND_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.NETHERITE_AXE, Material.WOODEN_AXE};
    public static ArrayList<ItemStack> customAxes = new ArrayList<>();
    public static final Material[] shovels = new Material[]{Material.DIAMOND_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.NETHERITE_SHOVEL, Material.WOODEN_SHOVEL};
    public static ArrayList<ItemStack> customShovels = new ArrayList<>();
    public static final Material[] hoes = new Material[]{Material.DIAMOND_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.NETHERITE_HOE, Material.WOODEN_HOE};
    public static ArrayList<ItemStack> customHoes = new ArrayList<>();
    public static ArrayList<ItemStack> customBows = new ArrayList<>();
    public static ArrayList<ItemStack> customFishingRods = new ArrayList<>();
    public static final Material[] helmets = new Material[]{Material.DIAMOND_HELMET, Material.LEATHER_HELMET, Material.IRON_HELMET, Material.GOLDEN_HELMET, Material.NETHERITE_HELMET, Material.CHAINMAIL_HELMET, Material.TURTLE_HELMET};
    public static ArrayList<ItemStack> customHelmets = new ArrayList<>();
    public static final Material[] chestplates = new Material[]{Material.DIAMOND_CHESTPLATE, Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLDEN_CHESTPLATE, Material.NETHERITE_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE};
    public static ArrayList<ItemStack> customChestplates = new ArrayList<>();
    public static final Material[] leggings = new Material[]{Material.DIAMOND_LEGGINGS, Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.NETHERITE_LEGGINGS, Material.CHAINMAIL_LEGGINGS};
    public static ArrayList<ItemStack> customLeggings = new ArrayList<>();
    public static final Material[] boots = new Material[]{Material.DIAMOND_BOOTS, Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.GOLDEN_BOOTS, Material.NETHERITE_BOOTS, Material.CHAINMAIL_BOOTS};
    public static ArrayList<ItemStack> customBoots = new ArrayList<>();
    public static ArrayList<ItemStack> customCrossbows = new ArrayList<>();
    public static ArrayList<ItemStack> customTridents = new ArrayList<>();

    public double getChance() {return 0.0;} // return chance that enchantment is applied when enchanting
    public String getName() {return "";} // return plain name without level
    public int getMaxLevel() {return 0;} // return max enchant level
    public void onAttack(EntityDamageByEntityEvent event, int level) {}
    public void onKill(EntityDeathEvent event, int level) {}
    public Collection<ItemStack> onBlockBreak(Collection<ItemStack> drops, BlockBreakEvent event, int level) { return drops; }

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

    public static boolean isEnchantable(ItemStack i) {
        return isSword(i) || isHelmet(i) || isChestplate(i) || isLeggings(i) || isBoots(i) || isPickaxe(i) || isAxe(i) || isShovel(i) || isHoe(i) || isBow(i) || isCrossbow(i) || isFishingRod(i) || isTrident(i);
    }

    public static boolean isSword(ItemStack itemStack) {
        for (Material m : swords) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customSwords) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHelmet(ItemStack itemStack) {
        for (Material m : helmets) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customHelmets) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChestplate(ItemStack itemStack) {
        for (Material m : chestplates) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customChestplates) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLeggings(ItemStack itemStack) {
        for (Material m : leggings) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customLeggings) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBoots(ItemStack itemStack) {
        for (Material m : boots) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customBoots) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPickaxe(ItemStack itemStack) {
        for (Material m : pickaxes) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customPickaxes) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAxe(ItemStack itemStack) {
        for (Material m : axes) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customAxes) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isShovel(ItemStack itemStack) {
        for (Material m : shovels) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customShovels) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHoe(ItemStack itemStack) {
        for (Material m : hoes) {
            if (ItemRegistry.isItemStackEqual(itemStack, new ItemStack(m))) {
                return true;
            }
        }
        for (ItemStack s : customHoes) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBow(ItemStack itemStack) {
        for (ItemStack s : customBows) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return ItemRegistry.isItemStackEqual(itemStack, new ItemStack(Material.BOW));
    }

    public static boolean isCrossbow(ItemStack itemStack) {
        for (ItemStack s : customCrossbows) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return ItemRegistry.isItemStackEqual(itemStack, new ItemStack(Material.CROSSBOW));
    }

    public static boolean isFishingRod(ItemStack itemStack) {
        for (ItemStack s : customFishingRods) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return ItemRegistry.isItemStackEqual(itemStack, new ItemStack(Material.FISHING_ROD));
    }

    public static boolean isTrident(ItemStack itemStack) {
        for (ItemStack s : customTridents) {
            if (ItemRegistry.isItemStackEqual(itemStack, s)) {
                return true;
            }
        }
        return ItemRegistry.isItemStackEqual(itemStack, new ItemStack(Material.TRIDENT));
    }

    public static boolean hasEnchantment(ItemStack itemStack, EnchantmentBase enchantment) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && itemMeta.getLore() != null) {
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
        if (itemMeta != null && itemMeta.getLore() != null) {
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
