package skyblock.listeners.entities;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import skyblock.entities.Minion;

public class MinionListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        if(((CraftEntity) event.getRightClicked()).getHandle() instanceof Minion) {
            net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftEntity) event.getRightClicked()).getHandle();
            event.setCancelled(true);
            event.getPlayer().openInventory(((Minion)nmsEntity).getInventory());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof Minion) {
            ((Minion)event.getInventory().getHolder()).update();
            ((Minion)event.getInventory().getHolder()).saveContent();
        }
    }

    @EventHandler
    public void onEntitySpawn(ChunkLoadEvent event) {
        for(Entity e : event.getChunk().getEntities()) {
            if(e instanceof ArmorStand && !(((CraftEntity) e).getHandle() instanceof Minion)) {
                Minion.replaceArmorStand((ArmorStand) e);
            }
        }
    }
}
