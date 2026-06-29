package com.aetherteam.aether.client.renderer.entity.sub;

import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public class FountainMoaSubRenderer implements MoaSubRenderer {
    @Override
    public void render(MoaModel parentModel, MoaModel layerModel, PoseStack poseStack, MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public AnimationData getAnimationData() {
        return new AnimationData(16, 100);
    }
}
