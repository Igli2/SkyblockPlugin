package skyblock.generators;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import skyblock.SkyblockMain;

import javax.annotation.Nonnull;
import java.util.Random;

public class SkyblockChunkGenerator extends ChunkGenerator {

    @Override
    @Nonnull
    public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int x, int z, @Nonnull BiomeGrid biome) {
        ChunkData data = this.createChunkData(world);
        if(x == 0 && z == 0 && SkyblockMain.starterIsland != null) {
            SkyblockMain.starterIsland.place(new Location(world, 0, 100, 0), data);
        }
        return data;
    }
}
