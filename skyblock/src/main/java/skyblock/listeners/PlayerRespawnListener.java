package skyblock.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import skyblock.SkyblockMain;

public class PlayerRespawnListener implements Listener {
    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent event) {
        // teleport to lobby if player died and has neither bed nor respawn anchor
        if (!event.isBedSpawn() && !event.isAnchorSpawn()) {
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> event.getPlayer().teleport(new Location(Bukkit.getWorld("lobby"), 0.5, 110, 0.5)));
        }
    }
}
