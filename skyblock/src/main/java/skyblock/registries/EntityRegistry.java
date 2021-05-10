package skyblock.registries;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import skyblock.entities.Icicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityRegistry {
    public static List<Entity> entities = new ArrayList<>();

    public static void spawnIcicle(Location location) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.AQUA + "Icicle");
        gameProfile.getProperties().put("textures", new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYyMDUwMzc5OTUwOCwKICAicHJvZmlsZUlkIiA6ICI0ZWQ4MjMzNzFhMmU0YmI3YTVlYWJmY2ZmZGE4NDk1NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJGaXJlYnlyZDg4IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2NjMTM1M2RmY2Q2Y2RhYWEwYzUwOWYyMjhkZTZjYzlmOWM2ZDM5ZDcyYzM3ZGNlYmM4NDk0OTc0ZWExYTMzYmYiCiAgICB9CiAgfQp9", "hLWY8V2a4fNuzRSWt+cV4vEyAmvyBEIPKP/8bMMD4dgSBxXI2a/l2NjARvPFbtZkQBmVGG2WJYtrgbR9j76nrPpeAfVhq6e8Xw4bxTrIjZDf1S3IbgRXYl8e3zfXHpIxaqBpFaxe+Kg8eNl6cEUYUnOuAfsNhMsG6NoruQOSP4lU1EKqdAYyu8Mgh3juLmPK3mKiTL3tPFrk+rH1nOG3SqAhcdB/GO+IuSUaQJMa8XdAgK4OfNFhNc8zzJREpwmDzXU9mYhGpfVTW/jAKf58IA8MaJ7gg5CRivT3YUPiLqVoHOUTnOQz+kMxglaWTkoKjl73+CLBqKRAmaQX2enX/oNV3XW+BZ9pDibIVEkI8YTylSRixXoxXjMkfivDUyVUY+rhB6+UqI/iw8ynAEd76UHYnTP0mOVWS7h4QoYOo5N/UOQFFtsP16DnUXQkEz1shZRssKFTFh8kP5EhxSgyuNydwwOEmC28Ii4kX8z0rARHhx4ZNIeofhFCq+PL4mx17XFo01IQOwThgwK+bBTlN4Y26A8uV4IMfVCeNo/VvQjfxDIG7SeanuyGEbgu+GHMBkpCOghAgkyPsZfr9+uGWQ+VvV/gDyL3FpBbM5UsIVElDGsaKitNmtQcOfFCSvanPBl7Zz6PyysCfCeYifvWz8nbsy0WLYSH6hYJkzkNKeM="));

        Icicle icicleCustom = new Icicle(server, worldServer, gameProfile, new PlayerInteractManager(worldServer), location.getX(), location.getY(), location.getZ());

        EntityRegistry.entities.add(icicleCustom);
    }
}
