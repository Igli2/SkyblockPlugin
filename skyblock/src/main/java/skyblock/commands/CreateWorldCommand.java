package skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import skyblock.SkyblockMain;
import skyblock.generators.SkyblockChunkGenerator;
import skyblock.utils.WorldInfo;

import javax.annotation.Nonnull;

public class CreateWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if(commandSender.isOp()) {
            if(strings.length == 1) {
                if(SkyblockMain.worldRegistry.hasWorld(strings[0])) {
                    commandSender.sendMessage(ChatColor.RED + "This world already exists!");
                    return true;
                }

                WorldCreator wc = new WorldCreator(strings[0]);
                wc.generator(new SkyblockChunkGenerator());
                Bukkit.createWorld(wc);

                SkyblockMain.worldRegistry.addWorld(new WorldInfo(WorldInfo.WorldType.PUBLIC_WORLD, strings[0], true));

                commandSender.sendMessage(ChatColor.GREEN + "World successfully created!");
            } else {
                commandSender.sendMessage(ChatColor.RED + "/create_world <world_name>");
            }
            return true;
        } else {
            commandSender.sendMessage(ChatColor.RED + "You are not allowed to do that!");
        }
        return false;
    }
}
