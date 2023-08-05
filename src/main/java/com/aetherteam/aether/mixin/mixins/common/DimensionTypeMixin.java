package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {
    /**
     * Checks if the dimension is the Aether using the {@link DimensionType#effectsLocation()} field, and uses
     * {@link AetherDimensions#AETHER_TICKS_PER_DAY} instead of the default day time of 24000 ticks.
     */
    @ModifyVariable(at = @At(value = "STORE"), method = "timeOfDay(J)F", index = 3)
    private double modifyTimeOfDay(double d0, long dayTime) {
        DimensionType dimensionType = (DimensionType) (Object) this;
        if (dimensionType.effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            return Mth.frac(dayTime / (double) AetherDimensions.AETHER_TICKS_PER_DAY - 0.25);
        } else {
            return d0;
        }
    }

    /**
     * [CODE COPY] - {@link DimensionType#moonPhase(long)}.<br><br>
     * Checks if the dimension is the Aether using the {@link DimensionType#effectsLocation()} field, and uses
     * {@link AetherDimensions#AETHER_TICKS_PER_DAY} instead of the default day time of 24000 ticks.
     */
    @Inject(at = @At("HEAD"), method = "moonPhase(J)I", cancellable = true)
    private void moonPhase(long dayTime, CallbackInfoReturnable<Integer> cir) {
        DimensionType dimensionType = (DimensionType) (Object) this;
        if (dimensionType.effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            cir.setReturnValue((int) (dayTime / (long) AetherDimensions.AETHER_TICKS_PER_DAY % 8L + 8L) % 8);
        }
    }
}
