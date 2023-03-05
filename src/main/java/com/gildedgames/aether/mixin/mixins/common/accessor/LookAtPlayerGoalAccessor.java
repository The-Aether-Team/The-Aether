package com.gildedgames.aether.mixin.mixins.common.accessor;

import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LookAtPlayerGoal.class)
public interface LookAtPlayerGoalAccessor {
    @Mutable
    @Accessor("lookAtContext")
    void aether$setLookAtContext(TargetingConditions lookAtContext);
}
