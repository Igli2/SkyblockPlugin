package skyblock.registries;

import java.util.HashMap;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
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

      //  ItemStack geode = getHead(new ItemStack(Material.PLAYER_HEAD));
        ItemStack geode = new ItemStack(Material.PLAYER_HEAD);

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

    // TODO
    /*
     * private void setSkullTexture(ItemStack item) { SkullMeta skullMeta =
     * (SkullMeta) item.getItemMeta(); }
     */

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
