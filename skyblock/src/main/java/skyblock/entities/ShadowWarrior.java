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

import javax.annotation.Nullable;

public class ShadowWarrior extends EntitySkeletonWither {

    private static final int MAX_MINIONS = 7;

    private BossBar bossBar;
    private int minionCount;

    public ShadowWarrior(EntityTypes<? extends EntitySkeletonWither> entitytypes, World world) {
        super(entitytypes, world);
        this.minionCount = 0;
    }

    public ShadowWarrior(Location location) {
        super(EntityTypes.WITHER_SKELETON, ((CraftWorld)location.getWorld()).getHandle());
        this.minionCount = 0;

        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setNoAI(false);

        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(100);
        this.setHealth(100);

        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(64);

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

    @Nullable
    @Override
    public EntityItem a(IMaterial imaterial) {
        return null;
    }

    @Nullable
    @Override
    public EntityItem a(IMaterial imaterial, int i) {
        return null;
    }

    @Nullable
    @Override
    public EntityItem a(net.minecraft.server.v1_16_R3.ItemStack itemstack) {
        return null;
    }

    @Nullable
    @Override
    public EntityItem a(net.minecraft.server.v1_16_R3.ItemStack itemstack, float f) {
        return null;
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(5, new PathfinderGoalRandomStrollLand(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    @Override
    protected void dropDeathLoot(DamageSource damagesource, int i, boolean flag) {
        ItemStack shadowSteelIngot = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SHADOWSTEEL_INGOT);
        shadowSteelIngot.setAmount(1 + SkyblockMain.random.nextInt(3));
        this.getBukkitEntity().getWorld().dropItemNaturally(this.getBukkitEntity().getLocation(), shadowSteelIngot);
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        if(damagesource.isMagic()) return false;

        if(this.minionCount == 0) {
            boolean result = super.damageEntity0(damagesource, f);
            this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
            return result;
        }

        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getGoalTarget() != null) {
            EntityEquipment equipment = ((WitherSkeleton)this.getBukkitEntity()).getEquipment();
            if(equipment != null) {
                equipment.getItemInMainHand();
                if (equipment.getItemInMainHand().getType() == org.bukkit.Material.BOW) {
                    if (this.getGoalTarget().getBukkitEntity().getLocation().distance(this.getBukkitEntity().getLocation()) < 4.0) {
                        equipment.setItemInMainHand(new ItemStack(org.bukkit.Material.NETHERITE_SWORD));
                    }
                } else if (equipment.getItemInMainHand().getType() == org.bukkit.Material.NETHERITE_SWORD) {
                    if (this.getGoalTarget().getBukkitEntity().getLocation().distance(this.getBukkitEntity().getLocation()) > 5.0) {
                        equipment.setItemInMainHand(new ItemStack(org.bukkit.Material.BOW));
                    }
                }
            }

            if(SkyblockMain.random.nextDouble() < 0.005) {
                this.spawnMinions(Math.min(1 + SkyblockMain.random.nextInt(3), ShadowWarrior.MAX_MINIONS - this.minionCount));
            }
        }
    }

    @Override
    public void die() {
        super.die();
        this.bossBar.removeAll();
    }

    public void minionDied() {
        if(--this.minionCount == 0) {
            this.bossBar.setColor(BarColor.PURPLE);
        }
    }

    private void spawnMinions(int count) {
        for(int m = 0; m < count; m++) {
            int offX =  5 - SkyblockMain.random.nextInt(11);
            int offZ =  5 - SkyblockMain.random.nextInt(11);

            Location minionPos = this.getBukkitEntity().getLocation().clone();
            minionPos.setX(minionPos.getBlockX() + offX);
            minionPos.setZ(minionPos.getZ() + offZ);
            if (minionPos.getWorld() != null) {
                minionPos.setY(minionPos.getWorld().getHighestBlockYAt(minionPos.getBlockX(), minionPos.getBlockZ()) + 1);
            }

            ShadowWarriorMinion minion = new ShadowWarriorMinion(minionPos, this);

            this.getWorld().addEntity(minion);
        }

        this.minionCount += count;
        this.bossBar.setColor(BarColor.RED);
    }
}
