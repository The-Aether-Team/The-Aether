package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.button.AccessoryButton;
import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherTitleScreen;
import com.gildedgames.aether.client.registry.AetherKeys;
import com.gildedgames.aether.common.event.hooks.DimensionHooks;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.OpenAccessoriesPacket;
import com.mojang.blaze3d.vertex.*;
import com.mojang.realmsclient.gui.screens.RealmsPlayerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.client.gui.CuriosScreen;

public class GuiHooks {
    public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(Aether.MODID, "textures/gui/options_background.png");
    public static ResourceLocation OLD_LOCATION;
    public static ResourceLocation OLD_REALMS_LOCATION;

    private static AetherTitleScreen aether_menu = null;
    private static TitleScreen default_menu = null;
    private static boolean shouldAddButton = true;
    private static boolean generateTrivia = true;
    private static Screen lastScreen = null;

    public static void drawSentryBackground(Screen screen) {
        if (screen instanceof TitleScreen) {
            if (OLD_LOCATION == null) {
                OLD_LOCATION = GuiComponent.BACKGROUND_LOCATION;
            }
            if (OLD_REALMS_LOCATION == null) {
                OLD_REALMS_LOCATION = RealmsPlayerScreen.OPTIONS_BACKGROUND;
            }
            GuiComponent.BACKGROUND_LOCATION = AetherConfig.CLIENT.enable_aether_menu.get() ? BACKGROUND_LOCATION : OLD_LOCATION;
            RealmsPlayerScreen.OPTIONS_BACKGROUND = AetherConfig.CLIENT.enable_aether_menu.get() ? BACKGROUND_LOCATION : OLD_REALMS_LOCATION;
        }
    }

    public static void setupMenus(TitleScreen screen) {
        if (aether_menu == null) {
            aether_menu = new AetherTitleScreen();
        }
        if (default_menu == null) {
            default_menu = screen;
        }
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

    public static void openAccessoryMenu() {
        Minecraft minecraft = Minecraft.getInstance();
        Player entity = minecraft.player;
        if (entity != null) {
            if (AetherKeys.openAccessoryInventory.consumeClick() && minecraft.isWindowActive()) {
                AetherPacketHandler.sendToServer(new OpenAccessoriesPacket(entity.getId(), ItemStack.EMPTY));
                shouldAddButton = false;
            }
        }
    }

    public static Button setupMenuSwitchButton(Screen screen) {
        if (screen instanceof TitleScreen) {
            if (AetherConfig.CLIENT.enable_aether_menu_button.get()) {
                return new Button(screen.width - 24, 4, 20, 20, new TextComponent("T"),
                        (pressed) -> {
                            AetherConfig.CLIENT.enable_aether_menu.set(!AetherConfig.CLIENT.enable_aether_menu.get());
                            AetherConfig.CLIENT.enable_aether_menu.save();
                            Minecraft.getInstance().setScreen(getMenu());
                        },
                        (button, matrixStack, x, y) ->
                                screen.renderTooltip(matrixStack, new TranslatableComponent(AetherConfig.CLIENT.enable_aether_menu.get() ? "gui.aether.menu.minecraft" : "gui.aether.menu.aether"), x + 4, y + 12));
            }
        }
        return null;
    }

    private static TitleScreen getMenu() {
        if (AetherConfig.CLIENT.enable_aether_menu.get()) {
            aether_menu.setSplash(default_menu.splash);
            aether_menu.fading = true;
            aether_menu.fadeInStart = 0L;
            return aether_menu;
        } else {
            default_menu.splash = aether_menu.getSplash();
            default_menu.fading = true;
            default_menu.fadeInStart = 0L;
            return default_menu;
        }
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

    public static void drawTrivia(Screen screen, PoseStack poseStack) {
        if (screen instanceof TitleScreen) {
            if (generateTrivia) {
                Aether.TRIVIA_READER.generateTriviaList();
                generateTrivia = false;
            }
        }

        if (screen instanceof GenericDirtMessageScreen || screen instanceof LevelLoadingScreen || screen instanceof ReceivingLevelScreen) {
            Component triviaLine = Aether.TRIVIA_READER.getTriviaLine();
            if (triviaLine != null && AetherConfig.CLIENT.enable_trivia.get()) {
                Screen.drawCenteredString(poseStack, screen.getMinecraft().font, triviaLine, screen.width / 2, screen.height - 16, 16777113);
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
                        Screen.drawCenteredString(poseStack, screen.getMinecraft().font, new TranslatableComponent("gui.aether.descending"), screen.width / 2, 50, 16777215);
                    } else {
                        Screen.drawCenteredString(poseStack, screen.getMinecraft().font, new TranslatableComponent("gui.aether.ascending"), screen.width / 2, 50, 16777215);
                    }
                }
            }
        } else {
            DimensionHooks.displayAetherTravel = false;
        }
    }
}
