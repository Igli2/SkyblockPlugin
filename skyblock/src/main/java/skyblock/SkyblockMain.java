package skyblock;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import skyblock.commands.*;
import skyblock.enchantments.EnchantmentRegistry;
import skyblock.entities.Minion;
import skyblock.listeners.*;
import skyblock.listeners.entities.CaoCaoListener;
import skyblock.listeners.entities.IcicleListeners;
import skyblock.listeners.entities.IcicleListener;
import skyblock.listeners.entities.MinionListener;
import skyblock.listeners.items.*;
import skyblock.registries.ItemRegistry;
import skyblock.registries.NPCRegistry;
import skyblock.registries.RecipeRegistry;
import skyblock.registries.WorldRegistry;
import skyblock.utils.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;


public class SkyblockMain extends JavaPlugin {
    public static SkyblockMain instance;

    // registries
    public static ItemRegistry itemRegistry;
    public static WorldRegistry worldRegistry;
    public static MoneyHandler moneyHandler;
    public static DatabaseHandler databaseHandler;
    public static NPCRegistry npcRegistry;

    public static Structure starterIsland;

    public static File worldDataPath = Paths.get("plugins", "SkyblockPlugin", "special_blocks.yaml").toFile();

    public static Random random;

    @Override
    public void onEnable() {
        // init
        SkyblockMain.instance = this;
        SkyblockMain.itemRegistry = new ItemRegistry();
        SkyblockMain.moneyHandler = new MoneyHandler();
        SkyblockMain.databaseHandler = new DatabaseHandler();
        SkyblockMain.npcRegistry = new NPCRegistry();

        SkyblockMain.starterIsland = Structure.fromFile(this.getDataFolder().getAbsolutePath() + "/structures/skyblock.struct");

        SkyblockMain.worldRegistry = WorldRegistry.loadFromConfig(this.getDataFolder().getAbsolutePath() + "/world_registry.yaml");
        if(!SkyblockMain.worldRegistry.hasWorld("world")) SkyblockMain.worldRegistry.addWorld(new WorldInfo(WorldInfo.WorldType.PUBLIC_WORLD, "world", true));
        SkyblockMain.worldRegistry.loadPublicWorlds();
        SkyblockMain.moneyHandler.loadData();

        SkyblockMain.random = new Random();

        this.loadWorldData();

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
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockFromToListener(), this);
        this.getServer().getPluginManager().registerEvents(new EnchantItemListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChangeWorldListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerFishEvent(), this);
        this.getServer().getPluginManager().registerEvents(new CreatureSpawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemBrowser(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerBucketEmptyListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerBucketFillListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityPickupItemListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityTargetListener(), this);

        this.getServer().getPluginManager().registerEvents(new IcicleListener(), this);
        this.getServer().getPluginManager().registerEvents(new MinionListener(), this);

        this.getServer().getPluginManager().registerEvents(new IcicleListeners(), this);
        this.getServer().getPluginManager().registerEvents(new CaoCaoListener(), this);

        this.getServer().getPluginManager().registerEvents(new Geode(), this);
        this.getServer().getPluginManager().registerEvents(new ArcheologistsPickaxe(), this);
        this.getServer().getPluginManager().registerEvents(new CreeperWand(), this);
        this.getServer().getPluginManager().registerEvents(new AppleHarvester(), this);
        this.getServer().getPluginManager().registerEvents(new TheTrunk(), this);
        this.getServer().getPluginManager().registerEvents(new Tornado(), this);
        this.getServer().getPluginManager().registerEvents(new BiomeSticks(), this);
        this.getServer().getPluginManager().registerEvents(new Jackhammer(), this);
        this.getServer().getPluginManager().registerEvents(new AutoStripper(), this);
        this.getServer().getPluginManager().registerEvents(new TreeCapitator(), this);
        this.getServer().getPluginManager().registerEvents(new CocoaPlantingGadget(), this);

        // commands
        this.setCommandExecutors();

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(SkyblockMain.instance, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                int npcID = event.getPacket().getIntegers().read(0);

                if (event.getPacket().getEntityUseActions().read(0) == EnumWrappers.EntityUseAction.INTERACT) {
                    if (SkyblockMain.npcRegistry.isEntityRegistered(npcID)) {
                        SkyblockMain.npcRegistry.getNPC(npcID).interact(event.getPlayer());
                    }
                }
            }
        });

        JSONParser parser = new JSONParser();
        loadNpcs(parser);
        loadSellableItems(parser);

        RecipeRegistry.registerCustomRecipes();
        RecipeRegistry.registerVanillaRecipes();
        EnchantmentRegistry.registerAllEnchantments();

        Bukkit.getScheduler().runTaskLater(SkyblockMain.instance, new Runnable() {
            @Override
            public void run() {
                for(World w : Bukkit.getWorlds()) {
                    for(Entity e : w.getEntities()) {
                        if(e instanceof ArmorStand && !(((CraftEntity) e).getHandle() instanceof Minion)) {
                            Minion.replaceArmorStand((ArmorStand) e);
                        }
                    }
                }
            }
        }, 20);
    }

    private void loadNpcs(JSONParser parser) {
        try {
            JSONObject builder = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/builder.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzMwNzg5NTg5NiwKICAicHJvZmlsZUlkIiA6ICIzOTg5OGFiODFmMjU0NmQxOGIyY2ExMTE1MDRkZGU1MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNeVV1aWRJcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yYzcyMzUzYTliYzVjMTA4NGM5YjUyOWMxM2Y0ZjY4MWZmZmRmMDBjYzE2MjQ4N2Q4OGFkMDQyZGFhZmNmNjk0IgogICAgfQogIH0KfQ==", "ok83pzF5kpTgGtcbChohun9fu+66yW5JcgduGfXzrR+mH9H1WNnA4EF0JwApuUUrfPGZmFcwPmnu8QALgPkXfHEBNqUGDsmnISDP4eWW51gxiGcQkRBDwuN4hTC97ufh320C7Ao+QV4g9/qbZ7tAe1c6WpyAWq/EpqTA58O7PpxrawVmJJP4JIv36p0seFK+XJ+FCWGxATY/76YOj4oD3Y0b+lKqG83pMBzDj80m7cf1GwBtcYbvMd712lQdDKs/lOFwAu8FGjHAn3zMhpvxw7U+pt0tDNYIZt1UfRwM8YD6g4UOJiA6gcUY64MURySMqrMaIdGkT+LxTwx9KaGBdn+xVH80shGdbVgcpTXjO4PlQSu0EFLje7et+wdWse0K2BX3Uw6OawW6tlk3stlw6lYhF/cgTx3CgYuwfCgCZ2yIU5DX6hGNQfyE2bhv+v5LoNyJqRMMsCD3ar4yoweAuoPAL1XTRPsz//zQ8XG3dyzGWWYcqqkc+oeRpkCGztkc62oh1Bm86gX1oodluPhpMXvASnlbu8I8q8mkrzvL3q+RaoWnFdnka3NtpEHCERotdxKL03kPsEa2dRvntgsDH5TQl2r4GP/RO1/JNbgwXbnNxzvwyl9iuA3Mo0AVCHkL8Exqm5avzpYnshO/h/hvjn3e4DlzVuYAZq+n0707KPY="), builder));
            JSONObject miner = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/miner.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNjE0OTM2NTMxOSwKICAicHJvZmlsZUlkIiA6ICIzOWEzOTMzZWE4MjU0OGU3ODQwNzQ1YzBjNGY3MjU2ZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJkZW1pbmVjcmFmdGVybG9sIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ZkZWY5NWUzZjQ0MDA3MTA2ODdjZjBjYjhhNTJmNmExNDVjMmM0YmM2YTBlYmMzNWE2NjNjYjE5NTViYmM1NTQiCiAgICB9CiAgfQp9", "bXNibqPU3wMxGPlZoJ11ibQWhdT/1DootFS80AQGjigmeTWsEMyjKqQDAvSVvtoAAkNSgoQLlhy+o+PLt9xjDGPV1OVRyLZs+Yw2J1lQr5V7gpGrkXDWgs9K618q6ohFGbU5AJhLOZOebVhwyn0phDhEDbvMX+1I5yuCqQYSr/B4lGJC4krzruVF+vIz3su6rmEn72rjTZpgmyrMPhqZlB2HarSk6hu5kpJkmUAGmrZifVKosQGYi7z0eIe5gzo8LF90iO6OnQCbjtyad23uGmgwvMvJdxWy7D8JMj22GJgdL+ZLLaoFEJm+YTG+yvO4Eat0e6tD8Jf83rPdtHwpJSU8DbwUSv06lh1Qo8KMWFCmIAscTQWcwIe5JO9UFrJfTd3UTn0ReV0LJcEujgIQ7S4Im1NHRjb+4A5SjCgh4ca/ni4a7+jBdPPb4kmr/oBkIJAQaiSgl6zUCM94gUbf08RRNc8LBtv9dqw39bxbX1YqeQ5DoBx6jyUElkaruu24JaXDJ7k+Ox0fXepf4OrwbRUjwPURrdnkE4vZ+gPt5yCwMMju9NIQPUOkSjMyT9lkUo3iFBgsy+InfKHa511PTr6T0UwZrR//2biLdOWts5YX1cLBw6segy2Uq439W4NLMlCjFnY0BcQihXKtr/O5fSIC/VqJzQ5xaW0vkfGqUbE="), miner));
            JSONObject gardener = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/gardener.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxODUxMzc5ODQ1NSwKICAicHJvZmlsZUlkIiA6ICI0ZWQ4MjMzNzFhMmU0YmI3YTVlYWJmY2ZmZGE4NDk1NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaXJlYnlyZDg4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk2NzJkNDBmZTIyMjY2MGVkNTQ0ODFmZTliOGE2MWZkZGNlYzUzMGE4OGMxN2NhYWFhYzU4MmYyNmU3OWEzZDAiCiAgICB9CiAgfQp9", "NxSLWBMkjs/z/hmyFUpPVLrqhDKouaU1fJxBn74/j8W/N4UMCdHtJMeyeE0R6P5c1bUPkJs6/n++RgER2KhxT9ZM598+3q/Gp3nuF3iqWG3nMyVU3Es5wK7cJxrYQePZKommG5+a5ZqTPEe8LwtQi89hJLpcowvXIl77NKwPoTk2pY7srVgdxLrq+3/sYuuoNww1IAJrj+zIiQ+gzK0FVEJwONyoCd9/47jvqW5yTThfOKFTmzE9rtJFmVO2KMrjZLee/I08b5FsjSQJUSa+qXLBMG3IEA92HrTCXH7sLmDzB+CjWmuAWJ1cWdSTDbxYIXWWcES2S1i7P/03cfqEjtu5pfj34N4aikgqi8CKK6p8WTfdiwloKb+CKVwhLzg25NdV2uxsG+lYuyHro3sQILweWHaeIgGqNY7GnYwG7upoD3n/bkE8GMOiKB0hGmNogc9EykhyD/AJkIeLE2b1vmYt10cBDBMzKgYPNwVt/oCnc4QCe19zcSS3jo+9oBcKwJ2NU5UU9Aw5h/gqui0AD8Uu1tO9jZiCK2rVgpHrkmB/jyE1wVHs6jllOju4xeg1zjg4815Kbw4H0NhbLU0lq82iDmZDHFhPcsj/vRdRj8vky24NeTGOZHlPdQuxT/cYJrWqPQvHZd0zKEwfnSQ8Kt1W5Dycj6VB0syx87A9rdw="), gardener));
            JSONObject wizard = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/wizard.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTU5MzY4OTUzNzI0OSwKICAicHJvZmlsZUlkIiA6ICI4MmM2MDZjNWM2NTI0Yjc5OGI5MWExMmQzYTYxNjk3NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJOb3ROb3RvcmlvdXNOZW1vIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzMzODJiOWZhMWU5YmJiMzIxNWEwZTNhODgyZjAwOGZiZDQ0MTczY2JiNGFiODdiYTIzYzUzMDk4YTU2MzBhMGQiCiAgICB9CiAgfQp9", "omw1iY1qK8HYvlAEeW/8Wf4cWHsd+t2QQYjystDhpb8kx4wW8b2ZqY5JsWFDHkRthsoywj2vOs7a1b7s9p/AMPu9iLwErs2SOkulaMpsYnT6szZ5iIZjy9cLVZu5yAUC2AapySW6rDtzBZGSCpbErEIVwwOQan6Wz0NN3Gl5/nZx3EyBFJyyIxU2jUm4d0sxU77174+gKWvg1HhWqt7tliNNQH1wzvmcRwYfE1GRHTO1kDnModtQAiHbZOY78tOsSY85nPQIWbqVwvyODjghvcq2zKvJXz9ztxbMGGGsElOMUNPbYmlPmW1fNBGbc4bv0Q3aiylLvoShuF75osJCeS0nncguWa5bIgQ7Ija3T/ndMUs50sJ08c9Ye/Ps0AbpvlEyU3c2JCvzpOXxm9YfqsXHWeI4SjA+ND/ujaB2L+XhJuvQSh9ORnLCkuwuDu0KMgaflIflibCV2hzDt9vTY/xDa7vHQunFn4wgATyUAxkOUp3u9GAg4zJpKNrvJx88toRsJEI+yVdIXuRk03BjoQUQhEnBRdyQglcu8ejy/dIEf8HsZOxDgPR1p+5BMZO3+XmCn0TLhj2sBmf7epG4LfbS6Iz6aobNtt3xWwb/7K93lSH8Fox/i0AXMGFYYnqGfEfh6i2Y5JPSH6Fq2D2UV5fCuwHsr/OPtuMY0aUsYcw="), wizard));
            JSONObject smith = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/smith.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNDcyMjI4Mzg2OSwKICAicHJvZmlsZUlkIiA6ICI0ZWQ4MjMzNzFhMmU0YmI3YTVlYWJmY2ZmZGE4NDk1NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaXJlYnlyZDg4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ2MmIxNzk3ZDc2N2M2MmRhMDQzZDAxNjIyNjcyODM5ZjE4YWYwNTUzM2M0YjNlMTU2ZjIzMTQ4YmFmMjQzODgiCiAgICB9CiAgfQp9", "coPz3nyuTT/5h+yVQjwCha9oQiE/Yj2ngAEVt4CIS4rsB9jEFgiVV7x83kW4twW3kpZ0y37/eha99DRiHWUYj+4pnM/1dhxbLI20rX3gh5J6gSZ7b/CNqoY7Qx3xWMkUgOF5Ezlx+hU8t4OFTMxudcByQFkF3uHPD6cTwGYeYZ+uq3mT1/W6Jayivh/EhvVJgcoa6Zxfa7vRYnMYnZ6z5OVPPTLXVkQwAbEKIZapS0P5V/pb6uIh057DbEk8y5w5naRp7FjhdCW06tDHmz3/vNLD7LPrRMjqwKnm0L+3asg3oLJytL1DZrI9497ec41A6F5i5cOWbsRmJ9YBsBXf11bf6wyDKoXLgzKMD6KrIqFlU0uZGP3tOm3nZ2mzVgaveAPDQvzUVArLPYgUYvET2odqsdHfY1/ZXonN61GVlP2hvfEqS3PvuvA+FzY8qVWFihmgQ5dSgQ61JCZB0dcMOwRN54sZ6Pgwqf/6qK4Ds2A7hcpSj+Zdted2PTBL5pFu453DJigGYkkDpbld+ZqWPbkaS/Mr3xwwE1C1bkX36HdsedJN66tcahapWrzqf7ojwog10Lnq/LZb79pyFM//kxm9NfUzV+vzw7LedwOc8K+4BtvlPW/pOTcYptjBukgtJuxCKJBM00sKrQLMXZGmryo1nS8tkMb/GcDj2QGUQ6E="), smith));
            JSONObject endExplorer = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/endexplorer.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxODUxNDI3MDYwNCwKICAicHJvZmlsZUlkIiA6ICIwNWQ0NTNiZWE0N2Y0MThiOWI2ZDUzODg0MWQxMDY2MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJFY2hvcnJhIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2YxMzZmN2I3NzY5Nzg3MTlkNzNmMmNmMDc0NTQ0MDExYzVhMmU3MzQzZDA0NzczNTU1Yjk2Y2UxYWMxMmZmNDciCiAgICB9CiAgfQp9", "MG+KahD3AEWyGY4XfE7ngQT/qxF9kkpwB3ViSF7GgyMne+duy7amDy9jAvsry4fibtPfof1mJRu9hFSXFFq0sH7tryRM0sm0WVVxfavRHcUafe3UfqPcA6w/NOKnxF2bPIUy9SQAbrFdkAFkgvzWWkrCS5UcuHufC2Ezqehg4Z82etIkIsg7QCWCQ0beqDUcpKGLe9I1oAiqkKx90TIQ2x2lIhX1ZjyZ9DFpp9wQwm1rlPBejHWk238XyVKRP9HE22uoTXy4TU+AqovpTim1jn5ojhre7+LieksXHFBxADbMrukaKqTzaNMCuZ8t+RYoClge5jytfuil53IKpaJqbBUd5+qVJdmj0qrnGQ42ZO95KQM0N8SyOHE858Fx5ZVS+qBCYU0vouuayJR9zYyzS70eU7KL3lkX9Hfe7+ndpPo4OPeI9NEaLJ7JXy7m7vKe3dvZHGX99q64UWEIdchzCRfYFi6V3JmiedL/w1GSqOOcnK8/aO5C74oYwSTO0i1jtFZuwN9i4CyyefnnanRcQURxEidyDSKmQFqN0IRXr6Rg7eHZLgrvLV5H3P2adDCrhI8MTFfGzGkwSNazdleLwUsTISlYXdwl4rASxhRs0Hllv5Tu9gWW+2LLFL6GmgockbsRMxIX1Xl6z5pnL/P1394z65nb2ySxu8p4QxIGysE="), endExplorer));
            JSONObject netherMaster = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/nethermaster.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYwMDM3NDg0NTgyNSwKICAicHJvZmlsZUlkIiA6ICI4MmM2MDZjNWM2NTI0Yjc5OGI5MWExMmQzYTYxNjk3NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJOb3ROb3RvcmlvdXNOZW1vIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzFmNzhiYjVkMDI3MmIyNDgyNmEwZjdmY2E2ZWRlN2IyOWJkNzE0YzIyYzNhNWRiYzYyOTA1M2E1YzgxMDBlM2MiCiAgICB9CiAgfQp9", "NEEcixB7tuQ/0Htyh9BpI2HslvEWvNswnqLZyxHaCAxlob8Z4Jhz6TP93lf8RnGFR578M7axc6LZuXKiHSa1mA7UY9a4NW0lDscfMRs691SQxgw6e0SzfhE0lRzr7YEU44WW0xD9sSGvXlgBrOwaZ+CQ5GsOS83DuD7PbYnydffFV5jyeYBleYgNKk8SGgH7XOH8/68Y9UEfiZb2bim6y3WfN0EycHsmz6owiDlfAAl+4Fw6D0NLAppWFPu7h3DKxzxD0jr20XX7ez9PjuQtYID3R6aohVz+v5gOk4ZhlPpd4OtMsGH6AA2MrhxydzNR3rYSD+A6T23NdkJm2kA51K6VMaaOTHnTd1T3GIh6wPY1ToludIsZ9FKNj5MOSnGEKahuH1F5MZEgJbcXwwsrLxtLbXpjxi3OuT3cchQht7g2+HpoTzHBRJFWPO81n2mOVxoE2OZNOQxruLXvy7QqzPafZseXrKFf11tOnJsKFliteXg2Ex+7ZHFqf4hdNp1dQpk4zShTQqkydcUfKIa3zhrh1TrKAH2ZKN50hrM3MkAoWYQgtTGxjZrewc4JhXiW39Vk/SPG2oTrQYtGHkrVZbnqgQluYeVfoJhhS4MbSeXVxYw13cxhQPeMy5w5i5LLXdvRkbIEXuJquuc4oCoALofTOPh9ErFSFXzTo6nTxDg="), netherMaster));
            JSONObject sell = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/sell.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYwMDM3NDg0NTgyNSwKICAicHJvZmlsZUlkIiA6ICI4MmM2MDZjNWM2NTI0Yjc5OGI5MWExMmQzYTYxNjk3NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJOb3ROb3RvcmlvdXNOZW1vIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzFmNzhiYjVkMDI3MmIyNDgyNmEwZjdmY2E2ZWRlN2IyOWJkNzE0YzIyYzNhNWRiYzYyOTA1M2E1YzgxMDBlM2MiCiAgICB9CiAgfQp9", "NEEcixB7tuQ/0Htyh9BpI2HslvEWvNswnqLZyxHaCAxlob8Z4Jhz6TP93lf8RnGFR578M7axc6LZuXKiHSa1mA7UY9a4NW0lDscfMRs691SQxgw6e0SzfhE0lRzr7YEU44WW0xD9sSGvXlgBrOwaZ+CQ5GsOS83DuD7PbYnydffFV5jyeYBleYgNKk8SGgH7XOH8/68Y9UEfiZb2bim6y3WfN0EycHsmz6owiDlfAAl+4Fw6D0NLAppWFPu7h3DKxzxD0jr20XX7ez9PjuQtYID3R6aohVz+v5gOk4ZhlPpd4OtMsGH6AA2MrhxydzNR3rYSD+A6T23NdkJm2kA51K6VMaaOTHnTd1T3GIh6wPY1ToludIsZ9FKNj5MOSnGEKahuH1F5MZEgJbcXwwsrLxtLbXpjxi3OuT3cchQht7g2+HpoTzHBRJFWPO81n2mOVxoE2OZNOQxruLXvy7QqzPafZseXrKFf11tOnJsKFliteXg2Ex+7ZHFqf4hdNp1dQpk4zShTQqkydcUfKIa3zhrh1TrKAH2ZKN50hrM3MkAoWYQgtTGxjZrewc4JhXiW39Vk/SPG2oTrQYtGHkrVZbnqgQluYeVfoJhhS4MbSeXVxYw13cxhQPeMy5w5i5LLXdvRkbIEXuJquuc4oCoALofTOPh9ErFSFXzTo6nTxDg="), sell));
            JSONObject zoologist = (JSONObject) parser.parse(new FileReader(this.getDataFolder().getAbsolutePath() + "/npcs/zoologist.json"));
            SkyblockMain.npcRegistry.registerNPC(new ShopNPCEntity(new Property("textures", "eyJ0aW1lc3RhbXAiOjE1NzYxNzAwODUzMTQsInByb2ZpbGVJZCI6IjIzZjFhNTlmNDY5YjQzZGRiZGI1MzdiZmVjMTA0NzFmIiwicHJvZmlsZU5hbWUiOiIyODA3Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82N2U5ODRjMGExODBjOGZhNGJjOGQ2ZWM3MDFlNDljMDgxOWJhZjQxMmYzZThmYmIyMWI2YTM1ZDJiZWY4MjQwIn19fQ==", "q5ia5Q7ti8H8VN4mGg96pSO85oYnO+wHJxOTDoyrUBwLYeLyr68K8CDDl4q7vGqXTXYOrX03WBzGadgRGb0AazX/zE+eqBuRk7MNqf4yyknhximO8fsBVLZUu+YDKB1HWZGX89fOe9d+KavC9pKqHH/fqqjJsaYAl+5DITxDTfn/IyFeTXfhtBQs19rQNdGUshGcYj11jUrGViRtEvkcVvwfEhL+jWRH0g1jAFHz5TiMHy6ekfYRbUOOFIBR7txXsyaPqIixKuoGUYUc5yIJelzNxOF31cjHGlMeeF5hUjzu1L/IpfOkcpLtZOji2EZXGzMjW1rrwgRK8YkH+xU+ZMfObbARx0ikryqXAguIkMzLTpL7pRhgv3QUE5ZxiGg/LdflL1LPdJWeiChYn+z83vNxDnutzMg33yFhuULdWqOmb4dFO6SckxhYIkVpc/qaweq4PgnxgiZTQ8cZK7RU/V3KqbHfXrmEbHmz0MLLGBJeLxwi/Wy1hErvbDDvvFtp2Q8nVlh1gWSnfV79+Z5nhE2Po/V+W9Ak9RrO5cy0byYatK8dKNEm5iSb7fZXhdZT4zH5ednyJlsYX0AJqgsMrvekeUSs+ni75aGZGq4n3xyiNlRyKvVl/qu1XCs+zoedE9f/mDoxD7kXRwl3kysDzOs3WNm5si6tZXbry6U0eKY="), zoologist));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSellableItems(JSONParser parser) {
        try {
            JSONObject sellablesJson = (JSONObject) parser.parse(new FileReader(SkyblockMain.instance.getDataFolder().getAbsolutePath() + "/items.json"));
            for (Object o : sellablesJson.keySet()) {
                String itemName = o.toString();
                String[] comp = itemName.split(":");
                ItemStack shopItem = null;

                if (comp[0].equals("skyblock")) {
                    shopItem = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.valueOf(comp[1].toUpperCase()));
                } else if (comp[0].equals("minecraft")) {
                    shopItem = new ItemStack(Material.valueOf(comp[1].toUpperCase()));
                }

                if (shopItem != null) {
                    ShopNPCEntity.sellables.add(new ShopItem(shopItem, Integer.parseInt(sellablesJson.get(o).toString()), 1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void setCommandExecutors() {
        this.getCommand("warp").setExecutor(new WarpCommand());
        this.getCommand("create_world").setExecutor(new CreateWorldCommand());
        this.getCommand("fp_test").setExecutor(new FakePlayerTestCommand());
        this.getCommand("structurizer").setExecutor(new StructurizerCommand());
        this.getCommand("sell").setExecutor(new SellCommand());
        this.getCommand("itembrowser").setExecutor(new ItemBrowser());
        this.getCommand("visit").setExecutor(new VisitCommand());
    }

    @Override
    public void onDisable() {
        SkyblockMain.worldRegistry.unloadAll();
        SkyblockMain.worldRegistry.saveToConfig(this.getDataFolder().getAbsolutePath() + "/world_registry.yaml");
        SkyblockMain.moneyHandler.saveData();
        SkyblockMain.databaseHandler.closeConnection();
        this.saveWorldData();
    }

    public void saveWorldData() {
        try {
            Files.deleteIfExists(SkyblockMain.worldDataPath.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(SkyblockMain.worldDataPath);

        for (Location location : BlockPlaceListener.specialBlocks.keySet()) {
            fileConfig.set(this.locationToString(location), BlockPlaceListener.specialBlocks.get(location));
        }

        try {
            fileConfig.save(SkyblockMain.worldDataPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWorldData() {
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(SkyblockMain.worldDataPath);

        for (String s : fileConfig.getKeys(false)) {
            Location location = this.locationFromString(s);
            ItemStack itemStack = fileConfig.getItemStack(s);
            BlockPlaceListener.specialBlocks.put(location, itemStack);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public String locationToString(Location location) {
        String s = "";
        s += location.getWorld().getName();
        s += ",";
        s += (int) location.getX();
        s += ",";
        s += (int) location.getY();
        s += ",";
        s += (int) location.getZ();
        return s;
    }

    public Location locationFromString(String string) {
        String[] values = string.split(",");
        World world = getServer().getWorld(values[0]);
        double x = Double.parseDouble(values[1]);
        double y = Double.parseDouble(values[2]);
        double z = Double.parseDouble(values[3]);
        return new Location(world, x, y, z);
    }
}