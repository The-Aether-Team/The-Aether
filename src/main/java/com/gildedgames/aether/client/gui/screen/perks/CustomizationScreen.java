package com.gildedgames.aether.client.gui.screen.perks;

import com.gildedgames.aether.core.util.SkinCustomizations;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;

public class CustomizationScreen extends Screen
{
    private final Screen lastScreen;
    private final SkinCustomizations customizations = SkinCustomizations.INSTANCE;

    public CustomizationScreen(Screen screen) {
        super(new TranslatableComponent("gui.aether.customization.title"));
        this.lastScreen = screen;
    }

    protected void init() {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.customizations.load();
            this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 6, 150, 20,
                    new TranslatableComponent(this.customizations.areSleeveGloves() ? "gui.aether.customization.gloves.sleeve" : "gui.aether.customization.gloves.arm"),
                    (pressed) -> this.customizations.setAreSleeveGloves(!this.customizations.areSleeveGloves()),
                    (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(this.customizations.areSleeveGloves() ? "gui.aether.customization.gloves.sleeve" : "gui.aether.customization.gloves.arm"))
            ));
            this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 4, 150, 20,
                    new TranslatableComponent(this.customizations.isHaloEnabled() ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"),
                    (pressed) -> this.customizations.setIsHaloEnabled(!this.customizations.isHaloEnabled()),
                    (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(this.customizations.isHaloEnabled() ? "gui.aether.customization.halo.on" : "gui.aether.customization.halo.off"))
            ));
            this.addRenderableWidget(new Button(this.width / 2 - 75, this.height / 2, 150, 20,
                    new TranslatableComponent(this.customizations.isDeveloperGlowEnabled() ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"),
                    (pressed) -> this.customizations.setIsDeveloperGlowEnabled(!this.customizations.isDeveloperGlowEnabled()),
                    (button, poseStack, x, y) -> button.setMessage(new TranslatableComponent(this.customizations.isDeveloperGlowEnabled() ? "gui.aether.customization.developer_glow.on" : "gui.aether.customization.developer_glow.off"))
            ));
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
