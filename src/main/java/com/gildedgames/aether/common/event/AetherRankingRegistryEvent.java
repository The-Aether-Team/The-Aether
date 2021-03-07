package com.gildedgames.aether.common.event;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.gildedgames.aether.core.capability.player.AetherRank;

import net.minecraftforge.eventbus.api.Event;

public class AetherRankingRegistryEvent extends Event {
	private final Map<UUID, Set<AetherRank>> ranks;
	
	public AetherRankingRegistryEvent(Map<UUID, Set<AetherRank>> ranks) {
		this.ranks = ranks;
	}
	
	public boolean addRank(UUID uuid, AetherRank rank) {
		return this.ranks.computeIfAbsent(uuid, __ -> EnumSet.noneOf(AetherRank.class)).add(rank);
	}
	
	public boolean addRank(UUID uuid, AetherRank... ranksIn) {
		return this.ranks.computeIfAbsent(uuid, __ -> EnumSet.noneOf(AetherRank.class)).addAll(Arrays.asList(ranksIn));
	}
	
	public boolean addRank(String uuidStr, AetherRank rank) {
		return this.addRank(UUID.fromString(uuidStr), rank);
	}
	
	public boolean addRank(String uuidStr, AetherRank... ranksIn) {
		return this.addRank(UUID.fromString(uuidStr), ranksIn);
	}
	
	public boolean removeRank(UUID uuid, AetherRank rank) {
		Set<AetherRank> ranks = this.ranks.get(uuid);
		return ranks != null && ranks.remove(rank);
	}
	
	public boolean removeRank(UUID uuid, AetherRank... ranksIn) {
		Set<AetherRank> ranks = this.ranks.get(uuid);
		return ranks != null && ranks.removeAll(Arrays.asList(ranksIn));
	}
	
	public boolean removeRank(String uuidStr, AetherRank rank) {
		return this.removeRank(UUID.fromString(uuidStr), rank);
	}
	
	public boolean removeRank(String uuidStr, AetherRank... ranksIn) {
		return this.removeRank(UUID.fromString(uuidStr), ranksIn);
	}
	
	/**
	 * @deprecated You forgot to add a list of ranks
	 */
	@Deprecated
	public boolean addRank(UUID uuid) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @deprecated You forgot to add a list of ranks
	 */
	@Deprecated
	public boolean addRank(String uuidStr) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @deprecated You forgot to add a list of ranks
	 */
	@Deprecated
	public boolean removeRank(UUID uuid) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated You forgot to add a list of ranks
	 */
	@Deprecated
	public boolean removeRank(String uuidStr) {
		throw new UnsupportedOperationException();
	}
	
}
