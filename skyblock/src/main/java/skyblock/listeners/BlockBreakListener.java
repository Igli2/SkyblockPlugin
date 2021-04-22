package skyblock.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.enchantments.EnchantmentBase;
import skyblock.enchantments.EnchantmentRegistry;
import skyblock.registries.ItemRegistry;

import java.util.HashMap;

public class BlockBreakListener implements Listener {
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
    public void blockBreakEvent(BlockBreakEvent event) {
        if (!event.getPlayer().isOp() && !event.getPlayer().getWorld().getName().equals(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }

        if (!event.isCancelled()) {
            ItemStack itemHeld = event.getPlayer().getInventory().getItemInMainHand();
            // geode drop
            if (itemHeld.getType() != Material.AIR && !itemHeld.getItemMeta().hasDisplayName() && itemHeld.getType() == Material.STONE_PICKAXE) {
                if (event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.STONE) {
                    if (Math.random() > 0.995) {
                        event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GEODE));
                    }
                }
            }
            // archeologist's pickaxe
            if (itemHeld.getType() != Material.AIR && itemHeld.getItemMeta().hasDisplayName() && itemHeld.getItemMeta().getDisplayName().equals("Archeologist's Pickaxe")) {
                if (event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.STONE) {
                    double random = Math.random();
                    double counter = 0;
                    for (Material m : oreDropChances.keySet()) {
                        double dropChance = oreDropChances.get(m);
                        if (random < (dropChance + counter)) {
                            event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(m));
                            event.setExpToDrop(event.getExpToDrop() + 3);
                            event.setDropItems(false);
                            break;
                        }
                        counter += dropChance;
                    }
                }
            }

            // get special blocks from extra file
            Location location = event.getBlock().getLocation();
            ItemStack specialBlock = BlockPlaceListener.specialBlocks.get(location);
            if (specialBlock != null) {
                event.setDropItems(false);
                // drop special item instead
                if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
                    event.getBlock().getWorld().dropItem(location, specialBlock);
                }
                // remove special item from hashmap
                BlockPlaceListener.specialBlocks.remove(location);
            }

            this.applyEnchantments(event);

            if(itemHeld.equals(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.TREE_CAPITATOR))) {
                if(this.isWood(event.getBlock().getType())) {
                    Material toBreak = event.getBlock().getType();
                    event.getBlock().breakNaturally();
                    event.setCancelled(true);
                    this.breakNeighboursWithType(toBreak, event.getBlock().getLocation(), 10);
                }
            }
        }
    }

    private void applyEnchantments(BlockBreakEvent event) {
        for (EnchantmentBase enchantment : EnchantmentRegistry.enchantments) {
            ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
            if (EnchantmentBase.hasEnchantment(tool, enchantment)) {
                enchantment.onBlockBreak(event, EnchantmentBase.getEnchantmentLevel(tool, enchantment));
            }
        }
    }

    private boolean isWood(Material material) {
        return material.toString().endsWith("WOOD") || material.toString().endsWith("LOG");
    }

    private void breakNeighboursWithType(Material material, Location block, int depth) {
        if(depth > 0) {
            for(int x = block.getBlockX() - 1; x < block.getBlockX() + 2; x++) {
                for(int y = block.getBlockY() - 1; y < block.getBlockY() + 2; y++) {
                    for(int z = block.getBlockZ() - 1; z < block.getBlockZ() + 2; z++) {
                        if(x != block.getBlockX() || y != block.getBlockY() || z != block.getBlockZ()) {
                            Location neighbour = new Location(block.getWorld(), x, y, z);
                            if(neighbour.getBlock().getType() == material) {
                                neighbour.getBlock().breakNaturally();
                                this.breakNeighboursWithType(material, neighbour, depth - 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
