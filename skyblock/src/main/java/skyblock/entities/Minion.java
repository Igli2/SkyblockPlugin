package skyblock.entities;

import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import skyblock.SkyblockMain;
import skyblock.utils.minion.Instruction;
import skyblock.utils.minion.InstructionCodeGenerator;
import skyblock.utils.minion.ProgramLexer;
import skyblock.utils.minion.ProgramParser;

import java.util.ArrayList;

public class Minion extends EntityArmorStand implements InventoryHolder {

    private Inventory inv;
    private ArrayList<Instruction> instructions;
    private int pc;
    private int nextUpdate;

    public Minion(EntityTypes<? extends EntityArmorStand> entitytypes, World world) {
        super(entitytypes, world);
        this.inv = Bukkit.createInventory(this, 9 * 6, "Program");
        this.getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey(SkyblockMain.instance, "isMinion"), PersistentDataType.INTEGER, 1);

        this.instructions = new ArrayList<>();
        this.pc = 0;
        this.nextUpdate = 0;
    }

    public Minion(Location location) {
        super(EntityTypes.ARMOR_STAND, ((CraftWorld)location.getWorld()).getHandle());
        this.inv = Bukkit.createInventory(this, 9 * 6, "Program");

        this.instructions = new ArrayList<>();
        this.pc = 0;
        this.nextUpdate = 0;

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
        return this.inv;
    }

    public void updateProgram() {
        if(this.inv.getItem(0) != null && this.inv.getItem(0).getType() == Material.WRITTEN_BOOK) {
            ItemStack program = this.inv.getItem(0);
            BookMeta bookMeta = (BookMeta) program.getItemMeta();
            String programStr = String.join("\n", bookMeta.getPages());

            ProgramParser parser = new ProgramParser(new ProgramLexer(programStr));

            try {
                this.instructions = InstructionCodeGenerator.generateInstructions(parser.parse());
            /*    for(Instruction instruction : instructions) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + instruction.toString());
                }*/
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.nextUpdate == 0) {
            this.nextUpdate = 20;

            if(this.instructions.size() == 0) return;
            if(this.pc >= this.instructions.size()) this.pc = 0;

            this.executeInstruction(this.instructions.get(pc));
        } else {
            this.nextUpdate--;
        }
    }

    private void executeInstruction(Instruction instruction) {
        Location front = this.getBukkitEntity().getLocation().add(this.getBukkitEntity().getLocation().getDirection());

        switch(instruction.getType()) {
            case USE:
                break;
            case SELECT:
                break;
            case THROW:
                break;
            case CRAFT:
                break;
            case DROP:
                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            case BREAK:
                if(!front.getBlock().isEmpty()) front.getBlock().breakNaturally();
                break;
            case PLACE:
                if(front.getBlock().getType() == Material.AIR) front.getBlock().setType(Material.STONE);
                break;
            case JMP_IF_TRUE:
                {
                    String condition = instruction.getArg().split(";")[0];
                    int offset = Integer.parseInt(instruction.getArg().split(";")[1]);

                    if(this.checkCondition(condition, front)) {
                        this.pc += offset;
                        return;
                    }
                    break;
                }
            case JMP_IF_FALSE:
                {
                    String condition = instruction.getArg().split(";")[0];
                    int offset = Integer.parseInt(instruction.getArg().split(";")[1]);

                    if (!this.checkCondition(condition, front)) {
                        this.pc += offset;
                        return;
                    }
                    break;
                }
        }
        this.pc++;
    }

    private boolean checkCondition(String condition, Location front) {
        if(condition.equals("power")) {
            return this.getBukkitEntity().getLocation().add(0.0, -1.0, 0.0).getBlock().isBlockPowered();
        } else {
            return front.getBlock().getType() == Material.valueOf(condition.toUpperCase());
        }
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
