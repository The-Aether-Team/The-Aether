package com.gildedgames.aether.client.gui.screen;

import com.gildedgames.aether.client.gui.button.NpcDialogueComponent;
import com.gildedgames.aether.client.gui.button.PlayerDialogueOption;
import com.gildedgames.aether.common.entity.monster.dungeon.ValkyrieQueen;
import com.gildedgames.aether.common.registry.AetherItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * Screen for speaking with the valkyrie queen.
 * @see PlayerDialogueOption
 */
@OnlyIn(Dist.CLIENT)
public class ValkyrieQueenDialogueScreen extends Screen {

    private final ValkyrieQueen queen;
    protected NpcDialogueComponent dialogue;

    public ValkyrieQueenDialogueScreen(ValkyrieQueen queen) {
        super(queen.getBossName());
        this.dialogue = new NpcDialogueComponent(setupQueenName(queen.getBossName()));
        this.queen = queen;
    }

    @Override
    protected void init() {
        this.addDialogueOptions(
                new PlayerDialogueOption(buildPlayerDialogue("question"), pButton -> this.onClose()),
                new PlayerDialogueOption(buildPlayerDialogue("challenge"), pButton -> {
                    this.setDialogue(new TranslatableComponent("gui.aether.queen.dialog.challenge"));
                    PlayerDialogueOption option;
                    int count = this.minecraft.player.getInventory().countItem(AetherItems.VICTORY_MEDAL.get());
                    if (count >= 10) {
                        option = new PlayerDialogueOption(buildPlayerDialogue("have_medals"), button -> this.onClose());
                    } else {
                        option = new PlayerDialogueOption(buildPlayerDialogue("no_medals").append(new TextComponent("I'll return when I have them. (" + count + "/10)")), button -> this.onClose());
                    }
                    this.addDialogueOptions(
                            option,
                            new PlayerDialogueOption(buildPlayerDialogue("deny_fight"), button -> this.onClose())
                    );
                }),
                new PlayerDialogueOption(buildPlayerDialogue("leave"), pButton -> this.onClose())
        );
        this.positionDialogueOptions();
    }

    @Override
    public void resize(@Nonnull Minecraft pMinecraft, int pWidth, int pHeight) {
        this.width = pWidth;
        this.height = pHeight;
        this.positionDialogueOptions();
    }

    /**
     * Adds and repositions a new set of dialogue options.
     */
    public void addDialogueOptions(PlayerDialogueOption... options) {
        this.clearWidgets();
        for (PlayerDialogueOption option : options) {
            this.addRenderableWidget(option);
        }
        this.positionDialogueOptions();
    }

    private void positionDialogueOptions() {
        int lineNumber = this.dialogue.height / 12;
        this.dialogue.x = this.width / 2 - this.dialogue.width / 2;
        this.dialogue.y = this.height / 2;
        for (Widget widget : this.renderables) {
            if (widget instanceof PlayerDialogueOption option) {
                option.x = this.width / 2 - option.getWidth() / 2;
                option.y = this.height / 2 + 12 * lineNumber;
                lineNumber++;
            }
        }
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.dialogue.render(pPoseStack);
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

    private void setDialogue(Component component) {
        this.dialogue.message = setupQueenName(this.queen.getBossName()).append(": ").append(component);
    }

    public static MutableComponent setupQueenName(Component component) {
        return new TextComponent("[").append(component.copy().withStyle(ChatFormatting.YELLOW)).append("]");
    }

    public static TranslatableComponent buildPlayerDialogue(String id) {
        return new TranslatableComponent("gui.aether.player.dialog." + id);
    }
}
