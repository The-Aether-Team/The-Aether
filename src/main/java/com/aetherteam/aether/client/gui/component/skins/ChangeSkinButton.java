package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.Aether;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class ChangeSkinButton extends Button {
    public static final WidgetSprites APPLY_WIDGET = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "skins/apply_button"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "skins/apply_button_disabled"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "skins/apply_button_highlighted"));
    public static final WidgetSprites REMOVE_WIDGET = new WidgetSprites(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "skins/remove_button"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "skins/remove_button_disabled"), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "skins/remove_button_highlighted"));

    private final ButtonType buttonType;

    public ChangeSkinButton(ButtonType buttonType, Builder builder) {
        super(builder);
        this.buttonType = buttonType;
        this.active = false;
    }

    public void onPress() {
        if (this.isActive()) {
            this.onPress.onPress(this);
        }
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        ResourceLocation location;
        if (this.buttonType == ButtonType.APPLY) {
            location = APPLY_WIDGET.get(this.isActive(), this.isHovered());
        } else {
            location = REMOVE_WIDGET.get(this.isActive(), this.isHovered());
        }
        guiGraphics.blitSprite(location, this.getX(), this.getY(), 7, 7);
    }

    public enum ButtonType {
        APPLY,
        REMOVE
    }
}
