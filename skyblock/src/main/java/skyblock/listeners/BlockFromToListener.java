package skyblock.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.inventory.ItemStack;

public class BlockFromToListener implements Listener {
    @EventHandler
    public void blockFromToEvent(BlockFromToEvent event) {
        // get special blocks from extra file
        Location location = event.getToBlock().getLocation();
        ItemStack specialBlock = BlockPlaceListener.specialBlocks.get(location);
        if (specialBlock != null) {
            // disable block drop
            event.getToBlock().getWorld().getBlockAt(event.getToBlock().getLocation()).setType(Material.AIR);
            event.setCancelled(true);
            // drop special item
            event.getToBlock().getWorld().dropItem(location, specialBlock);
            // remove special item from hashmap
            BlockPlaceListener.specialBlocks.remove(location);
        }
    }
}
