package skyblock.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener implements Listener {

    @EventHandler
    public void entityChangeBlockEvent(EntityChangeBlockEvent event) {
        //disable endermen picking up blocks
        if (event.getEntity() != null && event.getEntityType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}
