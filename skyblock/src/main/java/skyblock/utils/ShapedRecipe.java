package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;
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
                    List<ItemStack> itemStackThis = new ArrayList<>();
                    for (Ingredient ingredient : this.ingredients) {
                        if (ingredient.getKey() == this.shape[row].charAt(col)) {
                            itemStackThis = ingredient.getItem();
                        }
                    }

                    boolean check = false;
                    for (ItemStack itemStack : itemStackThis) {
                        if (ItemRegistry.isItemStackEqual(itemStack, itemStackOther)) {
                            check = true;
                            break;
                        }
                    }
                    if (!check) {
                        return false;
                    }

                    // check stack size
                    if (itemStackThis.get(0).getType() != Material.AIR && itemStackOther != null && itemStackOther.getType() != Material.AIR) {
                        int amount = itemStackThis.get(0).getAmount();
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
