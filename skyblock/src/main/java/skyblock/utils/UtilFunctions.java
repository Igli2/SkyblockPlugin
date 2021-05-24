package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class UtilFunctions {

    public static String itemStackToBase64(ItemStack item) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(outStream);
            bukkitStream.writeObject(item);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(outStream.toByteArray());
    }

    public static ItemStack itemStackFromBase64(String encoded) {
        byte[] programBytes = Base64.getDecoder().decode(encoded);

        InputStream inStream = new ByteArrayInputStream(programBytes);
        try {
            BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(inStream);
            return (ItemStack) bukkitStream.readObject();
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }

        return null;
    }

    public static double getBlockBreakDuration(ItemStack usedTool, Block block, int hasteLevel, int miningFatigueLevel, boolean aquaAffinity, boolean inWater, boolean onGround) {
        double speedMultiplier = 1.0;
        if(block.isPreferredTool(usedTool)) {
            speedMultiplier = UtilFunctions.getSpeedMultiplier(usedTool, block);

            if(block.getType().getHardness() < 0) {
                speedMultiplier = 1.0;
            } else if(usedTool.getEnchantmentLevel(Enchantment.DIG_SPEED) > 0) {
                speedMultiplier += Math.pow(usedTool.getEnchantmentLevel(Enchantment.DIG_SPEED), 2) + 1.0;
            }
        }

        if(hasteLevel >= 0.0) speedMultiplier *= 1.0 + (0.2 * hasteLevel);

        if(miningFatigueLevel >= 0.0) {
            switch(miningFatigueLevel) {
                case 0:
                    speedMultiplier += 0.3;
                    break;
                case 1:
                    speedMultiplier *= 0.09;
                    break;
                case 2:
                    speedMultiplier *= 0.0027;
                    break;
                default:
                    speedMultiplier *= 0.00081;
                    break;
            }
        }

        if(inWater && !aquaAffinity) speedMultiplier /= 5.0;
        if(!onGround) speedMultiplier /= 5.0;

        double damage = speedMultiplier / block.getType().getHardness();

        if(block.getType().getHardness() >= 0.0) {
            damage /= 30.0;
        } else {
            damage /= 100.0;
        }

        if(damage > 1) return 0.0;

        return Math.ceil(1 / damage) / 20;
    }

    public static double getSpeedMultiplier(ItemStack tool, Block block) {
        switch(tool.getType()) {
            case WOODEN_AXE:
            case WOODEN_SHOVEL:
            case WOODEN_PICKAXE:
            case WOODEN_HOE:
                return 2.0;
            case STONE_AXE:
            case STONE_SHOVEL:
            case STONE_PICKAXE:
            case STONE_HOE:
                return 4.0;
            case IRON_AXE:
            case IRON_SHOVEL:
            case IRON_PICKAXE:
            case IRON_HOE:
                return 6.0;
            case DIAMOND_AXE:
            case DIAMOND_SHOVEL:
            case DIAMOND_PICKAXE:
            case DIAMOND_HOE:
                return 8.0;
            case NETHERITE_AXE:
            case NETHERITE_SHOVEL:
            case NETHERITE_PICKAXE:
            case NETHERITE_HOE:
                return 9.0;
            case GOLDEN_AXE:
            case GOLDEN_SHOVEL:
            case GOLDEN_PICKAXE:
            case GOLDEN_HOE:
                return 12.0;
            case SHEARS:
                if(Tag.WOOL.isTagged(block.getType())) {
                    return 5.0;
                } else if(block.getType() == Material.COBWEB) {
                    return 15.0;
                } else {
                    return 1.5;
                }
            case WOODEN_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case DIAMOND_SWORD:
            case NETHERITE_SWORD:
            case GOLDEN_SWORD:
                return (block.getType() == Material.COBWEB) ? 15.0 : 1.5;
            default:
                return 1.0;
        }
    }
}
