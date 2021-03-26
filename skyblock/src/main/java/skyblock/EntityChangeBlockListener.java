package skyblock;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener implements Listener {
    App plugin;

    public EntityChangeBlockListener(App plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void entityChangeBlockEvent(EntityChangeBlockEvent event) {
        //disable endermen picking up blocks
        plugin.getLogger().info(event.getEntityType().toString());
        if (event.getEntity() != null && event.getEntityType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}
