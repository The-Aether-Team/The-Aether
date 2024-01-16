package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.block.FrictionCapped;
import io.github.fabricators_of_create.porting_lib.block.CustomFrictionBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class QuicksoilGlassBlock extends GlassBlock implements FrictionCapped, CustomFrictionBlock {
    public QuicksoilGlassBlock(Properties properties) {
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
