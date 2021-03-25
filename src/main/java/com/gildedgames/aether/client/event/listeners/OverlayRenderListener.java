package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OverlayRenderListener
{
    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        MainWindow window = event.getWindow();
        LazyOptional<IAetherPlayer> aetherPlayer = player.getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY);
        aetherPlayer.ifPresent(handler -> {
            //Portal overlay
            if(event.getType() == RenderGameOverlayEvent.ElementType.PORTAL) {
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
        });
    }
}
