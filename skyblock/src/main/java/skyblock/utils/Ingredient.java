package skyblock.utils;

import org.bukkit.Material;

public class Ingredient {
    private Material material;
    private char key;

    public Ingredient(Material material, char key) {
        this.material = material;
        this.key = key;
    }

    public Material getMaterial() {
        return material;
    }

    public char getKey() {
        return key;
    }
}
