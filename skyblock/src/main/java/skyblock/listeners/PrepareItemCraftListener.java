package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import skyblock.SkyblockMain;

public class PrepareItemCraftListener implements Listener {

    @EventHandler
    public void prepareItemCraftEvent(PrepareItemCraftEvent event) {
        if (event.getRecipe() != null && event.getRecipe().getResult() != null) {
            ItemStack result = event.getRecipe().getResult();
            SkyblockMain.instance.getLogger().info(String.valueOf(result.getType().getMaxDurability()));
            if (result.getType().getMaxDurability() > 0) {
                ItemMeta itemMeta = result.getItemMeta();
                itemMeta.setUnbreakable(true);
                result.setItemMeta(itemMeta);
                event.getInventory().setResult(result);
            }
        }
    }
}