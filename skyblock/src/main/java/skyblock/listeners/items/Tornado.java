package skyblock.listeners.items;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class Tornado implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ItemRegistry.isItemStackEqual(event.getItem(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TORNADO))) {
                for (Entity entity : event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), 5, 5, 5)) {
                    if (entity.isOnGround() && !(entity.getEntityId() == event.getPlayer().getEntityId())) {
                        entity.setVelocity(new Vector(0, 1, 0));
                    }
                }
                // spawn particles
                Location playerLocation = event.getPlayer().getLocation();
                for (int y = 0; y < 18; y += 1) {
                    double dist = y / 12f + 0.5;
                    for (int j = 0; j < 20; j++) {
                        int i = j * 18 + y * 6; // j * 18: angle, y * 8: offset
                        double posX = playerLocation.getX() + Math.cos(Math.toRadians(i)) * dist;
                        double posY = playerLocation.getY() + y / 4f;
                        double posZ = playerLocation.getZ() + Math.sin(Math.toRadians(i)) * dist;
                        Location particleLocation = new Location(event.getPlayer().getWorld(), posX, posY, posZ);
                        event.getPlayer().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, particleLocation, 0, 0, 0, 0);
                    }
                }
            }
        }
    }
}
