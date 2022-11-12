package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    RandomSource getRandom();

    @Accessor
    BlockPos getPortalEntrancePos();

    @Accessor
    void setPortalEntrancePos(BlockPos portalEntrancePos);

    @Invoker("getRelativePortalPosition")
    Vec3 getRelativePortalPosition(Direction.Axis pAxis, BlockUtil.FoundRectangle pPortal);
}
