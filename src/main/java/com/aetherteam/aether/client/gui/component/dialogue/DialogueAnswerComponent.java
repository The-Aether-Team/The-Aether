package com.aetherteam.aether.client.gui.component.dialogue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

/**
 * A widget to handle an NPC's name and their response inside the dialogue screen.
 */
public class DialogueAnswerComponent {
    private final List<NpcDialogueElement> splitLines;
    public int height;

    public DialogueAnswerComponent(Component message) {
        this.splitLines = new ArrayList<>();
        this.updateDialogue(message);
    }

    public void render(GuiGraphics guiGraphics) {
        this.splitLines.forEach(element -> element.render(guiGraphics));
    }

    /**
     * Repositions the dialogue to the center of the screen.
     *
     * @param width  The {@link Integer} for the parent screen width.
     * @param height The {@link Integer} for the parent screen height.
     */
    public void reposition(int width, int height) {
        int i = 0;
        for (NpcDialogueElement dialogue : this.splitLines) {
            dialogue.width = Minecraft.getInstance().font.width(dialogue.text) + 2;
            dialogue.x = width / 2 - dialogue.width / 2;
            dialogue.y = height / 2 + i * 12;
            i++;
        }
        this.height = this.splitLines.size() * 12;
    }

    public void updateDialogue(Component message) {
        this.splitLines.clear();
        List<FormattedCharSequence> list = Minecraft.getInstance().font.split(message, 300);
        this.height = list.size() * 12;
        list.forEach(text -> this.splitLines.add(new NpcDialogueElement(0, 0, 0, text)));
    }

    /**
     * This inner class is used to store data for each line of text.
     */
    public static class NpcDialogueElement {
        private final FormattedCharSequence text;
        private int x;
        private int y;
        private int width;

        public NpcDialogueElement(int x, int y, int width, FormattedCharSequence text) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.width = width;
        }

        public void render(GuiGraphics guiGraphics) {
            guiGraphics.fillGradient(this.x, this.y, this.x + width, this.y + 12, 0x66000000, 0x66000000);
            guiGraphics.drawString(Minecraft.getInstance().font, this.text, this.x + 1, this.y + 1, 0xFFFFFF);
        }
    }
}
