package skyblock.registries;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import skyblock.generators.SkyblockChunkGenerator;
import skyblock.utils.WorldInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WorldRegistry {

    private HashMap<String, WorldInfo> worlds;

    public WorldRegistry() {
        this.worlds = new HashMap<>();
    }

    public void addWorld(WorldInfo world) {
        this.worlds.put(world.getWorldName(), world);
    }

    public boolean removeWorld(String world) {
        if(this.hasWorld(world)) {
            this.worlds.remove(world);
            return true;
        } else {
            return false;
        }
    }

    public boolean hasWorld(String world) {
        return this.worlds.containsKey(world);
    }

    public WorldInfo.WorldType getWorldType(String world) {
        if(this.hasWorld(world)) {
            return this.worlds.get(world).getType();
        } else {
            return null;
        }
    }

    public boolean isWorldLoaded(String world) {
        if(this.hasWorld(world)) {
            return this.worlds.get(world).isLoaded();
        } else {
            return false;
        }
    }

    public boolean unloadWorld(String world) {
        if(this.isWorldLoaded(world)) {
            Bukkit.unloadWorld(world, true);
            this.worlds.get(world).setStatus(false);
            return true;
        }

        return false;
    }

    public boolean loadWorld(String world) {
        if(!this.isWorldLoaded(world)) {
            WorldCreator wc = new WorldCreator(world);
            wc.generator(new SkyblockChunkGenerator());
            Bukkit.createWorld(wc);
            this.worlds.get(world).setStatus(true);
            return true;
        }

        return false;
    }

    public void unloadAll() {
        for(WorldInfo world : this.worlds.values()) {
            Bukkit.unloadWorld(world.getWorldName(), true);
        }
    }

    public void loadPublicWorlds() {
        for(WorldInfo world : this.worlds.values()) {
            if(this.getWorldType(world.getWorldName()) == WorldInfo.WorldType.PUBLIC_WORLD) {
                this.loadWorld(world.getWorldName());
            }
        }
    }

    public static WorldRegistry loadFromConfig(String filePath) {
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(new File(filePath));
        } catch(Exception e) {
            return new WorldRegistry();
        }

        WorldRegistry registry = new WorldRegistry();

        for(Map.Entry<String, Object> world : config.getValues(false).entrySet()) {
            registry.addWorld(WorldInfo.fromString((String) world.getValue()));
        }

        return registry;
    }

    public boolean saveToConfig(String filePath) {
        YamlConfiguration config = new YamlConfiguration();

        for(WorldInfo world : this.worlds.values()) {
            config.set(world.getWorldName(), world.toString());
        }

        try {
            config.save(new File(filePath));
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
