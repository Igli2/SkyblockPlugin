package skyblock.entities;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.EntityRegistry;
import skyblock.registries.ItemRegistry;

public class Icicle extends CustomEntityPlayer {
    public Icicle(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager, double x, double y, double z) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
        this.playerConnection = new PlayerConnection(server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), this);
        this.setPosition(x, y, z);
        worldserver.addEntity(this);
        sendSpawnToPlayers(worldserver.getWorld());
        server.getPlayerList().players.removeIf(e -> e.getUniqueID().equals(this.getUniqueID()));
    }

    public void dropDeathLoot() {
        org.bukkit.inventory.ItemStack ice = new org.bukkit.inventory.ItemStack(Material.ICE);
        ice.setAmount(random.nextInt(6) + random.nextInt(6) + 5);
        this.getBukkitEntity().getWorld().dropItemNaturally(this.getBukkitEntity().getLocation(), ice);

        if (Math.random() < 0.05) {
            org.bukkit.inventory.ItemStack icePearl = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ICE_PEARL);
            this.getBukkitEntity().getWorld().dropItemNaturally(this.getBukkitEntity().getLocation(), icePearl);
        }
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        if (damagesource.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) damagesource.getEntity();
            ItemStack weapon = player.inventory.getItemInHand();
            org.bukkit.inventory.ItemStack bukkitWeapon = CraftItemStack.asBukkitCopy(weapon);
            if (bukkitWeapon.getType().toString().contains("PICKAXE")) {
                this.setHealth(this.getHealth() - f);
                this.world.getWorld().playSound(new Location(this.world.getWorld(), this.lastX, this.lastY, this.lastZ), Sound.BLOCK_GLASS_BREAK, 0.4f, 1.5f);
                if (this.getHealth() <= 0) {
                    this.die(damagesource);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        this.sendDeathToPlayers(); //prevent death animation
        this.dropDeathLoot();
        this.world.getWorld().playSound(new Location(this.world.getWorld(), this.lastX, this.lastY, this.lastZ), Sound.BLOCK_GLASS_BREAK, 2.5f, 1);
        EntityRegistry.entities.remove(this);
    }
}
