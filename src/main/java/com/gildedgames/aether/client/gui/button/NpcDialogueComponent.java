package com.gildedgames.aether.client.gui.button;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

/**
 * GuiComponent for the npc to respond to the player with.
 */
public class NpcDialogueComponent extends GuiComponent {
    public Component message;
    public int x;
    public int y;
    public int width;
    public int height;

    public NpcDialogueComponent(Component pMessage) {
        this.message = pMessage;
        this.width = Minecraft.getInstance().font.width(pMessage) + 2;
        this.height = (pMessage.getString().length() / 300 + 1) * 12;
    }

    public void render(@Nonnull PoseStack pPoseStack) {
        this.fillGradient(pPoseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0x66000000, 0x66000000);
        drawString(pPoseStack, Minecraft.getInstance().font, this.message, this.x + 1, this.y + 1, 0xFFFFFF);
    }

    /**
     * Repositions the dialogue to the center of the screen.
     */
    public void reposition(int width, int height) {
        this.width = Minecraft.getInstance().font.width(this.message.getVisualOrderText()) + 2;
        this.x = width / 2 - this.width / 2;
        this.y = height / 2;
    }
}
