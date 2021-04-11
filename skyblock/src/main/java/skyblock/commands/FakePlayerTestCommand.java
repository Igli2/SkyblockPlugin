package skyblock.commands;

import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import skyblock.SkyblockMain;
import skyblock.utils.NPCEntity;

import javax.annotation.Nonnull;

public class FakePlayerTestCommand implements CommandExecutor {

    private final Property textures = new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxNzMwNzg5NTg5NiwKICAicHJvZmlsZUlkIiA6ICIzOTg5OGFiODFmMjU0NmQxOGIyY2ExMTE1MDRkZGU1MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJNeVV1aWRJcyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yYzcyMzUzYTliYzVjMTA4NGM5YjUyOWMxM2Y0ZjY4MWZmZmRmMDBjYzE2MjQ4N2Q4OGFkMDQyZGFhZmNmNjk0IgogICAgfQogIH0KfQ==", "ok83pzF5kpTgGtcbChohun9fu+66yW5JcgduGfXzrR+mH9H1WNnA4EF0JwApuUUrfPGZmFcwPmnu8QALgPkXfHEBNqUGDsmnISDP4eWW51gxiGcQkRBDwuN4hTC97ufh320C7Ao+QV4g9/qbZ7tAe1c6WpyAWq/EpqTA58O7PpxrawVmJJP4JIv36p0seFK+XJ+FCWGxATY/76YOj4oD3Y0b+lKqG83pMBzDj80m7cf1GwBtcYbvMd712lQdDKs/lOFwAu8FGjHAn3zMhpvxw7U+pt0tDNYIZt1UfRwM8YD6g4UOJiA6gcUY64MURySMqrMaIdGkT+LxTwx9KaGBdn+xVH80shGdbVgcpTXjO4PlQSu0EFLje7et+wdWse0K2BX3Uw6OawW6tlk3stlw6lYhF/cgTx3CgYuwfCgCZ2yIU5DX6hGNQfyE2bhv+v5LoNyJqRMMsCD3ar4yoweAuoPAL1XTRPsz//zQ8XG3dyzGWWYcqqkc+oeRpkCGztkc62oh1Bm86gX1oodluPhpMXvASnlbu8I8q8mkrzvL3q+RaoWnFdnka3NtpEHCERotdxKL03kPsEa2dRvntgsDH5TQl2r4GP/RO1/JNbgwXbnNxzvwyl9iuA3Mo0AVCHkL8Exqm5avzpYnshO/h/hvjn3e4DlzVuYAZq+n0707KPY=");

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] strings) {
        if(commandSender instanceof Player && strings.length == 3) {
            Player player = (Player) commandSender;

            /*MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer world = ((CraftWorld) player.getWorld()).getHandle();*/

            NPCEntity npc = new NPCEntity(strings[0], textures, player.getWorld());
            //npc.getEntity().setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            npc.getEntity().setPositionRotation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), Float.parseFloat(strings[1]), Float.parseFloat(strings[2]));
            SkyblockMain.npcRegistry.registerNPC(npc);

            for(Player ignored : Bukkit.getOnlinePlayers()) {
                SkyblockMain.npcRegistry.showNPCs(player);
            }

            return true;
        }

        return false;
    }
}
