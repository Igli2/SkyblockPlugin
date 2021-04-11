package skyblock.enchantments;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VenomousEnchantment extends EnchantmentBase {
    public double getChance() {
        return 0.15;
    }

    public String getName() {
        return "Venomous";
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean appliesOn(ItemStack itemStack) {
        return (EnchantmentBase.isSword(itemStack) || super.appliesOn(itemStack));
    }

    public void onAttack(EntityDamageByEntityEvent event, int level) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) event.getEntity();
            le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 2 * level, 1));
        }
    }
}
