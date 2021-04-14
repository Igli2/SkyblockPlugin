package skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import skyblock.utils.Anvil;
import skyblock.utils.CraftingTable;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        // open own crafting menu, anvil
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !(event.getPlayer().isSneaking() && event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR)) {
            Block block = event.getClickedBlock();
            if (block != null && block.getType() == Material.CRAFTING_TABLE) {
                event.setCancelled(true);
                event.getPlayer().openInventory(CraftingTable.getInventory());
            } else if (block != null && (block.getType() == Material.ANVIL || block.getType() == Material.CHIPPED_ANVIL || block.getType() == Material.DAMAGED_ANVIL)) {
                event.setCancelled(true);
                event.getPlayer().openInventory(Anvil.getInventory());
            }
        }

        // prevent farmland trampling
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }
}
