package skyblock.enchantments;

import java.util.ArrayList;

public class EnchantmentRegistry {
    public static ArrayList<EnchantmentBase> enchantments = new ArrayList<>();

    public static void registerAllEnchantments() {
        EnchantmentRegistry.enchantments.add(new VenomousEnchantment());
    }
}
