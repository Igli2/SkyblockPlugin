package skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import skyblock.SkyblockMain;
import skyblock.utils.Anvil;
import skyblock.utils.CraftingTable;
import skyblock.utils.NPCEntity;
import skyblock.utils.ShopNPCEntity;

public class InventoryClickListener implements Listener {
    @EventHandler()
    public void inventoryClickEvent(InventoryClickEvent event) {
        // crafting table handling
        if (event.getView().getTitle().equals("Crafting Table")) {
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> CraftingTable.updateContents(event.getInventory()));

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
                CraftingTable.onCraft(event.getInventory(), event.getWhoClicked(), event.isShiftClick());
            }
        } else if (event.getView().getTitle().equals("Anvil")) {
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> Anvil.updateContents(event.getInventory()));

            // make glass pane placeholders not obtainable
            for (int i : Anvil.GLASS_PANES) {
                if (event.getRawSlot() == i) {
                    event.setCancelled(true);
                    return;
                }
            }

            // craft the item if there is a valid recipe
            if (event.getRawSlot() == Anvil.RESULT) {
                event.setCancelled(true);
                Anvil.onCraft(event.getInventory(), event.getWhoClicked(), event.isShiftClick());
            }
        }

        //shop npc handling
        if(event.getWhoClicked() instanceof Player) {
            for(NPCEntity npc : SkyblockMain.npcRegistry.getNPCs()) {
                if(npc instanceof ShopNPCEntity) {
                    if(event.getView().getTitle().equals(npc.getEntity().getName())) {
                        if(event.getClickedInventory() != null && !event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
                            ((ShopNPCEntity) npc).buyOffer((Player) event.getWhoClicked(), event.getCurrentItem(), event.isShiftClick());
                            event.setCancelled(true);
                        } else {
                            if(event.isShiftClick()) {
                                event.setCancelled(true);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}
