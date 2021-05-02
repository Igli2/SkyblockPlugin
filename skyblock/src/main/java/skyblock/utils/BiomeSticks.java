package skyblock.utils;

import net.minecraft.server.v1_16_R3.PacketPlayOutMapChunk;
import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BiomeSticks {
    public static void setSize(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {return;}

        List<String> lore = itemMeta.getLore();
        if (lore == null) {return;}
        int counter = 0;
        for (String s : lore) {
            if (s.startsWith("Size: ")) {
                String[] splitted = s.split(" ");
                if (splitted[2].equals("block")) {
                    // set to chunk mode
                    s = s.replaceFirst("block", "chunk");
                } else {
                    // set to block mode
                    s = s.replaceFirst("chunk", "block");
                }
                lore.set(counter, s);
                break;
            }
            counter += 1;
        }

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    public static void setBiome(PlayerInteractEvent event, ItemStack item, Biome biome) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {return;}

        List<String> lore = itemMeta.getLore();
        if (lore == null) {return;}

        for (String s : lore) {
            if (s.startsWith("Size: ")) {
                String[] splitted = s.split(" ");
                if (splitted[2].equals("block")) {
                    for (int y = 0; y < 256; y++) {
                        event.getPlayer().getWorld().setBiome(event.getPlayer().getLocation().getBlockX(), y, event.getPlayer().getLocation().getBlockZ(), biome);
                    }
                    Chunk c = event.getPlayer().getLocation().getChunk();
                    ((CraftPlayer)event.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutMapChunk(((CraftChunk) c).getHandle(), 65535));
                } else {
                    Chunk c = event.getPlayer().getLocation().getChunk();
                    for (int x = c.getX() * 16; x < c.getX() * 16 + 16; x++) {
                        for (int y = 0; y < 256; y++) {
                            for (int z = c.getZ() * 16; z < c.getZ() * 16 + 16; z++) {
                                event.getPlayer().getWorld().setBiome(x, y, z, biome);
                            }
                        }
                    }
                    ((CraftPlayer)event.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutMapChunk(((CraftChunk) c).getHandle(), 65535));
                }
            }
        }
    }
}
