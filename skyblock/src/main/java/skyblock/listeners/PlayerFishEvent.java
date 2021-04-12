package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class PlayerFishEvent implements Listener {

    public static final double GRAPPLE_ANGLE = -0.1;
    public static final double GRAPPLE_SPEED = 2.5;

    @EventHandler
    public void onFish(org.bukkit.event.player.PlayerFishEvent event) {
        if(event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().equals(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GRAPPLING_HOOK))) {
            if(event.getState() == org.bukkit.event.player.PlayerFishEvent.State.IN_GROUND) {
                Vector direction = event.getHook().getLocation().toVector().subtract(event.getPlayer().getLocation().toVector());
                Vector force = new Vector(1, -PlayerFishEvent.GRAPPLE_ANGLE * direction.length(), 0).normalize();

                event.getPlayer().setVelocity(event.getPlayer().getVelocity().add(new Vector(direction.normalize().getX(), force.getY(), direction.normalize().getZ()).normalize().multiply(GRAPPLE_SPEED)));
            }
        }
    }
}
