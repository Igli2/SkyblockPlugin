package skyblock.utils;

import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skyblock.SkyblockMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BankerNPCEntity extends NPCEntity {
    private static final int[] contractSlots = new int[]{10, 12, 14, 16};
    // todo save to database
    private static HashMap<UUID, List<Contract>> contracts = new HashMap<>();

    public BankerNPCEntity(String name, Property textures, World world) {
        super(name, textures, world);
        this.getEntity().setPositionRotation(14, 110, -21.5, 0, 0);
        SkyblockMain.npcRegistry.registerNPC(this);
    }

    public BankerNPCEntity() {
        this("[NPC] Banker", new Property("textures", "", ""), Bukkit.getWorld("lobby"));
    }

    @Override
    public void interact(Player player) {
        Bukkit.getScheduler().runTask(SkyblockMain.instance, () -> {
            Inventory inventory = BankerNPCEntity.createInventory(player);
            player.openInventory(inventory);
        });
    }

    public static void onInventoryClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        for (int i = 0; i < 4; ++i) {
            if (BankerNPCEntity.contractSlots[i] == slot) {
                Player player = (Player) event.getView().getPlayer();
                Contract contract = BankerNPCEntity.contracts.get(player.getUniqueId()).get(i);
                if (contract.canStart(player)) {
                    contract.start(player);
                } else if (contract.canClaim()) {
                    contract.claim(player);
                }
                event.getInventory().setItem(BankerNPCEntity.contractSlots[i], contract.getItem());
            }
        }

        event.setCancelled(true);
    }

    private static Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "[NPC] Banker");
        for (int i = 0; i < 27; ++i) {
            inventory.setItem(i, CraftingTable.PLACEHOLDER);
        }

        if (!BankerNPCEntity.contracts.containsKey(player.getUniqueId())) { // create new entry
            BankerNPCEntity.contracts.put(player.getUniqueId(), BankerNPCEntity.createContracts());
        }
        List<Contract> playerContracts = BankerNPCEntity.contracts.get(player.getUniqueId());
        for (int i = 0; i < 4; ++i) {
            inventory.setItem(BankerNPCEntity.contractSlots[i], playerContracts.get(i).getItem());
        }

        return inventory;
    }

    private static List<Contract> createContracts() {
        List<Contract> list = new ArrayList<>();
        list.add(new Contract(1000));
        list.add(new Contract(10000));
        list.add(new Contract(100000));
        list.add(new Contract(1000000));
        return list;
    }

    private static class Contract {
        public long timeStamp = -1;
        public boolean claimed = true;
        public int value;
        public double profit;
        public int timeToComplete;

        public Contract(int value) {
            this.value = value;
            switch (value) {
                case 1000:
                    this.profit = 1.2;
                    this.timeToComplete = 60 * 60 * 1000;
                    break;
                case 10000:
                    this.profit = 1.15;
                    this.timeToComplete = 4 * 60 * 60 * 1000;
                    break;
                case 100000:
                    this.profit = 1.1;
                    this.timeToComplete = 10 * 60 * 60 * 1000;
                    break;
                case 1000000:
                    this.profit = 1.05;
                    this.timeToComplete = 24 * 60 * 60 * 1000;
                    break;
                default:
                    this.profit = 1.0;
                    this.timeToComplete = 0;
            }
        }

        public void claim(Player player) {
            SkyblockMain.moneyHandler.addMoney(player, (int) (this.value * this.profit));
            this.claimed = true;
            this.timeStamp = -1;
        }

        public boolean canClaim() {
            return this.timeStamp < System.currentTimeMillis();
        }

        public void start(Player player) {
            SkyblockMain.moneyHandler.removeMoney(player, this.value);
            this.claimed = false;
            this.timeStamp = System.currentTimeMillis() + this.timeToComplete;
        }

        public boolean canStart(Player player) {
            if (SkyblockMain.moneyHandler.getMoney(player) >= this.value) {
                return this.claimed && this.timeStamp == -1;
            }
            return false;
        }

        public ItemStack getItem() {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("Contract");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("Value: " + this.value);
                lore.add("Profit: " + this.profit);
                if (!this.claimed) {
                    long timeLeft = (this.timeStamp - System.currentTimeMillis()) / 1000L;
                    if (timeLeft > 0) {
                        lore.add("Time to complete: " + timeLeft + "s");
                    } else {
                        lore.add("Time to complete: 0s");
                    }
                }
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
            return item;
        }
    }
}
