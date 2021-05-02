package skyblock.entities;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class ShadowWarrior extends EntitySkeletonWither {

    private BossBar bossBar;

    public ShadowWarrior(EntityTypes<? extends EntitySkeletonWither> entitytypes, World world) {
        super(entitytypes, world);
    }

    public ShadowWarrior(Location location) {
        super(EntityTypes.WITHER_SKELETON, ((CraftWorld)location.getWorld()).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setNoAI(false);

        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(100.0);
        this.setHealth(100.0f);

        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(64.0);

        this.bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Shadow Warrior", BarColor.PURPLE, BarStyle.SOLID, BarFlag.DARKEN_SKY, BarFlag.CREATE_FOG, BarFlag.PLAY_BOSS_MUSIC);
        this.bossBar.setProgress(1.0);
        this.bossBar.setVisible(true);

        WitherSkeleton bukkitEntity = (WitherSkeleton)this.getBukkitEntity();

        EntityEquipment equipment = bukkitEntity.getEquipment();
        if (equipment != null) {
            equipment.setHelmet(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_HELMET));
            equipment.setChestplate(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_CHESTPLATE));
            equipment.setLeggings(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_LEGGINGS));
            equipment.setBoots(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_BOOTS));
            equipment.setItemInMainHand(new ItemStack(org.bukkit.Material.NETHERITE_SWORD));
        }
        bukkitEntity.setCanPickupItems(false);

        for(Player player : Bukkit.getOnlinePlayers()) {
            this.bossBar.addPlayer(player);
        }

        this.getBukkitEntity().setCustomName(ChatColor.DARK_PURPLE + "Shadow Warrior");
        this.getBukkitEntity().setCustomNameVisible(true);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(5, new PathfinderGoalRandomStrollLand(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        if(damagesource.isMagic()) return false;
        boolean result = super.damageEntity0(damagesource, f);
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());

        EntityEquipment equipment = ((WitherSkeleton)this.getBukkitEntity()).getEquipment();
        if(equipment != null) {
            equipment.setItemInMainHand(new ItemStack(org.bukkit.Material.BOW));
        }

        return result;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getGoalTarget() != null) {
            EntityEquipment equipment = ((WitherSkeleton)this.getBukkitEntity()).getEquipment();
            if(equipment != null && equipment.getItemInMainHand() != null) {
                if(equipment.getItemInMainHand().getType() == org.bukkit.Material.BOW) {
                    if(this.getGoalTarget().getBukkitEntity().getLocation().distance(this.getBukkitEntity().getLocation()) < 4.0) {
                        equipment.setItemInMainHand(new ItemStack(org.bukkit.Material.NETHERITE_SWORD));
                    }
                } else if(equipment.getItemInMainHand().getType() == org.bukkit.Material.NETHERITE_SWORD) {
                    if(this.getGoalTarget().getBukkitEntity().getLocation().distance(this.getBukkitEntity().getLocation()) > 5.0) {
                        equipment.setItemInMainHand(new ItemStack(org.bukkit.Material.BOW));
                    }
                }
            }
        }
    }

    @Override
    public void die() {
        super.die();
        this.bossBar.removeAll();
    }
}