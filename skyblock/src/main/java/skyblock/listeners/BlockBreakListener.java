package skyblock.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            // geode drop
            if (itemHeld.getType() != Material.AIR && !itemHeld.getItemMeta().hasDisplayName() && itemHeld.getType() == Material.STONE_PICKAXE) {
                if (event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.STONE) {
                    if (Math.random() > 0.995) {
                        event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GEODE));
                    }
                }
            }

            // get special blocks from extra file
            Location location = event.getBlock().getLocation();
            ItemStack specialBlock = BlockPlaceListener.specialBlocks.get(location);
            if (specialBlock != null) {
                event.setDropItems(false);
                // drop special item instead
                if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
                    event.getBlock().getWorld().dropItem(location, specialBlock);
                }
                // remove special item from hashmap
                BlockPlaceListener.specialBlocks.remove(location);
            }
        }
    }
}
