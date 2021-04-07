package skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import skyblock.utils.CraftingTable;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        // open own crafting menu
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !(event.getPlayer().isSneaking() && event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR)) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CRAFTING_TABLE) {
                event.setCancelled(true);
                event.getPlayer().openInventory(CraftingTable.getInventory());
            }
        }

        // prevent farmland trampling
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}
