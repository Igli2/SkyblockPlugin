package skyblock.registries;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import skyblock.SkyblockMain;
import skyblock.utils.Ingredient;
import skyblock.utils.ShapedRecipe;

import java.util.*;

public class RecipeRegistry {
    public static ArrayList<ShapedRecipe> recipes = new ArrayList<>();
    public static ArrayList<skyblock.utils.ShapelessRecipe> shapelessRecipes = new ArrayList<>();

    public static void addShapedRecipe(List<Ingredient> ingredients, ItemStack result, String[] shape) {
        ShapedRecipe recipe = new ShapedRecipe(ingredients, result, shape);
        RecipeRegistry.recipes.add(recipe);
    }

    public static void addShapelessRecipe(List<ItemStack> ingredients, ItemStack result) {
        skyblock.utils.ShapelessRecipe recipe = new skyblock.utils.ShapelessRecipe(ingredients, result);
        RecipeRegistry.shapelessRecipes.add(recipe);
    }

    public static void registerCustomRecipes() {
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GEODE), 'G'), new Ingredient(Material.STICK, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ARCHEOLOGISTS_PICKAXE),
                new String[]{"GGG", " S ", " S "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.SUGAR, 64, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE),
                new String[]{"SSS", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_HELMET),
                new String[]{"SSS", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_CHESTPLATE),
                new String[]{"S S", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_LEGGINGS),
                new String[]{"SSS", "S S", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_BOOTS),
                new String[]{"S S", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STONE, 64, 'S'), new Ingredient(Material.GOLD_INGOT, 64, 'G'), new Ingredient(Material.LAPIS_LAZULI, 64, 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHINY_PEBBLE),
                new String[]{"SLS", "LGL", "SLS"});
    }

    public static void registerVanillaRecipes() {
        // add vanilla recipes
        Iterator<org.bukkit.inventory.Recipe> recipeIterator = Bukkit.recipeIterator();
        while (recipeIterator.hasNext()) {
            org.bukkit.inventory.Recipe recipe = recipeIterator.next();
            if (recipe instanceof org.bukkit.inventory.ShapedRecipe) {
                org.bukkit.inventory.ShapedRecipe shapedRecipe = (org.bukkit.inventory.ShapedRecipe) recipe;
                List<Ingredient> ingredients = new ArrayList<>();
                String[] shape = shapedRecipe.getShape();

                // convert ingredients
                Map<Character, ItemStack> ingredientMap = shapedRecipe.getIngredientMap();
                for (char c : ingredientMap.keySet()) {
                    if (ingredientMap.get(c) != null) {
                        Ingredient newIngredient = new Ingredient(ingredientMap.get(c), c);
                        ingredients.add(newIngredient);
                    } else {
                        for (String line : shape) {
                            for (char key : line.toCharArray()) {
                                if (key == c) {
                                    line = line.replace(key, ' ');
                                }
                            }
                        }
                    }
                }

                RecipeRegistry.addShapedRecipe(ingredients, recipe.getResult(), shape);
            } else if (recipe instanceof ShapelessRecipe) {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
                RecipeRegistry.addShapelessRecipe(shapelessRecipe.getIngredientList(), shapelessRecipe.getResult());
            }
        }
    }
}
