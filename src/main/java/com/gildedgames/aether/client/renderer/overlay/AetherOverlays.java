package com.gildedgames.aether.client.renderer.overlay;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEffects;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@OnlyIn(Dist.CLIENT)
public class AetherOverlays
{
    private static final ResourceLocation TEXTURE_INEBRIATION_VIGNETTE = new ResourceLocation("aether", "textures/blur/inebriation_vignette.png");
    private static final ResourceLocation TEXTURE_REMEDY_VIGNETTE = new ResourceLocation("aether", "textures/blur/remedy_vignette.png");
    private static final ResourceLocation TEXTURE_REPULSION_SHIELD_VIGNETTE = new ResourceLocation("aether", "textures/blur/repulsion_shield_vignette.png");

    public static void renderAetherPortalOverlay(RenderGameOverlayEvent.Post event, Minecraft mc, MainWindow window, IAetherPlayer handler) {
        float timeInPortal = handler.getPrevPortalAnimTime() + (handler.getPortalAnimTime() - handler.getPrevPortalAnimTime()) * event.getPartialTicks();
        if (timeInPortal > 0.0F) {
            if (timeInPortal < 1.0F) {
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            RenderSystem.disableAlphaTest();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, timeInPortal);
            mc.getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
            TextureAtlasSprite textureatlassprite = mc.getBlockRenderer().getBlockModelShaper().getParticleIcon(AetherBlocks.AETHER_PORTAL.get().defaultBlockState());
            float f = textureatlassprite.getU0();
            float f1 = textureatlassprite.getV0();
            float f2 = textureatlassprite.getU1();
            float f3 = textureatlassprite.getV1();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).uv(f, f3).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).uv(f2, f3).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).uv(f2, f1).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(f, f1).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public static void renderInebriationOverlay(Minecraft mc, MainWindow window, IAetherPlayer handler) {
        PlayerEntity player = handler.getPlayer();
        EffectInstance inebriation = player.getEffect(AetherEffects.INEBRIATION.get());
        if (inebriation != null) {
            float inebriationDuration = (float) (inebriation.getDuration() % 50) / 50;
            float alpha = (inebriationDuration * inebriationDuration) / 5.0F + 0.4F;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            mc.getTextureManager().bind(TEXTURE_INEBRIATION_VIGNETTE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    public static void renderRemedyOverlay(Minecraft mc, MainWindow window, IAetherPlayer handler) {
        int remedyMaximum = handler.getRemedyMaximum();
        int remedyTimer = handler.getRemedyTimer();
        if (remedyTimer > 0) {
            float alpha = ((float) remedyTimer / remedyMaximum) / 1.5F;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            mc.getTextureManager().bind(TEXTURE_REMEDY_VIGNETTE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    public static void renderRepulsionShieldOverlay(Minecraft mc, MainWindow window, IAetherPlayer handler) {
        int projectileImpactedMaximum = handler.getProjectileImpactedMaximum();
        int projectileImpactedTimer = handler.getProjectileImpactedTimer();
        if (projectileImpactedTimer > 0) {
            float alpha = (float) projectileImpactedTimer / projectileImpactedMaximum;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            mc.getTextureManager().bind(TEXTURE_REPULSION_SHIELD_VIGNETTE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }
}
