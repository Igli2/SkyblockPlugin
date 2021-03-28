package skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import skyblock.generators.SkyblockChunkGenerator;

public class JoinSkyblockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            WorldCreator wc = new WorldCreator(player.getUniqueId().toString());
            wc.generator(new SkyblockChunkGenerator());
            wc.createWorld();

            player.teleport(new Location(Bukkit.getWorld(player.getUniqueId().toString()), 0, 100, 0));

            return true;
        }
        return false;
    }
}
