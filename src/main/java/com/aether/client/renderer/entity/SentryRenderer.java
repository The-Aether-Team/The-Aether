package com.aether.client.renderer.entity;

import com.aether.Aether;
import com.aether.client.renderer.entity.layers.SentryLayer;
import com.aether.entity.monster.SentryEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SentryRenderer extends MobRenderer<SentryEntity, SlimeModel<SentryEntity>> {

	private static final ResourceLocation SENTRY_TEXTURES     = new ResourceLocation(Aether.MODID, "textures/entity/sentry/sentry.png");
	private static final ResourceLocation SENTRY_TEXTURES_LIT = new ResourceLocation(Aether.MODID, "textures/entity/sentry/sentry_lit.png");
	
	public SentryRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new SlimeModel<>(0), 0.3F);
		this.addLayer(new SentryLayer<>(this));
	}

	@Override
	public ResourceLocation getEntityTexture(SentryEntity entity) {
		return entity.isAwake()? SENTRY_TEXTURES_LIT : SENTRY_TEXTURES;
	}
	
	@Override
	protected void preRenderCallback(SentryEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
//		GL11.glScalef(1.75F, 1.75F, 1.75F);
		float f = 0.879F;
		matrixStackIn.scale(f, f, f);
		float f1 = entitylivingbaseIn.getSlimeSize();
		float f2 = 0.0F;// /*MathHelper.lerp(partialTickTime, entitylivingbaseIn.prevSquishFactor, entitylivingbaseIn.squishFactor)*/ / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		matrixStackIn.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}
	
}
