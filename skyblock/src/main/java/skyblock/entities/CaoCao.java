package skyblock.entities;

import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import skyblock.SkyblockMain;
import skyblock.registries.EntityRegistry;
import skyblock.registries.ItemRegistry;

import javax.annotation.Nullable;

public class CaoCao extends EntitySkeletonWither {
    private BossBar bossBar;
    private CaoCaoPlayer player;
    protected int minionCount = 6;

    public CaoCao(EntityTypes<? extends EntitySkeletonWither> entitytypes, World world) {
        super(entitytypes, world);
    }

    public CaoCao(Location location) {
        super(EntityTypes.WITHER_SKELETON, ((CraftWorld)location.getWorld()).getHandle());

        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setNoAI(false);

        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(64);
        this.getAttributeInstance(GenericAttributes.ARMOR).setValue(10);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(500);
        this.setHealth(500);
        this.setCanPickupLoot(false);

        this.bossBar = Bukkit.createBossBar(ChatColor.BLACK + "" + ChatColor.BOLD + "Cao Cao", BarColor.RED, BarStyle.SOLID, BarFlag.DARKEN_SKY, BarFlag.CREATE_FOG, BarFlag.PLAY_BOSS_MUSIC);
        this.bossBar.setProgress(1.0);
        this.bossBar.setVisible(true);

        for(Player player : Bukkit.getOnlinePlayers()) {
            this.bossBar.addPlayer(player);
        }

        this.player = EntityRegistry.spawnCaoCaoPlayer(location, this);

        WitherSkeleton bukkitEntity = (WitherSkeleton) this.getBukkitEntity();
        bukkitEntity.setCustomName(ChatColor.BLACK + "" + ChatColor.BOLD + "Cao Cao");
        bukkitEntity.setCustomNameVisible(true);
        bukkitEntity.setInvisible(true);

        for (int i = 0; i < this.minionCount; i++) {
            this.spawnMinion();
        }

        this.world.getWorld().playSound(bukkitEntity.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 1);
    }

    protected void onMinionDeath() {
        if(--this.minionCount == 0) {
            this.bossBar.setColor(BarColor.PURPLE);
        }
    }

    private void spawnMinion() {
        int offX =  4 - SkyblockMain.random.nextInt(9);
        int offZ =  4 - SkyblockMain.random.nextInt(9);

        Location minionPos = this.getBukkitEntity().getLocation().clone();
        minionPos.setX(minionPos.getX() + offX);
        minionPos.setZ(minionPos.getZ() + offZ);
        if (minionPos.getWorld() != null) {
            int y = minionPos.getWorld().getHighestBlockYAt(minionPos.getBlockX(), minionPos.getBlockZ()) + 1;
            if (y == 0) {
                this.spawnMinion();
                return;
            }
            minionPos.setY(y);
        }

        CaoCaoMinion minion = new CaoCaoMinion(minionPos, this);
        this.getWorld().addEntity(minion);
    }

    @Override
    protected void dropExperience() {
        this.world.addEntity(new EntityExperienceOrb(this.world, this.locX(), this.locY(), this.locZ(), 6000));
    }

    @Nullable
    @Override
    public EntityItem a(IMaterial imaterial) {
        return null;
    }

    @Nullable
    @Override
    public EntityItem a(IMaterial imaterial, int i) {
        return null;
    }

    @Nullable
    @Override
    public EntityItem a(net.minecraft.server.v1_16_R3.ItemStack itemstack) {
        return null;
    }

    @Nullable
    @Override
    public EntityItem a(net.minecraft.server.v1_16_R3.ItemStack itemstack, float f) {
        return null;
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(5, new PathfinderGoalRandomStrollLand(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    protected void dropDeathLoot() {
        org.bukkit.inventory.ItemStack bronzeIngot = SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.BRONZE_INGOT);
        this.getBukkitEntity().getWorld().dropItemNaturally(this.getBukkitEntity().getLocation(), bronzeIngot);
    }

    @Override
    protected boolean damageEntity0(DamageSource damagesource, float f) {
        if (this.minionCount > 0) {return false;}
        if (damagesource.getEntity() instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) damagesource.getEntity();
            ItemStack nmsItemStack = entityPlayer.getItemInMainHand();
            org.bukkit.inventory.ItemStack itemStack = CraftItemStack.asBukkitCopy(nmsItemStack);
            if (ItemRegistry.isItemStackEqual(itemStack, SkyblockMain.itemRegistry.getItemStack(ItemRegistry.SkyblockItems.GREEN_DRAGON_CRESCENT_BLADE))) {
                this.setHealth(this.getHealth() - f);
                this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
                return true;
            }
        } else if (damagesource == DamageSource.OUT_OF_WORLD) {
            this.setHealth(this.getHealth() - f * 100);
            this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.player.teleportAndSync(this.lastX, this.lastY, this.lastZ);
        this.player.yaw = this.yaw;
        this.player.pitch = this.pitch;
        this.player.setHeadRotation(this.getHeadRotation());

        // dash
        if(SkyblockMain.random.nextDouble() < 0.01 && this.minionCount == 0) {
            Location locationThis = this.getBukkitEntity().getLocation();
            org.bukkit.World world = this.getWorld().getWorld();
            for (Player player : world.getPlayers()) {
                if (player.getUniqueId().toString().equals(world.getName())) { // get nearest player that is allowed to build in this world
                    Location playerLocation = player.getLocation();
                    this.setMot((locationThis.getX() - playerLocation.getX()) * -0.3, (locationThis.getY() - playerLocation.getY()) * -0.3, (locationThis.getZ() - playerLocation.getZ()) * -0.3);
                    world.spawnParticle(org.bukkit.Particle.SMOKE_LARGE, locationThis, 20);
                }
            }
        }

        // evoker fangs
        if(SkyblockMain.random.nextDouble() < 0.007) {
            org.bukkit.World world = this.getWorld().getWorld();
            for (Player player : world.getPlayers()) {
                if (player.getUniqueId().toString().equals(world.getName())) { // get nearest player that is allowed to build in this world
                    Location playerLocation = player.getLocation();
                    float angle = (float)MathHelper.d(playerLocation.getZ() - this.locZ(), playerLocation.getX() - this.locX());
                    for(int i = 0; i < 16; ++i) {
                        double distance = 1.25D * (double)(i + 1);
                        this.spawnEvokerFangs(this.locX() + (double)MathHelper.cos(angle) * distance, this.locZ() + (double)MathHelper.sin(angle) * distance, Math.min(this.locY(), playerLocation.getY()), Math.max(this.locY(), playerLocation.getY()) + 1, angle, i);
                    }
                }
            }
        }

        // potions
        if(SkyblockMain.random.nextDouble() < 0.01 && this.minionCount == 0) {
            org.bukkit.World world = this.getWorld().getWorld();
            for (Player player : world.getPlayers()) {
                if (player.getUniqueId().toString().equals(world.getName())) { // get nearest player that is allowed to build in this world
                    Location playerLocation = player.getLocation();

                    double d0 = playerLocation.getX() - this.locX();
                    double d1 = playerLocation.getY() - this.locY();
                    double d2 = playerLocation.getZ() - this.locZ();
                    float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2);

                    org.bukkit.inventory.ItemStack bukkitItem = getRandomPotion();

                    EntityPotion entitypotion = new EntityPotion(this.world, this);
                    entitypotion.setItem(CraftItemStack.asNMSCopy(bukkitItem));
                    entitypotion.pitch -= -20.0F;
                    entitypotion.shoot(d0, d1 + (double)(f1 * 0.2F), d2, 0.75F, 8.0F);

                    this.world.playSound(null, this.locX(), this.locY(), this.locZ(), SoundEffects.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                    this.world.addEntity(entitypotion);
                }
            }
        }
    }

    private org.bukkit.inventory.ItemStack getRandomPotion() {
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(org.bukkit.Material.POTION, 1);

        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
        if (meta != null) {
            PotionEffectType effect = getRandomPotionEffect();
            if (effect != null) {
                meta.setColor(Color.ORANGE);
                meta.addCustomEffect(new PotionEffect(effect, 100, 0), true);
            }
        }
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private PotionEffectType getRandomPotionEffect() {
        PotionEffectType potionregistry = PotionEffectType.HARM;
        double rand = Math.random();

        if (rand < 0.25) {
            potionregistry = PotionEffectType.SLOW;
        } else if (rand < 0.5) {
            potionregistry = PotionEffectType.POISON;
        } else if (rand < 0.75) {
            potionregistry = PotionEffectType.WEAKNESS;
        }

        return potionregistry;
    }

    private void spawnEvokerFangs(double posX, double posZ, double var4, double var6, float var8, int var9) { // code from mc server (EntityEvoker)
        BlockPosition var10 = new BlockPosition(posX, var6, posZ);
        boolean var11 = false;
        double var12 = 0.0D;

        do {
            BlockPosition var14 = var10.down();
            IBlockData var15 = this.world.getType(var14);
            if (var15.d(this.world, var14, EnumDirection.UP)) {
                if (!this.world.isEmpty(var10)) {
                    IBlockData var16 = this.world.getType(var10);
                    VoxelShape var17 = var16.getCollisionShape(this.world, var10);
                    if (!var17.isEmpty()) {
                        var12 = var17.c(EnumDirection.EnumAxis.Y);
                    }
                }

                var11 = true;
                break;
            }

            var10 = var10.down();
        } while(var10.getY() >= MathHelper.floor(var4) - 1);

        if (var11) {
            this.world.addEntity(new EntityEvokerFangs(this.world, posX, (double)var10.getY() + var12, posZ, var8, var9, this));
        }
    }

    @Override
    public void die() {
        super.die();
        this.player.die();
        this.player.sendDeathToPlayers();
        this.bossBar.removeAll();
        this.dropDeathLoot();
        EntityRegistry.entities.remove(this.player);
    }
}
