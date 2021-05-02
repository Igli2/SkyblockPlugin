package skyblock.entities;

import net.minecraft.server.v1_16_R3.EntityBlaze;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;


public class ShadowWarriorMinion extends EntityBlaze {

    private ShadowWarrior owner;

    public ShadowWarriorMinion(EntityTypes<? extends EntityBlaze> entitytypes, World world, ShadowWarrior owner) {
        super(entitytypes, world);
        this.owner = owner;

        this.getBukkitEntity().setCustomName(ChatColor.RED + "Minion");
        this.getBukkitEntity().setCustomNameVisible(true);
    }

    public ShadowWarriorMinion(Location location, ShadowWarrior owner) {
        super(EntityTypes.BLAZE, ((CraftWorld)location.getWorld()).getHandle());

        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.owner = owner;

        this.getBukkitEntity().setCustomName(ChatColor.RED + "Minion");
        this.getBukkitEntity().setCustomNameVisible(true);
    }

    @Override
    public void die() {
        super.die();
        this.owner.minionDied();
    }
}
