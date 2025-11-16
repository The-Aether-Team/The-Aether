package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.BlockStateExtension;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockState.class)
public abstract class BlockStateMixin extends BlockBehaviour.BlockStateBase implements BlockStateExtension {
    protected BlockStateMixin(Block owner, Reference2ObjectArrayMap<Property<?>, Comparable<?>> values, MapCodec<BlockState> propertiesCodec) {
        super(owner, values, propertiesCodec);
    }

    @Override
    public boolean aetherFabric$hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState neighborState, Direction dir) {
        return this.getBlock().aetherFabric$hidesNeighborFace(level, pos, ((BlockState)(Object)this), neighborState, dir);
    }

    @Override
    public boolean aetherFabric$supportsExternalFaceHiding() {
        return this.getBlock().aetherFabric$supportsExternalFaceHiding((BlockState)(Object)this);
    }

    @Override
    public Float aetherFabric$getExplosionResistance(BlockGetter level, BlockPos pos, Explosion explosion) {
        return this.getBlock().aetherFabric$getExplosionResistance(((BlockState)(Object)this), level, pos, explosion);
    }

    @Override
    public Float aetherFabric$getFriction(LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return this.getBlock().aetherFabric$getFriction(((BlockState)(Object)this), level, pos, entity);
    }
}
