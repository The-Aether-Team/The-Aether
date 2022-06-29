package com.gildedgames.aether.client.gui.button;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

/**
 * This is a widget that allows the player to select a line of dialogue to say to an NPC.
 */
public class PlayerDialogueOption extends Button {

    public PlayerDialogueOption(Component pMessage, Button.OnPress onPress) {
        super(0, 0, 0, 12, appendBrackets(pMessage), onPress);
        this.width = Minecraft.getInstance().font.width(this.getMessage()) + 2;
    }

    @Override
    public void renderButton(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.fillGradient(pPoseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0x66000000, 0x66000000);
        drawString(pPoseStack, Minecraft.getInstance().font, this.getMessage(), this.x + 1, this.y + 1, this.isHovered ? 0xFFFF55: 0xFFFFFF);
    }

    public static Component appendBrackets(Component component) {
        return Component.literal("[").append(component).append("]");
    }
}
