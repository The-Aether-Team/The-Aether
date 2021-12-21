package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.SentryLayer;
import com.gildedgames.aether.common.entity.monster.dungeon.SentryEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SentryRenderer extends MobRenderer<SentryEntity, SlimeModel<SentryEntity>> {

	private static final ResourceLocation SENTRY_TEXTURES = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry.png");
	private static final ResourceLocation SENTRY_TEXTURES_LIT = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry_lit.png");
	
	public SentryRenderer(EntityRendererProvider.Context renderer) {
		super(renderer, new SlimeModel<>(renderer.bakeLayer(AetherModelLayers.SENTRY)), 0.3F);
		this.addLayer(new SentryLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(SentryEntity entity) {
		return entity.isAwake()? SENTRY_TEXTURES_LIT : SENTRY_TEXTURES;
	}

	@Override
	protected void scale(SentryEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
//		GL11.glScalef(1.75F, 1.75F, 1.75F);
		float f = 0.879F;
		matrixStackIn.scale(f, f, f);
		float f1 = entitylivingbaseIn.getSize();
		float f2 = 0.0F;// /*MathHelper.lerp(partialTickTime, entitylivingbaseIn.prevSquishFactor, entitylivingbaseIn.squishFactor)*/ / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		matrixStackIn.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}
	
}
