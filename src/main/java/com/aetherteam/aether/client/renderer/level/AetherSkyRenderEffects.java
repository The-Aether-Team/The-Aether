package com.aetherteam.aether.client.renderer.level;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.mixin.mixins.client.accessor.LevelRendererAccessor;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class AetherSkyRenderEffects extends DimensionSpecialEffects {
    private static final ResourceLocation CLOUDS_LOCATION = new ResourceLocation("textures/environment/clouds.png");
    private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");

    private final DimensionSpecialEffects OVERWORLD = new DimensionSpecialEffects.OverworldEffects();

    private final float[] sunriseCol = new float[4];

    private int prevCloudX = Integer.MIN_VALUE;
    private int prevCloudY = Integer.MIN_VALUE;
    private int prevCloudZ = Integer.MIN_VALUE;
    private Vec3 prevCloudColor = Vec3.ZERO;

    public AetherSkyRenderEffects() {
        super(9.5F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
    }

    /**
     * [CODE COPY] - {@link LightTexture#updateLightTexture(float)}.<br><br>
     * Copied from a specific section, and modified to make the lightmap not be tinted with any color. Checks for {@link AetherConfig.Client#colder_lightmap}.
     */
    @Override
    public void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken, float skyLight, float blockLight, int pixelX, int pixelY, Vector3f colors) {
        if (AetherConfig.CLIENT.colder_lightmap.get()) {
            Vector3f vector3f = (new Vector3f(skyDarken, skyDarken, 1.0F)).lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            Vector3f vector3f1 = new Vector3f();
            float f9 = LightTexture.getBrightness(level.dimensionType(), pixelX) * skyLight;
            float f10 = f9 * (f9 * f9 * 0.6F + 0.4F);
            vector3f1.set(f10, f10, f10); // Balance out RGB values instead of leaning the tint towards being warm colored.
            boolean flag = level.effects().forceBrightLightmap();
            if (flag) {
                vector3f1.lerp(new Vector3f(0.99F, 1.12F, 1.0F), 0.25F);
                AetherSkyRenderEffects.clampColor(vector3f1);
            } else {
                Vector3f vector3f2 = (new Vector3f(vector3f)).mul(blockLight);
                vector3f1.add(vector3f2);
                vector3f1.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                if (Minecraft.getInstance().gameRenderer.getDarkenWorldAmount(partialTicks) > 0.0F) {
                    float darken = Minecraft.getInstance().gameRenderer.getDarkenWorldAmount(partialTicks);
                    Vector3f vector3f3 = (new Vector3f(vector3f1)).mul(0.7F, 0.6F, 0.6F);
                    vector3f1.lerp(vector3f3, darken);
                }
            }
            colors.set(vector3f1);
        }
    }

    /**
     * [CODE COPY] - {@link LightTexture#clampColor(Vector3f)}.
     */
    private static void clampColor(Vector3f vector) {
        vector.set(Mth.clamp(vector.x(), 0.0F, 1.0F), Mth.clamp(vector.y(), 0.0F, 1.0F), Mth.clamp(vector.z(), 0.0F, 1.0F));
    }

    /**
     * [CODE COPY] - {@link DimensionSpecialEffects#getSunriseColor(float, float)}.<br><br>
     * Modified to have a config for different coloration if {@link AetherConfig.Client#green_sunset} is enabled,
     * along with slight changes to the coloration when it isn't.
     */
    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTicks) {
        if (AetherConfig.CLIENT.green_sunset.get()) {
            float f1 = Mth.cos(timeOfDay * Mth.TWO_PI) - 0.0F;
            if (f1 >= -0.4F && f1 <= 0.4F) {
                float f3 = (f1 + 0.0F) / 0.4F * 0.5F + 0.5F;
                float f4 = 1.0F - (1.0F - Mth.sin(f3 * Mth.PI)) * 0.99F;
                f4 *= f4;
                this.sunriseCol[0] = f3 * 0.5F + 0.0F; // Red
                this.sunriseCol[1] = f3 * f3 * 0.3F + 0.3F; // Green
                this.sunriseCol[2] = f3 * f3 * 0.5F + 0.3F; // Blue
                this.sunriseCol[3] = f4;
                return this.sunriseCol;
            } else {
                return null;
            }
        } else {
            float f1 = Mth.cos(timeOfDay * Mth.TWO_PI) - 0.0F;
            if (f1 >= -0.4F && f1 <= 0.4F) {
                float f3 = (f1 + 0.0F) / 0.4F * 0.5F + 0.5F;
                float f4 = 1.0F - (1.0F - Mth.sin(f3 * Mth.PI)) * 0.99F;
                f4 *= f4;
                this.sunriseCol[0] = f3 * 0.3F + 0.65F; // Red
                this.sunriseCol[1] = f3 * f3 * 0.7F + 0.25F; // Green
                this.sunriseCol[2] = f3 * f3 * 0.0F + 0.4F; // Blue
                this.sunriseCol[3] = f4;
                return this.sunriseCol;
            } else {
                return null;
            }
        }
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float brightness) {
        return OVERWORLD.getBrightnessDependentFogColor(color, brightness);
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return OVERWORLD.isFoggyAt(x, z);
    }

    /**
     * [CODE COPY] - {@link LevelRenderer#renderClouds(PoseStack, Matrix4f, float, double, double, double)}.
     */
    @Override
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix) {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        float cloudHeight = level.effects().getCloudHeight();
        if (!Float.isNaN(cloudHeight)) {
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.depthMask(true);
            double d1 = ((float) ticks + partialTick) * 0.03F;
            double d2 = (camX + d1) / 12.0;
            double d3 = cloudHeight - (float) camY + 0.33F;
            double d4 = camZ / 12.0 + (double) 0.33F;
            d2 -= Mth.floor(d2 / 2048.0) * 2048;
            d4 -= Mth.floor(d4 / 2048.0) * 2048;
            float f3 = (float) (d2 - (double) Mth.floor(d2));
            float f4 = (float) (d3 / 4.0 - (double) Mth.floor(d3 / 4.0)) * 4.0F;
            float f5 = (float) (d4 - (double) Mth.floor(d4));
            Vec3 vec3 = this.getCloudColor(level, partialTick);
            int i = Mth.floor(d2);
            int j = Mth.floor(d3 / 4.0);
            int k = Mth.floor(d4);
            if (i != this.prevCloudX || j != this.prevCloudY || k != this.prevCloudZ || Minecraft.getInstance().options.getCloudsType() != ((LevelRendererAccessor) levelRenderer).aether$getPrevCloudsType() || this.prevCloudColor.distanceToSqr(vec3) > 2.0E-4) {
                this.prevCloudX = i;
                this.prevCloudY = j;
                this.prevCloudZ = k;
                this.prevCloudColor = vec3;
                ((LevelRendererAccessor) levelRenderer).aether$setPrevCloudsType(Minecraft.getInstance().options.getCloudsType());
                ((LevelRendererAccessor) levelRenderer).aether$setGenerateClouds(true);
            }

            if (((LevelRendererAccessor) levelRenderer).aether$isGenerateClouds()) {
                ((LevelRendererAccessor) levelRenderer).aether$setGenerateClouds(false);
                BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                if (((LevelRendererAccessor) levelRenderer).aether$getCloudBuffer() != null) {
                    ((LevelRendererAccessor) levelRenderer).aether$getCloudBuffer().close();
                }
                ((LevelRendererAccessor) levelRenderer).aether$setCloudBuffer(new VertexBuffer());
                BufferBuilder.RenderedBuffer renderedBuffer = ((LevelRendererAccessor) levelRenderer).callBuildClouds(bufferbuilder, d2, d3, d4, vec3);
                ((LevelRendererAccessor) levelRenderer).aether$getCloudBuffer().bind();
                ((LevelRendererAccessor) levelRenderer).aether$getCloudBuffer().upload(renderedBuffer);
                VertexBuffer.unbind();
            }

            RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
            RenderSystem.setShaderTexture(0, CLOUDS_LOCATION);
            FogRenderer.levelFogColor();
            poseStack.pushPose();
            poseStack.scale(12.0F, 1.0F, 12.0F);
            poseStack.translate(-f3, f4, -f5);
            if (((LevelRendererAccessor) levelRenderer).aether$getCloudBuffer() != null && RenderSystem.getShader() != null) {
                ((LevelRendererAccessor) levelRenderer).aether$getCloudBuffer().bind();
                int l = ((LevelRendererAccessor) levelRenderer).aether$getPrevCloudsType() == CloudStatus.FANCY ? 0 : 1;

                for(int i1 = l; i1 < 2; ++i1) {
                    if (i1 == 0) {
                        RenderSystem.colorMask(false, false, false, false);
                    } else {
                        RenderSystem.colorMask(true, true, true, true);
                    }

                    ShaderInstance shaderInstance = RenderSystem.getShader();
                    ((LevelRendererAccessor) levelRenderer).aether$getCloudBuffer().drawWithShader(poseStack.last().pose(), projectionMatrix, shaderInstance);
                }

                VertexBuffer.unbind();
            }

            poseStack.popPose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
        }
        return true;
    }

    /**
     * [CODE COPY] - {@link ClientLevel#getCloudColor(float)}.<br><br>
     * Modified to have lighter cloud coloration than the Overworld during weather.
     */
    public Vec3 getCloudColor(ClientLevel level, float partialTick) {
        float f = level.getTimeOfDay(partialTick);
        float f1 = Mth.cos(f * Mth.TWO_PI) * 2.0F + 0.5F;
        f1 = Mth.clamp(f1, 0.0F, 1.0F);
        float f2 = 1.0F;
        float f3 = 1.0F;
        float f4 = 1.0F;
        float f5 = level.getRainLevel(partialTick);
        if (f5 > 0.0F) {
            float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.725F; // Modified darkening.
            float f7 = 1.0F - f5 * 0.8F;
            f2 = f2 * f7 + f6 * (1.0F - f7);
            f3 = f3 * f7 + f6 * (1.0F - f7);
            f4 = f4 * f7 + f6 * (1.0F - f7);
        }

        f2 *= f1 * 0.9F + 0.1F;
        f3 *= f1 * 0.9F + 0.1F;
        f4 *= f1 * 0.85F + 0.15F;
        float f9 = level.getThunderLevel(partialTick);
        if (f9 > 0.0F) {
            float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.5F; // Modified darkening.
            float f8 = 1.0F - f9 * 0.7F;
            f2 = f2 * f8 + f10 * (1.0F - f8);
            f3 = f3 * f8 + f10 * (1.0F - f8);
            f4 = f4 * f8 + f10 * (1.0F - f8);
        }

        return new Vec3(f2, f3, f4);
    }

    /**
     * [CODE COPY] - {@link LevelRenderer#renderSky(PoseStack, Matrix4f, float, Camera, boolean, Runnable)}.<br><br>
     * Modified to make the sun and moon fade out when they dip under the horizon.
     */
    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        if (RenderSystem.getShader() != null) {
            LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
            Vec3 vec3 = this.getSkyColor(level, camera.getPosition(), partialTick);
            float f = (float) vec3.x();
            float f1 = (float) vec3.y();
            float f2 = (float) vec3.z();
            FogRenderer.levelFogColor();
            BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
            ShaderInstance shaderInstance = RenderSystem.getShader();
            ((LevelRendererAccessor) levelRenderer).aether$getSkyBuffer().bind();
            ((LevelRendererAccessor) levelRenderer).aether$getSkyBuffer().drawWithShader(poseStack.last().pose(), projectionMatrix, shaderInstance);
            VertexBuffer.unbind();
            RenderSystem.enableBlend();
            float[] sunRiseRGBA = level.effects().getSunriseColor(level.getTimeOfDay(partialTick), partialTick);
            if (sunRiseRGBA != null) {
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.pushPose();
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                float f3 = Mth.sin(level.getSunAngle(partialTick)) < 0.0F ? 180.0F : 0.0F;
                poseStack.mulPose(Axis.ZP.rotationDegrees(f3));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                float f4 = sunRiseRGBA[0];
                float f5 = sunRiseRGBA[1];
                float f6 = sunRiseRGBA[2];
                Matrix4f matrix4f = poseStack.last().pose();
                bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
                bufferBuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, sunRiseRGBA[3]).endVertex();

                for (int j = 0; j <= 16; ++j) {
                    float f7 = j * Mth.TWO_PI / 16.0F;
                    float f8 = Mth.sin(f7);
                    float f9 = Mth.cos(f7);
                    bufferBuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * sunRiseRGBA[3]).color(sunRiseRGBA[0], sunRiseRGBA[1], sunRiseRGBA[2], 0.0F).endVertex();
                }

                BufferUploader.drawWithShader(bufferBuilder.end());
                poseStack.popPose();
            }

            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            poseStack.pushPose();

            this.drawCelestialBodies(partialTick, poseStack, level, bufferBuilder);

            float f10 = level.getStarBrightness(partialTick);
            if (f10 > 0.0F) {
                if (GameRenderer.getPositionShader() != null) {
                    RenderSystem.setShaderColor(f10, f10, f10, f10);
                    FogRenderer.setupNoFog();
                    ((LevelRendererAccessor) levelRenderer).aether$getStarBuffer().bind();
                    ((LevelRendererAccessor) levelRenderer).aether$getStarBuffer().drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
                    VertexBuffer.unbind();
                    setupFog.run();
                }
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            poseStack.popPose();

            RenderSystem.depthMask(true);
        }
        return true;
    }

    /**
     * [CODE COPY] - {@link ClientLevel#getSkyColor(Vec3, float)}.<br><br>
     * Modified to have lighter sky coloration than the Overworld during weather.
     */
    public Vec3 getSkyColor(ClientLevel level, Vec3 pos, float partialTick) {
        float f = level.getTimeOfDay(partialTick);
        Vec3 vec3 = pos.subtract(2.0, 2.0, 2.0).scale(0.25);
        BiomeManager biomeManager = level.getBiomeManager();
        Vec3 vec31 = CubicSampler.gaussianSampleVec3(vec3, (x, y, z) -> Vec3.fromRGB24(biomeManager.getNoiseBiomeAtQuart(x, y, z).value().getSkyColor()));
        float f1 = Mth.cos(f * Mth.TWO_PI) * 2.0F + 0.5F;
        f1 = Mth.clamp(f1, 0.0F, 1.0F);
        float f2 = (float) vec31.x() * f1;
        float f3 = (float) vec31.y() * f1;
        float f4 = (float) vec31.z() * f1;
        float f5 = level.getRainLevel(partialTick);
        if (f5 > 0.0F) {
            float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.61F; // Modified darkening.
            float f7 = 1.0F - f5 * 0.2F;
            f2 = f2 * f7 + f6 * (1.0F - f7);
            f3 = f3 * f7 + f6 * (1.0F - f7);
            f4 = f4 * f7 + f6 * (1.0F - f7);
        }

        float f9 = level.getThunderLevel(partialTick);
        if (f9 > 0.0F) {
            float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.48F; // Modified darkening.
            float f8 = 1.0F - f9 * 0.21F;
            f2 = f2 * f8 + f10 * (1.0F - f8);
            f3 = f3 * f8 + f10 * (1.0F - f8);
            f4 = f4 * f8 + f10 * (1.0F - f8);
        }

        if (!Minecraft.getInstance().options.hideLightningFlash().get() && level.getSkyFlashTime() > 0) {
            float f11 = (float)level.getSkyFlashTime() - partialTick;
            if (f11 > 1.0F) {
                f11 = 1.0F;
            }

            f11 *= 0.45F;
            f2 = f2 * (1.0F - f11) + 0.8F * f11;
            f3 = f3 * (1.0F - f11) + 0.8F * f11;
            f4 = f4 * (1.0F - f11) + f11;
        }

        return new Vec3(f2, f3, f4);
    }

    /**
     * [CODE COPY] - {@link LevelRenderer#renderSky(PoseStack, Matrix4f, float, Camera, boolean, Runnable)}.<br><br>
     * Copied parts of the middle segment that were used for sun/moon rendering.
     */
    private void drawCelestialBodies(float partialTick, PoseStack poseStack, ClientLevel level, BufferBuilder bufferBuilder) {
        // This code determines the current angle of the sun and moon and determines whether they should be visible or not.
        long dayTime = level.getDayTime() % (long) AetherDimensions.AETHER_TICKS_PER_DAY;
        float sunOpacity;
        float moonOpacity;
        if (dayTime > 71400L) {
            dayTime -= 71400L;
            sunOpacity = Math.min(dayTime * 0.00167F, 1F);
            moonOpacity = Math.max(1.0F - dayTime * 0.00167F, 0F);
        } else if (dayTime > 38400L) {
            dayTime -= 38400L;
            sunOpacity = Math.max(1.0F - dayTime * 0.00167F, 0F);
            moonOpacity = Math.min(dayTime * 0.00167F, 1F);
        } else {
            sunOpacity = 1.0F;
            moonOpacity = 0.0F;
        }
        sunOpacity -= level.getRainLevel(partialTick);
        moonOpacity -= level.getRainLevel(partialTick);

        // Render celestial bodies.
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(partialTick) * 360.0F));
        Matrix4f matrix4f1 = poseStack.last().pose();
        float celestialOffset = 30.0F;

        // Render the sun.
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, sunOpacity);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SUN_LOCATION);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f1, -celestialOffset, 100.0F, -celestialOffset).uv(0.0F, 0.0F).endVertex();
        bufferBuilder.vertex(matrix4f1, celestialOffset, 100.0F, -celestialOffset).uv(1.0F, 0.0F).endVertex();
        bufferBuilder.vertex(matrix4f1, celestialOffset, 100.0F, celestialOffset).uv(1.0F, 1.0F).endVertex();
        bufferBuilder.vertex(matrix4f1, -celestialOffset, 100.0F, celestialOffset).uv(0.0F, 1.0F).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());

        // Render the moon.
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, moonOpacity);
        celestialOffset = 20.0F;
        RenderSystem.setShaderTexture(0, MOON_LOCATION);
        int moonPhase = level.getMoonPhase();
        int textureX = moonPhase % 4;
        int textureY = moonPhase / 4 % 2;
        float uLeft = (float) (textureX) / 4.0F;
        float vDown = (float) (textureY) / 2.0F;
        float uRight = (float) (textureX + 1) / 4.0F;
        float vUp = (float) (textureY + 1) / 2.0F;
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f1, -celestialOffset, -100.0F, celestialOffset).uv(uRight, vUp).endVertex();
        bufferBuilder.vertex(matrix4f1, celestialOffset, -100.0F, celestialOffset).uv(uLeft, vUp).endVertex();
        bufferBuilder.vertex(matrix4f1, celestialOffset, -100.0F, -celestialOffset).uv(uLeft, vDown).endVertex();
        bufferBuilder.vertex(matrix4f1, -celestialOffset, -100.0F, -celestialOffset).uv(uRight, vDown).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }
}
