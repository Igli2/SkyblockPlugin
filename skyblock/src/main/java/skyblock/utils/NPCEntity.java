package skyblock.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPCEntity {
    private EntityPlayer entity;
    private boolean alive;

    public NPCEntity(String name, Property textures, World world) {
        this.alive = true;
        GameProfile npcProfile = new GameProfile(UUID.randomUUID(), name);
        npcProfile.getProperties().put("textures", textures);

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer craftWorld = ((CraftWorld) world).getHandle();

        this.entity = new EntityPlayer(server, craftWorld, npcProfile, new PlayerInteractManager(craftWorld));
    }

    public EntityPlayer getEntity() {
        if(!this.alive) return null;
        return this.entity;
    }

    public boolean isAlive() {
        return this.alive;
    }


    public void show(Player player) {
        if(this.alive) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this.entity));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this.entity));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(this.entity, (byte) (this.entity.yaw * 256.0f / 360.0f)));

            DataWatcher watcher = this.entity.getDataWatcher();
            watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 255);

            connection.sendPacket(new PacketPlayOutEntityMetadata(this.entity.getId(), watcher, true));
        }
    }

    public void hide(Player player) {
        if(this.alive) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.entity));
            connection.sendPacket(new PacketPlayOutEntityDestroy(this.entity.getId()));
        }
    }

    public void kill() {
        if(this.alive) {
            this.alive = false;
            for(Player p : Bukkit.getOnlinePlayers()) {
                PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this.entity));
                connection.sendPacket(new PacketPlayOutEntityDestroy(this.entity.getId()));
            }
        }
    }

    public void interact(Player player) {
        if(this.alive) {
            player.sendMessage(this.entity.getName() + ": Hello " + player.getName() + "! How are you today?");
        }
    }
}
