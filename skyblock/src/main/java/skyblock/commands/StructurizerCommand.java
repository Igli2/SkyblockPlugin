package skyblock.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import skyblock.SkyblockMain;

import java.io.File;
import java.io.FileWriter;

public class StructurizerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.isOp() && strings.length == 7 && commandSender instanceof Player) {
            Player player = (Player) commandSender;

            String file = strings[0] + ".struct";

            int x0 = Integer.parseInt(strings[1]);
            int y0 = Integer.parseInt(strings[2]);
            int z0 = Integer.parseInt(strings[3]);
            int x1 = Integer.parseInt(strings[4]);
            int y1 = Integer.parseInt(strings[5]);
            int z1 = Integer.parseInt(strings[6]);

            int startX = Math.min(x0, x1);
            int startY = Math.min(y0, y1);
            int startZ = Math.min(z0, z1);

            int endX = Math.max(x0, x1);
            int endY = Math.max(y0, y1);
            int endZ = Math.max(z0, z1);

            try {
                File toWrite = new File(SkyblockMain.instance.getDataFolder().getAbsolutePath() + "/structures/" + file);
                if(!toWrite.exists()) toWrite.createNewFile();

                FileWriter writer = new FileWriter(toWrite);
                for(int x = startX; x <= endX; x++) {
                    for(int y = startY; y <= endY; y++) {
                        for(int z = startZ; z <= endZ; z++) {
                            if(player.getWorld().getBlockAt(x, y, z).getType() != Material.AIR) {
                                writer.write((x - startX) + "," + (y - startY) + "," + (z - startZ) + "," + player.getWorld().getBlockAt(x, y, z).getType() + "\n");
                            }
                        }
                    }
                }
                writer.flush();
                writer.close();
                player.sendMessage(ChatColor.GREEN + "Successfully stored!");
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Unable to write to file!");
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }
}
