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
import org.bukkit.entity.Sheep;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class Sunshir extends EntitySheep {
    private BossBar bossBar;

    public Sunshir(EntityTypes<? extends EntitySheep> entitytypes, World world) {
        super(entitytypes, world);
    }

    public Sunshir(Location location) {
        super(EntityTypes.SHEEP, ((CraftWorld)location.getWorld()).getHandle());

        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setNoAI(false);

        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(80);
        this.setHealth(80);

        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(32);

        this.bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Sunshir", BarColor.PURPLE, BarStyle.SOLID, BarFlag.CREATE_FOG);
        this.bossBar.setProgress(1.0);
        this.bossBar.setVisible(true);

        Sheep bukkitEntity = (Sheep) this.getBukkitEntity();
        bukkitEntity.setCanPickupItems(false);

        for(Player player : Bukkit.getOnlinePlayers()) {
            this.bossBar.addPlayer(player);
        }

        this.getBukkitEntity().setCustomName(ChatColor.AQUA + "Sunshir");
        this.getBukkitEntity().setCustomNameVisible(true);
    }

    // TODO!!! attack player
    /*@Override
    protected void initPathfinder() {
        //this.goalSelector.a(5, new PathfinderGoalRandomStrollLand(this, 1.0D));
        //this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        //this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        //this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, new Class[0]));
        //this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }*/

    @Override
    protected void dropDeathLoot(DamageSource damagesource, int i, boolean flag) {
        if (Math.random() < 0.2) {
            org.bukkit.inventory.ItemStack sunPearl = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.SUN_PEARL);
            this.getBukkitEntity().getWorld().dropItemNaturally(this.getBukkitEntity().getLocation(), sunPearl);
        }
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        boolean result = super.damageEntity0(damagesource, f);
        this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        return result;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void die() {
        super.die();
        this.bossBar.removeAll();
    }
}
