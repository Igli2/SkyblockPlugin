package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Ingredient {
    private final ItemStack item;
    private final char key;

    public Ingredient(ItemStack item, char key) {
        this.item = item;
        this.key = key;
    }

    public Ingredient(Material material, int amount, char key) {
        this.item = new ItemStack(material, amount);
        this.key = key;
    }

    public Ingredient(Material material, char key) {
        this.item = new ItemStack(material, 1);
        this.key = key;
    }

    public ItemStack getItem() {
        return item;
    }

    public char getKey() {
        return key;
    }
}
