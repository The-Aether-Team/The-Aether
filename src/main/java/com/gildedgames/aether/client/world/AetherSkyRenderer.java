package com.gildedgames.aether.client.world;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.BufferUploader;
import net.minecraft.client.renderer.texture.TextureManager;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Matrix4f;
import net.minecraft.world.phys.Vec3;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ISkyRenderHandler;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class AetherSkyRenderer implements ISkyRenderHandler {
    private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");

    private final VertexFormat skyFormat = DefaultVertexFormat.POSITION;
    private VertexBuffer starBuffer, skyBuffer;

    public AetherSkyRenderer() {
        createLightSky();
        createStars();
    }

    @Override
    public void render(int ticks, float partialTicks, PoseStack matrixStack, ClientLevel world, Minecraft mc) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        RenderSystem.disableTexture();
        Vec3 worldSkyColor = world.getSkyColor(Minecraft.getInstance().gameRenderer.getMainCamera().getBlockPosition(), partialTicks);
        float x = (float)worldSkyColor.x;
        float y = (float)worldSkyColor.y;
        float z = (float)worldSkyColor.z;
        FogRenderer.levelFogColor();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.depthMask(false);
        RenderSystem.enableFog();
        RenderSystem.color3f(x, y, z);
        this.skyBuffer.bind();
        this.skyFormat.setupBufferState(0L);
        this.skyBuffer.draw(matrixStack.last().pose(), 7);
        VertexBuffer.unbind();
        this.skyFormat.clearBufferState();
        RenderSystem.disableFog();
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        float[] sunRiseRGBA = world.effects().getSunriseColor(world.getTimeOfDay(partialTicks), partialTicks);
        if (sunRiseRGBA != null) {
            RenderSystem.disableTexture();
            RenderSystem.shadeModel(7425);
            matrixStack.pushPose();
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            float celestialAngle = Mth.sin(world.getSunAngle(partialTicks)) < 0.0F ? 180.0F : 0.0F;
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(celestialAngle));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            float sunRed = sunRiseRGBA[0];
            float sunGreen = sunRiseRGBA[1];
            float sunBlue = sunRiseRGBA[2];
            Matrix4f matrix4f = matrixStack.last().pose();
            bufferbuilder.begin(6, DefaultVertexFormat.POSITION_COLOR);
            bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(sunRed, sunGreen, sunBlue, sunRiseRGBA[3]).endVertex();
            int i = 16;

            for(int j = 0; j <= 16; ++j) {
                float f7 = (float)j * ((float)Math.PI * 2F) / 16.0F;
                float f8 = Mth.sin(f7);
                float f9 = Mth.cos(f7);
                bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * sunRiseRGBA[3]).color(sunRiseRGBA[0], sunRiseRGBA[1], sunRiseRGBA[2], 0.0F).endVertex();
            }

            bufferbuilder.end();
            BufferUploader.end(bufferbuilder);
            matrixStack.popPose();
            RenderSystem.shadeModel(7424);
        }

        RenderSystem.enableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        matrixStack.pushPose();
        /**
         * This code determines the current angle of the sun and moon and determines whether they should be visible or not.
         */
        float timeFactor = world.getDayTime() % 72000;
        float sunOpacity = 1.0F;
        float moonOpacity = 0.0F;
        if (timeFactor > 71400) {
            timeFactor -= 71400;
            sunOpacity = timeFactor * 0.005F;
            moonOpacity = 1.0F - timeFactor * 0.005F;
        }
        else if (timeFactor > 38400) {
            timeFactor -= 38400;
            sunOpacity = 1.0F - timeFactor * 0.005F;
            moonOpacity = timeFactor * 0.005F;
        }

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, sunOpacity);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(world.getTimeOfDay(partialTicks) * 360.0F));
        Matrix4f matrix4f1 = matrixStack.last().pose();

        float f12;
        f12 = 30.0F;
        textureManager.bind(SUN_LOCATION);
        bufferbuilder.begin(7, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, moonOpacity);
        f12 = 20.0F;
        textureManager.bind(MOON_LOCATION);
        int k = world.getMoonPhase();
        int l = k % 4;
        int i1 = k / 4 % 2;
        float f13 = (float) (l + 0) / 4.0F;
        float f14 = (float) (i1 + 0) / 2.0F;
        float f15 = (float) (l + 1) / 4.0F;
        float f16 = (float) (i1 + 1) / 2.0F;
        bufferbuilder.begin(7, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
        bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
        bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
        bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
        RenderSystem.disableTexture();

        float starBrightness = world.getStarBrightness(partialTicks);
        if (starBrightness > 0.0F) {
            RenderSystem.color4f(starBrightness, starBrightness, starBrightness, starBrightness);
            this.starBuffer.bind();
            this.skyFormat.setupBufferState(0L);
            this.starBuffer.draw(matrixStack.last().pose(), 7);
            VertexBuffer.unbind();
            this.skyFormat.clearBufferState();
        }

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableFog();
        matrixStack.popPose();
        RenderSystem.disableTexture();
        RenderSystem.color3f(0.0F, 0.0F, 0.0F);

        if (world.effects().hasGround()) {
            RenderSystem.color3f(x * 0.2F + 0.04F, y * 0.2F + 0.04F, z * 0.6F + 0.1F);
        } else {
            RenderSystem.color3f(x, y, z);
        }

        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
        RenderSystem.disableFog();
    }

    private void createLightSky() {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        if (this.skyBuffer != null) {
            this.skyBuffer.close();
        }

        this.skyBuffer = new VertexBuffer(this.skyFormat);
        this.drawSkyHemisphere(bufferbuilder, 16.0F, false);
        bufferbuilder.end();
        this.skyBuffer.upload(bufferbuilder);
    }

    private void drawSkyHemisphere(BufferBuilder p_174968_1_, float p_174968_2_, boolean p_174968_3_) {
        int i = 64;
        int j = 6;
        p_174968_1_.begin(7, DefaultVertexFormat.POSITION);

        for(int k = -384; k <= 384; k += 64) {
            for(int l = -384; l <= 384; l += 64) {
                float f = (float)k;
                float f1 = (float)(k + 64);
                if (p_174968_3_) {
                    f1 = (float)k;
                    f = (float)(k + 64);
                }

                p_174968_1_.vertex((double)f, (double)p_174968_2_, (double)l).endVertex();
                p_174968_1_.vertex((double)f1, (double)p_174968_2_, (double)l).endVertex();
                p_174968_1_.vertex((double)f1, (double)p_174968_2_, (double)(l + 64)).endVertex();
                p_174968_1_.vertex((double)f, (double)p_174968_2_, (double)(l + 64)).endVertex();
            }
        }

    }

    private void createStars() {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        if (this.starBuffer != null) {
            this.starBuffer.close();
        }

        this.starBuffer = new VertexBuffer(this.skyFormat);
        this.drawStars(bufferbuilder);
        bufferbuilder.end();
        this.starBuffer.upload(bufferbuilder);
    }

    private void drawStars(BufferBuilder p_180444_1_) {
        Random random = new Random(10842L);
        p_180444_1_.begin(7, DefaultVertexFormat.POSITION);

        for(int i = 0; i < 1500; ++i) {
            double d0 = (double)(random.nextFloat() * 2.0F - 1.0F);
            double d1 = (double)(random.nextFloat() * 2.0F - 1.0F);
            double d2 = (double)(random.nextFloat() * 2.0F - 1.0F);
            double d3 = (double)(0.15F + random.nextFloat() * 0.1F);
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d4 < 1.0D && d4 > 0.01D) {
                d4 = 1.0D / Math.sqrt(d4);
                d0 = d0 * d4;
                d1 = d1 * d4;
                d2 = d2 * d4;
                double d5 = d0 * 100.0D;
                double d6 = d1 * 100.0D;
                double d7 = d2 * 100.0D;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                for(int j = 0; j < 4; ++j) {
                    double d17 = 0.0D;
                    double d18 = (double)((j & 2) - 1) * d3;
                    double d19 = (double)((j + 1 & 2) - 1) * d3;
                    double d20 = 0.0D;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0D * d13;
                    double d24 = 0.0D * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    p_180444_1_.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
                }
            }
        }

    }
}
