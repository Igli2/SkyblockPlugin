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
        this.setHealth(12.5f);
        this.maxFuseTicks = 15;
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5);
        this.getAttributeInstance(GenericAttributes.KNOCKBACK_RESISTANCE).setValue(1.0);

        this.getBukkitEntity().setCustomName(ChatColor.GREEN + "Speedy Creeper");
        this.getBukkitEntity().setCustomNameVisible(true);
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        if(!damagesource.isExplosion()) {
            return super.damageEntity0(damagesource, f);
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
    }
}
