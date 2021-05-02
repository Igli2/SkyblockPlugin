package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;
import skyblock.registries.ItemRegistry;

import java.util.HashMap;

public class ArcheologistsPickaxe implements Listener {
    private static final HashMap<Material, Double> oreDropChances = new HashMap<Material, Double>() {{
        put(Material.COAL, 0.05);
        put(Material.IRON_ORE, 0.025);
        put(Material.GOLD_ORE, 0.01);
        put(Material.LAPIS_LAZULI, 0.01);
        put(Material.REDSTONE, 0.02);
        put(Material.DIAMOND, 0.005);
        put(Material.EMERALD, 0.0025);
        put(Material.ANCIENT_DEBRIS, 0.002);
        put(Material.QUARTZ, 0.015);
    }};

    @EventHandler
    @SuppressWarnings("unused")
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            if (ItemRegistry.isItemStackEqual(itemHeld, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ARCHEOLOGISTS_PICKAXE))) {
                if (event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.STONE) {
                    int fortuneLevel = itemHeld.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                    int autosmeltLevel = EnchantmentBase.getEnchantmentLevel(itemHeld, EnchantmentRegistry.enchantments.get(2));

                    double random = Math.random();
                    double counter = 0;
                    for (Material m : oreDropChances.keySet()) {
                        double dropChance = oreDropChances.get(m) * (fortuneLevel / 10f + 1f);
                        if (autosmeltLevel == 1) { // apply auto-smelt enchantment
                            if (m == Material.GOLD_ORE) {
                                m = Material.GOLD_INGOT;
                            } else if (m == Material.IRON_ORE) {
                                m = Material.IRON_INGOT;
                            }
                        }
                        if (random < (dropChance + counter)) {
                            event.setDropItems(false);
                            Material finalM = m;
                            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> event.getPlayer().getWorld().dropItem(event.getBlock().getLocation().add(0.5, 0.5, 0.5), new ItemStack(finalM)), 1);
                            event.getBlock().getWorld().spawn(event.getBlock().getLocation().add(1,0,0), ExperienceOrb.class).setExperience(3);
                            break;
                        }
                        counter += dropChance;
                    }
                }
            }
        }
    }
}
