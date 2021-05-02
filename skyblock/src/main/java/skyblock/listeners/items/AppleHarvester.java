package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class AppleHarvester implements Listener {
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            if (ItemRegistry.isItemStackEqual(itemHeld, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.APPLE_HARVESTER))) {
                if (event.getBlock().getType().toString().endsWith("LEAVES")) {
                    if (Math.random() < 0.15) {
                        event.setDropItems(false);
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.APPLE));
                    }
                }
            }
        }
    }
}
