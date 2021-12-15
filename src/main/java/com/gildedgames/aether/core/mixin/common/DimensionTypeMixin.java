package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.event.listeners.capability.EternalDayListener;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
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
    private void timeOfDay(long p_236032_1_, CallbackInfoReturnable<Float> cir) {
        if (this.effectsLocation.equals(new ResourceLocation(Aether.MODID, "the_aether"))) {
            double time = (double) this.fixedTime.orElse(p_236032_1_);
            double d0 = MathHelper.frac(time / 72000.0D - 0.25D);
            double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
            cir.setReturnValue((float)(d0 * 2.0D + d1) / 3.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "moonPhase", cancellable = true)
    private void moonPhase(long p_236032_1_, CallbackInfoReturnable<Integer> cir) {
        if (this.effectsLocation.equals(new ResourceLocation(Aether.MODID, "the_aether"))) {
            long time = this.fixedTime.orElse(p_236032_1_);
            World world = EternalDayListener.world;
            if (world != null) {
                IEternalDay eternalDay = IEternalDay.get(world).orElse(null);
                eternalDay.setServerWorldTime(world.getDayTime());
                time = eternalDay.getServerWorldTime();
            }
            cir.setReturnValue((int) (time / 72000L % 8L + 8L) % 8);
        }
    }
}
