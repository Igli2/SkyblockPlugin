package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

import skyblock.SkyblockMain;

public class PrepareItemCraftListener implements Listener {
    SkyblockMain plugin;

    public PrepareItemCraftListener(SkyblockMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void prepareItemCraftEvent(PrepareItemCraftEvent event) {

    }
}
