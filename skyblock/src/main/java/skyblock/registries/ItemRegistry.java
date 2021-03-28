package skyblock.registries;

import java.util.HashMap;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.inventory.meta.SkullMeta;

import org.bukkit.inventory.meta.SkullMeta;
import skyblock.SkyblockMain;

public class ItemRegistry {
    private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";

    public static final int ARCHEOLOGISTS_PICKAXE = 1024;
    public static final int GEODE = 1025;
    HashMap<Integer, ItemStack> specialItems = new HashMap<Integer, ItemStack>();

    SkyblockMain plugin;

    public ItemRegistry(SkyblockMain plugin) {
        this.plugin = plugin;

        registerSpecialItems();
    }

    private void registerSpecialItems() {
        ItemStack archeologistsPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
        makeUnbreakable(archeologistsPickaxe);
        setItemName(archeologistsPickaxe, "Archeologist's Pickaxe");
        specialItems.put(ARCHEOLOGISTS_PICKAXE, archeologistsPickaxe);

        ItemStack geode = createTexturedSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2FiYjUxZjU5NDgxMTMyNTQ1YjUwZTQ3NWU3NjYyMzljNzljNjI0ZTliOTZhYjNhMGFjYjJhZjMwMWQ5NmM3OSJ9fX0=",
                new int[]{-1136006473, 240537101, -1791113915, -2037819923});

        addEnchantEffect(geode);
        setItemName(geode, "Geode");
        specialItems.put(GEODE, geode);
    }

    private void makeUnbreakable(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        item.setItemMeta(itemMeta);
    }

    private void setItemName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
    }

    private void addEnchantEffect(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
    }
    private ItemStack createTexturedSkull(String textureStr, int[] id) {
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

    public boolean isItem(int id, ItemStack item) {
        if (specialItems.get(id).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
            return true;
        }
        return false;
    }

    public ItemStack getItemStack(int id) {
        ItemStack itemStack = specialItems.get(id);
        return itemStack;
    }
}
