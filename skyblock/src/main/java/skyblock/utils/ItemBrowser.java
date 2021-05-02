package skyblock.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;
import skyblock.registries.RecipeRegistry;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ItemBrowser implements Listener, CommandExecutor {
    public static final int HOME_BUTTON = 49;
    public static final int INFO_SLOT = 25;
    public static final ItemStack HOME_BUTTON_ITEM = ItemRegistry.createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==", new int[]{3546554, -56354542, -564566, 111666666});
    public static final HashMap<ItemRegistry.SkyblockItems, List<String>> itemInfo = new HashMap<ItemRegistry.SkyblockItems, List<String>>() {{
        put(ItemRegistry.SkyblockItems.GEODE, Arrays.asList("1/200 chance to drop from stone or", "cobblestone when mined with a stone pickaxe"));
        put(ItemRegistry.SkyblockItems.ARCHEOLOGISTS_PICKAXE, Arrays.asList("Mine cobblestone or stone to get:", "Coal: 5%", "Iron ore: 2.5%", "Gold ore: 1%", "Lapis lazuli: 1%", "Redstone: 2%", "Diamond: 0.5%", "Emerald: 0.25%", "Ancient debris: 0.2%", "Nether quartz: 1.5%"));
        put(ItemRegistry.SkyblockItems.APPLE_HARVESTER, Collections.singletonList("15% chance to drop apple from any leaves block"));
        put(ItemRegistry.SkyblockItems.CREEPER_WAND, Collections.singletonList("Consumes 1 gunpowder on use"));
        put(ItemRegistry.SkyblockItems.THE_TRUNK, Collections.singletonList("Apply slowness 2 on hit"));
        put(ItemRegistry.SkyblockItems.TORNADO, Arrays.asList("Right click to use ability", "Launches all entities within 5 block radius", "into the air"));
    }};

    static {
        ItemMeta itemMeta = HOME_BUTTON_ITEM.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName("Home");
        }
        HOME_BUTTON_ITEM.setItemMeta(itemMeta);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Item Browser")) { // operators can take items
            int slot = event.getRawSlot();
            if (slot >= 54 || slot < 0) {
                event.setCancelled(true);
                return;
            }
            ItemStack item = event.getInventory().getItem(slot);

            if (slot < 45) {
                // cancel if null, because there willbe no recipe
                // cancel if placeholder
                if (item == null || item.equals(CraftingTable.PLACEHOLDER) || isItemInfo(item)) {
                    event.setCancelled(true);
                    return;
                }

                // operators can retrieve items from the item browser for testing purposes
                if (slot == CraftingTable.RESULT && event.getWhoClicked().isOp()) {
                    event.getWhoClicked().getInventory().addItem(item);
                }

                // get recipe
                Recipe recipe = getRecipe(item);
                setItemInfo(event.getInventory(), recipe, item);
            } else {
                // get page controls
                if (slot == HOME_BUTTON) {
                    setItemList(event.getInventory(), 0);
                }
            }
            event.setCancelled(true);
        }
    }

    private boolean isItemInfo(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        return itemMeta.getDisplayName().equals("Item Info");
    }

    private Recipe getRecipe(ItemStack itemStack) {
        for (ShapedRecipe recipe : RecipeRegistry.recipes) {
            ItemStack result = recipe.getResult();
            if (ItemRegistry.isItemStackEqual(result, itemStack)) {
                return recipe;
            }
        }
        for (ShapelessRecipe recipe : RecipeRegistry.shapelessRecipes) {
            ItemStack result = recipe.getResult();
            if (ItemRegistry.isItemStackEqual(result, itemStack)) {
                return recipe;
            }
        }
        return null;
    }

    public void openBrowser(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Item Browser");

        setItemList(inventory, 0);

        player.openInventory(inventory);
    }

    private void setItemInfo(Inventory inventory, Recipe recipe, ItemStack itemStack) {
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
        for (int i : CraftingTable.GLASS_PANES) {
            inventory.setItem(i, CraftingTable.PLACEHOLDER);
        }
        ItemStack clone = itemStack.clone();
        clone.setAmount(1);
        inventory.setItem(CraftingTable.RESULT, clone);
        inventory.setItem(HOME_BUTTON, HOME_BUTTON_ITEM);

        List<String> info = getItemInfo(itemStack);
        setItemInfo(inventory, info);
        if (recipe != null) {
            inventory.setItem(CraftingTable.RESULT, recipe.getResult());
            // recipe and info
            if (recipe instanceof ShapedRecipe) {
                String[] shape = ((ShapedRecipe) recipe).getShape();
                int row = 0;
                for (String s : shape) {
                    int col = 0;
                    for (char c : s.toCharArray()) {
                        Ingredient ingredient = ((ShapedRecipe) recipe).getIngredient(c);
                        inventory.setItem(CraftingTable.MATRIX[row * 3 + col], ingredient.getItem().get(0));
                        col += 1;
                    }
                    row += 1;
                }
            } else {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
                int pos = 0;
                for (List<ItemStack> list : shapelessRecipe.getIngredients()) {
                    inventory.setItem(CraftingTable.MATRIX[pos], list.get(0));
                    pos += 1;
                }
            }
        }
    }

    private void setItemInfo(Inventory inventory, List<String> info) {
        if (info == null) {
            return;
        }

        ItemStack infoPaper = new ItemStack(Material.PAPER);
        ItemMeta infoPaperMeta = infoPaper.getItemMeta();
        if (infoPaperMeta == null) {
            return;
        }
        infoPaperMeta.setDisplayName("Item Info");
        infoPaperMeta.setLore(info);
        infoPaper.setItemMeta(infoPaperMeta);
        inventory.setItem(INFO_SLOT, infoPaper);
    }

    private List<String> getItemInfo(ItemStack itemStack) {
        for (ItemRegistry.SkyblockItems skyblockItems : itemInfo.keySet()) {
            ItemStack skyblockItem = SkyblockMain.itemRegistry.getItemStack(skyblockItems);
            if (skyblockItem.equals(itemStack)) {
                return itemInfo.get(skyblockItems);
            }
        }
        return null;
    }

    private void setItemList(Inventory inventory, int page) {
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
        inventory.setItem(HOME_BUTTON, HOME_BUTTON_ITEM);

        int skip = page * 45;
        int slot = 0;
        for (ItemRegistry.SkyblockItems item : ItemRegistry.SkyblockItems.values()) {
            if (skip > 0) {
                skip -= 1;
            } else {
                inventory.setItem(slot, SkyblockMain.itemRegistry.getItemStack(item));
                slot += 1;
            }
        }
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull  Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            openBrowser(player);
            return true;
        }
        return false;
    }
}
