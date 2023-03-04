package com.gildedgames.aether.entity.monster.dungeon.boss;

import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.client.gui.screen.ValkyrieQueenDialogueScreen;
import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.data.resources.registries.AetherStructures;
import com.gildedgames.aether.entity.BossMob;
import com.gildedgames.aether.entity.NpcDialogue;
import com.gildedgames.aether.entity.ai.AetherBlockPathTypes;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class holds the implementation of valkyrie queens. They are the boss version of valkyries, and they fight
 * in the same way, with the additional ability to shoot thunder crystal projectiles at their enemies.
 */
public class ValkyrieQueen extends AbstractValkyrie implements BossMob<ValkyrieQueen>, NpcDialogue, IEntityAdditionalSpawnData {
    public static final EntityDataAccessor<Boolean> DATA_IS_READY = SynchedEntityData.defineId(ValkyrieQueen.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME = SynchedEntityData.defineId(ValkyrieQueen.class, EntityDataSerializers.COMPONENT);

    /**
     * The player whom the valkyrie queen is currently conversing with
     */
    @Nullable
    private Player tradingPlayer;
    private DungeonTracker<ValkyrieQueen> dungeon;
    private AABB dungeonBounds;
    private final ServerBossEvent bossFight;

    public ValkyrieQueen(EntityType<? extends ValkyrieQueen> type, Level level) {
        super(type, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setVisible(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setPathfindingMalus(AetherBlockPathTypes.BOSS_DOORWAY, -1.0F);
    }

    /**
     * Generates a name for the boss. In a naturally generating dungeon, save the dungeon bounds so the queen can
     * transform the locked blocks after the fight.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor level, @Nonnull DifficultyInstance difficulty, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnGroupData, compoundTag);
        this.setBossName(BossNameGenerator.generateValkyrieName());
        if (compoundTag != null && compoundTag.contains("Dungeon")) {
            // Set the bounds for the whole dungeon
            StructureManager manager = level.getLevel().structureManager();
            manager.registryAccess().registry(Registries.STRUCTURE).ifPresent(registry -> {
                        Structure temple = registry.get(AetherStructures.SILVER_DUNGEON);
                        if (temple != null) {
                            StructureStart start = manager.getStructureAt(this.blockPosition(), temple);
                            if (start != StructureStart.INVALID_START) {
                                BoundingBox box = start.getBoundingBox();
                                AABB dungeonBounds = new AABB(box.minX(), box.minY(), box.minZ(), box.maxX() + 1, box.maxY() + 1, box.maxZ() + 1);
                                this.setDungeonBounds(dungeonBounds);
                            }
                        }
                    }
            );
        }
        return data;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new NpcDialogueGoal<>(this));
        this.goalSelector.addGoal(1, new GetUnstuckGoal(this));
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
        this.trackDungeon();
    }

    /**
     * Allows the players to start a conversation with the valkyrie queen.
     */
    @Override
    @Nonnull
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (!this.isBossFight() && !this.level.isClientSide) {
                if (!this.isReady()) {
                    this.lookAt(player, 180F, 180F);
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (!this.isTrading()) {
                            AetherPacketHandler.sendToPlayer(new OpenNpcDialoguePacket(this.getId()), serverPlayer);
                            this.setTradingPlayer(serverPlayer);
                        }
                    }
                } else {
                    this.chatWithNearby(Component.translatable("gui.aether.queen.dialog.ready"));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * The valkyrie queen is invulnerable until 10 victory medals are presented.
     */
    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (source == DamageSource.OUT_OF_WORLD) {
            return super.hurt(source, amount);
        }

        if (this.isReady()) {
            if (source.getDirectEntity() instanceof LivingEntity attacker && this.level.getDifficulty() != Difficulty.PEACEFUL) {
                if (this.getDungeon() == null || this.getDungeon().isPlayerWithinRoomInterior(attacker)) {
                    if (super.hurt(source, amount) && this.getHealth() > 0) {
                        if (!this.level.isClientSide() && !this.isBossFight()) {
                            this.chatWithNearby(Component.translatable("gui.aether.queen.dialog.fight"));
                            this.setBossFight(true);
                            if (this.getDungeon() != null) {
                                this.closeRoom();
                            }
                        }
                        return true;
                    }
                } else {
                    if (!this.level.isClientSide() && attacker instanceof Player player) {
                        this.displayTooFarMessage(player);
                        return false;
                    }
                }
            }
        }
        return false;
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
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(pCause);
                this.tearDownRoom();
            }
        }
        super.die(pCause);
    }

    /**
     * The valkyrie queen needs to open the whole dungeon. Not just the boss room.
     */
    @Override
    public void tearDownRoom() {
        for (BlockPos pos : BlockPos.betweenClosed((int) this.dungeonBounds.minX, (int) this.dungeonBounds.minY, (int) this.dungeonBounds.minZ, (int) this.dungeonBounds.maxX, (int) this.dungeonBounds.maxY, (int) this.dungeonBounds.maxZ)) {
            BlockState state = this.level.getBlockState(pos);
            BlockState newState = this.convertBlock(state);
            if (newState != null) {
                this.level.setBlock(pos, newState, 1 | 2);
            }
        }
    }

    /**
     * Teleports near a target outside of a specified radius. Returns false if it fails.
     * If it's outside the room, clamp to inside the room.
     */
    @Override
    protected boolean teleportAroundTarget(Entity target) {
        Vec2 targetVec = new Vec2((this.random.nextFloat() - 0.5F), (this.random.nextFloat() - 0.5F)).normalized();
        double x = target.getX() + targetVec.x * 7;
        double y = target.getY();
        double z = target.getZ() + targetVec.y * 7;
        if (this.dungeon != null) {
            AABB room = this.dungeon.roomBounds();
            x = Mth.clamp(x, room.minX + 1, room.maxX - 1);
            y = Mth.clamp(y, room.minY + 1, room.maxY - 1);
            z = Mth.clamp(z, room.minZ + 1, room.maxZ - 1);
        }
        return this.teleport(x, y, z);
    }

    /**
     * Teleports to an unreachable player
     */
    protected boolean teleportUnstuck(Entity target) {
        return this.teleport(target.getX(), target.getY(), target.getZ());
    }

    public void readyUp() {
        MutableComponent message = Component.translatable("gui.aether.queen.dialog.begin");
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
        AABB room = this.dungeon == null ? this.getBoundingBox().inflate(16) : this.dungeon.roomBounds();
        this.level.getNearbyPlayers(NON_COMBAT, this, room).forEach(player -> this.chatItUp(player, message));
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    @Override
    public void startSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId()), pPlayer);
        if (this.getDungeon() == null || this.getDungeon().isPlayerTracked(pPlayer)) {
            this.bossFight.addPlayer(pPlayer);
        }
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

    @Override
    public void onDungeonPlayerAdded(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.addPlayer(serverPlayer);
        }
    }

    @Override
    public void onDungeonPlayerRemoved(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.removePlayer(serverPlayer);
        }
    }

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
    public DungeonTracker<ValkyrieQueen> getDungeon() {
        return this.dungeon;
    }

    @Override
    public void setDungeon(DungeonTracker<ValkyrieQueen> dungeon) {
        this.dungeon = dungeon;
        if (this.dungeonBounds == null) {
            this.dungeonBounds = dungeon.roomBounds();
        }
    }

    public void setDungeonBounds(AABB dungeonBounds) {
        this.dungeonBounds = dungeonBounds;
    }

    @Override
    public int getDeathScore() {
        return this.deathScore;
    }

    @Override
    public void reset() {
        this.setBossFight(false);
        this.setTarget(null);
        this.setHealth(this.getMaxHealth());
        if (this.getDungeon() != null) {
            this.openRoom();
        }
    }

    /**
     * Called on every block in the dungeon when the boss is defeated.
     */
    @Override
    @Nullable
    public BlockState convertBlock(BlockState state) {
        if (state.is(AetherBlocks.LOCKED_ANGELIC_STONE.get()) || state.is(AetherBlocks.TRAPPED_ANGELIC_STONE.get())) {
            return AetherBlocks.ANGELIC_STONE.get().defaultBlockState();
        }
        if (state.is(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get()) || state.is(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get())) {
            return AetherBlocks.LIGHT_ANGELIC_STONE.get().defaultBlockState();
        }
        if (state.is(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE.get())) {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.is(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE.get())) {
            return AetherBlocks.SKYROOT_TRAPDOOR.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING));
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
     *
     * @param interactionID - A code for which interaction was performed on the client.
     *                      0 - What can you tell me about this place?
     *                      1 - Challenged to a fight.
     *                      2 - Actually, I changed my mind (fight)
     *                      3 - Nevermind
     * @see NpcPlayerInteractPacket
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
        this.addBossSaveData(tag);
        if (this.dungeonBounds != null) {
            tag.putDouble("DungeonBoundsMinX", this.dungeonBounds.minX);
            tag.putDouble("DungeonBoundsMinY", this.dungeonBounds.minY);
            tag.putDouble("DungeonBoundsMinZ", this.dungeonBounds.minZ);
            tag.putDouble("DungeonBoundsMaxX", this.dungeonBounds.maxX);
            tag.putDouble("DungeonBoundsMaxY", this.dungeonBounds.maxY);
            tag.putDouble("DungeonBoundsMaxZ", this.dungeonBounds.maxZ);
        }
        tag.putBoolean("Ready", this.isReady());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readBossSaveData(tag);
        if (tag.contains("DungeonBoundsMinX")) {
            double minX = tag.getDouble("DungeonBoundsMinX");
            double minY = tag.getDouble("DungeonBoundsMinY");
            double minZ = tag.getDouble("DungeonBoundsMinZ");
            double maxX = tag.getDouble("DungeonBoundsMaxX");
            double maxY = tag.getDouble("DungeonBoundsMaxY");
            double maxZ = tag.getDouble("DungeonBoundsMaxZ");
            this.dungeonBounds = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
        }
        if (tag.contains("Ready")) {
            this.setReady(tag.getBoolean("Ready"));
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        this.addBossSaveData(tag);
        buffer.writeNbt(tag);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        CompoundTag tag = additionalData.readNbt();
        if (tag != null) {
            this.readBossSaveData(tag);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
                    this.attackTime = this.mob.getRandom().nextInt(40);
                }
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    public static class GetUnstuckGoal extends Goal {
        private final ValkyrieQueen valkyrie;
        protected int stuckTimer;

        public GetUnstuckGoal(ValkyrieQueen valkyrie) {
            this.valkyrie = valkyrie;
        }

        @Override
        public boolean canUse() {
            Entity target = this.valkyrie.getTarget();
            if (target == null) {
                return false;
            } else if (target.getY() > this.valkyrie.getY()) {
                if (this.stuckTimer++ >= 75) {
                    this.stuckTimer = 0;
                    return true;
                }
            } else {
                this.stuckTimer = 0;
            }
            return false;
        }

        @Override
        public void start() {
            this.valkyrie.teleportUnstuck(this.valkyrie.getTarget());
        }
    }
}
