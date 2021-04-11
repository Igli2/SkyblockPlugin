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
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            // apply enchantments on weapons
            for (EnchantmentBase enchantment : EnchantmentRegistry.enchantments) {
                ItemStack weapon = ((Player) event.getDamager()).getInventory().getItemInMainHand();
                if (EnchantmentBase.hasEnchantment(weapon, enchantment)) {
                    enchantment.onAttack(event, EnchantmentBase.getEnchantmentLevel(weapon, enchantment));
                }
            }
        }
    }
}
