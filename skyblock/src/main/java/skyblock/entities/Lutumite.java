package skyblock.entities;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;

public class Lutumite extends EntitySilverfish {
    private BossBar bossBar;

    public Lutumite(EntityTypes<? extends EntitySilverfish> entitytypes, World world) {
        super(entitytypes, world);
    }

    public Lutumite(Location location) {
        super(EntityTypes.SILVERFISH, ((CraftWorld)location.getWorld()).getHandle());

        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(20);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(30);
        this.setHealth(30);

        this.bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Lutumite", BarColor.PURPLE, BarStyle.SOLID);
        this.bossBar.setProgress(1.0);
        this.bossBar.setVisible(true);

        Silverfish bukkitEntity = (Silverfish) this.getBukkitEntity();
        bukkitEntity.setCustomName(ChatColor.GRAY + "Lutumite");
        bukkitEntity.setCustomNameVisible(true);

        for(Player player : Bukkit.getOnlinePlayers()) {
            this.bossBar.addPlayer(player);
        }

        this.expToDrop = 10;
    }

    protected void dropDeathLoot() {
        org.bukkit.inventory.ItemStack clay = new org.bukkit.inventory.ItemStack(org.bukkit.Material.CLAY);
        clay.setAmount(random.nextInt(4) + 4);
        this.getBukkitEntity().getWorld().dropItemNaturally(this.getBukkitEntity().getLocation(), clay);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.targetSelector.a(1, (new PathfinderGoalHurtByTarget(this, new Class[0])).a(new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        if (damagesource != DamageSource.DROWN) {
            boolean result = super.damageEntity0(damagesource, f);
            this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
            return result;
        }
        return false;
    }

    @Override
    public void die() {
        super.die();
        this.dropDeathLoot();
        this.bossBar.removeAll();
    }
}
