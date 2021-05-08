package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class CreeperWand implements Listener {

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && ItemRegistry.isItemStackEqual(item, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.CREEPER_WAND)) && event.getPlayer().getCooldown(Material.BAMBOO) == 0) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!event.getPlayer().getInventory().contains(Material.GUNPOWDER)) {
                    return;
                }
                Block target = getTargetBlock(event.getPlayer(), 25);
                if (target != null) {
                    event.getPlayer().getInventory().removeItem(new ItemStack(Material.GUNPOWDER));
                    target.getWorld().createExplosion(target.getLocation(), 1.5f, false, false);
                    event.getPlayer().setCooldown(Material.BAMBOO, 8);
                }
            }
        }
    }

    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            Block next = iter.next();
            if (next.getType() == Material.AIR) {
                lastBlock = next;
            } else {
                return lastBlock;
            }
        }
        return null;
    }
}
