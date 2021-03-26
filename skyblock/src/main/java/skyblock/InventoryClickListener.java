package skyblock;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener {
    App plugin;

    public InventoryClickListener(App plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        // disable renaming of items
        if (event.getView().getType() == InventoryType.ANVIL) {
            if (event.getRawSlot() == 2) {
                if (event.getInventory().getItem(0) != null && event.getInventory().getItem(2) != null) {
                    if (event.getInventory().getItem(0).getItemMeta().getDisplayName() != event.getInventory()
                            .getItem(2).getItemMeta().getDisplayName()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
