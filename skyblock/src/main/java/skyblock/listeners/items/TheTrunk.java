package skyblock.listeners.items;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class TheTrunk implements Listener {
    @EventHandler
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack itemHeld = player.getInventory().getItemInMainHand();
            if (ItemRegistry.isItemStackEqual(itemHeld, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.THE_TRUNK))) {
                LivingEntity le = (LivingEntity) event.getEntity();
                le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1));
            }
        }
    }
}
