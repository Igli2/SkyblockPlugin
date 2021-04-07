package skyblock.utils;

import org.bukkit.inventory.ItemStack;

public class ShopItem {

    private ItemStack item;

    private int sellValue;
    private int sellAmount;

    public ShopItem(ItemStack item, int sellValue, int sellAmount) {
        this.item = item;
        this.sellValue = sellValue;
        this.sellAmount = sellAmount;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSellValue() {
        return sellValue;
    }

    public int getSellAmount() {
        return sellAmount;
    }
}
