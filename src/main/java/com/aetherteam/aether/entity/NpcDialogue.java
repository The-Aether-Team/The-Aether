package com.aetherteam.aether.entity;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Interface for any NPC that can be engaged in conversation.
 * Never implement this with a class that isn't an entity!
 */
public interface NpcDialogue {
    /**
     * This method shouldn't be used on the server.
     */
    @OnlyIn(Dist.CLIENT)
    void openDialogueScreen();

    /**
     * Handles an NPC reaction on the server.
     * @param player The interacting {@link Player}.
     * @param interactionID The {@link Byte} ID corresponding to the option the player chose.
     */
    void handleNpcInteraction(Player player, byte interactionID);

    /**
     * These methods are used to store and retrieve the player whom the NPC is conversing with.
     */
    Player getConversingPlayer();
    default boolean isConversing() {
        return this.getConversingPlayer() != null;
    }
    void setConversingPlayer(Player player);
}
