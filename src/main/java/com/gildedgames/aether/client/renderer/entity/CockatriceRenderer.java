package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.CockatriceMarkingsLayer;
import com.gildedgames.aether.client.renderer.entity.model.CockatriceModel;
import com.gildedgames.aether.common.entity.monster.CockatriceEntity;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class CockatriceRenderer extends MobRenderer<CockatriceEntity, CockatriceModel>
{
    private static final ResourceLocation COCKATRICE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice.png");

    public CockatriceRenderer(EntityRendererProvider.Context context) {
        super(context, new CockatriceModel(context.bakeLayer(AetherModelLayers.COCKATRICE)), 0.7F);
        this.addLayer(new CockatriceMarkingsLayer<>(this));
    }

    @Override
    public void render(@Nonnull CockatriceEntity cockatrice, float entityYaw, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        super.render(cockatrice, entityYaw, partialTicks, poseStack, buffer, packedLight);
        this.model.setupWingsAnimation(cockatrice);
    }

    @Override
	protected void scale(@Nonnull CockatriceEntity cockatrice, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.8F, 1.8F, 1.8F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull CockatriceEntity entity) {
        return COCKATRICE_TEXTURE;
    }
}
