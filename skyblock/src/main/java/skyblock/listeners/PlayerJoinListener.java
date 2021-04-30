package skyblock.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import skyblock.SkyblockMain;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        SkyblockMain.moneyHandler.setScoreboard(event.getPlayer());

        // teleport to lobby
        if (event.getPlayer().getWorld().getName().equals("world")) {
            event.getPlayer().teleport(new Location(Bukkit.getWorld("lobby"), 2, 110, -3));
        }
    }
}
