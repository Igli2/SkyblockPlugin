package skyblock.registries;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import skyblock.SkyblockMain;
import skyblock.utils.Ingredient;
import skyblock.utils.Recipe;

import java.util.*;

public class RecipeRegistry {
    public static ArrayList<Recipe> recipes = new ArrayList<>();

    public static void addShapedRecipe(List<Ingredient> ingredients, ItemStack result, String[] shape) {
        Recipe recipe = new Recipe(ingredients, result, shape);
        RecipeRegistry.recipes.add(recipe);
    }

    public static void registerCustomRecipes() {
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.GEODE), 'G'), new Ingredient(Material.STICK, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.ARCHEOLOGISTS_PICKAXE),
                new String[]{"GGG", " S ", " S "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.SUGAR, 64, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SUGAR_CUBE),
                new String[]{"SSS", "SSS", "SSS"});
    }

    public static void registerVanillaRecipes() {
        //TODO: add vanilla recipes
        Iterator<org.bukkit.inventory.Recipe> recipeIterator = Bukkit.recipeIterator();
        while (recipeIterator.hasNext()) {
            org.bukkit.inventory.Recipe recipe = recipeIterator.next();
            if (recipe instanceof ShapedRecipe) {
                List<Ingredient> ingredients = new ArrayList<>();

                // convert shape
                String[] oldShape = ((ShapedRecipe) recipe).getShape();
                String[] newShape = new String[]{"   ", "   ", "   "};
                int index = 0;
                for (String s : oldShape) {
                    while (s.length() < 3) {
                        s += " ";
                    }
                    newShape[index] = s;
                    index += 1;
                }

                // convert ingredients
                Map<Character, ItemStack> ingredientMap = ((ShapedRecipe) recipe).getIngredientMap();
                for (char c : ingredientMap.keySet()) {
                    if (ingredientMap.get(c) != null) {
                        Ingredient newIngredient = new Ingredient(ingredientMap.get(c), c);
                        ingredients.add(newIngredient);
                    } else {
                        for (String line : newShape) {
                            for (int i = 0; i < 3; i++) {
                                char key = line.charAt(i);
                                if (key == c) {
                                    line = line.replace(key, ' ');
                                }
                            }
                        }
                    }
                }

                RecipeRegistry.addShapedRecipe(ingredients, recipe.getResult(), newShape);
            }
        }
    }
}
