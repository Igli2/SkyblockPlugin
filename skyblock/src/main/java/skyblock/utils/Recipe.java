package skyblock.utils;

import org.bukkit.inventory.ItemStack;

public interface Recipe {
    ItemStack getResult();
    boolean equals(ItemStack[][] matrixOther);
}
