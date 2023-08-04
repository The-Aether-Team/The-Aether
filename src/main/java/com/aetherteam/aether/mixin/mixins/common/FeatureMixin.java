package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.AetherTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class FeatureMixin {
    /**
     * Prevents Aether Dirt from being replaced by Podzol.<br><br>
     * Marked for deprecation as it will be replaced by a NeoForge event in 1.20.
     * @param level The {@link LevelSimulatedReader} that is being checked in.
     * @param pos The {@link BlockPos} to check for the block.
     * @param cir The {@link Boolean} {@link CallbackInfoReturnable} used for the method's return value.
     */
    @Deprecated(forRemoval = true, since = "1.19.4")
    @Inject(at = @At(value = "HEAD"), method = "isGrassOrDirt(Lnet/minecraft/world/level/LevelSimulatedReader;Lnet/minecraft/core/BlockPos;)Z", cancellable = true)
    private static void isGrassOrDirt(LevelSimulatedReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level.isStateAtPosition(pos, state -> state.is(AetherTags.Blocks.AETHER_DIRT))) {
            cir.setReturnValue(false);
        }
    }
}
