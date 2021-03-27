package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import skyblock.SkyblockMain;

public class CraftItemListener implements Listener {
    SkyblockMain plugin;

    public CraftItemListener(SkyblockMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void craftItemEvent(CraftItemEvent event) {
    }
}
