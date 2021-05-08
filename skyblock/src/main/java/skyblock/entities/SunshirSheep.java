package skyblock.entities;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Sheep;

import javax.annotation.Nullable;

public class SunshirSheep extends EntitySheep {
    private Sunshir owner;

    public SunshirSheep(EntityTypes<? extends EntitySheep> entitytypes, World world, Sunshir owner) {
        super(entitytypes, world);

        this.owner = owner;

        Sheep bukkitSheep = (Sheep) this.getBukkitEntity();
        bukkitSheep.setCustomName("Grumm");
        bukkitSheep.setCustomNameVisible(false);
        bukkitSheep.setAI(false);
        bukkitSheep.setCollidable(false);
        bukkitSheep.setColor(DyeColor.WHITE);
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        this.owner.damageEntity0(damagesource, f);
        return true;
    }

    public void teleport(CraftEntity entity) {
        this.getBukkitEntity().teleport(entity);
    }

    @Override
    public boolean canShear() {
        return false;
    }

    @Override
    public void g(@Nullable EntityHuman entityhuman) {
    }

    @Override
    public boolean k(ItemStack itemstack) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        Location location = this.getBukkitEntity().getLocation();
        location.add(random.nextFloat() * 1.5 - 0.75, 1.5 + random.nextFloat() / 5 - 0.1, random.nextFloat() * 1.5 - 0.75);
        location.getWorld().spawnParticle(org.bukkit.Particle.REDSTONE, location, 5, new org.bukkit.Particle.DustOptions(Color.WHITE, 3));
    }
}
