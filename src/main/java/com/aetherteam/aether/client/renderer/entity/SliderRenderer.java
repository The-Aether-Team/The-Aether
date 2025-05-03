package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.SliderGlowLayer;
import com.aetherteam.aether.client.renderer.entity.model.SliderModel;
import com.aetherteam.aether.client.renderer.entity.state.SliderRenderState;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class SliderRenderer extends MobRenderer<Slider, SliderRenderState, SliderModel> {
    private static final ResourceLocation SLIDER_ASLEEP_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_asleep.png");
    private static final ResourceLocation SLIDER_ASLEEP_CRITICAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_asleep_critical.png");
    private static final ResourceLocation SLIDER_AWAKE_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_awake.png");
    private static final ResourceLocation SLIDER_AWAKE_CRITICAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_awake_critical.png");

    public SliderRenderer(EntityRendererProvider.Context context) {
        super(context, new SliderModel(context.bakeLayer(AetherModelLayers.SLIDER)), 0.7F);
        this.addLayer(new SliderGlowLayer(this));
    }

    @Override
    public void render(SliderRenderState slider, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (slider.deathTime <= 0) {
            super.render(slider, poseStack, buffer, packedLight);
        }
    }

    @Override
    public SliderRenderState createRenderState() {
        return new SliderRenderState();
    }

    @Override
    public void extractRenderState(Slider entity, SliderRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.hurtAngleX = entity.getHurtAngleX();
        reusedState.hurtAngleZ = entity.getHurtAngleZ();
        reusedState.hurtAngle = entity.getHurtAngle();
        reusedState.critical = entity.isCritical();
        reusedState.awake = entity.isAwake();
    }

    /**
     * Rotates the Slider to tilt based on the direction and angle of the damage it has received.
     *
     * @param slider       The {@link Slider} entity.
     * @param poseStack    The rendering {@link PoseStack}.
     * @param bob          The {@link Float} for the entity's animation bob.
     * @param yBodyRot     The {@link Float} for the rotation yaw.
     */
    @Override
    protected void setupRotations(SliderRenderState slider, PoseStack poseStack, float bob, float yBodyRot) {
        if (!Minecraft.getInstance().isPaused()) {
            if (slider.hurtAngle != 0) {
                poseStack.mulPose(Axis.of(new Vector3f(slider.hurtAngleX, 0.0F, -slider.hurtAngleZ)).rotationDegrees(slider.hurtAngle * -15.0F));
            }
            if (slider.hurtAngle > 0.0) {
                slider.hurtAngle = (Mth.lerp(slider.partialTick, slider.hurtAngle, slider.hurtAngle * 0.78F));
            }
            if (slider.isUpsideDown) {
                poseStack.translate(0.0, slider.boundingBoxHeight + 0.1F, 0.0);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(SliderRenderState slider) {
        if (!slider.awake) {
            return !slider.critical ? SLIDER_ASLEEP_TEXTURE : SLIDER_ASLEEP_CRITICAL_TEXTURE;
        } else {
            return !slider.critical ? SLIDER_AWAKE_TEXTURE : SLIDER_AWAKE_CRITICAL_TEXTURE;
        }
    }
}
