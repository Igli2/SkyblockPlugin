package skyblock.listeners.items;

import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;
import skyblock.utils.BiomeSticks;

public class DesertBiomeStick implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void playerInteractEvent(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && ItemRegistry.isItemStackEqual(item, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.DESERT_BIOME_STICK))) {
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                // change size
                BiomeSticks.setSize(item);
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                // set biome
                BiomeSticks.setBiome(event, item, Biome.DESERT);
            }
        }
    }
}
