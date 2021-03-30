package skyblock;

import org.bukkit.Material;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import skyblock.commands.CreateWorldCommand;
import skyblock.commands.JoinSkyblockCommand;
import skyblock.commands.WarpCommand;
import skyblock.generators.SkyblockChunkGenerator;
import skyblock.listeners.*;
import skyblock.registries.ItemRegistry;
import skyblock.registries.RecipeRegistry;
import skyblock.registries.WorldRegistry;
import skyblock.utils.Ingredient;
import skyblock.utils.WorldInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class SkyblockMain extends JavaPlugin {
    public static SkyblockMain instance;

    // registries
    public static ItemRegistry itemRegistry;
    public static RecipeRegistry recipeRegistry;
    public static WorldRegistry worldRegistry;

    @Override
    public void onEnable() {
        // init
        SkyblockMain.instance = this;
        SkyblockMain.itemRegistry = new ItemRegistry(this);
        SkyblockMain.recipeRegistry = new RecipeRegistry();

        SkyblockMain.worldRegistry = WorldRegistry.loadFromConfig(this.getDataFolder().getAbsolutePath() + "/world_registry.yaml");
        if(!SkyblockMain.worldRegistry.hasWorld("world")) SkyblockMain.worldRegistry.addWorld(new WorldInfo(WorldInfo.WorldType.PUBLIC_WORLD, "world", true));
        SkyblockMain.worldRegistry.loadPublicWorlds();

        // listeners
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityChangeBlockListener(), this);
        this.getServer().getPluginManager().registerEvents(new CraftItemListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        this.getServer().getPluginManager().registerEvents(new PrepareItemCraftListener(), this);
        this.getServer().getPluginManager().registerEvents(new WorldRegistryListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityExplodeListener(), this);


        // commands
        this.getCommand("skyblock").setExecutor(new JoinSkyblockCommand());
        this.getCommand("warp").setExecutor(new WarpCommand());
        this.getCommand("create_world").setExecutor(new CreateWorldCommand());


        this.registerRecipes();

        // TODO!!!!!!!!
        /*
         * ItemStack ingredient = itemRegistry.getItemStack(ItemRegistry.GEODE);
         * ItemStack result =
         * itemRegistry.getItemStack(ItemRegistry.ARCHEOLOGISTS_PICKAXE); ShapedRecipe
         * recipe = new ShapedRecipe(new NamespacedKey(this, "archeologists_pickaxe"),
         * result); recipe.shape("GGG", " S ", " S "); recipe.setIngredient('G',
         * ingredient.getType()); recipe.setIngredient('S', Material.STICK);
         * this.getServer().addRecipe(recipe);
         */
    }

    @Override
    public void onDisable() {
        SkyblockMain.worldRegistry.unloadAll();
        SkyblockMain.worldRegistry.saveToConfig(this.getDataFolder().getAbsolutePath() + "/world_registry.yaml");
    }

    public void registerRecipes() {
        RecipeRegistry.addShapedRecipe(Arrays.asList(new Ingredient(itemRegistry.getItemStack(ItemRegistry.GEODE), 'G'), new Ingredient(Material.STICK, 1, 'S')),
                itemRegistry.getItemStack(ItemRegistry.ARCHEOLOGISTS_PICKAXE), new String[]{"GGG", " S ", " S "});
    }
}