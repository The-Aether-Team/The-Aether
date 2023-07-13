package com.aetherteam.aether.client.renderer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.mixin.mixins.client.accessor.GuiAccessor;
import com.aetherteam.aether.mixin.mixins.client.accessor.HeartTypeAccessor;
import com.mojang.blaze3d.vertex.*;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherOverlays {
    private static final ResourceLocation TEXTURE_INEBRIATION_VIGNETTE = new ResourceLocation(Aether.MODID, "textures/blur/inebriation_vignette.png");
    private static final ResourceLocation TEXTURE_REMEDY_VIGNETTE = new ResourceLocation(Aether.MODID, "textures/blur/remedy_vignette.png");
    private static final ResourceLocation TEXTURE_SHIELD_OF_REPULSION_VIGNETTE = new ResourceLocation(Aether.MODID, "textures/blur/shield_of_repulsion_vignette.png");
    private static final ResourceLocation TEXTURE_COOLDOWN_BAR = new ResourceLocation(Aether.MODID, "textures/gui/cooldown_bar.png");
    private static final ResourceLocation TEXTURE_JUMPS = new ResourceLocation(Aether.MODID, "textures/gui/jumps.png");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_HEARTS = new ResourceLocation(Aether.MODID, "textures/gui/life_shard_hearts.png");

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("aether_portal_overlay", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                AetherPlayer.get(player).ifPresent(handler -> renderAetherPortalOverlay(pStack, minecraft, window, handler, partialTicks));
            }
        });
        event.registerAboveAll("inebriation_vignette", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                AetherPlayer.get(player).ifPresent(handler -> renderInebriationOverlay(pStack, minecraft, window, handler));
            }
        });
        event.registerAboveAll("remedy_vignette", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                AetherPlayer.get(player).ifPresent(handler -> renderRemedyOverlay(pStack, minecraft, window, handler));
            }
        });
        event.registerAboveAll("shield_of_repulsion_vignette", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                AetherPlayer.get(player).ifPresent(handler -> renderRepulsionOverlay(pStack, minecraft, window, handler));
            }
        });
        event.registerAboveAll("hammer_cooldown", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderHammerCooldownOverlay(pStack, minecraft, window, player);
            }
        });
        event.registerAboveAll("moa_jumps", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderMoaJumps(pStack, window, player);
            }
        });
        event.registerAbove(new ResourceLocation("player_health"), "silver_life_shard_hearts", (gui, pStack, partialTicks, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            int[] lastLifeShardHealth = {0};
            int[] lastOverallHealth = {0};
            if (player != null) {
                renderSilverLifeShardHearts(pStack, gui, player, screenWidth, screenHeight, lastLifeShardHealth, lastOverallHealth);
            }
        });
    }


    /**
     * Warning for "deprecation" is suppressed because vanilla calls {@link net.minecraft.client.renderer.block.BlockModelShaper#getParticleIcon(BlockState)} just fine.
     */
    @SuppressWarnings("deprecation")
    private static void renderAetherPortalOverlay(PoseStack poseStack, Minecraft mc, Window window, AetherPlayer handler, float partialTicks) {
        float timeInPortal = handler.getPrevPortalAnimTime() + (handler.getPortalAnimTime() - handler.getPrevPortalAnimTime()) * partialTicks;
        if (timeInPortal > 0.0F) {
            if (timeInPortal < 1.0F) {
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * timeInPortal;
                timeInPortal = timeInPortal * 0.8F + 0.2F;
            }

            poseStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, timeInPortal);
            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            TextureAtlasSprite textureatlassprite = mc.getBlockRenderer().getBlockModelShaper().getParticleIcon(AetherBlocks.AETHER_PORTAL.get().defaultBlockState());
            float f = textureatlassprite.getU0();
            float f1 = textureatlassprite.getV0();
            float f2 = textureatlassprite.getU1();
            float f3 = textureatlassprite.getV1();
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.vertex(0.0, window.getGuiScaledHeight(), -90.0).uv(f, f3).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0).uv(f2, f3).endVertex();
            bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0, -90.0).uv(f2, f1).endVertex();
            bufferbuilder.vertex(0.0, 0.0, -90.0).uv(f, f1).endVertex();
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            poseStack.popPose();
        }
    }

    private static void renderInebriationOverlay(PoseStack poseStack, Minecraft minecraft, Window window, AetherPlayer handler) {
        Player player = handler.getPlayer();
        MobEffectInstance inebriation = player.getEffect(AetherEffects.INEBRIATION.get());
        double effectScale = minecraft.options.screenEffectScale().get();
        if (inebriation != null) {
            float inebriationDuration = (float) (inebriation.getDuration() % 50) / 50;
            float alpha = (inebriationDuration * inebriationDuration) / 5.0F + 0.4F;
            renderVignette(poseStack, window, effectScale, alpha, TEXTURE_INEBRIATION_VIGNETTE);
        }
    }

    private static void renderRemedyOverlay(PoseStack poseStack, Minecraft minecraft, Window window, AetherPlayer handler) {
        int remedyMaximum = handler.getRemedyMaximum();
        int remedyTimer = handler.getRemedyTimer();
        double effectScale = minecraft.options.screenEffectScale().get();
        if (remedyTimer > 0) {
            float alpha = ((float) remedyTimer / remedyMaximum) / 1.5F;
            renderVignette(poseStack, window, effectScale, alpha, TEXTURE_REMEDY_VIGNETTE);
        }
    }

    private static void renderRepulsionOverlay(PoseStack poseStack, Minecraft minecraft, Window window, AetherPlayer handler) {
        int projectileImpactedMaximum = handler.getProjectileImpactedMaximum();
        int projectileImpactedTimer = handler.getProjectileImpactedTimer();
        double effectScale = minecraft.options.screenEffectScale().get();
        if (projectileImpactedTimer > 0) {
            float alpha = (float) projectileImpactedTimer / projectileImpactedMaximum;
            renderVignette(poseStack, window, effectScale, alpha, TEXTURE_SHIELD_OF_REPULSION_VIGNETTE);
        }
    }

    private static void renderVignette(PoseStack poseStack, Window window, double effectScale, float alpha, ResourceLocation resource) {
        poseStack.pushPose();
        alpha *= Math.sqrt(effectScale);
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, resource);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0, window.getGuiScaledHeight(), -90.0).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(window.getGuiScaledWidth(), window.getGuiScaledHeight(), -90.0).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(window.getGuiScaledWidth(), 0.0, -90.0).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0, 0.0, -90.0).uv(0.0F, 0.0F).endVertex();
        tessellator.end();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }

    private static void renderHammerCooldownOverlay(PoseStack poseStack, Minecraft mc, Window window, LocalPlayer player) {
        Inventory inventory = player.getInventory();
        if (inventory.hasAnyMatching((itemStack) -> itemStack.is(AetherItems.HAMMER_OF_KINGBDOGZ.get()))) {
            for (ItemStack itemStack : inventory.items) {
                Item item = itemStack.getItem();
                if (item == AetherItems.HAMMER_OF_KINGBDOGZ.get()) {
                    float cooldownPercent = player.getCooldowns().getCooldownPercent(item, 0.0F);
                    if (cooldownPercent > 0.0F) {
                        if (player.getMainHandItem().getItem() == item) {
                            itemStack = player.getMainHandItem();
                        } else if (player.getOffhandItem().getItem() == item) {
                            itemStack = player.getOffhandItem();
                        }
                        String text = itemStack.getHoverName().getString().concat(" ").concat(Component.translatable("aether.hammer_of_kingbdogz_cooldown").getString());
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

    private static void renderMoaJumps(PoseStack poseStack, Window window, LocalPlayer player) {
        if (player.getVehicle() instanceof Moa moa) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE_JUMPS);
            for (int jumpCount = 0; jumpCount < moa.getMaxJumps(); jumpCount++) {
                int xPos = ((window.getGuiScaledWidth() / 2) + (jumpCount * 8)) - (moa.getMaxJumps() * 8) / 2;
                int yPos = 18;
                if (jumpCount < moa.getRemainingJumps()) {
                    GuiComponent.blit(poseStack, xPos, yPos, 0, 0, 9, 11, 256, 256);
                } else {
                    GuiComponent.blit(poseStack, xPos, yPos, 10, 0, 9, 11, 256, 256);
                }
            }
        }
    }

    private static void renderSilverLifeShardHearts(PoseStack poseStack, ForgeGui gui, LocalPlayer player, int width, int height, int[] lastLifeShardHealth, int[] lastOverallHealth) {
        GuiAccessor guiAccessor = (GuiAccessor) gui;
        if (AetherConfig.CLIENT.enable_silver_hearts.get() && gui.shouldDrawSurvivalElements()) {
            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                if (aetherPlayer.getLifeShardCount() > 0) {
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, TEXTURE_LIFE_SHARD_HEARTS);
                    RenderSystem.enableBlend();

                    double overallHealth = player.getAttribute(Attributes.MAX_HEALTH).getValue();
                    double maxLifeShardHealth = aetherPlayer.getLifeShardHealthAttributeModifier().getAmount();

                    int maxDefaultHealth = Mth.ceil(overallHealth - maxLifeShardHealth);

                    int currentOverallHealth = Mth.ceil(player.getHealth());
                    int currentLifeShardHealth = Mth.ceil(maxDefaultHealth > 20 ? Mth.clamp(currentOverallHealth - 20, 0, maxLifeShardHealth) : currentOverallHealth - maxDefaultHealth);

                    boolean highlight = guiAccessor.aether$getHealthBlinkTime() > (long) gui.getGuiTicks() && (guiAccessor.aether$getHealthBlinkTime() - (long) gui.getGuiTicks()) / 3L % 2L == 1L;
                    if (Util.getMillis() - guiAccessor.aether$getLastHealthTime() > 1000L) {
                        lastOverallHealth[0] = currentOverallHealth;
                        lastLifeShardHealth[0] = currentLifeShardHealth;
                    }

                    float displayOverallHealth = Math.max((float) overallHealth, Math.max(lastOverallHealth[0], currentOverallHealth));
                    float displayLifeShardHealth = Math.max((float) maxLifeShardHealth, Math.max(lastLifeShardHealth[0], currentLifeShardHealth));
                    int absorption = Mth.ceil(player.getAbsorptionAmount());

                    int healthRows = Mth.ceil((displayOverallHealth + absorption) / 2.0F / 10.0F);
                    int rowHeight = Math.max(10 - (healthRows - 2), 3);

                    int left = width / 2 - 91;
                    int top = height - 39;

                    int regen = -1;
                    if (player.hasEffect(MobEffects.REGENERATION)) {
                        regen = gui.getGuiTicks() % Mth.ceil(displayOverallHealth + 5.0F);
                    }

                    renderHearts(poseStack, player, gui, left, top, regen, displayOverallHealth, displayLifeShardHealth, maxDefaultHealth, currentLifeShardHealth, rowHeight, absorption, highlight);

                    RenderSystem.disableBlend();
                }
            });
        }
    }

    private static void renderHearts(PoseStack poseStack, Player player, ForgeGui gui, int left, int top, int regen, float displayOverallHealth, float displayLifeShardHealth, int maxDefaultHealth, int lifeShardHealth, int rowHeight, int absorption, boolean highlight) {
        GuiAccessor guiAccessor = (GuiAccessor) gui;
        Gui.HeartType heartType = HeartTypeAccessor.callForPlayer(player);
        int overallHearts = Mth.ceil((double) displayOverallHealth / 2.0);
        int lifeShardHearts = Mth.ceil((double) displayLifeShardHealth / 2.0);
        int maxDefaultHearts = Mth.ceil((double) maxDefaultHealth / 2.0);
        boolean tooManyHearts = overallHearts > 50;
        boolean tooLittleHearts = maxDefaultHearts < 10;
        for (int currentHeart = lifeShardHearts - 1; currentHeart >= 0; --currentHeart) {
            int x = left + (currentHeart + (tooLittleHearts ? overallHearts - lifeShardHearts : 0)) % 10 * 8;
            int y = top - (currentHeart + (tooManyHearts ? 0 : maxDefaultHearts + currentHeart < 10 ? 0 : 10)) / 10 * rowHeight;

            if (displayOverallHealth + absorption <= 4) {
                y += guiAccessor.aether$getRandom().nextInt(2);
            }
            if (currentHeart + (maxDefaultHearts > 10 ? overallHearts - 10 : maxDefaultHearts) < overallHearts && currentHeart + Math.min(maxDefaultHearts, 10) - (tooManyHearts ? overallHearts : 0) == regen) {
                y -= 2;
            }
            int selectedContainer = currentHeart * 2;
            if (highlight && selectedContainer < displayLifeShardHealth) {
                boolean halfHeart = selectedContainer + 1 == displayLifeShardHealth;
                guiAccessor.callRenderHeart(poseStack, heartType, x, y, 0, true, halfHeart);
            }
            if (selectedContainer < lifeShardHealth) {
                boolean halfHeart = selectedContainer + 1 == lifeShardHealth;
                guiAccessor.callRenderHeart(poseStack, heartType, x, y, 0, false, halfHeart);
            }
        }
    }
}
