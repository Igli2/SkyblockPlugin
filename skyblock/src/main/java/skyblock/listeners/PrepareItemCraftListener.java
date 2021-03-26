package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import skyblock.App;

public class PrepareItemCraftListener implements Listener {
    App plugin;

    public PrepareItemCraftListener(App plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void prepareItemCraftEvent(PrepareItemCraftEvent event) {

    }
}
