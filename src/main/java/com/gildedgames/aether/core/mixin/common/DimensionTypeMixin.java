package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.event.listeners.capability.EternalDayListener;
import com.gildedgames.aether.core.capability.interfaces.EternalDaySerializable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalLong;

@Mixin(DimensionType.class)
public class DimensionTypeMixin
{
    @Final
    @Shadow
    private OptionalLong fixedTime;
    @Final
    @Shadow
    private ResourceLocation effectsLocation;

    @Inject(at = @At("HEAD"), method = "timeOfDay", cancellable = true)
    private void timeOfDay(long dayTime, CallbackInfoReturnable<Float> cir) {
        if (this.effectsLocation.getNamespace().equals(Aether.MODID)) {
            double time = (double) this.fixedTime.orElse(dayTime);
            double d0 = Mth.frac(time / 72000.0D - 0.25D);
            double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
            cir.setReturnValue((float)(d0 * 2.0D + d1) / 3.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "moonPhase", cancellable = true)
    private void moonPhase(long dayTime, CallbackInfoReturnable<Integer> cir) {
        if (this.effectsLocation.getNamespace().equals(Aether.MODID)) {
            long time = this.fixedTime.orElse(dayTime);
            Level level = EternalDayListener.world;
            if (level != null) {
                EternalDaySerializable eternalDay = EternalDaySerializable.get(level).orElse(null);
                eternalDay.setServerLevelTime(level.getDayTime());
                time = eternalDay.getServerLevelTime();
            }
            cir.setReturnValue((int) (time / 72000L % 8L + 8L) % 8);
        }
    }
}
