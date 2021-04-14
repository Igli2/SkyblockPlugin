package skyblock.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import skyblock.SkyblockMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Structure {

    public static class Component {
        private int x;
        private int y;
        private int z;

        private Material material;

        public Component(int x, int y, int z, Material material) {
            this.x = x;
            this.y = y;
            this.z = z;

            this.material = material;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public Material getMaterial() {
            return material;
        }
    }

    private ArrayList<Component> components;

    public Structure() {
        this.components = new ArrayList<>();
    }

    public void place(Location location, ChunkGenerator.ChunkData data) {
        for(Component component : this.components) {
            data.setBlock(location.getBlockX() + component.x, location.getBlockY() + component.getY(), location.getBlockZ() + component.getZ(), component.getMaterial());
        }
    }

    public static Structure fromFile(String filePath) {
        File file = new File(filePath);

        if(file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                Structure structure = new Structure();
                String line = reader.readLine();

                while(line != null) {
                    String[] strComponents = line.split(",");
                    structure.components.add(new Component(Integer.parseInt(strComponents[0]), Integer.parseInt(strComponents[1]), Integer.parseInt(strComponents[2]), Material.valueOf(strComponents[3])));
                    line = reader.readLine();
                }
                return structure;
            } catch(Exception e) {
                SkyblockMain.instance.getLogger().info("Unable to load the structure from the file '" + filePath + "'!");
            }
        }

        return null;
    }
}
