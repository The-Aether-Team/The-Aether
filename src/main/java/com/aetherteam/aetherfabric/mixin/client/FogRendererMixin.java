package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.client.events.FogAdjustmentHelper;
import com.aetherteam.aetherfabric.client.events.FogColorHelper;
import com.aetherteam.aetherfabric.client.events.FogEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @Shadow
    private static float fogRed;

    @Shadow
    private static float fogGreen;

    @Shadow
    private static float fogBlue;

    @Inject(method = "setupFog", at = @At(value = "TAIL"))
    private static void aetherFabric$onFogRenderering(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean shouldCreateFog, float partialTick, CallbackInfo ci, @Local FogType fogType, @Local FogRenderer.FogData fogData){
        var helper = new FogAdjustmentHelper(camera, partialTick, fogMode, fogType, fogData.end, fogData.start, fogData.shape);

        FogEvents.ON_FOG_RENDER.invoker().onRenderer(helper);

        if (helper.isCanceled()) {
            RenderSystem.setShaderFogStart(helper.getNearPlaneDistance());
            RenderSystem.setShaderFogEnd(helper.getFarPlaneDistance());
            RenderSystem.setShaderFogShape(helper.getFogShape());
        }
    }

    @WrapOperation(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", ordinal = 1))
    private static void aetherFabric$adjustFogColor(float f, float g, float h, float i, Operation<Void> original, @Local(argsOnly = true) Camera activeRenderInfo, @Local(argsOnly = true, ordinal = 0) float partialTicks){
        var helper = new FogColorHelper(activeRenderInfo, partialTicks, f, g, h);

        FogEvents.ON_FOG_COLORING.invoker().onColor(helper);

        fogRed = helper.getRed();
        fogGreen = helper.getGreen();
        fogBlue = helper.getBlue();

        f = helper.getRed();
        g = helper.getGreen();
        h = helper.getBlue();

        original.call(f, g, h, i);
    }

    // TODO: GET PORTING LIB TO UPDATE THERE MIXIN TO ACTUALLY USE THE ARGS WITHIN MIXIN
//    @WrapOperation(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", remap = false))
//    private static void portingLibFix$modifyFogColors(float f, float g, float h, float i, Operation<Void> original) {
//        original.call(fogRed, fogGreen, fogBlue, i);
//    }
}
