package com.gildedgames.aether.client.gui.screen.perks;

import com.gildedgames.aether.core.capability.interfaces.IAetherRankings;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;

public class CustomizationScreen extends Screen
{
    private final Screen lastScreen;

    public CustomizationScreen(Screen screen) {
        super(new TranslatableComponent("gui.aether.customization.title"));
        this.lastScreen = screen;
    }

    protected void init() {
        if (this.minecraft.player != null) {
            IAetherRankings.get(this.minecraft.player).ifPresent(aetherRankings -> {
                this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 6, 150, 20,
                        new TranslatableComponent(aetherRankings.areSleeveGloves() ? "gui.aether.customization.gloves.sleeve" : "gui.aether.customization.gloves.arm"),
                        (pressed) -> aetherRankings.setSleeveGloves(!aetherRankings.areSleeveGloves()),
                        (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(aetherRankings.areSleeveGloves() ? "gui.aether.customization.gloves.sleeve" : "gui.aether.customization.gloves.arm"))
                ));
                this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 4, 150, 20,
                        new TranslatableComponent(aetherRankings.shouldRenderHalo() ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"),
                        (pressed) -> aetherRankings.setRenderHalo(!aetherRankings.shouldRenderHalo()),
                        (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(aetherRankings.shouldRenderHalo() ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"))
                ));
                this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 2, 150, 20,
                        new TranslatableComponent(aetherRankings.shouldRenderDeveloperGlow() ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"),
                        (pressed) -> aetherRankings.setRenderDeveloperGlow(!aetherRankings.shouldRenderDeveloperGlow()),
                        (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(aetherRankings.shouldRenderDeveloperGlow() ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"))
                ));
            });
        }


        //Might move this up a bit depending on spacing.
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, (pressed) -> this.minecraft.setScreen(this.lastScreen)));
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        drawCenteredString(pPoseStack, this.font, this.title, this.width / 2, 15, 16777215);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }
}
