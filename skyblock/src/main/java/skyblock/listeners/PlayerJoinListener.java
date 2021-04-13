package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import skyblock.SkyblockMain;
import skyblock.utils.ShadowWarriorBoss;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        SkyblockMain.moneyHandler.setScoreboard(event.getPlayer());
        ShadowWarriorBoss.spawn(event.getPlayer().getWorld(), event.getPlayer().getLocation());
    }
}
