package skyblock.registries;

import org.bukkit.inventory.ItemStack;
import skyblock.utils.Ingredient;
import skyblock.utils.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry {
    public static ArrayList<Recipe> recipes = new ArrayList<>();

    //add vanilla recipes
    //Bukkit.recipeIterator()

    public static void addShapedRecipe(List<Ingredient> ingredients, ItemStack result, String[] shape) {
        Recipe recipe = new Recipe(ingredients, result, shape);
        RecipeRegistry.recipes.add(recipe);
    }
}
