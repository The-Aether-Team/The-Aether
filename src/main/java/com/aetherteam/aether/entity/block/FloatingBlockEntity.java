package com.aetherteam.aether.entity.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.Floatable;
import com.aetherteam.aether.block.miscellaneous.FloatingBlock;
import com.aetherteam.aether.data.resources.registries.AetherDamageTypes;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.mixin.mixins.common.accessor.ConcretePowderBlockAccessor;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.item.FallingBlockEntity}.<br><br>
 * Modified for reversed gravity.
 */
public class FloatingBlockEntity extends Entity {
    private static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.BLOCK_POS);
    private BlockState blockState = Blocks.SAND.defaultBlockState();
    private int time;
    private boolean dropItem = true;
    private boolean cancelDrop;
    private boolean hurtEntities;
    private int fallDamageMax = 40;
    private float fallDamagePerDistance;
    private int floatDistance;
    @Nullable
    private CompoundTag blockData;
    public boolean forceTickAfterTeleportToDuplicate;
    /**
     * Detects if this block was floated naturally or by a player's interaction. Naturally floated blocks are not able to drop as an item.
     */
    private boolean natural = true;

    public FloatingBlockEntity(EntityType<? extends FloatingBlockEntity> type, Level level) {
        super(type, level);
    }

    public FloatingBlockEntity(Level level, double x, double y, double z, BlockState state) {
        this(AetherEntityTypes.FLOATING_BLOCK.get(), level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y + (double) ((1.0F - this.getBbHeight()) / 2.0F), z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_START_POS, BlockPos.ZERO);
    }

    @Override
    public void tick() {
        if (this.getBlockState().isAir()) {
            this.discard();
        } else {
            Block block = this.getBlockState().getBlock();
            if (this.time++ == 0) {
                BlockPos blockPos = this.blockPosition();
                if (this.level().getBlockState(blockPos).is(block)) {
                    this.level().removeBlock(blockPos, false);
                }
            }

            this.applyGravity();
            this.floatDistance = this.blockPosition().getY() - this.getStartPos().getY();
            if (this.level().isClientSide()) {
                this.spawnFloatingBlockParticles();
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            this.handlePortal();
            if (!this.level().isClientSide() && this.level() instanceof ServerLevel serverLevel && (this.isAlive() || this.forceTickAfterTeleportToDuplicate)) {
                BlockPos blockPos1 = this.blockPosition();
                boolean isConcrete = this.getBlockState().getBlock() instanceof ConcretePowderBlock;
                boolean canConvert = isConcrete && this.blockState.canBeHydrated(this.level(), blockPos1, this.level().getFluidState(blockPos1), blockPos1);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (isConcrete && d0 > 1.0) {
                    BlockHitResult blockHitResult = this.level().clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                    if (blockHitResult.getType() != HitResult.Type.MISS && this.blockState.canBeHydrated(this.level(), blockPos1, this.level().getFluidState(blockHitResult.getBlockPos()), blockHitResult.getBlockPos())) {
                        blockPos1 = blockHitResult.getBlockPos();
                        canConvert = true;
                    }
                }

                if ((!this.verticalCollision || this.onGround()) && !canConvert) {
                    if (!this.level().isClientSide() && (this.time > 100 && (blockPos1.getY() <= this.level().getMinBuildHeight() || blockPos1.getY() > this.level().getMaxBuildHeight()) || this.time > 600)) { // Checks max y-level.
                        if ((!this.natural || !this.getBlockState().requiresCorrectToolForDrops()) && this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.dropBlock(this.getBlockState());
                        }
                        this.discard();
                    }
                } else {
                    BlockState blockState = this.level().getBlockState(blockPos1);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
                    if (!blockState.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean canBeReplaced = blockState.canBeReplaced(new DirectionalPlaceContext(this.level(), blockPos1, Direction.UP, ItemStack.EMPTY, Direction.DOWN));
                            boolean isAboveFree = FloatingBlock.isFree(this.level().getBlockState(blockPos1.above())) && (!isConcrete || !canConvert); // Check above position.
                            boolean canBlockSurvive = this.getBlockState().canSurvive(this.level(), blockPos1) && !isAboveFree;
                            if ((canBeReplaced && canBlockSurvive) || (this.natural && blockState.getBlock().defaultDestroyTime() >= 0)) {
                                if (this.getBlockState().hasProperty(BlockStateProperties.WATERLOGGED) && this.level().getFluidState(blockPos1).is(Fluids.WATER)) {
                                    this.blockState = this.getBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
                                }
                                BlockState previousBlockState = this.level().getBlockState(blockPos1);
                                if (this.level().setBlock(blockPos1, this.getBlockState(), 1 | 2)) {
                                    if (this.natural && !previousBlockState.isAir()) {
                                        this.dropBlock(previousBlockState);
                                    }

                                    serverLevel.getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockPos1, this.level().getBlockState(blockPos1)));
                                    this.discard();
                                    if (block instanceof Floatable floatable) {
                                        floatable.onCollide(this.level(), blockPos1, this.getBlockState(), blockState, this);
                                    } else if (block instanceof ConcretePowderBlock concretePowderBlock) {
                                        if (ConcretePowderBlockAccessor.callShouldSolidify(this.level(), blockPos1, blockState)) {
                                            ConcretePowderBlockAccessor concretePowderBlockAccessor = (ConcretePowderBlockAccessor) concretePowderBlock;
                                            this.level().setBlock(blockPos1, concretePowderBlockAccessor.aether$getConcrete().defaultBlockState(), 1 | 2);
                                        }
                                    } else if (block instanceof AnvilBlock) {
                                        if (!this.isSilent()) {
                                            this.level().levelEvent(1029, blockPos1, 0);
                                        }
                                    }

                                    if (this.blockData != null && this.getBlockState().hasBlockEntity()) {
                                        BlockEntity blockEntity = this.level().getBlockEntity(blockPos1);
                                        if (blockEntity != null) {
                                            CompoundTag tag = blockEntity.saveWithoutMetadata(this.level().registryAccess());

                                            for (String s : this.blockData.getAllKeys()) {
                                                tag.put(s, this.blockData.get(s).copy());
                                            }

                                            try {
                                                blockEntity.loadWithComponents(tag, this.level().registryAccess());
                                            } catch (Exception exception) {
                                                Aether.LOGGER.error("Failed to load block entity from floating block", exception);
                                            }
                                            blockEntity.setChanged();
                                        }
                                    }
                                } else if ((!this.natural || !this.getBlockState().requiresCorrectToolForDrops()) && this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.callOnBrokenAfterFall(block, blockPos1);
                                    this.dropBlock(this.getBlockState());
                                }
                            } else {
                                this.discard();
                                if ((!this.natural || !this.getBlockState().requiresCorrectToolForDrops() || blockState.getBlock().defaultDestroyTime() < 0) && this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.callOnBrokenAfterFall(block, blockPos1);
                                    this.dropBlock(this.getBlockState());
                                }
                            }
                        } else {
                            this.discard();
                            this.callOnBrokenAfterFall(block, blockPos1);
                        }
                    }
                }
            }
            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }
    }

    public void setHurtsEntities(float fallDamagePerDistance, int fallDamageMax) {
        this.hurtEntities = true;
        this.fallDamagePerDistance = fallDamagePerDistance;
        this.fallDamageMax = fallDamageMax;
    }

    private void dropBlock(BlockState state) {
        if (this.level() instanceof ServerLevel serverLevel) {
            for (ItemStack stack : Block.getDrops(state, serverLevel, this.blockPosition(), null)) {
                this.spawnAtLocation(stack);
            }
        }
    }

    public void callOnBrokenAfterFall(Block block, BlockPos pos) {
        if (block instanceof Floatable floatable) {
            floatable.onBrokenAfterCollide(this.level(), pos, this);
        }
    }

    @Override
    protected void applyGravity() {
        double d0 = this.getGravity();
        if (d0 != 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, d0, 0.0));
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        if (this.hurtEntities) {
            int i = this.floatDistance;
            if (i >= 0) {
                Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
                DamageSource damageSource = this.blockState.getBlock() instanceof Floatable floatable
                    ? floatable.getFallDamageSource(this)
                    : AetherDamageTypes.entityDamageSource(this.level(), AetherDamageTypes.FLOATING_BLOCK, this);
                float f = (float) Math.min(Mth.floor((float) i * this.fallDamagePerDistance), this.fallDamageMax);
                this.level().getEntities(this, this.getBoundingBox(), predicate).forEach((entity) -> entity.hurt(damageSource, f));
                boolean flag = this.getBlockState().is(BlockTags.ANVIL);
                if (flag && f > 0.0F && this.random.nextFloat() < 0.05F + (float) i * 0.05F) {
                    BlockState blockstate = AnvilBlock.damage(this.getBlockState());
                    if (blockstate == null) {
                        this.disableDrop();
                    } else {
                        this.blockState = blockstate;
                    }
                }
            }
        }
        return false;
    }

    private void spawnFloatingBlockParticles() {
        if (this.random.nextInt(8) == 0) {
            double d0 = (this.getX() - 0.5) + this.random.nextDouble();
            double d1 = this.getY() - 0.05;
            double d2 = (this.getZ() - 0.5) + this.random.nextDouble();
            this.level().addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, this.getBlockState()), d0, d1, d2, 0.0, 0.0, 0.0);
        }
    }

    public void setStartPos(BlockPos pOrigin) {
        this.getEntityData().set(DATA_START_POS, pOrigin);
    }

    public BlockPos getStartPos() {
        return this.getEntityData().get(DATA_START_POS);
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public void setNatural(boolean natural) {
        this.natural = natural;
    }

    public void disableDrop() {
        this.cancelDrop = true;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    protected Component getTypeName() {
        return Component.translatable("entity.aether.floating_block_type", this.blockState.getBlock().getName());
    }

    @Nullable
    @Override
    public Entity changeDimension(DimensionTransition transition) {
        ResourceKey<Level> newDimension = transition.newLevel().dimension();
        ResourceKey<Level> currentDimension = this.level().dimension();
        boolean flag = (currentDimension == Level.END || newDimension == Level.END) && currentDimension != newDimension;
        Entity entity = super.changeDimension(transition);
        this.forceTickAfterTeleportToDuplicate = entity != null && flag;
        return entity;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        tag.putInt("Time", this.time);
        tag.putBoolean("DropItem", this.dropItem);
        tag.putBoolean("HurtEntities", this.hurtEntities);
        tag.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        tag.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            tag.put("TileEntityData", this.blockData);
        }
        tag.putBoolean("Natural", this.natural);
        tag.putBoolean("CancelDrop", this.cancelDrop);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("BlockState")) {
            this.blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("BlockState"));
        }
        if (tag.contains("Time")) {
            this.time = tag.getInt("Time");
        }
        if (tag.contains("HurtEntities", 99)) {
            this.hurtEntities = tag.getBoolean("HurtEntities");
            this.fallDamagePerDistance = tag.getFloat("FallHurtAmount");
            this.fallDamageMax = tag.getInt("FallHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }
        if (tag.contains("DropItem", 99)) {
            this.dropItem = tag.getBoolean("DropItem");
        }
        if (tag.contains("TileEntityData", 10)) {
            this.blockData = tag.getCompound("TileEntityData");
        }
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }
        if (tag.contains("CancelDrop")) {
            this.cancelDrop = tag.getBoolean("CancelDrop");
        }
        if (tag.contains("Natural", 99)) {
            this.natural = tag.getBoolean("Natural");
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return new ClientboundAddEntityPacket(this, entity, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double d0 = packet.getX();
        double d1 = packet.getY();
        double d2 = packet.getZ();
        this.setPos(d0, d1, d2);
        this.setStartPos(this.blockPosition());
    }

    @Override
    public void fillCrashReportCategory(CrashReportCategory category) {
        super.fillCrashReportCategory(category);
        category.setDetail("Imitating BlockState", this.getBlockState().toString());
    }
}
