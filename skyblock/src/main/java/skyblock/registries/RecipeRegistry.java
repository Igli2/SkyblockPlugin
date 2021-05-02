package skyblock.registries;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import skyblock.SkyblockMain;
import skyblock.utils.Ingredient;
import skyblock.utils.ShapedRecipe;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecipeRegistry {
    public static ArrayList<ShapedRecipe> recipes = new ArrayList<>();
    public static ArrayList<skyblock.utils.ShapelessRecipe> shapelessRecipes = new ArrayList<>();

    public static void addShapedRecipe(List<Ingredient> ingredients, ItemStack result, String[] shape) {
        ShapedRecipe recipe = new ShapedRecipe(ingredients, result, shape);
        RecipeRegistry.recipes.add(recipe);
    }

    public static void addShapelessRecipe(List<List<ItemStack>> ingredients, ItemStack result) {
        skyblock.utils.ShapelessRecipe recipe = new skyblock.utils.ShapelessRecipe(ingredients, result);
        RecipeRegistry.shapelessRecipes.add(recipe);
    }

    public static void registerCustomRecipes() {
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GEODE), 'G'), new Ingredient(Material.STICK, 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ARCHEOLOGISTS_PICKAXE),
                new String[]{"GGG", " S ", " S "});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(Material.SUGAR, 64, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE),
                new String[]{"SSS", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_HELMET),
                new String[]{"SSS", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_CHESTPLATE),
                new String[]{"S S", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_LEGGINGS),
                new String[]{"SSS", "S S", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUGAR_CUBE), 5, 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPEEDY_BOOTS),
                new String[]{"S S", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STONE, 64, 'S'), new Ingredient(Material.GOLD_INGOT, 64, 'G'), new Ingredient(Material.LAPIS_LAZULI, 64, 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHINY_PEBBLE),
                new String[]{"SLS", "LGL", "SLS"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHINY_PEBBLE), 'P'), new Ingredient(Material.STICK, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GUARDS_DEFENDER),
                new String[]{"P", "P", "S"});
        RecipeRegistry.addShapelessRecipe(Arrays.asList(Collections.singletonList(new ItemStack(Material.OAK_LOG, 64)), Collections.singletonList(new ItemStack(Material.BIRCH_LOG, 64)), Collections.singletonList(new ItemStack(Material.SPRUCE_LOG, 64)), Collections.singletonList(new ItemStack(Material.DARK_OAK_LOG, 64)), Collections.singletonList(new ItemStack(Material.JUNGLE_LOG, 64)), Collections.singletonList(new ItemStack(Material.ACACIA_LOG, 64))),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOSSILIZED_LOG));
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STICK, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOSSILIZED_LOG), 5, 'L'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREE_CAPITATOR),
                new String[]{"LL", "LS", " S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STICK, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOSSILIZED_LOG), 5, 'L'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREE_CAPITATOR),
                new String[]{"LL", "SL", "S "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.IRON_BLOCK, 16, 'I'), new Ingredient(Material.COAL_BLOCK, 32, 'C'), new Ingredient(Material.BASALT, 64, 'B')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT),
                new String[]{"BCB", "CIC", "BCB"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.YELLOW_WOOL, 64, 'W'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOSSILIZED_LOG), 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPOOL_OF_THREAD),
                new String[]{"WWW", "WLW", "WWW"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STICK, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPOOL_OF_THREAD), 'T'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GRAPPLING_HOOK),
                new String[]{"  S", " ST", "S T"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_HELMET),
                new String[]{"SSS", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_CHESTPLATE),
                new String[]{"S S", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_LEGGINGS),
                new String[]{"SSS", "S S", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'S'), new Ingredient(Material.AIR, ' ')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_BOOTS),
                new String[]{"S S", "S S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.EGG, 'E'), new Ingredient(Material.NETHER_BRICK, 16, 'N')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOW_WARRIOR_SPAWN_EGG),
                new String[]{"NNN", "NEN", "NNN"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.BAMBOO, 'B'), new Ingredient(Material.TNT, 64, 'T')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.CREEPER_WAND),
                new String[]{"T", "T", "B"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.OAK_LEAVES, 64, 'L'), new Ingredient(Material.OAK_WOOD, 64, 'W')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREETOP_HELMET),
                new String[]{"LLL", "LLL", "LWL"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.AIR, ' '), new Ingredient(Material.WHITE_WOOL, 'W'), new Ingredient(Material.STICK, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.APPLE_HARVESTER),
                new String[]{"WW", " S", " S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.MAGMA_CREAM, 'M'), new Ingredient(Material.DARK_OAK_LOG, 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREATED_WOOD),
                new String[]{"LLL", "LML", "LLL"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREATED_WOOD), 32, 'D')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.THE_TRUNK),
                new String[]{"DD", "DD", "DD"});
        RecipeRegistry.addShapedRecipe(Collections.singletonList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUN_PEARL), 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.CLOUD),
                new String[]{"SSS", "SSS", "SSS"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.CLOUD), 'C'), new Ingredient(Material.STICK, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TORNADO),
                new String[]{"C", "C", "S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STICK, 64, 'S'), new Ingredient(Material.AIR, ' ')),
                new ItemStack(Material.DEAD_BUSH),
                new String[]{"S S", "SSS", " S "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.BLAZE_POWDER, 'B'), new Ingredient(Material.SLIME_BALL, 'S'), new Ingredient(Material.ORANGE_DYE, 'D'), new Ingredient(Material.NETHERITE_INGOT, 'N')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM),
                new String[]{"BDB", "DSD", "BNB"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.DEAD_BUSH, 16, 'D'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM), 'G')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.DESERT_BIOME_STICK),
                new String[]{"D", "G"});
    }

    public static void registerVanillaRecipes() {
        JSONParser parser = new JSONParser();

        // shaped recipes
        File folder = new File(SkyblockMain.instance.getDataFolder().getAbsolutePath() + "/shaped");
        File[] files = folder.listFiles();

        assert files != null;
        for (File f : files) {
            try {
                JSONObject data = (JSONObject) parser.parse(new FileReader(f.getAbsolutePath()));
                ItemStack result = getResult(data);
                List<Ingredient> ingredients = getIngredients(data);
                String[] shape = getShape(data);
                RecipeRegistry.addShapedRecipe(ingredients, result, shape);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        folder = new File(SkyblockMain.instance.getDataFolder().getAbsolutePath() + "/shapeless");
        files = folder.listFiles();

        assert files != null;
        for (File f : files) {
            try {
                JSONObject data = (JSONObject) parser.parse(new FileReader(f.getAbsolutePath()));
                ItemStack result = getResult(data);
                List<List<ItemStack>> shapelessIngredients = getItemStacks(data);
                RecipeRegistry.addShapelessRecipe(shapelessIngredients, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ItemStack getResult(JSONObject json) {
        int amount = Integer.parseInt(json.get("count").toString());
        String s = (String) json.get("result");
        ItemStack itemStack = asItemStack(s);
        if (itemStack != null) {
            itemStack.setAmount(amount);
        }
        return itemStack;
    }

    public static List<Ingredient> getIngredients(JSONObject json) {
        JSONObject keys = (JSONObject) json.get("keys");
        List<Ingredient> all = new ArrayList<>();
        for (Object o : keys.keySet()) {
            char c = o.toString().charAt(0);
            JSONArray values = (JSONArray) keys.get(o);
            List<ItemStack> possibilities = new ArrayList<>();
            for (Object value : values) {
                String rawItem = (String) value;
                ItemStack itemStack = asItemStack(rawItem);
                possibilities.add(itemStack);
            }

            Ingredient ingredient = new Ingredient(possibilities, c);
            all.add(ingredient);
        }

        return all;
    }

    // used for shapeless recipes
    public static List<List<ItemStack>> getItemStacks(JSONObject json) {
        JSONArray ingredients = (JSONArray) json.get("ingredients");
        List<List<ItemStack>> all = new ArrayList<>();
        for (Object object : ingredients) {
            JSONArray possibilites = (JSONArray) object;
            List<ItemStack> inner = new ArrayList<>();
            for (Object possibilite : possibilites) {
                ItemStack itemStack = asItemStack(possibilite.toString());
                inner.add(itemStack);
            }
            all.add(inner);
        }
        return all;
    }

    public static String[] getShape(JSONObject json) {
        JSONArray jsonArray = (JSONArray) json.get("pattern");
        String[] shape = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            String line = (String) jsonArray.get(i);
            shape[i] = line;
        }

        return shape;
    }

    public static ItemStack asItemStack(String s) {
        String[] splitted = s.split(":");
        if (splitted.length != 2) {
            return null;
        }

        if (splitted[0].equals("skyblock")) {
            return SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.valueOf(splitted[1].toUpperCase()));
        } else if (splitted[0].equals("minecraft")) {
            return new ItemStack(Material.valueOf(splitted[1].toUpperCase()));
        }

        return null;
    }
}
