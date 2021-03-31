package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import skyblock.registries.ItemRegistry;

public class PrepareItemCraftListener implements Listener {
    @EventHandler
    public void prepareItemCraftEvent(PrepareItemCraftEvent event) {
        if (event.getRecipe() != null) {
            ItemStack result = event.getRecipe().getResult();
            if (result.getType().getMaxDurability() > 0) {
                ItemRegistry.makeUnbreakable(result);
                event.getInventory().setResult(result);
            }
        }
    }
}
