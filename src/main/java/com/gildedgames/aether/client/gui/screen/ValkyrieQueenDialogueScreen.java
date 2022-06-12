package com.gildedgames.aether.client.gui.screen;

import com.gildedgames.aether.client.gui.button.DialogueElement;
import com.gildedgames.aether.common.entity.monster.dungeon.ValkyrieQueen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * Screen for speaking with the valkyrie queen.
 * @see DialogueElement
 */
@OnlyIn(Dist.CLIENT)
public class ValkyrieQueenDialogueScreen extends Screen {

    private final ValkyrieQueen queen;

    public ValkyrieQueenDialogueScreen(ValkyrieQueen queen) {
        super(queen.getDisplayName()); // TODO: Implement proper boss naming.
        this.queen = queen;
    }

    @Override
    protected void init() {
        this.addDialogueOptions(new DialogueElement(new TextComponent("What is this place?")),
                new DialogueElement(new TextComponent("I wish to fight you!")),
                new DialogueElement(new TextComponent("Nevermind")));
    }

    /**
     * Adds and repositions a new set of dialogue options.
     */
    public void addDialogueOptions(DialogueElement... options) {
        this.renderables.clear();
        for (DialogueElement option : options) {
            this.addRenderableWidget(option);
        }
        this.positionDialogueOptions();
    }

    private void positionDialogueOptions() {
        int lineNumber = 0;
        for (Widget widget : this.renderables) {
            if (widget instanceof DialogueElement option) {
                option.setXPosition(this.width / 2 - option.width);
                option.setYPosition(this.height / 2 + this.font.substrByWidth(this.title, 300).getString().length() * 12 + 12 * lineNumber);

                lineNumber++;
            }
        }
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    /**
     * Override to prevent rendering a dark gradient over the background.
     */
    @Override
    public void renderBackground(@Nonnull PoseStack pPoseStack, int pVOffset) {
        if (this.minecraft.level != null) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ScreenEvent.BackgroundDrawnEvent(this, pPoseStack));
        } else {
            this.renderDirtBackground(pVOffset);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
