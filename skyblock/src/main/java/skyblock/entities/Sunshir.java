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
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import javax.annotation.Nullable;

public class Sunshir extends EntityZombie {
    private BossBar bossBar;
    private SunshirSheep sheep;

    public Sunshir(EntityTypes<? extends EntityZombie> entitytypes, World world) {
        super(entitytypes, world);
    }

    public Sunshir(Location location) {
        super(EntityTypes.ZOMBIE, ((CraftWorld)location.getWorld()).getHandle());

        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(120);
        this.setHealth(120);

        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(32);

        this.bossBar = Bukkit.createBossBar(ChatColor.DARK_PURPLE + "Sunshir", BarColor.PURPLE, BarStyle.SOLID, BarFlag.CREATE_FOG);
        this.bossBar.setProgress(1.0);
        this.bossBar.setVisible(true);

        Zombie bukkitEntity = (Zombie) this.getBukkitEntity();
        bukkitEntity.setCanPickupItems(false);
        bukkitEntity.setCustomName(ChatColor.AQUA + "Sunshir");
        bukkitEntity.setCustomNameVisible(true);
        bukkitEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1, false, false));
        bukkitEntity.setInvisible(true);

        for(Player player : Bukkit.getOnlinePlayers()) {
            this.bossBar.addPlayer(player);
        }

        this.sheep = new SunshirSheep(EntityTypes.SHEEP, this.getWorld(), this);
        this.world.addEntity(this.sheep);

    }

    @Override
    public boolean isFireProof() {
        return true;
    }

    @Override
    public void setOnFire(int i) {
    }

    @Override
    public void setOnFire(int i, boolean callEvent) {
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
    public EntityItem a(ItemStack itemstack) {
        return null;
    }

    @Nullable
    @Override
    public EntityItem a(ItemStack itemstack, float f) {
        return null;
    }

    @Override
    protected void dropDeathLoot(DamageSource damagesource, int i, boolean flag) {
    }

    protected void dropDeathLoot() {
        if (Math.random() < 0.05) {
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
        this.sheep.teleport(this.getBukkitEntity());
    }

    @Override
    public void die() {
        this.dropDeathLoot();
        super.die();
        this.bossBar.removeAll();
        this.sheep.getBukkitEntity().remove();
    }
}
