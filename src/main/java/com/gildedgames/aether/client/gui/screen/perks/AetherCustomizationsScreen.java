package com.gildedgames.aether.client.gui.screen.perks;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.button.ColorBox;
import com.gildedgames.aether.client.gui.button.CustomizationSaveButton;
import com.gildedgames.aether.client.gui.button.CustomizationUndoButton;
import com.gildedgames.aether.core.registry.AetherPlayerRankings;
import com.gildedgames.aether.core.util.AetherCustomizations;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.UUID;

public class AetherCustomizationsScreen extends Screen
{
    private final Screen lastScreen;
    private final AetherCustomizations customizations = AetherCustomizations.INSTANCE;

    public static final ResourceLocation SAVE_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/config/save_button.png");
    public static final ResourceLocation UNDO_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/config/undo_button.png");

    public AetherCustomizationsScreen(Screen screen) {
        super(new TranslatableComponent("gui.aether.customization.title"));
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
            this.addRenderableWidget(new Button(xPos, yPos + (25 * i), 150, 20,
                    new TranslatableComponent(this.customizations.areSleeveGloves() ? "gui.aether.customization.gloves.sleeve" : "gui.aether.customization.gloves.arm"),
                    (pressed) -> {
                        this.customizations.setAreSleeveGloves(!this.customizations.areSleeveGloves());
                        this.updateValues();
                    },
                    (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(this.customizations.areSleeveGloves() ? "gui.aether.customization.gloves.sleeve" : "gui.aether.customization.gloves.arm"))
            ));
            if (AetherPlayerRankings.hasHalo(playerUUID)) {
                i++;
                this.addRenderableWidget(new Button(xPos, yPos + (25 * i), 150, 20,
                        new TranslatableComponent(this.customizations.isHaloEnabled() ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"),
                        (pressed) -> {
                            this.customizations.setIsHaloEnabled(!this.customizations.isHaloEnabled());
                            this.updateValues();
                        },
                        (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(this.customizations.isHaloEnabled() ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"))
                ));
                ColorBox colorBox = new ColorBox("haloEnabled", "haloColor", Minecraft.getInstance().font, xPos + 155, yPos + (25 * i), 60, 20, new TranslatableComponent("gui.aether.customization.halo.color"));
                if (this.customizations.getHaloHex() != null && !this.customizations.getHaloHex().isEmpty()) {
                    colorBox.setSavedValue(this.customizations.getHaloHex());
                    colorBox.setValue(colorBox.getSavedValue());
                }
                this.addRenderableWidget(colorBox);
                this.addRenderableWidget(new CustomizationUndoButton(colorBox, xPos + 220, yPos + (25 * i), 20, 20, 0, 20, 20, UNDO_BUTTON, 20, 60,
                        (pressed) -> {
                            if (pressed.isActive()) {
                                if (!colorBox.getSavedValue().isEmpty()) {
                                    colorBox.setValue(colorBox.getSavedValue());
                                } else if (this.customizations.getHaloHex() != null && !this.customizations.getHaloHex().isEmpty()) {
                                    colorBox.setValue(this.customizations.getHaloHex());
                                }
                            }
                        },
                        (button, poseStack, x, y) -> this.renderTooltip(poseStack, new TranslatableComponent("gui.aether.customization.undo"), x, y),
                        new TranslatableComponent("gui.aether.customization.undo"))
                );
                this.addRenderableWidget(new CustomizationSaveButton(colorBox, xPos + 245, yPos + (25 * i), 20, 20, 0, 20, 20, SAVE_BUTTON, 20, 60,
                        (pressed) -> {
                            if (pressed.isActive()) {
                                colorBox.setSavedValue(colorBox.getValue());
                                this.customizations.setHaloColor(colorBox.getSavedValue());
                                this.updateValues();
                            }
                        },
                        (button, poseStack, x, y) -> this.renderTooltip(poseStack, new TranslatableComponent("gui.aether.customization.save"), x, y),
                        new TranslatableComponent("gui.aether.customization.save"))
                );
            }
            if (AetherPlayerRankings.hasDevGlow(playerUUID)) {
                i++;
                this.addRenderableWidget(new Button(xPos, yPos + (25 * i), 150, 20,
                        new TranslatableComponent(this.customizations.isDeveloperGlowEnabled() ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"),
                        (pressed) -> {
                            this.customizations.setIsDeveloperGlowEnabled(!this.customizations.isDeveloperGlowEnabled());
                            this.updateValues();
                        },
                        (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(this.customizations.isDeveloperGlowEnabled() ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"))
                ));
                ColorBox colorBox = new ColorBox("developerGlowEnabled", "developerGlowColor", Minecraft.getInstance().font, xPos + 155, yPos + (25 * i), 60, 20, new TranslatableComponent("gui.aether.customization.developer_glow.color"));
                if (this.customizations.getDeveloperGlowHex() != null && !this.customizations.getDeveloperGlowHex().isEmpty()) {
                    colorBox.setSavedValue(this.customizations.getDeveloperGlowHex());
                    colorBox.setValue(colorBox.getSavedValue());
                }
                this.addRenderableWidget(colorBox);
                this.addRenderableWidget(new CustomizationUndoButton(colorBox, xPos + 220, yPos + (25 * i), 20, 20, 0, 20, 20, UNDO_BUTTON, 20, 60,
                        (pressed) -> {
                            if (pressed.isActive()) {
                                if (!colorBox.getSavedValue().isEmpty()) {
                                    colorBox.setValue(colorBox.getSavedValue());
                                } else if (this.customizations.getDeveloperGlowHex() != null && !this.customizations.getDeveloperGlowHex().isEmpty()) {
                                    colorBox.setValue(this.customizations.getDeveloperGlowHex());
                                }
                            }

                        },
                        (button, poseStack, x, y) -> this.renderTooltip(poseStack, new TranslatableComponent("gui.aether.customization.undo"), x, y),
                        new TranslatableComponent("gui.aether.customization.undo"))
                );
                this.addRenderableWidget(new CustomizationSaveButton(colorBox, xPos + 245, yPos + (25 * i), 20, 20, 0, 20, 20, SAVE_BUTTON, 20, 60,
                        (pressed) -> {
                            if (pressed.isActive()) {
                                colorBox.setSavedValue(colorBox.getValue());
                                this.customizations.setDeveloperGlowColor(colorBox.getSavedValue());
                                this.updateValues();
                            }
                        },
                        (button, poseStack, x, y) -> this.renderTooltip(poseStack, new TranslatableComponent("gui.aether.customization.save"), x, y),
                        new TranslatableComponent("gui.aether.customization.save"))
                );
            }
        }
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 30, 200, 20, CommonComponents.GUI_DONE, (pressed) -> this.onClose()));
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float pPartialTick) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        int x = (this.width / 2) - 175;
        int y = (this.height / 2) + 50;
        InventoryScreen.renderEntityInInventory(x + 33, y, 60, (float) (x + 33 - mouseX), (float) (y - 100 - mouseY), this.minecraft.player);
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
        this.customizations.sync(Minecraft.getInstance().player);
    }
}
