package skyblock.utils;

import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;
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
            if(comp[0].equals("skyblock")) {
                this.items.add(new ShopItem(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.valueOf(comp[1].toUpperCase())), (int)(long) item.get(1), (int)(long) item.get(3), (int)(long) item.get(2), (int)(long) item.get(4),
                        (boolean) item.get(5), (boolean) item.get(6)));
            } else if(comp[0].equals("minecraft")) {
                this.items.add(new ShopItem(new ItemStack(Material.valueOf(comp[1].toUpperCase())), (int)(long) item.get(1), (int)(long) item.get(3), (int)(long) item.get(2), (int)(long) item.get(4),
                        (boolean) item.get(5), (boolean) item.get(6)));
            }
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
}
