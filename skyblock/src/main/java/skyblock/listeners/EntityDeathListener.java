package skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;
import skyblock.registries.ItemRegistry;

import java.util.List;

public class EntityDeathListener implements Listener {
    @EventHandler
    public void entityDeathEvent(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        for (ItemStack drop : drops) {
            if (drop.getType().getMaxDurability() > 0) {
                ItemRegistry.makeUnbreakable(drop);
            }
        }

        if (event.getEntity().getKiller() != null) {
            // apply enchantments
            for (EnchantmentBase enchantment : EnchantmentRegistry.enchantments) {
                ItemStack weapon = event.getEntity().getKiller().getInventory().getItemInMainHand();
                if (EnchantmentBase.hasEnchantment(weapon, enchantment)) {
                    enchantment.onKill(event, EnchantmentBase.getEnchantmentLevel(weapon, enchantment));
                }
            }
        }
    }
}
