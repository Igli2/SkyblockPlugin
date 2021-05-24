package skyblock.utils.quest;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import skyblock.SkyblockMain;
import skyblock.registries.ItemRegistry;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class QuestRegistry {
    private final HashMap<String, Quest> quests = new HashMap<>();

    public QuestRegistry() {
        // register / create all quests
        Quest goldenSlimeQuest = new Quest();
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("I'm here to operate this well. But there has been a storm recently...", "The well got damaged and can no longer be operated.", "This well is vital for us as there is absolutely no water around here.", "I need a new chain and some sandstone, would you help me please?"), "[NPC] Harvey"));
        goldenSlimeQuest.addTask(new TaskDeliver(Arrays.asList("Thanks!", "That will do for now.", "My wife is just cooking dinner. Maybe she needs something too."), "[NPC] Harvey", "Bring me 2 chains and 16 sandstone.", new HashMap<ItemStack, Integer>() {{
            put(new ItemStack(Material.CHAIN, 2), 1);
            put(new ItemStack(Material.SANDSTONE, 16), 1);
        }}));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("I dont like cooking.", "But I have to...", "Could you bring me some rabbit stew so I just have to warm it up?"), "[NPC] Arif"));
        goldenSlimeQuest.addTask(new TaskDeliver(Collections.singletonList("Thanks."), "[NPC] Arif", "Could you bring me 2 rabbit stews?", new HashMap<ItemStack, Integer>() {{
            put(new ItemStack(Material.RABBIT_STEW, 1), 2);
        }}));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("I heard some bandits are camping over there behind the cliffs.", "I'm afraid they could raid us.", "Check out what they're about to do and tell me later."), "[NPC] Harvey"));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("You're here because of that guy camping at the well.", "I have seen you talking.", "What's his concern? We won't do him any harm. He got nothing we could steal. *laughs*"), "[NPC] Mitchell"));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("You said they won't do me any harm?", "I don't believe them. They are bandits and they look suspicious.", "What if they want to raid someone else? You have to find and warn them!"), "[NPC] Harvey"));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("I'm not afraid of bandits.", "But you could help me a litte...", "My pickaxe broke and I need a new one."), "[NPC] Charles"));
        goldenSlimeQuest.addTask(new TaskDeliver(Arrays.asList("Thanks. Now I can finally mine that gold!", "If you don't mind, check that bandits again...", "Now that I've thought of them, I don't like them being there."), "[NPC] Charles", "Bring me one diamond pickaxe.", new HashMap<ItemStack, Integer>() {{
            put(new ItemStack(Material.DIAMOND_PICKAXE, 1), 1);
        }}));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("You smell like gold. Why though?", "Hey Mitchell, I think there's something to raid!", "We're going to be rich!"), "[NPC] Darnell"));
        goldenSlimeQuest.addTask(new TaskTalk(Collections.singletonList("Have you heard that? We will soon be rich!"), "[NPC] Mitchell"));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("They wanna steal steal my beloved gold? How dare they!", "Quick! Bring me a sword and some armor."), "[NPC] Charles"));
        goldenSlimeQuest.addTask(new TaskDeliver(Arrays.asList("Good. That will do for now.", "But better if they don't attack at all. Let's find out what they want."), "[NPC] Charles", "Bring me an iron sword and iron chestplate", new HashMap<ItemStack, Integer>() {{
            put(new ItemStack(Material.IRON_SWORD, 1), 1);
            put(new ItemStack(Material.IRON_CHESTPLATE, 1), 1);
        }}));
        goldenSlimeQuest.addTask(new TaskTalk(Collections.singletonList("What? He should just give us all of his gold."), "[NPC] Mitchell"));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("They want all my gold I worked so hard for?", "No way!", "I'm kinda poor, but if you could help me pay the bribe, I could give you something else.", "Ask them how much it is."), "[NPC] Charles"));
        goldenSlimeQuest.addTask(new TaskTalk(Collections.singletonList("We want at least 2 backpacks filled with gold!"), "[NPC] Mitchell"));
        goldenSlimeQuest.addTask(new TaskDeliver(Arrays.asList("WOW! So shiny!", "I don't think we will ever need to raid someone again."), "[NPC] Mitchell", "Bring us 2 stacks of gold ingots", new HashMap<ItemStack, Integer>() {{
            put(new ItemStack(Material.GOLD_INGOT, 64), 2);
        }}));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("Thanks!", "Let me look where I put that thing.", "Maybe in my back pockets?", "...", "...", "...", "Oh, now I remember. I traded it for some water at the well.", "Ask Harvey and he will probably give it to you."), "[NPC] Charles"));
        goldenSlimeQuest.addTask(new TaskTalk(Arrays.asList("Hey! It's you again.", "That's true. Charles gave that to me a while ago.", "But I have no use for this helmet, so you can have it."), "[NPC] Harvey"));
        goldenSlimeQuest.addTask(new TaskReceive(Collections.singletonList("Here you go! And thanks for your help."), "[NPC] Harvey", new HashMap<ItemStack, Integer>() {{
            put(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GOLDEN_SLIME_HELMET), 1);
        }}));
        this.quests.put("goldenSlimeQuest", goldenSlimeQuest);

        Quest goldenMelonQuest = new Quest();
        goldenMelonQuest.addTask(new TaskTalk(Arrays.asList("Welcome on my jungle oasis.", "The weeds and plants here are growing insanely fast.", "That's why I have to be here all day long.", "Could you do me a favor and bring me a new hoe?"), "[NPC] Letitia"));
        goldenMelonQuest.addTask(new TaskDeliver(Arrays.asList("Now I can finally eliminate those weeds effectively", "You can take a look around if you want.", "People always need help."), "[NPC] Letitia", "Bring me an iron hoe.", new HashMap<ItemStack, Integer>() {{
            put(new ItemStack(Material.IRON_HOE, 1), 1);
        }}));
        goldenMelonQuest.addTask(new TaskTalk(Arrays.asList("Hi, I think I've never seen you here before...", "Whatever, my friend is missing and I can't look for him because my girlfriend needs me.", "My friend is a little lazy...", "And he likes climbing trees.", "It would be nice of you if you looked for him. And tell him he should talk to me."), "[NPC] Gordon"));
        goldenMelonQuest.addTask(new TaskTalk(Arrays.asList("Gordon is looking for me? But I'm sure I told him that I'm away for a while.", "I climbed this tree to have a better view and look where I would go next.", "And now I'm not able to get down anymore.", "I think I should just stay here and build a small tree house.", "But first talk to Gordon and tell him I won't come back for another month and that he doesn't have to worry about me."), "[NPC] Anderson"));
        goldenMelonQuest.addTask(new TaskTalk(Arrays.asList("If he really said so...", "Then I shall respect his decision."), "[NPC Gordon]"));
        goldenMelonQuest.addTask(new TaskTalk(Arrays.asList("While you were talking to Gordon, I made plans for my tree house.", "Now that I know Gordon no longer worries about me, I can start constructing the roof.", "I just need some wood."), "[NPC] Anderson"));
        goldenMelonQuest.addTask(new TaskDeliver(Arrays.asList("Perfect!", "It will take a few weeks but you can check back anytime you want and visit me.", "I probably need some more stuff sooner or later."), "[NPC] Anderson", "Bring me some stacks of jungle logs.", new HashMap<ItemStack, Integer>() {{
            put(new ItemStack(Material.JUNGLE_LOG, 64), 8);
        }}));
        goldenMelonQuest.addTask(new TaskTalk(Collections.singletonList("It's Evelyn's birthday soon!"), "[NPC] Letitia"));
        goldenMelonQuest.addTask(new TaskTalk(Arrays.asList("I still need a present for Evelyn.", "I thought of something shiny and cold.", "Just like her beautiful blue eyes.", "Her main hobby is collecting rare artifacts.", "Could you help me get what I just described?"), "[NPC] Gordon"));
        goldenMelonQuest.addTask(new TaskDeliver(Arrays.asList("Thanks. I'm sure she likes it.", "She will be very happy!"), "[NPC] Gordon", "It's a drop from a mysterious monster that lives in cold areas.", new HashMap<ItemStack, Integer>() {{
            put(SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.ICE_PEARL), 1);
        }}));
        this.quests.put("goldenMelonQuest", goldenMelonQuest);

        this.loadData();
    }

    public void saveData() {
        for (String questName : this.quests.keySet()) {
            Quest quest = this.quests.get(questName);
            quest.saveData(questName);
        }
    }

    public void loadData() {
        for (String questName : this.quests.keySet()) {
            Quest quest = this.quests.get(questName);
            quest.loadData(questName);
        }
    }

    public long playerInteractQuestNPC(Player player, QuestNPCEntity npc) {
        for (Quest quest : this.quests.values()) {
            long result = quest.triggerTasks(player, npc);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
}
