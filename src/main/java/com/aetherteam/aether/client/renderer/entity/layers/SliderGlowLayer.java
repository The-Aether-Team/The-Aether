package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SliderModel;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SliderGlowLayer extends EyesLayer<Slider, SliderModel> {
    private static final RenderType SLIDER_AWAKE_GLOW = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/slider/slider_awake_glow.png"));
    private static final RenderType SLIDER_AWAKE_CRITICAL_GLOW = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/slider/slider_awake_critical_glow.png"));

    public SliderGlowLayer(RenderLayerParent<Slider, SliderModel> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Slider slider, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer consumer = buffer.getBuffer(this.renderType(slider));
        if (slider.isAwake()) {
            this.getParentModel().renderToBuffer(poseStack, consumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

   
    public RenderType renderType(Slider slider) {
        if (slider.isCritical()) {
            return SLIDER_AWAKE_CRITICAL_GLOW;
        }
        return this.renderType();
    }

   
    @Override
    public RenderType renderType() {
        return SLIDER_AWAKE_GLOW;
    }
}
