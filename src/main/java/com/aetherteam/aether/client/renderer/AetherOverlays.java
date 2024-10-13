package com.aetherteam.aether.client.renderer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.mixin.mixins.client.accessor.GuiAccessor;
import com.aetherteam.aether.mixin.mixins.client.accessor.HeartTypeAccessor;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class AetherOverlays {
    private static final ResourceLocation TEXTURE_INEBRIATION_VIGNETTE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/blur/inebriation_vignette.png");
    private static final ResourceLocation TEXTURE_REMEDY_VIGNETTE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/blur/remedy_vignette.png");
    private static final ResourceLocation TEXTURE_SHIELD_OF_REPULSION_VIGNETTE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/blur/shield_of_repulsion_vignette.png");

    private static final ResourceLocation TEXTURE_COOLDOWN_BAR = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/cooldown");
    private static final ResourceLocation TEXTURE_COOLDOWN_BAR_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/cooldown_background");

    private static final ResourceLocation TEXTURE_JUMPS = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/jumps");
    private static final ResourceLocation TEXTURE_JUMPS_BACKGROUND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/jumps_background");

    private static final ResourceLocation TEXTURE_LIFE_SHARD_FULL = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_full");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_HALF = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_half");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_FULL_BLINKING = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_full_blinking");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_HALF_BLINKING = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_half_blinkin");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_POISONED_FULL = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_poisoned_full");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_POISONED_HALF = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_poisoned_half");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_POISONED_FULL_BLINKING = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_poisoned_full_blinking");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_POISONED_HALF_BLINKING = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_poisoned_half_blinking");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_WITHERED_FULL = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_withered_full");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_WITHERED_HALF = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_withered_half");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_WITHERED_FULL_BLINKING = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_withered_full_blinking");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_WITHERED_HALF_BLINKING = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_withered_half_blinking");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_ABSORBING_FULL = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_absorbing_full");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_ABSORBING_HALF = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_absorbing_half");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_FROZEN_FULL = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_frozen_full");
    private static final ResourceLocation TEXTURE_LIFE_SHARD_FROZEN_HALF = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hud/heart/shard_frozen_half");

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "aether_portal_overlay"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderAetherPortalOverlay(guiGraphics, minecraft, window, player, partialTicks);
            }
        });
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "inebriation_vignette"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderInebriationOverlay(guiGraphics, minecraft, window, player);
            }
        });
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remedy_vignette"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderRemedyOverlay(guiGraphics, minecraft, window, player);
            }
        });
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "shield_of_repulsion_vignette"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderRepulsionOverlay(guiGraphics, minecraft, window, player);
            }
        });
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "hammer_cooldown"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderHammerCooldownOverlay(guiGraphics, minecraft, window, player);
            }
        });
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "moa_jumps"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                renderMoaJumps(guiGraphics, window, player);
            }
        });
        event.registerAbove(ResourceLocation.withDefaultNamespace("player_health"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "silver_life_shard_hearts"), (guiGraphics, partialTicks) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
//                renderSilverLifeShardHearts(guiGraphics, gui, player, screenWidth, screenHeight);
            }
        });
    }


    /**
     * [CODE COPY] - {@link Gui#renderPortalOverlay(GuiGraphics, float)}.<br><br>
     * Warning for "deprecation" is suppressed because vanilla calls {@link net.minecraft.client.renderer.block.BlockModelShaper#getParticleIcon(BlockState)} just fine.
     */
    @SuppressWarnings("deprecation")
    private static void renderAetherPortalOverlay(GuiGraphics guiGraphics, Minecraft minecraft, Window window, Player player, DeltaTracker partialTicks) {
//        var handler = player.getData(AetherDataAttachments.AETHER_PLAYER);
//        PoseStack poseStack = guiGraphics.pose();
//        float timeInPortal = Mth.lerp(partialTicks.getGameTimeDeltaPartialTick(false), handler.getOldPortalIntensity(), handler.getPortalIntensity());
//        if (timeInPortal > 0.0F) {
//            if (timeInPortal < 1.0F) {
//                timeInPortal = timeInPortal * timeInPortal;
//                timeInPortal = timeInPortal * timeInPortal;
//                timeInPortal = timeInPortal * 0.8F + 0.2F;
//            }
//
//            poseStack.pushPose();
//            RenderSystem.disableDepthTest();
//            RenderSystem.depthMask(false);
//            TextureAtlasSprite textureAtlasSprite = minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(AetherBlocks.AETHER_PORTAL.get().defaultBlockState());
//            guiGraphics.blit(0, 0, -90, window.getGuiScaledWidth(), window.getGuiScaledHeight(), textureAtlasSprite, 1.0F, 1.0F, 1.0F, timeInPortal);
//            RenderSystem.depthMask(true);
//            RenderSystem.enableDepthTest();
//            poseStack.popPose();
//        }
    }

    /**
     * Renders a purple vignette with pulsing opacity.
     *
     * @param guiGraphics The {@link GuiGraphics} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The game {@link Window}.
     * @param player      The player that has the effect.
     */
    private static void renderInebriationOverlay(GuiGraphics guiGraphics, Minecraft minecraft, Window window, Player player) {
        MobEffectInstance inebriation = player.getEffect(AetherEffects.INEBRIATION);
        double effectScale = minecraft.options.screenEffectScale().get();
        if (inebriation != null) {
            float inebriationDuration = (float) (inebriation.getDuration() % 50) / 50;
            float alpha = (inebriationDuration * inebriationDuration) / 5.0F + 0.4F;
            renderVignette(guiGraphics, window, effectScale, alpha, TEXTURE_INEBRIATION_VIGNETTE);
        }
    }

    /**
     * Renders a green vignette with a gradually fading opacity.
     *
     * @param guiGraphics The {@link GuiGraphics} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The game {@link Window}.
     * @param player      The player that has the effect.
     */
    private static void renderRemedyOverlay(GuiGraphics guiGraphics, Minecraft minecraft, Window window, Player player) {
        MobEffectInstance remedy = player.getEffect(AetherEffects.REMEDY);
        double effectScale = minecraft.options.screenEffectScale().get();
        if (remedy != null) {
            int remedyStartDuration = player.getData(AetherDataAttachments.AETHER_PLAYER).getRemedyStartDuration();
            int remedyDuration = remedy.getDuration();
            if (remedyStartDuration > 0 && remedyDuration > 0) {
                float alpha = ((float) remedyDuration / remedyStartDuration) / 1.5F;
                renderVignette(guiGraphics, window, effectScale, alpha, TEXTURE_REMEDY_VIGNETTE);
            }
        }
    }

    /**
     * Renders a blue vignette with a gradually fading opacity.
     *
     * @param guiGraphics The {@link GuiGraphics} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The game {@link Window}.
     * @param player      The player.
     */
    private static void renderRepulsionOverlay(GuiGraphics guiGraphics, Minecraft minecraft, Window window, Player player) {
        var handler = player.getData(AetherDataAttachments.AETHER_PLAYER);
        int projectileImpactedMaximum = handler.getProjectileImpactedMaximum();
        int projectileImpactedTimer = handler.getProjectileImpactedTimer();
        double effectScale = minecraft.options.screenEffectScale().get();
        if (projectileImpactedTimer > 0) {
            float alpha = (float) projectileImpactedTimer / projectileImpactedMaximum;
            renderVignette(guiGraphics, window, effectScale, alpha, TEXTURE_SHIELD_OF_REPULSION_VIGNETTE);
        }
    }

    private static void renderVignette(GuiGraphics guiGraphics, Window window, double effectScale, float alpha, ResourceLocation resource) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        alpha *= Math.sqrt(effectScale);
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        guiGraphics.blit(resource, 0, 0, -90, 0.0F, 0.0F, window.getGuiScaledWidth(), window.getGuiScaledHeight(), window.getGuiScaledWidth(), window.getGuiScaledHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        poseStack.popPose();
    }

    /**
     * Renders a boss-esque bar at the top of the screen for the Hammer of Kingbdogz' item cooldown.
     *
     * @param guiGraphics The {@link GuiGraphics} for rendering.
     * @param minecraft   The {@link Minecraft} instance.
     * @param window      The game {@link Window}.
     * @param player      The {@link LocalPlayer}.
     */
    private static void renderHammerCooldownOverlay(GuiGraphics guiGraphics, Minecraft minecraft, Window window, LocalPlayer player) {
        if (AetherConfig.CLIENT.enable_hammer_cooldown_overlay.get()) {
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
                            guiGraphics.drawString(minecraft.font, text, (int) ((window.getGuiScaledWidth() / 2.0F) - (minecraft.font.width(text) / 2.0F)), 32, 16777215);
                            guiGraphics.blitSprite(TEXTURE_COOLDOWN_BAR_BACKGROUND, 128, 8, 0, 0, window.getGuiScaledWidth() / 2 - 64, 42, 128, 8);
                            guiGraphics.blitSprite(TEXTURE_COOLDOWN_BAR, 128, 8, 0, 0, window.getGuiScaledWidth() / 2 - 64, 42, (int) (cooldownPercent * 128), 8);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Renders feathers at the top of the screen corresponding to the amount of jumps the currently mounted Moa has.
     *
     * @param guiGraphics The {@link GuiGraphics} for rendering.
     * @param window      The game {@link Window}.
     * @param player      The {@link LocalPlayer}.
     */
    private static void renderMoaJumps(GuiGraphics guiGraphics, Window window, LocalPlayer player) {
        if (player.getVehicle() instanceof Moa moa) {
            for (int jumpCount = 0; jumpCount < moa.getMaxJumps(); jumpCount++) {
                int xPos = ((window.getGuiScaledWidth() / 2) + (jumpCount * 8)) - (moa.getMaxJumps() * 8) / 2;
                int yPos = 18;
                if (jumpCount < moa.getRemainingJumps()) {
                    guiGraphics.blitSprite(TEXTURE_JUMPS, xPos, yPos, 9, 11);
                } else {
                    guiGraphics.blitSprite(TEXTURE_JUMPS_BACKGROUND, xPos, yPos, 9, 11);
                }
            }
        }
    }

    /**
     * [CODE COPY] - {@link Gui#renderPlayerHealth(GuiGraphics)}.<br><br>
     * Stripped down to only use what is necessary.<br>
     * Renders silver heart textures over the extra hearts given by Life Shards.
     *
     * @param guiGraphics The {@link GuiGraphics} for rendering.
     * @param gui         The {@link ExtendedGui} included in rendering.
     * @param player      The {@link LocalPlayer}.
     * @param width       The {@link Integer} for the screen width.
     * @param height      The {@link Integer} for the screen height.
     */
    private static void renderSilverLifeShardHearts(GuiGraphics guiGraphics, Gui gui, LocalPlayer player, int width, int height) {
//        GuiAccessor guiAccessor = (GuiAccessor) gui;
//        if (AetherConfig.CLIENT.enable_silver_hearts.get() && gui.shouldDrawSurvivalElements()) {
//            var aetherPlayer = player.getData(AetherDataAttachments.AETHER_PLAYER);
//            if (aetherPlayer.getLifeShardCount() > 0) {
//                AttributeInstance attributeInstance = player.getAttribute(Attributes.MAX_HEALTH);
//                if (attributeInstance != null) {
//                    int lastLifeShardHealth = 0;
//                    int lastOverallHealth = 0;
//
//                    RenderSystem.enableBlend();
//
//                    double overallHealth = attributeInstance.getValue();
//                    double maxLifeShardHealth = aetherPlayer.getLifeShardHealthAttributeModifier().getAmount();
//
//                    int maxDefaultHealth = Mth.ceil(overallHealth - maxLifeShardHealth);
//
//                    int currentOverallHealth = Mth.ceil(player.getHealth());
//                    int currentLifeShardHealth = Mth.ceil(maxDefaultHealth > 20 ? Mth.clamp(currentOverallHealth - 20, 0, maxLifeShardHealth) : Math.min(player.getHealth(), currentOverallHealth - maxDefaultHealth));
//
//                    boolean highlight = guiAccessor.aether$getHealthBlinkTime() > (long) gui.getGuiTicks() && (guiAccessor.aether$getHealthBlinkTime() - (long) gui.getGuiTicks()) / 3L % 2L == 1L;
//                    if (Util.getMillis() - guiAccessor.aether$getLastHealthTime() > 1000L) {
//                        lastOverallHealth = currentOverallHealth;
//                        lastLifeShardHealth = currentLifeShardHealth;
//                    }
//
//                    //do NOT cast this to long. This is the only way the hearts will properly shake when health is low
//                    //the only time the shaking will be off is if the player's max health attribute base is below 0. This probably can't be fixed.
//                    guiAccessor.aether$getRandom().setSeed(gui.getGuiTicks() * 312871L);
//
//                    float displayOverallHealth = Math.max((float) overallHealth, Math.max(lastOverallHealth, currentOverallHealth));
//                    float displayLifeShardHealth = Math.max((float) maxLifeShardHealth, Math.max(lastLifeShardHealth, currentLifeShardHealth));
//                    int absorption = Mth.ceil(player.getAbsorptionAmount());
//
//                    int healthRows = Mth.ceil((displayOverallHealth + absorption) / 2.0F / 10.0F);
//                    int rowHeight = Math.max(10 - (healthRows - 2), 3);
//
//                    int left = width / 2 - 91;
//                    int top = height - 39;
//
//                    int regen = Integer.MIN_VALUE;
//                    if (player.hasEffect(MobEffects.REGENERATION)) {
//                        regen = gui.getGuiTicks() % Mth.ceil(displayOverallHealth + 5.0F);
//                    }
//
//                    renderHearts(guiGraphics, player, gui, left, top, regen, displayOverallHealth, displayLifeShardHealth, maxDefaultHealth, currentLifeShardHealth, rowHeight, absorption, highlight);
//
//                    RenderSystem.disableBlend();
//                }
//            }
//        }
    }

    /**
     * [CODE COPY] - {@link Gui#renderHearts(GuiGraphics, Player, int, int, int, int, float, int, int, int, boolean)}.<br><br>
     * Stripped down to only use what is necessary.
     */
    private static void renderHearts(GuiGraphics guiGraphics, Player player, Gui gui, int left, int top, int regen, float displayOverallHealth, float displayLifeShardHealth, int maxDefaultHealth, int lifeShardHealth, int rowHeight, int absorption, boolean highlight) {
        GuiAccessor guiAccessor = (GuiAccessor) gui;
        Gui.HeartType heartType = HeartTypeAccessor.callForPlayer(player);
        int overallHearts = Mth.ceil((double) displayOverallHealth / 2.0);
        int lifeShardHearts = Mth.ceil((double) displayLifeShardHealth / 2.0);
        int maxDefaultHearts = Mth.ceil((double) maxDefaultHealth / 2.0);
        boolean tooManyHearts = overallHearts > 50;
        boolean tooLittleHearts = maxDefaultHearts < 10 && maxDefaultHearts > 0;
        for (int currentHeart = Math.min(overallHearts, lifeShardHearts - 1); currentHeart >= 0; --currentHeart) {
            int x = left + (currentHeart + (tooLittleHearts ? overallHearts - lifeShardHearts : 0)) % 10 * 8;
            int y = top - (currentHeart + (tooManyHearts ? 0 : maxDefaultHearts + currentHeart < 10 ? 0 : 10)) / 10 * rowHeight;

            if (Mth.ceil(player.getHealth()) + absorption <= 4) {
                y += guiAccessor.aether$getRandom().nextInt(2);
            }
            if (currentHeart + (maxDefaultHearts > 10 ? overallHearts - 10 : maxDefaultHearts) < overallHearts && currentHeart + Math.min(maxDefaultHearts, 10) - (tooManyHearts ? overallHearts : 0) == regen) {
                y -= 2;
            }
            int selectedContainer = currentHeart * 2;
            if (highlight && selectedContainer < displayLifeShardHealth) {
                boolean halfHeart = selectedContainer + 1 == displayLifeShardHealth;
                guiGraphics.blitSprite(AetherOverlays.getSprite(heartType, halfHeart, true), x, y, 9, 9);
            }
            if (selectedContainer < lifeShardHealth) {
                boolean halfHeart = selectedContainer + 1 == lifeShardHealth;
                guiGraphics.blitSprite(AetherOverlays.getSprite(heartType, halfHeart, false), x, y, 9, 9);
            }
        }
    }

    private static ResourceLocation getSprite(Gui.HeartType heartType, boolean halfHeart, boolean blinking) {
        if (heartType == Gui.HeartType.NORMAL) {
            if (!halfHeart) {
                return !blinking ? TEXTURE_LIFE_SHARD_FULL : TEXTURE_LIFE_SHARD_FULL_BLINKING;
            } else {
                return !blinking ? TEXTURE_LIFE_SHARD_HALF : TEXTURE_LIFE_SHARD_HALF_BLINKING;
            }
        } else if (heartType == Gui.HeartType.POISIONED) {
            if (!halfHeart) {
                return !blinking ? TEXTURE_LIFE_SHARD_POISONED_FULL : TEXTURE_LIFE_SHARD_POISONED_FULL_BLINKING;
            } else {
                return !blinking ? TEXTURE_LIFE_SHARD_POISONED_HALF : TEXTURE_LIFE_SHARD_POISONED_HALF_BLINKING;
            }
        } else if (heartType == Gui.HeartType.WITHERED) {
            if (!halfHeart) {
                return !blinking ? TEXTURE_LIFE_SHARD_WITHERED_FULL : TEXTURE_LIFE_SHARD_WITHERED_FULL_BLINKING;
            } else {
                return !blinking ? TEXTURE_LIFE_SHARD_WITHERED_HALF : TEXTURE_LIFE_SHARD_WITHERED_HALF_BLINKING;
            }
        } else if (heartType == Gui.HeartType.ABSORBING) {
            return !halfHeart ? TEXTURE_LIFE_SHARD_ABSORBING_FULL : TEXTURE_LIFE_SHARD_ABSORBING_HALF;
        } else if (heartType == Gui.HeartType.FROZEN) {
            return !halfHeart ? TEXTURE_LIFE_SHARD_FROZEN_FULL : TEXTURE_LIFE_SHARD_FROZEN_HALF;
        }
        return Gui.HeartType.CONTAINER.getSprite(false, halfHeart, blinking);
    }
}
