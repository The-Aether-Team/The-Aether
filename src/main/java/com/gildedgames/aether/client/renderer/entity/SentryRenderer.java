package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherModelLayers;
import com.gildedgames.aether.client.renderer.entity.layers.SentryLayer;
import com.gildedgames.aether.common.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class SentryRenderer extends MobRenderer<Sentry, SlimeModel<Sentry>> {
	private static final ResourceLocation SENTRY_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry.png");
	private static final ResourceLocation SENTRY_LIT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry_lit.png");
	
	public SentryRenderer(EntityRendererProvider.Context context) {
		super(context, new SlimeModel<>(context.bakeLayer(AetherModelLayers.SENTRY)), 0.3F);
		this.addLayer(new SentryLayer<>(this));
	}

	@Override
	protected void scale(Sentry sentry, PoseStack poseStack, float partialTickTime) {
		float f = 0.879F;
		poseStack.scale(f, f, f);
		float f1 = sentry.getSize() + 1.0F;
		float f2 = 0.0F;
		float f3 = 1.0F / (f2 + 1.0F);
		poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}

	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(Sentry sentry) {
		return sentry.isAwake() ? SENTRY_LIT_TEXTURE : SENTRY_TEXTURE;
	}
}
