package skyblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;
import skyblock.listeners.EnchantItemListener;

public class Anvil {
    public static final int ITEM_SLOT = 11;
    public static final int BOOK_SLOT = 13;
    public static final int RESULT = 15;
    public static final int[] GLASS_PANES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};

    public static Inventory getInventory() {
        // create new inventory
        Inventory inventory = Bukkit.createInventory(null, 27, "Anvil");
        for (int i : Anvil.GLASS_PANES) {
            inventory.setItem(i, CraftingTable.PLACEHOLDER);
        }
        inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
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

    @SuppressWarnings("ConstantConditions")
    public static void onCraft(Inventory inventory, HumanEntity humanEntity, boolean isShiftClick) {
        ItemStack result = inventory.getItem(Anvil.RESULT);
        if (result != null && !result.getItemMeta().getDisplayName().equals(CraftingTable.INVALID.getItemMeta().getDisplayName())) {
            if (isShiftClick) {
                humanEntity.getInventory().addItem(result);
                removeInput(inventory);
            } else {
                if (humanEntity.getItemOnCursor().getType() == Material.AIR) {
                    humanEntity.setItemOnCursor(result);
                    removeInput(inventory);
                }
            }
        }
    }

    public static void removeInput(Inventory inventory) {
        inventory.setItem(Anvil.ITEM_SLOT, new ItemStack(Material.AIR));
        inventory.setItem(Anvil.BOOK_SLOT, new ItemStack(Material.AIR));
    }

    public static void updateContents(Inventory inventory) {
        ItemStack item = inventory.getItem(Anvil.ITEM_SLOT);
        ItemStack book = inventory.getItem(Anvil.BOOK_SLOT);
        if (book == null || book.getType() != Material.ENCHANTED_BOOK) {
            inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
            return;
        }

        if (item != null && EnchantmentBase.isEnchantable(item)) {
            // handle item + book
            Tuple<ItemStack, Integer> result = combineItemAndBook(item, book);
            if (result.y != 0) {
                inventory.setItem(Anvil.RESULT, result.x);
            } else {
                inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
            }
        } else if (item != null && item.getType() == Material.ENCHANTED_BOOK) {
            // handle book + book
            Tuple<ItemStack, Integer> result = combineBookAndBook(item, book);
            if (result.y != 0) {
                inventory.setItem(Anvil.RESULT, result.x);
            } else {
                inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
            }
        } else {
            inventory.setItem(Anvil.RESULT, CraftingTable.INVALID);
        }
    }

    public static Tuple<ItemStack, Integer> combineBookAndBook(ItemStack item, ItemStack book) {
        ItemStack result = item.clone();
        EnchantmentStorageMeta resultMeta = (EnchantmentStorageMeta) result.getItemMeta();
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
        if (bookMeta == null || resultMeta == null) {
            return new Tuple<>(item, 0);
        }

        int applied = 0;
        // apply vanilla enchantments
        for (Enchantment e : bookMeta.getStoredEnchants().keySet()) {
            int level = bookMeta.getStoredEnchantLevel(e);
            try {
                if (resultMeta.getStoredEnchantLevel(e) == level) { // combine enchantment levels
                    if (resultMeta.addStoredEnchant(e, level + 1, false)) {
                        applied += 1;
                    }
                } else if (resultMeta.getStoredEnchantLevel(e) < level) { // overwrite enchantment level
                    if (resultMeta.addStoredEnchant(e, level, false)) {
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
                    applied += 1;
                } else if (EnchantmentBase.getEnchantmentLevel(item, e) < level && e.getMaxLevel() >= level) {
                    EnchantItemListener.removeEnchantmentLore(result, e);
                    EnchantItemListener.insertEnchantmentLore(result, e, level);
                    applied += 1;
                }
            }
        }

        return new Tuple<>(result, applied);
    }

    public static Tuple<ItemStack, Integer> combineItemAndBook(ItemStack item, ItemStack book) {
        ItemStack result = item.clone();
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) book.getItemMeta();
        if (bookMeta == null) {
            return new Tuple<>(item, 0);
        }

        int applied = 0;
        // apply vanilla enchantments
        for (Enchantment e : bookMeta.getStoredEnchants().keySet()) {
            int level = bookMeta.getStoredEnchantLevel(e);
            try {
                if (item.getEnchantmentLevel(e) == level) { // combine enchantment levels
                    result.addEnchantment(e, level + 1);
                    applied += 1;
                } else if (item.getEnchantmentLevel(e) < level) { // overwrite enchantment level
                    result.addEnchantment(e, level);
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
                    applied += 1;
                } else if (EnchantmentBase.getEnchantmentLevel(item, e) < level && e.getMaxLevel() >= level) {
                    EnchantItemListener.removeEnchantmentLore(result, e);
                    EnchantItemListener.insertEnchantmentLore(result, e, level);
                    applied += 1;
                }
            }
        }

        return new Tuple<>(result, applied);
    }

    public static class Tuple<X, Y> {
        public final X x;
        public final Y y;

        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
}
