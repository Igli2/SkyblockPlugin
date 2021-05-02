package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class PlayerBucketFillListener implements Listener {
    @EventHandler
    public void playerBucketFillEvent(PlayerBucketFillEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
        }
    }
}
