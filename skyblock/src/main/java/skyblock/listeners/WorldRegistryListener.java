package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import skyblock.SkyblockMain;

public class WorldRegistryListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        String worldName = event.getPlayer().getUniqueId().toString();
        if(SkyblockMain.worldRegistry.hasWorld(worldName) && !SkyblockMain.worldRegistry.isWorldLoaded(worldName)) {
            SkyblockMain.worldRegistry.loadWorld(worldName);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        String worldName = event.getPlayer().getUniqueId().toString();
        if(SkyblockMain.worldRegistry.isWorldLoaded(worldName)) {
            SkyblockMain.worldRegistry.unloadWorld(worldName);
        }
    }
}
