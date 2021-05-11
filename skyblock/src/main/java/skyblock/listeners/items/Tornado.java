package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import skyblock.SkyblockMain;
import skyblock.entities.TornadoProjectile;
import skyblock.registries.ItemRegistry;

public class Tornado implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) {return;}
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ItemRegistry.isItemStackEqual(event.getItem(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TORNADO)) && event.getPlayer().getCooldown(Material.IRON_SWORD) == 0) {
                Vector direction = event.getPlayer().getLocation().getDirection();
                direction.setY(0);

                TornadoProjectile tornadoProjectile = new TornadoProjectile(event.getPlayer().getLocation(), 10, direction, event.getPlayer().getUniqueId());
                ((CraftWorld)event.getPlayer().getWorld()).getHandle().addEntity(tornadoProjectile);

                event.getPlayer().setCooldown(Material.IRON_SWORD, 50);
            }
        }
    }
}
