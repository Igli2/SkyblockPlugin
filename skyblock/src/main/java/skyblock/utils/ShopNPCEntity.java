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
import java.util.Locale;

public class ShopNPCEntity extends NPCEntity {

    //TODO: how to store the item of the shop inventory
    private ArrayList<ShopItem> items = new ArrayList<>();

    public ShopNPCEntity(Property textures, JSONObject json) {
        super((String) json.get("name"), textures, Bukkit.getWorld((String) ((JSONObject) json.get("location")).get("world")));
        JSONObject location = (JSONObject) json.get("location");

        this.getEntity().setPositionRotation((double) location.get("x"), (double) location.get("y"), (double) location.get("z"),
                                             (float)(double) location.get("yaw"), (float)(double) location.get("pitch"));
        JSONArray shop_items = (JSONArray) json.get("shop");
        for(int i = 0; i < shop_items.size(); i++) {
            JSONArray item = (JSONArray) shop_items.get(i);
            String itemName = (String) item.get(0);
            String[] comp = itemName.split(":");
            ItemStack shopItem = null;
            if(comp[0].equals("skyblock")) {
                shopItem = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.valueOf(comp[1].toUpperCase()));
                ItemMeta shopItemMeta = shopItem.getItemMeta();
                shopItemMeta.setLore(Collections.singletonList(ChatColor.GOLD + String.valueOf((long) item.get(1)) + "$"));
                shopItem.setItemMeta(shopItemMeta);
            } else if(comp[0].equals("minecraft")) {
                shopItem = new ItemStack(Material.valueOf(comp[1].toUpperCase()));
                ItemMeta shopItemMeta = shopItem.getItemMeta();
                shopItemMeta.setLore(Collections.singletonList(ChatColor.GOLD + String.valueOf((long) item.get(1)) + "$"));
                shopItem.setItemMeta(shopItemMeta);
            }
            shopItem.setAmount((int)(long) item.get(2));
            this.items.add(new ShopItem(shopItem, (int)(long) item.get(1), (int)(long) item.get(2)));
        }
    }

    @Override
    public void interact(Player player) {
        if(player.getOpenInventory() != null) {
            Bukkit.getScheduler().runTask(SkyblockMain.instance, () -> {
                Inventory inventory = Bukkit.createInventory(null, (int) Math.ceil((double) this.items.size() / 9) * 9, this.getEntity().getName());
                for (int i = 0; i < this.items.size(); i++) {
                    inventory.setItem(i, this.items.get(i).getItem());
                }
                player.openInventory(inventory);
            });
        }
    }

    public boolean buyOffer(Player player, ItemStack item, boolean isShiftClick) {
        for(ShopItem offer : this.items) {
            if(offer.getItem().equals(item)) {
                if(isShiftClick) {
                    for(int i = 0; i < (int)Math.floor(64.0 / offer.getSellAmount()); i++) {
                        if(!this.buyItem(player, offer)) return false;
                    }
                    return true;
                } else {
                    return this.buyItem(player, offer);
                }
            }
        }

        player.sendMessage(ChatColor.RED + "Something went wrong, because the item you want to buy doesn't exist!");
        return false;
    }

    private boolean buyItem(Player player, ShopItem item) {
        if(SkyblockMain.moneyHandler.removeMoney(player, item.getSellValue())) {
            ItemStack toBuy = item.getItem().clone();
            ItemMeta toBuyMeta = toBuy.getItemMeta();
            toBuyMeta.setLore(null);
            toBuy.setItemMeta(toBuyMeta);

            HashMap<Integer, ItemStack> excess = player.getInventory().addItem(toBuy.clone());

            if(excess.isEmpty()) {
                return  true;
            } else {
                SkyblockMain.moneyHandler.addMoney(player, item.getSellValue());
                toBuy.setAmount(toBuy.getAmount() - excess.get(0).getAmount());
                player.getInventory().removeItem(toBuy);
                player.sendMessage(ChatColor.RED + "Your inventory is too full to hold all of the items you want to buy!");
                return false;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have enough money to buy that!");
            return false;
        }
    }
}
