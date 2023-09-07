package com.aetherteam.aether.client.gui.screen;

import com.aetherteam.aether.client.gui.component.dialogue.DialogueAnswerComponent;
import com.aetherteam.aether.client.gui.component.dialogue.DialogueChoiceComponent;
import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.NpcPlayerInteractPacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;

public class ValkyrieQueenDialogueScreen extends Screen {
    private final DialogueAnswerComponent dialogueAnswer;
    private final ValkyrieQueen valkyrieQueen;

    public ValkyrieQueenDialogueScreen(ValkyrieQueen valkyrieQueen) {
        super(valkyrieQueen.getBossName());
        this.dialogueAnswer = new DialogueAnswerComponent(this.buildDialogueAnswerName(valkyrieQueen.getBossName()));
        this.valkyrieQueen = valkyrieQueen;
    }

    @Override
    protected void init() {
        if (this.getMinecraft().player != null) {
            this.setupDialogueChoices( // Set up choices.
                    new DialogueChoiceComponent(this.buildDialogueChoice("question"), button -> this.finishChat((byte) 0)),
                    new DialogueChoiceComponent(this.buildDialogueChoice("challenge"), button -> { // Opens a new dialogue tree.
                        this.setDialogueAnswer(Component.translatable("gui.aether.queen.dialog.challenge")); // The Valkyrie Queen's response to the challenge choice in the GUI (not a chat message).
                        int medals = this.getMinecraft().player.getInventory().countItem(AetherItems.VICTORY_MEDAL.get());
                        DialogueChoiceComponent startFightChoice = medals >= 10
                                ? new DialogueChoiceComponent(this.buildDialogueChoice("have_medals"), button1 -> this.finishChat((byte) 1))
                                : new DialogueChoiceComponent(this.buildDialogueChoice("no_medals").append(" (" + medals + "/10)"), button1 -> this.finishChat((byte) 1));
                        this.setupDialogueChoices( // Set up another set of choices.
                                startFightChoice,
                                new DialogueChoiceComponent(this.buildDialogueChoice("deny_fight"), button1 -> this.finishChat((byte) 2))
                        );
                    }),
                    new DialogueChoiceComponent(this.buildDialogueChoice("leave"), pButton -> this.finishChat((byte) 3))
            );
            this.positionDialogue();
        }
    }

    /**
     * Adds and repositions a new set of dialogue options.
     * @param options The {@link DialogueChoiceComponent} option buttons to render.
     */
    public void setupDialogueChoices(DialogueChoiceComponent... options) {
        this.clearWidgets();
        for (DialogueChoiceComponent option : options) {
            this.addRenderableWidget(option);
        }
        this.positionDialogue();
    }

    /**
     * Repositions the Valkyrie Queen's dialogue answer and the player's dialogue choices based on the amount of choices.
     */
    private void positionDialogue() {
        // Dialogue answer.
        this.dialogueAnswer.reposition(this.width, this.height);
        // Dialogue choices.
        int lineNumber = this.dialogueAnswer.height / 12 + 1;
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof DialogueChoiceComponent option) {
                option.setX(this.width / 2 - option.getWidth() / 2);
                option.setY(this.height / 2 + 12 * lineNumber);
                lineNumber++;
            }
        }
    }

    /**
     * Sets what message to display for a dialogue answer.
     * @param component The message {@link Component}.
     */
    private void setDialogueAnswer(Component component) {
        this.dialogueAnswer.updateDialogue(this.buildDialogueAnswerName(this.valkyrieQueen.getBossName()).append(": ").append(component));
    }

    /**
     * Sets up the formatting for the Valkyrie Queen's name in the {@link DialogueAnswerComponent} widget.
     * @param component The name {@link Component}.
     * @return The formatted {@link MutableComponent}.
     */
    public MutableComponent buildDialogueAnswerName(Component component) {
        return Component.literal("[").append(component.copy().withStyle(ChatFormatting.YELLOW)).append("]");
    }

    /**
     * Sets up the text for a player dialogue choice in a {@link DialogueChoiceComponent} button.
     * @param key The suffix {@link String} to get the full translation key from.
     * @return The {@link MutableComponent} for the choice text.
     */
    public MutableComponent buildDialogueChoice(String key) {
        return Component.translatable("gui.aether.player.dialog." + key);
    }

    /**
     * Sends an NPC interaction to the server, which is sent through a packet to be handled in {@link ValkyrieQueen#handleNpcInteraction(Player, byte)}.
     * @param interactionID A code for which interaction was performed on the client.<br>
     *                      0 - "What can you tell me about this place?"<br>
     *                      1 - "I wish to fight you!"<br>
     *                      2 - "On second thought, I'd rather not."<br>
     *                      3 - "Nevermind."<br>
     * @see NpcPlayerInteractPacket
     * @see ValkyrieQueen#handleNpcInteraction(Player, byte)
     */
    private void finishChat(byte interactionID) {
        PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new NpcPlayerInteractPacket(this.valkyrieQueen.getId(), interactionID));
        super.onClose();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        this.dialogueAnswer.render(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    /**
     * [CODE COPY] - {@link Screen#renderBackground(PoseStack)}.<br><br>
     * Remove code for dark gradient and dirt background.
     */
    @Override
    public void renderBackground(PoseStack poseStack) {
        if (this.getMinecraft().level != null) {
            MinecraftForge.EVENT_BUS.post(new ScreenEvent.BackgroundRendered(this, poseStack));
        }
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.width = width;
        this.height = height;
        this.positionDialogue();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.finishChat((byte) 3);
    }
}
