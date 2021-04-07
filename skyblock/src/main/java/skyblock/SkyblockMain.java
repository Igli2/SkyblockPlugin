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
            JSONObject builder = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/builder.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzMwNzg5NTg5NiwKICAicHJvZmlsZUlkIiA6ICIzOTg5OGFiODFmMjU0NmQxOGIyY2ExMTE1MDRkZGU1MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNeVV1aWRJcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yYzcyMzUzYTliYzVjMTA4NGM5YjUyOWMxM2Y0ZjY4MWZmZmRmMDBjYzE2MjQ4N2Q4OGFkMDQyZGFhZmNmNjk0IgogICAgfQogIH0KfQ==", "ok83pzF5kpTgGtcbChohun9fu+66yW5JcgduGfXzrR+mH9H1WNnA4EF0JwApuUUrfPGZmFcwPmnu8QALgPkXfHEBNqUGDsmnISDP4eWW51gxiGcQkRBDwuN4hTC97ufh320C7Ao+QV4g9/qbZ7tAe1c6WpyAWq/EpqTA58O7PpxrawVmJJP4JIv36p0seFK+XJ+FCWGxATY/76YOj4oD3Y0b+lKqG83pMBzDj80m7cf1GwBtcYbvMd712lQdDKs/lOFwAu8FGjHAn3zMhpvxw7U+pt0tDNYIZt1UfRwM8YD6g4UOJiA6gcUY64MURySMqrMaIdGkT+LxTwx9KaGBdn+xVH80shGdbVgcpTXjO4PlQSu0EFLje7et+wdWse0K2BX3Uw6OawW6tlk3stlw6lYhF/cgTx3CgYuwfCgCZ2yIU5DX6hGNQfyE2bhv+v5LoNyJqRMMsCD3ar4yoweAuoPAL1XTRPsz//zQ8XG3dyzGWWYcqqkc+oeRpkCGztkc62oh1Bm86gX1oodluPhpMXvASnlbu8I8q8mkrzvL3q+RaoWnFdnka3NtpEHCERotdxKL03kPsEa2dRvntgsDH5TQl2r4GP/RO1/JNbgwXbnNxzvwyl9iuA3Mo0AVCHkL8Exqm5avzpYnshO/h/hvjn3e4DlzVuYAZq+n0707KPY="), builder));
            JSONObject miner = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/miner.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNjE0OTM2NTMxOSwKICAicHJvZmlsZUlkIiA6ICIzOWEzOTMzZWE4MjU0OGU3ODQwNzQ1YzBjNGY3MjU2ZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJkZW1pbmVjcmFmdGVybG9sIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ZkZWY5NWUzZjQ0MDA3MTA2ODdjZjBjYjhhNTJmNmExNDVjMmM0YmM2YTBlYmMzNWE2NjNjYjE5NTViYmM1NTQiCiAgICB9CiAgfQp9", "bXNibqPU3wMxGPlZoJ11ibQWhdT/1DootFS80AQGjigmeTWsEMyjKqQDAvSVvtoAAkNSgoQLlhy+o+PLt9xjDGPV1OVRyLZs+Yw2J1lQr5V7gpGrkXDWgs9K618q6ohFGbU5AJhLOZOebVhwyn0phDhEDbvMX+1I5yuCqQYSr/B4lGJC4krzruVF+vIz3su6rmEn72rjTZpgmyrMPhqZlB2HarSk6hu5kpJkmUAGmrZifVKosQGYi7z0eIe5gzo8LF90iO6OnQCbjtyad23uGmgwvMvJdxWy7D8JMj22GJgdL+ZLLaoFEJm+YTG+yvO4Eat0e6tD8Jf83rPdtHwpJSU8DbwUSv06lh1Qo8KMWFCmIAscTQWcwIe5JO9UFrJfTd3UTn0ReV0LJcEujgIQ7S4Im1NHRjb+4A5SjCgh4ca/ni4a7+jBdPPb4kmr/oBkIJAQaiSgl6zUCM94gUbf08RRNc8LBtv9dqw39bxbX1YqeQ5DoBx6jyUElkaruu24JaXDJ7k+Ox0fXepf4OrwbRUjwPURrdnkE4vZ+gPt5yCwMMju9NIQPUOkSjMyT9lkUo3iFBgsy+InfKHa511PTr6T0UwZrR//2biLdOWts5YX1cLBw6segy2Uq439W4NLMlCjFnY0BcQihXKtr/O5fSIC/VqJzQ5xaW0vkfGqUbE="), miner));
            JSONObject gardener = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/gardener.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYwOTYyODkxNjU2MCwKICAicHJvZmlsZUlkIiA6ICI5ZjNkYmFjNWViYTQ0YzZhODQ5YTg2YTY5OWM1YzQxYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTcGVjdHJhc29uaWMiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzYwYjdmZGEzOTQyZDYwMTZkZTBjMWNmZTk0MjZhM2MwZDY0MTY1NTRhNWY5ZjY2NDJmMTU0YTU4ZjdlY2IwYiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9", "MSFfqC2kOecLt/6i5rvsOMTb+c4L2L6ccmn7A/DvjSJneOivDIf+5bxmJXP2IzysQBy8+DAsknbEk0Kko0MvCKT5tBpvqH1MHQs01JNshR6rebHaoB/Hsu7Xo+MU9EcpIkO0uwdZkZTZYiP3VjHLv3P84ptYuI18CizGWj9h4FppmukrPCUGBE52fyOdkSKVaQb6f+A7UOhznLYkQahTeY1/2P72nulZGBw9j+c03ZMJgTzYnaZ+mLwGSJ6OW+to6oo959QKmcTJbhfPUe0KEwxlyPDk5+CtJQfj8xVZDGpMLSdHZmCxwuzfGbQTqRSdwz236hvmbyixRetQ2sgpkK03FOatCOhLhvJ+NHGSLzsIwMdLVDIkqmDcig5FgsU5FOu4dZoVBYlsw1+NbCgIKDLY0YYXLfJ8v/cBSAwGxNsh/f+B0UB+XHBCA0U5NOi7yLSpIhCTf1Rh9qSXToRQplOUGUaO3CXuvWKekZTnGhOSQ374m0uqwarFiJcAxoeZJccrT4bB5/keaa6jDndAogvNGBlv+bEq8L8yvzBZEBU/oWCH3HsRlMaIdXr9/e4fsbx3OiPBvXCEl10C2MKaCniwvmLal4NSLLBmQoffprBV6X52IRtQLGXLKPXODfn75AmASoFyh1J7MSW5GKJ0zQDUcNTItTusV5IupY8S+a0="), gardener));
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