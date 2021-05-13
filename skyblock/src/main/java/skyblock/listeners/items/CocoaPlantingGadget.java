package skyblock.listeners.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Cocoa;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

public class CocoaPlantingGadget implements Listener {
    @EventHandler
    @SuppressWarnings("unused")
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY) {return;}
        if (!ItemRegistry.isItemStackEqual(event.getItem(), SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.COCOA_PLANTING_GADGET))) {return;}
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {return;}
        if (event.getBlockFace() == BlockFace.UP || event.getBlockFace() == BlockFace.DOWN) {return;}

        BlockFace blockFace = event.getBlockFace();
        Block block = event.getClickedBlock();
        if (block == null) {return;}
        Location mid = block.getLocation();

        if (blockFace == BlockFace.NORTH || blockFace == BlockFace.SOUTH) {
            int z = 1;
            if (blockFace == BlockFace.NORTH) {
                z = -1;
            }

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    Block attachedBlock = block.getWorld().getBlockAt(new Location(mid.getWorld(), mid.getBlockX() + x, mid.getBlockY() + y, mid.getBlockZ()));
                    if (attachedBlock.getType().toString().endsWith("JUNGLE_WOOD") || attachedBlock.getType().toString().endsWith("JUNGLE_LOG")) {
                        placeCocoa(mid.getBlockX() + x, mid.getBlockY() + y, mid.getBlockZ() + z, blockFace.getOppositeFace(), mid.getWorld(), event.getPlayer());
                    }
                }
            }
        } else if (blockFace == BlockFace.WEST || blockFace == BlockFace.EAST) {
            int x = 1;
            if (blockFace == BlockFace.WEST) {
                x = -1;
            }

            for (int z = -1; z < 2; z++) {
                for (int y = -1; y < 2; y++) {
                    Block attachedBlock = block.getWorld().getBlockAt(new Location(mid.getWorld(), mid.getBlockX(), mid.getBlockY() + y, mid.getBlockZ() + z));
                    if (attachedBlock.getType().toString().endsWith("JUNGLE_WOOD") || attachedBlock.getType().toString().endsWith("JUNGLE_LOG")) {
                        placeCocoa(mid.getBlockX() + x, mid.getBlockY() + y, mid.getBlockZ() + z, blockFace.getOppositeFace(), mid.getWorld(), event.getPlayer());
                    }
                }
            }
        }
    }

    private void placeCocoa(int x, int y, int z, BlockFace face, World world, Player player) {
        if (!player.getInventory().contains(Material.COCOA_BEANS)) {return;}

        Block toReplace = world.getBlockAt(new Location(world, x, y, z));
        if (toReplace.getType() == Material.AIR) {
            toReplace.setType(Material.COCOA);
            Cocoa cocoa = (Cocoa) toReplace.getBlockData();
            cocoa.setFacing(face);
            toReplace.setBlockData(cocoa);
            player.getInventory().removeItem(new ItemStack(Material.COCOA_BEANS));
        }
    }
}
