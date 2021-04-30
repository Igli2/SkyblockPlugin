package skyblock.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;
import skyblock.utils.ShadowWarriorBoss;

import java.util.HashMap;

public class BlockPlaceListener implements Listener {
    public static HashMap<Location, ItemStack> specialBlocks = new HashMap<>();

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }

        // save special blocks extra
        if (event.getItemInHand().getItemMeta() != null && event.getItemInHand().getItemMeta().hasDisplayName()) { // check if item is a custom item
            if (ItemRegistry.isItemStackEqual(event.getItemInHand(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOW_WARRIOR_SPAWN_EGG))) { // unplaceable spawn eggs...
                ShadowWarriorBoss.spawn(event.getBlock().getWorld(), event.getBlockPlaced().getLocation());
                ItemStack hand = event.getPlayer().getInventory().getItemInMainHand();
                hand.setAmount(hand.getAmount() - 1);
                event.getPlayer().getInventory().setItemInMainHand(hand);
                event.setCancelled(true);
            } else if (event.getItemInHand().getType() == Material.PLAYER_HEAD) { // check if placing it makes sence
                ItemStack placed = event.getItemInHand().clone();
                placed.setAmount(1);
                BlockPlaceListener.specialBlocks.put(event.getBlockPlaced().getLocation(), placed);
            } else {
                event.setCancelled(true);
            }
        }
    }
}
