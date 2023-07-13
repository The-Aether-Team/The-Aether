package com.aetherteam.aether.client.gui.screen;

import com.aetherteam.aether.client.gui.component.NpcDialogueComponent;
import com.aetherteam.aether.client.gui.component.PlayerDialogueButton;
import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.NpcPlayerInteractPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

/**
 * Screen for speaking with the valkyrie queen.
 * @see PlayerDialogueButton
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
                new PlayerDialogueButton(buildPlayerDialogue("question"), pButton -> this.finishChat((byte) 0)),
                new PlayerDialogueButton(buildPlayerDialogue("challenge"), pButton -> {
                    this.setDialogue(Component.translatable("gui.aether.queen.dialog.challenge"));
                    PlayerDialogueButton option;
                    int count = this.minecraft.player.getInventory().countItem(AetherItems.VICTORY_MEDAL.get());
                    if (count >= 10) {
                        option = new PlayerDialogueButton(buildPlayerDialogue("have_medals"), button -> this.finishChat((byte) 1));
                    } else {
                        option = new PlayerDialogueButton(buildPlayerDialogue("no_medals").append(" (" + count + "/10)"), button -> this.finishChat((byte) 1));
                    }
                    this.addDialogueOptions(
                            option,
                            new PlayerDialogueButton(buildPlayerDialogue("deny_fight"), button -> this.finishChat((byte) 2))
                    );
                }),
                new PlayerDialogueButton(buildPlayerDialogue("leave"), pButton -> this.finishChat((byte) 3))
        );
        this.positionDialogueOptions();
    }

    /**
     * Sends an NPC interaction to the server.
     * @see NpcPlayerInteractPacket
     * @see ValkyrieQueen#handleNpcInteraction(Player, byte)
     * @param interactionID - A code for which interaction was performed on the client.
     *                      0 - What can you tell me about this place?
     *                      1 - Challenged to a fight.
     *                      2 - Actually, I changed my mind (fight)
     *                      3 - Nevermind
     */
    private void finishChat(byte interactionID) {
        AetherPacketHandler.sendToServer(new NpcPlayerInteractPacket(this.queen.getId(), interactionID));
        super.onClose();
    }

    @Override
    public void onClose() {
        this.finishChat((byte) 3);
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
    public void addDialogueOptions(PlayerDialogueButton... options) {
        this.clearWidgets();
        for (PlayerDialogueButton option : options) {
            this.addRenderableWidget(option);
        }
        this.positionDialogueOptions();
    }

    private void positionDialogueOptions() {
        this.dialogue.reposition(this.width, this.height);
        int lineNumber = this.dialogue.height / 12 + 1;
        for (Renderable renderable : this.renderables) {
            if (renderable instanceof PlayerDialogueButton option) {
                option.setX(this.width / 2 - option.getWidth() / 2);
                option.setY(this.height / 2 + 12 * lineNumber);
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
    public void renderBackground(@Nonnull PoseStack poseStack) {
        if (this.minecraft.level != null) {
            MinecraftForge.EVENT_BUS.post(new ScreenEvent.BackgroundRendered(this, poseStack));
        } else {
            this.renderDirtBackground(poseStack);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void setDialogue(Component component) {
        this.dialogue.updateDialogue(setupQueenName(this.queen.getBossName()).append(": ").append(component));
    }

    public static MutableComponent setupQueenName(Component component) {
        return Component.literal("[").append(component.copy().withStyle(ChatFormatting.YELLOW)).append("]");
    }

    public static MutableComponent buildPlayerDialogue(String id) {
        return Component.translatable("gui.aether.player.dialog." + id);
    }
}
