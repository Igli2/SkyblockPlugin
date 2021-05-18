package skyblock.utils.quest;

import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import skyblock.SkyblockMain;
import skyblock.utils.NPCEntity;

import java.util.HashMap;
import java.util.UUID;

public class QuestNPCEntity extends NPCEntity {
    private final String greeting;
    private final HashMap<UUID, Long> playerCooldown = new HashMap<>();

    public QuestNPCEntity(JSONObject json) {
        super((String) json.get("name"), new Property("textures", (String) json.get("value"), (String) json.get("signature")), Bukkit.getWorld((String) ((JSONObject) json.get("location")).get("world")));

        this.greeting = (String) json.get("greeting");
        JSONObject location = (JSONObject) json.get("location");
        this.getEntity().setPositionRotation((double) location.get("x"), (double) location.get("y"), (double) location.get("z"),
                (float) (double) location.get("yaw"), (float) (double) location.get("pitch"));
    }

    @Override
    public void interact(Player player) {
        long cooldown = this.playerCooldown.getOrDefault(player.getUniqueId(), 0L);
        if (System.currentTimeMillis() >= cooldown) {
            long a = SkyblockMain.questRegistry.playerInteractQuestNPC(player, this);
            if (a == 0) {
                player.sendMessage(this.getEntity().getName() + ": " + this.greeting);
                a = 1000;
            }
            this.playerCooldown.put(player.getUniqueId(), System.currentTimeMillis() + a);
        }
    }
}
