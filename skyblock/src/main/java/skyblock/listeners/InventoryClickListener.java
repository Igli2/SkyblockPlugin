package skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import skyblock.SkyblockMain;
import skyblock.utils.CraftingTable;
import skyblock.utils.NPCEntity;
import skyblock.utils.ShopNPCEntity;

public class InventoryClickListener implements Listener {
    @EventHandler()
    public void inventoryClickEvent(InventoryClickEvent event) {
        this.cancelAnvilRenaming(event);

        // crafting table handling
        if (event.getView().getTitle().equals("Crafting Table")) {
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> CraftingTable.updateContents(event.getInventory()));

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
                    CraftingTable.onCraft(event.getInventory(), event.getWhoClicked(), event.isShiftClick());
                }
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

    @SuppressWarnings("ConstantConditions")
    private void cancelAnvilRenaming(InventoryClickEvent event) {
        if (event.getView().getType() == InventoryType.ANVIL) {
            if (event.getRawSlot() == 2) {
                if (event.getInventory().getItem(0) != null && event.getInventory().getItem(2) != null) {
                    if (!event.getInventory().getItem(0).getItemMeta().getDisplayName().equals(event.getInventory()
                            .getItem(2).getItemMeta().getDisplayName())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
