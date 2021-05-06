package skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class VisitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if (strings.length != 1) {return false;}

        Player player = Bukkit.getPlayer(strings[0]);
        if (player == null) {
            commandSender.sendMessage("Not a valid player!");
            return true;
        }

        ((Player) commandSender).teleport(new Location(Bukkit.getWorld(player.getUniqueId().toString()), 9, 111, 8));
        return true;
    }
}
