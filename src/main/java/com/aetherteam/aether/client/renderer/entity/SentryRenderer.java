package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.SentryGlowLayer;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SentryRenderer extends MobRenderer<Sentry, SlimeModel<Sentry>> {
	private static final ResourceLocation SENTRY_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry.png");
	private static final ResourceLocation SENTRY_LIT_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sentry/sentry_lit.png");
	
	public SentryRenderer(EntityRendererProvider.Context context) {
		super(context, new SlimeModel<>(context.bakeLayer(AetherModelLayers.SENTRY)), 0.3F);
		this.addLayer(new SentryGlowLayer(this));
	}

	/**
	 * Scales the Sentry according to its size.
	 * @param sentry The {@link Sentry} entity.
	 * @param poseStack The rendering {@link PoseStack}.
	 * @param partialTicks The {@link Float} for the game's partial ticks.
	 */
	@Override
	protected void scale(Sentry sentry, PoseStack poseStack, float partialTicks) {
		float f = 0.879F;
		poseStack.scale(f, f, f);
		float f1 = sentry.getSize() + 1.0F;
		float f2 = 0.0F;
		float f3 = 1.0F / (f2 + 1.0F);
		poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}

	@Override
	public ResourceLocation getTextureLocation(Sentry sentry) {
		return sentry.isAwake() ? SENTRY_LIT_TEXTURE : SENTRY_TEXTURE;
	}
}
