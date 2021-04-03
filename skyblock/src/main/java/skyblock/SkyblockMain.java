package skyblock;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.Items;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.util.Vector;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import skyblock.commands.CreateWorldCommand;
import skyblock.commands.FakePlayerTestCommand;
import skyblock.commands.JoinSkyblockCommand;
import skyblock.commands.WarpCommand;
import skyblock.listeners.*;
import skyblock.registries.ItemRegistry;
import skyblock.registries.NPCRegistry;
import skyblock.registries.RecipeRegistry;
import skyblock.registries.WorldRegistry;
import skyblock.utils.*;

import java.io.FileReader;
import java.io.IOException;


public class SkyblockMain extends JavaPlugin {
    public static SkyblockMain instance;

    // registries
    public static ItemRegistry itemRegistry;
    public static WorldRegistry worldRegistry;
    public static MoneyHandler moneyHandler;
    public static DatabaseHandler databaseHandler;
    public static NPCRegistry npcRegistry;

    @Override
    public void onEnable() {
        // init
        SkyblockMain.instance = this;
        SkyblockMain.itemRegistry = new ItemRegistry();
        SkyblockMain.moneyHandler = new MoneyHandler();
        SkyblockMain.databaseHandler = new DatabaseHandler();
        SkyblockMain.npcRegistry = new NPCRegistry();

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

        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChangeWorldListener(), this);

        // commands
        this.getCommand("skyblock").setExecutor(new JoinSkyblockCommand());
        this.getCommand("warp").setExecutor(new WarpCommand());
        this.getCommand("create_world").setExecutor(new CreateWorldCommand());
        this.getCommand("fp_test").setExecutor(new FakePlayerTestCommand());

        RecipeRegistry.registerCustomRecipes();
        RecipeRegistry.registerVanillaRecipes();

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(SkyblockMain.instance, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                int npcID = event.getPacket().getIntegers().read(0);

                if(event.getPacket().getEntityUseActions().read(0) == EnumWrappers.EntityUseAction.INTERACT) {
                    if(SkyblockMain.npcRegistry.isEntityRegistered(npcID)) {
                        SkyblockMain.npcRegistry.getNPC(npcID).interact(event.getPlayer());
                    }
                }
            }
        });
        JSONParser parser = new JSONParser();

        try {
            JSONObject json = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/test_npc.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzMwNzg5NTg5NiwKICAicHJvZmlsZUlkIiA6ICIzOTg5OGFiODFmMjU0NmQxOGIyY2ExMTE1MDRkZGU1MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNeVV1aWRJcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yYzcyMzUzYTliYzVjMTA4NGM5YjUyOWMxM2Y0ZjY4MWZmZmRmMDBjYzE2MjQ4N2Q4OGFkMDQyZGFhZmNmNjk0IgogICAgfQogIH0KfQ==", "ok83pzF5kpTgGtcbChohun9fu+66yW5JcgduGfXzrR+mH9H1WNnA4EF0JwApuUUrfPGZmFcwPmnu8QALgPkXfHEBNqUGDsmnISDP4eWW51gxiGcQkRBDwuN4hTC97ufh320C7Ao+QV4g9/qbZ7tAe1c6WpyAWq/EpqTA58O7PpxrawVmJJP4JIv36p0seFK+XJ+FCWGxATY/76YOj4oD3Y0b+lKqG83pMBzDj80m7cf1GwBtcYbvMd712lQdDKs/lOFwAu8FGjHAn3zMhpvxw7U+pt0tDNYIZt1UfRwM8YD6g4UOJiA6gcUY64MURySMqrMaIdGkT+LxTwx9KaGBdn+xVH80shGdbVgcpTXjO4PlQSu0EFLje7et+wdWse0K2BX3Uw6OawW6tlk3stlw6lYhF/cgTx3CgYuwfCgCZ2yIU5DX6hGNQfyE2bhv+v5LoNyJqRMMsCD3ar4yoweAuoPAL1XTRPsz//zQ8XG3dyzGWWYcqqkc+oeRpkCGztkc62oh1Bm86gX1oodluPhpMXvASnlbu8I8q8mkrzvL3q+RaoWnFdnka3NtpEHCERotdxKL03kPsEa2dRvntgsDH5TQl2r4GP/RO1/JNbgwXbnNxzvwyl9iuA3Mo0AVCHkL8Exqm5avzpYnshO/h/hvjn3e4DlzVuYAZq+n0707KPY="), json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        SkyblockMain.worldRegistry.unloadAll();
        SkyblockMain.worldRegistry.saveToConfig(this.getDataFolder().getAbsolutePath() + "/world_registry.yaml");
        SkyblockMain.moneyHandler.saveData();
        SkyblockMain.databaseHandler.closeConnection();
    }
}