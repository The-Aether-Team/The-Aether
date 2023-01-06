package com.gildedgames.aether.client.renderer.entity.layers;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.entity.model.SliderModel;
import com.gildedgames.aether.entity.monster.dungeon.boss.slider.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class SliderGlowLayer extends EyesLayer<Slider, SliderModel> {
    private static final RenderType SLIDER_AWAKE_GLOW = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/slider/slider_awake_glow.png"));
    private static final RenderType SLIDER_AWAKE_CRITICAL_GLOW = RenderType.eyes(new ResourceLocation(Aether.MODID, "textures/entity/mobs/slider/slider_awake_critical_glow.png"));

    public SliderGlowLayer(RenderLayerParent<Slider, SliderModel> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, MultiBufferSource buffer, int packedLight, @Nonnull Slider slider, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer consumer = buffer.getBuffer(this.renderType(slider));
        if (slider.isAwake()) {
            this.getParentModel().renderToBuffer(poseStack, consumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Nonnull
    public RenderType renderType(Slider slider) {
        if (slider.isCritical()) {
            return SLIDER_AWAKE_CRITICAL_GLOW;
        }
        return this.renderType();
    }

    @Nonnull
    @Override
    public RenderType renderType() {
        return SLIDER_AWAKE_GLOW;
    }
}
