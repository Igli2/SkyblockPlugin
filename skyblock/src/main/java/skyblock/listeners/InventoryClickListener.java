package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import skyblock.SkyblockMain;
import skyblock.utils.CraftingTable;

public class InventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
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
        // crafting table handling
        if (event.getView().getTitle().equals("Crafting Table")) {
            // TODO: FUCKING BUKKIT IS TRASH BECAUSE IT'S NOT UPDATING THE DAMN FUCKING INVENTORY
            // TODO: THAT'S WHY YOU HAVE TO WRITE UGLY CODE LIKE THIS:
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, new Runnable() {
                @Override
                public void run() {
                    CraftingTable.updateContents(event.getInventory());
                }
            });

            if (event.getRawSlot() < 45) {
                // make glass pane placeholders not obtainable
                for (int i : CraftingTable.GLASS_PANES) {
                    if (event.getRawSlot() == i) {
                        event.setCancelled(true);
                        return;
                    }
                }
                // craft the item if there is a valid recipe
                if (event.getRawSlot() == CraftingTable.RESULT) {
                    event.setCancelled(true);
                    CraftingTable.onCraft(event.getInventory());
                }
            }
        }
    }
}
