package com.gildedgames.aether.mixin.mixins.common.accessor;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NoiseRouterData.class)
public interface NoiseRouterDataAccessor {
    @Accessor("SHIFT_X")
    static ResourceKey<DensityFunction> getShiftX() {
        throw new AssertionError();
    }

    @Accessor("SHIFT_Z")
    static ResourceKey<DensityFunction> getShiftZ() {
        throw new AssertionError();
    }

    @Accessor("BASE_3D_NOISE_OVERWORLD")
    static ResourceKey<DensityFunction> getBase3DNoiseOverworld() {
        throw new AssertionError();
    }
}
