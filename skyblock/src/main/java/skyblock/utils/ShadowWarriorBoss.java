package skyblock.utils;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class ShadowWarriorBoss {
    public static void setAttributes(WitherSkeleton boss) {
        EntityEquipment equipment = boss.getEquipment();
        if (equipment != null) {
            equipment.setHelmet(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_HELMET));
            equipment.setChestplate(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_CHESTPLATE));
            equipment.setLeggings(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_LEGGINGS));
            equipment.setBoots(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_BOOTS));
        }
        boss.setCanPickupItems(false);
        // todo: boss bar

        AttributeInstance attrInstance = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attrInstance != null) {
            attrInstance.setBaseValue(100);
            boss.setHealth(100);
        }

        boss.setCustomName(ShadowWarriorBoss.getName() + " " + getHealthText(boss));
        boss.setCustomNameVisible(true);
    }

    public static String getName() {
        return ChatColor.BLACK + "Shadow Warrior";
    }

    public static String getHealthText(WitherSkeleton boss) {
        return getHealthText(boss, 0);
    }

    public static String getHealthText(WitherSkeleton boss, double damage) {
        int newHealth = (int) (boss.getHealth() - damage);
        if (newHealth < 0) {
            newHealth = 0;
        }
        return (ChatColor.RED + "" + newHealth + "❤");
    }

    public static void spawn(World world, Location location) {
        WitherSkeleton boss = world.spawn(location, WitherSkeleton.class);
        ShadowWarriorBoss.setAttributes(boss);
    }

    public static List<ItemStack> getDrops(int looting) {
        List<ItemStack> drops = new ArrayList<>();

        drops.add(new ItemStack(Material.BONE, (int) Math.round(Math.random() * looting * 5 + (Math.random() + 0.3) * 10)));
        ItemStack shadowSteel = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT);
        shadowSteel.setAmount((int) Math.round(Math.random() * looting + Math.random() * 2));
        drops.add(shadowSteel);

        return drops;
    }

    public static void updateEntity(Entity entity, double damage) {
        // entity is always the boss here
        WitherSkeleton boss = (WitherSkeleton) entity;
        entity.setCustomName(ShadowWarriorBoss.getName() + " " + getHealthText(boss, damage));

        // summon normal wither skeletons
        Location l = entity.getLocation();
        for (int x = l.getBlockX() - 5; x < l.getBlockX() + 5; x++) {
            for (int z = l.getBlockZ() - 5; z < l.getBlockZ() + 5; z++) {
                int airCount = 0;
                for (int y = l.getBlockY() + 5; y > l.getBlockY() - 5; y--) {
                    if (l.getWorld() != null && l.getWorld().getBlockAt(x, y, z).getType() == Material.AIR) {
                        airCount += 1;
                    } else {
                        if (airCount >= 2 && Math.random() < 0.002) {
                            Location newLocation = new Location(l.getWorld(), x, y, z);
                            Entity newEntity = l.getWorld().spawnEntity(newLocation, EntityType.WITHER_SKELETON);
                            entity.playEffect(EntityEffect.ENTITY_POOF);
                            newEntity.playEffect(EntityEffect.ENTITY_POOF);
                            l.getWorld().playSound(l, Sound.ENTITY_EVOKER_PREPARE_SUMMON, 10, 1); // volume, pitch
                            return;
                        }
                        airCount = 0;
                    }
                }
            }
        }
    }
}
