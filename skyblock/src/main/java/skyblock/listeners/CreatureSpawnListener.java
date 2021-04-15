package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {
    @EventHandler
    public void CreatureSpawnEvent(CreatureSpawnEvent event) {
        if (event.getEntity().getWorld().getName().equals("lobby")) {
            event.setCancelled(true);
        }
    }
}
