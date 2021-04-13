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
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(Material.SUGAR, 64, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE),
                new String[]{"SSS", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_HELMET),
                new String[]{"SSS", "S S"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_CHESTPLATE),
                new String[]{"S S", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_LEGGINGS),
                new String[]{"SSS", "S S", "S S"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_BOOTS),
                new String[]{"S S", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STONE, 64, 'S'), new Ingredient(Material.GOLD_INGOT, 64, 'G'), new Ingredient(Material.LAPIS_LAZULI, 64, 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHINY_PEBBLE),
                new String[]{"SLS", "LGL", "SLS"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHINY_PEBBLE), 'P'), new Ingredient(Material.STICK, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GUARDS_DEFENDER),
                new String[]{"P", "P", "S"});
        RecipeRegistry.addShapelessRecipe(Arrays.asList(new ItemStack(Material.OAK_LOG, 64), new ItemStack(Material.BIRCH_LOG, 64), new ItemStack(Material.SPRUCE_LOG, 64), new ItemStack(Material.DARK_OAK_LOG, 64), new ItemStack(Material.JUNGLE_LOG, 64), new ItemStack(Material.ACACIA_LOG, 64)),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOSSILIZED_LOG));
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STICK, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOSSILIZED_LOG), 5, 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREE_CAPITATOR),
                new String[]{"LL", "LS", " S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STICK, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOSSILIZED_LOG), 5, 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREE_CAPITATOR),
                new String[]{"LL", "SL", "S "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.IRON_BLOCK, 16, 'I'), new Ingredient(Material.COAL_BLOCK, 32, 'C'), new Ingredient(Material.BASALT, 64, 'B')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT),
                new String[]{"BCB", "CIC", "BCB"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.YELLOW_WOOL, 64, 'W'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOSSILIZED_LOG), 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPOOL_OF_THREAD),
                new String[]{"WWW", "WLW", "WWW"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STICK, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPOOL_OF_THREAD), 'T')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GRAPPLING_HOOK),
                new String[]{"  S", " ST", "S T"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_HELMET),
                new String[]{"SSS", "S S"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_CHESTPLATE),
                new String[]{"S S", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_LEGGINGS),
                new String[]{"SSS", "S S", "S S"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_BOOTS),
                new String[]{"S S", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.EGG, 'E'), new Ingredient(Material.NETHER_BRICK, 16, 'N')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOW_WARRIOR_SPAWN_EGG),
                new String[]{"NNN", "NEN", "NNN"});
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
