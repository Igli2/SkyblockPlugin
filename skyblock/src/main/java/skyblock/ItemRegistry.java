package skyblock;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRegistry {
    public static final int ARCHEOLOGISTS_PICKAXE = 1024;
    public static final int GEODE = 1025;
    HashMap<Integer, ItemStack> specialItems = new HashMap<Integer, ItemStack>();

    App plugin;

    public ItemRegistry(App plugin) {
        this.plugin = plugin;

        registerSpecialItems();
    }

    private void registerSpecialItems() {
        ItemStack archeologistsPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
        makeUnbreakable(archeologistsPickaxe);
        setItemName(archeologistsPickaxe, "Archeologist's Pickaxe");
        specialItems.put(ARCHEOLOGISTS_PICKAXE, archeologistsPickaxe);

        ItemStack geode = new ItemStack(Material.STONE);
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
