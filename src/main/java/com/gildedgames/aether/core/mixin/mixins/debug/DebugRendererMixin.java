package com.gildedgames.aether.core.mixin.mixins.debug;

//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.debug.DebugRenderer;
//import net.minecraft.client.renderer.debug.PathfindingRenderer;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(DebugRenderer.class)
//public class DebugRendererMixin {
//    @Shadow
//    @Final
//    public PathfindingRenderer pathfindingRenderer;
//
//    @Inject(at = @At("HEAD"), method = "render", remap = false)
//    private void render(PoseStack pPoseStack, MultiBufferSource.BufferSource pBufferSource, double pCamX, double pCamY, double pCamZ, CallbackInfo ci) {
//        this.pathfindingRenderer.render(pPoseStack, pBufferSource, pCamX, pCamY, pCamZ);
//    }
//}
