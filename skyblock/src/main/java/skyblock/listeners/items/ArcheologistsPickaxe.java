package skyblock.listeners.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.Collections;
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
                    double random = Math.random();
                    double counter = 0;
                    for (Material m : oreDropChances.keySet()) {
                        double dropChance = oreDropChances.get(m) * (fortuneLevel / 10f + 1f);
                        if (random < (dropChance + counter)) {
                            SkyblockMain.applyBlockBreakEnchantmentsAndDropItems(Collections.singletonList(new ItemStack(m)), event);
                            event.getBlock().getWorld().spawn(event.getBlock().getLocation().add(0.5,0.5,0.5), ExperienceOrb.class).setExperience(3);
                            break;
                        }
                        counter += dropChance;
                    }
                }
            }
        }
    }
}
