package skyblock.utils.minion;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BreakerRunnable implements Runnable {

    private Block block;
    private ItemStack item;

    public BreakerRunnable(Block block, ItemStack item) {
        this.block = block;
        this.item = item;
    }

    @Override
    public void run() {
        this.block.breakNaturally(item);
    }
}
