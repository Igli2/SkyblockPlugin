package skyblock.registries;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import skyblock.enchantments.EnchantmentBase;

import java.util.*;

public class ItemRegistry {
    public enum  SkyblockItems {
        APPLE_HARVESTER,
        ARCHEOLOGISTS_PICKAXE,
        CREEPER_WAND,
        CLOUD,
        DESERT_BIOME_STICK,
        EMERALD_STUDDED_GEODE,
        FOREST_BIOME_STICK,
        FOSSILIZED_LOG,
        GEODE,
        GRAPPLING_HOOK,
        GUARDS_DEFENDER,
        JACKHAMMER,
        MANIPULATION_GEM,
        SHADOWSTEEL_INGOT,
        SHADOWSTEEL_HELMET,
        SHADOWSTEEL_CHESTPLATE,
        SHADOWSTEEL_LEGGINGS,
        SHADOWSTEEL_BOOTS,
        SUGAR_CUBE,
        SPEEDY_HELMET,
        SPEEDY_CHESTPLATE,
        SPEEDY_LEGGINGS,
        SPEEDY_BOOTS,
        SPOOL_OF_THREAD,
        SHINY_PEBBLE,
        SHADOW_WARRIOR_SPAWN_EGG,
        SUN_PEARL,
        THE_TRUNK,
        TORNADO,
        TREATED_WOOD,
        TREE_CAPITATOR,
        TREETOP_HELMET
    }

    public HashMap<SkyblockItems, ItemStack> specialItems = new HashMap<>();

    public ItemRegistry() {
        registerSpecialItems();
    }

    private void registerSpecialItems() {
        ItemStack archeologistsPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
        setItemName(archeologistsPickaxe, "Archeologist's Pickaxe");
        makeUnbreakable(archeologistsPickaxe);
        specialItems.put(SkyblockItems.ARCHEOLOGISTS_PICKAXE, archeologistsPickaxe);
        EnchantmentBase.customPickaxes.add(archeologistsPickaxe);

        ItemStack geode = createTexturedSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2FiYjUxZjU5NDgxMTMyNTQ1YjUwZTQ3NWU3NjYyMzljNzljNjI0ZTliOTZhYjNhMGFjYjJhZjMwMWQ5NmM3OSJ9fX0=",
                new int[]{-1136006473, 240537101, -1791113915, -2037819923});
        setItemName(geode, "Geode");
        specialItems.put(SkyblockItems.GEODE, geode);

        ItemStack sugarCube = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2E2YWE0MGRiNzQxYTZjYmVkYjExOWEzZTVjYmE4YTg3ZjdlYzhmNzRkMjY4YWQ4MjgyYTQ2ZTVlMDU0ZmNiOSJ9fX0=",
                new int[]{-1171650357, -2028255289, -1780378138, 1576325737});
        setItemName(sugarCube, "Sugar Cube");
        specialItems.put(SkyblockItems.SUGAR_CUBE, sugarCube);

        ItemStack speedyHelmet = new ItemStack(Material.LEATHER_HELMET);
        addEnchantEffect(speedyHelmet);
        setArmorColor(speedyHelmet, 180, 220, 140);
        setItemName(speedyHelmet, "Speedy Helmet");
        setLore(speedyHelmet, Collections.singletonList(ChatColor.GOLD + "Gives you permanent speed"));
        setAttrModifier(speedyHelmet, Attribute.GENERIC_ARMOR, "generic.armor", 1.0, EquipmentSlot.HEAD);
        setAttrModifier(speedyHelmet, Attribute.GENERIC_MOVEMENT_SPEED, "generic.movement_speed", 0.02, EquipmentSlot.HEAD);
        makeUnbreakable(speedyHelmet);
        specialItems.put(SkyblockItems.SPEEDY_HELMET, speedyHelmet);
        EnchantmentBase.customHelmets.add(speedyHelmet);

        ItemStack speedyChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        addEnchantEffect(speedyChestplate);
        setArmorColor(speedyChestplate, 180, 220, 140);
        setItemName(speedyChestplate, "Speedy Chestplate");
        setLore(speedyChestplate, Collections.singletonList(ChatColor.GOLD + "Gives you permanent speed"));
        setAttrModifier(speedyChestplate, Attribute.GENERIC_ARMOR, "generic.armor", 3.0, EquipmentSlot.CHEST);
        setAttrModifier(speedyChestplate, Attribute.GENERIC_MOVEMENT_SPEED, "generic.movement_speed", 0.02, EquipmentSlot.CHEST);
        makeUnbreakable(speedyChestplate);
        specialItems.put(SkyblockItems.SPEEDY_CHESTPLATE, speedyChestplate);
        EnchantmentBase.customChestplates.add(speedyChestplate);

        ItemStack speedyLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
        addEnchantEffect(speedyLeggings);
        setArmorColor(speedyLeggings, 180, 220, 140);
        setItemName(speedyLeggings, "Speedy Leggings");
        setLore(speedyLeggings, Collections.singletonList(ChatColor.GOLD + "Gives you permanent speed"));
        setAttrModifier(speedyLeggings, Attribute.GENERIC_ARMOR, "generic.armor", 2.0, EquipmentSlot.LEGS);
        setAttrModifier(speedyLeggings, Attribute.GENERIC_MOVEMENT_SPEED, "generic.movement_speed", 0.02, EquipmentSlot.LEGS);
        makeUnbreakable(speedyLeggings);
        specialItems.put(SkyblockItems.SPEEDY_LEGGINGS, speedyLeggings);
        EnchantmentBase.customLeggings.add(speedyLeggings);

        ItemStack speedyBoots = new ItemStack(Material.LEATHER_BOOTS);
        addEnchantEffect(speedyBoots);
        setArmorColor(speedyBoots, 180, 220, 140);
        setItemName(speedyBoots, "Speedy Boots");
        setLore(speedyBoots, Collections.singletonList(ChatColor.GOLD + "Gives you permanent speed"));
        setAttrModifier(speedyBoots, Attribute.GENERIC_ARMOR, "generic.armor", 1.0, EquipmentSlot.FEET);
        setAttrModifier(speedyBoots, Attribute.GENERIC_MOVEMENT_SPEED, "generic.movement_speed", 0.02, EquipmentSlot.FEET);
        makeUnbreakable(speedyBoots);
        specialItems.put(SkyblockItems.SPEEDY_BOOTS, speedyBoots);
        EnchantmentBase.customBoots.add(speedyBoots);

        ItemStack shinyPebble = new ItemStack(Material.STONE_BUTTON);
        addEnchantEffect(shinyPebble);
        setItemName(shinyPebble, "Shiny Pebble");
        setLore(shinyPebble, Collections.singletonList("A very special rock that's super rare"));
        specialItems.put(SkyblockItems.SHINY_PEBBLE, shinyPebble);

        ItemStack guardsDefender = new ItemStack(Material.STONE_SWORD);
        setItemName(guardsDefender, "Guard's Defender");
        setLore(guardsDefender, Collections.singletonList("The legendary sword of the temple guardians"));
        setAttrModifier(guardsDefender, Attribute.GENERIC_ARMOR, "generic.armor", 4.0, EquipmentSlot.HAND);
        setAttrModifier(guardsDefender, Attribute.GENERIC_ATTACK_DAMAGE, "generic.attack_damage", 3.0, EquipmentSlot.HAND);
        setAttrModifier(guardsDefender, Attribute.GENERIC_ATTACK_SPEED, "generic.attack_speed", -2.4, EquipmentSlot.HAND);
        makeUnbreakable(guardsDefender);
        specialItems.put(SkyblockItems.GUARDS_DEFENDER, guardsDefender);
        EnchantmentBase.customSwords.add(guardsDefender);

        ItemStack fossilizedLog = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWU4ZmU4NDAzMTFmMTI2MzlhOGVlNmFkN2VkNjMyNWIyMmI3OTBkODEyNzg4YzlhZGExMTI4NDE3NzVhZSJ9fX0=",
                new int[]{-34234234, 436345413, 5346534, 55});
        setItemName(fossilizedLog, "Fossilized Log");
        specialItems.put(SkyblockItems.FOSSILIZED_LOG, fossilizedLog);

        ItemStack treeCapitator = new ItemStack(Material.WOODEN_AXE);
        setItemName(treeCapitator, "Tree Capitator");
        setLore(treeCapitator, Collections.singletonList("Cut down whole trees in a single hit!"));
        makeUnbreakable(treeCapitator);
        specialItems.put(SkyblockItems.TREE_CAPITATOR, treeCapitator);
        EnchantmentBase.customAxes.add(treeCapitator);

        ItemStack shadowsteelIngot = new ItemStack(Material.NETHER_BRICK);
        setItemName(shadowsteelIngot, "Shadowsteel Ingot");
        setLore(shadowsteelIngot, Collections.singletonList("A very old metal created by ancient shadow creatures"));
        addEnchantEffect(shadowsteelIngot);
        specialItems.put(SkyblockItems.SHADOWSTEEL_INGOT, shadowsteelIngot);

        ItemStack spoolOfThread = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmI5YTRkZmNjNzE1M2MzMTc2ZGM4Y2YzODg4N2QyZDgzODU1NDVmYTRjNWY4YzJmZjEzZjlkNjllOThlOSJ9fX0=",
                new int[]{-436457567, -3456763, -1, 349875349});
        setItemName(spoolOfThread, "Spool Of Thread");
        specialItems.put(SkyblockItems.SPOOL_OF_THREAD, spoolOfThread);

        ItemStack grapplingHook = new ItemStack(Material.FISHING_ROD);
        setItemName(grapplingHook, "Grappling Hook");
        makeUnbreakable(grapplingHook);
        specialItems.put(SkyblockItems.GRAPPLING_HOOK, grapplingHook);

        ItemStack shadowsteelHelmet = new ItemStack(Material.LEATHER_HELMET);
        setArmorColor(shadowsteelHelmet, 50, 0, 64);
        setItemName(shadowsteelHelmet, "Shadowsteel Helmet");
        setAttrModifier(shadowsteelHelmet, Attribute.GENERIC_ARMOR, "generic.armor", 5.0, EquipmentSlot.HEAD);
        setAttrModifier(shadowsteelHelmet, Attribute.GENERIC_KNOCKBACK_RESISTANCE, "generic.knockback_resistance", 0.05, EquipmentSlot.HEAD);
        setAttrModifier(shadowsteelHelmet, Attribute.GENERIC_ARMOR_TOUGHNESS, "generic.armor_toughness", 2.0, EquipmentSlot.HEAD);
        setAttrModifier(shadowsteelHelmet, Attribute.GENERIC_MAX_HEALTH, "generic.max_health", 2.0, EquipmentSlot.HEAD);
        makeUnbreakable(shadowsteelHelmet);
        specialItems.put(SkyblockItems.SHADOWSTEEL_HELMET, shadowsteelHelmet);
        EnchantmentBase.customHelmets.add(shadowsteelHelmet);

        ItemStack shadowsteelChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        setArmorColor(shadowsteelChestplate, 50, 0, 64);
        setItemName(shadowsteelChestplate, "Shadowsteel Chestplate");
        setAttrModifier(shadowsteelChestplate, Attribute.GENERIC_ARMOR, "generic.armor", 10.0, EquipmentSlot.CHEST);
        setAttrModifier(shadowsteelChestplate, Attribute.GENERIC_KNOCKBACK_RESISTANCE, "generic.knockback_resistance", 0.05, EquipmentSlot.CHEST);
        setAttrModifier(shadowsteelChestplate, Attribute.GENERIC_ARMOR_TOUGHNESS, "generic.armor_toughness", 2.0, EquipmentSlot.CHEST);
        setAttrModifier(shadowsteelChestplate, Attribute.GENERIC_MAX_HEALTH, "generic.max_health", 4.0, EquipmentSlot.CHEST);
        makeUnbreakable(shadowsteelChestplate);
        specialItems.put(SkyblockItems.SHADOWSTEEL_CHESTPLATE, shadowsteelChestplate);
        EnchantmentBase.customChestplates.add(shadowsteelChestplate);

        ItemStack shadowsteelLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
        setArmorColor(shadowsteelLeggings, 50, 0, 64);
        setItemName(shadowsteelLeggings, "Shadowsteel Leggings");
        setAttrModifier(shadowsteelLeggings, Attribute.GENERIC_ARMOR, "generic.armor", 8.0, EquipmentSlot.LEGS);
        setAttrModifier(shadowsteelLeggings, Attribute.GENERIC_KNOCKBACK_RESISTANCE, "generic.knockback_resistance", 0.05, EquipmentSlot.LEGS);
        setAttrModifier(shadowsteelLeggings, Attribute.GENERIC_ARMOR_TOUGHNESS, "generic.armor_toughness", 2.0, EquipmentSlot.LEGS);
        setAttrModifier(shadowsteelLeggings, Attribute.GENERIC_MAX_HEALTH, "generic.max_health", 2.0, EquipmentSlot.LEGS);
        makeUnbreakable(shadowsteelLeggings);
        specialItems.put(SkyblockItems.SHADOWSTEEL_LEGGINGS, shadowsteelLeggings);
        EnchantmentBase.customLeggings.add(shadowsteelLeggings);

        ItemStack shadowsteelBoots = new ItemStack(Material.LEATHER_BOOTS);
        setArmorColor(shadowsteelBoots, 50, 0, 64);
        setItemName(shadowsteelBoots, "Shadowsteel Boots");
        setAttrModifier(shadowsteelBoots, Attribute.GENERIC_ARMOR, "generic.armor", 5.0, EquipmentSlot.FEET);
        setAttrModifier(shadowsteelBoots, Attribute.GENERIC_KNOCKBACK_RESISTANCE, "generic.knockback_resistance", 0.05, EquipmentSlot.FEET);
        setAttrModifier(shadowsteelBoots, Attribute.GENERIC_ARMOR_TOUGHNESS, "generic.armor_toughness", 2.0, EquipmentSlot.FEET);
        setAttrModifier(shadowsteelBoots, Attribute.GENERIC_MAX_HEALTH, "generic.max_health", 2.0, EquipmentSlot.FEET);
        makeUnbreakable(shadowsteelBoots);
        specialItems.put(SkyblockItems.SHADOWSTEEL_BOOTS, shadowsteelBoots);
        EnchantmentBase.customBoots.add(shadowsteelBoots);

        ItemStack shadowWarriorSpawnEgg = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNhOGIwZTlhMDZmMTk5MzFlYTU5N2JhNzY4MjNiNTk0MDQ4NDFkNTNiYzQ4MTEyN2JlOWY3MTBjNmI5OTgifX19",
                new int[]{-432423452, 45435, 435646754, 6000});
        setItemName(shadowWarriorSpawnEgg, "Shadow Warrior Spawn Egg");
        specialItems.put(SkyblockItems.SHADOW_WARRIOR_SPAWN_EGG, shadowWarriorSpawnEgg);

        ItemStack creeperWand = new ItemStack(Material.BAMBOO);
        setItemName(creeperWand, "Creeper Wand");
        setLore(creeperWand, Arrays.asList(ChatColor.RED + "Beware!", "Explosions are usually lethal!"));
        specialItems.put(SkyblockItems.CREEPER_WAND, creeperWand);

        ItemStack treetopHelmet = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM3NjVlOGNlMTFhZTZmMTMxYzcyNWMwN2Y1OGYyZDQxMzJkOTg0YWQ2NjM2MDM5YTdhMzZhM2M1MTI3MjEifX19",
                new int[]{-25443534, 453453, 4647686, 1111});
        setItemName(treetopHelmet, "Treetop Helmet");
        setAttrModifier(treetopHelmet, Attribute.GENERIC_ARMOR, "generic.armor", 2.0, EquipmentSlot.HEAD);
        setAttrModifier(treetopHelmet, Attribute.GENERIC_MOVEMENT_SPEED, "generic.movement_speed", -0.05, EquipmentSlot.HEAD);
        setAttrModifier(treetopHelmet, Attribute.GENERIC_MAX_HEALTH, "generic.max_health", 15.0, EquipmentSlot.HEAD);
        specialItems.put(SkyblockItems.TREETOP_HELMET, treetopHelmet);

        ItemStack appleHarvester = new ItemStack(Material.IRON_HOE);
        setItemName(appleHarvester, "Apple Harvester");
        makeUnbreakable(appleHarvester);
        specialItems.put(SkyblockItems.APPLE_HARVESTER, appleHarvester);

        ItemStack treatedWood = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc1NWYyNDJhNjVhYmRhNDdhZDQ3MDM1OTRiMzMxZTA4YzU2ZjBmODY2MTQ0MmRjNTY1ZjQzZWYzYzkzYTQyMyJ9fX0=",
                new int[]{4444, -55555, 666666, 7777777});
        setItemName(treatedWood, "Treated Wood");
        setLore(treatedWood, Collections.singletonList("Fire-Resistant"));
        specialItems.put(SkyblockItems.TREATED_WOOD, treatedWood);

        ItemStack theTrunk = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE5NWRkMmVkYTAzY2E0OTE3OTZmNTM4ZDYyNTU3OWE4MjhiNzE4NDNmYjYwODY5YjEyYzkxZWNlMTUxNiJ9fX0=",
                new int[]{-1, -1111, 0, 1111000549});
        setItemName(theTrunk, "The Trunk");
        setLore(theTrunk, Arrays.asList("You probably need a lot of", "strength to use this."));
        setAttrModifier(theTrunk, Attribute.GENERIC_MOVEMENT_SPEED, "generic.movement_speed", -0.01, EquipmentSlot.HAND);
        setAttrModifier(theTrunk, Attribute.GENERIC_ATTACK_DAMAGE, "generic.attack_damage", 15, EquipmentSlot.HAND);
        setAttrModifier(theTrunk, Attribute.GENERIC_ATTACK_SPEED, "generic.attack_speed", -3.5, EquipmentSlot.HAND);
        specialItems.put(SkyblockItems.THE_TRUNK, theTrunk);

        ItemStack tornado = new ItemStack(Material.IRON_SWORD);
        setItemName(tornado, "Tornado");
        setLore(tornado, Collections.singletonList("The ultimate weapon of the gods"));
        setAttrModifier(tornado, Attribute.GENERIC_ATTACK_DAMAGE, "generic.attack_damage", 5, EquipmentSlot.HAND);
        setAttrModifier(tornado, Attribute.GENERIC_ATTACK_SPEED, "generic.attack_speed", -0.5, EquipmentSlot.HAND);
        makeUnbreakable(tornado);
        specialItems.put(SkyblockItems.TORNADO, tornado);
        EnchantmentBase.customSwords.add(tornado);

        ItemStack sunPearl = new ItemStack(Material.SNOWBALL);
        setItemName(sunPearl, "Sun Pearl");
        addEnchantEffect(sunPearl);
        specialItems.put(SkyblockItems.SUN_PEARL, sunPearl);

        ItemStack cloud = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDY2YjEwYmY2ZWUyY2Q3ZTNhYzk2ZDk3NDllYTYxNmFhOWM3MzAzMGJkY2FlZmZhZWQyNDllNTVjODQ5OTRhYyJ9fX0=",
                new int[]{-34345, 56900000, 4359211, 653299119});
        setItemName(cloud, "Cloud");
        specialItems.put(SkyblockItems.CLOUD, cloud);

        ItemStack manipulationGem = new ItemStack(Material.MAGMA_CREAM);
        setItemName(manipulationGem, "Manipulation Gem");
        addEnchantEffect(manipulationGem);
        specialItems.put(SkyblockItems.MANIPULATION_GEM, manipulationGem);

        ItemStack desertBiomeStick = new ItemStack(Material.STICK);
        setItemName(desertBiomeStick, "Desert Biome Stick");
        setLore(desertBiomeStick, Arrays.asList("Size: 1 block", "Left click to change"));
        specialItems.put(SkyblockItems.DESERT_BIOME_STICK, desertBiomeStick);

        ItemStack forestBiomeStick = new ItemStack(Material.STICK);
        setItemName(forestBiomeStick, "Forest Biome Stick");
        setLore(forestBiomeStick, Arrays.asList("Size: 1 block", "Left click to change"));
        specialItems.put(SkyblockItems.FOREST_BIOME_STICK, forestBiomeStick);

        ItemStack emeraldStuddedGeode = createTexturedSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjI4ZGQzZDliODFjYzlmOTZhNGMxMWZlMTNiMDc5NDk0ZjI3ZmM1YjkzM2M0ZjA4MzNmNjU2NDQ3MmVlMTYwOSJ9fX0=",
                new int[]{3454335, -455689, 33333333, -1010110045});
        setItemName(emeraldStuddedGeode, "Emerald-Studded Geode");
        specialItems.put(SkyblockItems.EMERALD_STUDDED_GEODE, emeraldStuddedGeode);

        ItemStack jackhammer = new ItemStack(Material.IRON_PICKAXE);
        setItemName(jackhammer, "Jackhammer");
        makeUnbreakable(jackhammer);
        specialItems.put(SkyblockItems.JACKHAMMER, jackhammer);
        EnchantmentBase.customPickaxes.add(jackhammer);
    }

    public static void setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
    }

    public static void setArmorColor(ItemStack itemStack, int r, int g, int b) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setColor(Color.fromRGB(r, g, b));
            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        }
        itemStack.setItemMeta(itemMeta);
    }

    public static void setItemName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
        }
        item.setItemMeta(itemMeta);
    }

    public static void addEnchantEffect(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemMeta);
    }

    public static void setAttrModifier(ItemStack itemStack, Attribute attr, String attrName, double value, EquipmentSlot slot) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addAttributeModifier(attr, new AttributeModifier(UUID.randomUUID(), attrName, value, AttributeModifier.Operation.ADD_NUMBER, slot));
        }
        itemStack.setItemMeta(itemMeta);
    }

    public static ItemStack createTexturedSkull(String textureStr, int[] id) {
        net.minecraft.server.v1_16_R3.ItemStack head = CraftItemStack.asNMSCopy(new ItemStack(Material.PLAYER_HEAD));
        NBTTagCompound tag = head.getOrCreateTag();
        NBTTagCompound skullOwner = new NBTTagCompound();

        //Texture
        NBTTagCompound properties = new NBTTagCompound();
        NBTTagList textures = new NBTTagList();
        NBTTagCompound texture = new NBTTagCompound();
        texture.setString("Value", textureStr);
        textures.add(texture);
        properties.set("textures", textures);
        skullOwner.set("Properties", properties);

        //ID
        skullOwner.setIntArray("Id", id);

        tag.set("SkullOwner", skullOwner);
        head.setTag(tag);

        return CraftItemStack.asBukkitCopy(head);
    }

    public static void makeUnbreakable(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setUnbreakable(true);
        }
        item.setItemMeta(itemMeta);
    }

    public static boolean isItemStackEqual(ItemStack itemStack, ItemStack other) {
        if (itemStack == null) {
            itemStack = new ItemStack(Material.AIR);
        }
        if (other == null) {
            other = new ItemStack(Material.AIR);
        }

        if (itemStack.getType() == Material.AIR && other.getType() == Material.AIR) {
            return true;
        } else if (itemStack.getType() == Material.AIR || other.getType() == Material.AIR) {
            return false;
        }

        ItemMeta itemStackMeta = itemStack.getItemMeta();
        if (itemStackMeta == null) {return false;}
        ItemMeta otherMeta = other.getItemMeta();
        if (otherMeta == null) {return false;}

        String itemStackName = (itemStackMeta.hasDisplayName()) ? itemStackMeta.getDisplayName().toLowerCase() : itemStack.getType().toString().toLowerCase();
        String otherName = (otherMeta.hasDisplayName()) ? otherMeta.getDisplayName().toLowerCase() : other.getType().toString().toLowerCase();

        return itemStackName.equals(otherName);
    }

    public ItemStack getItemStack(SkyblockItems id) {
        return this.specialItems.get(id).clone();
    }
}
