package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.WorldDisplayHelper;
import com.aetherteam.aether.client.AetherMusicManager;
import com.aetherteam.aether.client.gui.component.AccessoryButton;
import com.aetherteam.aether.client.gui.component.DynamicMenuButton;
import com.aetherteam.aether.client.gui.component.skins.RefreshButton;
import com.aetherteam.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen;
import com.aetherteam.aether.client.gui.screen.menu.VanillaLeftTitleScreen;
import com.aetherteam.aether.client.AetherKeys;
import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.inventory.menu.AccessoriesMenu;
import com.aetherteam.aether.mixin.mixins.client.accessor.*;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.server.OpenAccessoriesPacket;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Tuple;
import net.minecraft.world.BossEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import top.theillusivec4.curios.client.gui.CuriosScreen;

import java.util.Calendar;
import java.util.Date;

public class GuiHooks {
    public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Aether.MODID, "textures/gui/options_background.png");
    public static ResourceLocation OLD_BACKGROUND_LOCATION;
    public static ResourceLocation OLD_OPTIONS_BACKGROUND;

    public static final ResourceLocation LIGHT_DIRT_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/light_dirt_background.png");
    public static ResourceLocation OLD_LIGHT_DIRT_BACKGROUND;

    public static final ResourceLocation HEADER_SEPERATOR = new ResourceLocation(Aether.MODID, "textures/gui/header_separator.png");
    public static ResourceLocation OLD_HEADER_SEPERATOR;

    public static final ResourceLocation FOOTER_SEPERATOR = new ResourceLocation(Aether.MODID, "textures/gui/footer_separator.png");
    public static ResourceLocation OLD_FOOTER_SEPERATOR;

    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Aether.MODID, "textures/gui/tab_button.png");
    public static ResourceLocation OLD_TEXTURE_LOCATION;

    public static AetherTitleScreen aether_menu = null;
    public static TitleScreen default_menu = null;
    public static VanillaLeftTitleScreen default_left_menu = null;
    private static final ResourceLocation AETHER_BARS_LOCATION = new ResourceLocation(Aether.MODID, "textures/gui/boss_bar.png");
    private static boolean shouldAddButton = true;
    private static boolean generateTrivia = true;
    private static Screen lastScreen = null;

    private static boolean alignMenuLeft = false;

    public static void drawSentryBackground(Screen screen) {
        if (screen instanceof TitleScreen) {
            if (OLD_BACKGROUND_LOCATION == null) {
                OLD_BACKGROUND_LOCATION = GuiComponent.BACKGROUND_LOCATION;
            }
            if (OLD_OPTIONS_BACKGROUND == null) {
                OLD_OPTIONS_BACKGROUND = RealmsPlayerScreenAccessor.aether$getOptionsBackground();
            }
            if (OLD_LIGHT_DIRT_BACKGROUND == null) {
                OLD_LIGHT_DIRT_BACKGROUND = GuiComponent.LIGHT_DIRT_BACKGROUND;
            }
            if (OLD_HEADER_SEPERATOR == null) {
                OLD_HEADER_SEPERATOR = CreateWorldScreen.HEADER_SEPERATOR;
            }
            if (OLD_FOOTER_SEPERATOR == null) {
                OLD_FOOTER_SEPERATOR = CreateWorldScreen.FOOTER_SEPERATOR;
            }
            if (OLD_TEXTURE_LOCATION == null) {
                OLD_TEXTURE_LOCATION = TabButtonAccessor.getTextureLocation();
            }
            GuiComponentAccessor.aether$setBackgroundLocation(AetherConfig.CLIENT.enable_aether_menu.get() ? BACKGROUND_LOCATION : OLD_BACKGROUND_LOCATION);
            RealmsPlayerScreenAccessor.aether$setOptionsBackground(AetherConfig.CLIENT.enable_aether_menu.get() ? BACKGROUND_LOCATION : OLD_OPTIONS_BACKGROUND);
            GuiComponentAccessor.aether$setLightDirtBackground(AetherConfig.CLIENT.enable_aether_menu.get() ? LIGHT_DIRT_BACKGROUND : OLD_LIGHT_DIRT_BACKGROUND);
            CreateWorldScreenAccessor.setHeaderSeparator(AetherConfig.CLIENT.enable_aether_menu.get() ? HEADER_SEPERATOR : OLD_HEADER_SEPERATOR);
            CreateWorldScreenAccessor.setFooterSeparator(AetherConfig.CLIENT.enable_aether_menu.get() ? FOOTER_SEPERATOR : OLD_FOOTER_SEPERATOR);
            TabButtonAccessor.setTextureLocation(AetherConfig.CLIENT.enable_aether_menu.get() ? TEXTURE_LOCATION : OLD_TEXTURE_LOCATION);
        }
    }

    public static void setupMenus(TitleScreen screen) {
        if (aether_menu == null) {
            aether_menu = new AetherTitleScreen();
        }
        if (default_left_menu == null) {
            default_left_menu = new VanillaLeftTitleScreen();
        }
        if (default_menu == null) {
            default_menu = screen;
        }
    }

    public static void setupWorldPreview(Screen screen) {
        if (screen instanceof TitleScreen && AetherConfig.CLIENT.enable_world_preview.get()) {
            WorldDisplayHelper.enableWorldPreview();
        }
    }

    public static VanillaLeftTitleScreen openLeftDefaultMenu(Screen screen) {
        if (screen instanceof TitleScreen titleScreen) {
            setupMenus(titleScreen);
            if (displayAlignedLeftVanillaMenu()) {
                return default_left_menu;
            }
        }
        return null;
    }

    public static AetherTitleScreen openAetherMenu(Screen screen) {
        if (screen instanceof TitleScreen titleScreen) {
            setupMenus(titleScreen);
            if (AetherConfig.CLIENT.enable_aether_menu.get()) {
                return aether_menu;
            }
        }
        return null;
    }

    public static void setupSplash(Screen screen) {
        if (screen instanceof TitleScreen titleScreen) {
            TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) titleScreen;
            TitleScreenAccessor defaultMenuAccessor = (TitleScreenAccessor) default_menu;
            if (defaultMenuAccessor.aether$getSplash() != null) {
                titleScreenAccessor.aether$setSplash(defaultMenuAccessor.aether$getSplash());
            } else {
                defaultMenuAccessor.aether$setSplash(titleScreenAccessor.aether$getSplash());
            }
        }
    }

    public static Button setupToggleWorldButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(screen.width - 24, 4, 20, 20, Component.translatable("gui.aether.menu.button.world_preview"),
                    (pressed) -> {
                        AetherConfig.CLIENT.enable_world_preview.set(!AetherConfig.CLIENT.enable_world_preview.get());
                        AetherConfig.CLIENT.enable_world_preview.save();
                        WorldDisplayHelper.toggleWorldPreview(AetherConfig.CLIENT.enable_world_preview.get());
                    });
            dynamicMenuButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.menu.preview")));
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_world_preview_button);
            return dynamicMenuButton;
        }
        return null;
    }

    public static Button setupMenuSwitchButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(screen.width - 24, 4, 20, 20, Component.translatable("gui.aether.menu.button.theme"),
                    (pressed) -> {
                        AetherConfig.CLIENT.enable_aether_menu.set(!AetherConfig.CLIENT.enable_aether_menu.get());
                        AetherConfig.CLIENT.enable_aether_menu.save();
                        Minecraft.getInstance().setScreen(getMenu());
                        Minecraft.getInstance().getMusicManager().stopPlaying();
                        AetherMusicManager.stopPlaying();
                    });
            dynamicMenuButton.setTooltip(Tooltip.create(Component.translatable(AetherConfig.CLIENT.enable_aether_menu.get() ? "gui.aether.menu.minecraft" : "gui.aether.menu.aether")));
            dynamicMenuButton.setOffsetConfigs(AetherConfig.CLIENT.enable_world_preview_button);
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_aether_menu_button);
            return dynamicMenuButton;
        }
        return null;
    }

    public static Button setupQuickLoadButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            DynamicMenuButton dynamicMenuButton = new DynamicMenuButton(screen.width - 24, 4, 20, 20, Component.translatable("gui.aether.menu.button.quick_load"),
                    (pressed) -> {
                        WorldDisplayHelper.quickLoad();
                        Minecraft.getInstance().getMusicManager().stopPlaying();
                        AetherMusicManager.stopPlaying(); //todo doesn't quite work. might need to stop it through the sound manager
                    });
            dynamicMenuButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.menu.load")));
            dynamicMenuButton.setOffsetConfigs(AetherConfig.CLIENT.enable_world_preview_button, AetherConfig.CLIENT.enable_aether_menu_button);
            dynamicMenuButton.setDisplayConfigs(AetherConfig.CLIENT.enable_world_preview, AetherConfig.CLIENT.enable_quick_load_button);
            return dynamicMenuButton;
        }
        return null;
    }

    /**
     * If the current date is July 22nd, displays the Aether's anniversary splash text.
     */
    public static void setSplashText(TitleScreen screen) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(Calendar.MONTH) + 1 == 7 && calendar.get(Calendar.DATE) == 22) {
            TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) screen;
            titleScreenAccessor.aether$setSplash("Happy anniversary to the Aether!");
        }
    }

    public static TitleScreen getMenu() {
        if (AetherConfig.CLIENT.enable_aether_menu.get()) {
            aether_menu.fading = true;
            aether_menu.fadeInStart = 0L;
            return aether_menu;
        } else {
            if (displayAlignedLeftVanillaMenu()) {
                default_left_menu.fading = true;
                default_left_menu.fadeInStart = 0L;
                return default_left_menu;
            } else {
                TitleScreenAccessor defaultMenuAccessor = (TitleScreenAccessor) default_menu;
                defaultMenuAccessor.aether$setFading(true);
                defaultMenuAccessor.aether$setFadeInStart(0L);
                return default_menu;
            }
        }
    }

    public static boolean displayAlignedLeftVanillaMenu() {
        return (AetherConfig.CLIENT.menu_type_toggles_alignment.get() && AetherConfig.CLIENT.enable_world_preview.get()) || AetherConfig.CLIENT.align_vanilla_menu_elements_left.get();
    }

    public static AccessoryButton setupAccessoryButtonWithinInventories(Screen screen, Tuple<Integer, Integer> offsets) {
        if (screen instanceof InventoryScreen || screen instanceof CuriosScreen || screen instanceof CreativeModeInventoryScreen) {
            AbstractContainerScreen<?> inventoryScreen = (AbstractContainerScreen<?>) screen;
            return new AccessoryButton(inventoryScreen, inventoryScreen.getGuiLeft() + offsets.getA(), inventoryScreen.getGuiTop() + offsets.getB(), AccessoriesScreen.ACCESSORIES_BUTTON);
        }
        return null;
    }

    public static AccessoryButton setupAccessoryButtonWithinAccessoryMenu(Screen screen, Tuple<Integer, Integer> offsets) {
        if (screen instanceof AccessoriesScreen accessoriesScreen) {
            if (shouldAddButton) {
                return new AccessoryButton(accessoriesScreen, accessoriesScreen.getGuiLeft() + offsets.getA(), accessoriesScreen.getGuiTop() + offsets.getB(), AccessoriesScreen.ACCESSORIES_BUTTON);
            } else {
                shouldAddButton = true;
            }
        }
        return null;
    }

    public static boolean areItemsPresent() {
        boolean flag = true;
        for (String string : AccessoriesMenu.AETHER_IDENTIFIERS) {
            ITagManager<Item> itemTags = ForgeRegistries.ITEMS.tags();
            if (itemTags != null) {
                if (itemTags.getTag(TagKey.create(Registries.ITEM, new ResourceLocation("curios", string))).isEmpty()) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public static GridLayout setupPerksButtons(Screen screen) {
        int x = AetherConfig.CLIENT.layout_perks_x.get();
        int y = AetherConfig.CLIENT.layout_perks_y.get();

        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(1);

        ImageButton skinsButton = new ImageButton(0, 0, 20, 20, 0, 0, 20, AccessoriesScreen.SKINS_BUTTON, 20, 40,
                (pressed) -> Minecraft.getInstance().setScreen(new MoaSkinsScreen(screen)),
                Component.translatable("gui.aether.accessories.skins_button"));
        skinsButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.accessories.skins_button")));
        rowHelper.addChild(skinsButton, gridLayout.newCellSettings().paddingTop(58));

        User user = UserData.Client.getClientUser();
        if (user != null && (PerkUtil.hasDeveloperGlow().test(user) || PerkUtil.hasHalo().test(user))) {
            ImageButton customizationButton = new ImageButton(0, 0, 20, 20, 0, 0, 20, AccessoriesScreen.CUSTOMIZATION_BUTTON, 20, 40,
                    (pressed) -> Minecraft.getInstance().setScreen(new AetherCustomizationsScreen(screen)),
                    Component.translatable("gui.aether.accessories.customization_button"));
            customizationButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.accessories.customization_button")));
            rowHelper.addChild(customizationButton);
        } else {
            y -= 6;
        }

        gridLayout.arrangeElements();
        FrameLayout.alignInRectangle(gridLayout, x, y, screen.width, screen.height, 0.5F, 0.25F);

        return gridLayout;
    }

    public static void setMenuAlignment() {
        alignMenuLeft = displayAlignedLeftVanillaMenu();
    }

    public static void drawTrivia(Screen screen, PoseStack poseStack) {
        if (screen instanceof TitleScreen) {
            if (generateTrivia) {
                if (Aether.TRIVIA_READER.getTrivia().isEmpty()) {
                    Aether.TRIVIA_READER.generateTriviaList();
                    generateTrivia = false;
                }
            }
        } else if (screen instanceof LevelLoadingScreen) {
            if (generateTrivia) {
                if (Aether.TRIVIA_READER.getTrivia().isEmpty()) {
                    Aether.TRIVIA_READER.generateTriviaList();
                    generateTrivia = false;
                }
            }
        }

        if (screen instanceof GenericDirtMessageScreen || screen instanceof LevelLoadingScreen || screen instanceof ReceivingLevelScreen) {
            Component triviaLine = Aether.TRIVIA_READER.getTriviaLine();
            if (triviaLine != null && AetherConfig.CLIENT.enable_trivia.get()) {
                Font font = Minecraft.getInstance().font;
                int y = (screen.height - 7) - font.wordWrapHeight(triviaLine, screen.width);
                for (FormattedCharSequence sequence : font.split(triviaLine, screen.width)) {
                    Screen.drawCenteredString(poseStack, font, sequence, screen.width / 2, y, 16777113);
                    y += 9;
                }
            }
            if (screen != lastScreen) {
                if (!Aether.TRIVIA_READER.getTrivia().isEmpty()) {
                    Aether.TRIVIA_READER.randomizeTriviaIndex();
                }
            }
        }
        lastScreen = screen;
    }

    public static void drawAetherTravelMessage(Screen screen, PoseStack poseStack) {
        if (screen instanceof ReceivingLevelScreen || screen instanceof ProgressScreen) {
            if (Minecraft.getInstance().player != null) {
                if (DimensionHooks.displayAetherTravel) {
                    if (DimensionHooks.playerLeavingAether) {
                        Screen.drawCenteredString(poseStack, screen.getMinecraft().font, Component.translatable("gui.aether.descending"), screen.width / 2, 50, 16777215);
                    } else {
                        Screen.drawCenteredString(poseStack, screen.getMinecraft().font, Component.translatable("gui.aether.ascending"), screen.width / 2, 50, 16777215);
                    }
                }
            }
        } else {
            DimensionHooks.displayAetherTravel = false;
        }
    }

    public static void changeMenuAlignment(Screen screen, Minecraft minecraft) {
        if (screen instanceof TitleScreen titleScreen) {
            if (alignMenuLeft != displayAlignedLeftVanillaMenu()) {
                alignMenuLeft = displayAlignedLeftVanillaMenu();
                if (alignMenuLeft) {
                    if (titleScreen != default_left_menu) {
                        minecraft.forceSetScreen(default_left_menu);
                    }
                } else {
                    TitleScreen defaultMenu = AetherConfig.CLIENT.enable_aether_menu.get() ? aether_menu : default_menu;
                    if (titleScreen != defaultMenu) {
                        minecraft.forceSetScreen(defaultMenu);
                    }
                }
            }
        }
    }

    public static void closeContainerMenu(int key, int action) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen instanceof AbstractContainerScreen abstractContainerScreen && !abstractContainerScreen.passEvents) {
            if (!AetherConfig.CLIENT.disable_accessory_button.get() && AetherKeys.OPEN_ACCESSORY_INVENTORY.getKey().getValue() == key && (action == InputConstants.PRESS || action == InputConstants.REPEAT)) {
                abstractContainerScreen.onClose();
            }
        }
    }

    public static void openAccessoryMenu() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && minecraft.getOverlay() == null && (minecraft.screen == null || minecraft.screen.passEvents)) {
            if (!AetherConfig.CLIENT.disable_accessory_button.get() && AetherKeys.OPEN_ACCESSORY_INVENTORY.consumeClick()) {
                AetherPacketHandler.sendToServer(new OpenAccessoriesPacket(ItemStack.EMPTY));
                shouldAddButton = false;
            }
        }
    }

    public static void tickMenuWhenPaused(Minecraft minecraft) {
        if (minecraft != null && minecraft.level != null && minecraft.player != null) {
            if (WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null && minecraft.isPaused()) {
                minecraft.gameRenderer.tick();
                minecraft.levelRenderer.tick();
                AetherMusicManager.tick();
                minecraft.getMusicManager().tick();
                minecraft.getSoundManager().tick(false);
                minecraft.level.animateTick(minecraft.player.getBlockX(), minecraft.player.getBlockY(), minecraft.player.getBlockZ());
                Minecraft.getInstance().particleEngine.tick();
            }
        }
    }

    public static void handleRefreshRebound() {
        if (RefreshButton.reboundTimer > 0) {
            RefreshButton.reboundTimer--;
        }
        if (RefreshButton.reboundTimer < 0) {
            RefreshButton.reboundTimer = 0;
        }
    }

    /**
     * Vanilla copy
     * @see net.minecraft.client.gui.components.BossHealthOverlay#render(PoseStack)
     * This is used to draw the Aether's custom boss health bars.
     */
    public static void drawBossHealthBar(PoseStack poseStack, int x, int y, LerpingBossEvent bossEvent) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, AETHER_BARS_LOCATION);
        drawBar(poseStack, x + 2, y + 2, bossEvent);
        Component component = bossEvent.getName();
        int nameLength = Minecraft.getInstance().font.width(component);
        int nameX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - nameLength / 2;
        int nameY = y - 9;
        Minecraft.getInstance().font.drawShadow(poseStack, component, (float)nameX, (float)nameY, 16777215);
    }

    /**
     * @see net.minecraft.client.gui.components.BossHealthOverlay#drawBar(PoseStack, int, int, BossEvent)
     * Draws the boss health bar. This version of the method doesn't account for other types of boss bars because the
     * Aether only has one.
     */
    public static void drawBar(PoseStack pPoseStack, int pX, int pY, BossEvent pBossEvent) {
        pX -= 37; // The default boss health bar is offset by -91. We need -128.
        GuiComponent.blit(pPoseStack, pX, pY, -90, 0, 16, 256, 16, 256, 256);
        int health = (int)(pBossEvent.getProgress() * 256.0F);
        if (health > 0) {
            GuiComponent.blit(pPoseStack, pX, pY, -90, 0, 0, health, 16, 256, 256);
        }
    }

    public static boolean hideOverlays() {
        return AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null;
    }
}
