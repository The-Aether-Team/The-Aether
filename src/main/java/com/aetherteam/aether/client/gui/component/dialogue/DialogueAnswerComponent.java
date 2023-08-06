package com.aetherteam.aether.client.gui.component.dialogue;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

/**
 * GuiComponent for the npc to respond to the player with.
 */
public class DialogueAnswerComponent extends GuiComponent {
    private final List<NpcDialogueElement> splitLines;
    public int height;

    public DialogueAnswerComponent(Component pMessage) {
        this.splitLines = new ArrayList<>();
        this.updateDialogue(pMessage);
    }

    public void render(PoseStack pPoseStack) {
        this.splitLines.forEach(element -> element.render(pPoseStack));
    }

    /**
     * Repositions the dialogue to the center of the screen.
     */
    public void reposition(int width, int height) {
        int i = 0;
        for (NpcDialogueElement dialogue : splitLines) {
            dialogue.width = Minecraft.getInstance().font.width(dialogue.text) + 2;
            dialogue.x = width / 2 - dialogue.width / 2;
            dialogue.y = height / 2 + i * 12;
            i++;
        }
        this.height = this.splitLines.size() * 12;
    }

    public void updateDialogue(Component pMessage) {
        this.splitLines.clear();
        List<FormattedCharSequence> list = Minecraft.getInstance().font.split(pMessage, 300);
        this.height = list.size() * 12;
        list.forEach(text -> this.splitLines.add(new NpcDialogueElement(0, 0, 0, text)));
    }

    /**
     * This inner class is used to store data for each line of text.
     */
    public class NpcDialogueElement {
        public int x;
        public int y;
        public int width;
        public FormattedCharSequence text;

        public NpcDialogueElement(int x, int y, int width, FormattedCharSequence text) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.text = text;
        }

        public void render(PoseStack pPoseStack) {
            DialogueAnswerComponent.this.fillGradient(pPoseStack, this.x, this.y, this.x + width, this.y + 12, 0x66000000, 0x66000000);
            drawString(pPoseStack, Minecraft.getInstance().font, this.text, this.x + 1, this.y + 1, 0xFFFFFF);
        }
    }
}
