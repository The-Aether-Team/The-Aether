package com.aetherteam.aether.entity.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.Floatable;
import com.aetherteam.aether.block.miscellaneous.FloatingBlock;
import com.aetherteam.aether.data.resources.AetherDamageTypes;
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
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.item.FallingBlockEntity}.<br><br>
 * Modified for reversed gravity.
 */
public class FloatingBlockEntity extends Entity {
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.BLOCK_POS);
    private BlockState blockState = Blocks.SAND.defaultBlockState();
    public int time;
    public boolean dropItem = true;
    private boolean cancelDrop;
    private boolean hurtEntities;
    private int fallDamageMax = 40;
    private float fallDamagePerDistance;
    private int floatDistance;
    @Nullable
    public CompoundTag blockData;
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
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_START_POS, BlockPos.ZERO);
    }

    @Override
    public void tick() {
        if (this.getBlockState().isAir()) {
            this.discard();
        } else {
            Block block = this.getBlockState().getBlock();
            if (this.time++ == 0) {
                BlockPos blockPos = this.blockPosition();
                if (this.getLevel().getBlockState(blockPos).is(block)) {
                    this.getLevel().removeBlock(blockPos, false);
                }
            }

            if (!this.isNoGravity()) {
                this.floatDistance = this.blockPosition().getY() - this.getStartPos().getY();
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.04, 0.0)); // Move upwards.
                this.causeFallDamage();
                if (this.getLevel().isClientSide()) {
                    this.spawnFloatingBlockParticles();
                }
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.getLevel().isClientSide() && this.getLevel() instanceof ServerLevel serverLevel) {
                BlockPos blockPos1 = this.blockPosition();
                boolean isConcrete = this.getBlockState().getBlock() instanceof ConcretePowderBlock;
                boolean canConvert = isConcrete && this.getLevel().getFluidState(blockPos1).is(FluidTags.WATER);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (isConcrete && d0 > 1.0) {
                    BlockHitResult blockHitResult = this.getLevel().clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                    if (blockHitResult.getType() != HitResult.Type.MISS && this.getLevel().getFluidState(blockHitResult.getBlockPos()).is(FluidTags.WATER)) {
                        blockPos1 = blockHitResult.getBlockPos();
                        canConvert = true;
                    }
                }

                if ((!this.verticalCollision || this.isOnGround()) && !canConvert) {
                    if (!this.getLevel().isClientSide() && (this.time > 100 && (blockPos1.getY() <= this.getLevel().getMinBuildHeight() || blockPos1.getY() > this.getLevel().getMaxBuildHeight()) || this.time > 600)) { // Checks max y-level.
                        if ((!this.natural || !this.getBlockState().requiresCorrectToolForDrops()) && this.dropItem && this.getLevel().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.dropBlock(this.getBlockState());
                        }
                        this.discard();
                    }
                } else {
                    BlockState blockState = this.getLevel().getBlockState(blockPos1);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
                    if (!blockState.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean canBeReplaced = blockState.canBeReplaced(new DirectionalPlaceContext(this.getLevel(), blockPos1, Direction.UP, ItemStack.EMPTY, Direction.DOWN));
                            boolean isAboveFree = FloatingBlock.isFree(this.getLevel().getBlockState(blockPos1.above())) && (!isConcrete || !canConvert); // Check above position.
                            boolean canBlockSurvive = this.getBlockState().canSurvive(this.getLevel(), blockPos1) && !isAboveFree;
                            if ((canBeReplaced && canBlockSurvive) || (this.natural && blockState.getBlock().defaultDestroyTime() >= 0)) {
                                if (this.getBlockState().hasProperty(BlockStateProperties.WATERLOGGED) && this.getLevel().getFluidState(blockPos1).is(Fluids.WATER)) {
                                    this.blockState = this.getBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
                                }
                                BlockState previousBlockState = this.getLevel().getBlockState(blockPos1);
                                if (this.getLevel().setBlock(blockPos1, this.getBlockState(), 1 | 2)) {
                                    if (this.natural && !previousBlockState.isAir()) {
                                        this.dropBlock(previousBlockState);
                                    }

                                    serverLevel.getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockPos1, this.getLevel().getBlockState(blockPos1)));
                                    this.discard();
                                    if (block instanceof Floatable floatable) {
                                        floatable.onCollide(this.getLevel(), blockPos1, this.getBlockState(), blockState, this);
                                    } else if (block instanceof ConcretePowderBlock concretePowderBlock) {
                                        if (ConcretePowderBlockAccessor.callShouldSolidify(this.getLevel(), blockPos1, blockState)) {
                                            ConcretePowderBlockAccessor concretePowderBlockAccessor = (ConcretePowderBlockAccessor) concretePowderBlock;
                                            this.getLevel().setBlock(blockPos1, concretePowderBlockAccessor.aether$getConcrete(), 1 | 2);
                                        }
                                    } else if (block instanceof AnvilBlock) {
                                        if (!this.isSilent()) {
                                            this.getLevel().levelEvent(1029, blockPos1, 0);
                                        }
                                    }

                                    if (this.blockData != null && this.getBlockState().hasBlockEntity()) {
                                        BlockEntity blockEntity = this.getLevel().getBlockEntity(blockPos1);
                                        if (blockEntity != null) {
                                            CompoundTag tag = blockEntity.saveWithoutMetadata();
                                            for (String string : this.blockData.getAllKeys()) {
                                                Tag blockDataTag = this.blockData.get(string);
                                                if (blockDataTag != null) {
                                                    tag.put(string, blockDataTag.copy());
                                                }
                                            }

                                            try {
                                                blockEntity.load(tag);
                                            } catch (Exception exception) {
                                                Aether.LOGGER.error("Failed to load block entity from floating block", exception);
                                            }
                                            blockEntity.setChanged();
                                        }
                                    }
                                } else if ((!this.natural || !this.getBlockState().requiresCorrectToolForDrops()) && this.dropItem && this.getLevel().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.callOnBrokenAfterFall(block, blockPos1);
                                    this.dropBlock(this.getBlockState());
                                }
                            } else {
                                this.discard();
                                if ((!this.natural || !this.getBlockState().requiresCorrectToolForDrops() || blockState.getBlock().defaultDestroyTime() < 0) && this.dropItem && this.getLevel().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
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
        if (this.getLevel() instanceof ServerLevel serverLevel) {
            for (ItemStack stack : Block.getDrops(state, serverLevel, this.blockPosition(), null)) {
                this.spawnAtLocation(stack);
            }
        }
    }

    public void callOnBrokenAfterFall(Block block, BlockPos pos) {
        if (block instanceof Floatable floatable) {
            floatable.onBrokenAfterCollide(this.getLevel(), pos, this);
        }
    }

    private void causeFallDamage() {
        if (this.hurtEntities) {
            Predicate<Entity> predicate;
            DamageSource damageSource;
            if (this.getBlockState().getBlock() instanceof Floatable floatable) {
                predicate = floatable.getHurtsEntitySelector();
                damageSource = floatable.getFallDamageSource(this);
            } else {
                predicate = EntitySelector.NO_SPECTATORS;
                damageSource = AetherDamageTypes.entityDamageSource(this.getLevel(), AetherDamageTypes.FLOATING_BLOCK, this);
            }

            float f = (float) Math.min(Mth.floor((float) this.floatDistance * this.fallDamagePerDistance), this.fallDamageMax);
            this.getLevel().getEntities(this, this.getBoundingBox(), predicate).forEach((p_149649_) -> p_149649_.hurt(damageSource, f));
            boolean flag = this.getBlockState().is(BlockTags.ANVIL);
            if (flag && f > 0.0F && this.random.nextFloat() < 0.05F + (float) this.floatDistance * 0.05F) {
                BlockState blockstate = AnvilBlock.damage(this.getBlockState());
                if (blockstate == null) {
                    this.cancelDrop = true;
                } else {
                    this.blockState = blockstate;
                }
            }
        }
    }

    private void spawnFloatingBlockParticles() {
        if (this.random.nextInt(8) == 0) {
            double d0 = (this.getX() - 0.5) + this.random.nextDouble();
            double d1 = this.getY() - 0.05;
            double d2 = (this.getZ() - 0.5) + this.random.nextDouble();
            this.getLevel().addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, this.getBlockState()), d0, d1, d2, 0.0, 0.0, 0.0);
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

    @Override
    public Entity changeDimension(ServerLevel destination, ITeleporter teleporter) {
        return null;
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
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("BlockState")) {
            this.blockState = NbtUtils.readBlockState(this.level.holderLookup(Registries.BLOCK), tag.getCompound("BlockState"));
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
        if (tag.contains("Natural", 99)) {
            this.natural = tag.getBoolean("Natural");
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double d0 = packet.getX();
        double d1 = packet.getY();
        double d2 = packet.getZ();
        this.setPos(d0, d1 + (double)((1.0F - this.getBbHeight()) / 2.0F), d2);
        this.setStartPos(this.blockPosition());
    }

    @Override
    public void fillCrashReportCategory(CrashReportCategory category) {
        super.fillCrashReportCategory(category);
        category.setDetail("Immitating BlockState", this.getBlockState().toString());
    }
}
