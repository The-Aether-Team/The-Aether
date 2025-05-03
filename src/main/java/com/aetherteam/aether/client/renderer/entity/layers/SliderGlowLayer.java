package com.aetherteam.aether.client.renderer.entity.layers;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SliderModel;
import com.aetherteam.aether.client.renderer.entity.state.SliderRenderState;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SliderGlowLayer extends EyesLayer<SliderRenderState, SliderModel> {
    private static final RenderType SLIDER_AWAKE_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_awake_glow.png"));
    private static final RenderType SLIDER_AWAKE_CRITICAL_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_awake_critical_glow.png"));

    public SliderGlowLayer(RenderLayerParent<SliderRenderState, SliderModel> pRenderer) {
        super(pRenderer);
    }

    /**
     * Renders the glowing eye layer for the Slider when it is awake.
     *
     * @param poseStack       The rendering {@link PoseStack}.
     * @param buffer          The rendering {@link MultiBufferSource}.
     * @param packedLight     The {@link Integer} for the packed lighting for rendering.
     * @param slider          The {@link Slider} entity.
     * @param netHeadYaw      The {@link Float} for the head yaw rotation.
     * @param headPitch       The {@link Float} for the head pitch rotation.
     */
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, SliderRenderState slider, float netHeadYaw, float headPitch) {
        VertexConsumer consumer = buffer.getBuffer(this.renderType(slider));
        if (slider.awake) {
            this.getParentModel().renderToBuffer(poseStack, consumer, 15728640, OverlayTexture.NO_OVERLAY);
        }
    }

    /**
     * Selects the red glow render if the Slider is critical, and otherwise selects the default blue glow render.
     *
     * @param slider The {@link SliderRenderState}.
     * @return The {@link RenderType}.
     */
    public RenderType renderType(SliderRenderState slider) {
        if (slider.critical) {
            return SLIDER_AWAKE_CRITICAL_GLOW;
        }
        return this.renderType();
    }

    @Override
    public RenderType renderType() {
        return SLIDER_AWAKE_GLOW;
    }
}
