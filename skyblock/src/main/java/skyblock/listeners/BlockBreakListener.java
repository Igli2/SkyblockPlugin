package skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class BlockBreakListener implements Listener {
    SkyblockMain plugin;

    public BlockBreakListener(SkyblockMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            // geode drop
            if (itemHeld.getType() != Material.AIR && !itemHeld.getItemMeta().hasDisplayName() && itemHeld.getType() == Material.STONE_PICKAXE) {
                if (event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.STONE) {
                    if (Math.random() > 0.995) {
                        plugin.getServer().getWorld("World").dropItem(event.getPlayer().getLocation(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.GEODE));
                    }
                }
            }
        }
    }
}
