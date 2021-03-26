package skyblock;

//import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {
    App plugin;

    public CreatureSpawnListener(App plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void creatureSpawnEvent(CreatureSpawnEvent event) {
        // allow only specific mobs to spawn
        /*if (event.getEntityType() != EntityType.ZOMBIE && event.getEntityType() != EntityType.SKELETON
                && event.getEntityType() != EntityType.ENDERMAN && event.getEntityType() != EntityType.WITCH
                && event.getEntityType() != EntityType.SLIME && event.getEntityType() != EntityType.SPIDER) {
            event.setCancelled(true);
        }*/
    }
}
