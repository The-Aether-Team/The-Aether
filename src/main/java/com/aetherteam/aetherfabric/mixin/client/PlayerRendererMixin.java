package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.client.events.PlayerRenderEvents;
import com.aetherteam.aetherfabric.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @WrapOperation(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private <T extends LivingEntity> void aetherFabric$onPlayerRenderer(PlayerRenderer instance, T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Operation<Void> original) {
        var player = (AbstractClientPlayer) entity;

        var callback = new CancellableCallbackImpl();

        PlayerRenderEvents.BEFORE_RENDER.invoker().beforeRendering(player, instance, partialTicks, poseStack, buffer, packedLight, callback);

        if(callback.isCanceled()) return;

        original.call(instance, entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        PlayerRenderEvents.AFTER_RENDER.invoker().afterRendering(player, instance, partialTicks, poseStack, buffer, packedLight);
    }

    @WrapMethod(method = "renderLeftHand")
    private void aetherFabric$onRenderLeftHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, Operation<Void> original){
        float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(!player.level().tickRateManager().isEntityFrozen(player));

        var callback = new CancellableCallbackImpl();

        PlayerRenderEvents.BEFORE_ARM_RENDER.invoker().beforeRendering(player, (PlayerRenderer) (Object) this, partialTicks, poseStack, buffer, packedLight, HumanoidArm.LEFT, callback);

        if(callback.isCanceled()) return;

        original.call(poseStack, buffer, packedLight, player);

        PlayerRenderEvents.AFTER_ARM_RENDER.invoker().afterRendering(player, (PlayerRenderer) (Object) this, partialTicks, poseStack, buffer, packedLight, HumanoidArm.LEFT);
    }

    @WrapMethod(method = "renderRightHand")
    private void aetherFabric$onRenderRightHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, Operation<Void> original){
        float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(!player.level().tickRateManager().isEntityFrozen(player));

        var callback = new CancellableCallbackImpl();

        PlayerRenderEvents.BEFORE_ARM_RENDER.invoker().beforeRendering(player, (PlayerRenderer) (Object) this, partialTicks, poseStack, buffer, packedLight, HumanoidArm.RIGHT, callback);

        if(callback.isCanceled()) return;

        original.call(poseStack, buffer, packedLight, player);

        PlayerRenderEvents.AFTER_ARM_RENDER.invoker().afterRendering(player, (PlayerRenderer) (Object) this, partialTicks, poseStack, buffer, packedLight, HumanoidArm.RIGHT);
    }
}
