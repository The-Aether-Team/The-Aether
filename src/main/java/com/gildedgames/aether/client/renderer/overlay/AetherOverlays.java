package com.gildedgames.aether.client.renderer.overlay;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class AetherOverlays
{
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
            bufferbuilder.vertex(0.0D, window.getScreenHeight(), -90.0D).uv(f, f3).endVertex();
            bufferbuilder.vertex(window.getScreenWidth(), window.getScreenHeight(), -90.0D).uv(f2, f3).endVertex();
            bufferbuilder.vertex(window.getScreenWidth(), 0.0D, -90.0D).uv(f2, f1).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(f, f1).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
