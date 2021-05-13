package skyblock.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class CustomEntityPlayer extends EntityPlayer {
    public CustomEntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
    }

    public void sendEquipmentToPlayers(World world, List<Pair<EnumItemSlot, ItemStack>> equipment) {
        for (Player player : world.getPlayers()) {
            this.sendEquipmentToPlayer(player, equipment);
        }
    }

    public void sendEquipmentToPlayer(Player player, List<Pair<EnumItemSlot, ItemStack>> equipment) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityEquipment(this.getId(), equipment));
    }

    public void sendSpawnToPlayers(World world) {
        for (Player player : world.getPlayers()) {
            this.sendSpawnToPlayer(player);
        }
    }

    public void sendSpawnToPlayer(Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(this, (byte) (0)));

        DataWatcher watcher = this.getDataWatcher();
        watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 255);
        connection.sendPacket(new PacketPlayOutEntityMetadata(this.getId(), watcher, true));
    }

    public void sendDeathToPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.sendDeathToPlayer(player);
        }
    }

    public void sendDeathToPlayer(Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this));
        connection.sendPacket(new PacketPlayOutEntityDestroy(this.getId()));
    }
}
