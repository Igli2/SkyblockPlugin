package skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import skyblock.SkyblockMain;
import skyblock.utils.Anvil;
import skyblock.utils.CraftingTable;
import skyblock.utils.NPCEntity;
import skyblock.utils.ShopNPCEntity;

public class InventoryDragListener implements Listener {
    @EventHandler
    public void inventoryDragEvent(InventoryDragEvent event) {
        if (event.getView().getTitle().equals("Crafting Table")) {
            for (int slot : event.getRawSlots()) {
                if (slot == CraftingTable.RESULT) {
                    event.setCancelled(true);
                    return;
                }
            }
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> CraftingTable.updateContents(event.getInventory()));
        } else if (event.getView().getTitle().equals("Anvil")) {
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> Anvil.updateContents(event.getInventory()));
        }

        //shop npc handling
        if(event.getWhoClicked() instanceof Player) {
            for(NPCEntity npc : SkyblockMain.npcRegistry.getNPCs()) {
                if(npc instanceof ShopNPCEntity) {
                    if(event.getView().getTitle().equals(npc.getEntity().getName())) {
                        for(int slotID : event.getRawSlots()) {
                            if(slotID < event.getInventory().getSize()) {
                                event.setCancelled(true);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}
