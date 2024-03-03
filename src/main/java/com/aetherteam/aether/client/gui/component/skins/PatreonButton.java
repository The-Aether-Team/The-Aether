package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class PatreonButton extends Button {
    public static final WidgetSprites LARGE_WIDGET = new WidgetSprites(new ResourceLocation(Aether.MODID, "skins/large_button"), new ResourceLocation(Aether.MODID, "skins/large_button_highlighted"), new ResourceLocation(Aether.MODID, "skins/large_button_highlighted"));
    public static final WidgetSprites SMALL_WIDGET = new WidgetSprites(new ResourceLocation(Aether.MODID, "skins/small_button"), new ResourceLocation(Aether.MODID, "skins/small_button_highlighted"), new ResourceLocation(Aether.MODID, "skins/small_button_highlighted"));

    private final boolean small;

    public PatreonButton(Builder builder) {
        this(builder, false);
    }

    public PatreonButton(Builder builder, boolean small) {
        super(builder);
        this.small = small;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceLocation location = LARGE_WIDGET.get(this.isActive(), this.isHoveredOrFocused());
        if (this.small) {
            location = SMALL_WIDGET.get(this.isActive(), this.isHoveredOrFocused());
        }
        guiGraphics.blitSprite(location, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.drawCenteredString(minecraft.font, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2) - 4, 16777215);
    }
}
