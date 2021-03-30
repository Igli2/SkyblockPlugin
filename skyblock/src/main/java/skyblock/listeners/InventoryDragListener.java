package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import skyblock.SkyblockMain;
import skyblock.utils.CraftingTable;

public class InventoryDragListener implements Listener {
    @EventHandler
    public void inventoryDragEvent(InventoryDragEvent event) {
        // needed to update crafting tabale inventory after item drag event because inventory click event isn't fired
        if (event.getView().getTitle().equals("Crafting Table")) {
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, new Runnable() {
                @Override
                public void run() {
                    CraftingTable.updateContents(event.getInventory());
                }
            });
        }
    }
}
