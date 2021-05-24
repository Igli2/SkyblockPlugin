package skyblock.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;

import java.util.Collections;

public class BlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    @SuppressWarnings("unused")
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }

        if (!event.isCancelled()) {
            // get special blocks from extra file
            Location location = event.getBlock().getLocation();
            ItemStack specialBlock = BlockPlaceListener.specialBlocks.get(location);
            if (specialBlock != null) {
                // drop special item instead
                SkyblockMain.applyBlockBreakEnchantmentsAndDropItems(Collections.singletonList(specialBlock), event);
                // remove special item from hashmap
                BlockPlaceListener.specialBlocks.remove(location);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    @SuppressWarnings("unused")
    public void blockBreakEventLast(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            SkyblockMain.applyBlockBreakEnchantmentsAndDropItems(event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer()), event);
        }
    }
}
