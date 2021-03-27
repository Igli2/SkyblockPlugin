package skyblock.listeners;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import skyblock.SkyblockMain;

public class PrepareItemCraftListener implements Listener {
    public static final Material ALL_TOOLS[] = new Material[] { Material.CARROT_ON_A_STICK,
            Material.WARPED_FUNGUS_ON_A_STICK, Material.FLINT_AND_STEEL, Material.WOODEN_SHOVEL,
            Material.WOODEN_PICKAXE, Material.WOODEN_AXE, Material.WOODEN_HOE };

    SkyblockMain plugin;

    public PrepareItemCraftListener(SkyblockMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void prepareItemCraftEvent(PrepareItemCraftEvent event) {
        if (event.getRecipe() != null && event.getRecipe().getResult() != null) {
            ItemStack result = event.getRecipe().getResult();
            for (Material m : PrepareItemCraftListener.ALL_TOOLS) {
                if (m == result.getType()) {
                    ItemMeta itemMeta = result.getItemMeta();
                    itemMeta.setUnbreakable(true);
                    result.setItemMeta(itemMeta);
                    event.getInventory().setResult(result);
                }
            }
        }
    }
}
