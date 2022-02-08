package com.gildedgames.aether.client.world;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IAetherTime;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.BufferUploader;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
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
import net.minecraftforge.common.util.LazyOptional;

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
    public void render(int ticks, float pPartialTick, PoseStack pPoseStack, ClientLevel world, Minecraft mc) { 
        Matrix4f pProjectionMatrix = RenderSystem.getProjectionMatrix();
        float renderDistance = mc.gameRenderer.getRenderDistance();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();
        boolean shouldRender = world.effects().isFoggyAt(Mth.floor(camPos.x), Mth.floor(camPos.y)) || mc.gui.getBossOverlay().shouldCreateWorldFog();

        RenderSystem.disableTexture();
        Vec3 vec3 = world.getSkyColor(camera.getPosition(), pPartialTick);
        float f = (float) vec3.x;
        float f1 = (float) vec3.y;
        float f2 = (float) vec3.z;
        FogRenderer.levelFogColor();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(f, f1, f2, 1.0F);
        ShaderInstance shaderinstance = RenderSystem.getShader();
        this.skyBuffer.drawWithShader(pPoseStack.last().pose(), pProjectionMatrix, shaderinstance);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float[] sunRiseRGBA = world.effects().getSunriseColor(world.getTimeOfDay(pPartialTick), pPartialTick);
        if (sunRiseRGBA != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableTexture();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            pPoseStack.pushPose();
            pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            float f3 = Mth.sin(world.getSunAngle(pPartialTick)) < 0.0F ? 180.0F : 0.0F;
            pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(f3));
            pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            float f4 = sunRiseRGBA[0];
            float f5 = sunRiseRGBA[1];
            float f6 = sunRiseRGBA[2];
            Matrix4f matrix4f = pPoseStack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, sunRiseRGBA[3]).endVertex();
            int i = 16;

            for (int j = 0; j <= 16; ++j) {
                float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
                float f8 = Mth.sin(f7);
                float f9 = Mth.cos(f7);
                bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * sunRiseRGBA[3]).color(sunRiseRGBA[0], sunRiseRGBA[1], sunRiseRGBA[2], 0.0F).endVertex();
            }

            bufferbuilder.end();
            BufferUploader.end(bufferbuilder);
            pPoseStack.popPose();
        }

        RenderSystem.enableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        pPoseStack.pushPose();

        this.drawCelestialBodies(pPartialTick, pPoseStack, world, bufferbuilder);

        RenderSystem.disableTexture();
        float f10 = world.getStarBrightness(pPartialTick);
        if (f10 > 0.0F) {
            RenderSystem.setShaderColor(f10, f10, f10, f10);
            FogRenderer.setupNoFog();
            this.starBuffer.drawWithShader(pPoseStack.last().pose(), pProjectionMatrix, GameRenderer.getPositionShader());
            FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_SKY, renderDistance, shouldRender, pPartialTick);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        pPoseStack.popPose();
        RenderSystem.disableTexture();
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);

        if (world.effects().hasGround()) {
            RenderSystem.setShaderColor(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0F);
        } else {
            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
        }

        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
    }

    /**
     * This method is used to draw the sun and/or moon in the Aether.
     */
    private void drawCelestialBodies(float pPartialTick, PoseStack pPoseStack, ClientLevel world, BufferBuilder bufferbuilder) {
        // This code determines the current angle of the sun and moon and determines whether they should be visible or not.
        IAetherTime aetherTime = world.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).orElse(null);
        long dayTime = aetherTime.getDayTime() % 72000L;
        float sunOpacity;
        float moonOpacity;
        if (dayTime > 71400L) {
            dayTime -= 71400L;
            sunOpacity = Math.min(dayTime * 0.001666666667F, 1F);
            moonOpacity = Math.max(1.0F - dayTime * 0.001666666667F, 0F);
        } else if (dayTime > 38400L) {
            dayTime -= 38400L;
            sunOpacity = Math.max(1.0F - dayTime * 0.001666666667F, 0F);
            moonOpacity = Math.min(dayTime * 0.001666666667F, 1F);
        } else {
            sunOpacity = 1.0F;
            moonOpacity = 0.0F;
        }
        sunOpacity -= world.getRainLevel(pPartialTick);
        moonOpacity -= world.getRainLevel(pPartialTick);

        //Render celestial bodies
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(world.getTimeOfDay(pPartialTick) * 360.0F));
        Matrix4f matrix4f1 = pPoseStack.last().pose();
        float celestialOffset = 30.0F;

        // Render the sun
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, sunOpacity);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SUN_LOCATION);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -celestialOffset, 100.0F, -celestialOffset).uv(0.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, celestialOffset, 100.0F, -celestialOffset).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, celestialOffset, 100.0F, celestialOffset).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, -celestialOffset, 100.0F, celestialOffset).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);

        // Render the moon
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, moonOpacity);
        celestialOffset = 20.0F;
        RenderSystem.setShaderTexture(0, MOON_LOCATION);
        int moonPhase = world.getMoonPhase();
        int textureX = moonPhase % 4;
        int textureY = moonPhase / 4 % 2;
        float uLeft = (float) (textureX) / 4.0F;
        float vDown = (float) (textureY) / 2.0F;
        float uRight = (float) (textureX + 1) / 4.0F;
        float vUp = (float) (textureY + 1) / 2.0F;
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -celestialOffset, -100.0F, celestialOffset).uv(uRight, vUp).endVertex();
        bufferbuilder.vertex(matrix4f1, celestialOffset, -100.0F, celestialOffset).uv(uLeft, vUp).endVertex();
        bufferbuilder.vertex(matrix4f1, celestialOffset, -100.0F, -celestialOffset).uv(uLeft, vDown).endVertex();
        bufferbuilder.vertex(matrix4f1, -celestialOffset, -100.0F, -celestialOffset).uv(uRight, vDown).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }

    private void createLightSky() {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        if (this.skyBuffer != null) {
            this.skyBuffer.close();
        }

        this.skyBuffer = new VertexBuffer();
        this.drawSkyHemisphere(bufferbuilder, 16.0F);
        bufferbuilder.end();
        this.skyBuffer.upload(bufferbuilder);
    }

    private void drawSkyHemisphere(BufferBuilder pBuilder, float pY) {
        float f = Math.signum(pY) * 512.0F;
        RenderSystem.setShader(GameRenderer::getPositionShader);
        pBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
        pBuilder.vertex(0.0D, (double) pY, 0.0D).endVertex();

        for (int i = -180; i <= 180; i += 45) {
            pBuilder.vertex((double) (f * Mth.cos((float) i * ((float) Math.PI / 180F))), (double) pY, (double) (512.0F * Mth.sin((float) i * ((float) Math.PI / 180F)))).endVertex();
        }
    }

    private void createStars() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (this.starBuffer != null) {
            this.starBuffer.close();
        }

        this.starBuffer = new VertexBuffer();
        this.drawStars(bufferbuilder);
        bufferbuilder.end();
        this.starBuffer.upload(bufferbuilder);
    }

    private void drawStars(BufferBuilder pBuilder) {
        Random random = new Random(10842L);
        pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (int i = 0; i < 1500; ++i) {
            double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
            double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
            double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
            double d3 = (double) (0.15F + random.nextFloat() * 0.1F);
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d4 < 1.0D && d4 > 0.01D) {
                d4 = 1.0D / Math.sqrt(d4);
                d0 *= d4;
                d1 *= d4;
                d2 *= d4;
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

                for (int j = 0; j < 4; ++j) {
                    double d17 = 0.0D;
                    double d18 = (double) ((j & 2) - 1) * d3;
                    double d19 = (double) ((j + 1 & 2) - 1) * d3;
                    double d20 = 0.0D;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0D * d13;
                    double d24 = 0.0D * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    pBuilder.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
                }
            }
        }
    }
}
