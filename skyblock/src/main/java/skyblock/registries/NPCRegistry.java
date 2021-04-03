package skyblock.registries;

import org.bukkit.entity.Player;
import skyblock.SkyblockMain;
import skyblock.utils.NPCEntity;

import java.util.HashMap;

public class NPCRegistry {

    private HashMap<Integer, NPCEntity> npcs = new HashMap<>();

    public NPCRegistry() {

    }

    public void registerNPC(NPCEntity npc) {
        if(npc.isAlive()) this.npcs.put(npc.getEntity().getId(), npc);
    }

    public void removeNPC(int npcID) {
        if(this.npcs.containsKey(npcID)) {
            NPCEntity npc = this.npcs.get(npcID);
            npc.kill();
            this.npcs.remove(npcID);
        }
    }

    public boolean isEntityRegistered(int npcID) {
        return this.npcs.containsKey(npcID);
    }

    public NPCEntity getNPC(int npcID) {
        if(!this.isEntityRegistered(npcID)) return null;
        return this.npcs.get(npcID);
    }

    public void showNPCs(Player player) {
        for(NPCEntity npc : this.npcs.values()) {
            SkyblockMain.instance.getLogger().info(npc.getEntity().getWorld().toString() + " <-> " + player.getWorld().toString());
            if(npc.getEntity().getWorld().getWorld().getName().equals(player.getWorld().getName())) {
                npc.show(player);
            }
        }
    }
}
