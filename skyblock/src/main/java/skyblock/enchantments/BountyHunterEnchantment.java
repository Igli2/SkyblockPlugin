package skyblock.enchantments;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;

public class BountyHunterEnchantment extends EnchantmentBase {
    public double getChance() {
        return 0.25;
    }

    public String getName() {
        return "Bountyhunter";
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean appliesOn(ItemStack itemStack) {
        return (EnchantmentBase.isBow(itemStack) || EnchantmentBase.isSword(itemStack) || EnchantmentBase.isAxe(itemStack) || super.appliesOn(itemStack));
    }

    public void onKill(EntityDeathEvent event, int level) {
        Player player = event.getEntity().getKiller();
        SkyblockMain.moneyHandler.addMoney(player, level * 5);
    }
}
