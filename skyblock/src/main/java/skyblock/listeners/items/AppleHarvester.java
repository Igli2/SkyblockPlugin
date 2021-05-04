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
                        SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> event.getPlayer().getWorld().dropItem(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.APPLE)), 1);
                    }
                }
            } else if (ItemRegistry.isItemStackEqual(itemHeld, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.REINFORCED_APPLE_HARVESTER))) {
                if (event.getBlock().getType().toString().endsWith("LEAVES")) {
                    if (Math.random() < 0.35) {
                        SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> event.getPlayer().getWorld().dropItem(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(Material.APPLE)), 1);
                    }
                }
            }
        }
    }
}
