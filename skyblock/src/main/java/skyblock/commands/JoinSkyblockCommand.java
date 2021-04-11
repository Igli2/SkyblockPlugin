package skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import skyblock.SkyblockMain;
import skyblock.generators.SkyblockChunkGenerator;
import skyblock.utils.WorldInfo;

import javax.annotation.Nonnull;

public class JoinSkyblockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            String worldName = player.getUniqueId().toString();

            if(!SkyblockMain.worldRegistry.hasWorld(worldName)) {
                WorldCreator wc = new WorldCreator(worldName);
                wc.generator(new SkyblockChunkGenerator());
                Bukkit.createWorld(wc);
                SkyblockMain.worldRegistry.addWorld(new WorldInfo(WorldInfo.WorldType.PLAYER_WORLD, worldName, true));
            }

            player.teleport(new Location(Bukkit.getWorld(worldName), 0, 100, 0));

            return true;
        }
        return false;
    }
}
