package skyblock.utils;

import org.bukkit.inventory.ItemStack;

public class ShopItem {

    private ItemStack item;

    private int sell_value;
    private int buy_value;
    private int sell_amount;
    private int buy_amount;

    private boolean sellable;
    private boolean buyable;

    public ShopItem(ItemStack item, int sell_value, int buy_value, int sell_amount, int buy_amount, boolean sellable, boolean buyable) {
        this.item = item;
        this.sell_value = sell_value;
        this.buy_value = buy_value;
        this.sell_amount = sell_amount;
        this.buy_amount = buy_amount;
        this.sellable = sellable;
        this.buyable = buyable;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSell_value() {
        return sell_value;
    }

    public int getBuy_value() {
        return buy_value;
    }

    public int getSell_amount() {
        return sell_amount;
    }

    public int getBuy_amount() {
        return buy_amount;
    }

    public boolean isSellable() {
        return sellable;
    }

    public boolean isBuyable() {
        return buyable;
    }
}
