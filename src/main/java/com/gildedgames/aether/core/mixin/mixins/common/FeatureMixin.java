package com.gildedgames.aether.core.mixin.mixins.common;

import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class FeatureMixin {
    @Inject(at = @At(value = "HEAD"), method = "isGrassOrDirt", cancellable = true)
    private static void placeBlockAt(LevelSimulatedReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level.isStateAtPosition(pos, state -> state.is(AetherTags.Blocks.AETHER_DIRT))) {
            cir.setReturnValue(false);
        }
    }
}
