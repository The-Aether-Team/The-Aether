package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.client.gui.button.AccessoryButton;
import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.client.gui.screen.menu.AetherMainMenuScreen;
import com.gildedgames.aether.client.registry.AetherKeys;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.OpenAccessoriesPacket;
import com.gildedgames.aether.core.util.TriviaReader;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.client.gui.CuriosScreen;

public class GuiHooks {
    private static boolean shouldAddButton = true;

    public static AetherMainMenuScreen openAetherMenu(Screen screen) {
        if (screen instanceof TitleScreen) {
            if (AetherConfig.CLIENT.enable_aether_menu.get()) {
                return new AetherMainMenuScreen();
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
                            Minecraft.getInstance().setScreen(AetherConfig.CLIENT.enable_aether_menu.get() ? new AetherMainMenuScreen() : new TitleScreen());
                        },
                        (button, matrixStack, x, y) ->
                                screen.renderTooltip(matrixStack, new TranslatableComponent(AetherConfig.CLIENT.enable_aether_menu.get() ? "gui.aether.menu.minecraft" : "gui.aether.menu.aether"), x + 4, y + 12));
            }
        }
        return null;
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
        if (screen instanceof GenericDirtMessageScreen) {
            Component triviaLine = TriviaReader.getTriviaLine();
            if (triviaLine != null && !screen.getTitle().equals(new TranslatableComponent("menu.savingLevel")) && AetherConfig.CLIENT.enable_trivia.get()) {
                Screen.drawCenteredString(poseStack, screen.getMinecraft().font, triviaLine, screen.width / 2, screen.height - 16, 16777113);
            }
        }
    }

    public static void drawAetherTravelMessage(Screen screen, PoseStack poseStack) {
        if (screen instanceof ReceivingLevelScreen || screen instanceof ProgressScreen) {
            if (Minecraft.getInstance().player != null) {
                if (DimensionClientHooks.displayAetherTravel) {
                    if (DimensionClientHooks.playerLeavingAether) {
                        Screen.drawCenteredString(poseStack, screen.getMinecraft().font, new TranslatableComponent("gui.aether.descending"), screen.width / 2, 50, 16777215);
                    } else {
                        Screen.drawCenteredString(poseStack, screen.getMinecraft().font, new TranslatableComponent("gui.aether.ascending"), screen.width / 2, 50, 16777215);
                    }
                }
            }
        } else {
            DimensionClientHooks.displayAetherTravel = false;
        }
    }
}
