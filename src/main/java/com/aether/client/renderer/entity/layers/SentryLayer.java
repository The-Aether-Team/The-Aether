package com.aether.client.renderer.entity.layers;

import com.aether.Aether;
import com.aether.entity.monster.SentryEntity;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SentryLayer extends LayerRenderer<SentryEntity, SlimeModel<SentryEntity>> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/sentry/eye.png");
	
	private final SlimeModel<SentryEntity> slimeModel = new SlimeModel<>(0);
	
	public SentryLayer(IEntityRenderer<SentryEntity, SlimeModel<SentryEntity>> entityRendererIn) {
		super(entityRendererIn);
	}
	
	@Override
	public void render(SentryEntity sentry, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!sentry.isAwake()) {
			return;
		}
		
		this.bindTexture(TEXTURE);
		
		GlStateManager.enableBlend();
		GlStateManager.disableAlphaTest();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		
		GlStateManager.depthMask(!sentry.isInvisible());
		
		int i = 61680;
		int j = i % 65536;
		int k = i / 65536;
		GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, j, k);
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		GlStateManager.translated(0.0, 0.0, -0.001);
		this.slimeModel.render(sentry, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		i = sentry.getBrightnessForRender();
		j = i % 65536;
		k = i % 65536;
		GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, j, k);
		GlStateManager.disableBlend();
		GlStateManager.enableAlphaTest();
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

}
