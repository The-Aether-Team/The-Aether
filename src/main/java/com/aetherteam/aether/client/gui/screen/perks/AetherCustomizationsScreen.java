package com.aetherteam.aether.client.gui.screen.perks;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.component.customization.*;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.server.ServerDeveloperGlowPacket;
import com.aetherteam.aether.network.packet.server.ServerHaloPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.aether.util.PerkUtil;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

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

    public AetherCustomizationsScreen(Screen screen) {
        super(Component.translatable("gui.aether.customization.title"));
        this.lastScreen = screen;
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
                this.haloToggleButton = this.addRenderableWidget(Button.builder(Component.translatable(this.haloEnabled ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"),
                        (pressed) -> {
                            this.haloEnabled = !this.haloEnabled;
                            pressed.setMessage(Component.translatable(this.haloEnabled ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"));
                        }).pos(xPos, yPos + (25 * i)).build());

                this.haloColorBox = this.addRenderableWidget(new HaloColorBox(this, this.getMinecraft().font, xPos + 155, yPos + (25 * i), 60, 20, Component.translatable("gui.aether.customization.halo.color")));
                if (this.haloColor != null && !this.haloColor.isEmpty()) {
                    this.haloColorBox.setValue(this.haloColor);
                }

                this.addRenderableWidget(new HaloCustomizationButton(this, CustomizationButton.ButtonType.UNDO, this.haloColorBox, 0, 20, 20, UNDO_BUTTON, 20, 60, Button.builder(Component.translatable("gui.aether.customization.undo"),
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
                        }
                ).pos(xPos + 220, yPos + (25 * i)).size(20, 20).tooltip(Tooltip.create(Component.translatable("gui.aether.customization.undo")))));

                this.addRenderableWidget(new HaloCustomizationButton(this, CustomizationButton.ButtonType.SAVE, this.haloColorBox, 0, 20, 20, SAVE_BUTTON, 20, 60, Button.builder(Component.translatable("gui.aether.customization.save"),
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
                                if (this.haloEnabled) {
                                    AetherPacketHandler.sendToServer(new ServerHaloPacket.Apply(this.getMinecraft().player.getUUID(), new Halo(this.haloColor)));
                                } else {
                                    AetherPacketHandler.sendToServer(new ServerHaloPacket.Remove(this.getMinecraft().player.getUUID()));
                                }
                                this.customizations.save();
                                this.customizations.load();
                            }
                        }
                ).pos(xPos + 245, yPos + (25 * i)).size(20, 20).tooltip(Tooltip.create(Component.translatable("gui.aether.customization.save")))));
            }
            if (hasDeveloperGlow) {
                i++;
                this.developerGlowToggleButton = this.addRenderableWidget(Button.builder(Component.translatable(this.developerGlowEnabled ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"),
                        (pressed) -> {
                            this.developerGlowEnabled = !this.developerGlowEnabled;
                            pressed.setMessage(Component.translatable(this.developerGlowEnabled ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"));
                        }).pos(xPos, yPos + (25 * i)).build());

                this.developerGlowColorBox = this.addRenderableWidget(new DeveloperGlowColorBox(this, this.getMinecraft().font, xPos + 155, yPos + (25 * i), 60, 20, Component.translatable("gui.aether.customization.developer_glow.color")));
                if (this.developerGlowColor != null && !this.developerGlowColor.isEmpty()) {
                    this.developerGlowColorBox.setValue(this.developerGlowColor);
                }

                this.addRenderableWidget(new DeveloperGlowCustomizationButton(this, CustomizationButton.ButtonType.UNDO, this.developerGlowColorBox, 0, 20, 20, UNDO_BUTTON, 20, 60, Button.builder(Component.translatable("gui.aether.customization.undo"),
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
                        }
                ).pos(xPos + 220, yPos + (25 * i)).size(20, 20).tooltip(Tooltip.create(Component.translatable("gui.aether.customization.undo")))));

                this.addRenderableWidget(new DeveloperGlowCustomizationButton(this, CustomizationButton.ButtonType.SAVE, this.developerGlowColorBox, 0, 20, 20, SAVE_BUTTON, 20, 60, Button.builder(Component.translatable("gui.aether.customization.save"),
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
                                if (this.developerGlowEnabled) {
                                    AetherPacketHandler.sendToServer(new ServerDeveloperGlowPacket.Apply(this.getMinecraft().player.getUUID(), new DeveloperGlow(this.developerGlowColor)));
                                } else {
                                    AetherPacketHandler.sendToServer(new ServerDeveloperGlowPacket.Remove(this.getMinecraft().player.getUUID()));
                                }
                                this.customizations.save();
                                this.customizations.load();
                            }
                        }
                ).pos(xPos + 245, yPos + (25 * i)).size(20, 20).tooltip(Tooltip.create(Component.translatable("gui.aether.customization.save")))));
            }
        }
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (pressed) -> this.onClose()).pos(this.width / 2 - 100, this.height - 30).size(200, 20).build());
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float pPartialTick) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        if (this.getMinecraft().player != null) {
            int x = (this.width / 2) - 175;
            int y = (this.height / 2) + 50;
            InventoryScreen.renderEntityInInventoryFollowsMouse(poseStack, x + 33, y, 60, (float) (x + 33 - mouseX), (float) (y - 100 - mouseY), this.minecraft.player);
        }
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
        super.render(poseStack, mouseX, mouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        this.getMinecraft().setScreen(this.lastScreen);
    }
}
