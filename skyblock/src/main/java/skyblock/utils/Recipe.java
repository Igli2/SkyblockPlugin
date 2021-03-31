package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import skyblock.registries.ItemRegistry;

import javax.annotation.Nonnull;
import java.util.List;

public class Recipe {
    private final List<Ingredient> ingredients;
    private final char[] matrix = new char[9];
    private final ItemStack result;

    public Recipe(List<Ingredient> ingredients, ItemStack result, String[] shape) {
        this.ingredients = ingredients;
        this.result = result;
        int c = 0;
        for (String s : shape) {
            matrix[c * 3] = s.charAt(0);
            matrix[c * 3 + 1] = s.charAt(1);
            matrix[c * 3 + 2] = s.charAt(2);
            c += 1;
        }
    }

    public boolean equals(ItemStack[] matrixOther) {
        for (int i = 0; i < 9; i++) {
            ItemStack itemStackOther = matrixOther[i];
            ItemStack itemStack = new ItemStack(Material.AIR);
            for (Ingredient ingredient : this.ingredients) {
                if (ingredient.getKey() == this.matrix[i]) {
                    itemStack = ingredient.getItem();
                }
            }

            if (!ItemRegistry.isItemStackEqual(itemStack, itemStackOther)) {
                return false;
            }
            // check stack size!
            if (itemStack != null && itemStack.getType() != Material.AIR && itemStackOther != null && itemStackOther.getType() != Material.AIR) {
                int amount = itemStack.getAmount();
                int amountOther = itemStackOther.getAmount();
                if (amountOther < amount) {
                    return false;
                }
            }
        }
        return true;
    }

    public ItemStack getResult() {
        return result;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public char[] getMatrix() {
        return matrix;
    }
}
