package skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;
import skyblock.utils.Anvil;
import skyblock.utils.CraftingTable;

public class PlayerInteractListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    @SuppressWarnings("unused")
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            if (event.getItem() != null && event.getItem().getType().isEdible()) {
                event.setUseInteractedBlock(Event.Result.DENY);
            } else {
                event.setCancelled(true);
            }
            return;
        }

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

        if (ItemRegistry.isItemStackEqual(event.getItem(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUN_PEARL))) {
            event.setCancelled(true);
        } else if (ItemRegistry.isItemStackEqual(event.getItem(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ICE_PEARL))) {
            event.setCancelled(true);
        }
    }
}
