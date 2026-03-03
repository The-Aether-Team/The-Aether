package com.aetherteam.aether.client.renderer.entity.sub;

import com.aetherteam.aether.client.AetherRenderTypes;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;

public class PotteryMoaSubRenderer implements MoaSubRenderer {
    @Override
    public void render(MoaModel parentModel, MoaModel layerModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        poseStack.pushPose();
        parentModel.copyPropertiesTo(layerModel);
        layerModel.prepareMobModel(moa, limbSwing, limbSwingAmount, partialTicks);
        layerModel.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        VertexConsumer consumer = buffer.getBuffer(AetherRenderTypes.potteryMoa());
        layerModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public void createParticles(ClientLevel level, Moa moa) {
        if (moa.tickCount % 2 == 0) {
            level.addParticle(ParticleTypes.ENCHANT, moa.getRandomX(0.5F), moa.getRandomY() + 0.5F, moa.getRandomZ(0.5F), (moa.getRandom().nextDouble() - 0.5F) * 2.0F, -moa.getRandom().nextDouble(), (moa.getRandom().nextDouble() - 0.5F) * 2.0F);
        }
    }
}
