package com.aetherteam.aether.client.gui.component.menu;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen;
import com.aetherteam.aether.mixin.mixins.client.accessor.ButtonAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AetherMenuButton extends Button {
    private static final WidgetSprites AETHER_WIDGETS = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "title/button"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "title/button_highlighted"));
    private static final WidgetSprites AETHER_WIDGETS_SMALL = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "title/button"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "title/button_highlighted_small"));
    public final int originalX;
    public final int originalY;
    public int hoverOffset;
    public int buttonCountOffset;
    public boolean serverButton;

    public AetherMenuButton(AetherTitleScreen screen, Builder builder) {
        super(builder);
        this.originalX = this.getX();
        this.originalY = this.getY();
        this.hoverOffset = 0;
    }

    public AetherMenuButton(AetherTitleScreen screen, Button oldButton) {
        this(screen, new Builder(oldButton.getMessage(), (button) -> oldButton.onPress()).bounds(oldButton.getX(), oldButton.getY(), oldButton.getWidth(), oldButton.getHeight()).createNarration((button) -> ((ButtonAccessor) oldButton).callCreateNarrationMessage()));
        oldButton.visible = false;
        oldButton.active = false;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        PoseStack poseStack = guiGraphics.pose();
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        ResourceLocation location = this.getWidth() < 100 ? AETHER_WIDGETS_SMALL.get(this.isActive(), this.isHoveredOrFocused()) : AETHER_WIDGETS.get(this.isActive(), this.isHoveredOrFocused());

        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        guiGraphics.blitSprite(location, 200, 20, 0, 0, this.getX() + this.hoverOffset, this.getY(), 200, 20);
        RenderSystem.disableBlend();

        poseStack.pushPose();
        float textX = this.getX() + 35 + this.hoverOffset;
        float textY = this.getY() + (this.height - 8) / 2.0F;
        poseStack.translate(textX, textY, 0.0F);
        guiGraphics.drawString(font, this.getMessage(), 0, 0, this.getTextColor(mouseX, mouseY) | Mth.ceil(this.alpha * 255.0F) << 24);
        poseStack.popPose();
    }

    /**
     * Determines the color for the button text depending on if its hovered over.
     *
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @return The decimal {@link Integer} for the color.
     */
    public int getTextColor(int mouseX, int mouseY) {
        if (!this.serverButton) {
            return this.isMouseOver(mouseX, mouseY) ? 11842776 : 13948116;
        } else {
            return this.isMouseOver(mouseX, mouseY) ? 13746759 : 15457113;
        }
    }
}
