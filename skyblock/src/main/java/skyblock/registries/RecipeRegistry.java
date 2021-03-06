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
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.EGG, 16, 'E'), new Ingredient(Material.NETHER_BRICK, 64, 'N'), new Ingredient(Material.NETHERITE_INGOT, 'I')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOW_WARRIOR_SPAWN_EGG),
                new String[]{"NEN", "EIE", "NEN"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.EGG, 16, 'E'), new Ingredient(Material.NETHER_BRICK, 64, 'N'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'I')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.CAOCAO_SPAWN_EGG),
                new String[]{"NEN", "EIE", "NEN"});
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
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM), 'G'), new Ingredient(Arrays.asList(new ItemStack(Material.OAK_SAPLING), new ItemStack(Material.BIRCH_SAPLING), new ItemStack(Material.ACACIA_SAPLING), new ItemStack(Material.DARK_OAK_SAPLING), new ItemStack(Material.JUNGLE_SAPLING), new ItemStack(Material.SPRUCE_SAPLING)), 64, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FOREST_BIOME_STICK),
                new String[]{"S", "G", "S"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM), 'G'), new Ingredient(Material.POPPY, 64, 'P'), new Ingredient(Material.DANDELION, 64, 'D')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.FLOWER_FOREST_BIOME_STICK),
                new String[]{"P", "D", "G"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM), 'G'), new Ingredient(Material.SLIME_BLOCK, 64, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SWAMP_BIOME_STICK),
                new String[]{"S", "G"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.EMERALD_BLOCK, 'E'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GEODE), 'G')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.EMERALD_STUDDED_GEODE),
                new String[]{"EEE", "EGE", "EEE"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.AIR, ' '), new Ingredient(Material.STICK, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.EMERALD_STUDDED_GEODE), 'A')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.JACKHAMMER),
                new String[]{"AAA", " S ", " S "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.YELLOW_GLAZED_TERRACOTTA, 64, 'T'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SPOOL_OF_THREAD), 4, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.APPLE_HARVESTER), 'A')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.REINFORCED_APPLE_HARVESTER),
                new String[]{"TST", "SAS", "TST"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.DISPENSER, 'D'), new Ingredient(Material.WOODEN_AXE, 'a'), new Ingredient(Material.STONE_AXE, 'b'), new Ingredient(Material.GOLDEN_AXE, 'c'), new Ingredient(Material.IRON_AXE, 'd'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREATED_WOOD), 'T')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.AUTO_STRIPPER),
                new String[]{"TaT", "bDc", "TdT"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.BONE, 64, 'B'), new Ingredient(Material.GREEN_DYE, 16, 'D'), new Ingredient(Material.SCUTE, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GREEN_SCALE),
                new String[]{"BDB", "DSD", "BDB"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.BONE, 64, 'B'), new Ingredient(Material.LIGHT_BLUE_DYE, 16, 'D'), new Ingredient(Material.SCUTE, 'S')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.BLUE_SCALE),
                new String[]{"BDB", "DSD", "BDB"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.STRING, 64, 'S'), new Ingredient(Material.AIR, ' '), new Ingredient(Material.STICK, 's'), new Ingredient(Material.MUSHROOM_STEM, 'M')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.WRAPPED_SWORD_GRIP),
                new String[]{" M ", "SsS", " M "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.AIR, ' '), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GREEN_SCALE), 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.WRAPPED_SWORD_GRIP), 'G')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GREEN_DRAGON_CRESCENT_BLADE),
                new String[]{"  S", " S ", "G  "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.AIR, ' '), new Ingredient(Material.WATER_BUCKET, 'W'), new Ingredient(Material.EMERALD, 64, 'E'), new Ingredient(Material.KELP, 64, 'K')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.OCEAN_ESSENCE),
                new String[]{" K ", "EWE", " K "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.OCEAN_ESSENCE), 'E'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM), 'M')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.OCEAN_BIOME_STICK),
                new String[]{"E", "M"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.ICE, 64, 'I'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM), 'M')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SNOWY_TAIGA_BIOME_STICK),
                new String[]{"I", "M"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.COCOA_BEANS, 64, 'C'), new Ingredient(Material.JUNGLE_SAPLING, 64, 'S'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM), 'M')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.JUNGLE_BIOME_STICK),
                new String[]{"C", "S", "M"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.WATER_BUCKET, 'W'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.MANIPULATION_GEM), 'M')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.RIVER_BIOME_STICK),
                new String[]{"WWW", "WMW", "WWW"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.AIR, ' '), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREATED_WOOD), 'T'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHINY_PEBBLE), 'P'), new Ingredient(Material.GOLD_NUGGET, 'G')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.COCOA_PLANTING_GADGET),
                new String[]{"  P", " G ", "TT "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.EGG, 'E'), new Ingredient(Material.WHITE_WOOL, 'W')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUNSHIR_SPAWN_EGG),
                new String[]{"WWW", "WEW", "WWW"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.AIR, ' '), new Ingredient(Material.EGG, 'E'), new Ingredient(Material.WATER_BUCKET, 'W'), new Ingredient(Material.WHITE_DYE, 'D'), new Ingredient(Material.IRON_NUGGET, 'N'), new Ingredient(Material.GHAST_TEAR, 'G')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ICICLE_SPAWN_EGG),
                new String[]{" N ", "DEG", " W "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.EGG, 'E'), new Ingredient(Material.SUGAR, 32, 'S'), new Ingredient(Material.STONE, 16, 's')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.LUTUMITE_SPAWN_EGG),
                new String[]{"SES", "sss"});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.AIR, ' '), new Ingredient(Material.BLAZE_ROD, 16, 'B'), new Ingredient(Material.GUNPOWDER, 64, 'G'), new Ingredient(Material.LAVA_BUCKET, 'L')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ESSENCE_OF_FIRE),
                new String[]{" B ", "BGB", " L "});
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(Material.AIR, ' '), new Ingredient(Material.GOLD_INGOT, 'a'), new Ingredient(Material.NETHERITE_INGOT, 'b'), new Ingredient(Material.IRON_INGOT, 'c'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT), 'd'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.BRONZE_INGOT), 'e'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.WRAPPED_SWORD_GRIP), 'G'), new Ingredient(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ESSENCE_OF_FIRE), 'E')),
                SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ZHANLU),
                new String[]{" bd", "aEc", "Ge "});
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
