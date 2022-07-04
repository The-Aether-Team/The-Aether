package com.gildedgames.aether.entity.ai.goal;

import com.gildedgames.aether.entity.NpcDialogue;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

/**
 * This goal makes an NPC stop in place when talking with a player.
 */
public class NpcDialogueGoal<T extends Mob & NpcDialogue> extends LookAtPlayerGoal {
    private final T npc;
    public NpcDialogueGoal(T npc) {
        super(npc, Player.class, 8.0F);
        this.npc = npc;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Flag.LOOK, Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.npc.isTrading() && this.npc.getTradingPlayer().isAlive() && !this.npc.hurtMarked && this.npc.distanceToSqr(this.npc.getTradingPlayer()) <= 64.0F) {
            this.lookAt = this.npc.getTradingPlayer();
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    /**
     * Stops the npc from moving
     */
    @Override
    public void start() {
        super.start();
        this.npc.getNavigation().stop();
    }

    /**
     * Makes the npc stop talking to the player.
     */
    @Override
    public void stop() {
        super.stop();
        this.npc.setTradingPlayer(null);
    }
}
