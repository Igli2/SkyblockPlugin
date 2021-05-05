package skyblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;
import skyblock.listeners.EnchantItemListener;

public class Anvil {
    public static final int ITEM_SLOT = 10;
    public static final int BOOK_SLOT = 12;
    public static final int RESULT = 14;
    public static final int EXP = 16;
    public static final int[] GLASS_PANES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    public static final ItemStack expDisplay = new ItemStack(Material.EXPERIENCE_BOTTLE);

    static {
        ItemMeta itemMeta = expDisplay.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName("0");
        }
        expDisplay.setItemMeta(itemMeta);
    }

    public static Inventory getInventory() {
        // create new inventory
        Inventory inventory = Bukkit.createInventory(null, 27, "Anvil");
        for (int i : Anvil.GLASS_PANES) {
            inventory.setItem(i, CraftingTable.PLACEHOLDER);
        }
        inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
        inventory.setItem(Anvil.EXP, expDisplay.clone());
        return inventory;
    }

    public static void onInventoryClosed(Inventory inventory, HumanEntity player) {
        // drop items from anvil inventory after it was closed
        if (inventory.getItem(Anvil.ITEM_SLOT) != null) {
            ItemStack drop = inventory.getItem(Anvil.ITEM_SLOT);
            if (drop != null) {
                player.getWorld().dropItem(player.getLocation(), drop);
            }
        }
        if (inventory.getItem(Anvil.BOOK_SLOT) != null) {
            ItemStack drop = inventory.getItem(Anvil.BOOK_SLOT);
            if (drop != null) {
                player.getWorld().dropItem(player.getLocation(), drop);
            }
        }
    }

    public static void onCraft(Inventory inventory, HumanEntity humanEntity, boolean isShiftClick) {
        ItemStack result = inventory.getItem(Anvil.RESULT);
        Player player = (Player) humanEntity;
        if (result != null && !result.equals(CraftingTable.INVALID)) {
            if (isShiftClick) {
                humanEntity.getInventory().addItem(result);
                removeInput(inventory);
                player.setLevel(player.getLevel() - getExpRequired(inventory));
            } else {
                if (humanEntity.getItemOnCursor().getType() == Material.AIR) {
                    humanEntity.setItemOnCursor(result);
                    removeInput(inventory);
                    player.setLevel(player.getLevel() - getExpRequired(inventory));
                }
            }
        }
    }

    public static void removeInput(Inventory inventory) {
        inventory.setItem(Anvil.ITEM_SLOT, new ItemStack(Material.AIR));
        inventory.setItem(Anvil.BOOK_SLOT, new ItemStack(Material.AIR));
    }

    public static void updateContents(Inventory inventory, HumanEntity player) {
        ItemStack item = inventory.getItem(Anvil.ITEM_SLOT);
        ItemStack book = inventory.getItem(Anvil.BOOK_SLOT);
        if (book == null || book.getType() != Material.ENCHANTED_BOOK) {
            inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
            setExpInfo(inventory, 0);
            return;
        }

        if (item != null && EnchantmentBase.isEnchantable(item)) {
            // handle item + book
            Triple<ItemStack, Integer, Integer> result = combineItemAndBook(item, book);
            setExpInfo(inventory, result.z);
            if (result.y != 0 && result.z <= ((Player) player).getLevel()) {
                inventory.setItem(Anvil.RESULT, result.x);
            } else {
                inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
            }
        } else if (item != null && item.getType() == Material.ENCHANTED_BOOK) {
            // handle book + book
            Triple<ItemStack, Integer, Integer> result = combineBookAndBook(item, book);
            setExpInfo(inventory, result.z);
            if (result.y != 0 && result.z <= ((Player) player).getLevel()) {
                inventory.setItem(Anvil.RESULT, result.x);
            } else {
                inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
            }
        } else {
            inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
            setExpInfo(inventory, 0);
        }
    }

    private static void setExpInfo(Inventory inventory, int exp) {
        ItemStack item = inventory.getItem(Anvil.EXP);
        if (item == null) {item = expDisplay.clone();}

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(String.valueOf(exp));
        }
        item.setItemMeta(itemMeta);

        inventory.setItem(Anvil.EXP, item);
    }

    private static int getExpRequired(Inventory inventory) {
        ItemStack item = inventory.getItem(Anvil.EXP);
        if (item == null) {return 0;}

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            String name = itemMeta.getDisplayName();
            return Integer.parseInt(name);
        }

        return 0;
    }

    public static Triple<ItemStack, Integer, Integer> combineBookAndBook(ItemStack item, ItemStack book) {
        ItemStack result = item.clone();
        EnchantmentStorageMeta resultMeta = (EnchantmentStorageMeta) result.getItemMeta();
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
        if (bookMeta == null || resultMeta == null) {
            return new Triple<>(item, 0, 0);
        }

        int applied = 0;
        int xpNeeded = 0;
        // apply vanilla enchantments
        for (Enchantment e : bookMeta.getStoredEnchants().keySet()) {
            int level = bookMeta.getStoredEnchantLevel(e);
            try {
                if (resultMeta.getStoredEnchantLevel(e) == level) { // combine enchantment levels
                    if (resultMeta.addStoredEnchant(e, level + 1, false)) {
                        xpNeeded += Math.pow(2, level + 1);
                        applied += 1;
                    }
                } else if (resultMeta.getStoredEnchantLevel(e) < level) { // overwrite enchantment level
                    if (resultMeta.addStoredEnchant(e, level, false)) {
                        xpNeeded += Math.pow(2, level);
                        applied += 1;
                    }
                }
            } catch (IllegalArgumentException exception) {
                // System.out.println("cannot apply enchantment");
            }
        }
        result.setItemMeta(resultMeta);
        // apply custom enchantments
        for (EnchantmentBase e : EnchantmentRegistry.enchantments) {
            if (EnchantmentBase.hasEnchantment(book, e)) {
                int level = EnchantmentBase.getEnchantmentLevel(book, e);

                if (EnchantmentBase.getEnchantmentLevel(item, e) == level && e.getMaxLevel() >= (level + 1)) {
                    EnchantItemListener.removeEnchantmentLore(result, e);
                    EnchantItemListener.insertEnchantmentLore(result, e, level + 1);
                    xpNeeded += Math.pow(2, level + 1);
                    applied += 1;
                } else if (EnchantmentBase.getEnchantmentLevel(item, e) < level && e.getMaxLevel() >= level) {
                    EnchantItemListener.removeEnchantmentLore(result, e);
                    EnchantItemListener.insertEnchantmentLore(result, e, level);
                    xpNeeded += Math.pow(2, level);
                    applied += 1;
                }
            }
        }

        return new Triple<>(result, applied, xpNeeded);
    }

    public static Triple<ItemStack, Integer, Integer> combineItemAndBook(ItemStack item, ItemStack book) {
        ItemStack result = item.clone();
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
        if (bookMeta == null) {
            return new Triple<>(item, 0, 0);
        }

        int applied = 0;
        int xpNeeded = 0;
        // apply vanilla enchantments
        for (Enchantment e : bookMeta.getStoredEnchants().keySet()) {
            int level = bookMeta.getStoredEnchantLevel(e);
            try {
                if (item.getEnchantmentLevel(e) == level) { // combine enchantment levels
                    result.addEnchantment(e, level + 1);
                    xpNeeded += Math.pow(2, level + 1);
                    applied += 1;
                } else if (item.getEnchantmentLevel(e) < level) { // overwrite enchantment level
                    result.addEnchantment(e, level);
                    xpNeeded += Math.pow(2, level);
                    applied += 1;
                }
            } catch (IllegalArgumentException exception) {
                // System.out.println("cannot apply enchantment");
            }
        }
        // apply custom enchantments
        for (EnchantmentBase e : EnchantmentRegistry.enchantments) {
            if (EnchantmentBase.hasEnchantment(book, e) && e.appliesOn(result)) {
                int level = EnchantmentBase.getEnchantmentLevel(book, e);

                if (EnchantmentBase.getEnchantmentLevel(item, e) == level && e.getMaxLevel() >= (level + 1)) {
                    EnchantItemListener.removeEnchantmentLore(result, e);
                    EnchantItemListener.insertEnchantmentLore(result, e, level + 1);
                    xpNeeded += Math.pow(2, level + 1);
                    applied += 1;
                } else if (EnchantmentBase.getEnchantmentLevel(item, e) < level && e.getMaxLevel() >= level) {
                    EnchantItemListener.removeEnchantmentLore(result, e);
                    EnchantItemListener.insertEnchantmentLore(result, e, level);
                    xpNeeded += Math.pow(2, level);
                    applied += 1;
                }
            }
        }

        return new Triple<>(result, applied, xpNeeded);
    }

    public static class Triple<X, Y, Z> {
        public final X x;
        public final Y y;
        public final Z z;

        public Triple(X x, Y y, Z z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
