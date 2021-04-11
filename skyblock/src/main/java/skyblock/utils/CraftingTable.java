package skyblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skyblock.registries.ItemRegistry;
import skyblock.registries.RecipeRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        itemMeta = INVALID.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName("Invalid Recipe");
        }
        INVALID.setItemMeta(itemMeta);
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
                ItemStack drop = inventory.getItem(slot);
                if (drop != null) {
                    player.getWorld().dropItem(player.getLocation(), drop);
                }
            }
        }
    }

    public static void onCraft(Inventory inventory, HumanEntity humanEntity, boolean isShiftClick) {
        // get recipe
        Recipe recipe = CraftingTable.getRecipe(CraftingTable.getMatrix(inventory));
        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
            ItemStack recipeResult = shapedRecipe.getResult();
            if (recipeResult.getType().getMaxDurability() > 0) {
                ItemRegistry.makeUnbreakable(recipeResult);
            }
            // give crafting result to player
            if (isShiftClick) {
                boolean sameRecipe = true;
                while (sameRecipe) {
                    HashMap<Integer, ItemStack> excess = humanEntity.getInventory().addItem(shapedRecipe.getResult());
                    if (excess.size() == 0) { // check if inventory is full
                        CraftingTable.removeRecipeFromMatrix(CraftingTable.getMatrix(inventory), shapedRecipe);
                    } else {
                        break;
                    }

                    // check if formed recipe is still the same
                    sameRecipe = false;
                    Recipe newRecipe = CraftingTable.getRecipe(CraftingTable.getMatrix(inventory));
                    if (newRecipe instanceof ShapedRecipe) {
                        ItemStack result = shapedRecipe.getResult();
                        ItemStack newResult = newRecipe.getResult();
                        if (ItemRegistry.isItemStackEqual(result, newResult)) {
                            sameRecipe = true;
                        }
                    }
                }
            } else {
                if (humanEntity.getItemOnCursor().getType() == Material.AIR) { // non-stackable items
                    humanEntity.setItemOnCursor(shapedRecipe.getResult());
                    CraftingTable.removeRecipeFromMatrix(CraftingTable.getMatrix(inventory), shapedRecipe);
                } else if (ItemRegistry.isItemStackEqual(humanEntity.getItemOnCursor(), shapedRecipe.getResult()) &&
                        humanEntity.getItemOnCursor().getAmount() + shapedRecipe.getResult().getAmount() <= shapedRecipe.getResult().getMaxStackSize()) { // stackable items with non-full item stack
                    ItemStack onCursor = humanEntity.getItemOnCursor();
                    onCursor.setAmount(onCursor.getAmount() + shapedRecipe.getResult().getAmount());
                    humanEntity.setItemOnCursor(onCursor);
                    CraftingTable.removeRecipeFromMatrix(CraftingTable.getMatrix(inventory), shapedRecipe);
                }
            }
        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
            ItemStack recipeResult = shapelessRecipe.getResult();
            if (recipeResult.getType().getMaxDurability() > 0) {
                ItemRegistry.makeUnbreakable(recipeResult);
            }
            // give crafting result to player
            if (isShiftClick) {
                boolean sameRecipe = true;
                while (sameRecipe) {
                    HashMap<Integer, ItemStack> excess = humanEntity.getInventory().addItem(shapelessRecipe.getResult());
                    if (excess.size() == 0) { // check if inventory is full
                        CraftingTable.removeRecipeFromMatrix(CraftingTable.getMatrix(inventory), shapelessRecipe);
                    } else {
                        break;
                    }

                    // check if formed recipe is still the same
                    sameRecipe = false;
                    Recipe newRecipe = CraftingTable.getRecipe(CraftingTable.getMatrix(inventory));
                    if (newRecipe instanceof ShapelessRecipe) {
                        ItemStack result = shapelessRecipe.getResult();
                        ItemStack newResult = newRecipe.getResult();
                        if (ItemRegistry.isItemStackEqual(result, newResult)) {
                            sameRecipe = true;
                        }
                    }
                }
            } else {
                if (humanEntity.getItemOnCursor().getType() == Material.AIR) { // non-stackable items
                    humanEntity.setItemOnCursor(shapelessRecipe.getResult());
                    CraftingTable.removeRecipeFromMatrix(CraftingTable.getMatrix(inventory), shapelessRecipe);
                } else if (ItemRegistry.isItemStackEqual(humanEntity.getItemOnCursor(), shapelessRecipe.getResult()) &&
                        humanEntity.getItemOnCursor().getAmount() + shapelessRecipe.getResult().getAmount() <= shapelessRecipe.getResult().getMaxStackSize()) { // stackable items with non-full item stack
                    ItemStack onCursor = humanEntity.getItemOnCursor();
                    onCursor.setAmount(onCursor.getAmount() + shapelessRecipe.getResult().getAmount());
                    humanEntity.setItemOnCursor(onCursor);
                    CraftingTable.removeRecipeFromMatrix(CraftingTable.getMatrix(inventory), shapelessRecipe);
                }
            }
        }
    }

    private static void removeRecipeFromMatrix(ItemStack[][] matrix, ShapelessRecipe recipe) {
        List<ItemStack> ingredients = recipe.getIngredients();
        for (ItemStack ingredient : ingredients) {
            // find itemstack in matrix and reduce amount
            label: for (ItemStack[] itemStacks : matrix) {
                for (ItemStack itemStack : itemStacks) {
                    if (ItemRegistry.isItemStackEqual(itemStack, ingredient) && ShapelessRecipe.isItem(itemStack)) {
                        itemStack.setAmount(itemStack.getAmount() - ingredient.getAmount());
                        break label;
                    }
                }
            }
        }
    }

    private static void removeRecipeFromMatrix(ItemStack[][] matrix, ShapedRecipe recipe) {
        // non-trimmed matrix
        ItemStack[][] trimmedMatrix = CraftingTable.trimMatrix(matrix);
        for (int row = 0; row <= (3 - trimmedMatrix.length); row++) {
            for (int col = 0; col <= (3 - trimmedMatrix[0].length); col++) {
                // check recipe
                boolean match = true;
                label:
                for (int i = 0; i < recipe.getShape().length; i++) {
                    for (int j = 0; j < recipe.getShape()[0].length(); j++) {
                        char c = recipe.getShape()[i].charAt(j);
                        Ingredient ingredient = recipe.getIngredient(c);
                        if (ingredient != null) {
                            if (!ItemRegistry.isItemStackEqual(matrix[row + i][col + j], ingredient.getItem())) {
                                match = false;
                                break label;
                            }
                        }
                    }
                }

                if (match) {
                    for (int i = 0; i < recipe.getShape().length; i++) {
                        for (int j = 0; j < recipe.getShape()[0].length(); j++) {
                            char c = recipe.getShape()[i].charAt(j);
                            Ingredient ingredient = recipe.getIngredient(c);
                            if (ingredient != null) {
                                matrix[row + i][col + j].setAmount(matrix[row + i][col + j].getAmount() - ingredient.getItem().getAmount());
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    private static ItemStack[][] getMatrix(Inventory inventory) {
        ItemStack[][] matrix = new ItemStack[3][3];
        int counter = 0;
        for (int slot : CraftingTable.MATRIX) {
            matrix[counter / 3][counter % 3] = inventory.getItem(slot);
            counter += 1;
        }
        return matrix;
    }

    public static void updateContents(Inventory inventory) {
        ItemStack[][] matrix = CraftingTable.getMatrix(inventory);

        // set recipe result
        Recipe recipe = CraftingTable.getRecipe(matrix);
        if (recipe != null) {
            ItemStack recipeResult = recipe.getResult();
            if (recipeResult.getType().getMaxDurability() > 0) {
                ItemRegistry.makeUnbreakable(recipeResult);
            }
            inventory.setItem(CraftingTable.RESULT, recipeResult);
        } else {
            inventory.setItem(CraftingTable.RESULT, CraftingTable.INVALID);
        }
    }

    private static Recipe getRecipe(ItemStack[][] matrix) {
        ItemStack[][] trimmedMatrix = CraftingTable.trimMatrix(matrix);
        for (ShapedRecipe recipe : RecipeRegistry.recipes) {
            if (recipe.equals(trimmedMatrix)) {
                return recipe;
            }
        }
        for (ShapelessRecipe recipe : RecipeRegistry.shapelessRecipes) {
            if (recipe.equals(matrix)) {
                return recipe;
            }
        }
        return null;
    }

    public static ItemStack[][] trimMatrix(ItemStack[][] matrix) {
        // columns
        int width = matrix[0].length;
        int height = matrix.length;

        boolean[] toTrim = new boolean[width];
        Arrays.fill(toTrim, true);
        for (int i = 0; i < width; i++) {
            for (ItemStack[] itemStacks : matrix) {
                if (itemStacks[i] != null && itemStacks[i].getType() != Material.AIR) {
                    toTrim[i] = false;
                }
            }
        }

        int widthNew = 0;
        for (boolean b : toTrim) {
            if (!b) {
                widthNew++;
            }
        }
        if (!toTrim[0] && !toTrim[2]) {
            widthNew = 3;
            toTrim[1] = false;
        }

        ItemStack[][] matrixNew = new ItemStack[height][widthNew];
        int removed = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!toTrim[i]) {
                    ItemStack itemStack = matrix[j][i];
                    matrixNew[j][i - removed] = itemStack;
                }
            }
            if (toTrim[i]) {
                removed++;
            }
        }

        // rows
        toTrim = new boolean[height];
        Arrays.fill(toTrim, true);
        for (int i = 0; i < matrixNew.length; i++) {
            for (ItemStack itemStack : matrixNew[i]) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    toTrim[i] = false;
                }
            }
        }

        int heightNew = 0;
        for (boolean b : toTrim) {
            if (!b) {
                heightNew++;
            }
        }
        if (!toTrim[0] && !toTrim[2]) {
            heightNew = 3;
            toTrim[1] = false;
        }

        matrix = new ItemStack[heightNew][widthNew];
        removed = 0;
        for (int i = 0; i < height; i++) {
            if (!toTrim[i]) {
                matrix[i - removed] = matrixNew[i];
            } else {
                removed++;
            }
        }

        return matrix;
    }

    @SuppressWarnings("unused")
    public static String arrayArrayToString(ItemStack[][] array) { // debug method
        StringBuilder s = new StringBuilder();
        for (ItemStack[] itemStacks : array) {
            for (ItemStack itemStack : itemStacks) {
                if (itemStack != null) {
                    s.append(itemStack.getType().toString()).append(",");
                } else {
                    s.append("   ,");
                }
            }
            s.append("\n");
        }
        s.append("------------");
        return s.toString();
    }
}
