package skyblock.listeners.entities;

import net.minecraft.server.v1_16_R3.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import skyblock.entities.Icicle;
import skyblock.registries.EntityRegistry;

public class IcicleListeners implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void playerDeathEvent(PlayerDeathEvent event) {
        if (event.getEntity().getName().equals("§bIcicle")) {
            event.setDeathMessage("");
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void playerChangeWorldEvent(PlayerChangedWorldEvent event) {
        for (Entity entity : EntityRegistry.entities) {
            if (entity instanceof Icicle) {
                Icicle icicle = (Icicle) entity;
                if (entity.getWorld().getWorld().getName().equals(event.getPlayer().getWorld().getName())) {
                    icicle.sendSpawnToPlayer(event.getPlayer());
                } else {
                    icicle.sendDeathToPlayer(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (Entity entity : EntityRegistry.entities) {
            if (entity instanceof Icicle) {
                Icicle icicle = (Icicle) entity;
                if (entity.getWorld().getWorld().getName().equals(event.getPlayer().getWorld().getName())) {
                    icicle.sendSpawnToPlayer(event.getPlayer());
                }
            }
        }
    }
}
