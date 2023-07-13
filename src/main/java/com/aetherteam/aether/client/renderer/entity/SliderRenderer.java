package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.SliderGlowLayer;
import com.aetherteam.aether.client.renderer.entity.model.SliderModel;
import com.aetherteam.aether.entity.monster.dungeon.boss.slider.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class SliderRenderer extends MobRenderer<Slider, SliderModel> {
    private static final ResourceLocation SLIDER_ASLEEP_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/slider/slider_asleep.png");
    private static final ResourceLocation SLIDER_ASLEEP_CRITICAL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/slider/slider_asleep_critical.png");
    private static final ResourceLocation SLIDER_AWAKE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/slider/slider_awake.png");
    private static final ResourceLocation SLIDER_AWAKE_CRITICAL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/slider/slider_awake_critical.png");

    public SliderRenderer(EntityRendererProvider.Context context) {
        super(context, new SliderModel(context.bakeLayer(AetherModelLayers.SLIDER)), 0.7F);
        this.addLayer(new SliderGlowLayer(this));
    }

    @Override
    public void render(@Nonnull Slider slider, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        if (!slider.isDeadOrDying()) {
            super.render(slider, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    @Override
    protected void setupRotations(@Nonnull Slider slider, @Nonnull PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        if (!Minecraft.getInstance().isPaused()) {
            if (slider.getHurtAngle() != 0) {
                poseStack.mulPose(Axis.of(new Vector3f(slider.getHurtAngleX(), 0.0F, -slider.getHurtAngleZ())).rotationDegrees(slider.getHurtAngle() * -15.0F));
            }
            if (slider.getHurtAngle() > 0.0) {
                slider.setHurtAngle(Mth.lerp(partialTicks, slider.getHurtAngle(), slider.getHurtAngle() * 0.98F));
            }
            if (isEntityUpsideDown(slider)) {
                poseStack.translate(0.0, slider.getBbHeight() + 0.1F, 0.0);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Slider slider) {
        if (!slider.isAwake()) {
            return !slider.isCritical() ? SLIDER_ASLEEP_TEXTURE : SLIDER_ASLEEP_CRITICAL_TEXTURE;
        } else {
            return !slider.isCritical() ? SLIDER_AWAKE_TEXTURE : SLIDER_AWAKE_CRITICAL_TEXTURE;
        }
    }
}
