package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipe implements Recipe {
    private final List<ItemStack> ingredients;
    private final ItemStack result;

    public ShapelessRecipe(List<ItemStack> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public boolean equals(ItemStack[][] matrixOther) {
        // check item stack amount
        int itemStackCount = 0;
        List<ItemStack> availableItems = new ArrayList<>();
        for (ItemStack[] row : matrixOther) {
            for (ItemStack itemStack : row) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    itemStackCount += 1;
                    availableItems.add(itemStack);
                }
            }
        }
        if (itemStackCount != this.ingredients.size()) {
            return false;
        }
        // check item stack type & amount
        for (ItemStack required : this.ingredients) {
            // if not in availableItems: return false
            for (ItemStack available : availableItems) {
                if (ItemRegistry.isItemStackEqual(available, required) && isItem(available) && isItem(required)) {
                    // check amount
                    if (available.getAmount() >= required.getAmount()) {
                        availableItems.remove(available);
                        break;
                    }
                }
            }
        }
        return availableItems.size() == 0;
    }

    public static boolean isItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR;
    }

    public ItemStack getResult() {
        return result;
    }

    public List<ItemStack> getIngredients() {
        return ingredients;
    }
}
