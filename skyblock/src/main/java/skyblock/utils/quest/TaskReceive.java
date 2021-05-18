package skyblock.utils.quest;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;

import java.util.HashMap;
import java.util.List;

public class TaskReceive extends TaskTalk {
    protected HashMap<ItemStack, Integer> items;

    public TaskReceive(List<String> message, String npcName, HashMap<ItemStack, Integer> items) {
        super(message, npcName);
        this.items = items;
    }

    @Override
    public Quest.State execute(Player player) {
        if (super.execute(player) == Quest.State.SUCCESS) {
            this.addOrDropItems(player);
            return Quest.State.SUCCESS;
        }
        return Quest.State.FAILURE;
    }

    private void addOrDropItems(Player player) {
        for (ItemStack itemStack : this.items.keySet()) {
            for (int i = 0; i < this.items.get(itemStack); ++i) {
                SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(
                        SkyblockMain.instance, () -> {
                            HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(itemStack.clone());
                            for (int index : leftOver.keySet()) {
                                player.getWorld().dropItem(player.getLocation(), leftOver.get(index));
                            }
                        }, 1);
            }
        }
    }
}
