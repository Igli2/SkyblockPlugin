package skyblock;

import org.bukkit.plugin.java.JavaPlugin;

import skyblock.commands.CreateWorldCommand;
import skyblock.commands.JoinSkyblockCommand;
import skyblock.commands.WarpCommand;
import skyblock.listeners.*;
import skyblock.registries.ItemRegistry;
import skyblock.registries.RecipeRegistry;
import skyblock.registries.WorldRegistry;
import skyblock.utils.DatabaseHandler;
import skyblock.utils.MoneyHandler;
import skyblock.utils.WorldInfo;


public class SkyblockMain extends JavaPlugin {
    public static SkyblockMain instance;

    // registries
    public static ItemRegistry itemRegistry;
    public static WorldRegistry worldRegistry;
    public static MoneyHandler moneyHandler;
    public static DatabaseHandler databaseHandler;

    @Override
    public void onEnable() {
        // init
        SkyblockMain.instance = this;
        SkyblockMain.itemRegistry = new ItemRegistry();
        SkyblockMain.moneyHandler = new MoneyHandler();
        SkyblockMain.databaseHandler = new DatabaseHandler();

        SkyblockMain.worldRegistry = WorldRegistry.loadFromConfig(this.getDataFolder().getAbsolutePath() + "/world_registry.yaml");
        if(!SkyblockMain.worldRegistry.hasWorld("world")) SkyblockMain.worldRegistry.addWorld(new WorldInfo(WorldInfo.WorldType.PUBLIC_WORLD, "world", true));
        SkyblockMain.worldRegistry.loadPublicWorlds();
        SkyblockMain.moneyHandler.loadData();

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
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);


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
        SkyblockMain.moneyHandler.saveData();
        SkyblockMain.databaseHandler.closeConnection();
    }
}