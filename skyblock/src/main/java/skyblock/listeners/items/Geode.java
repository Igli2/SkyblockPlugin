package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.Collections;

public class Geode implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            if (ItemRegistry.isItemStackEqual(itemHeld, new ItemStack(Material.STONE_PICKAXE))) {
                if (event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.STONE) {
                    if (Math.random() > 0.995) {
                        ItemStack geode = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GEODE);
                        SkyblockMain.applyBlockBreakEnchantmentsAndDropItems(Collections.singletonList(geode), event);
                    }
                }
            }
        }
    }
}
