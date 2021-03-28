//TODO: disable creeper damage

package skyblock;

import org.bukkit.Material;

import org.bukkit.plugin.java.JavaPlugin;

import skyblock.commands.JoinSkyblockCommand;
import skyblock.listeners.*;
import skyblock.registries.ItemRegistry;
import skyblock.registries.RecipeRegistry;
import skyblock.utils.Ingredient;

import java.util.Arrays;

public class SkyblockMain extends JavaPlugin {
    public static SkyblockMain instance;

    // registries
    public static ItemRegistry itemRegistry;
    public static RecipeRegistry recipeRegistry;

    @Override
    public void onEnable() {
        // init
        SkyblockMain.itemRegistry = new ItemRegistry(this);
        SkyblockMain.recipeRegistry = new RecipeRegistry();
        SkyblockMain.instance = this;

        // listeners
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityChangeBlockListener(this), this);
        this.getServer().getPluginManager().registerEvents(new CraftItemListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PrepareItemCraftListener(this), this);

        // commands
        this.getCommand("skyblock").setExecutor(new JoinSkyblockCommand());

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
    }

    public void registerRecipes() {
        RecipeRegistry.addShapedRecipe(this, "archeologists_pickaxe",
                Arrays.asList(new Ingredient(Material.GOLD_BLOCK, 'G'), new Ingredient(Material.STICK, 'S')),
                itemRegistry.getItemStack(ItemRegistry.GEODE), new String[] { "GGG", " S ", " S " });
    }
}