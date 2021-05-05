package skyblock.listeners.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class TreeCapitator implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            if(ItemRegistry.isItemStackEqual(itemHeld, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREE_CAPITATOR))) {
                if(this.isWood(event.getBlock().getType())) {
                    Material toBreak = event.getBlock().getType();
                    event.getBlock().breakNaturally();
                    event.setCancelled(true);
                    this.breakNeighboursWithType(toBreak, event.getBlock().getLocation(), event.getPlayer());
                }
            }
        }
    }

    private boolean isWood(Material material) {
        return material.toString().endsWith("WOOD") || material.toString().endsWith("LOG");
    }

    private void breakNeighboursWithType(Material material, Location block, Player player) {
        for(int x = block.getBlockX() - 1; x < block.getBlockX() + 2; x++) {
            for(int y = block.getBlockY() - 1; y < block.getBlockY() + 2; y++) {
                for(int z = block.getBlockZ() - 1; z < block.getBlockZ() + 2; z++) {
                    if(x != block.getBlockX() || y != block.getBlockY() || z != block.getBlockZ()) {
                        Location neighbour = new Location(block.getWorld(), x, y, z);
                        if(neighbour.getBlock().getType() == material) {
                            neighbour.getBlock().breakNaturally();

                            if (player.getFoodLevel() <= 0) {return;}
                            if (Math.random() < 0.15) {
                                player.setFoodLevel(player.getFoodLevel() - 1);
                            }

                            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> this.breakNeighboursWithType(material, neighbour, player), 2);
                        }
                    }
                }
            }
        }
    }
}
