package com.gildedgames.aether.entity.monster.dungeon.boss;

import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.client.gui.screen.ValkyrieQueenDialogueScreen;
import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.BossMob;
import com.gildedgames.aether.entity.NpcDialogue;
import com.gildedgames.aether.entity.ai.goal.NpcDialogueGoal;
import com.gildedgames.aether.entity.monster.dungeon.AbstractValkyrie;
import com.gildedgames.aether.entity.projectile.crystal.ThunderCrystal;
import com.gildedgames.aether.network.packet.server.NpcPlayerInteractPacket;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.BossInfoPacket;
import com.gildedgames.aether.network.packet.client.OpenNpcDialoguePacket;
import com.gildedgames.aether.api.BossNameGenerator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class holds the implementation of valkyrie queens. They are the boss version of valkyries, and they fight
 * in the same way, with the additional ability to shoot thunder crystal projectiles at their enemies.
 */
public class ValkyrieQueen extends AbstractValkyrie implements BossMob<ValkyrieQueen>, NpcDialogue {
    public static final EntityDataAccessor<Boolean> DATA_IS_READY = SynchedEntityData.defineId(ValkyrieQueen.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME = SynchedEntityData.defineId(ValkyrieQueen.class, EntityDataSerializers.COMPONENT);
    /** The player whom the valkyrie queen is currently conversing with */
    @Nullable
    private Player tradingPlayer;
    /** Boss health bar manager */
    private final ServerBossEvent bossFight;

    public ValkyrieQueen(EntityType<? extends ValkyrieQueen> type, Level level) {
        super(type, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setVisible(false);
        this.xpReward = XP_REWARD_BOSS;
    }

    /**
     * Generates a name for the boss.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setBossName(BossNameGenerator.generateValkyrieName());
        return data;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new NpcDialogueGoal<>(this));
        this.goalSelector.addGoal(2, new ThunderCrystalAttackGoal(this, 450, 28.0F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, livingEntity -> this.isBossFight()));
    }

    @Nonnull
    public static AttributeSupplier.Builder createQueenAttributes() {
        return AbstractValkyrie.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 28.0)
                .add(Attributes.ATTACK_DAMAGE, 13.5)
                .add(Attributes.MAX_HEALTH, 500.0);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_READY, false);
        this.entityData.define(DATA_BOSS_NAME, Component.literal("Valkyrie Queen"));
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
    }

    /**
     * Allows the players to start a conversation with the valkyrie queen.
     */
    @Override
    @Nonnull
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        if (!this.isReady()) {
            this.lookAt(player, 180F, 180F);
            if (player instanceof ServerPlayer serverPlayer) {
                if (!this.isTrading()) {
                    AetherPacketHandler.sendToPlayer(new OpenNpcDialoguePacket(this.getId()), serverPlayer);
                    this.setTradingPlayer(serverPlayer);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    /**
     * The valkyrie queen is invulnerable until 10 victory medals are presented.
     */
    @Override
    public boolean hurt(@Nonnull DamageSource source, float pDamageAmount) {
        boolean flag = this.isReady() && super.hurt(source, pDamageAmount);
        if (!this.level.isClientSide && source.getEntity() instanceof Player player) {
            if (!this.isBossFight() && flag && level.getDifficulty() != Difficulty.PEACEFUL && this.getHealth() > 0) {
                this.setBossFight(true);
                chatItUp(player, Component.translatable("gui.aether.queen.dialog.fight"));
            }
        }
        return flag;
    }

    /**
     * If the valkyrie kills the player, they will speak.
     */
    @Override
    public boolean doHurtTarget(@Nonnull Entity pEntity) {
        boolean result = super.doHurtTarget(pEntity);
        if (pEntity instanceof ServerPlayer player && player.getHealth() <= 0) {
            this.chatItUp(player, Component.translatable("gui.aether.queen.dialog.playerdeath"));
        }
        return result;
    }


    /**
     * Plays the valkyrie's defeat message.
     */
    @Override
    public void die(@Nonnull DamageSource pCause) {
        if (!this.level.isClientSide) {
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
            this.chatWithNearby(Component.translatable("gui.aether.queen.dialog.defeated"));
            this.spawnExplosionParticles();
        }
        super.die(pCause);
    }

    public void readyUp() {
        MutableComponent message = Component.translatable("gui.aether.queen.dialog.ready");
        this.chatWithNearby(message);
        this.setReady(true);
    }

    /**
     * Sends a message to the player who interacted with the valkyrie.
     */
    @Override
    protected void chatItUp(Player player, Component message) {
        player.sendSystemMessage(Component.literal("[").append(this.getBossName().copy().withStyle(ChatFormatting.YELLOW)).append("]: ").append(message));
    }

    /**
     * Sends a message to nearby players. Useful for boss fights.
     */
    protected void chatWithNearby(Component message) {
        this.level.getNearbyPlayers(NON_COMBAT, this, this.getBoundingBox().inflate(16, 16, 16)).forEach(player -> this.chatItUp(player, message));
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    @Override
    public void startSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId()), pPlayer);
        this.bossFight.addPlayer(pPlayer); //todo remove
    }

    /**
     * Removes the given player from the list of players tracking this entity.
     */
    @Override
    public void stopSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.stopSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId()), pPlayer);
        this.bossFight.removePlayer(pPlayer);
    }

//    @Override
//    public void onDungeonPlayerAdded(@Nullable Player player) {
//        if (player instanceof ServerPlayer serverPlayer) {
//            this.bossFight.addPlayer(serverPlayer);
//        }
//    }
//
//    @Override
//    public void onDungeonPlayerRemoved(@Nullable Player player) {
//        if (player instanceof ServerPlayer serverPlayer) {
//            this.bossFight.removePlayer(serverPlayer);
//        }
//    }

    @Override
    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        this.setBossName(pName);
    }

    public boolean isReady() {
        return this.entityData.get(DATA_IS_READY);
    }

    public void setReady(boolean ready) {
        this.entityData.set(DATA_IS_READY, ready);
    }

    @Override
    public Component getBossName() {
        return this.entityData.get(DATA_BOSS_NAME);
    }

    @Override
    public void setBossName(Component component) {
        this.entityData.set(DATA_BOSS_NAME, component);
        this.bossFight.setName(component);
    }

    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }

    @Override
    public DungeonTracker<ValkyrieQueen> getDungeon() { // TODO
        return null;
    }

    @Override
    public void setDungeon(DungeonTracker<ValkyrieQueen> dungeon) {

    }

    @Override
    public int getDeathScore() {
        return this.deathScore;
    }

    @Override
    public void reset() {

    }

    /**
     * Called on every block in the dungeon when the boss is defeated.
     */
    @Override
    @Nullable
    public BlockState convertBlock(BlockState state) {
        if (state.is(AetherBlocks.LOCKED_ANGELIC_STONE.get())) {
            return AetherBlocks.ANGELIC_STONE.get().defaultBlockState();
        }
        if (state.is(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get())) {
            return AetherBlocks.LIGHT_ANGELIC_STONE.get().defaultBlockState();
        }
        if (state.is(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE.get()) || state.is(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE.get())) {
            return Blocks.AIR.defaultBlockState();
        }
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return AetherSoundEvents.ENTITY_VALKYRIE_QUEEN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_VALKYRIE_QUEEN_DEATH.get();
    }

    /**
     * Opens an NPC dialogue window for this entity. Only call this on the client.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen() {
        Minecraft.getInstance().setScreen(new ValkyrieQueenDialogueScreen(this));
    }

    /**
     * Handles an NPC interaction on the server.
     * @see NpcPlayerInteractPacket
     * @param interactionID - A code for which interaction was performed on the client.
     *                      0 - What can you tell me about this place?
     *                      1 - Challenged to a fight.
     *                      2 - Actually, I changed my mind (fight)
     *                      3 - Nevermind
     */
    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID) {
            case 0: // Responds to the player's question of where they are.
                this.chatItUp(player, Component.translatable("gui.aether.queen.dialog.answer"));
                break;
            case 1: // Tells the players nearby to ready up for a fight.
                if (level.getDifficulty() == Difficulty.PEACEFUL) {
                    this.chatItUp(player, Component.translatable("gui.aether.queen.dialog.peaceful"));
                } else {
                    if (player.getInventory().countItem(AetherItems.VICTORY_MEDAL.get()) >= 10) {
                        this.readyUp();
                        int count = 10;
                        for (ItemStack item : player.inventoryMenu.getItems()) {
                            if (item.is(AetherItems.VICTORY_MEDAL.get())) {
                                if (item.getCount() > count) {
                                    item.shrink(count);
                                    break;
                                } else {
                                    count -= item.getCount();
                                    item.setCount(0);
                                }
                            }
                            if (count <= 0) break;
                        }
                    } else {
                        this.chatItUp(player, Component.translatable("gui.aether.queen.dialog.no_medals"));
                    }
                }
                break;
            case 2:
                this.chatItUp(player, Component.translatable("gui.aether.queen.dialog.deny_fight"));
                break;
            case 3:
            default: //Goodbye.
                this.chatItUp(player, Component.translatable("gui.aether.queen.dialog.goodbye"));
                break;
        }
        this.setTradingPlayer(null);
    }

    @Override
    @Nullable
    public Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("BossName", Component.Serializer.toJson(this.getBossName()));
        tag.putBoolean("BossFight", this.isBossFight());
        tag.putBoolean("Ready", this.isReady());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("BossName")) {
            Component name = Component.Serializer.fromJson(tag.getString("BossName"));
            if (name != null) {
                this.setBossName(name);
            }
        }
        if (tag.contains("BossFight")) {
            this.setBossFight(tag.getBoolean("BossFight"));
        }
        if (tag.contains("Ready")) {
            this.setReady(tag.getBoolean("Ready"));
        }
    }

    /**
     * Shoots thunder crystals without cancelling the movement of the mob.
     */
    public static class ThunderCrystalAttackGoal extends Goal {
        private final Mob mob;
        @Nullable
        private LivingEntity target;
        private final int attackInterval;
        private int attackTime = 0;
        private final float attackRadius;
        public ThunderCrystalAttackGoal(Mob mob, int attackInterval, float attackRadius) {
            this.mob = mob;
            this.attackInterval = attackInterval;
            this.attackRadius = attackRadius;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target != null && target.isAlive()) {
                this.target = target;
                return this.mob.level.getDifficulty() != Difficulty.PEACEFUL;
            } else {
                return false;
            }
        }

        @Override
        public void tick() {
            double distance = this.mob.distanceTo(this.target);
            if (distance < this.attackRadius) {
                if (++this.attackTime >= this.attackInterval) {
                    ThunderCrystal thunderCrystal = new ThunderCrystal(AetherEntityTypes.THUNDER_CRYSTAL.get(), this.mob.level, this.mob, this.target);
                    this.mob.level.addFreshEntity(thunderCrystal);
                    this.attackTime = this.mob.random.nextInt(40);
                }
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }
}
