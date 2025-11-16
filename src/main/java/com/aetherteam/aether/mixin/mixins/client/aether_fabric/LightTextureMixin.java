package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.renderer.level.AetherSkyRenderEffects;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Vector3f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightTexture.class)
public abstract class LightTextureMixin {
    @Inject(method = "updateLightTexture", at = @At(value = "JUMP", opcode = Opcodes.IFLE, ordinal = 4))
    private void aetherFabric$adjustLightTexture(float partialTicks, CallbackInfo ci, @Local() ClientLevel clientLevel,
                                                 @Local(ordinal = 1) float skyDarken, @Local(ordinal = 9) float skyLight,
                                                 @Local(ordinal = 10) float blockLight, @Local(ordinal = 0) int pixelX,
                                                 @Local(ordinal = 1) int pixelY, @Local(ordinal = 1) Vector3f colors) {
        if (clientLevel.effects() instanceof AetherSkyRenderEffects aetherSkyRenderEffects) {
            aetherSkyRenderEffects.adjustLightmapColors(clientLevel, partialTicks, skyDarken, skyLight, blockLight, pixelX, pixelY, colors);
        }
    }
}
