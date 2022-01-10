package com.gildedgames.aether.client.registry;

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
import net.minecraft.client.renderer.GameRenderer;
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
import net.minecraftforge.client.gui.OverlayRegistry;

@OnlyIn(Dist.CLIENT)
public class AetherOverlays
{
    private static final ResourceLocation TEXTURE_INEBRIATION_VIGNETTE = new ResourceLocation("aether", "textures/blur/inebriation_vignette.png");
    private static final ResourceLocation TEXTURE_REMEDY_VIGNETTE = new ResourceLocation("aether", "textures/blur/remedy_vignette.png");
    private static final ResourceLocation TEXTURE_REPULSION_SHIELD_VIGNETTE = new ResourceLocation("aether", "textures/blur/repulsion_shield_vignette.png");
    private static final ResourceLocation TEXTURE_COOLDOWN_BAR = new ResourceLocation("aether", "textures/gui/cooldown_bar.png");

    public static void registerOverlays() {
        OverlayRegistry.registerOverlayTop("Aether Portal Overlay", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                IAetherPlayer.get(player).ifPresent(handler -> {
                    gui.setupOverlayRenderState(true, false);
                    renderAetherPortalOverlay(minecraft, window, handler, partialTicks);
                });
            }
        });
        OverlayRegistry.registerOverlayTop("Inebriation Vignette", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                IAetherPlayer.get(player).ifPresent(handler -> {
                    gui.setupOverlayRenderState(true, false);
                    renderInebriationOverlay(minecraft, window, handler);
                });
            }
        });
        OverlayRegistry.registerOverlayTop("Remedy Vignette", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                IAetherPlayer.get(player).ifPresent(handler -> {
                    gui.setupOverlayRenderState(true, false);
                    renderRemedyOverlay(minecraft, window, handler);
                });
            }
        });
        OverlayRegistry.registerOverlayTop("Repulsion Shield Vignette", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                IAetherPlayer.get(player).ifPresent(handler -> {
                    gui.setupOverlayRenderState(true, false);
                    renderRepulsionShieldOverlay(minecraft, window, handler);
                });
            }
        });
        OverlayRegistry.registerOverlayTop("Hammer Cooldown", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                gui.setupOverlayRenderState(true, false);
                renderHammerCooldownOverlay(mStack, minecraft, window, player);
            }
        });
    }

    private static void renderAetherPortalOverlay(Minecraft mc, Window window, IAetherPlayer handler, float partialTicks) {
        float timeInPortal = handler.getPrevPortalAnimTime() + (handler.getPortalAnimTime() - handler.getPrevPortalAnimTime()) * partialTicks;
        if (timeInPortal > 0.0F) {
            if (timeInPortal < 1.0F) {
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, timeInPortal);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            TextureAtlasSprite textureatlassprite = mc.getBlockRenderer().getBlockModelShaper().getParticleIcon(AetherBlocks.AETHER_PORTAL.get().defaultBlockState());
            float f = textureatlassprite.getU0();
            float f1 = textureatlassprite.getV0();
            float f2 = textureatlassprite.getU1();
            float f3 = textureatlassprite.getV1();
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).uv(f, f3).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).uv(f2, f3).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).uv(f2, f1).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(f, f1).endVertex();
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private static void renderInebriationOverlay(Minecraft minecraft, Window window, IAetherPlayer handler) {
        Player player = handler.getPlayer();
        MobEffectInstance inebriation = player.getEffect(AetherEffects.INEBRIATION.get());
        float effectScale = minecraft.options.screenEffectScale;
        if (inebriation != null) {
            float inebriationDuration = (float) (inebriation.getDuration() % 50) / 50;
            float alpha = (inebriationDuration * inebriationDuration) / 5.0F + 0.4F;
            alpha *= Math.sqrt(effectScale);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE_INEBRIATION_VIGNETTE);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    private static void renderRemedyOverlay(Minecraft minecraft, Window window, IAetherPlayer handler) {
        int remedyMaximum = handler.getRemedyMaximum();
        int remedyTimer = handler.getRemedyTimer();
        float effectScale = minecraft.options.screenEffectScale;
        if (remedyTimer > 0) {
            float alpha = ((float) remedyTimer / remedyMaximum) / 1.5F;
            alpha *= Math.sqrt(effectScale);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE_REMEDY_VIGNETTE);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    private static void renderRepulsionShieldOverlay(Minecraft minecraft, Window window, IAetherPlayer handler) {
        int projectileImpactedMaximum = handler.getProjectileImpactedMaximum();
        int projectileImpactedTimer = handler.getProjectileImpactedTimer();
        float effectScale = minecraft.options.screenEffectScale;
        if (projectileImpactedTimer > 0) {
            float alpha = (float) projectileImpactedTimer / projectileImpactedMaximum;
            alpha *= Math.sqrt(effectScale);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE_REPULSION_SHIELD_VIGNETTE);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0D, window.getGuiScaledHeight(), -90.0D).uv(0.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0D).uv(1.0F, 1.0F).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
            bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
            tessellator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
        }
    }

    public static void renderHammerCooldownOverlay(PoseStack poseStack, Minecraft mc, Window window, LocalPlayer player) {
        Inventory inventory = player.getInventory();
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
                        mc.font.drawShadow(poseStack, text, (window.getGuiScaledWidth() / 2.0F) - (mc.font.width(text) / 2.0F), 32, 16777215);
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.setShaderTexture(0, TEXTURE_COOLDOWN_BAR);
                        GuiComponent.blit(poseStack, window.getGuiScaledWidth() / 2 - 64, 42, 0, 8, 128, 8, 256, 256);
                        GuiComponent.blit(poseStack, window.getGuiScaledWidth() / 2 - 64, 42, 0, 0, (int) (cooldownPercent * 128), 8, 256, 256);
                        break;
                    }
                }
            }
        }
    }
}
