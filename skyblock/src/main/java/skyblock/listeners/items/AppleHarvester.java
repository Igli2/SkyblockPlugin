package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.Collections;

public class AppleHarvester implements Listener {
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            if (ItemRegistry.isItemStackEqual(itemHeld, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.APPLE_HARVESTER))) {
                if (event.getBlock().getType() == Material.OAK_LEAVES || event.getBlock().getType() == Material.DARK_OAK_LEAVES) {
                    if (Math.random() < 0.15) {
                        SkyblockMain.applyBlockBreakEnchantmentsAndDropItems(Collections.singletonList(new ItemStack(Material.APPLE)), event);
                    }
                }
            } else if (ItemRegistry.isItemStackEqual(itemHeld, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.REINFORCED_APPLE_HARVESTER))) {
                if (event.getBlock().getType() == Material.OAK_LEAVES || event.getBlock().getType() == Material.DARK_OAK_LEAVES) {
                    if (Math.random() < 0.35) {
                        SkyblockMain.applyBlockBreakEnchantmentsAndDropItems(Collections.singletonList(new ItemStack(Material.APPLE)), event);
                    }
                }
            }
        }
    }
}
