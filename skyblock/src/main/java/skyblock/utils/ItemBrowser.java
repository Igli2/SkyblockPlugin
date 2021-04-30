package skyblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import javax.annotation.Nonnull;

public class ItemBrowser implements Listener, CommandExecutor {
    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Item Browser") && !event.getWhoClicked().isOp()) {
            event.setCancelled(true);
        }
    }

    public void openBrowser(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Item Browser");

        setItems(inventory, 0);

        player.openInventory(inventory);
    }

    private void setItems(Inventory inventory, int page) {
        int skip = page * 45;
        int slot = 0;
        for (ItemRegistry.SkyblockItems item : ItemRegistry.SkyblockItems.values()) {
            if (skip > 0) {
                skip -= 1;
            } else {
                inventory.setItem(slot, SkyblockMain.itemRegistry.getItemStack(item));
                slot += 1;
            }
        }
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull  Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            openBrowser(player);
            return true;
        }
        return false;
    }
}
