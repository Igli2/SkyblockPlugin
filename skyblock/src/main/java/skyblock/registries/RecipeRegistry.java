package skyblock.registries;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import skyblock.utils.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry {

    public static void addShapedRecipe(JavaPlugin plugin, String key, List<Ingredient> ingredients, ItemStack result, String[] shape) {
        System.out.println(plugin + "!!!");
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, key), result);

        recipe.shape(shape[0], shape[1], shape[2]);

        for(Ingredient ingredient : ingredients) {
            recipe.setIngredient(ingredient.getKey(), ingredient.getMaterial());
        }

        plugin.getServer().addRecipe(recipe);
    }

    public static void addShapelessRecipe(JavaPlugin plugin, String key, Material[] ingredients, ItemStack result) {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, key), result);

        for(Material ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }

        plugin.getServer().addRecipe(recipe);
    }
}
