package skyblock.utils.quest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;

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
        for (ItemStack itemStack : this.items.keySet()) {
            if (!player.getInventory().containsAtLeast(itemStack, this.items.get(itemStack) * itemStack.getAmount())) {
                return false;
            }
        }
        return true;
    }

    private void removeItems(Player player) {
        for (ItemStack itemStack : this.items.keySet()) {
            for (int i = 0; i < this.items.get(itemStack); ++i) {
                SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> player.getInventory().removeItem(itemStack), 1);
            }
        }
    }
}
