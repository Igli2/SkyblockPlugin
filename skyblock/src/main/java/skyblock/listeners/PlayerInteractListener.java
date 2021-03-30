package skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import skyblock.utils.CraftingTable;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !(event.getPlayer().isSneaking() && event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR)) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CRAFTING_TABLE) {
                event.setCancelled(true);
                // open own crafing menu
                event.getPlayer().openInventory(CraftingTable.getInventory());
            }
        }
    }
}
