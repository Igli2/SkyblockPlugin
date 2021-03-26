package skyblock;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    ItemRegistry itemRegistry = new ItemRegistry(this);

    @Override
    public void onEnable() {
        // getLogger().info("INFO");
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new CreatureSpawnListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityChangeBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new CraftItemListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
    }

    @Override
    public void onDisable() {
    }
}