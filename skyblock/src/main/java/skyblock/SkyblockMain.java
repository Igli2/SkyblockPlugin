package skyblock;

import org.bukkit.plugin.java.JavaPlugin;

import skyblock.commands.CreateWorldCommand;
import skyblock.commands.JoinSkyblockCommand;
import skyblock.commands.WarpCommand;
import skyblock.listeners.*;
import skyblock.registries.ItemRegistry;
import skyblock.registries.RecipeRegistry;
import skyblock.registries.WorldRegistry;
import skyblock.utils.WorldInfo;


public class SkyblockMain extends JavaPlugin {
    public static SkyblockMain instance;

    // registries
    public static ItemRegistry itemRegistry;
    public static WorldRegistry worldRegistry;

    @Override
    public void onEnable() {
        // init
        SkyblockMain.instance = this;
        SkyblockMain.itemRegistry = new ItemRegistry();

        SkyblockMain.worldRegistry = WorldRegistry.loadFromConfig(this.getDataFolder().getAbsolutePath() + "/world_registry.yaml");
        if(!SkyblockMain.worldRegistry.hasWorld("world")) SkyblockMain.worldRegistry.addWorld(new WorldInfo(WorldInfo.WorldType.PUBLIC_WORLD, "world", true));
        SkyblockMain.worldRegistry.loadPublicWorlds();

        // listeners
        this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityChangeBlockListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        this.getServer().getPluginManager().registerEvents(new PrepareItemCraftListener(), this);
        this.getServer().getPluginManager().registerEvents(new WorldRegistryListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityExplodeListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryDragListener(), this);


        // commands
        this.getCommand("skyblock").setExecutor(new JoinSkyblockCommand());
        this.getCommand("warp").setExecutor(new WarpCommand());
        this.getCommand("create_world").setExecutor(new CreateWorldCommand());

        RecipeRegistry.registerCustomRecipes();
        RecipeRegistry.registerVanillaRecipes();
    }

    @Override
    public void onDisable() {
        SkyblockMain.worldRegistry.unloadAll();
        SkyblockMain.worldRegistry.saveToConfig(this.getDataFolder().getAbsolutePath() + "/world_registry.yaml");
    }
}