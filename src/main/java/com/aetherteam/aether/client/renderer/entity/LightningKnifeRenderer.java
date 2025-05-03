package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.client.renderer.entity.state.LightningKnifeRenderState;
import com.aetherteam.aether.entity.projectile.weapon.ThrownLightningKnife;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Quaternionf;

public class LightningKnifeRenderer extends EntityRenderer<ThrownLightningKnife, LightningKnifeRenderState> {
    public LightningKnifeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public LightningKnifeRenderState createRenderState() {
        return new LightningKnifeRenderState();
    }

    @Override
    public void extractRenderState(ThrownLightningKnife p_entity, LightningKnifeRenderState reusedState, float partialTick) {
        super.extractRenderState(p_entity, reusedState, partialTick);
        reusedState.xRot = p_entity.getXRot(partialTick);
        reusedState.yRot = (-(p_entity.xRotO + (p_entity.getXRot() - p_entity.xRotO) * partialTick));
        reusedState.stack = p_entity.getItem();
        reusedState.id = p_entity.getId();
    }

    /**
     * Rotates and renders the Lightning Knife to look as if it has been thrown.
     *
     * @param lightningKnife The {@link LightningKnifeRenderState} entity.
     * @param poseStack      The rendering {@link PoseStack}.
     * @param buffer         The rendering {@link MultiBufferSource}.
     * @param packedLight    The {@link Integer} for the packed lighting for rendering.
     */
    @Override
    public void render(LightningKnifeRenderState lightningKnife, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        Quaternionf quaternion = Axis.YP.rotationDegrees(lightningKnife.yRot);
        quaternion.mul(Axis.XP.rotationDegrees(lightningKnife.yRot - 90.0F));
        quaternion.mul(Axis.ZP.rotationDegrees(-135.0F));
        poseStack.mulPose(quaternion);
        Minecraft.getInstance().getItemRenderer().renderStatic(lightningKnife.stack, ItemDisplayContext.GUI, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, lightningKnife.id);
        poseStack.popPose();
        super.render(lightningKnife, poseStack, buffer, packedLight);
    }

    public ResourceLocation getTextureLocation(LightningKnifeRenderState lightningKnife) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
