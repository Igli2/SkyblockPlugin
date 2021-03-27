package skyblock.generators;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class SkyblockChunkGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunk = this.createChunkData(world);

     /*   for(int chunk_x = 0; chunk_x < 16; chunk_x++) {
            for(int chunk_z = 0; chunk_z < 16; chunk_z++) {
                chunk.setBlock(chunk_x, (int) (255 * Math.sin(x + z + chunk_x + chunk_z)), chunk_z, Material.SNOW_BLOCK);
            }
        }*/


        return chunk;
        //return super.generateChunkData(world, random, x, z, biome);
    }
}
