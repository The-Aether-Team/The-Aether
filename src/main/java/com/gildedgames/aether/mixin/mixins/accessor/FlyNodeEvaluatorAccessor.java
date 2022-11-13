package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FlyNodeEvaluator.class)
public interface FlyNodeEvaluatorAccessor {
    @Invoker
    BlockPathTypes callGetCachedBlockPathType(int x, int y, int z);
}