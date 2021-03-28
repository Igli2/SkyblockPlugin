package skyblock.commands;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HeadTestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);

            net.minecraft.server.v1_16_R3.ItemStack stack = CraftItemStack.asNMSCopy(head);
            NBTTagCompound tag = stack.getOrCreateTag();
            NBTTagCompound skullOwner = new NBTTagCompound();

            //Texture
            NBTTagCompound properties = new NBTTagCompound();
            NBTTagList textures = new NBTTagList();
            NBTTagCompound texture = new NBTTagCompound();
            texture.setString("Value", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjEyYjU4Yzg0MWIzOTQ4NjNkYmNjNTRkZTFjMmFkMjY0OGFmOGYwM2U2NDg5ODhjMWY5Y2VmMGJjMjBlZTIzYyJ9fX0=");
            textures.add(texture);
            properties.set("textures", textures);
            skullOwner.set("Properties", properties);

            //ID
            skullOwner.setIntArray("Id", new int[]{1297605385, -1179891909, -1281037724, -233117787});

            tag.set("SkullOwner", skullOwner);
            stack.setTag(tag);

            player.getWorld().dropItemNaturally(player.getLocation(), CraftItemStack.asBukkitCopy(stack));
        }

        return true;
    }
}
