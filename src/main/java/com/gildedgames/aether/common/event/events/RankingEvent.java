package com.gildedgames.aether.common.event.events;

import com.gildedgames.aether.core.api.registers.PlayerRanking;
import net.minecraftforge.eventbus.api.Event;

import java.util.*;

public class RankingEvent extends Event
{
    private final Map<UUID, Set<PlayerRanking>> ranks;

    public RankingEvent(Map<UUID, Set<PlayerRanking>> ranks) {
        this.ranks = ranks;
    }

    public boolean addRank(String uuidString, PlayerRanking rank) {
        return this.addRank(UUID.fromString(uuidString), rank);
    }

    public boolean addRank(UUID uuid, PlayerRanking rank) {
        return this.ranks.computeIfAbsent(uuid, l -> EnumSet.noneOf(PlayerRanking.class)).add(rank);
    }

    public boolean addRank(String uuidString, PlayerRanking... ranksIn) {
        return this.addRank(UUID.fromString(uuidString), ranksIn);
    }

    public boolean addRank(UUID uuid, PlayerRanking... rank) {
        return this.ranks.computeIfAbsent(uuid, l -> EnumSet.noneOf(PlayerRanking.class)).addAll(Arrays.asList(rank));
    }

    public boolean removeRank(String uuidString, PlayerRanking rank) {
        return this.removeRank(UUID.fromString(uuidString), rank);
    }

    public boolean removeRank(UUID uuid, PlayerRanking rank) {
        Set<PlayerRanking> ranks = this.ranks.get(uuid);
        return ranks != null && ranks.remove(rank);
    }

    public boolean removeRank(String uuidString, PlayerRanking... ranksIn) {
        return this.removeRank(UUID.fromString(uuidString), ranksIn);
    }

    public boolean removeRank(UUID uuid, PlayerRanking... rank) {
        Set<PlayerRanking> ranks = this.ranks.get(uuid);
        return ranks != null && ranks.removeAll(Arrays.asList(rank));
    }
}
