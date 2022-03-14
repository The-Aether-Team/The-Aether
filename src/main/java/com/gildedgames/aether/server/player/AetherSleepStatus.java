package com.gildedgames.aether.server.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Vanilla copy - SleepStatus
 * This version of SleepStatus replaces the one in the Aether's server level to
 * prevent sleeping in the Aether from changing the time in the Overworld.
 */
public class AetherSleepStatus extends SleepStatus {
    private int activePlayers;
    private int sleepingPlayers;

    public boolean areEnoughDeepSleepingInAether(int p_144005_, List<ServerPlayer> players) {
        int i = (int)players.stream().filter(Player::isSleepingLongEnough).count();
        return i >= this.sleepersNeeded(p_144005_);
    }

    @Override
    public boolean areEnoughSleeping(int p_144003_) {
        return this.sleepingPlayers >= this.sleepersNeeded(p_144003_);
    }

    /**
     * Returns false to prevent time from skipping in other worlds.
     * Code moved to areEnoughDeepSleepingInAether(int, List<ServerPlayer>)
     */
    @Override
    public boolean areEnoughDeepSleeping(int p_144005_, @Nonnull List<ServerPlayer> players) {
        return false;
    }

    @Override
    public int sleepersNeeded(int p_144011_) {
        return Math.max(1, Mth.ceil((float)(this.activePlayers * p_144011_) / 100.0F));
    }

    @Override
    public void removeAllSleepers() {
        this.sleepingPlayers = 0;
    }

    @Override
    public int amountSleeping() {
        return this.sleepingPlayers;
    }

    @Override
    public boolean update(List<ServerPlayer> players) {
        int i = this.activePlayers;
        int j = this.sleepingPlayers;
        this.activePlayers = 0;
        this.sleepingPlayers = 0;

        for(ServerPlayer serverplayer : players) {
            if (!serverplayer.isSpectator()) {
                ++this.activePlayers;
                if (serverplayer.isSleeping()) {
                    ++this.sleepingPlayers;
                }
            }
        }

        return (j > 0 || this.sleepingPlayers > 0) && (i != this.activePlayers || j != this.sleepingPlayers);
    }
}
