package com.gildedgames.aether.client.gui.button;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class DialogueElement extends GuiComponent implements Widget, GuiEventListener, NarratableEntry {
    public final Component message;
    private int x;
    private int y;
    public final int height;
    public final int width;

    public DialogueElement(Component pMessage) {
        this.message = pMessage;
        this.height = 12;
        this.width = Minecraft.getInstance().font.width(this.message) + 2;
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.fillGradient(pPoseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0x66000000, 0x66000000);
        drawString(pPoseStack, Minecraft.getInstance().font, this.message, this.x + 2, this.y + 2, this.isMouseOver(pMouseX, pMouseY) ? 0xFFFF55: 0xFFFFFF);
    }

    public boolean isMouseOver(int pMouseX, int pMouseY) {
        return pMouseX >= this.x && pMouseY >= this.y && pMouseX < this.x + this.width && pMouseY < this.y + this.height;
    }

    public void setXPosition(int x) {
        this.x = x;
    }

    public void setYPosition(int y) {
        this.y = y;
    }

    @Override
    @Nonnull
    public NarrationPriority narrationPriority() {
        return NarratableEntry.NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@Nonnull NarrationElementOutput pNarrationElementOutput) {

    }
}
