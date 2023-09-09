package com.aetherteam.aether.client.gui.screen.perks;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.component.customization.*;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.ServerDeveloperGlowPacket;
import com.aetherteam.aether.network.packet.serverbound.ServerHaloPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * A screen for changing perk-related options in-game.
 * @see CustomizationsOptions
 */
public class AetherCustomizationsScreen extends Screen {
    public static final ResourceLocation SAVE_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/perks/customization/save_button.png");
    public static final ResourceLocation UNDO_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/perks/customization/undo_button.png");

    private final Screen lastScreen;
    private final CustomizationsOptions customizations = CustomizationsOptions.INSTANCE;
    private Button haloToggleButton;
    private ColorBox haloColorBox;
    private Button developerGlowToggleButton;
    private ColorBox developerGlowColorBox;

    public boolean haloEnabled;
    public String haloColor;
    public boolean developerGlowEnabled;
    public String developerGlowColor;

    public AetherCustomizationsScreen(Screen lastScreen) {
        super(Component.translatable("gui.aether.customization.title"));
        this.lastScreen = lastScreen;
    }

    protected void init() {
        this.customizations.load();

        this.haloEnabled = this.customizations.isHaloEnabled();
        this.haloColor = this.customizations.getHaloHex();
        this.developerGlowEnabled = this.customizations.isDeveloperGlowEnabled();
        this.developerGlowColor = this.customizations.getDeveloperGlowHex();

        if (this.getMinecraft().player != null) {
            User user = UserData.Client.getClientUser();
            boolean hasHalo = user != null && PerkUtil.hasHalo().test(user);
            boolean hasDeveloperGlow = user != null && PerkUtil.hasDeveloperGlow().test(user);

            int buttonCount = 0;
            if (hasHalo) {
                buttonCount++;
            }
            if (hasDeveloperGlow) {
                buttonCount++;
            }

            int xPos = this.width / 2 - 65;
            int yPos = this.height / 2 - 10 - (int) (buttonCount * 12.5);
            int i = 0;
            if (hasHalo) {
                i++;
                this.setupHaloOptions(xPos, yPos, i);
            }
            if (hasDeveloperGlow) {
                i++;
                this.setupDeveloperGlowOptions(xPos, yPos, i);
            }
        }
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (pressed) -> this.onClose()).bounds(this.width / 2 - 100, this.height - 30, 200, 20).build());
    }

    /**
     * Sets up the widgets for toggling the Halo rendering, changing the Halo color, and saving or undoing changes.
     * @param xPos The {@link Integer} x-position for widgets.
     * @param yPos The {@link Integer} y-position for widgets.
     * @param i The {@link Integer} offset multiplier for widgets.
     */
    private void setupHaloOptions(int xPos, int yPos, int i) {
        this.haloToggleButton = this.addRenderableWidget(Button.builder(Component.translatable(this.haloEnabled ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"),
            (pressed) -> {
                this.haloEnabled = !this.haloEnabled;
                pressed.setMessage(Component.translatable(this.haloEnabled ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"));
            }).pos(xPos, yPos + (25 * i)).build());

        this.haloColorBox = this.addRenderableWidget(new HaloColorBox(this, this.getMinecraft().font, xPos + 155, yPos + (25 * i), 60, 20, Component.translatable("gui.aether.customization.halo.color")));
        if (this.haloColor != null && !this.haloColor.isEmpty()) {
            this.haloColorBox.setValue(this.haloColor);
        }

        // Resets to the currently stored settings in the game.
        Component undoText = Component.translatable("gui.aether.customization.undo");
        HaloCustomizationButton undoButton = new HaloCustomizationButton(this, CustomizationButton.ButtonType.UNDO, this.haloColorBox, xPos + 220, yPos + (25 * i), 20, 20, 0, 20, 20, UNDO_BUTTON, 20, 60,
            (pressed) -> {
                if (pressed.isActive()) {
                    this.haloEnabled = this.customizations.isHaloEnabled();
                    this.haloColor = this.customizations.getHaloHex();
                    this.haloToggleButton.setMessage(Component.translatable(this.haloEnabled ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"));
                    if (this.haloColor != null && !this.haloColor.isEmpty()) {
                        this.haloColorBox.setValue(this.haloColor);
                    } else {
                        this.haloColorBox.setValue("");
                    }
                    this.customizations.load();
                }
            }, undoText
        );
        undoButton.setTooltip(Tooltip.create(undoText));
        this.addRenderableWidget(undoButton);

        // Saves and stores settings to the game.
        Component saveText = Component.translatable("gui.aether.customization.save");
        HaloCustomizationButton saveButton = new HaloCustomizationButton(this, CustomizationButton.ButtonType.SAVE, this.haloColorBox, xPos + 245, yPos + (25 * i), 20, 20, 0, 20, 20, SAVE_BUTTON, 20, 60,
            (pressed) -> {
                if (pressed.isActive()) {
                    if (this.haloColorBox.hasValidColor() && this.haloColorBox.hasTextChanged()) {
                        this.customizations.setHaloColor(this.haloColorBox.getValue());
                        this.haloColor = this.customizations.getHaloHex();
                    }
                    if (this.haloEnabled != this.customizations.isHaloEnabled()) {
                        this.customizations.setIsHaloEnabled(this.haloEnabled);
                        this.haloEnabled = this.customizations.isHaloEnabled();
                    }
                    // Propagate changes to the server for other players to see.
                    if (this.haloEnabled) {
                        if (this.getMinecraft().player != null) {
                            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerHaloPacket.Apply(this.getMinecraft().player.getUUID(), new Halo(this.haloColor)));
                        }
                    } else {
                        if (this.getMinecraft().player != null) {
                            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerHaloPacket.Remove(this.getMinecraft().player.getUUID()));
                        }
                    }
                    this.customizations.save();
                    this.customizations.load();
                }
            }, saveText
        );
        saveButton.setTooltip(Tooltip.create(saveText));
        this.addRenderableWidget(saveButton);
    }

    /**
     * Sets up the widgets for toggling the Developer Glow rendering, changing the Developer Glow color, and saving or undoing changes.
     * @param xPos The {@link Integer} x-position for widgets.
     * @param yPos The {@link Integer} y-position for widgets.
     * @param i The {@link Integer} offset multiplier for widgets.
     */
    private void setupDeveloperGlowOptions(int xPos, int yPos, int i) {
        this.developerGlowToggleButton = this.addRenderableWidget(Button.builder(Component.translatable(this.developerGlowEnabled ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"),
            (pressed) -> {
                this.developerGlowEnabled = !this.developerGlowEnabled;
                pressed.setMessage(Component.translatable(this.developerGlowEnabled ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"));
            }).pos(xPos, yPos + (25 * i)).build());

        this.developerGlowColorBox = this.addRenderableWidget(new DeveloperGlowColorBox(this, this.getMinecraft().font, xPos + 155, yPos + (25 * i), 60, 20, Component.translatable("gui.aether.customization.developer_glow.color")));
        if (this.developerGlowColor != null && !this.developerGlowColor.isEmpty()) {
            this.developerGlowColorBox.setValue(this.developerGlowColor);
        }

        // Resets to the currently stored settings in the game.
        Component undoText = Component.translatable("gui.aether.customization.undo");
        DeveloperGlowCustomizationButton undoButton = new DeveloperGlowCustomizationButton(this, CustomizationButton.ButtonType.UNDO, this.developerGlowColorBox, xPos + 220, yPos + (25 * i), 20, 20, 0, 20, 20, UNDO_BUTTON, 20, 60,
            (pressed) -> {
                if (pressed.isActive()) {
                    this.developerGlowEnabled = this.customizations.isDeveloperGlowEnabled();
                    this.developerGlowColor = this.customizations.getDeveloperGlowHex();
                    this.developerGlowToggleButton.setMessage(Component.translatable(this.developerGlowEnabled ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"));
                    if (this.developerGlowColor != null && !this.developerGlowColor.isEmpty()) {
                        this.developerGlowColorBox.setValue(this.developerGlowColor);
                    } else {
                        this.developerGlowColorBox.setValue("");
                    }
                    this.customizations.load();
                }
            }, undoText
        );
        undoButton.setTooltip(Tooltip.create(undoText));
        this.addRenderableWidget(undoButton);

        // Saves and stores settings to the game.
        Component saveText = Component.translatable("gui.aether.customization.save");
        DeveloperGlowCustomizationButton saveButton = new DeveloperGlowCustomizationButton(this, CustomizationButton.ButtonType.SAVE, this.developerGlowColorBox, xPos + 245, yPos + (25 * i), 20, 20, 0, 20, 20, SAVE_BUTTON, 20, 60,
            (pressed) -> {
                if (pressed.isActive()) {
                    if (this.developerGlowColorBox.hasValidColor() && this.developerGlowColorBox.hasTextChanged()) {
                        this.customizations.setDeveloperGlowColor(this.developerGlowColorBox.getValue());
                        this.developerGlowColor = this.customizations.getDeveloperGlowHex();
                    }
                    if (this.developerGlowEnabled != this.customizations.isDeveloperGlowEnabled()) {
                        this.customizations.setIsDeveloperGlowEnabled(this.developerGlowEnabled);
                        this.developerGlowEnabled = this.customizations.isDeveloperGlowEnabled();
                    }
                    // Propagate changes to the server for other players to see.
                    if (this.developerGlowEnabled) {
                        if (this.getMinecraft().player != null) {
                            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerDeveloperGlowPacket.Apply(this.getMinecraft().player.getUUID(), new DeveloperGlow(this.developerGlowColor)));
                        }
                    } else {
                        if (this.getMinecraft().player != null) {
                            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerDeveloperGlowPacket.Remove(this.getMinecraft().player.getUUID()));
                        }
                    }
                    this.customizations.save();
                    this.customizations.load();
                }
            }, saveText
        );
        saveButton.setTooltip(Tooltip.create(saveText));
        this.addRenderableWidget(saveButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.getTitle(), this.width / 2, 15, 16777215);
        guiGraphics.drawCenteredString(this.font, Component.translatable("gui.aether.customization.color"), (this.width / 2 - 65) + 184, (this.height / 2 - 10) - 14, 16777215);
        if (this.getMinecraft().player != null) {
            int x = (this.width / 2) - 175;
            int y = (this.height / 2) + 50;
            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, x + 33, y, 60, (float) (x + 33 - mouseX), (float) (y - 100 - mouseY), this.getMinecraft().player);
        }
        // Resets color values if they're invalid.
        if (this.haloColorBox != null && !this.haloColorBox.getValue().equals(this.haloColor)) {
            if (this.haloColorBox.getValue().length() == 6 && this.haloColorBox.hasValidColor()) {
                this.haloColor = this.haloColorBox.getValue();
            } else if (!this.haloColor.isEmpty()) {
                this.haloColor = "";
            }
        }
        if (this.developerGlowColorBox != null && !this.developerGlowColorBox.getValue().equals(this.developerGlowColor)) {
            if (this.developerGlowColorBox.getValue().length() == 6 && this.developerGlowColorBox.hasValidColor()) {
                this.developerGlowColor = this.developerGlowColorBox.getValue();
            } else if (!this.developerGlowColor.isEmpty()) {
                this.developerGlowColor = "";
            }
        }
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        this.getMinecraft().setScreen(this.lastScreen);
    }
}
