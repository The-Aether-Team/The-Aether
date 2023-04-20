package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.block.FrictionCapped;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class QuicksoilGlassPaneBlock extends IronBarsBlock implements FrictionCapped {
    public QuicksoilGlassPaneBlock(Properties properties) {
        super(properties);
    }

    /**
     * @see FrictionCapped#getCappedFriction(Entity, float)
     */
    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return this.getCappedFriction(entity, super.getFriction());
    }
}
