package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

import skyblock.App;

public class CraftItemListener implements Listener {
    App plugin;

    public CraftItemListener(App plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void craftItemEvent(CraftItemEvent event) {
        //make tools unbreakable
        if (event.isShiftClick()) {
            //make ALL tools unbreakable
        } else {
            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
            itemMeta.setUnbreakable(true);
            event.getCurrentItem().setItemMeta(itemMeta);
        }
    }
}
