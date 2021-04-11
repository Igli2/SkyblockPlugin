package skyblock.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BlockPlaceListener implements Listener {
    public static HashMap<Location, ItemStack> specialBlocks = new HashMap<>();

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event) {
        // save special blocks extra
        if (event.getItemInHand().getItemMeta() != null && event.getItemInHand().getItemMeta().hasDisplayName()) { // check if item is a special item
            if (event.getItemInHand().getType() == Material.PLAYER_HEAD) { // check if placing it makes sence
                ItemStack placed = event.getItemInHand().clone();
                placed.setAmount(1);
                BlockPlaceListener.specialBlocks.put(event.getBlockPlaced().getLocation(), placed);
            } else {
                event.setCancelled(true);
            }
        }
    }
}
