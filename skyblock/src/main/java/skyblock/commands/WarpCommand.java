package skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(strings.length == 1) {
                if (strings[0].equals("lobby")) {
                    player.setFallDistance(0);
                    player.teleport(new Location(Bukkit.getWorld("lobby"), 0.5, 110, 0.5));
                } else if (strings[0].equals("skyblock") || strings[0].equals("sb") || strings[0].equals("island") || strings[0].equals("is") || strings[0].equals("home")) {
                    SkyblockMain.npcRegistry.hideNPCs(player);
                    String worldName = player.getUniqueId().toString();

                    if(!SkyblockMain.worldRegistry.hasWorld(worldName)) {
                        WorldCreator wc = new WorldCreator(worldName);
                        wc.generator(new SkyblockChunkGenerator());
                        Bukkit.createWorld(wc);
                        SkyblockMain.worldRegistry.addWorld(new WorldInfo(WorldInfo.WorldType.PLAYER_WORLD, worldName, true));
                    }

                    player.setFallDistance(0);
                    player.teleport(new Location(Bukkit.getWorld(worldName), 9, 111, 8));
                } else {
                    player.sendMessage(ChatColor.RED + "Can't warp you there!");
                    return false;
                }
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "/warp <world_name>");
            }
        }
        return false;
    }
}
