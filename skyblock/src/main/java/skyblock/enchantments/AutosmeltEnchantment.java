package skyblock.enchantments;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;

public class AutosmeltEnchantment extends EnchantmentBase {
    public static final HashMap<Material, Material> smelted = new HashMap<Material, Material>() {{
        put(Material.COBBLESTONE, Material.STONE);
        put(Material.IRON_ORE, Material.IRON_INGOT);
        put(Material.GOLD_ORE, Material.GOLD_INGOT);
        put(Material.COAL_ORE, Material.COAL);
        put(Material.LAPIS_ORE, Material.LAPIS_LAZULI);
        put(Material.REDSTONE_ORE, Material.REDSTONE);
        put(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP);
        put(Material.DIAMOND_ORE, Material.DIAMOND);
        put(Material.EMERALD_ORE, Material.EMERALD);
        put(Material.NETHER_QUARTZ_ORE, Material.QUARTZ);
        put(Material.NETHER_GOLD_ORE, Material.GOLD_NUGGET);
        put(Material.STONE, Material.SMOOTH_STONE);
        put(Material.NETHERRACK, Material.NETHER_BRICK);
        put(Material.ACACIA_LOG, Material.CHARCOAL);
        put(Material.BIRCH_LOG, Material.CHARCOAL);
        put(Material.DARK_OAK_LOG, Material.CHARCOAL);
        put(Material.JUNGLE_LOG, Material.CHARCOAL);
        put(Material.OAK_LOG, Material.CHARCOAL);
        put(Material.SPRUCE_LOG, Material.CHARCOAL);
        put(Material.SAND, Material.GLASS);
    }};

    public double getChance() {
        return 0.1;
    }

    public String getName() {
        return "Autosmelt";
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean appliesOn(ItemStack itemStack) {
        return (EnchantmentBase.isPickaxe(itemStack) || EnchantmentBase.isAxe(itemStack) || super.appliesOn(itemStack));
    }

    public Collection<ItemStack> onBlockBreak(Collection<ItemStack> drops, BlockBreakEvent event, int level) {
        for (ItemStack itemStack : drops) {
            Material m = itemStack.getType();
            for (Material m1 : smelted.keySet()) {
                if (m == m1) {
                    itemStack.setType(smelted.get(m1));
                }
            }
        }

        return drops;
    }
}
