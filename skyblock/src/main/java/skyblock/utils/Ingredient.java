package skyblock.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private final List<ItemStack> item;
    private final char key;

    public Ingredient(List<ItemStack> i, char key) {
        this.item = i;
        this.key = key;
    }

    public Ingredient(List<ItemStack> i, int amount, char key) {
        for (ItemStack itemStack : i) {
            itemStack.setAmount(amount);
        }
        this.item = i;
        this.key = key;
    }

    public Ingredient(ItemStack item, char key) {
        this.item = new ArrayList<>();
        this.item.add(item);
        this.key = key;
    }

    public Ingredient(ItemStack i, int amount, char key) {
        i.setAmount(amount);
        this.item = new ArrayList<>();
        this.item.add(i);
        this.key = key;
    }

    public Ingredient(Material material, int amount, char key) {
        ItemStack i = new ItemStack(material, amount);
        this.item = new ArrayList<>();
        this.item.add(i);
        this.key = key;
    }

    public Ingredient(Material material, char key) {
        ItemStack i = new ItemStack(material, 1);
        this.item = new ArrayList<>();
        this.item.add(i);
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
