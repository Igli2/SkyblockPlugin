package skyblock.entities;

import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.util.Vector;

import java.util.UUID;


public class TornadoProjectile extends EntityArmorStand {

    private int lifetime;
    private UUID ownerID;
    private Vector velocity;

    public TornadoProjectile(Location location, int lifetime, Vector velocity, UUID ownerID) {
        super(EntityTypes.ARMOR_STAND, ((CraftWorld)location.getWorld()).getHandle());

        this.lifetime = lifetime;
        this.ownerID = ownerID;
        this.velocity = velocity;

        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setInvisible(true);
        this.getBukkitEntity().setVelocity(velocity);
        this.setInvulnerable(true);

        Location loc = this.getBukkitEntity().getLocation();

        for (int y = 0; y < 18; y += 1) {
            double dist = y / 12f + 0.5;
            for (int j = 0; j < 20; j++) {
                int i = j * 18 + y * 6; // j * 18: angle, y * 8: offset
                double posX = loc.getX() + Math.cos(Math.toRadians(i)) * dist;
                double posY = loc.getY() + y / 4f;
                double posZ = loc.getZ() + Math.sin(Math.toRadians(i)) * dist;
                Location particleLocation = new Location(loc.getWorld(), posX, posY, posZ);
                loc.getWorld().spawnParticle(org.bukkit.Particle.FIREWORKS_SPARK, particleLocation, 0, this.velocity.getX(), 0.1f, this.velocity.getZ());
            }
        }
    }

    @Override
    public boolean isInteractable() {
        return false;
    }

    @Override
    public void tick() {
        this.setPosition(this.locX() + this.velocity.getX(), this.locY() + this.velocity.getY(), this.locZ() + this.velocity.getZ());

        if(--this.lifetime <= 0) {
            this.dead = true;
            return;
        }

        for(org.bukkit.entity.Entity entity : this.getBukkitEntity().getNearbyEntities(2.0, 2.0, 5.0)) {
            if(entity.isOnGround() && !entity.getUniqueId().equals(this.ownerID)) {
                entity.setVelocity(new Vector(0, 1, 0));
            }
        }
    }
}
