package skyblock.utils;

import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ShopNPCEntity extends NPCEntity {
    private final ArrayList<ShopItem> items = new ArrayList<>();
    private ShopItem soldItem = new ShopItem(CraftingTable.PLACEHOLDER, 0, 0);
    public static ArrayList<ShopItem> sellables = new ArrayList<>();

    public ShopNPCEntity(Property textures, JSONObject json) {
        super((String) json.get("name"), textures, Bukkit.getWorld((String) ((JSONObject) json.get("location")).get("world")));
        JSONObject location = (JSONObject) json.get("location");

        this.getEntity().setPositionRotation((double) location.get("x"), (double) location.get("y"), (double) location.get("z"),
                (float) (double) location.get("yaw"), (float) (double) location.get("pitch"));

        loadShopItems(json);
    }

    private void loadShopItems(JSONObject json) {
        JSONArray sItems = (JSONArray) json.get("shop");
        for (Object sItem : sItems) {
            JSONArray item = (JSONArray) sItem;

            String itemName = (String) item.get(0);
            String[] comp = itemName.split(":");
            ItemStack shopItem = null;
            if (comp[0].equals("skyblock")) {
                shopItem = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.valueOf(comp[1].toUpperCase()));
                ItemMeta shopItemMeta = shopItem.getItemMeta();
                if (shopItemMeta != null) {
                    List<String> lore = shopItemMeta.getLore();
                    if (lore == null) {
                        lore = new ArrayList<>();
                    }
                    lore.add(ChatColor.GOLD + String.valueOf((long) item.get(1)) + "$");
                }
                shopItem.setItemMeta(shopItemMeta);
            } else if (comp[0].equals("minecraft")) {
                shopItem = new ItemStack(Material.valueOf(comp[1].toUpperCase()));
                ItemMeta shopItemMeta = shopItem.getItemMeta();
                if (shopItemMeta != null) {
                    shopItemMeta.setLore(Collections.singletonList(ChatColor.GOLD + String.valueOf((long) item.get(1)) + "$"));
                }
                shopItem.setItemMeta(shopItemMeta);
            }

            if (shopItem != null) {
                shopItem.setAmount((int) (long) item.get(2));
                this.items.add(new ShopItem(shopItem, (int) (long) item.get(1), (int) (long) item.get(2)));
            }
        }
    }

    @Override
    public void interact(Player player) {
        Bukkit.getScheduler().runTask(SkyblockMain.instance, () -> {
            int rows = 2 + (int) Math.ceil(this.items.size() / 7.0);
            Inventory inventory = Bukkit.createInventory(null, rows * 9, this.getEntity().getName());

            // set placeholder items
            for (int i = 0; i < rows * 9; i++) {
                inventory.setItem(i, CraftingTable.PLACEHOLDER);
            }

            // set buyable items
            int row = 1;
            int column = 1;
            for (ShopItem shopItem : this.items) {
                inventory.setItem(row * 9 + column, shopItem.getItem());
                column += 1;
                if (column > 7) {
                    row += 1;
                    column = 1;
                }
            }

            player.openInventory(inventory);
        });
    }

    public void buyOffer(Player player, ItemStack item, boolean isShiftClick, int slot, Inventory inventory) {
        // buy sold item back
        if (slot == inventory.getSize() - 5 && !soldItem.getItem().equals(CraftingTable.PLACEHOLDER)) {
            SkyblockMain.moneyHandler.removeMoney(player, soldItem.getSellValue());
            player.getInventory().addItem(soldItem.getItem());
            soldItem = new ShopItem(CraftingTable.PLACEHOLDER, 0, 0);
            inventory.setItem(inventory.getSize() - 5, soldItem.getItem());
            return;
        }

        for (ShopItem offer : this.items) {
            if (offer.getItem().equals(item)) {
                if (isShiftClick) {
                    for (int i = 0; i < (int) Math.floor(64.0 / offer.getSellAmount()); i++) {
                        if (!this.buyItem(player, offer)) return;
                    }
                } else {
                    this.buyItem(player, offer);
                }
            }
        }
    }

    public void sellItem(Player player, ItemStack itemStack, Inventory inventory) {
        if (itemStack != null && itemStack.getType() != Material.AIR) {
            for (ShopItem sellable : ShopNPCEntity.sellables) {
                if (ItemRegistry.isItemStackEqual(sellable.getItem(), itemStack)) {
                    int amount = itemStack.getAmount();
                    soldItem = new ShopItem(itemStack.clone(), sellable.getSellValue() * amount, amount);
                    inventory.setItem(inventory.getSize() - 5, itemStack);
                    SkyblockMain.moneyHandler.addMoney(player, sellable.getSellValue() * amount);
                    itemStack.setAmount(0);
                }
            }
        }
    }

    private boolean buyItem(Player player, ShopItem item) {
        if (SkyblockMain.moneyHandler.removeMoney(player, item.getSellValue())) {
            ItemStack toBuy = item.getItem().clone();
            ItemMeta itemMeta = toBuy.getItemMeta();
            if (itemMeta != null) {
                List<String> lore = itemMeta.getLore();
                if (lore != null) {
                    lore.removeIf(s -> s.equals(ChatColor.GOLD + String.valueOf((long) item.getSellValue()) + "$"));
                }
                itemMeta.setLore(lore);
            }
            toBuy.setItemMeta(itemMeta);
            HashMap<Integer, ItemStack> excess = player.getInventory().addItem(toBuy);

            if (excess.isEmpty()) {
                return true;
            } else {
                SkyblockMain.moneyHandler.addMoney(player, item.getSellValue());
                toBuy.setAmount(toBuy.getAmount() - excess.get(0).getAmount());
                player.getInventory().removeItem(toBuy);
                return false;
            }
        } else {
            return false;
        }
    }
}
