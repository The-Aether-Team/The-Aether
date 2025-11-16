package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.client.events.LivingEntityRenderEvents;
import com.aetherteam.aetherfabric.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {
    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @WrapMethod(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void aetherFabric$onLivingEntityRenderer(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Operation<Void> original) {
        var callback = new CancellableCallbackImpl();

        LivingEntityRenderEvents.BEFORE_RENDER.invoker().beforeRendering(entity, (LivingEntityRenderer<LivingEntity, ?>) (Object) this, partialTicks, poseStack, buffer, packedLight, callback);

        if (callback.isCanceled()) return;

        original.call(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        LivingEntityRenderEvents.AFTER_RENDER.invoker().afterRendering(entity, (LivingEntityRenderer<LivingEntity, ?>) (Object) this, partialTicks, poseStack, buffer, packedLight);
    }

    @WrapOperation(
        method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z")
    )
    private boolean aetherFabric$adjustPassengerCheck(LivingEntity instance, Operation<Boolean> original) {
        return original.call(instance) && (instance.getVehicle() != null && instance.getVehicle().aetherFabric$shouldRiderSit());
    }
}
