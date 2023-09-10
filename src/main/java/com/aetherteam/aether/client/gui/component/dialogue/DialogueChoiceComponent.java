package com.aetherteam.aether.client.gui.component.dialogue;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * A button widget that allows the player to select a line of dialogue to say to an NPC.
 */
public class DialogueChoiceComponent extends Button {
    public DialogueChoiceComponent(MutableComponent message, Button.OnPress onPress) {
        super(0, 0, 0, 12, appendBrackets(message), onPress);
        this.width = Minecraft.getInstance().font.width(this.getMessage()) + 2;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        GuiComponent.fillGradient(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0x66000000, 0x66000000, 0);
        GuiComponent.drawString(poseStack, Minecraft.getInstance().font, this.getMessage(), this.x + 1, this.y + 1, this.isHovered ? 0xFFFF55: 0xFFFFFF);
    }

    public static MutableComponent appendBrackets(MutableComponent component) {
        return Component.literal("[").append(component).append("]");
    }
}
