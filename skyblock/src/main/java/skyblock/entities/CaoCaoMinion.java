package skyblock.entities;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;

import javax.annotation.Nullable;

public class CaoCaoMinion extends EntityZombie {
    CaoCao boss;

    public CaoCaoMinion(EntityTypes<? extends EntityZombie> entitytypes, World world) {
        super(entitytypes, world);
    }

    public CaoCaoMinion(Location location, CaoCao boss) {
        super(EntityTypes.ZOMBIE, ((CraftWorld)location.getWorld()).getHandle());

        this.boss = boss;
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setNoAI(false);

        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(64);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(80);
        this.setHealth(80);
        this.setCanPickupLoot(false);

        Zombie bukkitEntity = (Zombie) this.getBukkitEntity();
        bukkitEntity.setCustomName(ChatColor.BLACK + "" + ChatColor.BOLD + "Minion");
        bukkitEntity.setCustomNameVisible(true);

        EntityEquipment e = bukkitEntity.getEquipment();
        if (e != null) {
            e.setItemInMainHand(new org.bukkit.inventory.ItemStack(org.bukkit.Material.GOLDEN_SWORD));
            e.setHelmet(new org.bukkit.inventory.ItemStack(org.bukkit.Material.GOLDEN_HELMET));
            e.setChestplate(new org.bukkit.inventory.ItemStack(org.bukkit.Material.GOLDEN_CHESTPLATE));
            e.setLeggings(new org.bukkit.inventory.ItemStack(org.bukkit.Material.GOLDEN_LEGGINGS));
            e.setBoots(new org.bukkit.inventory.ItemStack(org.bukkit.Material.GOLDEN_BOOTS));
        }
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
    public void die() {
        super.die();
        this.boss.onMinionDeath();
    }
}
