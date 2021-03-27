package skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import skyblock.generators.SkyblockChunkGenerator;

public class CreateSkyblockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        WorldCreator wc = new WorldCreator("test_world");
        wc.generator(new SkyblockChunkGenerator());
        wc.createWorld();

      //  Bukkit.createWorld(wc);
        return true;
    }
}
