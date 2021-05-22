package skyblock.utils.quest;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskDeliver extends TaskTalk {
    protected HashMap<ItemStack, Integer> items;
    protected String requirementMessage;

    public TaskDeliver(List<String> message, String npcName, String requirementMessage, HashMap<ItemStack, Integer> items) {
        super(message, npcName);
        this.items = items;
        this.requirementMessage = requirementMessage;
    }

    @Override
    public Quest.State execute(Player player) {
        if (this.hasItems(player)) {
            if (super.execute(player) == Quest.State.SUCCESS) {
                this.removeItems(player);
                return Quest.State.SUCCESS;
            }
            return Quest.State.FAILURE;
        } else {
            player.sendMessage(this.npcName + ": " + requirementMessage);
            return Quest.State.MISSING_REQUIREMENT;
        }
    }

    private boolean hasItems(Player player) {
        List<ItemStack> inventory = new ArrayList<>();
        for (int i = 0;  i < 36; ++i) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null) {
                inventory.add(item.clone());
            } else {
                inventory.add(new ItemStack(Material.AIR));
            }
        }

        for (ItemStack itemStack : this.items.keySet()) {
            int toRemove = itemStack.getAmount() * this.items.get(itemStack);
            for (ItemStack itemStack1 : inventory) {
                if (ItemRegistry.isItemStackEqual(itemStack, itemStack1)) {
                    toRemove -= itemStack1.getAmount();
                }
            }
            if (toRemove > 0) {
                return false;
            }
        }
        return true;
    }

    private void removeItems(Player player) {
        for (ItemStack itemStack : this.items.keySet()) {
            int toRemove = itemStack.getAmount() * this.items.get(itemStack);
            for (int i = 0; i < 36; ++i) {
                ItemStack itemStack1 = player.getInventory().getItem(i);
                if (itemStack1 == null) { break; }

                if (ItemRegistry.isItemStackEqual(itemStack1, itemStack)) {
                    if (itemStack1.getAmount() >= toRemove) {
                        itemStack1.setAmount(itemStack1.getAmount() - toRemove);
                        int finalI = i;
                        SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> player.getInventory().setItem(finalI, itemStack1), 1);
                        break;
                    } else {
                        int finalI = i;
                        SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> player.getInventory().setItem(finalI, new ItemStack(Material.AIR)), 1);
                        toRemove -= itemStack1.getAmount();
                    }
                }
            }
        }
    }
}
