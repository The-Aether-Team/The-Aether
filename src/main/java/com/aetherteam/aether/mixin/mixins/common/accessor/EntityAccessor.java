package com.aetherteam.aether.mixin.mixins.common.accessor;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("random")
    RandomSource aether$getRandom();

    @Accessor("portalEntrancePos")
    BlockPos aether$getPortalEntrancePos();

    @Accessor("portalEntrancePos")
    void aether$setPortalEntrancePos(BlockPos portalEntrancePos);

    @Invoker
    Vec3 callGetRelativePortalPosition(Direction.Axis axis, BlockUtil.FoundRectangle portal);

    @Accessor("fluidHeight")
    Object2DoubleMap<TagKey<Fluid>> getFluidHeight();
}