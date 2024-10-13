package com.aetherteam.aether.mixin.mixins.common.accessor;


import net.minecraft.server.level.WorldGenRegion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldGenRegion.class)
public interface WorldGenRegionAccessor {
//    @Accessor("structureManager")
//    StructureManager aether$getStructureManager();
}

