package com.gildedgames.aether.client.gui.screen.perks;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.CustomizationsOptions;
import com.gildedgames.aether.client.gui.component.ColorBox;
import com.gildedgames.aether.client.gui.component.CustomizationSaveButton;
import com.gildedgames.aether.client.gui.component.CustomizationUndoButton;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.server.RankingsForcePacket;
import com.gildedgames.aether.api.AetherPlayerRankings;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.UUID;

public class AetherCustomizationsScreen extends Screen
{
    private final Screen lastScreen;
    private final CustomizationsOptions customizations = CustomizationsOptions.INSTANCE;

    public static final ResourceLocation SAVE_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/config/save_button.png");
    public static final ResourceLocation UNDO_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/config/undo_button.png");

    public AetherCustomizationsScreen(Screen screen) {
        super(Component.translatable("gui.aether.customization.title"));
        this.lastScreen = screen;
    }

    protected void init() {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.customizations.load();
            UUID playerUUID = this.minecraft.player.getUUID();
            int buttonCount = 0;
            if (AetherPlayerRankings.hasHalo(playerUUID)) {
                buttonCount++;
            }
            if (AetherPlayerRankings.hasDevGlow(playerUUID)) {
                buttonCount++;
            }
            int xPos = this.width / 2 - 65;
            int yPos = this.height / 2 - 10 - (int) (buttonCount * 12.5);
            int i = 0;
            this.addRenderableWidget(Button.builder(Component.translatable(this.customizations.areSleeveGloves() ? "gui.aether.customization.gloves.sleeve" : "gui.aether.customization.gloves.arm"),
                    (pressed) -> {
                        this.customizations.setAreSleeveGloves(!this.customizations.areSleeveGloves());
                        this.updateValues();
                        pressed.setMessage(Component.translatable(this.customizations.areSleeveGloves() ? "gui.aether.customization.gloves.sleeve" : "gui.aether.customization.gloves.arm"));
                    }).pos(xPos, yPos + (25 * i)).build());
            if (AetherPlayerRankings.hasHalo(playerUUID)) {
                i++;
                this.addRenderableWidget(Button.builder(Component.translatable(this.customizations.isHaloEnabled() ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"),
                        (pressed) -> {
                            this.customizations.setIsHaloEnabled(!this.customizations.isHaloEnabled());
                            this.updateValues();
                            pressed.setMessage(Component.translatable(this.customizations.isHaloEnabled() ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"));
                        }).pos(xPos, yPos + (25 * i)).build());
                ColorBox colorBox = new ColorBox("haloEnabled", "haloColor", Minecraft.getInstance().font, xPos + 155, yPos + (25 * i), 60, 20, Component.translatable("gui.aether.customization.halo.color"));
                if (this.customizations.getHaloHex() != null && !this.customizations.getHaloHex().isEmpty() && !this.customizations.getHaloHex().equals("null")) {
                    colorBox.setSavedValue(this.customizations.getHaloHex());
                    colorBox.setValue(colorBox.getSavedValue());
                }
                this.addRenderableWidget(colorBox);
                CustomizationUndoButton undoButton = new CustomizationUndoButton(colorBox, xPos + 220, yPos + (25 * i), 20, 20, 0, 20, 20, UNDO_BUTTON, 20, 60,
                        (pressed) -> {
                            if (pressed.isActive()) {
                                if (!colorBox.getSavedValue().isEmpty()) {
                                    colorBox.setValue(colorBox.getSavedValue());
                                } else if (this.customizations.getHaloHex() != null && !this.customizations.getHaloHex().isEmpty()) {
                                    colorBox.setValue(this.customizations.getHaloHex());
                                }
                            }
                        },
                        Component.translatable("gui.aether.customization.undo"));
                undoButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.customization.undo")));
                this.addRenderableWidget(undoButton);
                CustomizationSaveButton saveButton = new CustomizationSaveButton(colorBox, xPos + 245, yPos + (25 * i), 20, 20, 0, 20, 20, SAVE_BUTTON, 20, 60,
                        (pressed) -> {
                            if (pressed.isActive()) {
                                colorBox.setSavedValue(colorBox.getValue());
                                this.customizations.setHaloColor(colorBox.getSavedValue());
                                this.updateValues();
                            }
                        },
                        Component.translatable("gui.aether.customization.save"));
                saveButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.customization.save")));
                this.addRenderableWidget(saveButton);
            }
            if (AetherPlayerRankings.hasDevGlow(playerUUID)) {
                i++;
                this.addRenderableWidget(Button.builder(Component.translatable(this.customizations.isDeveloperGlowEnabled() ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"),
                        (pressed) -> {
                            this.customizations.setIsDeveloperGlowEnabled(!this.customizations.isDeveloperGlowEnabled());
                            this.updateValues();
                            pressed.setMessage(Component.translatable(this.customizations.isDeveloperGlowEnabled() ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"));
                        }).pos(xPos, yPos + (25 * i)).build());
                ColorBox colorBox = new ColorBox("developerGlowEnabled", "developerGlowColor", Minecraft.getInstance().font, xPos + 155, yPos + (25 * i), 60, 20, Component.translatable("gui.aether.customization.developer_glow.color"));
                if (this.customizations.getDeveloperGlowHex() != null && !this.customizations.getDeveloperGlowHex().isEmpty() && !this.customizations.getDeveloperGlowHex().equals("null")) {
                    colorBox.setSavedValue(this.customizations.getDeveloperGlowHex());
                    colorBox.setValue(colorBox.getSavedValue());
                }
                this.addRenderableWidget(colorBox);
                CustomizationUndoButton undoButton = new CustomizationUndoButton(colorBox, xPos + 220, yPos + (25 * i), 20, 20, 0, 20, 20, UNDO_BUTTON, 20, 60,
                        (pressed) -> {
                            if (pressed.isActive()) {
                                if (!colorBox.getSavedValue().isEmpty()) {
                                    colorBox.setValue(colorBox.getSavedValue());
                                } else if (this.customizations.getDeveloperGlowHex() != null && !this.customizations.getDeveloperGlowHex().isEmpty()) {
                                    colorBox.setValue(this.customizations.getDeveloperGlowHex());
                                }
                            }
                        },
                        Component.translatable("gui.aether.customization.undo"));
                undoButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.customization.undo")));
                this.addRenderableWidget(undoButton);
                CustomizationSaveButton saveButton = new CustomizationSaveButton(colorBox, xPos + 245, yPos + (25 * i), 20, 20, 0, 20, 20, SAVE_BUTTON, 20, 60,
                        (pressed) -> {
                            if (pressed.isActive()) {
                                colorBox.setSavedValue(colorBox.getValue());
                                this.customizations.setDeveloperGlowColor(colorBox.getSavedValue());
                                this.updateValues();
                            }
                        },
                        Component.translatable("gui.aether.customization.save"));
                saveButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.customization.save")));
                this.addRenderableWidget(saveButton);
            }
        }
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (pressed) -> this.onClose()).pos(this.width / 2 - 100, this.height - 30).size(200, 20).build());
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float pPartialTick) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        int x = (this.width / 2) - 175;
        int y = (this.height / 2) + 50;
//        InventoryScreen.renderEntityInInventory(poseStack, x + 33, y, 60, (float) (x + 33 - mouseX), (float) (y - 100 - mouseY), this.minecraft.player); FIXME: Quaternions
        super.render(poseStack, mouseX, mouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        this.updateValues();
        this.minecraft.setScreen(this.lastScreen);
    }

    private void updateValues() {
        this.customizations.save();
        this.customizations.load();
        this.customizations.sync();
        if (this.minecraft != null && this.minecraft.player != null) {
            AetherPacketHandler.sendToServer(new RankingsForcePacket(this.minecraft.player.getId()));
        }
    }
}
