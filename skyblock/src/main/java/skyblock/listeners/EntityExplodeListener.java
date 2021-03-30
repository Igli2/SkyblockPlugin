package skyblock.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {
    @EventHandler
    public void entityExplodeEvent(EntityExplodeEvent event) {
        if (event.getEntity().getType() == EntityType.CREEPER) {
            event.setCancelled(true);
        }
    }
}
