package skyblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skyblock.registries.RecipeRegistry;

import java.util.HashMap;

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

    public static void onCraft(Inventory inventory, HumanEntity humanEntity, boolean isShiftClick) {
        // get recipe
        Recipe recipe = CraftingTable.getRecipe(CraftingTable.getMatrix(inventory));
        if (recipe != null) {
            // give crafting result to player
            if (isShiftClick) {
                boolean sameRecipe = true;
                while (sameRecipe) {
                    HashMap<Integer, ItemStack> excess = humanEntity.getInventory().addItem(recipe.getResult());
                    if (excess.size() == 0) { // check if inventory is full
                        CraftingTable.removeRecipeFromMatrix(CraftingTable.getMatrix(inventory), recipe);
                    }
                    // check if formed recipe is still the same
                    sameRecipe = false;
                    Recipe newRecipe = CraftingTable.getRecipe(CraftingTable.getMatrix(inventory));
                    if (newRecipe != null) {
                        ItemStack newResult = newRecipe.getResult();
                        if (!newResult.getItemMeta().hasDisplayName() &&
                                !recipe.getResult().getItemMeta().hasDisplayName() &&
                                newResult.getType() == recipe.getResult().getType()) {
                            sameRecipe = true;
                        } else if (newResult.getItemMeta().hasDisplayName() &&
                                recipe.getResult().getItemMeta().hasDisplayName() &&
                                newResult.getItemMeta().getDisplayName().equals(recipe.getResult().getItemMeta().getDisplayName())) {
                            sameRecipe = true;
                        }
                    }
                }
            } else {
                if (humanEntity.getItemOnCursor().getType() == Material.AIR) {
                    humanEntity.setItemOnCursor(recipe.getResult());
                    CraftingTable.removeRecipeFromMatrix(CraftingTable.getMatrix(inventory), recipe);
                }
            }
        }
    }

    private static void removeRecipeFromMatrix(ItemStack[] matrix, Recipe recipe) {
        for (int i = 0; i < 9; i++) {
            char c = recipe.getMatrix()[i];
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient.getKey() == c) {
                    ItemStack consumed = ingredient.getItem();
                    matrix[i].setAmount(matrix[i].getAmount() - consumed.getAmount());
                }
            }
        }
    }

    private static ItemStack[] getMatrix(Inventory inventory) {
        ItemStack[] matrix = new ItemStack[9];
        int counter = 0;
        for (int slot : CraftingTable.MATRIX) {
            matrix[counter] = inventory.getItem(slot);
            counter += 1;
        }
        return matrix;
    }

    public static void updateContents(Inventory inventory) {
        ItemStack[] matrix = CraftingTable.getMatrix(inventory);

        // set recipe result
        Recipe recipe = CraftingTable.getRecipe(matrix);
        if (recipe != null) {
            inventory.setItem(CraftingTable.RESULT, recipe.getResult());
        } else {
            inventory.setItem(CraftingTable.RESULT, CraftingTable.INVALID);
        }
    }

    private static Recipe getRecipe(ItemStack[] matrix) {
        for (Recipe recipe : RecipeRegistry.recipes) {
            if (recipe.equals(matrix)) {
                return recipe;
            }
        }
        return null;
    }
}
