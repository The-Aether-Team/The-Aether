package com.aetherteam.aether.mixin.mixins.common.accessor;


import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldGenRegion.class)
public interface WorldGenRegionAccessor {
//    @Accessor("structureManager")
//    StructureManager aether$getStructureManager();
}

