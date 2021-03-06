package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import skyblock.utils.Anvil;
import skyblock.utils.CraftingTable;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals("Crafting Table")) {
            CraftingTable.onInventoryClosed(event.getInventory(), event.getPlayer());
        } else if (event.getView().getTitle().equals("Anvil")) {
            Anvil.onInventoryClosed(event.getInventory(), event.getPlayer());
        }
    }
}
