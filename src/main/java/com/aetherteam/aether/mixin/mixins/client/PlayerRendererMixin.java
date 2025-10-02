package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.accessory.FirstPersonRendering;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    @Unique
    private static HumanoidArm currentArm = null;

    @Inject(method = "renderRightHand", at = @At("HEAD"))
    private void firstPersonRightAccessories(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, CallbackInfo ci) {
        currentArm = HumanoidArm.RIGHT;
    }

    @Inject(method = "renderLeftHand", at = @At("HEAD"))
    private void firstPersonLeftAccessories(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, CallbackInfo ci) {
        currentArm = HumanoidArm.LEFT;
    }

    @WrapMethod(method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;)V")
    private void renderHand(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, ModelPart rendererArm, ModelPart rendererArmwear, Operation<Void> original) {
        PlayerModel<AbstractClientPlayer> playerModel = ((PlayerRenderer) (Object) this).getModel();
        if (!AetherMixinHooks.RENDERING_ACCESSORY) {
            original.call(poseStack, buffer, combinedLight, player, rendererArm, rendererArmwear);
        }
        if (currentArm != null) {
            EquipmentUtil.getCurios(player, (item) -> item.getItem() instanceof AccessoryItem accessoryItem && accessoryItem.rendersInFirstPerson(item)).forEach((slotResult) -> {
                String identifier = slotResult.slotContext().identifier();
                int id = slotResult.slotContext().index();
                ItemStack itemStack = slotResult.stack();
                CuriosApi.getCuriosInventory(player).ifPresent(handler -> handler.getStacksHandler(identifier).ifPresent(stacksHandler -> {
                    if (stacksHandler.getRenders().get(id)) { // Check if accessory is visible.
                        CuriosRendererRegistry.getRenderer(itemStack.getItem()).ifPresent((renderer) -> {
                            if (renderer instanceof FirstPersonRendering firstPersonRendering) {
                                poseStack.pushPose();
                                firstPersonRendering.renderOnFirstPerson(currentArm, itemStack, player, poseStack, playerModel, buffer, combinedLight);
                                poseStack.popPose();
                            }
                        });
                    }
                }));
            });
        }
        currentArm = null;
    }

    @Shadow
    public abstract void render(AbstractClientPlayer entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight);
}
