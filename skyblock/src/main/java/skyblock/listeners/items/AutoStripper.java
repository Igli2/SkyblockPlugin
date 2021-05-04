package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.HashMap;

public class AutoStripper implements Listener {
    private static final HashMap<Material, Material> strippables = new HashMap<Material, Material>() {{
        put(Material.OAK_LOG, Material.STRIPPED_OAK_LOG);
        put(Material.OAK_WOOD, Material.STRIPPED_OAK_WOOD);
        put(Material.SPRUCE_LOG, Material.STRIPPED_SPRUCE_LOG);
        put(Material.SPRUCE_WOOD, Material.STRIPPED_SPRUCE_WOOD);
        put(Material.BIRCH_LOG, Material.STRIPPED_BIRCH_LOG);
        put(Material.BIRCH_WOOD, Material.STRIPPED_BIRCH_WOOD);
        put(Material.ACACIA_LOG, Material.STRIPPED_ACACIA_LOG);
        put(Material.ACACIA_WOOD, Material.STRIPPED_ACACIA_WOOD);
        put(Material.JUNGLE_LOG, Material.STRIPPED_JUNGLE_LOG);
        put(Material.JUNGLE_WOOD, Material.STRIPPED_JUNGLE_WOOD);
        put(Material.DARK_OAK_LOG, Material.STRIPPED_DARK_OAK_LOG);
        put(Material.DARK_OAK_WOOD, Material.STRIPPED_DARK_OAK_WOOD);
        put(Material.WARPED_STEM, Material.STRIPPED_WARPED_STEM);
        put(Material.WARPED_HYPHAE, Material.STRIPPED_WARPED_HYPHAE);
        put(Material.CRIMSON_STEM, Material.STRIPPED_CRIMSON_STEM);
        put(Material.CRIMSON_HYPHAE, Material.STRIPPED_CRIMSON_HYPHAE);
    }};

    @EventHandler
    @SuppressWarnings("unused")
    public void playerInteractEvent(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) {return;}

        if (ItemRegistry.isItemStackEqual(item, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.AUTO_STRIPPER))) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                boolean a = false;
                for (ItemStack i : event.getPlayer().getInventory().getContents()) {
                    for (Material m : strippables.keySet()) {
                        if (i != null && m == i.getType()) {
                            i.setType(strippables.get(m));
                            a = true;
                            break;
                        }
                    }
                }
                if (a) {
                    item.setAmount(item.getAmount() - 1);
                }
            }
        }
    }
}
