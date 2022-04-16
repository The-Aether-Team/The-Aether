package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.event.hooks.CapabilityHooks;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.time.AetherTime;
import com.gildedgames.aether.core.util.LevelUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin is used to handle the eternal day code in the Aether.
 */
@Mixin(DimensionType.class)
public class DimensionTypeMixin {
    @Inject(at = @At("HEAD"), method = "timeOfDay", cancellable = true)
    private void timeOfDay(long dayTime, CallbackInfoReturnable<Float> cir) {
        Level level = CapabilityHooks.AetherTimeHooks.world;
        if (level != null && LevelUtil.inTag(level, AetherTags.Dimensions.ETERNAL_DAY)) {
            Aether.LOGGER.info(1);
            AetherTime aetherTime = AetherTime.get(level).orElse(null);
            double time = aetherTime.getDayTime();
            double d0 = Mth.frac(time / 72000.0D - 0.25D);
            double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
            cir.setReturnValue((float)(d0 * 2.0D + d1) / 3.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "moonPhase", cancellable = true)
    private void moonPhase(long dayTime, CallbackInfoReturnable<Integer> cir) {
        Level level = CapabilityHooks.AetherTimeHooks.world;
        if (level != null && LevelUtil.inTag(level, AetherTags.Dimensions.ETERNAL_DAY)) {
            Aether.LOGGER.info(2);
            AetherTime aetherTime = AetherTime.get(level).orElse(null);
            long time = aetherTime.getDayTime();
            cir.setReturnValue((int) (time / 72000L % 8L + 8L) % 8);
        }
    }
}
