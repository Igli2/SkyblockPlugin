package skyblock.registries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagInt;
import net.minecraft.server.v1_16_R3.NBTTagList;
import net.minecraft.server.v1_16_R3.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemRegistry {
    public enum  SkyblockItems {
        ARCHEOLOGISTS_PICKAXE,
        GEODE,
        SUGAR_CUBE,
        SPEEDY_HELMET,
        SPEEDY_CHESTPLATE,
        SPEEDY_LEGGINGS,
        SPEEDY_BOOTS,
        SHINY_PEBBLE
    }

    public HashMap<SkyblockItems, ItemStack> specialItems = new HashMap<>();

    public ItemRegistry() {
        registerSpecialItems();
    }

    private void registerSpecialItems() {
        ItemStack archeologistsPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
        makeUnbreakable(archeologistsPickaxe);
        addEnchantEffect(archeologistsPickaxe);
        setItemName(archeologistsPickaxe, "Archeologist's Pickaxe");
        specialItems.put(SkyblockItems.ARCHEOLOGISTS_PICKAXE, archeologistsPickaxe);

        ItemStack geode = createTexturedSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2FiYjUxZjU5NDgxMTMyNTQ1YjUwZTQ3NWU3NjYyMzljNzljNjI0ZTliOTZhYjNhMGFjYjJhZjMwMWQ5NmM3OSJ9fX0=",
                new int[]{-1136006473, 240537101, -1791113915, -2037819923});
        addEnchantEffect(geode);
        setItemName(geode, "Geode");
        specialItems.put(SkyblockItems.GEODE, geode);

        ItemStack sugarCube = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2E2YWE0MGRiNzQxYTZjYmVkYjExOWEzZTVjYmE4YTg3ZjdlYzhmNzRkMjY4YWQ4MjgyYTQ2ZTVlMDU0ZmNiOSJ9fX0=",
                new int[]{-1171650357, -2028255289, -1780378138, 1576325737});
        addEnchantEffect(sugarCube);
        setItemName(sugarCube, "Sugar Cube");
        specialItems.put(SkyblockItems.SUGAR_CUBE, sugarCube);

        ItemStack speedyHelmet = new ItemStack(Material.LEATHER_HELMET);
        makeUnbreakable(speedyHelmet);
        addEnchantEffect(speedyHelmet);
        setArmorColor(speedyHelmet, 180, 220, 140);
        setItemName(speedyHelmet, "Speedy Helmet");
        speedyHelmet = setMovementSpeed(speedyHelmet, 0.02, "head", new int[]{457456, 546435345, -45346352, 5432678});
        specialItems.put(SkyblockItems.SPEEDY_HELMET, speedyHelmet);

        ItemStack speedyChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        makeUnbreakable(speedyChestplate);
        addEnchantEffect(speedyChestplate);
        setArmorColor(speedyChestplate, 180, 220, 140);
        setItemName(speedyChestplate, "Speedy Chestplate");
        speedyChestplate = setMovementSpeed(speedyChestplate, 0.02, "chest", new int[]{564547564, 342324, -34, 342534676});
        specialItems.put(SkyblockItems.SPEEDY_CHESTPLATE, speedyChestplate);

        ItemStack speedyLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
        makeUnbreakable(speedyLeggings);
        addEnchantEffect(speedyLeggings);
        setArmorColor(speedyLeggings, 180, 220, 140);
        setItemName(speedyLeggings, "Speedy Leggings");
        speedyLeggings = setMovementSpeed(speedyLeggings, 0.02, "legs", new int[]{43, 46723, -46, 335745524});
        specialItems.put(SkyblockItems.SPEEDY_LEGGINGS, speedyLeggings);

        ItemStack speedyBoots = new ItemStack(Material.LEATHER_BOOTS);
        makeUnbreakable(speedyBoots);
        addEnchantEffect(speedyBoots);
        setArmorColor(speedyBoots, 180, 220, 140);
        setItemName(speedyBoots, "Speedy Boots");
        //setLore(speedyBoots, Arrays.asList(ChatColor.GOLD + "Armor Set Bonus:", "Gives you permanent speed I"));
        speedyBoots = setMovementSpeed(speedyBoots, 0.02, "feet", new int[]{353634, 35346544, -336768756, 2017473902});
        specialItems.put(SkyblockItems.SPEEDY_BOOTS, speedyBoots);

        ItemStack shinyPebble = new ItemStack(Material.STONE_BUTTON);
        addEnchantEffect(shinyPebble);
        setItemName(shinyPebble, "Shiny Pebble");
        setLore(shinyPebble, Arrays.asList("A very special rock that's super rare"));
        specialItems.put(SkyblockItems.SHINY_PEBBLE, shinyPebble);
    }

    public static void setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
    }

    public static void setArmorColor(ItemStack itemStack, int r, int g, int b) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setColor(Color.fromRGB(r, g, b));
            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        }
        itemStack.setItemMeta(itemMeta);
    }

    public static void makeUnbreakable(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setUnbreakable(true);
        }
        item.setItemMeta(itemMeta);
    }

    public static void setItemName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
        }
        item.setItemMeta(itemMeta);
    }

    public static void addEnchantEffect(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemMeta);
    }

    public static ItemStack setMovementSpeed(ItemStack itemStack, double speed, String slot, int[] uuid) {
        net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = nmsItemStack.hasTag() ? nmsItemStack.getTag() : new NBTTagCompound();

        NBTTagList tagList = new NBTTagList();
        NBTTagCompound speedCompound = new NBTTagCompound();
        speedCompound.setString("AttributeName", "generic.movement_speed");
        speedCompound.setString("Name", "generic.movement_speed");
        speedCompound.setDouble("Amount", speed);
        speedCompound.setInt("Operation", 0);
        speedCompound.setString("Slot", slot);
        speedCompound.setIntArray("UUID", uuid);
        tagList.add(speedCompound);
        tag.set("AttributeModifiers", tagList);

        nmsItemStack.setTag(tag);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static ItemStack createTexturedSkull(String textureStr, int[] id) {
        net.minecraft.server.v1_16_R3.ItemStack head = CraftItemStack.asNMSCopy(new ItemStack(Material.PLAYER_HEAD));
        NBTTagCompound tag = head.getOrCreateTag();
        NBTTagCompound skullOwner = new NBTTagCompound();

        //Texture
        NBTTagCompound properties = new NBTTagCompound();
        NBTTagList textures = new NBTTagList();
        NBTTagCompound texture = new NBTTagCompound();
        texture.setString("Value", textureStr);
        textures.add(texture);
        properties.set("textures", textures);
        skullOwner.set("Properties", properties);

        //ID
        skullOwner.setIntArray("Id", id);

        tag.set("SkullOwner", skullOwner);
        head.setTag(tag);

        return CraftItemStack.asBukkitCopy(head);
    }

    public static boolean isItemStackEqual(ItemStack itemStack, ItemStack other) {
        if (itemStack == null) {
            itemStack = new ItemStack(Material.AIR);
        }
        if (other == null) {
            other = new ItemStack(Material.AIR);
        }

        if (itemStack.getType() == Material.AIR && other.getType() == Material.AIR) {
            return true;
        } else if (itemStack.getType() == Material.AIR || other.getType() == Material.AIR) {
            return false;
        }

        ItemMeta itemStackMeta = itemStack.getItemMeta();
        ItemMeta otherMeta = other.getItemMeta();
        String itemStackName = (itemStackMeta.hasDisplayName()) ? itemStackMeta.getDisplayName().toLowerCase() : itemStack.getType().toString().toLowerCase();
        String otherName = (otherMeta.hasDisplayName()) ? otherMeta.getDisplayName().toLowerCase() : other.getType().toString().toLowerCase();

        return itemStackName.equals(otherName);
    }

    public ItemStack getItemStack(SkyblockItems id) {
        return this.specialItems.get(id).clone();
    }
}
