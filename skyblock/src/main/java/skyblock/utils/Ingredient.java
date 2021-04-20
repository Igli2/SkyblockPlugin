package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private final List<ItemStack> item;
    private final char key;

    public Ingredient(List<ItemStack> item, char key) {
        this.item = item;
        this.key = key;
    }

    public Ingredient(List<ItemStack> item, int amount, char key) {
        for (ItemStack itemStack : item) {
            itemStack.setAmount(amount);
        }
        this.item = item;
        this.key = key;
    }

    public Ingredient(ItemStack item, char key) {
        this.item = new ArrayList<>();
        this.item.add(item);
        this.key = key;
    }

    public Ingredient(ItemStack item, int amount, char key) {
        item.setAmount(amount);
        this.item = new ArrayList<>();
        this.item.add(item);
        this.key = key;
    }

    public Ingredient(Material material, int amount, char key) {
        ItemStack item = new ItemStack(material, amount);
        this.item = new ArrayList<>();
        this.item.add(item);
        this.key = key;
    }

    public Ingredient(Material material, char key) {
        ItemStack item = new ItemStack(material, 1);
        this.item = new ArrayList<>();
        this.item.add(item);
        this.key = key;
    }

    public List<ItemStack> getItem() {
        return item;
    }

    public char getKey() {
        return key;
    }

    // debug method
    public String toString() {
        return "Ingredient{" + this.key + "," + this.item.get(0).getType() + "}";
    }
}
