package skyblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skyblock.registries.RecipeRegistry;

public class CraftingTable {
    public static final int[] MATRIX = new int[]{10, 11, 12, 19, 20, 21, 28, 29, 30};
    public static final int RESULT = 23;
    public static final int[] GLASS_PANES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 22, 24, 25, 26, 27, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};

    public static final ItemStack PLACEHOLDER = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
    public static final ItemStack INVALID = new ItemStack(Material.BARRIER);
    static {
        ItemMeta itemMeta = PLACEHOLDER.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(" ");
        }
        PLACEHOLDER.setItemMeta(itemMeta);
    }

    public static Inventory getInventory() {
        // create new inventory
        Inventory inventory = Bukkit.createInventory(null, 45, "Crafting Table");
        for (int i : CraftingTable.GLASS_PANES) {
            inventory.setItem(i, CraftingTable.PLACEHOLDER);
        }
        inventory.setItem(CraftingTable.RESULT, CraftingTable.INVALID);
        return inventory;
    }

    public static void onInventoryClosed(Inventory inventory, HumanEntity player) {
        // drop items from crafting table inventory after it was closed
        for (int slot : CraftingTable.MATRIX) {
            if (inventory.getItem(slot) != null) {
                player.getWorld().dropItem(player.getLocation(), inventory.getItem(slot));
            }
        }
    }

    public static void onCraft(Inventory inventory) {
        // TODO!!!
    }

    public static void updateContents(Inventory inventory) {
        ItemStack[] matrix = new ItemStack[9];
        int counter = 0;
        for (int slot : CraftingTable.MATRIX) {
            matrix[counter] = inventory.getItem(slot);
            counter += 1;
        }

        // check recipes
        for (Recipe recipe : RecipeRegistry.recipes) {
            if (recipe.equals(matrix)) {
                inventory.setItem(CraftingTable.RESULT, recipe.getResult());
                return;
            }
        }
        // no recipe detected
        inventory.setItem(CraftingTable.RESULT, CraftingTable.INVALID);
    }
}
