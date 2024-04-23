package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherKeys;
import com.aetherteam.aether.client.gui.component.inventory.AccessoryButton;
import com.aetherteam.aether.client.gui.component.skins.RefreshButton;
import com.aetherteam.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.entity.AetherBossMob;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import com.aetherteam.aether.inventory.menu.AccessoriesMenu;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.OpenAccessoriesPacket;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.fabricators_of_create.porting_lib.mixin.accessors.client.accessor.AbstractContainerScreenAccessor;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Tuple;
import net.minecraft.world.BossEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiHooks {
    /**
     * Set of UUIDs of boss bars that belong to Aether bosses.
     */
    public static final Map<UUID, Integer> BOSS_EVENTS = new HashMap<>();
    private static boolean shouldAddButton = true;
    private static boolean generateTrivia = true;
    @Nullable
    private static Screen lastScreen = null;

    /**
     * Checks whether the accessory button isn't disabled by {@link AetherConfig.Client#disable_accessory_button} or accessory tags being empty.
     * @return The {@link Boolean} value.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onGuiInitialize(ScreenEvent.Init.Post)
     */
    public static boolean isAccessoryButtonEnabled() {
        return !AetherConfig.CLIENT.disable_accessory_button.get() && areAccessoryTagsFilled();
    }

    /**
     * @return Whether any tags for accessories are empty, as a {@link Boolean}.
     */
    private static boolean areAccessoryTagsFilled() {
        boolean flag = true;
        for (String string : AccessoriesMenu.AETHER_IDENTIFIERS) {
            if (BuiltInRegistries.ITEM.getTagOrEmpty(TagKey.create(Registries.ITEM, new ResourceLocation("curios", string))).spliterator().estimateSize() == 0) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Creates an {@link AccessoryButton} if one can be created for the screen according to {@link GuiHooks#canCreateAccessoryButtonForScreen(Screen)}.
     * @param screen The parent {@link Screen}.
     * @param offsets A {@link Tuple} containing the x and y offset {@link Integer}s.
     * @return The {@link AccessoryButton}.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onGuiInitialize(ScreenEvent.Init.Post)
     */
    @Nullable
    public static AccessoryButton setupAccessoryButton(Screen screen, Tuple<Integer, Integer> offsets) {
        AbstractContainerScreen<?> containerScreen = canCreateAccessoryButtonForScreen(screen);
        if (containerScreen != null) {
            return new AccessoryButton(containerScreen, ((AbstractContainerScreenAccessor)containerScreen).port_lib$getGuiLeft() + offsets.getA(), ((AbstractContainerScreenAccessor)containerScreen).port_lib$getGuiTop() + offsets.getB(), AccessoriesScreen.ACCESSORIES_BUTTON);
        }
        return null;
    }

    /**
     * Checks whether the screen is an inventory screen or if it's the {@link AccessoriesScreen} and a button can be added to it.
     * If the screen is the {@link AccessoriesScreen}, then it sets the {@link AccessoryButton} should be rendered in it.
     * @param screen The parent {@link Screen}.
     * @return The parent screen, cast to a {@link AbstractContainerScreen}.
     */
    @Nullable
    private static AbstractContainerScreen<?> canCreateAccessoryButtonForScreen(Screen screen) {
        if (screen instanceof InventoryScreen || /*screen instanceof CuriosScreen TODO: PORT ||*/ screen instanceof CreativeModeInventoryScreen || (screen instanceof AccessoriesScreen && shouldAddButton)) {
            return (AbstractContainerScreen<?>) screen;
        } else if (screen instanceof AccessoriesScreen) {
            shouldAddButton = true;
        }
        return null;
    }

    /**
     * Sets up the buttons for the {@link MoaSkinsScreen} and the {@link AetherCustomizationsScreen} in a {@link GridLayout}.
     * @param screen The parent {@link Screen}.
     * @return The {@link GridLayout} holding the buttons.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onGuiInitialize(ScreenEvent.Init.Post)
     */
    @Nullable
    public static GridLayout setupPerksButtons(Screen screen) {
        if (screen instanceof PauseScreen) {
            int x = AetherConfig.CLIENT.layout_perks_x.get();
            int y = AetherConfig.CLIENT.layout_perks_y.get();

            // Sets up the GridLayout.
            GridLayout gridLayout = new GridLayout();
            gridLayout.defaultCellSetting().padding(4, 4, 4, 0);
            GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(1);

            createSkinsButton(screen, gridLayout, rowHelper); // Skins button.

            User user = UserData.Client.getClientUser();
            if (user != null && (PerkUtil.hasDeveloperGlow().test(user) || PerkUtil.hasHalo().test(user))) { // Only add the customizations button if the User has perks.
                createCustomizationsButton(screen, rowHelper); // Customizations button.
            } else {
                y -= 6;
            }

            // Arranges and aligns the GridLayout.
            gridLayout.arrangeElements();
            FrameLayout.alignInRectangle(gridLayout, x, y, screen.width, screen.height, 0.5F, 0.25F);

            return gridLayout;
        }
        return null;
    }

    /**
     * Creates the button for the {@link MoaSkinsScreen}.
     * @param screen The parent {@link Screen}.
     * @param gridLayout The {@link GridLayout} for the button.
     * @param rowHelper The {@link net.minecraft.client.gui.layouts.GridLayout.RowHelper} to add the button to.
     */
    private static void createSkinsButton(Screen screen, GridLayout gridLayout, GridLayout.RowHelper rowHelper) {
        ImageButton skinsButton = new ImageButton(0, 0, 20, 20, 0, 0, 20, AccessoriesScreen.SKINS_BUTTON, 20, 40,
                (pressed) -> Minecraft.getInstance().setScreen(new MoaSkinsScreen(screen)),
                Component.translatable("gui.aether.accessories.skins_button"));
        skinsButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.accessories.skins_button")));
        rowHelper.addChild(skinsButton, gridLayout.newCellSettings().paddingTop(58));
    }

    /**
     * Creates the button for the {@link AetherCustomizationsScreen}.
     * @param screen The parent {@link Screen}.
     * @param rowHelper The {@link net.minecraft.client.gui.layouts.GridLayout.RowHelper} to add the button to.
     */
    private static void createCustomizationsButton(Screen screen, GridLayout.RowHelper rowHelper) {
        ImageButton customizationButton = new ImageButton(0, 0, 20, 20, 0, 0, 20, AccessoriesScreen.CUSTOMIZATION_BUTTON, 20, 40,
                (pressed) -> Minecraft.getInstance().setScreen(new AetherCustomizationsScreen(screen)),
                Component.translatable("gui.aether.accessories.customization_button"));
        customizationButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.accessories.customization_button")));
        rowHelper.addChild(customizationButton);
    }

    /**
     * Generates and draws the Aether's trivia lines in various loading screens.
     * @param screen The current {@link Screen}.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onGuiDraw(ScreenEvent.Render.Post)
     */
    public static void drawTrivia(Screen screen, GuiGraphics guiGraphics) {
        generateTrivia(screen);
        if (screen instanceof GenericDirtMessageScreen || screen instanceof LevelLoadingScreen || screen instanceof ReceivingLevelScreen) {
            Component triviaLine = Aether.TRIVIA_READER.getTriviaLine(); // Get the current trivia line to display.
            if (triviaLine != null && AetherConfig.CLIENT.enable_trivia.get()) {
                Font font = Minecraft.getInstance().font;
                int y = (screen.height - 7) - font.wordWrapHeight(triviaLine, screen.width);
                for (FormattedCharSequence sequence : font.split(triviaLine, screen.width)) {
                    guiGraphics.drawCenteredString(font, sequence, screen.width / 2, y, 16777113);
                    y += 9;
                }
            }
            if (screen != lastScreen) { // Randomize the trivia to display if a new screen has been switched to.
                if (!Aether.TRIVIA_READER.getTrivia().isEmpty()) {
                    Aether.TRIVIA_READER.randomizeTriviaIndex();
                }
            }
        }
        lastScreen = screen;
    }

    /**
     * Generates the trivia lines for display.
     * @param screen The current {@link Screen}.
     */
    private static void generateTrivia(Screen screen) {
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
    }

    /**
     * Draws text for leaving and entering the Aether.
     * Checks for when to display different text are handled by {@link DimensionHooks}.
     * @param screen The current {@link Screen}.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onGuiDraw(ScreenEvent.Render.Post)
     */
    public static void drawAetherTravelMessage(Screen screen, GuiGraphics guiGraphics) {
        if (screen instanceof ReceivingLevelScreen || screen instanceof ProgressScreen) {
            if (Minecraft.getInstance().player != null) {
                if (DimensionHooks.displayAetherTravel) {
                    if (DimensionHooks.playerLeavingAether) {
                        guiGraphics.drawCenteredString(Screens.getTextRenderer(screen), Component.translatable("gui.aether.descending"), screen.width / 2, AetherConfig.CLIENT.portal_text_y.get(), 16777215);
                    } else {
                        guiGraphics.drawCenteredString(Screens.getTextRenderer(screen), Component.translatable("gui.aether.ascending"), screen.width / 2, AetherConfig.CLIENT.portal_text_y.get(), 16777215);
                    }
                }
            }
        } else {
            DimensionHooks.displayAetherTravel = false;
        }
    }

    /**
     * Handles the time until the Patreon {@link RefreshButton} can be clicked again.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onClientTick(TickEvent.ClientTickEvent)
     */
    public static void handlePatreonRefreshRebound() {
        if (RefreshButton.reboundTimer > 0) {
            RefreshButton.reboundTimer--;
        }
        if (RefreshButton.reboundTimer < 0) {
            RefreshButton.reboundTimer = 0;
        }
    }

    /**
     * Handles opening the {@link AccessoriesMenu} when clicking the {@link AetherKeys#OPEN_ACCESSORY_INVENTORY} keybind.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onKeyPress(int, int, int, int)
     */
    public static void openAccessoryMenu() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && minecraft.getOverlay() == null && minecraft.screen == null) {
            if (!AetherConfig.CLIENT.disable_accessory_button.get() && AetherKeys.OPEN_ACCESSORY_INVENTORY.consumeClick()) {
                PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new OpenAccessoriesPacket(ItemStack.EMPTY));
                shouldAddButton = false; // The AccessoryButton is not added to menus opened with the key.
            }
        }
    }

    /**
     * Allows various menus to be closed with the {@link AetherKeys#OPEN_ACCESSORY_INVENTORY} keybind.
     * @param key The {@link Integer} ID for the key.
     * @param action The {@link Integer} for the key action.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onKeyPress(int, int, int, int)
     */
    public static void closeContainerMenu(int key, int action) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.screen instanceof AbstractContainerScreen abstractContainerScreen) {
            if (!AetherConfig.CLIENT.disable_accessory_button.get() && KeyBindingHelper.getBoundKeyOf(AetherKeys.OPEN_ACCESSORY_INVENTORY).getValue() == key && (action == InputConstants.PRESS || action == InputConstants.REPEAT)) {
                abstractContainerScreen.onClose();
            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.gui.components.BossHealthOverlay#render(GuiGraphics)}
     * Modified to draw the Aether's custom boss health bars.
     * @see com.aetherteam.aether.client.event.listeners.GuiListener#onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress)
     */
    public static void drawBossHealthBar(GuiGraphics guiGraphics, int x, int y, LerpingBossEvent bossEvent) {
        int entityID = BOSS_EVENTS.get(bossEvent.getId());
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.getEntity(entityID) instanceof AetherBossMob<?> aetherBossMob) {
            drawBar(guiGraphics, x + 2, y + 2, bossEvent, aetherBossMob);
            Component component = bossEvent.getName();
            int nameLength = Minecraft.getInstance().font.width(component);
            int nameX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - nameLength / 2;
            int nameY = y - 9;
            guiGraphics.drawString(Minecraft.getInstance().font, component, nameX, nameY, 16777215);
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.gui.components.BossHealthOverlay#drawBar(GuiGraphics, int, int, BossEvent)}
     * This version of the method doesn't account for other types of boss bars because the Aether only has one.
     */
    public static void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent, AetherBossMob<?> aetherBossMob) {
        if (aetherBossMob.getBossBarTexture() != null) {
            x -= 37; // The default boss health bar is offset by -91. We need -128.
            guiGraphics.blit(aetherBossMob.getBossBarTexture(), x, y, -90, 0, 16, 256, 16, 256, 256);
            int health = (int) (bossEvent.getProgress() * 256.0F);
            if (health > 0) {
                guiGraphics.blit(aetherBossMob.getBossBarTexture(), x, y, -90, 0, 0, health, 16, 256, 256);
            }
        }
    }

    /**
     * Checks whether a boss bar belongs to an Aether boss, as determined by {@link GuiHooks#BOSS_EVENTS}.
     * @param uuid The boss {@link UUID}.
     * @return The {@link Boolean} value.
     */
    public static boolean isAetherBossBar(UUID uuid) {
        return BOSS_EVENTS.containsKey(uuid);
    }
}
