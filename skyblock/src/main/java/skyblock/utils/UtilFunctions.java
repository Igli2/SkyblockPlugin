package skyblock.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class UtilFunctions {

    public static String itemStackToBase64(ItemStack item) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(outStream);
            bukkitStream.writeObject(item);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(outStream.toByteArray());
    }

    public static ItemStack itemStackFromBase64(String encoded) {
        byte[] programBytes = Base64.getDecoder().decode(encoded);

        InputStream inStream = new ByteArrayInputStream(programBytes);
        try {
            BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(inStream);
            return (ItemStack) bukkitStream.readObject();
        } catch (Exception ioException) {
            ioException.printStackTrace();
        }

        return null;
    }
}
