package skyblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftingTable {
    ItemStack[] matrix = new ItemStack[9]; // slot 10, 11, 12, 19, 20, 21, 28, 29, 30
    ItemStack result = new ItemStack(Material.AIR); // slot 23
    public Inventory inventory = Bukkit.createInventory(null, 45, "Crafting Table");
    public static final int[] GLASS_PANES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 22, 24, 25, 26, 27, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};

    public CraftingTable() {
        for (int i : CraftingTable.GLASS_PANES) {
            this.inventory.setItem(i, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public static void onInventoryClosed() {
        // TODO: drop items from matrix
    }
}
