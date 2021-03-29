package skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import skyblock.SkyblockMain;
import skyblock.utils.WorldInfo;

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(strings.length == 1) {
                if(!SkyblockMain.worldRegistry.hasWorld(strings[0])) {
                    player.sendMessage(ChatColor.RED + "This world doesn't exist!");
                    return true;
                }
                if(SkyblockMain.worldRegistry.getWorldType(strings[0]) == WorldInfo.WorldType.PLAYER_WORLD) {
                    player.sendMessage(ChatColor.RED + "You can't warp into player worlds!");
                    return true;
                }

                if(!SkyblockMain.worldRegistry.isWorldLoaded(strings[0])) {
                    SkyblockMain.worldRegistry.loadWorld(strings[0]);
                }

                player.teleport(new Location(Bukkit.getWorld(strings[0]), 0, 100, 0));
            } else {
                player.sendMessage(ChatColor.RED + "/warp <world_name>");
            }
            return true;
        }
        return false;
    }
}
