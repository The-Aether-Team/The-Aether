package com.aetherteam.aether.entity.monster.dungeon.boss;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.gui.screen.ValkyrieQueenDialogueScreen;
import com.aetherteam.aether.data.resources.registries.AetherStructures;
import com.aetherteam.aether.entity.AetherBossMob;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.NpcDialogue;
import com.aetherteam.aether.entity.ai.AetherBlockPathTypes;
import com.aetherteam.aether.entity.ai.goal.NpcDialogueGoal;
import com.aetherteam.aether.entity.monster.dungeon.AbstractValkyrie;
import com.aetherteam.aether.entity.projectile.crystal.ThunderCrystal;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.BossInfoPacket;
import com.aetherteam.aether.network.packet.serverbound.NpcPlayerInteractPacket;
import com.aetherteam.nitrogen.entity.BossRoomTracker;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
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
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class ValkyrieQueen extends AbstractValkyrie implements AetherBossMob<ValkyrieQueen>, NpcDialogue, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Boolean> DATA_IS_READY = SynchedEntityData.defineId(ValkyrieQueen.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Component> DATA_BOSS_NAME = SynchedEntityData.defineId(ValkyrieQueen.class, EntityDataSerializers.COMPONENT);

    /**
     * Boss health bar manager
     */
    private final ServerBossEvent bossFight;
    @Nullable
    private BossRoomTracker<ValkyrieQueen> dungeon;
    @Nullable
    private AABB dungeonBounds;
    /**
     * The player whom the valkyrie queen is currently conversing with.
     */
    @Nullable
    private Player conversingPlayer;

    public ValkyrieQueen(EntityType<? extends ValkyrieQueen> type, Level level) {
        super(type, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.setBossFight(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setPathfindingMalus(AetherBlockPathTypes.BOSS_DOORWAY, -1.0F); // Prevents the Queen from leaving the boss room.
        this.setPersistenceRequired();
    }

    /**
     * Generates a name for the boss. Also save the dungeon bounds when in a naturally generating dungeon
     * so the queen can transform the locked blocks after the fight.<br><br>
     * Warning for "deprecation" is suppressed because this is fine to override.
     * @param level The {@link ServerLevelAccessor} where the entity is spawned.
     * @param difficulty The {@link DifficultyInstance} of the game.
     * @param reason The {@link MobSpawnType} reason.
     * @param spawnData The {@link SpawnGroupData}.
     * @param tag The {@link CompoundTag} to apply to this entity.
     * @return The {@link SpawnGroupData} to return.
     */
    @Override
    @SuppressWarnings("deprecation")
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag tag) {
        this.setBossName(BossNameGenerator.generateValkyrieName(this.getRandom()));
        // Set the bounds for the whole dungeon.
        if (tag != null && tag.contains("Dungeon")) {
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
        return spawnData;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new NpcDialogueGoal<>(this));
        this.goalSelector.addGoal(1, new GetUnstuckGoal(this));
        this.goalSelector.addGoal(2, new ThunderCrystalAttackGoal(this, 450, 28.0F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, livingEntity -> this.isBossFight()));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return AbstractValkyrie.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 28.0)
                .add(Attributes.ATTACK_DAMAGE, 13.5)
                .add(Attributes.MAX_HEALTH, 500.0);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_READY, false);
        this.getEntityData().define(DATA_BOSS_NAME, Component.literal("Valkyrie Queen"));
    }

    /**
     * Handles breaking blocks and evaporating liquids.
     */
    @Override
    public void tick() {
        super.tick();
        this.breakBlocks();
        this.evaporate();
    }

    /**
     * Breaks blocks that are in the way of the Valkyrie Queen between her and the target.
     */
    private void breakBlocks() {
        LivingEntity target = this.getTarget();
        if (!this.getLevel().isClientSide()) {
            if (target != null) {
                if (ForgeEventFactory.getMobGriefingEvent(this.getLevel(), this)) {
                    for (int i = 0; i < 2; i++) {
                        Vec3i vector = i == 0 ? this.getMotionDirection().getNormal() : Vec3i.ZERO;
                        BlockPos upperPosition = BlockPos.containing(this.getEyePosition()).offset(vector);
                        BlockPos lowerPosition = this.blockPosition().offset(vector);
                        BlockState upperState = this.getLevel().getBlockState(upperPosition);
                        BlockState lowerState = this.getLevel().getBlockState(lowerPosition);
                        if (!upperState.isAir() && !upperState.is(AetherTags.Blocks.VALKYRIE_QUEEN_UNBREAKABLE) // Check upper block at player height.
                                && (upperState.getShape(this.getLevel(), upperPosition).equals(Shapes.block()) || !upperState.getCollisionShape(this.getLevel(), upperPosition).isEmpty())
                                && (this.getDungeon() == null || this.getDungeon().roomBounds().contains(upperPosition.getCenter()))) {
                            this.getLevel().destroyBlock(upperPosition, true, this);
                            this.swing(InteractionHand.MAIN_HAND);
                        } else if (!lowerState.isAir() && !lowerState.is(AetherTags.Blocks.VALKYRIE_QUEEN_UNBREAKABLE) // Check lower block at player height.
                                && (lowerState.getShape(this.getLevel(), lowerPosition).equals(Shapes.block()) || !lowerState.getCollisionShape(this.getLevel(), lowerPosition).isEmpty())
                                && (this.getDungeon() == null || this.getDungeon().roomBounds().contains(lowerPosition.getCenter()))) {
                            this.getLevel().destroyBlock(lowerPosition, true, this);
                            this.swing(InteractionHand.MAIN_HAND);
                        }
                    }
                }
            }
        }
    }

    /**
     * Evaporates liquid blocks.
     * @see AetherBossMob#evaporate(Mob, BlockPos, BlockPos, Predicate)
     */
    private void evaporate() {
        Pair<BlockPos, BlockPos> minMax = this.getDefaultBounds(this);
        AetherBossMob.super.evaporate(this, minMax.getLeft(), minMax.getRight(), (blockState) -> !blockState.is(AetherTags.Blocks.VALKYRIE_QUEEN_UNBREAKABLE));
    }

    /**
     * Handles boss fight and health tracking, and dungeon tracking.
     */
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.trackDungeon();
    }

    /**
     * Teleports near a target but outside a specified radius. If it's outside the destination is outside boss room, clamp to inside the room.
     * @return Whether the teleportation was successful, as a {@link Boolean}.
     */
    @Override
    protected boolean teleportAroundTarget(Entity target) {
        Vec2 targetVec = new Vec2((this.getRandom().nextFloat() - 0.5F), (this.getRandom().nextFloat() - 0.5F)).normalized();
        double x = target.getX() + targetVec.x * 7;
        double y = target.getY();
        double z = target.getZ() + targetVec.y * 7;
        if (this.getDungeon() != null) {
            AABB room = this.getDungeon().roomBounds();
            x = Mth.clamp(x, room.minX + 1, room.maxX - 1);
            y = Mth.clamp(y, room.minY + 1, room.maxY - 1);
            z = Mth.clamp(z, room.minZ + 1, room.maxZ - 1);
        }
        return this.teleport(x, y, z);
    }

    /**
     * Forces teleportation to an unreachable player.
     * @param target The target {@link Entity}.
     */
    protected void teleportUnstuck(Entity target) {
        this.teleport(target.getX(), target.getY(), target.getZ());
    }

    /**
     * Allows the players to start a conversation with the Valkyrie Queen.
     * The dialogue screen is opened through {@link ValkyrieQueen#handleEntityEvent(byte)}.
     * @param player The interacting {@link Player}.
     * @param hand The {@link InteractionHand}.
     * @return The {@link InteractionResult}.
     */
    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            if (!this.isBossFight() && !this.getLevel().isClientSide()) {
                if (!this.isReady()) {
                    this.lookAt(player, 180.0F, 180.0F);
                    if (player instanceof ServerPlayer serverPlayer) {
                        if (this.getConversingPlayer() == null) {
                            this.getLevel().broadcastEntityEvent(this, (byte) 71);
                            this.setConversingPlayer(serverPlayer);
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
     * Opens an NPC dialogue window for this entity.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void openDialogueScreen() {
        Minecraft.getInstance().setScreen(new ValkyrieQueenDialogueScreen(this));
    }

    /**
     * Handles an NPC dialogue interaction on the server.
     * @param player The {@link Player}.
     * @param interactionID A code for which interaction was performed on the client.<br>
     *                      0 - "What can you tell me about this place?"<br>
     *                      1 - "I wish to fight you!"<br>
     *                      2 - "On second thought, I'd rather not."<br>
     *                      3 - "Nevermind."<br>
     * @see NpcPlayerInteractPacket
     */
    @Override
    public void handleNpcInteraction(Player player, byte interactionID) {
        switch (interactionID) {
            case 0: // Responds to the player's question of where they are.
                this.chat(player, Component.translatable("gui.aether.queen.dialog.answer"));
                break;
            case 1: // Tells the players nearby to ready up for a fight.
                if (this.getLevel().getDifficulty() == Difficulty.PEACEFUL) { // Check for peaceful mode.
                    this.chat(player, Component.translatable("gui.aether.queen.dialog.peaceful"));
                } else {
                    if (player.getInventory().countItem(AetherItems.VICTORY_MEDAL.get()) >= 10) { // Checks for Victory Medals.
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
                        this.chat(player, Component.translatable("gui.aether.queen.dialog.no_medals"));
                    }
                }
                break;
            case 2: // Deny fight.
                this.chat(player, Component.translatable("gui.aether.queen.dialog.deny_fight"));
                break;
            case 3:
            default: // Goodbye.
                this.chat(player, Component.translatable("gui.aether.queen.dialog.goodbye"));
                break;
        }
        this.setConversingPlayer(null);
    }

    /**
     * Sets the Valkyrie Queen as ready to be attacked.
     */
    public void readyUp() {
        MutableComponent message = Component.translatable("gui.aether.queen.dialog.begin");
        this.chatWithNearby(message);
        this.setReady(true);
    }

    /**
     * Sends a message to nearby players. Useful for boss fights.
     * @param message The message {@link Component}.
     */
    protected void chatWithNearby(Component message) {
        AABB room = this.dungeon == null ? this.getBoundingBox().inflate(16) : this.dungeon.roomBounds();
        this.level.getNearbyPlayers(NON_COMBAT, this, room).forEach(player -> this.chat(player, message));
    }

    /**
     * Sends a message to the player who interacted with the Valkyrie Queen.
     * @param player The interacting {@link Player}.
     * @param message The message {@link Component}.
     */
    @Override
    protected void chat(Player player, Component message) {
        player.sendSystemMessage(Component.literal("[").append(this.getBossName().copy().withStyle(ChatFormatting.YELLOW)).append("]: ").append(message));
    }

    /**
     * Handles damaging the Valkyrie Queen, which is only allowed after 10 Victory Medals have been presented.
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.hurt(source, amount);
        }
        if (this.isReady()) {
            if (source.getDirectEntity() instanceof LivingEntity attacker && this.getLevel().getDifficulty() != Difficulty.PEACEFUL) {
                if (this.getDungeon() == null || this.getDungeon().isPlayerWithinRoomInterior(attacker)) {
                    if (super.hurt(source, amount) && this.getHealth() > 0) {
                        if (!this.getLevel().isClientSide() && !this.isBossFight()) {
                            this.chatWithNearby(Component.translatable("gui.aether.queen.dialog.fight"));
                            this.setBossFight(true);
                            if (this.getDungeon() != null) {
                                this.closeRoom();
                            }
                        }
                        return true;
                    }
                } else {
                    if (!this.getLevel().isClientSide() && attacker instanceof Player player) {
                        this.displayTooFarMessage(player);
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Plays the message for the Valkyrie Queen defeating the player.
     * @param entity The hurt {@link Entity}.
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean result = super.doHurtTarget(entity);
        if (entity instanceof ServerPlayer player && player.getHealth() <= 0) {
            this.chat(player, Component.translatable("gui.aether.queen.dialog.playerdeath"));
        }
        return result;
    }

    /**
     * Resets the boss fight.
     */
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
     * Plays the Valkyrie Queen's defeat message, ends the boss fight, opens the room, and grants advancements when the boss dies.
     * @param source The {@link DamageSource}.
     */
    @Override
    public void die(DamageSource source) {
        if (!this.level.isClientSide) {
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth()); // Forces an update to the boss health meter.
            this.chatWithNearby(Component.translatable("gui.aether.queen.dialog.defeated"));
            this.spawnExplosionParticles();
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(source);
                this.tearDownRoom();
            }
        }
        super.die(source);
    }

    /**
     * Unlocks blocks across the whole Silver Dungeon, not just the boss room.
     */
    @Override
    public void tearDownRoom() {
        for (BlockPos pos : BlockPos.betweenClosed((int) this.dungeonBounds.minX, (int) this.dungeonBounds.minY, (int) this.dungeonBounds.minZ, (int) this.dungeonBounds.maxX, (int) this.dungeonBounds.maxY, (int) this.dungeonBounds.maxZ)) {
            BlockState state = this.getLevel().getBlockState(pos);
            BlockState newState = this.convertBlock(state);
            if (newState != null) {
                this.getLevel().setBlock(pos, newState, 1 | 2);
            }
        }
    }

    /**
     * Called on every block in the boss room when the boss is defeated.
     * @param state The {@link BlockState} to try to convert.
     * @return The converted {@link BlockState}.
     */
    @Nullable
    @Override
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

    /**
     * Tracks the player as a part of the boss fight when the player is nearby, displaying the boss bar for them.
     * @param player The {@link ServerPlayer}.
     */
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, new BossInfoPacket.Display(this.bossFight.getId()), player);
        if (this.getDungeon() == null || this.getDungeon().isPlayerTracked(player)) {
            this.bossFight.addPlayer(player);
        }
    }

    /**
     * Tracks the player as no longer in the boss fight when the player is nearby, removing the boss bar for them.
     * @param player The {@link ServerPlayer}.
     */
    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, new BossInfoPacket.Remove(this.bossFight.getId()), player);
        this.bossFight.removePlayer(player);
    }

    /**
     * Adds a player to the boss fight when they've entered the dungeon.
     * @param player The {@link Player}.
     */
    @Override
    public void onDungeonPlayerAdded(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.addPlayer(serverPlayer);
        }
    }

    /**
     * Removes a player from the boss fight when they've left the dungeon.
     * @param player The {@link Player}.
     */
    @Override
    public void onDungeonPlayerRemoved(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.removePlayer(serverPlayer);
        }
    }

    /**
     * @return Whether the Valkyrie Queen is ready to fight, as a {@link Boolean}.
     */
    public boolean isReady() {
        return this.getEntityData().get(DATA_IS_READY);
    }

    /**
     * Sets whether the Valkyrie Queen is ready to fight.
     * @param ready The {@link Boolean} value.
     */
    public void setReady(boolean ready) {
        this.getEntityData().set(DATA_IS_READY, ready);
    }

    /**
     * @return The {@link Component} for the boss name.
     */
    @Override
    public Component getBossName() {
        return this.getEntityData().get(DATA_BOSS_NAME);
    }

    /**
     * Sets the {@link Component} for the boss name and in the boss fight.
     * @param component The name {@link Component}.
     */
    @Override
    public void setBossName(Component component) {
        this.getEntityData().set(DATA_BOSS_NAME, component);
        this.bossFight.setName(component);
    }

    /**
     * @return The {@link ValkyrieQueen} {@link BossRoomTracker} for the Silver Dungeon.
     */
    @Nullable
    @Override
    public BossRoomTracker<ValkyrieQueen> getDungeon() {
        return this.dungeon;
    }

    /**
     * Sets the tracker for the Bronze Dungeon.
     * @param dungeon The {@link ValkyrieQueen} {@link BossRoomTracker}.
     */
    @Override
    public void setDungeon(@Nullable BossRoomTracker<ValkyrieQueen> dungeon) {
        this.dungeon = dungeon;
        if (this.dungeonBounds == null) {
            this.dungeonBounds = dungeon.roomBounds();
        }
    }

    /**
     * @return Whether the boss fight is active and the boss bar is visible, as a {@link Boolean}.
     */
    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    /**
     * Sets whether the boss fight is active and the boss bar is visible.
     * @param isFighting The {@link Boolean} value.
     */
    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }

    /**
     * Sets the bounds of the entire Silver Dungeon.
     * @param dungeonBounds The {@link AABB} bounds.
     */
    public void setDungeonBounds(@Nullable AABB dungeonBounds) {
        this.dungeonBounds = dungeonBounds;
    }

    /**
     * @return The {@link Player} that is conversing with the Valkyrie Queen.
     */
    @Nullable
    @Override
    public Player getConversingPlayer() {
        return this.conversingPlayer;
    }

    /**
     * Sets the player that is conversing with the Valkyrie Queen.
     * @param player The {@link Player}.
     */
    @Override
    public void setConversingPlayer(@Nullable Player player) {
        this.conversingPlayer = player;
    }

    /**
     * @return The death score {@link Integer} for the awarded kill score from this entity.
     */
    @Override
    public int getDeathScore() {
        return this.deathScore;
    }

    @Override
    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        this.setBossName(pName);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AetherSoundEvents.ENTITY_VALKYRIE_QUEEN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_VALKYRIE_QUEEN_DEATH.get();
    }

    /**
     * @return A {@link Boolean} for whether the Valkyrie Queen is affected by fluids.
     * She only is affected when jumping, to get around a bug where outright marking this as false
     * would prevent her from jumping when in water.
     */
    @Override
    protected boolean isAffectedByFluids() {
        return this.jumping;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 71) {
            this.openDialogueScreen();
        } else {
            super.handleEntityEvent(id);
        }
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#addBossSaveData(CompoundTag)
     */
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
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

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#readBossSaveData(CompoundTag)
     */
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
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

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#addBossSaveData(CompoundTag)
     */
    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        this.addBossSaveData(tag);
        buffer.writeNbt(tag);
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#readBossSaveData(CompoundTag)
     */
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
     * Shoots Thunder Crystals without cancelling the movement of the Valkyrie Queen.
     */
    public static class ThunderCrystalAttackGoal extends Goal {
        private final Mob mob;
        private final int attackInterval;
        private final float attackRadius;
        private int attackTime = 0;

        public ThunderCrystalAttackGoal(Mob mob, int attackInterval, float attackRadius) {
            this.mob = mob;
            this.attackInterval = attackInterval;
            this.attackRadius = attackRadius;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target != null && target.isAlive()) {
                return this.mob.getLevel().getDifficulty() != Difficulty.PEACEFUL;
            } else {
                return false;
            }
        }

        @Override
        public void tick() {
            if (this.mob.getTarget() != null) {
                double distance = this.mob.distanceTo(this.mob.getTarget());
                if (distance < this.attackRadius) {
                    if (++this.attackTime >= this.attackInterval) {
                        ThunderCrystal thunderCrystal = new ThunderCrystal(AetherEntityTypes.THUNDER_CRYSTAL.get(), this.mob.getLevel(), this.mob, this.mob.getTarget());
                        this.mob.getLevel().addFreshEntity(thunderCrystal);
                        this.attackTime = this.mob.getRandom().nextInt(40);
                    }
                }
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }
    }

    /**
     * Teleports the Valkyrie Queen to the target if they can't be reached.
     */
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
            if (this.valkyrie.getTarget() != null) {
                this.valkyrie.teleportUnstuck(this.valkyrie.getTarget());
            }
        }
    }
}
