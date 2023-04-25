package com.aetherteam.aether.entity.block;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.api.BlockLikeEntity;
import com.aetherteam.aether.api.FloatingBlockHelper;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FloatingBlockEntity extends BlockLikeEntity {
    private Supplier<Boolean> dropState = () -> FloatingBlockHelper.DEFAULT_DROP_STATE.apply(this);
    private boolean dropping = false;
    private BiConsumer<Double, Boolean> onEndFloating = (f, b) -> {
    };
    public double lastYVelocity = 0;

    public FloatingBlockEntity(EntityType<? extends BlockLikeEntity> entityType, Level world) {
        super(entityType, world);
    }

    public FloatingBlockEntity(Level world, double x, double y, double z, BlockState floatingBlockState) {
        super(AetherEntityTypes.FLOATING_BLOCK.get(), world, x, y, z, floatingBlockState);
        this.setHurtEntities(floatingBlockState.is(AetherTags.Blocks.HURTABLE_FLOATERS));
    }

    public FloatingBlockEntity(Level world, BlockPos pos, BlockState floatingBlockState, boolean partOfSet) {
        super(AetherEntityTypes.FLOATING_BLOCK.get(), world, pos, floatingBlockState, partOfSet);
        this.setHurtEntities(floatingBlockState.is(AetherTags.Blocks.HURTABLE_FLOATERS));
    }

    @Override
    public void postTickMoveEntities() {
        if (FallingBlock.isFree(this.blockState)) return;

        List<Entity> otherEntities = this.level.getEntities(this, getBoundingBox().minmax(getBoundingBox().move(0, 3 * (this.yo - this.getY()), 0)));
        for (Entity entity : otherEntities) {
            if (!(entity instanceof BlockLikeEntity) && !entity.noPhysics && this.collides) {
                entity.move(MoverType.SHULKER_BOX, this.getDeltaMovement());
                entity.setOnGround(true);

                entity.setPos(entity.getX(), this.getBoundingBox().maxY, entity.getZ());
                entity.fallDistance = 0F;
            }
            this.postTickEntityCollision(entity);
        }
    }

    @Override
    public void postTickMovement() {
        // Drag
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));

        this.lastYVelocity = this.getDeltaMovement().y;

        if (!this.isNoGravity()) {
            if (!isDropping() && !shouldBeginDropping()) {
                if (isFastFloater()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.05D, 0.0D));
                } else {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.03D, 0.0D));
                }
            } else {
                this.setDropping(true);
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
            }
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    public boolean shouldCease() {
        return super.shouldCease()
                || (this.isOnGround() && (this.isDropping() || this.getDeltaMovement().y() == 0)
                || (this.verticalCollision && !this.onGround));
    }

    public Supplier<Boolean> getDropState() {
        return dropState;
    }

    public void setDropState(Supplier<Boolean> supplier) {
        dropState = supplier;
    }

    public boolean shouldBeginDropping() {
        return getDropState().get();
    }

    public boolean isDropping() {
        return dropping;
    }

    public void setDropping(boolean dropping) {
        this.dropping = dropping;
    }

    public BiConsumer<Double, Boolean> getOnEndFloating() {
        return this.onEndFloating;
    }

    // It's fine if this isn't properly synced
    public void setOnEndFloating(BiConsumer<Double, Boolean> consumer) {
        this.onEndFloating = consumer;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Dropping", this.isDropping());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Dropping", 99)) this.setDropping(compound.getBoolean("Dropping"));
    }

    @Override
    public boolean trySetBlock() {
        if (super.trySetBlock()) {
            this.getOnEndFloating().accept(Math.abs(this.lastYVelocity), true);
            return true;
        }
        return false;
    }

    @Override
    public void breakApart() {
        super.breakApart();
        this.getOnEndFloating().accept(Math.abs(this.lastYVelocity), false);
    }

    public boolean isFastFloater() {
        return this.getBlockState().is(AetherTags.Blocks.FAST_FLOATERS) && !this.partOfSet;
    }

    @Override
    public void alignWith(BlockLikeEntity other, Vec3i offset) {
        super.alignWith(other, offset);
        if (other instanceof FloatingBlockEntity fbe) {
            this.setDropping(fbe.isDropping());
        }
    }
}
