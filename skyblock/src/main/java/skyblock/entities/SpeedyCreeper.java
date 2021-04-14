package skyblock.entities;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class SpeedyCreeper extends EntityCreeper {

    public SpeedyCreeper(EntityTypes<? extends EntityCreeper> entityTypes, World world) {
        super(entityTypes, world);
    }

    public SpeedyCreeper(Location location) {
        super(EntityTypes.CREEPER, ((CraftWorld)location.getWorld()).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setPowered(true);
        this.setNoAI(false);
        this.setHealth(30.0f);
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        damagesource.getEntity().getBukkitEntity().sendMessage(ChatColor.RED + "Ouch!");
        return super.damageEntity0(damagesource, f);
    }

    @Override
    protected void initPathfinder() {
        super.initPathfinder();
        this.goalSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityCreeper>(this, EntityCreeper.class, false));
    }
}
