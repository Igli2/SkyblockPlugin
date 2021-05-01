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
import java.util.List;

public class ItemBrowser implements Listener, CommandExecutor {
    public static final int HOME_BUTTON = 49;
    public static final ItemStack HOME_BUTTON_ITEM = ItemRegistry.createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==", new int[]{3546554, -56354542, -564566, 111666666});

    static {
        ItemMeta itemMeta = HOME_BUTTON_ITEM.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName("Home");
        }
        HOME_BUTTON_ITEM.setItemMeta(itemMeta);
    }

    @EventHandler
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
                if (item == null || item.equals(CraftingTable.PLACEHOLDER)) {
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
        inventory.setItem(CraftingTable.RESULT, itemStack);
        inventory.setItem(HOME_BUTTON, HOME_BUTTON_ITEM);

        // TODO: item info (eg. drop chance)
        if (recipe == null) {
            // no recipe, only info
        } else {
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
