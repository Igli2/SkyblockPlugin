package skyblock.utils.quest;

import org.bukkit.entity.Player;
import skyblock.SkyblockMain;

import java.util.List;

public class TaskTalk {
    protected String npcName;
    protected List<String> messages;

    public TaskTalk(List<String> messages, String npcName) {
        this.messages = messages;
        this.npcName = npcName;
    }

    public Quest.State execute(Player player) {
        int delay = 0;
        for (String message : this.messages) {
            SkyblockMain.instance.getServer().getScheduler().scheduleSyncDelayedTask(SkyblockMain.instance, () -> player.sendMessage(this.npcName + ": " + message), delay);
            delay += 40;
        }
        return Quest.State.SUCCESS;
    }

    public boolean matchesEntity(QuestNPCEntity entity) {
        return entity.getEntity().getName().equals(this.npcName);
    }

    public long getCooldown() {
        return this.messages.size() * 2000L;
    }
}
