package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {
    @EventHandler
    public void playerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
        }
    }
}
