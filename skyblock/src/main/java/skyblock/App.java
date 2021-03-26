//TODO: disable creeper damage

package skyblock;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    ItemRegistry itemRegistry = new ItemRegistry(this);

    @Override
    public void onEnable() {
        // getLogger().info("INFO");
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityChangeBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new CraftItemListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);

        ItemStack ingredient = itemRegistry.getItemStack(ItemRegistry.GEODE);
        ItemStack result = itemRegistry.getItemStack(ItemRegistry.ARCHEOLOGISTS_PICKAXE);
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "archeologists_pickaxe"), result);
        recipe.shape("GGG", " S ", " S ");
        recipe.setIngredient('G', ingredient.getType());
        recipe.setIngredient('S', Material.STICK);
        getServer().addRecipe(recipe);
    }

    @Override
    public void onDisable() {
    }
}