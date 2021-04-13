package skyblock.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class ShadowWarriorBoss {
    public static void setAttributes(WitherSkeleton boss) {
        boss.setCustomName("Shadow Warrior");
        boss.setCustomNameVisible(true);
        // todo: loot
        boss.getEquipment().setHelmet(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_HELMET));
        // todo: other armor
        boss.setCanPickupItems(false);
        // todo: health bar

        // set attributes
        AttributeInstance attrInstance = boss.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        attrInstance.setBaseValue(200);

        boss.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 255));
    }

    public static void spawn(World world, Location location) {
        WitherSkeleton boss = world.spawn(location, WitherSkeleton.class);
        ShadowWarriorBoss.setAttributes(boss);
    }
}
