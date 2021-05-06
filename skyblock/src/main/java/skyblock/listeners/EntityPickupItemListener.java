package skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemListener implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void entityPickupItemEvent(EntityPickupItemEvent event) {
        if (event.isCancelled()) {return;}

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!player.isOp() && !(player.getWorld().getName().equals(player.getUniqueId().toString()) || player.getWorld().getName().equals("lobby"))) {
                event.setCancelled(true);
            }
        }
    }
}
