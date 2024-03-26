package com.aetherteam.aether.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlueAercloudBlock extends AercloudBlock {
    protected static final VoxelShape COLLISION_SHAPE = Shapes.empty();

    public BlueAercloudBlock(Properties properties) {
        super(properties);
    }

    /**
     * Launches the entity inside the block into the air and resets their fall distance when not holding the shift key.
     * If they are holding the shift key, behavior defaults to that of {@link AercloudBlock#entityInside(BlockState, Level, BlockPos, Entity)}.
     * There is also code to reduce the amount of particles spawned by the block if the entity is stuck in it in some way (like because of creative flight).
     *
     * @param state  The {@link BlockState} of the block.
     * @param level  The {@link Level} the block is in.
     * @param pos    The {@link BlockPos} of the block.
     * @param entity The {@link Entity} in the block.
     */
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isShiftKeyDown() && (!entity.isVehicle() || !(entity.getControllingPassenger() instanceof Player))) {
            entity.resetFallDistance();
            entity.setDeltaMovement(entity.getDeltaMovement().x(), 2.0, entity.getDeltaMovement().z());
            if (level.isClientSide()) {
                int amount = 50; // Default amount.
                if (entity.getY() == entity.yOld) {
                    amount = 10; // Alternative amount if the entity's y-position is not changing.
                }
                for (int count = 0; count < amount; count++) {
                    double xOffset = pos.getX() + level.getRandom().nextDouble();
                    double yOffset = pos.getY() + level.getRandom().nextDouble();
                    double zOffset = pos.getZ() + level.getRandom().nextDouble();
                    level.addParticle(ParticleTypes.SPLASH, xOffset, yOffset, zOffset, 0.0, 0.0, 0.0);
                }
            }
            if (!(entity instanceof Projectile)) {
                entity.setOnGround(false);
            }
        } else {
            super.entityInside(state, level, pos, entity);
        }
    }

    @Override
    public VoxelShape getDefaultCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }
}
