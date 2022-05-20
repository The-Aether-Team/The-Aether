package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.util.AetherDoubleDropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class QuicksoilBlock extends AetherDoubleDropBlock {
    public QuicksoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        if (entity != null) {
            if (entity.getLevel().isClientSide()) {
                Vec3 motion = entity.getDeltaMovement();
                if (Math.abs(motion.x()) > 1.0 || Math.abs(motion.z()) > 1.0) {
                    return 0.99F;
                }
            }
        }
        return this.getFriction();
    }
}
