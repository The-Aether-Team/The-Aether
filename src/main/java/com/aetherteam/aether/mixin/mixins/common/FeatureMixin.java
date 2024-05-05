package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.AetherTags;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Feature.class)
public class FeatureMixin {
    /**
     * Prevents Aether Dirt from being replaced by Podzol.<br><br>
     * Marked for deprecation as it will be replaced by a NeoForge event in 1.20.<br><br>
     * Note from Alexandra: Improved despite deprecation because it's still not an event in this version.
     *
     * @param original Whether Podzol should replace this, before checking if it is Aether Dirt.
     * @param level The {@link LevelSimulatedReader} that is being checked in.
     * @param pos   The {@link BlockPos} to check for the block.
     * @return Whether Podzol should replace this.
     */
    @Deprecated(forRemoval = true)
    @ModifyReturnValue(at = @At(value = "RETURN"), method = "isGrassOrDirt(Lnet/minecraft/world/level/LevelSimulatedReader;Lnet/minecraft/core/BlockPos;)Z")
    private static boolean isGrassOrDirt(boolean original, @Local(ordinal = 0, argsOnly = true) LevelSimulatedReader level, @Local(ordinal = 0, argsOnly = true) BlockPos pos) {
        if (level.isStateAtPosition(pos, state -> state.is(AetherTags.Blocks.AETHER_DIRT))) return false;
        return original;
    }
}
