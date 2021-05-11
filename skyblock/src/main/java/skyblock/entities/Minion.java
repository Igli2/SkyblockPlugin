package skyblock.entities;

import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import skyblock.SkyblockMain;

public class Minion extends EntityArmorStand implements InventoryHolder {

    private Inventory program;

    public Minion(EntityTypes<? extends EntityArmorStand> entitytypes, World world) {
        super(entitytypes, world);
        this.program = Bukkit.createInventory(this, 9 * 5, "Program");
        this.getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey(SkyblockMain.instance, "isMinion"), PersistentDataType.INTEGER, 1);
    }

    public Minion(Location location) {
        super(EntityTypes.ARMOR_STAND, ((CraftWorld)location.getWorld()).getHandle());
        this.program = Bukkit.createInventory(this, 9 * 5, "Program");

        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.setInvulnerable(true);
        this.getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey(SkyblockMain.instance, "isMinion"), PersistentDataType.INTEGER, 1);

        ArmorStand bukkitEntity = (ArmorStand)this.getBukkitEntity();

        EntityEquipment equipment = bukkitEntity.getEquipment();
        if (equipment != null) {
            equipment.setHelmet(new ItemStack(org.bukkit.Material.CREEPER_HEAD));
            equipment.setChestplate(new ItemStack(org.bukkit.Material.LEATHER_CHESTPLATE));
            equipment.setLeggings(new ItemStack(org.bukkit.Material.LEATHER_LEGGINGS));
            equipment.setBoots(new ItemStack(org.bukkit.Material.LEATHER_BOOTS));
       //     equipment.setItemInMainHand(new ItemStack(org.bukkit.Material.CREEPER_HEAD));
        }
        bukkitEntity.setCanPickupItems(false);

        bukkitEntity.setCustomName(ChatColor.GOLD + "Minion");
        bukkitEntity.setCustomNameVisible(true);

        bukkitEntity.setArms(true);
        bukkitEntity.setSmall(true);
    }

    @Override
    public Inventory getInventory() {
        return this.program;
    }

    public void updateProgram() {
        Bukkit.broadcastMessage(ChatColor.GREEN + "Program successfully updated!");
    }

    public static boolean replaceArmorStand(ArmorStand armorStand) {
        if(armorStand.getPersistentDataContainer().has(new NamespacedKey(SkyblockMain.instance, "isMinion"), PersistentDataType.INTEGER) && armorStand.getPersistentDataContainer().get(new NamespacedKey(SkyblockMain.instance, "isMinion"), PersistentDataType.INTEGER) == 1) {
            armorStand.remove();
            Minion minion = new Minion(armorStand.getLocation());
            ((CraftWorld)armorStand.getWorld()).getHandle().addEntity(minion);

            return true;
        }

        return false;
    }
}
