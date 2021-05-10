package skyblock.listeners;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import skyblock.entities.Minion;

public class MinionListener implements Listener {

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        if(((CraftEntity) event.getRightClicked()).getHandle() instanceof Minion) {
            net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftEntity) event.getRightClicked()).getHandle();
            event.setCancelled(true);
            event.getPlayer().openInventory(((Minion)nmsEntity).getInventory());
            event.getPlayer().sendMessage("OUCH!");
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        System.out.println(event.getInventory().getHolder().toString());
        if(event.getInventory().getHolder() instanceof Minion) {
            ((Minion)event.getInventory().getHolder()).updateProgram();
        }
    }
}
