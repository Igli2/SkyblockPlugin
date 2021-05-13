package skyblock.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;

public class BlockBreakListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    @SuppressWarnings("unused")
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }

        if (!event.isCancelled()) {
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

            this.applyEnchantments(event);
        }
    }

    private void applyEnchantments(BlockBreakEvent event) {
        for (EnchantmentBase enchantment : EnchantmentRegistry.enchantments) {
            ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
            if (EnchantmentBase.hasEnchantment(tool, enchantment)) {
                enchantment.onBlockBreak(event, EnchantmentBase.getEnchantmentLevel(tool, enchantment));
            }
        }
    }
}
