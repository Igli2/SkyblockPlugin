package skyblock.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class CaoCaoPlayer extends CustomEntityPlayer {
    public CaoCaoPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager, double x, double y, double z) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
        this.playerConnection = new PlayerConnection(server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), this);
        this.setPosition(x, y, z);

        org.bukkit.inventory.ItemStack zhanlu = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ZHANLU);
        List<Pair<EnumItemSlot, ItemStack>> equipment = new ArrayList<>();
        equipment.add(new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(zhanlu)));

        worldserver.addEntity(this);
        sendSpawnToPlayers(worldserver.getWorld());
        sendEquipmentToPlayers(worldserver.getWorld(), equipment);
        server.getPlayerList().players.removeIf(e -> e.getUniqueID().equals(this.getUniqueID()));
    }
}
