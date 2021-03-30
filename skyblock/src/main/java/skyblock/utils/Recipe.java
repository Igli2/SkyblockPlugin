package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
            //get ingredient for key at position i in matrix
            ItemStack itemStack = new ItemStack(Material.AIR);
            for (Ingredient ingredient : this.ingredients) {
                if (ingredient.getKey() == this.matrix[i]) {
                    itemStack = ingredient.getItem();
                }
            }
            if (itemStackOther == null && itemStack.getType() != Material.AIR || itemStackOther != null && itemStack.getType() == Material.AIR) {
                //System.out.println(i + " not null and air");
                return false;
            }
            if (itemStackOther != null && itemStackOther.hasItemMeta() && itemStack.hasItemMeta()) {
                if (itemStackOther.getItemMeta().hasDisplayName() && itemStack.getItemMeta().hasDisplayName()) {
                    if (!itemStackOther.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())) {
                        //System.out.println(i + " no equal display names");
                        return false;
                    }
                } else {
                    if (itemStackOther.getType() != itemStack.getType()) {
                        //System.out.println(i + " no equal materials");
                        return false;
                    }
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
