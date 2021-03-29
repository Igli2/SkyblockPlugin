package skyblock.utils;

public class WorldInfo {
    public enum WorldType {
        PLAYER_WORLD,
        PUBLIC_WORLD
    }

    private WorldType type;
    private boolean loaded;
    private String worldName;

    public WorldInfo(WorldType type, String worldName, boolean loaded) {
        this.type = type;
        this.loaded = loaded;
        this.worldName = worldName;
    }

    public void setStatus(boolean loaded)  {
        this.loaded = loaded;
    }

    public WorldType getType() {
        return type;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public String getWorldName() {
        return worldName;
    }

    @Override
    public String toString() {
        return this.worldName + ';' + this.type.toString();
    }

    public static WorldInfo fromString(String worldInfoStr) {
        String[] components = worldInfoStr.split(";");

        return new WorldInfo(WorldType.valueOf(components[1]), components[0], false);
    }
}
