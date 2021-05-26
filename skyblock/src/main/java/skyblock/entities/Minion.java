package skyblock.entities;

import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import skyblock.SkyblockMain;
import skyblock.utils.CraftingTable;
import skyblock.utils.Recipe;
import skyblock.utils.UtilFunctions;
import skyblock.utils.minion.*;

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
    private int selectedSlot;

    private int sleepTicks;

    public Minion(EntityTypes<? extends EntityArmorStand> entitytypes, World world) {
        super(entitytypes, world);
        this.inv = Bukkit.createInventory(this, 9 * 6, "Program");
        this.getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey(SkyblockMain.instance, "isMinion"), PersistentDataType.INTEGER, 1);

        this.instructions = new ArrayList<>();
        this.pc = 0;
        this.nextUpdate = 0;
        this.selectedSlot = 0;
        this.sleepTicks = 0;
    }

    public Minion(Location location) {
        super(EntityTypes.ARMOR_STAND, ((CraftWorld)location.getWorld()).getHandle());
        this.inv = Bukkit.createInventory(this, 9 * 6, "Program");

        this.instructions = new ArrayList<>();
        this.pc = 0;
        this.nextUpdate = 0;
        this.selectedSlot = 0;
        this.sleepTicks = 0;

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

    public void update() {
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

        ((ArmorStand)this.getBukkitEntity()).getEquipment().setItemInMainHand(this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]));
    }

    @Override
    public void tick() {
        super.tick();

        if(this.sleepTicks > 0) {
            this.sleepTicks--;
            return;
        }

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
        Location head = this.getBukkitEntity().getLocation().add(0.0, this.getBukkitEntity().getHeight(), 0.0);

        switch(instruction.getType()) {
            case USE:
                {
                    ItemStack currItem = this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]);
                    if(currItem != null) {
                        if(currItem.getType() == Material.WOODEN_SWORD || currItem.getType() == Material.STONE_SWORD || currItem.getType() == Material.IRON_SWORD
                           || currItem.getType() == Material.GOLDEN_SWORD || currItem.getType() == Material.DIAMOND_SWORD || currItem.getType() == Material.NETHERITE_SWORD) {
                            double attackDamage = CraftItemStack.asNMSCopy(currItem).a(EnumItemSlot.MAINHAND).get(GenericAttributes.ATTACK_DAMAGE).iterator().next().getAmount() * 2;
                            for(Entity e : this.getBukkitEntity().getNearbyEntities(3.0, 1.0, 3.0)) {
                                if(e instanceof LivingEntity && e != this.getBukkitEntity()) {
                                    ((LivingEntity) e).damage(attackDamage, this.getBukkitEntity());
                                }
                            }
                        }
                    }
                }
                break;
            case SELECT:
                this.selectedSlot = Integer.parseInt(instruction.getArg());
                ((ArmorStand)this.getBukkitEntity()).getEquipment().setItemInMainHand(this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]));
                break;
            case THROW:
                if(this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]) != null) {
                    ItemStack toDrop = this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]).clone();

                    int dropAmount = Math.min(Integer.parseInt(instruction.getArg()), toDrop.getAmount());
                    this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]).setAmount(toDrop.getAmount() - dropAmount);
                    toDrop.setAmount(dropAmount);

                    Item dropped = this.getBukkitEntity().getWorld().dropItem(head, toDrop);
                    dropped.setVelocity(this.getBukkitEntity().getLocation().getDirection().normalize());
                }
                break;
            case CRAFT:
                {
                    ItemStack[][] recipeItems = new ItemStack[][]{
                            {this.inv.getItem(Minion.CRAFTING_GRID[0]), this.inv.getItem(Minion.CRAFTING_GRID[1]), this.inv.getItem(Minion.CRAFTING_GRID[2])},
                            {this.inv.getItem(Minion.CRAFTING_GRID[3]), this.inv.getItem(Minion.CRAFTING_GRID[4]), this.inv.getItem(Minion.CRAFTING_GRID[5])},
                            {this.inv.getItem(Minion.CRAFTING_GRID[6]), this.inv.getItem(Minion.CRAFTING_GRID[7]), this.inv.getItem(Minion.CRAFTING_GRID[8])}
                    };
                    Recipe recipe = CraftingTable.getRecipe(recipeItems);
                    if(recipe != null) {
                        this.getBukkitEntity().getWorld().dropItem(head, recipe.getResult().clone());
                    }
                }
                break;
            case DROP:
                if(this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]) != null) {
                    ItemStack toDrop = this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]).clone();

                    int dropAmount = Math.min(Integer.parseInt(instruction.getArg()), toDrop.getAmount());
                    this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]).setAmount(toDrop.getAmount() - dropAmount);
                    toDrop.setAmount(dropAmount);

                    this.getBukkitEntity().getWorld().dropItem(head, toDrop);
                }
                break;
            case LEFT:
                this.setYawPitch(this.yaw - 90.0f, this.pitch);
                break;
            case RIGHT:
                this.setYawPitch(this.yaw + 90.0f, this.pitch);
                break;
            case BREAK:
                if(!front.getBlock().isEmpty() && front.getBlock().getType().getHardness() >= 0.0) {
                    double breakTime = UtilFunctions.getBlockBreakDuration(this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot]), front.getBlock(),
                                -1, -1, false, this.inWater, this.onGround);
                    this.sleepTicks = (int) (breakTime * 20);
                    Bukkit.getScheduler().runTaskLater(SkyblockMain.instance, new BreakerRunnable(front.getBlock(), this.inv.getItem(Minion.INVENTORY_SLOTS[this.selectedSlot])), (long) (breakTime * 20));
                }
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

    public void saveContent() {
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

            minion.update();
            minion.saveContent();

            return true;
        }

        return false;
    }
}
