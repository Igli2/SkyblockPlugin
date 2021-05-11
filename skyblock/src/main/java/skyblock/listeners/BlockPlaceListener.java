package skyblock.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.entities.Lutumite;
import skyblock.entities.ShadowWarrior;
import skyblock.entities.Sunshir;
import skyblock.registries.EntityRegistry;
import skyblock.registries.ItemRegistry;

import java.util.HashMap;

public class BlockPlaceListener implements Listener {
    public static HashMap<Location, ItemStack> specialBlocks = new HashMap<>();

    @EventHandler
    @SuppressWarnings("unused")
    public void blockPlaceEvent(BlockPlaceEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }

        // save special blocks extra
        if (event.getItemInHand().getItemMeta() != null && event.getItemInHand().getItemMeta().hasDisplayName()) { // check if item is a custom item
            if (ItemRegistry.isItemStackEqual(event.getItemInHand(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOW_WARRIOR_SPAWN_EGG))) { // unplaceable spawn eggs...
                ShadowWarrior shadowWarrior = new ShadowWarrior(event.getBlock().getLocation().add(0.5, 0.5, 0.5));
                ((CraftWorld)event.getBlock().getWorld()).getHandle().addEntity(shadowWarrior);
                removeItem(event);
                event.setCancelled(true);
            } else if (ItemRegistry.isItemStackEqual(event.getItemInHand(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUNSHIR_SPAWN_EGG))) {
                Sunshir sunshir = new Sunshir(event.getBlock().getLocation().add(0.5, 0.5, 0.5));
                ((CraftWorld)event.getBlock().getWorld()).getHandle().addEntity(sunshir);
                removeItem(event);
                event.setCancelled(true);
            } else if (ItemRegistry.isItemStackEqual(event.getItemInHand(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ICICLE_SPAWN_EGG))) {
                EntityRegistry.spawnIcicle(event.getBlock().getLocation().add(0.5, 0, 0.5));
                removeItem(event);
                event.setCancelled(true);
            } else if (ItemRegistry.isItemStackEqual(event.getItemInHand(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.LUTUMITE_SPAWN_EGG))) {
                Lutumite lutumite = new Lutumite(event.getBlock().getLocation().add(0.5, 0.5, 0.5));
                ((CraftWorld)event.getBlock().getWorld()).getHandle().addEntity(lutumite);
                removeItem(event);
                event.setCancelled(true);
            } else if (event.getItemInHand().getType() == Material.PLAYER_HEAD) { // check if placing it makes sence
                ItemStack placed = event.getItemInHand().clone();
                placed.setAmount(1);
                BlockPlaceListener.specialBlocks.put(event.getBlockPlaced().getLocation(), placed);
            } else {
                event.setCancelled(true);
            }
        }
    }

    private void removeItem(BlockPlaceEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            item.setAmount(item.getAmount() - 1);
            event.getPlayer().getInventory().setItemInMainHand(item);
        } else if (event.getHand() == EquipmentSlot.OFF_HAND) {
            ItemStack item = event.getPlayer().getInventory().getItemInOffHand();
            item.setAmount(item.getAmount() - 1);
            event.getPlayer().getInventory().setItemInOffHand(item);
        }
    }
}
