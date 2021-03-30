package skyblock.registries;

import org.bukkit.inventory.ItemStack;
import skyblock.utils.Ingredient;
import skyblock.utils.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry {
    public static ArrayList<Recipe> recipes = new ArrayList<>();

    public static void addShapedRecipe(List<Ingredient> ingredients, ItemStack result, String[] shape) {
        Recipe recipe = new Recipe(ingredients, result, shape);
        RecipeRegistry.recipes.add(recipe);
    }

    /*public static void addShapelessRecipe(JavaPlugin plugin, String key, Material[] ingredients, ItemStack result) {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, key), result);

        for(Material ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }

        RecipeRegistry.recipes.add(recipe);
        plugin.getServer().addRecipe(recipe);
    }*/
}
