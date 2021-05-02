package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class Geode implements Listener {
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            if (ItemRegistry.isItemStackEqual(itemHeld, new ItemStack(Material.STONE_PICKAXE))) {
                if (event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.STONE) {
                    if (Math.random() > 0.995) {
                        ItemStack geode = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GEODE);
                        event.setDropItems(false);
                        event.setCancelled(true);
                        event.getBlock().setType(Material.AIR);
                        SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), geode), 1);
                    }
                }
            }
        }
    }
}
