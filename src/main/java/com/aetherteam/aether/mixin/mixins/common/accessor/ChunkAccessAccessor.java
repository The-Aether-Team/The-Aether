package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkAccess.class)
public interface ChunkAccessAccessor {
    @Accessor("noiseChunk")
    NoiseChunk aether$getNoiseChunk();
}
