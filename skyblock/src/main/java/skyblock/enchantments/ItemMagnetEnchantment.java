package skyblock.enchantments;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ItemMagnetEnchantment extends EnchantmentBase {
    @Override
    public double getChance() {
        return 0.1;
    }

    @Override
    public String getName() {
        return "Itemmagnet";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean appliesOn(ItemStack itemStack) {
        return (EnchantmentBase.isEnchantable(itemStack) || super.appliesOn(itemStack));
    }

    @Override
    public void onKill(EntityDeathEvent event, int level) {
        Player player = event.getEntity().getKiller();
        giveOrDropItems(player, event.getDrops());
        event.getDrops().clear();
    }

    @Override
    public Collection<ItemStack> onBlockBreak(Collection<ItemStack> drops, BlockBreakEvent event, int level) {
        giveOrDropItems(event.getPlayer(), drops);
        drops = new ArrayList<>();
        return drops;
    }

    public static void giveOrDropItems(Player player, Collection<ItemStack> drops) {
        for (ItemStack itemStack : drops) {
            HashMap<Integer, ItemStack> excess = player.getInventory().addItem(itemStack);
            for (int id : excess.keySet()) {
                player.getWorld().dropItem(player.getLocation(), excess.get(id));
            }
        }
    }

    private void giveOrDropItems(Player player, List<ItemStack> drops) {
        for (ItemStack itemStack : drops) {
            HashMap<Integer, ItemStack> excess = player.getInventory().addItem(itemStack);
            for (int id : excess.keySet()) {
                player.getWorld().dropItem(player.getLocation(), excess.get(id));
            }
        }
    }
}
