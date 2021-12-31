package com.gildedgames.aether.core.api;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.event.AetherRankingRegistryEvent;
import com.gildedgames.aether.core.api.AetherRank;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

//TODO: Port over changes from the feature/ranking_system branch that's off of 1.16.
public class AetherRankings {
	
	private static final Map<UUID, Set<AetherRank>> RANKS;
	
	static {
		Map<UUID, Set<AetherRank>> ranks = Maps.newHashMap();
		MinecraftForge.EVENT_BUS.post(new AetherRankingRegistryEvent(ranks));
		for (Map.Entry<UUID, Set<AetherRank>> entry : ranks.entrySet()) {
			entry.setValue(ImmutableSet.copyOf(entry.getValue()));
		}
		RANKS = ImmutableMap.copyOf(ranks);
	}
	
	public static Set<AetherRank> getRanksOf(UUID uuid) {
		return RANKS.getOrDefault(uuid, Collections.emptySet());
	}
	
	public static AetherRank getPrimaryRankOf(UUID uuid) {
		return getRanksOf(uuid).stream().sorted(Comparator.reverseOrder()).findFirst().orElse(AetherRank.NONE);
	}
	
	@EventBusSubscriber(modid = Aether.MODID)
	public static class Registration {
		
		@SubscribeEvent
		public static void registerRanks(AetherRankingRegistryEvent event) {
			/* * * * * * * * * * * * * *\
			 * THIS LIST IS INCOMPLETE 
			\* * * * * * * * * * * * * */
			
			
			event.addRank("c3e6871e-8e60-490a-8a8d-2bbe35ad1604", AetherRank.DEVELOPER, AetherRank.GILDED_GAMES); // Raptor
			event.addRank("58a5d694-a8a6-4605-ab33-d6904107ad5f", AetherRank.DEVELOPER, AetherRank.GILDED_GAMES); // bconlon
			event.addRank("353a859b-ba16-4e6a-8f63-9a8c79ab0071", AetherRank.DEVELOPER, AetherRank.GILDED_GAMES); // quek
			event.addRank("3c0e4411-3421-40bd-b092-056d3e99b98a", AetherRank.DEVELOPER, AetherRank.GILDED_GAMES); // Oscar Payne
			
			event.addRank("6e8be0ba-e4bb-46af-aea8-2c1f5eec5bc2", AetherRank.RETIRED_DEVELOPER); // Brendan Freeman
			event.addRank("5f112332-0993-4f52-a5ab-9a55dc3173cb", AetherRank.RETIRED_DEVELOPER); // JorgeQ
			event.addRank("6a0e8505-1556-4ee9-bec0-6af32f05888d", AetherRank.RETIRED_DEVELOPER); // 115kino
			event.addRank("1d680bb6-2a9a-4f25-bf2f-a1af74361d69", AetherRank.RETIRED_DEVELOPER); // Phygie
			
			event.addRank("13655ac1-584d-4785-b227-650308195121", AetherRank.GILDED_GAMES, AetherRank.MOJANG); // Brandon Pearce
			event.addRank("6fb2f965-6b57-46de-9ef3-0ef4c9b9bdc6", AetherRank.GILDED_GAMES); // Hugo Payn
			event.addRank("dc4cf9b2-f601-4eb4-9436-2924836b9f42", AetherRank.GILDED_GAMES); // Jaryt Bustard
			event.addRank("c0643897-c500-4f61-a62a-8051801562a9", AetherRank.GILDED_GAMES); // Christian Peterson
			
			event.addRank("4bfb28a3-005d-4fc9-9238-a55c6c17b575", AetherRank.RETIRED_GILDED_GAMES); // Jon Lachney
			event.addRank("2afd6a1d-1531-4985-a104-399c0c19351d", AetherRank.RETIRED_GILDED_GAMES); // Brandon Potts
			event.addRank("b5ee3d5d-2ad7-4642-b9d4-6b041ad600a4", AetherRank.RETIRED_GILDED_GAMES); // Emile van Krieken
			event.addRank("ffb94179-dd54-400d-9ece-834720cd7be9", AetherRank.RETIRED_GILDED_GAMES); // Collin Soares
			
			event.addRank("853c80ef-3c37-49fd-aa49-938b674adae6", AetherRank.MOJANG); // Jens Bergensten
			// https://namemc.com/cape/cb5dd34bee340182  for a list of mojang employees' game accounts, I don't wanna do all of them right now
			
			event.addRank("5f820c39-5883-4392-b174-3125ac05e38c", AetherRank.CELEBRITY); // CaptainSparklez
			event.addRank("0c063bfd-3521-413d-a766-50be1d71f00e", AetherRank.CELEBRITY); // AntVenom
			event.addRank("069a79f4-44e9-4726-a5be-fca90e38aaf5", AetherRank.CELEBRITY); // Notch
			event.addRank("c7da90d5-6a05-4217-b94a-7d427cbbcad8", AetherRank.CELEBRITY); // Mumbo Jumbo
			event.addRank("5f8eb73b-25be-4c5a-a50f-d27d65e30ca0", AetherRank.CELEBRITY); // Grian
			
			event.addRank("6f8be24f-03f3-4288-9218-16c9ecc08c8f", AetherRank.CONTRIBUTOR); // Jonathing
			event.addRank("c15c4d6d-9a80-4d6b-9eda-770859b5ed91", AetherRank.CONTRIBUTOR); // Everett1999
			event.addRank("4f0e8dd5-caf4-4d88-bfa4-1b0f1e13779f", AetherRank.CONTRIBUTOR); // Indianajaune
			event.addRank("6c249311-f939-4e66-9f31-49b753bfb14b", AetherRank.CONTRIBUTOR); // InsomniaKitten
			event.addRank("2b5187c9-dc5d-480e-ab6f-e884e92fce45", AetherRank.CONTRIBUTOR); // ItzDennisz
//			event.addRank("????????-????-????-????-????????????", CONTRIBUTOR); // Overspace
		}
		
	}
	
}
