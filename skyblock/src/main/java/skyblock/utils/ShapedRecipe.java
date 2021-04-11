package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import skyblock.registries.ItemRegistry;

import java.util.List;

public class ShapedRecipe implements Recipe {
    private final List<Ingredient> ingredients;
    private final String[] shape;
    private final ItemStack result;

    public ShapedRecipe(List<Ingredient> ingredients, ItemStack result, String[] shape) {
        this.ingredients = ingredients;
        this.result = result;
        this.shape = shape;
    }

    public boolean equals(ItemStack[][] matrixOther) {
        // matrix already trimmed here
        if (matrixOther.length == this.shape.length && matrixOther.length > 0 && matrixOther[0].length == this.shape[0].length() && matrixOther[0].length > 0) {
            for (int row = 0; row < matrixOther.length; row++) {
                for (int col = 0; col < matrixOther[0].length; col++) {
                    ItemStack itemStackOther = matrixOther[row][col];
                    ItemStack itemStackThis = new ItemStack(Material.AIR);
                    for (Ingredient ingredient : this.ingredients) {
                        if (ingredient.getKey() == this.shape[row].charAt(col)) {
                            itemStackThis = ingredient.getItem();
                        }
                    }

                    if (!ItemRegistry.isItemStackEqual(itemStackThis, itemStackOther)) {
                        return false;
                    }
                    // check stack size
                    if (itemStackThis != null && itemStackThis.getType() != Material.AIR && itemStackOther != null && itemStackOther.getType() != Material.AIR) {
                        int amount = itemStackThis.getAmount();
                        int amountOther = itemStackOther.getAmount();
                        if (amountOther < amount) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public ItemStack getResult() {
        return result;
    }

    @SuppressWarnings("unused")
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Ingredient getIngredient(char c) {
        for (Ingredient ingredient : this.ingredients) {
            if (ingredient.getKey() == c) {
                return ingredient;
            }
        }
        return null;
    }

    public String[] getShape() {
        return this.shape;
    }
}
