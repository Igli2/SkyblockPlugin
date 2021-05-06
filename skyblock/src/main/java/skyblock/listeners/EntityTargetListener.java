package skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTargetListener implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void entityTargetEvent(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            Player targetPlayer = (Player) event.getTarget();
            if (!targetPlayer.getWorld().getName().equals(targetPlayer.getUniqueId().toString())) {
                event.setCancelled(true);
            }
        }
    }
}
