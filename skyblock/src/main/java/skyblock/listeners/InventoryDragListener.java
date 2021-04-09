package skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import skyblock.SkyblockMain;
import skyblock.utils.CraftingTable;
import skyblock.utils.NPCEntity;
import skyblock.utils.ShopNPCEntity;

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
