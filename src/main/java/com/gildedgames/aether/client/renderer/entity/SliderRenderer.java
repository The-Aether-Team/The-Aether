package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.renderer.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.SliderGlowLayer;
import com.gildedgames.aether.client.renderer.entity.model.SliderModel;
import com.gildedgames.aether.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
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
    protected void setupRotations(@Nonnull Slider slider, @Nonnull PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        poseStack.mulPose(new Vector3f(slider.getHurtAngleX(), 0.0F, -slider.getHurtAngleZ()).rotationDegrees(slider.getHurtAngle() * -30.0F));
        if (slider.getHurtAngle() > 0.0) {
            slider.setHurtAngle(slider.getHurtAngle() * 0.94F);
        }
        super.setupRotations(slider, poseStack, ageInTicks, rotationYaw, partialTicks);
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
