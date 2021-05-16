package skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import skyblock.SkyblockMain;
import skyblock.utils.*;

public class InventoryClickListener implements Listener {
    @EventHandler()
    public void inventoryClickEvent(InventoryClickEvent event) {
        // crafting table handling
        if (event.getView().getTitle().equals("Crafting Table")) {
            if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                event.setCancelled(true);
                return;
            }
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
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> Anvil.updateContents(event.getInventory(), event.getWhoClicked()));

            // make glass pane placeholders not obtainable
            for (int i : Anvil.GLASS_PANES) {
                if (event.getRawSlot() == i) {
                    event.setCancelled(true);
                    return;
                }
            }
            if (event.getRawSlot() == Anvil.EXP) {
                event.setCancelled(true);
                return;
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
                if(npc instanceof ShopNPCEntity && event.getView().getTitle().equals(npc.getEntity().getName())) {
                    ShopNPCEntity shopNpc = (ShopNPCEntity) npc;
                    if(event.getClickedInventory() != null) {
                        if (event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
                            shopNpc.sellItem((Player) event.getWhoClicked(), event.getCurrentItem(), event.getInventory());
                        } else {
                            shopNpc.buyOffer((Player) event.getWhoClicked(), event.getCurrentItem(), event.isShiftClick(), event.getSlot(), event.getInventory());
                        }
                        event.setCancelled(true);
                    }
                    break;
                } else if(npc instanceof BankerNPCEntity && event.getView().getTitle().equals(npc.getEntity().getName())) {
                    BankerNPCEntity.onInventoryClick(event);
                }
            }
        }
    }
}
