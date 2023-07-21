package com.aetherteam.aether.client.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * This is a widget that allows the player to select a line of dialogue to say to an NPC.
 */
public class PlayerDialogueButton extends Button {
    public PlayerDialogueButton(MutableComponent pMessage, Button.OnPress onPress) {
        super(0, 0, 0, 12, appendBrackets(pMessage), onPress, DEFAULT_NARRATION);
        this.width = Minecraft.getInstance().font.width(this.getMessage()) + 2;
    }

    @Override
    public void renderWidget(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        fillGradient(pPoseStack, this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, 0x66000000, 0x66000000);
        drawString(pPoseStack, Minecraft.getInstance().font, this.getMessage(), this.getX() + 1, this.getY() + 1, this.isHovered ? 0xFFFF55: 0xFFFFFF);
    }

    public static MutableComponent appendBrackets(MutableComponent component) {
        return Component.literal("[").append(component).append("]");
    }
}
