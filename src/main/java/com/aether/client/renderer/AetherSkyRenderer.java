package com.aether.client.renderer;

import java.util.Random;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.SkyRenderHandler;

public class AetherSkyRenderer implements SkyRenderHandler {
	private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("textures/environment/sun.png");

	private final VertexFormat skyVertexFormat = DefaultVertexFormats.POSITION;
	@Nullable
	private VertexBuffer starVBO, skyVBO, sky2VBO;

	public AetherSkyRenderer() {
		this.generateStars();
		this.generateSky();
		this.generateSky2();
	}

	private void generateStars() {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		if (this.starVBO != null) {
			this.starVBO.close();
		}

		this.starVBO = new VertexBuffer(this.skyVertexFormat);
		this.renderStars(bufferbuilder);
		bufferbuilder.finishDrawing();
		this.starVBO.upload(bufferbuilder);
	}

	private void generateSky() {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		if (this.skyVBO != null) {
			this.skyVBO.close();
		}

		this.skyVBO = new VertexBuffer(this.skyVertexFormat);
		this.renderSky(bufferbuilder, 16.0F, false);
		bufferbuilder.finishDrawing();
		this.skyVBO.upload(bufferbuilder);
	}

	private void generateSky2() {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		if (this.sky2VBO != null) {
			this.sky2VBO.close();
		}

		this.sky2VBO = new VertexBuffer(this.skyVertexFormat);
		this.renderSky(bufferbuilder, -16.0F, true);
		bufferbuilder.finishDrawing();
		this.sky2VBO.upload(bufferbuilder);
	}

	private void renderStars(BufferBuilder bufferBuilderIn) {
		Random random = new Random(10842L);
		bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);

		for (int i = 0; i < 1500; ++i) {
			double d0 = random.nextFloat() * 2.0F - 1.0F;
			double d1 = random.nextFloat() * 2.0F - 1.0F;
			double d2 = random.nextFloat() * 2.0F - 1.0F;
			double d3 = 0.15F + random.nextFloat() * 0.1F;
			double d4 = d0 * d0 + d1 * d1 + d2 * d2;
			if (d4 < 1.0 && d4 > 0.01) {
				d4 = 1.0 / Math.sqrt(d4);
				d0 = d0 * d4;
				d1 = d1 * d4;
				d2 = d2 * d4;
				double d5 = d0 * 100.0;
				double d6 = d1 * 100.0;
				double d7 = d2 * 100.0;
				double d8 = Math.atan2(d0, d2);
				double d9 = Math.sin(d8);
				double d10 = Math.cos(d8);
				double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
				double d12 = Math.sin(d11);
				double d13 = Math.cos(d11);
				double d14 = random.nextDouble() * Math.PI * 2.0;
				double d15 = Math.sin(d14);
				double d16 = Math.cos(d14);

				for (int j = 0; j < 4; ++j) {
					double d17 = 0.0;
					double d18 = ((j & 2) - 1) * d3;
					double d19 = ((j + 1 & 2) - 1) * d3;
					double d20 = 0.0;
					double d21 = d18 * d16 - d19 * d15;
					double d22 = d19 * d16 + d18 * d15;
					double d23 = d21 * d12 + 0.0 * d13;
					double d24 = 0.0 * d12 - d21 * d13;
					double d25 = d24 * d9 - d22 * d10;
					double d26 = d22 * d9 + d24 * d10;
					bufferBuilderIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
				}
			}
		}

	}

	private void renderSky(BufferBuilder bufferBuilderIn, float posY, boolean reverseX) {
		int i = 64;
		int j = 6;
		bufferBuilderIn.begin(7, DefaultVertexFormats.POSITION);

		for (int k = -384; k <= 384; k += 64) {
			for (int l = -384; l <= 384; l += 64) {
				float f = k;
				float f1 = k + 64;
				if (reverseX) {
					f1 = k;
					f = k + 64;
				}

				bufferBuilderIn.pos(f, posY, l).endVertex();
				bufferBuilderIn.pos(f1, posY, l).endVertex();
				bufferBuilderIn.pos(f1, posY, l + 64).endVertex();
				bufferBuilderIn.pos(f, posY, l + 64).endVertex();
			}
		}

	}
	
	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void render(int ticks, float partialTicks, MatrixStack matrixStackIn, ClientWorld world, Minecraft mc) {
		TextureManager textureManager = mc.getTextureManager();
		RenderSystem.disableTexture();
		Vec3d vec3d = world.getSkyColor(mc.gameRenderer.getActiveRenderInfo().getBlockPos(), partialTicks);
		float f = (float)vec3d.x;
		float f1 = (float)vec3d.y;
		float f2 = (float)vec3d.z;
		FogRenderer.applyFog();
		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.depthMask(false);
		RenderSystem.enableFog();
		RenderSystem.color3f(f, f1, f2);
		this.skyVBO.bindBuffer();
		this.skyVertexFormat.setupBufferState(0L);
		this.skyVBO.draw(matrixStackIn.getLast().getMatrix(), 7);
		VertexBuffer.unbindBuffer();
		this.skyVertexFormat.clearBufferState();
		RenderSystem.disableFog();
		RenderSystem.disableAlphaTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		float[] afloat = world.dimension.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
		if (afloat != null) {
			RenderSystem.disableTexture();
			RenderSystem.shadeModel(7425);
			matrixStackIn.push();
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
			float f3 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F? 180.0F : 0.0F;
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(f3));
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
			float f4 = afloat[0];
			float f5 = afloat[1];
			float f6 = afloat[2];
			Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
			bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
			bufferbuilder.pos(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
			int i = 16;

			for (int j = 0; j <= 16; ++j) {
				float f7 = j * ((float)Math.PI * 2.0F) / 16.0F;
				float f8 = MathHelper.sin(f7);
				float f9 = MathHelper.cos(f7);
				bufferbuilder.pos(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3])
					.color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
			}

			bufferbuilder.finishDrawing();
			WorldVertexBufferUploader.draw(bufferbuilder);
			matrixStackIn.pop();
			RenderSystem.shadeModel(7424);
		}

		RenderSystem.enableTexture();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		matrixStackIn.push();
		float f11 = 1.0F - world.getRainStrength(partialTicks);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, f11);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90.0F));
		matrixStackIn.rotate(Vector3f.XP.rotationDegrees(world.getCelestialAngle(partialTicks) * 360.0F));
		Matrix4f matrix4f1 = matrixStackIn.getLast().getMatrix();
		float f12 = 30.0F;
		textureManager.bindTexture(SUN_TEXTURES);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(matrix4f1, -f12, 100.0F, -f12).tex(0.0F, 0.0F).endVertex();
		bufferbuilder.pos(matrix4f1, f12, 100.0F, -f12).tex(1.0F, 0.0F).endVertex();
		bufferbuilder.pos(matrix4f1, f12, 100.0F, f12).tex(1.0F, 1.0F).endVertex();
		bufferbuilder.pos(matrix4f1, -f12, 100.0F, f12).tex(0.0F, 1.0F).endVertex();
		bufferbuilder.finishDrawing();
		WorldVertexBufferUploader.draw(bufferbuilder);
		f12 = 20.0F;
		textureManager.bindTexture(MOON_PHASES_TEXTURES);
		int k = world.getMoonPhase();
		int l = k % 4;
		int i1 = k / 4 % 2;
		float f13 = (l + 0) / 4.0F;
		float f14 = (i1 + 0) / 2.0F;
		float f15 = (l + 1) / 4.0F;
		float f16 = (i1 + 1) / 2.0F;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(matrix4f1, -f12, -100.0F, f12).tex(f15, f16).endVertex();
		bufferbuilder.pos(matrix4f1, f12, -100.0F, f12).tex(f13, f16).endVertex();
		bufferbuilder.pos(matrix4f1, f12, -100.0F, -f12).tex(f13, f14).endVertex();
		bufferbuilder.pos(matrix4f1, -f12, -100.0F, -f12).tex(f15, f14).endVertex();
		bufferbuilder.finishDrawing();
		WorldVertexBufferUploader.draw(bufferbuilder);
		RenderSystem.disableTexture();
		float f10 = world.getStarBrightness(partialTicks) * f11;
		if (f10 > 0.0F) {
			RenderSystem.color4f(f10, f10, f10, f10);
			this.starVBO.bindBuffer();
			this.skyVertexFormat.setupBufferState(0L);
			this.starVBO.draw(matrixStackIn.getLast().getMatrix(), 7);
			VertexBuffer.unbindBuffer();
			this.skyVertexFormat.clearBufferState();
		}

		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableFog();
		matrixStackIn.pop();
		RenderSystem.disableTexture();
		
		float red, green, blue;

		if (world.dimension.isSkyColored()) {
			red = f * 0.2F + 0.04F;
			green = f1 * 0.2F + 0.04F;
			blue = f2 * 0.6F + 0.1F;
		}
		else {
			red = f;
			green = f1;
			blue = f2;
		}
		
		RenderSystem.color3f(red, green, blue);

		RenderSystem.enableTexture();
		RenderSystem.depthMask(true);
		RenderSystem.disableFog();
	}

}
