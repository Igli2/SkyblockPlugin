package skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import skyblock.entities.Minion;

import javax.annotation.Nonnull;

public class FakePlayerTestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if(commandSender instanceof Player && commandSender.isOp()) {
            Player player = (Player) commandSender;

           /* Sunshir sunshir = new Sunshir(player.getLocation());
            ((CraftWorld)player.getWorld()).getHandle().addEntity(sunshir);*/
            Minion minion = new Minion(player.getLocation());
            ((CraftWorld)player.getWorld()).getHandle().addEntity(minion);

            return true;
        }

        return false;
    }
}
