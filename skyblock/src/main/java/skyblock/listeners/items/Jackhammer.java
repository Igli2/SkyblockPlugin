package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.Collections;

public class Jackhammer implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void blockBreakEvent(BlockBreakEvent event) {
        if (event.isCancelled()) {return;}

        ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
        if (ItemRegistry.isItemStackEqual(itemHeld, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.JACKHAMMER))) {
            if (event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.STONE) {
                double rand = Math.random();
                if (rand < 0.1) {
                    SkyblockMain.applyBlockBreakEnchantmentsAndDropItems(Collections.singletonList(new ItemStack(Material.GRAVEL)), event);
                } else if (rand < 0.2) {
                    SkyblockMain.applyBlockBreakEnchantmentsAndDropItems(Collections.singletonList(new ItemStack(Material.SAND)), event);
                }
            }
        }
    }
}
