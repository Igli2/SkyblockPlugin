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

    public Minion(EntityTypes<? extends EntityArmorStand> entitytypes, World world) {
        super(entitytypes, world);
        this.inv = Bukkit.createInventory(this, 9 * 6, "Program");
        this.getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey(SkyblockMain.instance, "isMinion"), PersistentDataType.INTEGER, 1);
    }

    public Minion(Location location) {
        super(EntityTypes.ARMOR_STAND, ((CraftWorld)location.getWorld()).getHandle());
        this.inv = Bukkit.createInventory(this, 9 * 6, "Program");

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
                ArrayList<Instruction> instructions = InstructionCodeGenerator.generateInstructions(parser.parse());

                for(Instruction instruction : instructions) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + instruction.toString());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            /*Token t = lexer.nextToken();

            while(t.getType() != Token.TokenType.EOF) {
                Bukkit.broadcastMessage(ChatColor.GRAY + t.toString());
                t = lexer.nextToken();
            }*/
        }

       /* Condition c0 = new Condition(true, Token.TokenType.MATERIAL, "oak_log");
        Condition c1 = new Condition(false, Token.TokenType.POWER, "power");
        Condition c2 = new Condition(false, Token.TokenType.MATERIAL, "iron_block");

        SequenceBranch sequenceBranch = new SequenceBranch();
        sequenceBranch.addChild(new ConditionalBranch(new Condition[]{c0, c1, c2}, new CommandBranch(Token.TokenType.BREAK)));
        sequenceBranch.addChild(new CommandBranch(Token.TokenType.SELECT, 2));

        Bukkit.broadcastMessage(ChatColor.GREEN + sequenceBranch.toString());*/
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
