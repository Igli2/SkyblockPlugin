package skyblock.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity().getWorld().getName().equals("lobby")) {
            event.setCancelled(true);
            return;
        }

        // disable players taking damage on other islands
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!player.getWorld().getName().equals(player.getUniqueId().toString())) {
                event.setCancelled(true);
                return;
            }
        }

        // disable players attacking entities on other islands
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (!player.isOp() && !player.getWorld().getName().equals(player.getUniqueId().toString())) {
                event.setCancelled(true);
                return;
            }

            // apply enchantments on weapons
            for (EnchantmentBase enchantment : EnchantmentRegistry.enchantments) {
                ItemStack weapon = player.getInventory().getItemInMainHand();
                if (EnchantmentBase.hasEnchantment(weapon, enchantment)) {
                    enchantment.onAttack(event, EnchantmentBase.getEnchantmentLevel(weapon, enchantment));
                }
            }
        }
    }
}
