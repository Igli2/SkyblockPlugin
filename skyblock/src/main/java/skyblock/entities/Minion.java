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
import skyblock.utils.UtilFunctions;
import skyblock.utils.minion.Instruction;
import skyblock.utils.minion.InstructionCodeGenerator;
import skyblock.utils.minion.ProgramLexer;
import skyblock.utils.minion.ProgramParser;

import java.util.ArrayList;

public class Minion extends EntityArmorStand implements InventoryHolder {

    private static final int PROGRAM_SLOT = 37;
    private static final int DESTROY_SLOT = 39;

    private static final int[] CRAFTING_GRID = {10, 11, 12, 19, 20, 21, 28, 29, 30};

    private static final int[] INVENTORY_SLOTS = {14, 15, 16, 23, 24, 25, 32, 33, 34, 41, 42, 43};

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
        if(this.inv.getItem(Minion.PROGRAM_SLOT) != null && this.inv.getItem(Minion.PROGRAM_SLOT).getType() == Material.WRITTEN_BOOK) {
            ItemStack program = this.inv.getItem(Minion.PROGRAM_SLOT);
            BookMeta bookMeta = (BookMeta) program.getItemMeta();
            String programStr = String.join("\n", bookMeta.getPages());

            ProgramParser parser = new ProgramParser(new ProgramLexer(programStr));

            try {
                this.instructions = InstructionCodeGenerator.generateInstructions(parser.parse());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        this.saveContent();
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
                this.setYawPitch(this.yaw - 90.0f, this.pitch);
                break;
            case RIGHT:
                this.setYawPitch(this.yaw + 90.0f, this.pitch);
                break;
            case BREAK:
                if(!front.getBlock().isEmpty()) front.getBlock().breakNaturally();
                break;
            case PLACE:
                if(front.getBlock().getType() == Material.AIR) front.getBlock().setType(Material.STONE);
                break;
            case JMP:
                this.pc += Integer.parseInt(instruction.getArg());
                return;
            case JMP_IF_TRUE:
                {
                    String[] parameters = instruction.getArg().split(";");

                    if(this.checkCondition(parameters[0], front)) {
                        this.pc += Integer.parseInt(parameters[1]);
                        return;
                    }

                    break;
                }
            case JMP_IF_FALSE:
                {
                    String[] parameters = instruction.getArg().split(";");

                    if(!this.checkCondition(parameters[0], front)) {
                        this.pc += Integer.parseInt(parameters[1]);
                        return;
                    }

                    break;
                }
        }
        this.pc++;
    }

    private void saveContent() {
        if(this.inv.getItem(Minion.PROGRAM_SLOT) != null && this.inv.getItem(Minion.PROGRAM_SLOT).getType() == Material.WRITTEN_BOOK) {
            this.getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey(SkyblockMain.instance, "program"), PersistentDataType.STRING,
                    UtilFunctions.itemStackToBase64(this.inv.getItem(Minion.PROGRAM_SLOT)));
        }

        String craftingGrid = "";
        for(int s = 0; s < Minion.CRAFTING_GRID.length; s++) {
            int slot = Minion.CRAFTING_GRID[s];

            if(s != 0) craftingGrid += ";";

            if(this.inv.getItem(slot) == null) {
                craftingGrid += "null";
            } else {
                craftingGrid += UtilFunctions.itemStackToBase64(this.inv.getItem(slot));
            }
        }

        this.getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey(SkyblockMain.instance, "craftingGrid"), PersistentDataType.STRING,
                craftingGrid);

        String inventoryGrid = "";
        for(int s = 0; s < Minion.INVENTORY_SLOTS.length; s++) {
            int slot = Minion.INVENTORY_SLOTS[s];

            if(s != 0) inventoryGrid += ";";

            if(this.inv.getItem(slot) == null) {
                inventoryGrid += "null";
            } else {
                inventoryGrid += UtilFunctions.itemStackToBase64(this.inv.getItem(slot));
            }
        }

        this.getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey(SkyblockMain.instance, "inventoryGrid"), PersistentDataType.STRING,
                inventoryGrid);
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

            if(armorStand.getPersistentDataContainer().has(new NamespacedKey(SkyblockMain.instance, "program"), PersistentDataType.STRING)) {
                try {
                    minion.inv.setItem(Minion.PROGRAM_SLOT, UtilFunctions.itemStackFromBase64(armorStand.getPersistentDataContainer().get(
                            new NamespacedKey(SkyblockMain.instance, "program"), PersistentDataType.STRING)));
                } catch (Exception ioException) {
                    ioException.printStackTrace();
                }
            }

            if(armorStand.getPersistentDataContainer().has(new NamespacedKey(SkyblockMain.instance, "craftingGrid"), PersistentDataType.STRING)) {
                String[] encoded = armorStand.getPersistentDataContainer().get(new NamespacedKey(SkyblockMain.instance, "craftingGrid"), PersistentDataType.STRING).split(";");

                for(int i = 0; i < encoded.length; i++) {
                    int slot = Minion.CRAFTING_GRID[i];

                    if(!encoded[i].equals("null")) minion.inv.setItem(slot, UtilFunctions.itemStackFromBase64(encoded[i]));
                }
            }

            if(armorStand.getPersistentDataContainer().has(new NamespacedKey(SkyblockMain.instance, "inventoryGrid"), PersistentDataType.STRING)) {
                String[] encoded = armorStand.getPersistentDataContainer().get(new NamespacedKey(SkyblockMain.instance, "inventoryGrid"), PersistentDataType.STRING).split(";");

                for(int i = 0; i < encoded.length; i++) {
                    int slot = Minion.INVENTORY_SLOTS[i];

                    if(!encoded[i].equals("null")) minion.inv.setItem(slot, UtilFunctions.itemStackFromBase64(encoded[i]));
                }
            }

            minion.updateProgram();

            return true;
        }

        return false;
    }
}
