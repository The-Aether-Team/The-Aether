package com.gildedgames.aether.common.entity;

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
     * Handles an NPC reaction on the server. The interactionID corresponds to the option the player chose.
     */
    void handleNpcInteraction(Player player, byte interactionID);
}
