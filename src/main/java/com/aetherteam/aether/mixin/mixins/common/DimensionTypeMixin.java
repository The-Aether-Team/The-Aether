package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin is used to handle the eternal day code in the Aether.
 */
@Mixin(DimensionType.class)
public class DimensionTypeMixin {
    @Final
    @Shadow
    private ResourceLocation effectsLocation;

    @Inject(at = @At("HEAD"), method = "timeOfDay", cancellable = true)
    private void timeOfDay(long dayTime, CallbackInfoReturnable<Float> cir) {
        if (this.effectsLocation.equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            double d0 = Mth.frac(dayTime / (double) AetherDimensions.AETHER_TICKS_PER_DAY - 0.25);
            double d1 = 0.5 - Math.cos(d0 * Math.PI) / 2.0;
            cir.setReturnValue((float)(d0 * 2.0 + d1) / 3.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "moonPhase", cancellable = true)
    private void moonPhase(long dayTime, CallbackInfoReturnable<Integer> cir) {
        if (this.effectsLocation.equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            cir.setReturnValue((int) (dayTime / (long) AetherDimensions.AETHER_TICKS_PER_DAY % 8L + 8L) % 8);
        }
    }
}
