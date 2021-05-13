package skyblock.listeners.entities;

import net.minecraft.server.v1_16_R3.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import skyblock.entities.CaoCaoPlayer;
import skyblock.registries.EntityRegistry;

public class CaoCaoListener implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void playerChangeWorldEvent(PlayerChangedWorldEvent event) {
        for (Entity entity : EntityRegistry.entities) {
            if (entity instanceof CaoCaoPlayer) {
                CaoCaoPlayer caoCaoPlayer = (CaoCaoPlayer) entity;
                if (entity.getWorld().getWorld().getName().equals(event.getPlayer().getWorld().getName())) {
                    caoCaoPlayer.sendSpawnToPlayer(event.getPlayer());
                } else {
                    caoCaoPlayer.sendDeathToPlayer(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (Entity entity : EntityRegistry.entities) {
            if (entity instanceof CaoCaoPlayer) {
                CaoCaoPlayer caoCaoPlayer = (CaoCaoPlayer) entity;
                if (entity.getWorld().getWorld().getName().equals(event.getPlayer().getWorld().getName())) {
                    caoCaoPlayer.sendSpawnToPlayer(event.getPlayer());
                }
            }
        }
    }
}
