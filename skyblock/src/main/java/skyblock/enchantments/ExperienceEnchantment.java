package skyblock.enchantments;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ExperienceEnchantment extends EnchantmentBase {
    public double getChance() {
        return 0.1;
    }

    public String getName() {
        return "Experience";
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean appliesOn(ItemStack itemStack) {
        return (EnchantmentBase.isSword(itemStack) || EnchantmentBase.isBow(itemStack) || EnchantmentBase.isPickaxe(itemStack) || EnchantmentBase.isAxe(itemStack) || super.appliesOn(itemStack));
    }

    public void onKill(EntityDeathEvent event, int level) {
        int amount = level * (int) Math.round(Math.random() + Math.random());
        event.setDroppedExp(event.getDroppedExp() + amount);
    }

    public void onBlockBreak(BlockBreakEvent event, int level) {
        int amount = level * (int) Math.round(Math.random() + Math.random());
        event.setExpToDrop(event.getExpToDrop() + amount);
    }
}
