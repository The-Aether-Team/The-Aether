package com.gildedgames.aether.client.gui.overlay;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEffects;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@OnlyIn(Dist.CLIENT)
public class AetherOverlays
{
    private static final ResourceLocation TEXTURE_INEBRIATION_VIGNETTE = new ResourceLocation("aether", "textures/blur/inebriation_vignette.png");
    private static final ResourceLocation TEXTURE_REMEDY_VIGNETTE = new ResourceLocation("aether", "textures/blur/remedy_vignette.png");
    private static final ResourceLocation TEXTURE_REPULSION_SHIELD_VIGNETTE = new ResourceLocation("aether", "textures/blur/repulsion_shield_vignette.png");
    private static final ResourceLocation TEXTURE_COOLDOWN_BAR = new ResourceLocation("aether", "textures/gui/cooldown_bar.png");

    public static void renderAetherPortalOverlay(RenderGameOverlayEvent.Post event, Minecraft mc, Window window, IAetherPlayer handler) {
        float timeInPortal = handler.getPrevPortalAnimTime() + (handler.getPortalAnimTime() - handler.getPrevPortalAnimTime()) * event.getPartialTicks();
        if (timeInPortal > 0.0F) {
            if (timeInPortal < 1.0F) {
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, timeInPortal);
            mc.getTextureManager().bindForSetup(TextureAtlas.LOCATION_BLOCKS);
            TextureAtlasSprite textureatlassprite = mc.getBlockRenderer().getBlockModelShaper().getParticleIcon(AetherBlocks.AETHER_PORTAL.get().defaultBlockState());
            float f = textureatlassprite.getU0();
            float f1 = textureatlassprite.getV0();
            float f2 = textureatlassprite.getU1();
            float f3 = textureatlassprite.getV1();
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).uv(f, f3).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).uv(f2, f3).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).uv(f2, f1).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(f, f1).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public static void renderInebriationOverlay(Minecraft mc, Window window, IAetherPlayer handler) {
        Player player = handler.getPlayer();
        MobEffectInstance inebriation = player.getEffect(AetherEffects.INEBRIATION.get());
        if (inebriation != null) {
            float inebriationDuration = (float) (inebriation.getDuration() % 50) / 50;
            float alpha = (inebriationDuration * inebriationDuration) / 5.0F + 0.4F;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            mc.getTextureManager().bindForSetup(TEXTURE_INEBRIATION_VIGNETTE);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    public static void renderRemedyOverlay(Minecraft mc, Window window, IAetherPlayer handler) {
        int remedyMaximum = handler.getRemedyMaximum();
        int remedyTimer = handler.getRemedyTimer();
        if (remedyTimer > 0) {
            float alpha = ((float) remedyTimer / remedyMaximum) / 1.5F;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            mc.getTextureManager().bindForSetup(TEXTURE_REMEDY_VIGNETTE);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    public static void renderRepulsionShieldOverlay(Minecraft mc, Window window, IAetherPlayer handler) {
        int projectileImpactedMaximum = handler.getProjectileImpactedMaximum();
        int projectileImpactedTimer = handler.getProjectileImpactedTimer();
        if (projectileImpactedTimer > 0) {
            float alpha = (float) projectileImpactedTimer / projectileImpactedMaximum;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            mc.getTextureManager().bindForSetup(TEXTURE_REPULSION_SHIELD_VIGNETTE);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    public static void renderHammerCooldownOverlay(RenderGameOverlayEvent.Post event, Minecraft mc, Window window, LocalPlayer player) {
        Inventory inventory = player.getInventory();
        PoseStack matrixStack = event.getMatrixStack();
        if (inventory.contains(new ItemStack(AetherItems.HAMMER_OF_NOTCH.get()))) {
            for (ItemStack itemStack : inventory.items) {
                Item item = itemStack.getItem();
                if (item == AetherItems.HAMMER_OF_NOTCH.get()) {
                    float cooldownPercent = player.getCooldowns().getCooldownPercent(item, 0.0F);
                    if (cooldownPercent > 0.0F) {
                        if (player.getMainHandItem().getItem() == item) {
                            itemStack = player.getMainHandItem();
                        } else if (player.getOffhandItem().getItem() == item) {
                            itemStack = player.getOffhandItem();
                        }
                        String text = itemStack.getHoverName().getString().concat(" ").concat(new TranslatableComponent("aether.hammer_of_notch_cooldown").getString());
                        mc.font.drawShadow(matrixStack, text, (window.getGuiScaledWidth() / 2.0F) - (mc.font.width(text) / 2.0F), 32, 16777215);
                        mc.getTextureManager().bindForSetup(TEXTURE_COOLDOWN_BAR);
                        GuiComponent.blit(matrixStack, window.getGuiScaledWidth() / 2 - 64, 42, 0, 8, 128, 8, 256, 256);
                        GuiComponent.blit(matrixStack, window.getGuiScaledWidth() / 2 - 64, 42, 0, 0, (int) (cooldownPercent * 128), 8, 256, 256);
                        break;
                    }
                }
            }
        }
    }
}
