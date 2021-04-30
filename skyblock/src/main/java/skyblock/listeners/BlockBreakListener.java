package skyblock.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;
import skyblock.registries.ItemRegistry;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }

        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();

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

            if(itemHeld.equals(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREE_CAPITATOR))) {
                if(this.isWood(event.getBlock().getType())) {
                    Material toBreak = event.getBlock().getType();
                    event.getBlock().breakNaturally();
                    event.setCancelled(true);
                    this.breakNeighboursWithType(toBreak, event.getBlock().getLocation(), 10);
                }
            }
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

    private boolean isWood(Material material) {
        return material.toString().endsWith("WOOD") || material.toString().endsWith("LOG");
    }

    private void breakNeighboursWithType(Material material, Location block, int depth) {
        if(depth > 0) {
            for(int x = block.getBlockX() - 1; x < block.getBlockX() + 2; x++) {
                for(int y = block.getBlockY() - 1; y < block.getBlockY() + 2; y++) {
                    for(int z = block.getBlockZ() - 1; z < block.getBlockZ() + 2; z++) {
                        if(x != block.getBlockX() || y != block.getBlockY() || z != block.getBlockZ()) {
                            Location neighbour = new Location(block.getWorld(), x, y, z);
                            if(neighbour.getBlock().getType() == material) {
                                neighbour.getBlock().breakNaturally();
                                this.breakNeighboursWithType(material, neighbour, depth - 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
