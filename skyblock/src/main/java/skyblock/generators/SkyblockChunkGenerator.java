package skyblock.generators;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class SkyblockChunkGenerator extends ChunkGenerator {

    @Override
    @Nonnull
    public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int x, int z, @Nonnull BiomeGrid biome) {
        return this.createChunkData(world);
        /*for(int chunk_x = 0; chunk_x < 16; chunk_x++) {
            for(int chunk_z = 0; chunk_z < 16; chunk_z++) {
                chunk.setBlock(chunk_x, (int) (255 * Math.sin(x + z + chunk_x + chunk_z)), chunk_z, Material.SNOW_BLOCK);
            }
        }*/
        //return super.generateChunkData(world, random, x, z, biome);
    }
}
