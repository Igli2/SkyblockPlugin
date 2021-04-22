package skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import skyblock.SkyblockMain;
import skyblock.utils.NPCEntity;

import javax.annotation.Nonnull;

public class SellCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if(commandSender instanceof Player) {
            for (NPCEntity npc : SkyblockMain.npcRegistry.getNPCs()) {
                System.out.println(npc.getEntity().getName());
                if (npc.getEntity().getName().equals("[NPC] Sell")) {
                    Player player = (Player) commandSender;
                    npc.interact(player);
                }
            }
            return true;
        }

        return false;
    }
}
